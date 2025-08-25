package browser;

import config.RuntimeConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class ChromeBrowserSetup implements BrowserSetup {
    @Override
    public WebDriver setupDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions ch = new ChromeOptions();
        if (RuntimeConfig.incognito) ch.addArguments("--incognito");
        if (RuntimeConfig.headless)  ch.addArguments("--headless=new");

        return new ChromeDriver();
    }

}
