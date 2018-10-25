/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shvul.wda.client.remote;

import com.github.shvul.wda.client.driver.CommandExecutor;
import com.github.shvul.wda.client.support.IOUtil;
import com.github.shvul.wda.client.support.LoggerManager;
import com.github.shvul.wda.client.driver.DriverCapabilities;
import com.github.shvul.wda.client.exception.WebDriverAgentException;
import com.github.shvul.wda.client.support.XCodeBuilder;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WebDriverAgentRunner {
    private static final String WDA_BASE_URL = "http://localhost";
    private static final String WDA_STATE_FIELD = "state";
    private static final int WDA_AGENT_PORT = 8100;
    private static final int WDA_LAUNCH_TIMEOUT = 60;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DriverCapabilities capabilities;
    private CommandExecutor commandExecutor;
    private Process wdaProcess;

    public WebDriverAgentRunner(DriverCapabilities capabilities) {
        this.capabilities = capabilities;
        this.commandExecutor = new WDACommandExecutor(getWdaUrl());
    }

    public void start() {
        boolean startNewProcess = Optional.ofNullable(capabilities.getCapability(DriverCapabilities.Key.PREBUILT_WDA))
                .map(k -> !Boolean.valueOf(k))
                .orElse(true);
        if (startNewProcess) {
            LoggerManager.info("Start WebDriverAgent.");
            wdaProcess = new XCodeBuilder()
                    .setWdaPath(capabilities.getCapability(DriverCapabilities.Key.WDA_PATH))
                    .setPlatform(capabilities.getCapability(DriverCapabilities.Key.PLATFORM))
                    .setDeviceName(capabilities.getCapability(DriverCapabilities.Key.DEVICE_NAME))
                    .setDeviceId(capabilities.getCapability(DriverCapabilities.Key.DEVICE_ID))
                    .setOsVersion(capabilities.getCapability(DriverCapabilities.Key.OS_VERSION))
                    .build();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                Optional.ofNullable(wdaProcess).ifPresent(Process::destroyForcibly);
            }));
        } else {
            LoggerManager.info("Use existing WebDriverAgent process.");
        }

        waitForReachability(getWdaUrl(), WDA_LAUNCH_TIMEOUT);
        checkStatus();
    }

    public void stop() {
        LoggerManager.info("Stop WebDriverAgent.");
        Optional.ofNullable(wdaProcess).ifPresent(process -> {
            LoggerManager.getLogger(LoggerManager.SERVER_LOGGER_NAME).debug(IOUtil.getOutput(process));
            process.destroyForcibly();
        });
    }

    public URL getWdaUrl() {

        String urlStr = Optional.ofNullable(capabilities.getCapability(DriverCapabilities.Key.DEVICE_IP))
                .map(url -> String.format("http://%s:%s", url, WDA_AGENT_PORT))
                .orElse(String.format("%s:%s", WDA_BASE_URL, WDA_AGENT_PORT));
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            throw new WebDriverAgentException("Url syntax is malformed: " + urlStr);
        }
    }

    private void waitForReachability(URL url, int timeout) {
        LoggerManager.info("Wait for WebDriverAgent reachability.");

        try {
            SimpleTimeLimiter.create(executorService).callWithTimeout(() -> {
                long sleepMillis = 10L;
                while (true) {
                    if (isUrlReachable(url)) {
                        return sleepMillis;
                    }

                    TimeUnit.MILLISECONDS.sleep(sleepMillis);
                    sleepMillis = sleepMillis >= 320L ? sleepMillis : sleepMillis * 2L;
                }
            }, timeout, TimeUnit.SECONDS);
            LoggerManager.info("WebDriverAgent is reachable.");
        } catch (TimeoutException | InterruptedException | ExecutionException e) {

            Optional.ofNullable(wdaProcess).ifPresent(process -> {
                String output = IOUtil.getOutput(wdaProcess);
                LoggerManager.getLogger(LoggerManager.SERVER_LOGGER_NAME).debug(output);
            });
            throw new WebDriverAgentException(String.format("WDA is not reachable in %d seconds.", timeout), e);
        }
    }

    private boolean isUrlReachable(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = this.connectToUrl(url);
            return connection.getResponseCode() == HttpStatus.SC_OK;
        } catch (IOException e) {
            LoggerManager.debug("Wait for WDA to be available: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    private void checkStatus() {
        LoggerManager.info("Check WebDriverAgent status.");
        RemoteResponse response = commandExecutor.execute(WDACommand.STATUS);
        String state = (String) response.getValueAsMap().get(WDA_STATE_FIELD);

        if (!state.equals("success")) {
            throw new WebDriverAgentException("WDA returned error state: " + state);
        }
    }

    private HttpURLConnection connectToUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(500);
        connection.setReadTimeout(1000);
        connection.connect();
        return connection;
    }
}
