package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ERailPage {
    private static final String URL = "https://erail.in/";
    private static final Pattern DATE_TIMESTAMP = Pattern.compile("DoDateSelect\\((\\d+)");

    private final WebDriver driver;
    private final WebDriverWait wait;
    // eRail exposes this stable field ID in its public markup.
    private final By fromField = By.id("txtStationFrom");
    // eRail's autocomplete plug-in renders direct option divs inside .autocomplete-w1 > .autocomplete.
    private final By stationSuggestions = By.cssSelector(".autocomplete-w1 .autocomplete > div");
    // eRail dynamically inserts the date button into this stable container.
    private final By dateButton = By.cssSelector("#tdDateFromTo input[type='button']");
    private final By calendar = By.id("divCalender");
    private final By calendarDateOptions = By.cssSelector("#divCalender [onclick*='DoDateSelect(']");

    public ERailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromField));
    }

    public void clickFromField() {
        wait.until(ExpectedConditions.elementToBeClickable(fromField)).click();
    }

    public void clearFromField() {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(fromField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = ''; arguments[0].dispatchEvent(new Event('input', {bubbles:true}));", field);
    }

    public void enterFromStation(String stationCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromField)).sendKeys(stationCode);
    }

    public List<String> getDropdownStations() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(stationSuggestions, 0));
        return driver.findElements(stationSuggestions).stream()
                .map(element -> element.getText().trim())
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());
    }

    public String selectStationAtPosition(int oneBasedPosition) {
        List<WebElement> suggestions = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(stationSuggestions, 0));
        if (suggestions.size() < oneBasedPosition) {
            throw new IllegalStateException("Expected at least " + oneBasedPosition + " stations but found " + suggestions.size());
        }
        String station = suggestions.get(oneBasedPosition - 1).getText().trim();
        suggestions.get(oneBasedPosition - 1).click();
        wait.until(webDriver -> !webDriver.findElement(fromField).getAttribute("value").isBlank());
        return station;
    }

    public String selectDate(LocalDate targetDate) {
        wait.until(ExpectedConditions.elementToBeClickable(dateButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendar));
        List<WebElement> dates = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(calendarDateOptions, 0));
        for (WebElement date : dates) {
            Matcher matcher = DATE_TIMESTAMP.matcher(date.getAttribute("onclick"));
            if (matcher.find()) {
                LocalDate candidate = Instant.ofEpochMilli(Long.parseLong(matcher.group(1)))
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                if (targetDate.equals(candidate)) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", date);
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(dateButton)).getAttribute("value");
                }
            }
        }
        throw new IllegalStateException("Date " + targetDate + " was not available in the eRail calendar.");
    }
}
