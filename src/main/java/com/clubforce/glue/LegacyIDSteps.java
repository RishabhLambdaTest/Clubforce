package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.AccountPage;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.LottoPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LegacyIDSteps extends WebDriverManager {

    WebDriverManager driverManager;

    public LegacyIDSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.superUserPage = driverManager.superUserPage;
    }

    private static final Logger logger = LogManager.getLogger();
    public static String clubAMainIDHOLDER;
    public static String clubASecondaryIDHOLDER;
    public static String clubATertiaryIDHOLDER;


    @Then("they can add 3 legacy IDs for the club")
    public void add3legacyIDs() {
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Update account')]",60);

        if (superUserPage.isElementDisplayed("//button[contains(.,'add add legacy ID(S)')]")) {
            logger.info("This club have no current IDs, adding brand new IDs");
            superUserPage.click("//button[contains(.,'add add legacy ID(S)')]");
        } else {
            logger.info("This club already have current IDs, changing the IDs");
        }

        clubAMainIDHOLDER = RandomStringUtils.randomNumeric(5);
        clubASecondaryIDHOLDER = RandomStringUtils.randomNumeric(5);
        clubATertiaryIDHOLDER = RandomStringUtils.randomNumeric(5);
        logger.info("clubAMainIDHOLDER "+clubAMainIDHOLDER);
        logger.info("clubASecondaryIDHOLDER "+clubASecondaryIDHOLDER);
        logger.info("clubATertiaryIDHOLDER "+clubATertiaryIDHOLDER);

        superUserPage.sendKeys("(//input[contains(@maxlength,'5')])[1]", clubAMainIDHOLDER);
        superUserPage.sendKeys("(//input[contains(@maxlength,'5')])[2]", clubASecondaryIDHOLDER);
        superUserPage.sendKeys("(//input[contains(@maxlength,'5')])[3]", clubATertiaryIDHOLDER);

        superUserPage.scrollPageToBottom();
        superUserPage.click("//button[contains(.,'Update')]");
        superUserPage.scrollPageToTop();
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Updated successfully.')]",5);
        superUserPage.refreshPage();

        assertThat("LegacyID 1 not as expected!", superUserPage.getElementAttribute("(//input[contains(@maxlength,'5')])[1]", "value"), containsString(clubAMainIDHOLDER));
        assertThat("LegacyID 2 not as expected!", superUserPage.getElementAttribute("(//input[contains(@maxlength,'5')])[2]", "value"), containsString(clubASecondaryIDHOLDER));
        assertThat("LegacyID 3 not as expected!", superUserPage.getElementAttribute("(//input[contains(@maxlength,'5')])[3]", "value"), containsString(clubATertiaryIDHOLDER));
    }

    @Then("they cannot use the same three legacy IDs as pennybridge")
    public void addAlreadyUsedLegacyIDs() {
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Update account')]",60);

        if (superUserPage.isElementDisplayed("//*[contains(text(),'add legacy')]")) {
            logger.info("This club have no current IDs, adding brand new IDs");
            superUserPage.click("//*[contains(text(),'add legacy')]");
        } else {
            logger.info("This club already have current IDs, changing the IDs");
        }

        // pennybridge already have 55555,77777,99999
        clubAMainIDHOLDER = "55555";
        clubASecondaryIDHOLDER = "77777";
        clubATertiaryIDHOLDER = "99999";
        logger.info("clubAMainIDHOLDER "+clubAMainIDHOLDER);
        logger.info("clubASecondaryIDHOLDER "+clubASecondaryIDHOLDER);
        logger.info("clubATertiaryIDHOLDER "+clubATertiaryIDHOLDER);

        superUserPage.sendKeys("(//input[contains(@maxlength,'5')])[1]", clubAMainIDHOLDER);
        superUserPage.sendKeys("(//input[contains(@maxlength,'5')])[2]", clubASecondaryIDHOLDER);
        superUserPage.sendKeys("(//input[contains(@maxlength,'5')])[3]", clubATertiaryIDHOLDER);

        superUserPage.scrollPageToBottom();
        superUserPage.click("//button[contains(.,'Update')]");
        superUserPage.scrollPageToTop();
        superUserPage.findOnPage("//h4[contains(.,'Error updating account.')]");
        superUserPage.findOnPage("//div[@class='ng-star-inserted'][contains(.,'Legacy ID (main) should be unique')]");
        superUserPage.findOnPage("//div[@class='ng-star-inserted'][contains(.,'Legacy ID (secondary) should be unique')]");
        superUserPage.findOnPage("//div[@class='ng-star-inserted'][contains(.,'Legacy ID (tertiary) should be unique')]");

        superUserPage.refreshPage();
        assertFalse(superUserPage.isElementDisplayed("//div[@class='ng-star-inserted'][contains(.,'Legacy ID (main) should be unique')]"));
        assertFalse(superUserPage.isElementDisplayed("//div[@class='ng-star-inserted'][contains(.,'Legacy ID (secondary) should be unique')]"));
        assertFalse(superUserPage.isElementDisplayed("//div[@class='ng-star-inserted'][contains(.,'Legacy ID (tertiary) should be unique')]"));
    }

    @And("the IDs can be removed")
    public void removeLegacyIDs() {
        logger.info("Removing the IDs");

        superUserPage.clearInputFieldUsingBackSpaceKey("(//input[contains(@maxlength,'5')])[1]");
        superUserPage.clearInputFieldUsingBackSpaceKey("(//input[contains(@maxlength,'5')])[2]");
        superUserPage.clearInputFieldUsingBackSpaceKey("(//input[contains(@maxlength,'5')])[3]");

        superUserPage.scrollPageToBottom();
        superUserPage.click("//button[contains(.,'Update')]");
        superUserPage.scrollPageToTop();
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Updated successfully.')]",5);
        superUserPage.refreshPage();

        String ID1 = superUserPage.getElementAttribute("(//input[contains(@maxlength,'5')])[1]", "value");
        assertFalse(ID1.contains(clubAMainIDHOLDER));

        String ID2 = superUserPage.getElementAttribute("(//input[contains(@maxlength,'5')])[2]", "value");
        assertFalse(ID2.contains(clubASecondaryIDHOLDER));

        String ID3 = superUserPage.getElementAttribute("(//input[contains(@maxlength,'5')])[3]", "value");
        assertFalse(ID3.contains(clubATertiaryIDHOLDER));
    }
}
