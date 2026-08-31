package com.example.steps;

import com.example.pages.GoogleHomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.testng.Assert.assertTrue;

public class GoogleSearchSteps {
    private WebDriver driver;
    private GoogleHomePage googleHomePage;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        googleHomePage = new GoogleHomePage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("user is on the Google homepage")
    public void userIsOnGoogleHomepage() {
        googleHomePage.open();
    }

    @When("user searches for {string}")
    public void userSearchesFor(String keyword) {
        googleHomePage.searchFor(keyword);
    }

    @Then("the search query should be submitted for {string}")
    public void theSearchQueryShouldBeSubmittedFor(String expectedText) {
        String currentUrl = driver.getCurrentUrl().toLowerCase();
        assertTrue(currentUrl.contains("q=" + expectedText.toLowerCase()),
                "Expected Google URL to include search query '" + expectedText + "' but it was: '" + driver.getCurrentUrl() + "'");
    }
}
