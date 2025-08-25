package pages;

import config.RuntimeConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.BasePage;

import java.util.List;

public class HomePage extends BasePage {
    private static final By HEADER_NAV = By.id("navigation");
    private static final By COMPANY_MENU = By.xpath("//nav//a[normalize-space()='Company']");
    private static final By CAREERS_LINK = By.cssSelector("a[href*='/careers']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected List<By> mandatory() {
        return List.of(HEADER_NAV);
    }

    public HomePage open() {
        driver.get(RuntimeConfig.baseUrl);
        return this;
    }

    public HomePage openCompanyMenuIfNeeded() {
        if (exists(COMPANY_MENU)) {
            el(COMPANY_MENU).click();
        }
        return this;
    }

    public CareersPage goToCareers() {
        openCompanyMenuIfNeeded();
        el(CAREERS_LINK).click();
        return new CareersPage(driver);
    }

}
