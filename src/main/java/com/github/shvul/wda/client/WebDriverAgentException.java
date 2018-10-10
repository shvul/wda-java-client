package com.github.shvul.wda.client;

public class WebDriverAgentException extends RuntimeException {

    public WebDriverAgentException(String message) {
        super(message);
    }

    public WebDriverAgentException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebDriverAgentException(Throwable cause) {
        super(cause);
    }
}
