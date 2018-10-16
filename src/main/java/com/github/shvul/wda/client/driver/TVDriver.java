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

public interface TVDriver extends Screenshotable, CommandExecutor {

    /**
     * Find all elements within the current page using the given mechanism.
     *
     * @param locator The locating mechanism to use
     * @return A list of all {@link TVElement}s, or an empty list if nothing matches
     * @see TVLocator
     */
    List<TVElement> findElements(TVLocator locator);

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
    TVElement findElement(TVLocator locator);

    /**
     * Returns {@link TVElement} with focuse square.
     *
     * @return focused element
     */
    TVElement focused();

    /**
     * Use this method to simulate typing into tv keyboard.
     *
     * @param keysToSend character sequence to send to the element
     */
    void sendKeys(CharSequence... keysToSend);

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
     * If param is not present, capabilities value would be taken.
     *
     * @param appPath path to app to install.
     */
    void installApp(String... appPath);

    /**
     * Remove an app on the tv device.
     * If param is not present, capabilities value would be taken.
     *
     * @param bundleId bundle id of the app to remove.
     */
    void removeApp(String... bundleId);

    /**
     * Launch app on the tv device.
     * If param is not present, capabilities value would be taken.
     *
     * @param bundleId bundle id of the app to launch.
     */
    void launch(String... bundleId);

    /**
     * Terminate app on the tv device.
     * If param is not present, capabilities value would be taken.
     *
     * @param bundleId bundle id of the app to launch.
     */
    void terminate(String... bundleId);

    /**
     * Activate app on the tv device.
     * If param is not present, capabilities value would be taken.
     *
     * @param bundleId bundle id of the app to launch.
     */
    void activate(String... bundleId);

    /**
     * Quits the session.
     */
    void quit();

    /**
     * Returns {@link RemoteControl} for tv device
     *
     * @return the remote control
     */
    RemoteControl getRemoteControl();

    /**
     * Returns {@link RemoteControl} for tv device
     *
     * @return the remote control
     */
    Alert getAlert();

    /**
     * Remote control commands
     */
    interface RemoteControl {

        /**
         * Press up button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void up(int... duration);

        /**
         * Press down button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void down(int... duration);

        /**
         * Press left button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void left(int... duration);

        /**
         * Press right button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void right(int... duration);

        /**
         * Press select button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void select(int... duration);

        /**
         * Press menu button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void menu(int... duration);

        /**
         * Press play/pause button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void playPause(int... duration);

        /**
         * Press home button on the Remote Control.
         *
         * @param duration of press in seconds. Optional parameter. Default 0.
         */
        void home(int... duration);
    }

    /**
     * Alert commands
     */
    interface Alert {

        /**
         * Dismiss currently open Alert.
         */
        void dismiss();

        /**
         * Accept currently open Alert.
         */
        void accept();

        /**
         * Extracts text from Alert element.
         *
         * @return alert text
         */
        String getText();
    }
}
