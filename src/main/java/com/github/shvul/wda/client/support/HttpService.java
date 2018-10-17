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

import com.github.shvul.wda.client.exception.WebDriverAgentException;
import com.github.shvul.wda.client.remote.WDACommandExecutor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpService {

    private static HttpService instance;
    private HttpClient client;

    private HttpService() {
        client = HttpClientBuilder.create().build();
    }

    public static HttpService getInstance() {
        if (null == instance) {
            synchronized (WDACommandExecutor.class) {
                if (null == instance) {
                    instance = new HttpService();
                }
            }
        }
        return instance;
    }

    public String executeGet(String url) {
        HttpGet request = new HttpGet(url);
        return execute(request);
    }

    public String executePost(String url, String jsonParams) {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(jsonParams, ContentType.APPLICATION_JSON));

        return execute(request);
    }

    public String executeDelete(String url) {
        HttpDelete request = new HttpDelete(url);
        return execute(request);
    }

    private String execute(HttpRequestBase request) {
        try {
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return getContent(response);
            } else {
                throw new WebDriverAgentException("Status code is not OK during http request execution: " + statusCode);
            }
        } catch (IOException e) {
            throw new WebDriverAgentException(e);
        }
    }

    private String getContent(HttpResponse response) {
        String result = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            result = content.toString();
        } catch (IOException e) {
            LoggerManager.error(e);
        }
        return result;
    }
}
