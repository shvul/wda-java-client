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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RemoteCommandInfo {

    public enum HttpMethod {GET, POST, DELETE}

    private final String url;
    private final HttpMethod method;

    public RemoteCommandInfo(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return this.url;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RemoteCommandInfo that = (RemoteCommandInfo) o;

        return new EqualsBuilder()
                .append(url, that.url)
                .append(method, that.method)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(url)
                .append(method)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "RemoteCommandInfo{" +
                "url='" + url + '\'' +
                ", method=" + method +
                '}';
    }
}
