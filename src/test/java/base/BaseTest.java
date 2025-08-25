package base;

import browser.BrowserFactory;
import config.ConfigLoader;
import config.RuntimeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import testUtility.reporting.ExtentTestManager;
import testUtility.reporting.TestResult;

import java.lang.reflect.Method;
import java.time.Duration;

public abstract class BaseTest {
    private WebDriver driver;
    public WebDriver getDriver() { return driver; }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        //to read the config file
        ConfigLoader.loadRuntime();

        ExtentTestManager.startTest(method.getName());

        driver = BrowserFactory.create(RuntimeConfig.browser);

        if (RuntimeConfig.implicitWait > 0) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(RuntimeConfig.implicitWait));
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(RuntimeConfig.pageLoadTimeout));
        driver.manage().window().maximize();
        driver.get(RuntimeConfig.baseUrl);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result){
        try {
            TestResult.process(result, driver);
        } finally {
            ExtentTestManager.endTest();
            if (driver != null) { driver.quit(); }
            driver = null;
        }
    }

}
