package element;

import config.RuntimeConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class Commons {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected Commons(WebDriver driver) {
        this.driver = driver;
        int secs = (RuntimeConfig.explicitWait > 0) ? RuntimeConfig.explicitWait : 10;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(secs));
    }

}
