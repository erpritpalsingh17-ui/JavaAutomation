package com.example.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/OrangeHrmLogin.feature",
        glue = {"com.example.stepdefinitions", "com.example.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/orangehrm-cucumber.html"},
        monochrome = true,
        tags = "@orangehrm"
)
public class OrangeHrmTestRunner extends AbstractTestNGCucumberTests {
}
