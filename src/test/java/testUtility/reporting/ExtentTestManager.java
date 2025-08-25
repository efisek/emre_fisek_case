package testUtility.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> TL = new ThreadLocal<>();
    private ExtentTestManager() {

    }

    public static ExtentTest startTest(String name) {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest(name);
        TL.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return TL.get();
    }
    public static void endTest() {
        TL.remove();
    }
}
