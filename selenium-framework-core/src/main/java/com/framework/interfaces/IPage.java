package com.framework.interfaces;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

/**
 * Base interface for page objects using Java 17 features
 */
public interface IPage {
    
    /**
     * Record for page validation rules
     */
    record PageValidation(String description, Predicate<IPage> condition) {
        public static PageValidation of(String description, Predicate<IPage> condition) {
            return new PageValidation(description, condition);
        }
    }
    
    /**
     * Functional interface for page actions
     */
    @FunctionalInterface
    interface PageAction {
        void perform(IPage page);
    }
    
    /**
     * Gets an element by locator key
     */
    IElement getElement(String locatorKey);
    
    /**
     * Gets a list of elements by locator key
     */
    List<IElement> getElements(String locatorKey);
    
    /**
     * Waits for a specific condition on the page
     */
    IPage waitUntil(Predicate<IPage> condition, Duration timeout);
    
    /**
     * Waits for the page to be loaded
     */
    IPage waitForLoad();
    
    /**
     * Performs a custom action on the page
     */
    IPage performAction(PageAction action);
    
    /**
     * Validates the page state
     */
    default boolean validate(PageValidation... validations) {
        for (PageValidation validation : validations) {
            if (!validation.condition().test(this)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets the page URL
     */
    String getUrl();
    
    /**
     * Gets the page title
     */
    String getTitle();
    
    /**
     * Refreshes the page
     */
    IPage refresh();
    
    /**
     * Navigates back in browser history
     */
    IPage back();
    
    /**
     * Navigates forward in browser history
     */
    IPage forward();
} 