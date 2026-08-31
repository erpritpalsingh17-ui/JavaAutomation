package com.example.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.TestNG;

@CucumberOptions(
        features = "src/test/resources/features/ERail.feature",
        glue = {"com.example.stepdefinitions", "com.example.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/erail-cucumber.html"},
        monochrome = true,
        tags = "@erail"
)
public class ERailTestRunner extends AbstractTestNGCucumberTests {

    /**
     * Allows this Cucumber/TestNG suite to be launched directly from VS Code.
     */
    public static void main(String[] args) {
        TestNG testNg = new TestNG();
        testNg.setTestClasses(new Class<?>[] { ERailTestRunner.class });
        testNg.setDefaultSuiteName("eRail BDD");
        testNg.setDefaultTestName("eRail runner");
        testNg.run();

        if (testNg.hasFailure()) {
            System.exit(1);
        }
    }
}
