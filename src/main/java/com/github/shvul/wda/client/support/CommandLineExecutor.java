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

import com.github.shvul.wda.client.exception.WebDriverAgentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommandLineExecutor {

    private static final int DEFAULT_TIMEOUT = 30;

    public static String execute(String[] command) {
        return execute(command, DEFAULT_TIMEOUT);
    }

    public static String execute(String[] command, int timeout) {
        Process process = null;
        try {
            process = createProcess(command);
            process.waitFor(timeout, TimeUnit.SECONDS);
            return getOutput(process);
        } catch (InterruptedException | IOException e) {
            throw new WebDriverAgentException(e);
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
    }

    public static Process createProcess(String[] command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            return builder.start();
        } catch (IOException e) {
            throw new WebDriverAgentException(e);
        }
    }

    private static String getOutput(Process process) throws IOException {
        String error = getOutput(process.getErrorStream());
        if (error.isEmpty()) {
            return getOutput(process.getInputStream());
        }
        return error;
    }

    private static String getOutput(InputStream input) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            while (buffer.ready()) {
                builder.append(buffer.readLine());
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
