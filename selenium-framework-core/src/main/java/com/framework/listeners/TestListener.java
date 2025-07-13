package com.framework.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestListener implements ITestListener {
    
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
    public void onStart(ITestContext context) {
        log.info("Starting test suite: {}", context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        log.info("Finished test suite: {}", context.getName());
        log.info("Passed tests: {}", context.getPassedTests().size());
        log.info("Failed tests: {}", context.getFailedTests().size());
        log.info("Skipped tests: {}", context.getSkippedTests().size());
    }
} 