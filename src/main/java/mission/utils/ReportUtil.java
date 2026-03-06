package mission.utils;

import com.aventstack.extentreports.Status;

public class ReportUtil {

    public static void logStep(String message) {

        TestListener.test.get().log(Status.INFO, message);

    }

    public static void logPass(String message) {

        TestListener.test.get().log(Status.PASS, message);

    }

    public static void logFail(String message) {

        TestListener.test.get().log(Status.FAIL, message);

    }
}