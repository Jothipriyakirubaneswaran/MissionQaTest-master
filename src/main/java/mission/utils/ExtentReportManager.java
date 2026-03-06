package mission.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    // Main report object
    private static ExtentReports extent;

    // Each test entry
    private static ExtentTest test;

    // -----------------------------------------
    // Create / Get Report Instance
    // -----------------------------------------

    public static ExtentReports getReportInstance() {

        if (extent == null) {

            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String reportPath = System.getProperty("user.dir")
                    + "/reports/TestExecutionReport_" + timeStamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setReportName("Mission Insurance Automation Report");
            spark.config().setDocumentTitle("Automation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Tester", "Jothipriya");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Framework", "Selenium + TestNG + RestAssured");
        }

        return extent;
    }

    // -----------------------------------------
    // Create Test Entry in Report
    // -----------------------------------------

    public static void createTest(String testName) {

        test = getReportInstance().createTest(testName);
    }

    // -----------------------------------------
    // Log Step (Generic Step)
    // -----------------------------------------

    public static void logStep(String message) {

        test.log(Status.INFO, message);
    }

    // -----------------------------------------
    // Log API Request
    // -----------------------------------------

    public static void logRequest(String requestDetails) {

        test.log(Status.INFO, "API Request: " + requestDetails);
    }

    // -----------------------------------------
    // Log API Response
    // -----------------------------------------

    public static void logResponse(String responseDetails) {

        test.log(Status.INFO, "API Response: " + responseDetails);
    }

    // -----------------------------------------
    // PASS Log
    // -----------------------------------------

    public static void pass(String message) {

        test.log(Status.PASS, message);
    }

    // -----------------------------------------
    // FAIL Log
    // -----------------------------------------

    public static void fail(String message) {

        test.log(Status.FAIL, message);
    }

    // -----------------------------------------
    // Flush Report (Write results to file)
    // -----------------------------------------

    public static void flushReport() {

        if (extent != null) {

            extent.flush();
        }
    }
}