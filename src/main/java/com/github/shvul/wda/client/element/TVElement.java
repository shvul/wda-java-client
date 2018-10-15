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

package com.github.shvul.wda.client.element;

import com.github.shvul.wda.client.driver.CommandExecutor;
import com.github.shvul.wda.client.driver.Screenshotable;
import com.github.shvul.wda.client.exception.WebDriverAgentException;

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

public interface TVElement extends Screenshotable, CommandExecutor {
    /**
     * Select this element.
     * <p>
     * Selection mechanism is closely connected with {@link #focuse()} and is invoked before selection execution.
     *
     * @throws WebDriverAgentException If the element no longer exists as initially defined
     */
    void select();

    /**
     * Focusing this element. If the element could not be focused
     * WebDriverAgentException would be thrown.
     * <p>
     * There are conditions when current application has non logical focuse
     * layout gide. In such cases WebDriverAgent focuse engine may fail.
     * Use RemoteControl commands to perform element focusing.
     *
     * @throws WebDriverAgentException If the given element is not within a form
     */
    void focuse();

    /**
     * This method sets the new value of the attribute "value".
     *
     * @param value is the new value which should be set
     */
    void setValue(String value);

    /**
     * If this element is a text entry element, this will clear the value. Has no effect on other
     * elements.
     */
    void clear();

    /**
     * Get the value of the given attribute of the element. Will return the current value, even if
     * this has been modified after the page has been loaded.
     * <p>
     * <p>More exactly, this method will return the value of the property with the given name, if it
     * exists. If it does not, then the value of the attribute with the given name is returned. If
     * neither exists, null is returned.
     * <p>
     *
     * @param name The name of the attribute.
     * @return The attribute/property's current value or null if the value is not set.
     */
    String getAttribute(String name);

    /**
     * Is the element currently enabled or not? This will generally return true for everything but
     * disabled input elements.
     *
     * @return True if the element is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     * Determine whether or not this element is focused or not.
     * <p>
     * Note that there is only one focused element at once during tv application running.
     *
     * @return True if the element is focused, false otherwise.
     */
    boolean isFocused();

    /**
     * Get the visible text of this element
     *
     * @return The text of this element.
     */
    String getText();

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
     * Is this element displayed or not? This method avoids the problem of having to parse an
     * element's "style" attribute.
     *
     * @return Whether or not the element is displayed
     */
    boolean isDisplayed();

    /**
     * Where on the page is the top left-hand corner of the rendered element?
     *
     * @return A point, containing the location of the top left-hand corner of the element
     */
    Point getLocation();

    /**
     * What is the width and height of the rendered element?
     *
     * @return The size of the element on the page.
     */
    Dimension getSize();

    /**
     * @return The location and size of the rendered element
     */
    Rectangle getRect();
}
