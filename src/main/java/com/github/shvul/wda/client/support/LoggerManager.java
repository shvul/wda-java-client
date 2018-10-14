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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerManager {
    private static final Logger LOGGER = LogManager.getLogger("WDA Client");

    public static Logger getLogger() {
        return LOGGER;
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
}
