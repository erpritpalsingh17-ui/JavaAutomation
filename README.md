# JavaAutomation - Selenium BDD Framework

A clean, interview-friendly automation framework using **Java 17**, **Selenium WebDriver**, **Cucumber BDD**, **TestNG**, **Page Object Model**, and **Apache POI** for data-driven testing.

## 📋 Project Structure

```
src/
├── main/java/com/example/utils/
│   └── StringUtils.java
│
└── test/
    ├── java/com/example/
    │   ├── base/
    │   │   └── BaseTest.java              # Thread-safe WebDriver management
    │   │
    │   ├── pages/
    │   │   ├── OrangeHrmLoginPage.java   # Page Object for OrangeHRM
    │   │   └── ERailPage.java            # Page Object for eRail
    │   │
    │   ├── stepdefinitions/
    │   │   ├── OrangeHrmLoginSteps.java  # Cucumber steps for OrangeHRM
    │   │   └── ERailSteps.java           # Cucumber steps for eRail
    │   │
    │   ├── runner/
    │   │   ├── OrangeHrmTestRunner.java  # OrangeHRM test executor
    │   │   └── ERailTestRunner.java      # eRail test executor
    │   │
    │   ├── hooks/
    │   │   └── Hooks.java                # Setup/teardown and reporting
    │   │
    │   └── utilities/
    │       ├── ExcelUtils.java           # Excel file handling
    │       ├── OrangeHrmLoginData.java   # Login data management
    │       ├── ScreenshotUtils.java      # Screenshot capture
    │       ├── DateUtils.java            # Date utilities
    │       └── ExtentReportManager.java  # Report generation
    │
    └── resources/
        ├── features/
        │   ├── OrangeHrmLogin.feature    # OrangeHRM test scenarios
        │   └── ERail.feature             # eRail test scenarios
        │
        ├── testdata/
        │   └── LoginData.xlsx            # OrangeHRM login test data
        │
        └── cucumber.properties            # Cucumber configuration
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Chrome browser (or configured WebDriver)

### Installation
```bash
cd JavaAutomation
mvn clean install
```

### Run All Tests
```bash
mvn clean test
```

### Run Only OrangeHRM Tests
```bash
mvn clean test -Dtest=OrangeHrmTestRunner
```

### Run Only eRail Tests
```bash
mvn clean test -Dtest=ERailTestRunner
```

### Run with Headless Browser
```bash
mvn clean test -Dheadless=true
```

## 📱 OrangeHRM BDD Framework

Tests OrangeHRM login functionality with multiple users and scenarios.

### Features
- ✅ Valid login test
- ✅ Invalid username test
- ✅ Invalid password test
- 📊 Data-driven testing using Excel

### Test Data (LoginData.xlsx)
Located at: `target/testdata/orangehrm-login-data.xlsx`

| Username | Password | ExpectedResult |
|----------|----------|----------------|
| Admin | admin123 | VALID |
| Admin | invalidPassword | INVALID |
| WrongUser | wrong123 | INVALID |

The file is auto-generated on first run if missing.

### OrangeHRM Architecture
```
Feature File (OrangeHrmLogin.feature)
           ↓
Step Definitions (OrangeHrmLoginSteps.java)
           ↓
Page Object (OrangeHrmLoginPage.java)
           ↓
Base Test (BaseTest.java - WebDriver)
           ↓
Test Data (OrangeHrmLoginData.java)
           ↓
Excel File (LoginData.xlsx)
```

## 🚂 eRail BDD Framework

Tests eRail railway website with station search and date selection.

### Features
- ✅ Station autocomplete dropdown validation
- ✅ Station selection from dropdown
- ✅ Excel-based station list validation
- ✅ Dynamic date selection (30 days from today)
- 📊 Configurable expected station data

### eRail Architecture
```
Feature File (ERail.feature)
           ↓
Step Definitions (ERailSteps.java)
           ↓
Page Object (ERailPage.java)
           ↓
Base Test (BaseTest.java - WebDriver)
           ↓
Utilities (DateUtils.java, ExcelUtils.java)
```

### Run with Custom Expected Stations
```bash
mvn test -Dtest=ERailTestRunner -Derail.expectedStations='DEL|BOM|BLR'
```

By default, the first dropdown result becomes the baseline.

## 🛠️ Key Components Explained

### BaseTest.java
- Thread-safe WebDriver initialization using ThreadLocal
- Automatic Chrome driver setup via WebDriverManager
- Supports headless mode via `-Dheadless=true`
- Proper cleanup after each test

### Page Object Model (POM)
- **OrangeHrmLoginPage.java**: Contains locators and methods for login interactions
- **ERailPage.java**: Contains locators and methods for station and date selection
- Keeps UI interaction logic separate from step definitions
- Easy to maintain and update

### Hooks.java
- Auto-starts WebDriver before each scenario
- Captures screenshots on failure
- Generates Extent Reports
- Proper driver cleanup after each scenario

### ExtentReportManager.java
- Generates HTML reports in `reports/ExtentReport.html`
- Logs all steps and assertions
- Attaches screenshots for failed scenarios
- ThreadLocal-safe for parallel execution

## 📊 Reports

### Extent Reports
After running tests, open:
```
reports/ExtentReport.html
```

### Cucumber Reports
- OrangeHRM: `target/cucumber-reports/orangehrm-cucumber.html`
- eRail: `target/cucumber-reports/erail-cucumber.html`

## ✨ Key Features

1. **Simple & Clean Code** - Easy to understand and explain in interviews
2. **Page Object Model** - Locators and methods separated from step definitions
3. **Data-Driven Testing** - Excel-based test data for OrangeHRM
4. **Extent Reports** - Beautiful HTML reports with screenshots
5. **BDD with Cucumber** - Readable feature files and step definitions
6. **ThreadLocal Driver** - Safe for parallel execution
7. **Automatic Test Data** - Excel files created automatically if missing
8. **Screenshots on Failure** - Automatically captured and attached to reports

## 🔧 Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Programming Language |
| Selenium | 4.11.0 | Browser Automation |
| Cucumber | 7.15.0 | BDD Framework |
| TestNG | 7.8.0 | Test Framework |
| Apache POI | 5.3.0 | Excel Handling |
| Extent Reports | 5.1.2 | Reporting |
| WebDriverManager | 5.9.3 | Driver Management |

## 📚 Interview Points

1. **Architecture**: POM + BDD = clear separation of concerns
2. **Data-Driven**: Shows understanding of reusable test data
3. **Reporting**: ExtentReports demonstrates professional practices
4. **Utilities**: DateUtils, ExcelUtils show code reusability
5. **Hooks**: Pre/post scenario setup shows test lifecycle understanding
6. **ThreadLocal**: WebDriver management shows thread-safety knowledge

## ❌ Troubleshooting

### Browser not found
```bash
mvn test -Dwebdrivermanager.prop=webdrivermanager.properties
```

### Excel file not found
The file is auto-created in `target/testdata/`. Check permissions.

### Headless mode issues
Update Chrome to latest version or try:
```bash
mvn test -Dheadless=false
```

## 📝 Notes

- All tests are written using Cucumber BDD feature files
- Step definitions contain readable steps, not Selenium code
- Page objects contain all locators and interaction methods
- No duplicate code or unnecessary complexity
- Framework is interview-friendly and easy to explain

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
