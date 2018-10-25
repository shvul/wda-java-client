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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class IOUtil {
    private IOUtil() {}

    public static String getOutput(Process process) {
        String error = getOutput(process.getErrorStream());
        if (error.isEmpty()) {
            return getOutput(process.getInputStream());
        }
        return error;
    }

    public static String getOutput(InputStream input) {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException ignored) {}
        return builder.toString();
    }
}
