package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.SuperUserPage;
import com.clubforce.pages.WebsiteSettingsPage;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;

public class ComortaisSteps extends WebDriverManager {
    // logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public ComortaisSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficePage = driverManager.backofficePage;
        this.comortaisPage = driverManager.comortaisPage;
    }

    @Then("Comortais is {string} for club")
    public void suEnablesClubComortais(String display_option) {
        moveToStep4Features();
        switch (display_option) {
            case "disabled":
                if (comortaisPage.isElementPresent(BackofficePage.SUComortaisFeatureToggleOFF)) {
                    logger.warn("Comortais toggle was already OFF, test continue");
                } else {
                    disableComortaisFeatureToggle();
                    logger.info("Comortais set to OFF");
                }
                break;
            case "enabled":
                if (comortaisPage.isElementPresent(BackofficePage.SUComortaisFeatureToggleON)) {
                    logger.warn("Comortais toggle was already ON, test continue");
                } else {
                    enableComortaisFeatureToggle();
                    logger.info("Comortais set to ON");
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for suEnablesClubComortais!");
        }
        comortaisPage.waitOneSecond();
    }

    @Then("Comortais set up is {string} for {string}")
    public void checkIfComortaisDisplayed(String display_option, String club) {
        backofficePage.waitFiveSeconds();
        backofficePage.findOnPage(BackofficePage.DashboardHeading);
        switch (display_option) {
            case "not visible":
                backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                backofficePage.waitThreeSeconds();
                assertFalse(backofficePage
                        .isElementDisplayed("//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Comortais')]"));
                logger.info("Comortais is not displayed for " + club + " in Backoffice");
                break;
            case "visible":
                backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                backofficePage.findOnPage("//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Comortais')]");
                logger.info("Comortais is displayed for " + club + " in Backoffice");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkIfComortaisDisplayed!");
        }
    }

    @Then("CA can see content on Fixture and Results page for {string}")
    public void caGoToFixturesAndResultsPage(String club) {
        logger.info("CA can see content on Fixture and Results page for " + club);
        backofficePage.switchToBrowserTab(1);
        backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Fixtures')]", 15);
        backofficePage.click("//span[contains(.,'Fixtures')]");
        backofficePage.waitUntilElementInvisible("//span[contains(@class,'loader circle progress')]", 30);
        backofficePage.findOnPage("//h1[contains(.,'Fixtures')]");
        backofficePage.findOnPage("//h1[contains(.,'Our latest results')]");

        switch (club) {
            case "Dublin Bus FC":
            case "St James Athletic":
            case "Naas AFC":
            case "Murroe":
            case "Benbulben FC":
            case "Cappoquin Railway F.C.":
                backofficePage.findOnPage("//strong[contains(.,'Division')]");
                backofficePage.findOnPage("//strong[contains(.,'Home')]");
                backofficePage.findOnPage("//strong[contains(.,'Score')]");
                backofficePage.findOnPage("//strong[contains(.,'Away')]");
                backofficePage.findOnPage("//div[contains(.,'" + club + "')]");
                break;
            case "East Galway United":
                backofficePage.findOnPage("//h2[contains(.,'No fixtures found')]");
                backofficePage.findOnPage("//h2[contains(.,'No latest results found')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for caGoToFixturesAndResultsPage!");
        }
    }

    @Then("clicking View All shows the expected Comortais page for {string}")
    public void userClickViewAllToGoToComortaisPage(String club) {
        logger.info("clicking View All shows the expected Comortais page for " + club);
        backofficePage.click("//button[contains(.,'open_in_new View All')]");
        backofficePage.switchToBrowserTab(2);
        backofficePage.findOnPage("//div[contains(.,'" + club + "')]");

        switch (club) {
            case "Dublin Bus FC":
                backofficePage.findOnPage("//div[contains(.,'Dublin Bus FC Home')]");
                break;
            case "St James Athletic":
                backofficePage.findOnPage("//h1[contains(.,'DDSL')]");
                backofficePage.findOnPage("//*[contains(text(),'Dublin & District Schoolboys / Girls')]");
                break;
            case "East Galway United":
                backofficePage.findOnPage("//img[@src='/images/cairdeaslogo.jpg']");
                break;
            case "Naas AFC":
                backofficePage.findOnPage("//img[@src='/images/kdulimage.png']");
                break;
            case "Murroe":
                backofficePage.findOnPage("//img[@src='/images/LCDLBanner.png']");
                break;
            case "Benbulben FC":
                backofficePage.findOnPage("//img[@src='/images/sldsllogo.jpg']");
                break;
            case "Cappoquin Railway F.C.":
                backofficePage.findOnPage("//img[@src='/images/wweclogo.jpg']");
                break;
            default:
                throw new NotFoundException(
                        "For some reason there is no case for userClickViewAllToGoToComortaisPage!");
        }
    }

    public void moveToStep4Features() {
        comortaisPage.click(SuperUserPage.Step2TileTextSU);
        comortaisPage.waitOneSecond();
        comortaisPage.click(SuperUserPage.Step3TileTextSU);
        comortaisPage.waitOneSecond();
        comortaisPage.click(SuperUserPage.Step4TileTextSU);
        comortaisPage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.ComortaisFeatureToggle, 5);
    }

    public void disableComortaisFeatureToggle() {
        comortaisPage.click(BackofficePage.ComortaisFeatureToggle);
        comortaisPage.click(WebsiteSettingsPage.UpdateTopButton);
        comortaisPage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully, 10);

        if (comortaisPage.isElementDisplayed(BackofficePage.SUComortaisFeatureToggleON)) {
            logger.warn("Toggle change didn't work, still ON! Trying again!");
            comortaisPage.click(BackofficePage.ComortaisFeatureToggle);
            comortaisPage.click(WebsiteSettingsPage.UpdateTopButton);
            assertThat("Tried again, toggle change didn't work, still ON! ",
                    comortaisPage.isElementDisplayed(BackofficePage.SUComortaisFeatureToggleOFF));
        }
    }

    public void enableComortaisFeatureToggle() {
        comortaisPage.waitOneSecond();
        comortaisPage.click(BackofficePage.ComortaisFeatureToggle);
        comortaisPage.waitOneSecond();
        comortaisPage.click(WebsiteSettingsPage.UpdateTopButton);
        comortaisPage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully, 10);

        if (comortaisPage.isElementDisplayed(BackofficePage.SUComortaisFeatureToggleOFF)) {
            logger.warn("Toggle change didn't work, still OFF! Trying again!");
            comortaisPage.click(BackofficePage.ComortaisFeatureToggle);
            comortaisPage.waitTwoSeconds();
            comortaisPage.click(WebsiteSettingsPage.UpdateTopButton);
            comortaisPage.waitTwoSeconds();
            assertThat("Tried again, toggle change didn't work, still ON!",
                    comortaisPage.isElementDisplayed(BackofficePage.SUComortaisFeatureToggleOFF));
        }
    }

    @Then("CA links {string} club to Comortais")
    public void caLinksComortaisClub(String club) {
        backofficePage.waitForElementDisplayedByXpathWithTimeout(
                "//span[@class='cf-ui-content-description'][contains(.,'Comortais is a Competition League Management.  This allows all soccer clubs in Ireland to display their clubs upcoming Fixtures and latest Results on their Clubforce website.')]",
                5);
        backofficePage.sendKeys("//input[contains(@data-placeholder,'Search clubs')]", club);
        backofficePage.twoBackSpacesTriggerDropdown("//input[contains(@data-placeholder,'Search clubs')]");
        backofficePage.waitForElementDisplayedByXpathWithTimeout(
                "//span[@class='mat-option-text'][contains(.,'" + club + "')]", 5);
        backofficePage.click("//span[@class='mat-option-text'][contains(.,'" + club + "')]");
        backofficePage.escape();
        backofficePage.click("//button[contains(.,'save')]");
        backofficePage.waitForElementDisplayedByXpathWithTimeout("//h3[@class='mb-0'][contains(.,'All set!')]", 5);
        backofficePage.waitForElementDisplayedByXpathWithTimeout(
                "//span[contains(.,'Your fixtures and results are available on')]", 5);
    }
}
