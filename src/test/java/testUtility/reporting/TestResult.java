package testUtility.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.Screenshot;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestResult {
    private TestResult() {}

    public static void process(ITestResult result, WebDriver driver) {
        var test = ExtentTestManager.getTest();
        if (test == null) return;

        String name = result.getMethod().getMethodName();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS -> {
                test.log(Status.PASS, MarkupHelper.createLabel("Test Passed: " + name, ExtentColor.GREEN));
            }
            case ITestResult.FAILURE -> {
                test.log(Status.FAIL, MarkupHelper.createLabel("Test Failed: " + name, ExtentColor.RED));
                if (result.getThrowable() != null) test.fail(result.getThrowable());

                String shot = Screenshot.takeScreenshot(driver, name + "_FAIL");
                if (shot != null) {
                    try {
                        Path shotPath   = Paths.get(shot).toAbsolutePath();
                        Path reportBase = ExtentManager.getReportDir().toAbsolutePath();

                        String rel = reportBase.relativize(shotPath).toString().replace('\\', '/');

                        test.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(rel).build());
                    } catch (Exception ignored) {
                        try { test.addScreenCaptureFromPath(shot); } catch (Exception ignore) {}
                    }
                }
            }
            case ITestResult.SKIP -> {
                test.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped: " + name, ExtentColor.ORANGE));
                if (result.getThrowable() != null) test.skip(result.getThrowable());
            }
            default -> {}
        }
    }

}
