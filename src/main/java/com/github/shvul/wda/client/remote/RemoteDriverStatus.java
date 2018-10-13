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

import com.github.shvul.wda.client.exception.WebDriverAgentException;

public enum RemoteDriverStatus {

    NO_ERROR(0),
    UNSUPPORTED(1),
    NO_SUCH_SESSION(6),
    NO_SUCH_ELEMENT(7),
    UNHANDLED(13),
    INVALID_ARGUMENT(15),
    UNEXPECTED_ALERT_PRESENT(26),
    NO_ALERT_PRESENT(27),
    INVALID_SELECTOR(32),
    INVALID_XPATH_SELECTOR(51),
    APP_DEADLOCK_DETECTED(888),
    APP_CRASH_DETECTED(889);

    private int status;

    RemoteDriverStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public static RemoteDriverStatus getByStatusCode(int status) {
        for (RemoteDriverStatus driverStatus : RemoteDriverStatus.values()) {
            if (driverStatus.getStatus() == status) {
                return driverStatus;
            }
        }
        throw new WebDriverAgentException(String.format("Unknown status: %d", status));
    }

    public static String getMessage(RemoteDriverStatus status) {
        switch (status) {
        case NO_ERROR:
            return "Status OK";
        case UNSUPPORTED:
            return "Command is not supported";
        case NO_SUCH_SESSION:
            return "A session is either terminated or not started";
        case NO_SUCH_ELEMENT:
            return "An element could not be located on the page using the given search parameters";
        case UNHANDLED:
            return "An unknown server-side error occurred while processing the command";
        case INVALID_ARGUMENT:
            return "Invalid argument";
        case UNEXPECTED_ALERT_PRESENT:
            return "A modal dialog was open, blocking this operation";
        case NO_ALERT_PRESENT:
            return "An attempt was made to operate on a modal dialog when one was not open";
        case INVALID_SELECTOR:
            return "Argument was an invalid selector (e.g. XPath)";
        case INVALID_XPATH_SELECTOR:
            return "Invalid XPath selector";
        case APP_DEADLOCK_DETECTED:
            return "Application deadlock detected";
        case APP_CRASH_DETECTED:
            return "Application crash detected";
        default:
            return "";
        }
    }
}

