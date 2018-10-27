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

package com.github.shvul.wda.client.support;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class XcodeLogger implements Runnable {

    private static final String WDA_RUNNER_NAME = "WebDriverAgentRunner_tvOS-Runner";
    private static final Logger LOG = LoggerManager.getLogger(LoggerManager.SERVER_LOGGER_NAME);

    private Process process;

    public XcodeLogger(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        while (true) {
            if (process == null || !process.isAlive()) {
                return;
            }
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                logMessage(buffer);
            } catch (IOException ignored) {
            }
        }
    }

    private void logMessage(BufferedReader buffer) throws IOException {
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = buffer.readLine()) != null) {
            if (builder.length() > 0 && line.contains(WDA_RUNNER_NAME)) {
                removeLastLineFeed(builder);
                LOG.info(builder.toString());
                builder = new StringBuilder(line);
            } else {
                builder.append(line);
                builder.append("\n");
            }
        }
        LOG.info(builder.toString());
    }

    private void removeLastLineFeed(StringBuilder builder) {
        char last = builder.charAt(builder.length() - 1);
        if (last == '\n') {
            builder.replace(builder.length() - 1, builder.length(), "");
        }
    }
}
