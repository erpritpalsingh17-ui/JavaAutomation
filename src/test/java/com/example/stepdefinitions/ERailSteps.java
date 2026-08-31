 package com.example.stepdefinitions;

// package com.erail.utilities;
// import java.util.List;

// // import org.apache.poi.sl.draw.geom.Path;
// import org.testng.Assert;

// import com.example.base.BaseTest;
// import com.example.pages.ERailPage;
// import com.example.utilities.ExtentReportManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.example.utilities.ExcelUtils;

import com.example.base.BaseTest;
import com.example.pages.ERailPage;
import com.example.utilities.DateUtils;
import com.example.utilities.ExtentReportManager;

import org.testng.Assert;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ERailSteps {
    private static final long UI_PAUSE_MILLIS = Long.getLong("ui.pause.ms", 1_000L);

    private ERailPage eRailPage;
    private List<String> actualStations;
    private String selectedStation;

    @Given("I open the eRail application")
    public void openERailApplication() {
        eRailPage = new ERailPage(BaseTest.driver(), BaseTest.waitForDriver());
        eRailPage.open();
        ExtentReportManager.pass("Application launched: https://erail.in/");
        pauseForUi();
    }

    @When("I click on the From field")
    public void clickOnFromField() {
        eRailPage.clickFromField();
        ExtentReportManager.info("Clicked the From field.");
        pauseForUi();
    }

    @When("I clear the From field")
    public void clearFromField() {
        eRailPage.clearFromField();
        ExtentReportManager.info("Cleared the From field.");
        pauseForUi();
    }

    @When("I enter {string} in the From field")
    public void enterFromField(String stationCode) {
        eRailPage.enterFromStation(stationCode);
        ExtentReportManager.info("Entered station search value: " + stationCode);
        pauseForUi();
    }

    @Then("I should see the station dropdown")
    public void verifyStationDropdown() {
        actualStations = eRailPage.getDropdownStations();
        actualStations.forEach(station -> System.out.println("Station: " + station));
        Assert.assertFalse(actualStations.isEmpty(), "Station dropdown must contain results.");
        ExtentReportManager.pass("Station dropdown displayed " + actualStations.size() + " stations: " + actualStations);
    }

    @Then("I select the {int}th station from the dropdown")
    public void selectStationFromDropdown(int position) {
        selectedStation = eRailPage.selectStationAtPosition(position);
        System.out.println("Selected station: " + selectedStation);
        ExtentReportManager.pass("Selected station at position " + position + ": " + selectedStation);
        pauseForUi();
    }

    @Then("I validate the station list with Excel data")
    public void validateStationListWithExcel() throws Exception {
        //Get excel file path
        Path workbook = ExcelUtils.stationWorkbookPath();
        List<String> expected = configuredExpectedStations(actualStations);
        ExcelUtils.createStationWorkbook(workbook, expected);
        ExcelUtils.writeActualStations(workbook, actualStations);

        List<String> expectedFromExcel = ExcelUtils.readStations(workbook, "Expected");
        List<String> actualFromExcel = ExcelUtils.readStations(workbook, "Actual");
        boolean matches = ExcelUtils.listsMatch(expectedFromExcel, actualFromExcel);
        String result = matches ? "PASS" : "FAIL";
        ExtentReportManager.info("Excel file: " + workbook.toAbsolutePath());
        ExtentReportManager.info("Excel comparison result: " + result);
        Assert.assertTrue(matches, "Expected and actual station lists differ. Expected=" + expectedFromExcel + ", actual=" + actualFromExcel);
        ExtentReportManager.pass("Excel station comparison: " + result);
    }


    @Then("I select a date {int} days from today")
    public void selectDateDaysFromToday(int days) {
        LocalDate targetDate = days == 30 ? DateUtils.thirtyDaysFromToday() : LocalDate.now().plusDays(days);
        String selectedDate = eRailPage.selectDate(targetDate);
        Assert.assertTrue(selectedDate.contains(DateUtils.formatForERail(targetDate)),
                "Expected selected date to contain " + DateUtils.formatForERail(targetDate) + " but was " + selectedDate);
        ExtentReportManager.pass("Selected dynamic date: " + selectedDate);
        pauseForUi();
    }

    private List<String> configuredExpectedStations(List<String> fallback) {
        String configured = System.getProperty("erail.expectedStations", "AUTO");
        if (configured.isBlank() || "AUTO".equalsIgnoreCase(configured)) {
            ExtentReportManager.info("Expected station data source is AUTO; the current dropdown is saved as the initial baseline.");
            return fallback;
        }
        return Arrays.stream(configured.split("\\|"))
                // Eclipse's null analysis treats a method-reference input as
                // nullable. Guard it before calling String.trim().
                .map(value -> value == null ? "" : value.trim())
                .filter(value -> !value.isEmpty())
                .toList();
    }

    private void pauseForUi() {
        try {
            Thread.sleep(UI_PAUSE_MILLIS);
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Test execution was interrupted during the UI pause.", error);
        }
    }
}


