package com.framework.pages;

import com.framework.driver.DriverManager;
import com.framework.interfaces.IElement;
import com.framework.interfaces.IPage;
import com.framework.elements.BaseElement;
import com.framework.config.YamlConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base implementation of IPage interface using Java 17 features
 */
@Log4j2
public class BasePage implements IPage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Duration defaultTimeout = Duration.ofSeconds(10);
    private static final String LOCATORS_CONFIG = "locators";
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, defaultTimeout);
        loadLocators();
    }
    
    private void loadLocators() {
        try {
            YamlConfig.loadConfig(YamlConfig.ConfigSource.of(
                LOCATORS_CONFIG,
                "src/test/resources/locators/locators.yml"
            ));
        } catch (YamlConfig.ConfigException e) {
            log.error("Failed to load locators configuration", e);
            throw e;
        }
    }
    
    private By getLocator(String locatorKey) {
        Map<String, Object> locator = YamlConfig.getValue(LOCATORS_CONFIG, locatorKey)
            .map(value -> (Map<String, Object>) value)
            .orElseThrow(() -> new IllegalArgumentException("Locator not found: " + locatorKey));
        
        String type = (String) locator.get("type");
        String value = (String) locator.get("value");
        
        return switch (type.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "class" -> By.className(value);
            case "css" -> By.cssSelector(value);
            case "xpath" -> By.xpath(value);
            case "tag" -> By.tagName(value);
            case "link" -> By.linkText(value);
            case "partial" -> By.partialLinkText(value);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + type);
        };
    }
    
    @Override
    public IElement getElement(String locatorKey) {
        log.debug("Getting element with locator: {}", locatorKey);
        By locator = getLocator(locatorKey);
        WebElement element = wait.until(driver -> driver.findElement(locator));
        return new BaseElement(element);
    }
    
    @Override
    public List<IElement> getElements(String locatorKey) {
        log.debug("Getting elements with locator: {}", locatorKey);
        By locator = getLocator(locatorKey);
        List<WebElement> elements = wait.until(driver -> driver.findElements(locator));
        return elements.stream()
            .map(BaseElement::new)
            .collect(Collectors.toList());
    }
    
    @Override
    public IPage waitUntil(Predicate<IPage> condition, Duration timeout) {
        new WebDriverWait(driver, timeout)
            .until(driver -> condition.test(this));
        return this;
    }
    
    @Override
    public IPage waitForLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
            .executeScript("return document.readyState")
            .equals("complete"));
        return this;
    }
    
    @Override
    public IPage performAction(PageAction action) {
        action.perform(this);
        return this;
    }
    
    @Override
    public String getUrl() {
        return driver.getCurrentUrl();
    }
    
    @Override
    public String getTitle() {
        return driver.getTitle();
    }
    
    @Override
    public IPage refresh() {
        driver.navigate().refresh();
        return waitForLoad();
    }
    
    @Override
    public IPage back() {
        driver.navigate().back();
        return waitForLoad();
    }
    
    @Override
    public IPage forward() {
        driver.navigate().forward();
        return waitForLoad();
    }
} 