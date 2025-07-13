package com.framework.elements;

import com.framework.driver.DriverManager;
import com.framework.interfaces.IElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Base implementation of IElement interface using Java 17 features
 */
public class BaseElement implements IElement {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebElement element;
    protected final Duration defaultTimeout = Duration.ofSeconds(10);
    
    public BaseElement(WebElement element) {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, defaultTimeout);
        this.element = element;
    }
    
    @Override
    public WebElement getWebElement() {
        return element;
    }
    
    @Override
    public IElement click() {
        element.click();
        return this;
    }
    
    @Override
    public IElement type(String text) {
        element.clear();
        element.sendKeys(text);
        return this;
    }
    
    @Override
    public IElement clear() {
        element.clear();
        return this;
    }
    
    @Override
    public String getText() {
        return element.getText();
    }
    
    @Override
    public String getAttribute(String attributeName) {
        return element.getAttribute(attributeName);
    }
    
    @Override
    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean isEnabled() {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean isSelected() {
        try {
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public IElement waitUntil(Predicate<IElement> condition, Duration timeout) {
        new WebDriverWait(driver, timeout)
            .until(driver -> condition.test(this));
        return this;
    }
    
    @Override
    public IElement performAction(Consumer<IElement> action) {
        action.accept(this);
        return this;
    }
    
    @Override
    public <T> T getProperty(Function<IElement, T> propertyGetter) {
        return propertyGetter.apply(this);
    }
} 