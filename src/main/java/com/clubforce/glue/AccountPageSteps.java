package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.AccountPage;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.ClubPage;
import com.clubforce.pages.ContactPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AccountPageSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public AccountPageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.accountPage = driverManager.accountPage;
    }

    private String contactEmailHOLDER;
    private String contactPhoneHOLDER;
    private String contactWebsiteHOLDER;
    private String contactPostcodeHOLDER;
    private String contactCountyHOLDER;
    private String contactCityHOLDER;
    private String contactAddressHOLDER;

    @Then("they can change contact details")
    public void accountPageChangeUserDetails() {
        changeAndSaveContactDetails();
    }

    @Then("updated contact details remain")
    public void accountPageUserDetailsRemain() {
        verifyUpdatedContactDetailsRemain();
    }

    @When("social media links are {string} on Account page")
    public void accountPageSocialMediaLinks(String selection) {
        accountPage.click(AccountPage.AccountUpdateHeading);
        switch (selection) {
            case "removed":
                deleteSocialMediaLinks();
                break;
            case "added":
                addSocialMediaLinks();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for accountPageSocialMediaLinks!");
        }
        accountPage.click(AccountPage.AccountPageUpdateBottomButton);
        accountPage.click(AccountPage.AccountUpdatedNotification);
    }

    @Then("social media links are {string} on the club front page")
    public void accountPageSocialMediaLinksWebsiteCheck(String selection) {
        logger.info("Going to club");
        accountPage.click(BackofficePage.BO_MenuDots);
        accountPage.click(BackofficePage.BO_MenuDotsGoToMyClub);
        accountPage.switchToBrowserTab(1);
        accountPage.waitForElementDisplayedByXpathWithTimeout(ContactPage.ContactLinkForClubPage, 10);
        accountPage.refreshPage();
        accountPage.waitForElementDisplayedByXpathWithTimeout(ContactPage.ContactLinkForClubPage, 10);

        switch (selection) {
            case "removed":
                if (driverManager.agent.contains("appium")) {
                    verifyLinksAreRemovedFromHomepageFooter();
                    accountPage.click(ClubPage.MenuIcon);
                    verifyLinksAreRemovedFromHomepageHeader();
                } else {
                    verifyLinksAreRemovedFromHomepageHeader();
                    verifyLinksAreRemovedFromHomepageFooter();
                }
                break;
            case "added":
                if (driverManager.agent.contains("appium")) {
                    verifyLinksAreAddedToHomePageFooter();
                    accountPage.click(ClubPage.MenuIcon);
                    verifyLinksAreAddedToHomepageHeader();
                } else {
                    verifyLinksAreAddedToHomepageHeader();
                    verifyLinksAreAddedToHomePageFooter();
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for accountPageSocialMediaLinksWebsiteCheck!");
        }
    }

    public void changeAndSaveContactDetails() {
        Lorem lorem = LoremIpsum.getInstance();

        logger.info("Updating " + clubAdminUsername + " contact details");
        contactEmailHOLDER = lorem.getFirstNameMale().toLowerCase() + "@"+lorem.getWords(1)+".qa";
        accountPage.waitOneSecond();
        accountPage.sendKeys(AccountPage.AccountPageMailField, contactEmailHOLDER);

        logger.info("Mail is: "+ contactEmailHOLDER);
        contactPhoneHOLDER = "0035387"+RandomStringUtils.randomNumeric(5);
        accountPage.sendKeys(AccountPage.AccountPagePhoneField1, contactPhoneHOLDER);

        contactWebsiteHOLDER = lorem.getFirstNameMale().toLowerCase() + ".example.qa";
        accountPage.sendKeys(AccountPage.AccountPageWebsiteField, contactWebsiteHOLDER);

        contactPostcodeHOLDER = RandomStringUtils.randomNumeric(7);
        accountPage.sendKeys(AccountPage.AccountPagePostCodeField, contactPostcodeHOLDER);

        contactCountyHOLDER = lorem.getStateFull();
        accountPage.sendKeys(AccountPage.AccountPageCountyField, contactCountyHOLDER);

        contactCityHOLDER = lorem.getCity();
        accountPage.sendKeys(AccountPage.AccountPageCityField, contactCityHOLDER);

        contactAddressHOLDER = lorem.getTitle(3,3);
        accountPage.sendKeys(AccountPage.AccountPageAddressField, contactAddressHOLDER);

        accountPage.click(AccountPage.SportsAssociationText); //defocus field

        logger.info("Saving updated contact details");
        if ((driverManager.agent.contains("appium"))) {
            accountPage.scrollPageToTop();
        }
        accountPage.click(AccountPage.AccountPageUpdateTopButton);
        accountPage.findOnPage(AccountPage.AccountUpdatedNotification);
        assertThat("Mail not as expected after update button is click!", accountPage.getElementAttribute(AccountPage.AccountPageMailField,
                "value"), containsString(contactEmailHOLDER.toLowerCase()));

        assertThat("Mail not as expected when email is changed", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected when phone number changed!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected when website url changed!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected when postcode is changed!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected when county is changed!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected when city is changed!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected when address is changed!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Mail not as expected before update button is clicked", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));

    }

    public void verifyUpdatedContactDetailsRemain() {
        logger.info("Verifying " + clubAdminUsername + " club details remain");
        accountPage.waitForElementDisplayedByXpathWithTimeout(AccountPage.AccountPageUpdateTopButton, 30);
        assertThat("Mail not as expected!", accountPage.getElementAttribute(AccountPage.AccountPageMailField, "value"), containsString(contactEmailHOLDER.toLowerCase()));
        assertThat("Phone not as expected!", accountPage.getElementAttribute(AccountPage.AccountPagePhoneField1, "value"), containsString(contactPhoneHOLDER));
        assertThat("Website not as expected!", accountPage.getElementAttribute(AccountPage.AccountPageWebsiteField, "value"), containsString(contactWebsiteHOLDER));
        assertThat("Postcode not as expected!", accountPage.getElementAttribute(AccountPage.AccountPagePostCodeField, "value"), containsString(contactPostcodeHOLDER));
        assertThat("County not as expected!", accountPage.getElementAttribute(AccountPage.AccountPageCountyField, "value"), containsString(contactCountyHOLDER));
        assertThat("City not as expected!", accountPage.getElementAttribute(AccountPage.AccountPageCityField, "value"), containsString(contactCityHOLDER));
        assertThat("Address not as expected!", accountPage.getElementAttribute(AccountPage.AccountPageAddressField, "value"), containsString(contactAddressHOLDER));
    }

    public void deleteSocialMediaLinks() {
        logger.info("Delete social links for FB, Twitter, Instagram links");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageFacebookField);
        accountPage.sendKeys(AccountPage.AccountPageFacebookField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageFacebookField);
        accountPage.sendKeys(AccountPage.AccountPageFacebookField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageFacebookField);
        accountPage.sendKeys(AccountPage.AccountPageFacebookField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageFacebookField);
        logger.info("Facebook field cleared");

        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageInstagramField);
        accountPage.sendKeys(AccountPage.AccountPageInstagramField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageInstagramField);
        accountPage.sendKeys(AccountPage.AccountPageInstagramField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageInstagramField);
        accountPage.sendKeys(AccountPage.AccountPageInstagramField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageInstagramField);
        logger.info("Instagram field cleared");

        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageTwitterField);
        accountPage.sendKeys(AccountPage.AccountPageTwitterField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageTwitterField);
        accountPage.sendKeys(AccountPage.AccountPageTwitterField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageTwitterField);
        accountPage.sendKeys(AccountPage.AccountPageTwitterField, "x");
        accountPage.clearInputFieldUsingBackSpaceKey(AccountPage.AccountPageTwitterField);
        logger.info("Twitter field cleared");
    }

    public void addSocialMediaLinks() {
        logger.info("Add social links for FB, Twitter, Instagram links");
        accountPage.findOnPage(AccountPage.AccountPageUpdateTopButton);
        accountPage.sendKeys(AccountPage.AccountPageFacebookField, "https://www.facebook.com/WeAreClubforce");
        accountPage.sendKeys(AccountPage.AccountPageInstagramField, "https://www.instagram.com/clubforcecom");
        accountPage.sendKeys(AccountPage.AccountPageTwitterField, "https://twitter.com/WeAreClubforce");
        accountPage.click(AccountPage.SocialMediaText);  //defocus
    }

    public void verifyLinksAreRemovedFromHomepageHeader() {
        logger.info("Check links are removed homepage header and footer");
        assertFalse(accountPage.isElementPresent(ClubPage.FacebookIconHeader));
        assertFalse(accountPage.isElementPresent(ClubPage.TwitterIconHeader));
        assertFalse(accountPage.isElementPresent(ClubPage.InstagramIconHeader));
    }

    public void verifyLinksAreRemovedFromHomepageFooter() {
        logger.info("Check links are removed homepage header and footer");
        assertFalse(accountPage.isElementPresent(ClubPage.FacebookIconFooter));
        assertFalse(accountPage.isElementPresent(ClubPage.TwitterIconFooter));
        assertFalse(accountPage.isElementPresent(ClubPage.InstagramIconFooter));
    }

    public void verifyLinksAreAddedToHomepageHeader() {
        logger.info("Check links are added homepage header");
        assertTrue(accountPage.isElementPresent(ClubPage.FacebookIconHeader));
        assertTrue(accountPage.isElementPresent(ClubPage.TwitterIconHeader));
        assertTrue(accountPage.isElementPresent(ClubPage.InstagramIconHeader));
    }

    public void verifyLinksAreAddedToHomePageFooter() {
        logger.info("Check links are added to page footer");
        assertTrue(accountPage.isElementPresent(ClubPage.FacebookIconFooter));
        assertTrue(accountPage.isElementPresent(ClubPage.TwitterIconFooter));
        assertTrue(accountPage.isElementPresent(ClubPage.InstagramIconFooter));
    }
}
