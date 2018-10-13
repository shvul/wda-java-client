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

package com.github.shvul.wda.client;


import com.github.shvul.wda.client.remote.RemoteResponse;

import java.util.Map;

public interface CommandExecutable {

    /**
     * Execute remote command on the WebDriverAgent.
     *
     * @param command key to be executed.
     * @param parameters for the command.
     * @return WebDriverAgent response
     * @see RemoteResponse
     */
    RemoteResponse execute(String command, Map<String, String> parameters);

    /**
     * Execute remote command on the WebDriverAgent with no parameters.
     *
     * @param command key to be executed.
     * @return WebDriverAgent response
     * @see RemoteResponse
     */
    RemoteResponse execute(String command);
}
