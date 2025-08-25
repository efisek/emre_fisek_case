package pages;

import org.openqa.selenium.WebDriver;
import page.BasePage;

public class LeverPage extends BasePage {
    public LeverPage(WebDriver driver) {
        super(driver);
    }

    public void assertLeverDomain() {
        String url = driver.getCurrentUrl();
        try {
            assert url != null;
        } catch (Exception e) {
            throw new AssertionError("Could not parse current url: " + url);
        }
        if (!url.contains("lever.co")) {
            throw new AssertionError("Expected lever.co, but got: " + url);
        }
    }

}
