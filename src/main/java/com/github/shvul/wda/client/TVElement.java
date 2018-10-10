package com.github.shvul.wda.client;

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

public interface TVElement extends Screenshotable {
    /**
     * Select this element. If this causes a new page to load, you
     * should discard all references to this element and any further
     * operations performed on this element will throw a
     * WebDriverAgentException.
     * <p>
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
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend character sequence to send to the element
     */
    void sendKeys(CharSequence... keysToSend);

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
     * Determine whether or not this element is selected or not. This operation only applies to input
     * elements such as checkboxes, options in a select and radio buttons.
     *
     * @return True if the element is currently selected or checked, false otherwise.
     */
    boolean isSelected();

    /**
     * Is the element currently enabled or not? This will generally return true for everything but
     * disabled input elements.
     *
     * @return True if the element is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     * Determine whether or not this element is focused or not.
     *
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
