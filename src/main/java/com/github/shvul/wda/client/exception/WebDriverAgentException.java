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

package com.github.shvul.wda.client.exception;

public class WebDriverAgentException extends RuntimeException {

    private static final long serialVersionUID = 8477965642656299750L;

    public WebDriverAgentException(String message) {
        super(message);
    }

    public WebDriverAgentException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebDriverAgentException(Throwable cause) {
        super(cause);
    }
}
