# JavaAutomation

This is a standalone Maven Java project (Java 17) for Selenium + TestNG tests. It also contains an eRail Selenium WebDriver + Cucumber BDD framework using Page Objects, Apache POI Excel validation, and Extent Reports.

Project layout:

- pom.xml
- src/main/java
- src/test/java

## eRail BDD framework

The eRail implementation is isolated in these packages and does not use the adjacent Playwright or Appium projects:

- `com.example.base` — thread-safe WebDriver setup through WebDriverManager
- `com.example.pages.ERailPage` — eRail locators and interactions only
- `com.example.stepdefinitions.ERailSteps` — Cucumber steps with assertions and report logging
- `com.example.hooks.Hooks` — scenario setup/cleanup and failure screenshots
- `com.example.utilities` — Excel, date, report, and screenshot helpers
- `src/test/resources/features/ERail.feature` — the BDD scenario

The test creates `target/testdata/stations.xlsx` on every run. It writes configurable expected stations to the `Expected` worksheet and live dropdown values to `Actual`, then performs an ordered, case-insensitive comparison. By default, `AUTO` creates a baseline from the live dropdown. For a strict expected-data check, pass station values in display order:

```bash
mvn test -Dtest=ERailTestRunner -Derail.expectedStations='Station 1|Station 2|Station 3'
```

The date is calculated using `LocalDate.now().plusDays(30)`. The page object opens eRail's date picker and clicks the rendered calendar entry matching that date's timestamp; no date is hardcoded.

Run eRail in headless mode:

```bash
cd JavaAutomation
mvn test -Dtest=ERailTestRunner
```

Run it visibly:

```bash
mvn test -Dtest=ERailTestRunner -Dheadless=false
```

Or run the supplied TestNG suite:

```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

Reports are written to `reports/ExtentReport.html`, screenshots on failure to `reports/screenshots/`, and Cucumber HTML output to `target/cucumber-reports/erail-cucumber.html`.

How to run (no local JDK/Maven required): use Docker (recommended)

1. Build and run tests with Maven inside an official Maven image (with Temurin 17):

```bash
# from the workspace root
cd JavaAutomation
# run Maven tests inside Docker (binds current project folder)
docker run --rm -v "$PWD":/workspace -w /workspace maven:3.9.4-eclipse-temurin-17 mvn test
```

2. Or, if you have JDK 17 and Maven installed locally:

```bash
cd JavaAutomation
mvn test
```

Notes:
- Selenium 4 includes Selenium Manager which should automatically download the correct browser driver (ChromeDriver) when you run the test, but GUI browsers may require an X server in headless environments. On macOS with Chrome installed, it should work locally.
- Do NOT modify files in your existing Playwright project; this `JavaAutomation` folder was created separately.

Playwright project included alongside this Java setup:

```bash
cd JavaAutomation/playwright
npm install
npx playwright test
```
