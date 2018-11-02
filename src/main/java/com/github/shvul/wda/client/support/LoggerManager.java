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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerManager {
    public static final String CLIENT_LOGGER_NAME = "wda.client";
    public static final String SERVER_LOGGER_NAME = "wda.xcode";
    private static final String CLIENT_CONSOLE_APPENDER_NAME = "Console";
    private static final String SERVER_CONSOLE_APPENDER_NAME = "Console-Xcode";
    private static final Logger LOGGER = LoggerFactory.getLogger(CLIENT_LOGGER_NAME);

    public static Logger getLogger() {
        return LOGGER;
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
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

    public static void error(Throwable throwable) {
        LOGGER.error(throwable.getMessage(), throwable);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    public static void enableClientConsoleLogging() {
        enableConsoleLogging(CLIENT_LOGGER_NAME, CLIENT_CONSOLE_APPENDER_NAME);
    }

    public static void enableServerConsoleLogging() {
        enableConsoleLogging(SERVER_LOGGER_NAME, SERVER_CONSOLE_APPENDER_NAME);
    }

    public static void enableConsoleLogging(String loggerName, String appenderName) {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Configuration configuration = loggerContext.getConfiguration();
        LoggerConfig wdaLogger = configuration.getLoggerConfig(loggerName);
        Appender appender = configuration.getAppender(appenderName);
        wdaLogger.addAppender(appender, Level.ALL, null);
    }
}
