package com.github.shvul.wda.client;

public interface Screenshotable {

    /**
     * Takes screenshot and saves it in required format
     *
     * @see com.github.shvul.wda.client.OutputImageType
     */
    <X> X getScreenshotAs(OutputImageType<X> outputType);
}
