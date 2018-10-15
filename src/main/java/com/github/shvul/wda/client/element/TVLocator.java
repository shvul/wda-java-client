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

package com.github.shvul.wda.client.element;

public class TVLocator {

    private Selector selector;
    private String value;

    private TVLocator(Selector selector, String value) {
        this.selector = selector;
        this.value = value;
    }

    public static TVLocator id(String value) {
        return new TVLocator(Selector.ID, value);
    }

    public static TVLocator name(String value) {
        return new TVLocator(Selector.NAME, value);
    }

    public static TVLocator accessibilityId(String value) {
        return new TVLocator(Selector.ACCESSIBILITY_ID, value);
    }

    public static TVLocator predicate(String value) {
        return new TVLocator(Selector.NS_PREDICATE, value);
    }

    public static TVLocator classChain(String value) {
        return new TVLocator(Selector.CLASS_CHAIN, value);
    }

    public static TVLocator className(String value) {
        return new TVLocator(Selector.CLASS_NAME, value);
    }

    public static TVLocator linkText(String value) {
        return new TVLocator(Selector.LINK_TEXT, value);
    }

    public static TVLocator xpath(String value) {
        return new TVLocator(Selector.XPATH, value);
    }

    public Selector getSelector() {
        return this.selector;
    }

    public String getValue() {
        return this.value;
    }

    public enum Selector {
        ID("id"),
        NAME("name"),
        ACCESSIBILITY_ID("accessibility id"),
        NS_PREDICATE("predicate string"),
        CLASS_CHAIN("class chain"),
        CLASS_NAME("class name"),
        LINK_TEXT("link text"),
        XPATH("xpath");

        private String key;

        Selector(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

    }
}
