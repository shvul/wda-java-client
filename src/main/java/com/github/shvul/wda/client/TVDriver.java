package com.github.shvul.wda.client;

import java.awt.Dimension;
import java.util.List;

public interface TVDriver extends Screenshotable {

    /**
     * Find all elements within the current page using the given mechanism.
     * This method is affected by the 'implicit wait' times in force at the time of execution. When
     * implicitly waiting, this method will return as soon as there are more than 0 items in the
     * found collection, or will return an empty list if the timeout is reached.
     *
     * @param locator The locating mechanism to use
     * @return A list of all {@link TVElement}s, or an empty list if nothing matches
     * @see com.github.shvul.wda.client.TVLocator
     */
    <T extends TVElement> List<T> findElements(TVLocator locator);

    /**
     * Find the first {@link TVElement} using the given method.
     * This method is affected by the 'implicit wait' times in force at the time of execution.
     * The findElement(..) invocation will return a matching row, or try again repeatedly until
     * the configured timeout is reached.
     *
     * findElement should not be used to look for non-present elements, use {@link #findElements(TVLocator)}
     * and assert zero length response instead.
     *
     * @param locator The locating mechanism
     * @return The first matching element on the current page
     * @throws WebDriverAgentException If no matching elements are found
     * @see com.github.shvul.wda.client.TVLocator
     * @see com.github.shvul.wda.client.WebDriverAgentException
     */
    <T extends TVElement> T findElement(TVLocator locator);

    /**
     * Get the source of the last loaded application page.
     *
     * @return The source of the current application page
     */
    String getPageSource();

    /**
     * Get the size of the application window frame.
     *
     * @return The current window size.
     */
    Dimension getWindowSize();

    /**
     * Install an app on the tv device.
     *
     * @param appPath path to app to install.
     */
    void installApp(String appPath);

    /**
     * Remove an app on the tv device.
     *
     * @param bundleId bundle id of the app to remove.
     */
    void removeApp(String bundleId);

    /**
     * Launch an app on the tv device.
     *
     * @param bundleId bundle id of the app to launch.
     */
    void launchApp(String bundleId);

    /**
     * Close the app which was provided in the capabilities at session creation
     * and quits the session.
     */
    void closeApp();

    /**
     * Quits this driver, closing running app.
     */
    void quit();

    /**
     * Returns {@link RemoteControl} for tv device
     *
     * @return the remote control
     */
    RemoteControl getRemoteControl();

    /**
     * Remote control commands
     */
    interface RemoteControl {

        /**
         * Press up button on the Remote Control
         */
        void up();

        /**
         * Press down button on the Remote Control
         */
        void down();

        /**
         * Press left button on the Remote Control
         */
        void left();

        /**
         * Press right button on the Remote Control
         */
        void right();

        /**
         * Press select button on the Remote Control
         */
        void select();

        /**
         * Press menu button on the Remote Control
         */
        void menu();

        /**
         * Press play/pause button on the Remote Control
         */
        void playPause();

        /**
         * Press home button on the Remote Control
         */
        void home();
    }
}
