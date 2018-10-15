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
import com.github.shvul.wda.client.element.AppleTVElement;
import com.github.shvul.wda.client.element.TVElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RemoteResponse {

    private volatile Object value;
    private volatile String sessionId;
    private volatile String id;
    private volatile Integer status;
    private volatile String state;

    public RemoteResponse() {
    }

    public RemoteResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map getValueAsMap() {
        return Optional.of(this.value)
                .filter(HashMap.class::isInstance)
                .map(HashMap.class::cast).orElseThrow(() -> new RuntimeException("Unable to extract map value."));
    }

    public ArrayList getValueAsList() {
        return Optional.of(this.value)
                .filter(ArrayList.class::isInstance)
                .map(ArrayList.class::cast).orElseThrow(() -> new RuntimeException("Unable to extract list value."));
    }

    public Dimension getValueAsDimension() {
        Map value = getValueAsMap();
        return new Dimension((int) value.get("width"), (int) value.get("height"));
    }

    public Point getValueAsPoint() {
        Map value = getValueAsMap();
        return new Point((int) value.get("x"), (int) value.get("y"));
    }

    public Rectangle getValueAsRect() {
        return new Rectangle(getValueAsPoint(), getValueAsDimension());
    }

    public List<TVElement> getValueAsElementsList(CommandExecutor commandExecutor) {
        List<TVElement> elements = new ArrayList<>();
        ArrayList responseList = getValueAsList();
        for (Object elementEntry : responseList) {
            elements.add(castToElement(elementEntry, commandExecutor));
        }
        return elements;
    }

    public TVElement getValueAsElement(CommandExecutor commandExecutor) {
        return castToElement(value, commandExecutor);
    }

    private TVElement castToElement(Object entry, CommandExecutor commandExecutor) {
        String uid = Optional.of(entry)
                .filter(HashMap.class::isInstance)
                .map(HashMap.class::cast)
                .map(k -> (String) k.get("ELEMENT"))
                .orElseThrow(() -> new RuntimeException("Unable to extract element uid value."));
        return new AppleTVElement(sessionId, uid, commandExecutor);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RemoteResponse that = (RemoteResponse) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .append(sessionId, that.sessionId)
                .append(status, that.status)
                .append(state, that.state)
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .append(sessionId)
                .append(status)
                .append(state)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "RemoteResponse{" +
                "value=" + value +
                ", sessionId='" + sessionId + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", state='" + state + '\'' +
                '}';
    }
}
