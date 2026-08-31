package com.example.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Path;

public final class ExtentReportManager {
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized void initialize() {
        if (extentReports == null) {
            Path reportFile = Path.of(System.getProperty("extent.report.file", "reports/ExtentReport.html")).toAbsolutePath();
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportFile.toString());
            reporter.config().setDocumentTitle("Selenium Automation Test Report");
            reporter.config().setReportName("Selenium + Cucumber + TestNG Execution");
            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Framework", "Selenium + Cucumber + TestNG");
        }
    }

    public static void startTest(String name) {
        initialize();
        CURRENT_TEST.set(extentReports.createTest(name));
    }

    public static void info(String message) {
        CURRENT_TEST.get().info(message);
    }

    public static void pass(String message) {
        CURRENT_TEST.get().pass(message);
    }

    public static void fail(String message) {
        CURRENT_TEST.get().fail(message);
    }

    public static void fail(Throwable error) {
        CURRENT_TEST.get().fail(error);
    }

    public static void addScreenshot(String path) {
        try {
            CURRENT_TEST.get().addScreenCaptureFromPath(path);
        } catch (Exception ignored) {
            CURRENT_TEST.get().warning("Screenshot could not be attached: " + path);
        }
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
        CURRENT_TEST.remove();
    }
}
