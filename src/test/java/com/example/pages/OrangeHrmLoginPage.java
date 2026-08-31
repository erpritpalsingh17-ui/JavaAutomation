package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrangeHrmLoginPage {
    private static final String URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By username = By.name("username");
    private final By password = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By dashboardHeader = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By errorAlert = By.cssSelector(".oxd-alert-content-text");

    public OrangeHrmLoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(username));
    }

    public void login(String user, String pass) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(username));
        usernameField.clear();
        usernameField.sendKeys(user);

        WebElement passwordField = driver.findElement(password);
        passwordField.clear();
        passwordField.sendKeys(pass);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isDashboardVisible() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(dashboardHeader),
                ExpectedConditions.visibilityOfElementLocated(errorAlert)
        ));
        return !driver.findElements(dashboardHeader).isEmpty();
    }

    public String loginError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert)).getText().trim();
    }

    public void logout() {
        By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
        By logoutLink = By.xpath("//a[normalize-space()='Logout']");
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(username));
    }
}
