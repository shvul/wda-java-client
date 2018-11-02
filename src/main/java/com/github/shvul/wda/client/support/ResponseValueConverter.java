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

import com.github.shvul.wda.client.driver.CommandExecutor;
import com.github.shvul.wda.client.element.AppleTVElement;
import com.github.shvul.wda.client.element.TVElement;
import com.github.shvul.wda.client.remote.RemoteResponse;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ResponseValueConverter {
    private RemoteResponse response;

    public ResponseValueConverter(RemoteResponse response) {
        this.response = response;
    }

    public Map toMap() {
        return Optional.of(response.getValue())
                .filter(HashMap.class::isInstance)
                .map(HashMap.class::cast).orElseThrow(() -> new RuntimeException("Unable to extract map value."));
    }

    public ArrayList toList() {
        return Optional.of(response.getValue())
                .filter(ArrayList.class::isInstance)
                .map(ArrayList.class::cast).orElseThrow(() -> new RuntimeException("Unable to extract list value."));
    }

    public Dimension toDimension() {
        Map map = toMap();
        return new Dimension((int) map.get("width"), (int) map.get("height"));
    }

    public Point toPoint() {
        Map map = toMap();
        return new Point((int) map.get("x"), (int) map.get("y"));
    }

    public Rectangle toRect() {
        return new Rectangle(toPoint(), toDimension());
    }

    public List<TVElement> toElementsList(CommandExecutor commandExecutor) {
        List<TVElement> elements = new ArrayList<>();
        ArrayList responseList = toList();
        for (Object elementEntry : responseList) {
            elements.add(castToElement(elementEntry, commandExecutor));
        }
        return elements;
    }

    public TVElement toElement(CommandExecutor commandExecutor) {
        return castToElement(response.getValue(), commandExecutor);
    }

    private TVElement castToElement(Object entry, CommandExecutor commandExecutor) {
        String uid = Optional.of(entry)
                .filter(HashMap.class::isInstance)
                .map(HashMap.class::cast)
                .map(k -> (String) k.get("ELEMENT"))
                .orElseThrow(() -> new RuntimeException("Unable to extract element uid value."));
        return new AppleTVElement(response.getSessionId(), uid, commandExecutor);
    }
}
