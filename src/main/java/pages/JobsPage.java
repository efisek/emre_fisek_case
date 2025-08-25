package pages;

import org.openqa.selenium.*;
import page.BasePage;

import java.util.List;
import java.util.NoSuchElementException;

public class JobsPage extends BasePage {
    private static final By HDR_ALL_OPEN_POSITIONS = By.xpath("//*[self::h3][contains(normalize-space(.),'All open positions')]");
    private static final By HDR_OPEN_POSITIONS = By.xpath("//h3[contains(normalize-space(.),'Open Positions')]");
    private static final By FILTERS_ROOT = By.xpath("//*[contains(normalize-space(.),'Filter by Location') or contains(normalize-space(.),'Filter by Department')]");
    private static final By FILTER_LOCATION_BTN = By.id("select2-filter-by-location-container");
    private static final By FILTER_DEPARTMENT_BTN = By.id("select2-filter-by-department-container");
    private static final By ANY_OPTION_CONTAINER = By.xpath("//*[contains(@class,'menu') or contains(@class,'list') or @role='listbox' or contains(@class,'dropdown')]");
    private static final By VIEW_ROLE_LINKS = By.xpath("//a[contains(normalize-space(.),'View Role')]");
    private static final By CARD_FROM_VIEW_ROLE = By.xpath("//a[contains(normalize-space(.),'View Role') or contains(normalize-space(.),'Apply')]/ancestor::*[self::li or self::article or self::div][1]");
    private static final By QA_TITLE = By.xpath("//h1[contains(text(),'Quality Assurance')]");
    private static final By QA_JOBS_BTN = By.xpath("//a[contains(text(),'See all QA jobs')]");
    private static final By RESULT_COUNTER = By.id("resultCounter");

    public static By locatorHelper(String text){
        return By.xpath("//li[text()='"+text+"']");
    }

    public JobsPage(WebDriver driver) { super(driver); }

    @Override
    protected List<By> mandatory() {
        return List.of(QA_TITLE);
    }
    public void seeAllQaJobs(){
        WebElement qaJobsButton = waitClickable(QA_JOBS_BTN);
        qaJobsButton.click();
    }
    public JobsPage assertOpenPositionsVisible() {
        try {
            waitVisible(HDR_ALL_OPEN_POSITIONS);
        } catch (TimeoutException ignored) {
            waitVisible(HDR_OPEN_POSITIONS);
        }
        waitVisible(FILTERS_ROOT);
        return this;
    }

    public JobsPage filterLocation(String exactLocation) {
        try {
            waitVisible(ANY_OPTION_CONTAINER);
            String textDepartment = getElementText(waitVisible(FILTER_DEPARTMENT_BTN));
            String textNumberOfPositions = getElementText(waitVisible(RESULT_COUNTER));

            if (!textNumberOfPositions.contains("NaN") && !textDepartment.equals("Quality Assurance")) {
                String textLocation = getElementText(waitVisible(FILTER_LOCATION_BTN));
                if(textLocation.toLowerCase().contains("all")){
                    el(FILTER_LOCATION_BTN).click();
                    el(locatorHelper(exactLocation)).click();
                }
            }

        } catch (Exception ignored) {

        }
        return this;
    }

    public JobsPage assertJobsPresent() {
        List<WebElement> links = driver.findElements(VIEW_ROLE_LINKS);
        if (links.isEmpty()) {
            throw new AssertionError("No position available.");
        }
        return this;
    }

    public JobsPage assertAllCardsContainIgnoreCase(String... needles) {
        List<WebElement> cards = driver.findElements(CARD_FROM_VIEW_ROLE);
        if (cards.isEmpty()) {
            cards = driver.findElements(VIEW_ROLE_LINKS);
        }
        if (cards.isEmpty()) {
            throw new AssertionError("Nothing to validate.");
        }
        for (WebElement card : cards) {
            String text = getElementText(card).toLowerCase();
            for (String needle : needles) {
                if (!text.contains(needle.toLowerCase())) {
                    throw new AssertionError("Does not contain '" + needle + "'. Text:\n" + text);
                }
            }
        }
        return this;
    }

    public LeverPage clickFirstViewRole() {
        WebElement link = driver.findElements(VIEW_ROLE_LINKS)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No 'View Role' found"));

        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", link);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }

        // to change the tab
        driver.getWindowHandles().forEach(tab-> driver.switchTo().window(tab));

        return new LeverPage(driver);
    }

    private static String getElementText(WebElement element) {
        try {
            return element.getText();
        } catch (StaleElementReferenceException e) {
            return "";
        }
    }

}
