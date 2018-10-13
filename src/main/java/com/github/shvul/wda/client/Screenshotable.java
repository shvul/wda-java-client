package com.github.shvul.wda.client;

public interface Screenshotable {

    /**
     * Takes screenshot and saves it in required format
     *
     * @see com.github.shvul.wda.client.OutputImageType
     */
    <T> T getScreenshot(OutputImageType<T> outputType);
}
