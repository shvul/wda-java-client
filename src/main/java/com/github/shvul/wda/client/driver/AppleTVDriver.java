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

package com.github.shvul.wda.client.driver;

import com.github.shvul.wda.client.driver.DriverCapabilities.Key;
import com.github.shvul.wda.client.element.TVElement;
import com.github.shvul.wda.client.element.TVLocator;
import com.github.shvul.wda.client.remote.RemoteResponse;
import com.github.shvul.wda.client.remote.WDACommand;
import com.github.shvul.wda.client.remote.WDACommand.Parameter;
import com.github.shvul.wda.client.remote.WDACommand.Wildcard;
import com.github.shvul.wda.client.remote.WDACommandExecutor;
import com.github.shvul.wda.client.remote.WebDriverAgentRunner;
import com.github.shvul.wda.client.support.IOSDeploy;
import com.github.shvul.wda.client.support.LoggerManager;
import com.google.common.collect.ImmutableMap;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppleTVDriver implements TVDriver {

    private DriverCapabilities capabilities;
    private WebDriverAgentRunner wdaRunner;
    private CommandExecutor commandExecutor;
    private String sessionId;

    public AppleTVDriver(DriverCapabilities capabilities) {
        this.capabilities = capabilities;
        this.wdaRunner = new WebDriverAgentRunner(capabilities);
        this.commandExecutor = new WDACommandExecutor(wdaRunner.getWdaUrl());
        this.wdaRunner.start();
        this.createSession();
    }

    @Override
    public List<TVElement> findElements(TVLocator locator) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Parameter.USING.getKey(), locator.getSelector().getKey());
        parameters.put(Parameter.VALUE.getKey(), locator.getValue());
        RemoteResponse response = execute(WDACommand.FIND_ELEMENTS, new EnumMap<>(Wildcard.class), parameters);
        return response.getValueAsElementsList(commandExecutor);
    }

    @Override
    public TVElement findElement(TVLocator locator) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Parameter.USING.getKey(), locator.getSelector().getKey());
        parameters.put(Parameter.VALUE.getKey(), locator.getValue());
        RemoteResponse response = execute(WDACommand.FIND_ELEMENT, new EnumMap<>(Wildcard.class), parameters);
        return response.getValueAsElement(commandExecutor);
    }

    @Override
    public TVElement focused() {
        return execute(WDACommand.GET_FOCUSED_ELEMENT).getValueAsElement(commandExecutor);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        Map<String, Object> parameters = new HashMap<>();
        List<String> keys = Arrays.stream(keysToSend)
                .map(String.class::cast)
                .map(k -> k.split(""))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        parameters.put(WDACommand.Parameter.VALUE.getKey(), keys);
        execute(WDACommand.SEND_KEYS, new EnumMap<>(Wildcard.class), parameters);
    }

    @Override
    public String getPageSource() {
        return (String) execute(WDACommand.GET_PAGE_SOURCE).getValue();
    }

    @Override
    public Dimension getWindowSize() {
        return execute(WDACommand.GET_CURRENT_WINDOW_SIZE).getValueAsDimension();
    }

    @Override
    public void installApp(String... appPath) {
        String path = appPath.length > 0 ? appPath[0] : capabilities.getCapability(Key.APP_PATH);
        new IOSDeploy(capabilities.getCapability(Key.DEVICE_ID)).installApp(path);
    }

    @Override
    public void removeApp(String... bundleId) {
        String id = bundleId.length > 0 ? bundleId[0] : capabilities.getCapability(Key.BUNDLE_ID);
        new IOSDeploy(capabilities.getCapability(Key.DEVICE_ID)).removeApp(id);
    }

    @Override
    public void launch(String... bundleId) {
        String id = bundleId.length > 0 ? bundleId[0] : capabilities.getCapability(Key.BUNDLE_ID);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Parameter.BUNDLE_ID.getKey(), id);
        parameters.put(Parameter.ARGUMENTS.getKey(), buildArgs());
        execute(WDACommand.LAUNCH, new EnumMap<>(Wildcard.class), parameters);
    }

    @Override
    public void terminate(String... bundleId) {
        String id = bundleId.length > 0 ? bundleId[0] : capabilities.getCapability(Key.BUNDLE_ID);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Parameter.BUNDLE_ID.getKey(), id);
        execute(WDACommand.TERMINATE, new EnumMap<>(Wildcard.class), parameters);
    }

    @Override
    public void activate(String... bundleId) {
        String id = bundleId.length > 0 ? bundleId[0] : capabilities.getCapability(Key.BUNDLE_ID);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Parameter.BUNDLE_ID.getKey(), id);
        execute(WDACommand.ACTIVATE, new EnumMap<>(Wildcard.class), parameters);
    }

    @Override
    public void quit() {
        sessionId = null;
        wdaRunner.stop();
    }

    @Override
    public RemoteControl getRemoteControl() {
        return new AppleTVRemote();
    }

    @Override
    public Alert getAlert() {
        return new AppleTVAlert();
    }

    @Override
    public <T> T getScreenshot(OutputImageType<T> outputType) {
        return outputType.convertFromBase64Png((String) execute(WDACommand.GET_WINDOW_SCREENSHOT).getValue());
    }

    @Override
    public RemoteResponse execute(String command, Map<Wildcard, String> wildcards, Map<String, ?> parameters) {
        Optional.ofNullable(sessionId).ifPresent(id -> wildcards.put(Wildcard.SESSION_ID, sessionId));
        return commandExecutor.execute(command, wildcards, parameters);
    }

    @Override
    public RemoteResponse execute(String command, Map<Wildcard, String> wildcards) {
        return this.execute(command, wildcards, new HashMap<>());
    }

    @Override
    public RemoteResponse execute(String command) {
        return this.execute(command, new EnumMap<>(Wildcard.class), new HashMap<>());
    }

    private class AppleTVRemote implements RemoteControl {

        private static final int REMOTE_BUTTON_UP = 0;
        private static final int REMOTE_BUTTON_DOWN = 1;
        private static final int REMOTE_BUTTON_LEFT = 2;
        private static final int REMOTE_BUTTON_RIGHT = 3;
        private static final int REMOTE_BUTTON_SELECT = 4;
        private static final int REMOTE_BUTTON_MENU = 5;
        private static final int REMOTE_BUTTON_PLAYPAUSE = 6;
        private static final int REMOTE_BUTTON_HOME = 7;

        @Override
        public void up(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_UP, duration);
        }

        @Override
        public void down(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_DOWN, duration);
        }

        @Override
        public void left(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_LEFT, duration);
        }

        @Override
        public void right(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_RIGHT, duration);
        }

        @Override
        public void select(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_SELECT, duration);
        }

        @Override
        public void menu(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_MENU, duration);
        }

        @Override
        public void playPause(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_PLAYPAUSE, duration);
        }

        @Override
        public void home(int... duration) {
            executeButtonCommand(REMOTE_BUTTON_HOME, duration);
        }

        private void executeButtonCommand(int button, int... duration) {
            int pressDuration = duration.length > 0 ? duration[0] : 0;
            Map<Wildcard, String> wildcards = new EnumMap<>(Wildcard.class);
            wildcards.put(Wildcard.REMOTE_BUTTON, String.valueOf(button));
            Map<String, String> parameters = ImmutableMap.of("duration", String.valueOf(pressDuration));
            execute(WDACommand.PRESS_REMOTE_BUTTON, wildcards, parameters);
        }
    }

    private class AppleTVAlert implements Alert {

        @Override
        public void dismiss() {
            execute(WDACommand.DISMISS_ALERT);
        }

        @Override
        public void accept() {
            execute(WDACommand.ACCEPT_ALERT);
        }

        @Override public String getText() {
            return (String) execute(WDACommand.GET_ALERT_TEXT).getValue();
        }
    }

    private void createSession() {
        LoggerManager.info("Starting new WebDriverAgent session.");
        LoggerManager.debug("Capabilities: " + capabilities.toString());
        String command = WDACommand.NEW_SESSION;

        Optional.ofNullable(capabilities.getCapability(Key.APP_PATH)).ifPresent(path -> {
            new IOSDeploy(capabilities.getCapability(Key.DEVICE_ID)).installApp(path);
        });

        RemoteResponse response = execute(command, new EnumMap<>(Wildcard.class), buildDesiredCapabilities());
        this.sessionId = response.getSessionId();
    }

    private Map<String, Object> buildDesiredCapabilities() {
        Map<String, Object> desiredWrapper = new HashMap<>();
        Map<String, Object> desiredCaps = new HashMap<>();
        desiredCaps.put(Parameter.ARGUMENTS.getKey(), buildArgs());

        Optional.ofNullable(capabilities.getCapability(Key.BUNDLE_ID)).ifPresent(cap -> {
            desiredCaps.put(Key.BUNDLE_ID.getKey(), cap);
        });

        desiredWrapper.put("desiredCapabilities", desiredCaps);

        return desiredWrapper;
    }

    private Map<String, Object> buildArgs() {
        Map<String, Object> args = new HashMap<>();
        Optional.ofNullable(capabilities.getCapability(Key.LANGUAGE)).ifPresent(cap -> {
            args.put("-AppleLanguages", cap);
            args.put("-NSLanguages", cap);
        });

        Optional.ofNullable(capabilities.getCapability(Key.LOCALE)).ifPresent(cap -> {
            args.put("-AppleLocale", cap);
        });
        return args;
    }
}