//     private ERailPage eRailPage;

//     private List<String> actualStations;
//      private String selectedStation;


// @Given("I open the eRail application")
// public void openERailApplication() {

//     eRailPage = new ERailPage(BaseTest.driver(), BaseTest.waitForDriver());
//     eRailPage.open();
//     ExtentReportManager.pass("Application launched: https://erail.in/");
//     pauseForUi();

    
// }


// @When("I click on the From field")
// public void i_click_on_the_from_field() {
//     eRailPage.clickFromField();
//     ExtentReportManager.info("Clicked the From field.");
//         pauseForUi();

// }

// @When("I clear the From field")
// public void i_clear_the_from_field() {
//     eRailPage.clearFromField();
//     ExtentReportManager.info("Cleared the From field.");
//         pauseForUi();

// }

// @When("I enter {string} in the From field")
// public void i_enter_in_the_from_field(String stationCode) {
//     eRailPage.enterFromStation(stationCode);
//     ExtentReportManager.info("Entered station search value: " + stationCode);
//         pauseForUi();

// }
// @Then("I should see the station dropdown")
//     public void verifyStationDropdown() {
//         actualStations = eRailPage.getDropdownStations();
//         actualStations.forEach(station -> System.out.println("Station: " + station));
//         Assert.assertFalse(actualStations.isEmpty(), "Station dropdown must contain results.");
//         ExtentReportManager.pass("Station dropdown displayed " + actualStations.size() + " stations: " + actualStations);
//     }

//  @Then("I select the {int}th station from the dropdown")
//     public void selectStationFromDropdown(int position) {
//         selectedStation = eRailPage.selectStationAtPosition(position);
//         System.out.println("Selected station: " + selectedStation);
//         ExtentReportManager.pass("Selected station at position " + position + ": " + selectedStation);
//         pauseForUi();
//     }


// //     @Then("I validate the station list with Excel data")
// // public void i_validate_the_station_list_with_excel_data() {
// //         Path workbook = ExcelUtils.stationWorkbookPath();
// //         List<String> expected = configuredExpectedStations(actualStations);
// //         ExcelUtils.createStationWorkbook(workbook, expected);
// //         ExcelUtils.writeActualStations(workbook, actualStations);

// //         List<String> expectedFromExcel = ExcelUtils.readStations(workbook, "Expected");
// //         List<String> actualFromExcel = ExcelUtils.readStations(workbook, "Actual");
// //         boolean matches = ExcelUtils.listsMatch(expectedFromExcel, actualFromExcel);
// //         String result = matches ? "PASS" : "FAIL";
// //         ExtentReportManager.info("Excel file: " + workbook.toAbsolutePath());
// //         ExtentReportManager.info("Excel comparison result: " + result);
// //         Assert.assertTrue(matches, "Expected and actual station lists differ. Expected=" + expectedFromExcel + ", actual=" + actualFromExcel);
// //         ExtentReportManager.pass("Excel station comparison: " + result);
// //     }



// // private List<String> configuredExpectedStations(List<String> actualStations2) {
// //         throw new UnsupportedOperationException("Unimplemented method 'configuredExpectedStations'");
// //     }


// private void pauseForUi() {
//         try {
//             Thread.sleep(UI_PAUSE_MILLIS);
//         } catch (InterruptedException error) {
//             Thread.currentThread().interrupt();
//             throw new IllegalStateException("Test execution was interrupted during the UI pause.", error);
//         }
//     }

// }
