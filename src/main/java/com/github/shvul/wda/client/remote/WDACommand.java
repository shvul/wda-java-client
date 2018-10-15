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

import com.github.shvul.wda.client.remote.RemoteCommandInfo.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class WDACommand {

    public static final String DISMISS_ALERT;
    public static final String ACCEPT_ALERT;
    public static final String GET_ALERT_TEXT;
    public static final String DEACTIVATE_APP;
    public static final String DISMISS_KEYBOARD;
    public static final String NEW_SESSION;
    public static final String STATUS;
    public static final String GET_PAGE_SOURCE;
    public static final String GET_CURRENT_WINDOW_SIZE;
    public static final String GET_WINDOW_SCREENSHOT;
    public static final String GET_ELEMENT_SCREENSHOT;
    public static final String PRESS_REMOTE_BUTTON;
    public static final String FIND_ELEMENT;
    public static final String FIND_ELEMENTS;
    public static final String FIND_CHILD_ELEMENT;
    public static final String FIND_CHILD_ELEMENTS;
    public static final String ELEMENT_SELECT;
    public static final String ELEMENT_FOCUSE;
    public static final String SEND_KEYS;
    public static final String CLEAR_ELEMENT;
    public static final String SET_VALUE;
    public static final String GET_ELEMENT_ATTRIBUTE;
    public static final String IS_ELEMENT_ENABLED;
    public static final String IS_ELEMENT_FOCUSED;
    public static final String GET_ELEMENT_TEXT;
    public static final String IS_ELEMENT_DISPLAYED;
    public static final String GET_ELEMENT_RECT;
    public static final String GET_FOCUSED_ELEMENT;

    public static final Map<String, RemoteCommandInfo> commands;

    static {
        DISMISS_ALERT = "dismissAlert";
        ACCEPT_ALERT = "acceptAlert";
        GET_ALERT_TEXT = "getAlertText";
        DEACTIVATE_APP = "deactivateApp";
        DISMISS_KEYBOARD = "dismissKeyboard";
        NEW_SESSION = "newSession";
        STATUS = "status";
        GET_PAGE_SOURCE = "getPageSource";
        GET_CURRENT_WINDOW_SIZE = "getCurrentWindowSize";
        GET_WINDOW_SCREENSHOT = "getWindowScreenshot";
        GET_ELEMENT_SCREENSHOT = "getElementScreenshot";
        PRESS_REMOTE_BUTTON = "pressRemoteButton";
        FIND_ELEMENT = "findElement";
        FIND_ELEMENTS = "findElements";
        FIND_CHILD_ELEMENT = "findChildElement";
        FIND_CHILD_ELEMENTS = "findChildElements";
        ELEMENT_SELECT = "selectElement";
        ELEMENT_FOCUSE = "focuseElement";
        SEND_KEYS = "sendKeys";
        CLEAR_ELEMENT = "clearElement";
        SET_VALUE = "setValue";
        GET_ELEMENT_ATTRIBUTE = "getElementAttribute";
        IS_ELEMENT_ENABLED = "isElementEnabled";
        IS_ELEMENT_FOCUSED = "isElementFocused";
        GET_ELEMENT_TEXT = "getElementText";
        IS_ELEMENT_DISPLAYED = "isElementDisplayed";
        GET_ELEMENT_RECT = "getElementRect";
        GET_FOCUSED_ELEMENT = "getFocusedElement";

        commands = new HashMap<>();
        commands.put(DISMISS_ALERT, post("/session/:sessionId/alert/dismiss"));
        commands.put(ACCEPT_ALERT, post("/session/:sessionId/alert/accept"));
        commands.put(GET_ALERT_TEXT, get("/session/:sessionId/alert/text"));
        commands.put(DEACTIVATE_APP, get("/session/:sessionId/remote/deactivateApp"));
        commands.put(DISMISS_KEYBOARD, post("/session/:sessionId/remote/keyboard/dismiss"));
        commands.put(NEW_SESSION, post("/session"));
        commands.put(STATUS, get("/status"));
        commands.put(GET_PAGE_SOURCE, get("/source"));
        commands.put(GET_CURRENT_WINDOW_SIZE, get("/session/:sessionId/window/size"));
        commands.put(GET_WINDOW_SCREENSHOT, get("/screenshot"));
        commands.put(GET_ELEMENT_SCREENSHOT, get("/session/:sessionId/element/:uuid/screenshot"));
        commands.put(PRESS_REMOTE_BUTTON, post("/remote/press/:button"));
        commands.put(FIND_ELEMENT, post("/session/:sessionId/element"));
        commands.put(FIND_ELEMENTS, post("/session/:sessionId/elements"));
        commands.put(FIND_CHILD_ELEMENT, post("/session/:sessionId/element/:uuid/element"));
        commands.put(FIND_CHILD_ELEMENTS, post("/session/:sessionId/element/:uuid/elements"));
        commands.put(ELEMENT_SELECT, post("/session/:sessionId/element/:uuid/click"));
        commands.put(ELEMENT_FOCUSE, post("/session/:sessionId/element/:uuid/focuse"));
        commands.put(SEND_KEYS, post("/session/:sessionId/wda/keys"));
        commands.put(CLEAR_ELEMENT, post("/session/:sessionId/element/:uuid/clear"));
        commands.put(SET_VALUE, post("/session/:sessionId/element/:uuid/value"));
        commands.put(GET_ELEMENT_ATTRIBUTE, get("/session/:sessionId/element/:uuid/attribute/:name"));
        commands.put(IS_ELEMENT_ENABLED, get("/session/:sessionId/element/:uuid/enabled"));
        commands.put(IS_ELEMENT_FOCUSED, get("/session/:sessionId/element/:uuid/focused"));
        commands.put(GET_ELEMENT_TEXT, get("/session/:sessionId/element/:uuid/text"));
        commands.put(IS_ELEMENT_DISPLAYED, get("/session/:sessionId/element/:uuid/displayed"));
        commands.put(GET_ELEMENT_RECT, get("/session/:sessionId/element/:uuid/rect"));
        commands.put(GET_FOCUSED_ELEMENT, get("/wda/element/focused"));
    }

    public enum Wildcard {

        SESSION_ID(":sessionId"),
        ELEMENT_UI_ID(":uuid"),
        REMOTE_BUTTON(":button"),
        NAME(":name");

        private String key;

        Wildcard(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public enum Parameter {

        USING("using"),
        VALUE("value"),
        FREQUENCY("frequency");

        private String key;

        Parameter(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static RemoteCommandInfo get(String url) {
        return new RemoteCommandInfo(url, HttpMethod.GET);
    }

    public static RemoteCommandInfo post(String url) {
        return new RemoteCommandInfo(url, HttpMethod.POST);
    }

    public static RemoteCommandInfo delete(String url) {
        return new RemoteCommandInfo(url, HttpMethod.DELETE);
    }

    public static RemoteCommandInfo getCommand(String command) {
        return commands.get(command);
    }
}
