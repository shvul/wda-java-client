package com.github.shvul.wda.client.remote;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RemoteResponse {

    private volatile Object value;
    private volatile String sessionId;
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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .append(sessionId)
                .append(status)
                .append(state)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "RemoteResponse{" +
                "value=" + value +
                ", sessionId='" + sessionId + '\'' +
                ", status=" + status +
                ", state='" + state + '\'' +
                '}';
    }
}
