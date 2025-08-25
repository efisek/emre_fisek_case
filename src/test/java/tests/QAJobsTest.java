package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.JobsPage;
import pages.LeverPage;

public class QAJobsTest extends BaseTest {
    @DataProvider(name = "jobFilters")
    public Object[][] jobFilters() {
        return new Object[][]{
                // location, department, needles to assert on job cards
                {"Istanbul, Turkiye",   "Quality Assurance",  new String[]{"quality assurance", "istanbul, turkiye"}}
        };
    }

    @Test(dataProvider = "jobFilters")
    public void filterQaJobsAndOpenLever(String location, String department, String[] needles) {
        HomePage home = new HomePage(getDriver())
                .open();
        home.assertOpen();

        CareersPage careers = home.goToCareers();
        careers.assertOpen();
        careers.assertBlocksVisible();
        careers.goToQATeamViaUI();

        JobsPage jobs = new JobsPage(getDriver());
        jobs.assertOpen();
        jobs.seeAllQaJobs();
        jobs.assertOpenPositionsVisible();
        jobs.filterLocation(location);
        jobs.assertJobsPresent();
        jobs.assertAllCardsContainIgnoreCase(needles);

        LeverPage lever = jobs.clickFirstViewRole();
        lever.assertLeverDomain();
    }
}

