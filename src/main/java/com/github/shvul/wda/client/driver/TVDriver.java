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

package com.github.shvul.wda.client.driver;

import com.github.shvul.wda.client.element.TVElement;
import com.github.shvul.wda.client.element.TVLocator;
import com.github.shvul.wda.client.exception.WebDriverAgentException;

import java.awt.Dimension;
import java.util.List;

public interface TVDriver extends Screenshotable, CommandExecutable {

    /**
     * Find all elements within the current page using the given mechanism.
     *
     * @param locator The locating mechanism to use
     * @return A list of all {@link TVElement}s, or an empty list if nothing matches
     * @see TVLocator
     */
    <T extends TVElement> List<T> findElements(TVLocator locator);

    /**
     * Find the first {@link TVElement} using the given method.
     * The findElement(..) invocation will return a matching row, or try again repeatedly until
     * the configured timeout is reached.
     *
     * @param locator The locating mechanism
     * @return The first matching element on the current page
     * @throws WebDriverAgentException If no matching elements are found
     * @see TVLocator
     * @see WebDriverAgentException
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
