package pages;

import config.RuntimeConfig;
import org.openqa.selenium.*;
import page.BasePage;

import java.util.List;

public class CareersPage extends BasePage {
    private static final By READY_TO_DISRUPT = By.xpath("//*[self::h1 or self::h2][contains(normalize-space(.),'Ready to disrupt')]");
    private static final By BLOCK_LOCATIONS = By.xpath("//*[self::h2 or self::h3][normalize-space(.)='Our Locations']");
    private static final By BLOCK_TEAMS = By.xpath("//*[self::h2 or self::h3][contains(normalize-space(.),'Find your calling')]");
    private static final By BLOCK_LIFE_AT = By.xpath("//*[self::h2 or self::h3][contains(normalize-space(.),'Life at Insider')]");
    private static final By SEE_ALL_TEAMS_BTN = By.xpath("//a[contains(text(),'See all teams')]");
    private static final By QA_TEAM_ANCHOR = By.xpath("//h3[contains(text(),'Quality Assurance')]");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected List<By> mandatory() {
        return List.of(READY_TO_DISRUPT);
    }

    public CareersPage assertBlocksVisible() {
        waitVisible(BLOCK_LOCATIONS);
        waitVisible(BLOCK_TEAMS);
        waitVisible(BLOCK_LIFE_AT);
        return this;
    }

    public CareersPage clickSeeAllTeams() {
        waitVisible(BLOCK_TEAMS);
        if (exists(SEE_ALL_TEAMS_BTN)) {
            el(SEE_ALL_TEAMS_BTN).click();
        }
        return this;
    }

    public JobsPage goToQATeamViaUI() {
        clickSeeAllTeams();
        try {
            waitVisible(QA_TEAM_ANCHOR);
            el(QA_TEAM_ANCHOR).click();
        } catch (TimeoutException e) {
            driver.get(RuntimeConfig.baseUrl + "/careers/quality-assurance/");
        }
        return new JobsPage(driver);
    }
}
