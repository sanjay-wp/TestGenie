package com.framework.interfaces;

import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface for element wrappers using Java 17 features
 */
public interface IElement {
    
    /**
     * Record for element validation rules
     */
    record ElementValidation(String description, Predicate<IElement> condition) {
        public static ElementValidation of(String description, Predicate<IElement> condition) {
            return new ElementValidation(description, condition);
        }
    }
    
    /**
     * Gets the underlying WebElement
     */
    WebElement getWebElement();
    
    /**
     * Clicks the element
     */
    IElement click();
    
    /**
     * Types text into the element
     */
    IElement type(String text);
    
    /**
     * Clears the element's text
     */
    IElement clear();
    
    /**
     * Gets the element's text
     */
    String getText();
    
    /**
     * Gets the value of an attribute
     */
    String getAttribute(String attributeName);
    
    /**
     * Checks if the element is displayed
     */
    boolean isDisplayed();
    
    /**
     * Checks if the element is enabled
     */
    boolean isEnabled();
    
    /**
     * Checks if the element is selected
     */
    boolean isSelected();
    
    /**
     * Waits for a condition to be true
     */
    IElement waitUntil(Predicate<IElement> condition, Duration timeout);
    
    /**
     * Waits for the element to be clickable
     */
    default IElement waitUntilClickable(Duration timeout) {
        return waitUntil(element -> element.isDisplayed() && element.isEnabled(), timeout);
    }
    
    /**
     * Waits for the element to be visible
     */
    default IElement waitUntilVisible(Duration timeout) {
        return waitUntil(IElement::isDisplayed, timeout);
    }
    
    /**
     * Performs a custom action on the element
     */
    IElement performAction(Consumer<IElement> action);
    
    /**
     * Gets a property of the element
     */
    <T> T getProperty(Function<IElement, T> propertyGetter);
    
    /**
     * Validates the element state
     */
    default boolean validate(ElementValidation... validations) {
        for (ElementValidation validation : validations) {
            if (!validation.condition().test(this)) {
                return false;
            }
        }
        return true;
    }
} 