package com.framework.listeners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CucumberListener implements ConcurrentEventListener, ITestListener {
    
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    }
    
    private void handleTestCaseStarted(TestCaseStarted event) {
        log.info("Starting scenario: {}", event.getTestCase().getName());
    }
    
    private void handleTestCaseFinished(TestCaseFinished event) {
        log.info("Finished scenario: {}. Status: {}", 
                event.getTestCase().getName(), 
                event.getResult().getStatus());
    }
    
    private void handleTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            log.info("Starting step: {}", step.getStep().getText());
        }
    }
    
    private void handleTestStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            log.info("Finished step: {}. Status: {}", 
                    step.getStep().getText(), 
                    event.getResult().getStatus());
        }
    }
    
    // ITestListener implementation
    @Override
    public void onTestStart(ITestResult result) {
        log.info("Starting test: {}", result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: {}", result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Test failed: {}", result.getName());
        if (result.getThrowable() != null) {
            log.error("Failure reason: ", result.getThrowable());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test skipped: {}", result.getName());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test failed within success percentage: {}", result.getName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        log.info("Starting test suite: {}", context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        log.info("Finished test suite: {}", context.getName());
    }
} 