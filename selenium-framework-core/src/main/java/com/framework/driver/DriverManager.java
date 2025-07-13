package com.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.function.Supplier;

/**
 * Thread-safe WebDriver manager using Java 17 features
 */
public final class DriverManager {
    
    /**
     * Record for browser configuration
     */
    public record BrowserConfig(String name, String version) {
        public static BrowserConfig of(String name, String version) {
            return new BrowserConfig(name, version);
        }
    }
    
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    
    /**
     * Enum for supported browsers with lambda setup
     */
    public enum Browser {
        CHROME(() -> {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }),
        FIREFOX(() -> {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }),
        EDGE(() -> {
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        });

        private final Supplier<WebDriver> driverSupplier;

        Browser(Supplier<WebDriver> driverSupplier) {
            this.driverSupplier = driverSupplier;
        }

        public WebDriver createDriver() {
            return driverSupplier.get();
        }
    }

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Gets the WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    /**
     * Creates a new WebDriver instance for the current thread
     */
    public static void setDriver(Browser browser) {
        if (driverThread.get() != null) {
            removeDriver();
        }
        driverThread.set(browser.createDriver());
    }

    /**
     * Removes the WebDriver instance for the current thread
     */
    public static void removeDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }

    /**
     * Pattern matching for browser type
     */
    public static Browser getBrowserType(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> Browser.CHROME;
            case "firefox" -> Browser.FIREFOX;
            case "edge" -> Browser.EDGE;
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }
} 