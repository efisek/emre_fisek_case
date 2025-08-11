package condition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class Conditions {
    public static ExpectedCondition<WebElement> visible(By by)   { return ExpectedConditions.visibilityOfElementLocated(by); }
    public static ExpectedCondition<WebElement> clickable(By by) { return ExpectedConditions.elementToBeClickable(by); }

}
