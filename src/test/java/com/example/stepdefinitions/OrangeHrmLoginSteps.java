package com.example.stepdefinitions;

import com.example.base.BaseTest;
import com.example.pages.OrangeHrmLoginPage;
import com.example.utilities.ExtentReportManager;
import com.example.utilities.OrangeHrmLoginData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OrangeHrmLoginSteps {
    private OrangeHrmLoginPage loginPage;
    private final List<String> validationFailures = new ArrayList<>();

    @Given("I open the OrangeHRM login page")
    public void openOrangeHrmLoginPage() {
        loginPage = new OrangeHrmLoginPage(BaseTest.driver(), BaseTest.waitForDriver());
        loginPage.open();
        ExtentReportManager.pass("Opened the OrangeHRM login page.");
    }

    @When("I validate each login from the OrangeHRM Excel sheet")
    public void validateLoginsFromExcel() throws Exception {
        Path workbook = OrangeHrmLoginData.workbookPath();
        List<OrangeHrmLoginData.LoginData> loginData = OrangeHrmLoginData.readLoginData();
        ExtentReportManager.info("Excel data source: " + workbook.toAbsolutePath());

        for (OrangeHrmLoginData.LoginData row : loginData) {
            loginPage.login(row.username(), row.password());
            boolean loginSucceeded = loginPage.isDashboardVisible();
            boolean expectedSuccess = "VALID".equalsIgnoreCase(row.expectedResult());

            if (loginSucceeded == expectedSuccess) {
                ExtentReportManager.pass("Login row for '" + row.username() + "' returned expected result: " + row.expectedResult());
            } else {
                String detail = loginSucceeded
                        ? "Dashboard was displayed."
                        : "Login error: " + loginPage.loginError();
                validationFailures.add("User '" + row.username() + "' expected " + row.expectedResult() + ". " + detail);
                ExtentReportManager.fail(validationFailures.get(validationFailures.size() - 1));
            }

            if (loginSucceeded) {
                loginPage.logout();
            } else {
                loginPage.open();
            }
        }
    }

    @Then("every OrangeHRM login result should match the expected outcome")
    public void verifyAllLoginResults() {
        Assert.assertTrue(validationFailures.isEmpty(), String.join(System.lineSeparator(), validationFailures));
    }
}
