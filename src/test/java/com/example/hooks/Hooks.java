package com.example.hooks;

import com.example.base.BaseTest;
import com.example.utilities.ExtentReportManager;
import com.example.utilities.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentReportManager.startTest(scenario.getName());
        BaseTest.startDriver();
        ExtentReportManager.info("WebDriver started successfully.");
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                ExtentReportManager.fail("Scenario failed: " + scenario.getName());
                String screenshot = ScreenshotUtils.capture(BaseTest.driver(), scenario.getName());
                ExtentReportManager.addScreenshot(screenshot);
            } else {
                ExtentReportManager.pass("Scenario passed: " + scenario.getName());
            }
        } catch (Exception error) {
            ExtentReportManager.fail(error);
        } finally {
            BaseTest.quitDriver();
            ExtentReportManager.flush();
        }
    }
}
