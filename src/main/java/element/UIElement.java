package element;

import condition.Conditions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class UIElement extends Commons {
    private final By by;

    public UIElement(WebDriver driver, By by) {
        super(driver);
        this.by = by;
    }

    public WebElement visible()   { return wait.until(Conditions.visible(by)); }
    public WebElement clickable() { return wait.until(Conditions.clickable(by)); }

    public void click() {
        try {
            clickable().click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            WebElement el = visible();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center',inline:'center'})", el);
            try {
                new Actions(driver).moveToElement(el).pause(Duration.ofMillis(100)).click().perform();
            } catch (Exception ignored) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click " + by + ". Screenshot: ", e);
        }
    }

}
