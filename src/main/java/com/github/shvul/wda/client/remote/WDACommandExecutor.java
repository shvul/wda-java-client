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
import com.github.shvul.wda.client.exception.WebDriverAgentException;
import com.github.shvul.wda.client.support.HttpService;
import com.github.shvul.wda.client.support.JsonConverter;
import com.github.shvul.wda.client.remote.WDACommand.Wildcard;
import com.github.shvul.wda.client.support.LoggerManager;

import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WDACommandExecutor implements CommandExecutor {

    private URL wdaUrl;

    public WDACommandExecutor(URL wdaUrl) {
        this.wdaUrl = wdaUrl;
    }

    @Override
    public RemoteResponse execute(String command, Map<Wildcard, String> wildcards, Map<String, ?> parameters) {
        LoggerManager.info(String.format("Execute command: %s with wildcards: %s and parameters: %s",
                command, wildcards, parameters));
        RemoteCommandInfo commandInfo = getCommandInfo(command);
        String url = wdaUrl.toString() + commandInfo.getUrl();

        for (Wildcard wildcard : Wildcard.values()) {
            String value = wildcards.get(wildcard);
            if (value != null) {
                url = url.replace(wildcard.getKey(), value);
            }
        }

        String responseContent = null;
        switch (commandInfo.getMethod()) {
        case GET:
            responseContent = HttpService.getInstance().executeGet(url);
            break;
        case POST:
            String jsonParameters = JsonConverter.toJson(parameters);
            responseContent = HttpService.getInstance().executePost(url, jsonParameters);
            break;
        case DELETE:
            responseContent = HttpService.getInstance().executeDelete(url);
            break;
        }
        RemoteResponse response = JsonConverter.fromJson(responseContent, RemoteResponse.class);

        if (response.getStatus() != RemoteDriverStatus.NO_ERROR.getStatus()) {
            RemoteDriverStatus remoteStatus = RemoteDriverStatus.getByStatusCode(response.getStatus());
            String message = RemoteDriverStatus.getMessage(remoteStatus);
            throw new WebDriverAgentException(String.format("%s. Description: %s", message, response.getValue()));
        }

        return response;
    }

    @Override
    public RemoteResponse execute(String command, Map<Wildcard, String> wildcards) {
        return execute(command, wildcards, new HashMap<>());
    }

    @Override
    public RemoteResponse execute(String command) {
        return execute(command, new EnumMap<>(Wildcard.class), new HashMap<>());
    }

    private RemoteCommandInfo getCommandInfo(String command) {
        return Optional.ofNullable(WDACommand.getCommand(command))
                .orElseThrow(() -> new WebDriverAgentException("Unable to find command: " + command));
    }
}
