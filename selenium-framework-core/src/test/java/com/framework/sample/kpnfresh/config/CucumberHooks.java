package com.framework.sample.kpnfresh.config;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.framework.driver.DriverManager;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CucumberHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("Starting scenario: {}", scenario.getName());
        // Initialize WebDriver
        DriverManager.setDriver(DriverManager.Browser.EDGE);
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("Finished scenario: {}. Status: {}", scenario.getName(), scenario.getStatus());
        // Clean up WebDriver
        DriverManager.removeDriver();
    }
} 