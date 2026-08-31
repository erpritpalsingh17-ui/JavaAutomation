package com.example.tests;

import com.example.pages.GoogleHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class GoogleTest {
    private WebDriver driver;
    private GoogleHomePage google;

    @BeforeClass
    public void setUp() {
        // Selenium Manager (bundled with Selenium) should locate the driver automatically
        driver = new ChromeDriver();
        google = new GoogleHomePage(driver);
    }

    @Test
    public void openGoogle() {
        google.open();
        String title = driver.getTitle();
        assertTrue(title.contains("Google"), "Title should contain 'Google'");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
