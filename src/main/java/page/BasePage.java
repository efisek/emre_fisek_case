package page;

import condition.Conditions;
import config.RuntimeConfig;
import element.UIElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public abstract class BasePage implements Page{
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        int secs = (RuntimeConfig.explicitWait > 0) ? RuntimeConfig.explicitWait : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
    }

    protected WebElement waitVisible(By by) { return wait.until(Conditions.visible(by)); }
    protected WebElement waitClickable(By by) { return wait.until(Conditions.clickable(by)); }
    protected boolean exists(By by) { return !driver.findElements(by).isEmpty(); }
    protected UIElement el(By by) { return new UIElement(driver, by); }
    protected List<By> mandatory() { return Collections.emptyList(); }

    // to prove page is open by mandatory locators).
    @Override
    public void assertOpen() {
        for (By by : mandatory()) {
            if (waitVisible(by) == null) {
                throw new AssertionError("Element not visible: " + by);
            }
        }
    }

}
