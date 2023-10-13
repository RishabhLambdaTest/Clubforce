package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.ArticlePage;
import com.clubforce.pages.ContactPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ContactPageSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();
    WebDriverManager driverManager;

    public ContactPageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.contactPage = driverManager.contactPage;
        this.clubPage = driverManager.clubPage;
    }

    // Variable holders
    private String ContactFirstNamesHOLDER = " ";
    private String ContactLastNameHOLDER = " ";
    private String ContactEmailHOLDER = " ";
    private String ContactPhoneHOLDER = " ";
    private String ContactMessageTextHOLDER = " ";

    @Given("all contact messages in admin are read")
    public void makeAllContactMessagesRead() {
        contactPage.waitFiveSeconds();
        logger.info("Marking unread mails read");

        while (contactPage.isElementPresent(ContactPage.UnreadMailIcon)) {
            contactPage.waitOneSecond();
            contactPage.click(ContactPage.UnreadMailIcon);
            contactPage.waitOneSecond();
        }
        logger.info("All mail marked read");
    }

    @And("user go to club contact page")
    public void userGoesToClubContactPage() {
        logger.info("Going to club contact page");
        contactPage.goToClubURL("/");
        contactPage.acceptCookies();
        if ((driverManager.agent.contains("appium"))) {
            contactPage.click("//span[contains(@class,'navbar-toggler-icon')]");
            contactPage.waitTwoSeconds();
        }
        contactPage.click(ContactPage.ClubPageContactLink);
        contactPage.findOnPage("//img[contains(@itemprop,'logo')]");
        contactPage.findOnPage("//h2[contains(.,'Send us a message')]");
        contactPage.findOnPage("//button[contains(.,'Send')]");
    }

    @And("they can send a contact message to the club")
    public void userSendsMessageToClub() {
        checkErrorHandling();
        inputNameAndSendAndThenVerifyResults();
        inputLastNameAndSendAndThenVerifyResults();
        inputInvalidEmailAddressAndSendAndThenVerifyResults();
        inputValidEmailAddressAndSendAndThenVerifyResults();
        inputPhoneDetails();
        inputMessageAndThenVerifyResults();
        contactPage.waitTwoSeconds();
        contactPage.click(ContactPage.ClubPageContactSendButton);
        contactPage.waitTwoSeconds();
        contactPage.deleteAllCookies();// so club admin can log in again in next step
        contactPage.waitFiveSeconds();
    }

    @Then("ClubAdmin see contact message")
    public void adminSeeContactMessage() {
        logger.info("Verifying message appears in admin inbox.");
        contactPage.waitTwoSeconds();
        contactPage.findOnPage("//span[@class='fw-bold ng-star-inserted'][contains(.,'"+ ContactFirstNamesHOLDER +" "+ContactLastNameHOLDER+"')]");
        contactPage.click(ContactPage.ReadMoreMailChevron);
        contactPage.waitTwoSeconds();
        assertThat("Did not find expected user's names!", contactPage.isElementPresent(String.format(ArticlePage.ArticlePageText, ContactFirstNamesHOLDER +" "+ContactLastNameHOLDER)));
        assertThat("Did not find expected user's mail!", contactPage.isElementPresent(String.format(ArticlePage.ArticlePageText, ContactEmailHOLDER)));
        assertThat("Did not find expected user's phone!", contactPage.isElementPresent(String.format(ArticlePage.ArticlePageText, ContactPhoneHOLDER)));
        assertThat("Did not find expected user's message!", contactPage.isElementPresent(String.format(ArticlePage.ArticlePageText, ContactMessageTextHOLDER)));

        logger.info("Set message read");
        contactPage.click(ContactPage.UnreadMailIcon);
        contactPage.waitTwoSeconds();
        assertFalse(contactPage.isElementDisplayed(ContactPage.UnreadMailIcon));
    }

    @Then("{string} is displayed on Dashboard contact message tile")
    public void checkContactMessagesOnDashboardTile(String number) {
        contactPage.waitForElementDisplayedByXpathWithTimeout("//button[contains(.,'View all messages')]", 30);

        switch(number) {
            case "No New Messages":
                contactPage.waitForElementDisplayedByXpathWithTimeout("//button[contains(.,'View all messages')]", 30);
                assertTrue(contactPage.isElementDisplayed(ContactPage.DashboardMessageTileNoNewMessagesText));
                assertTrue(contactPage.isElementDisplayed(ContactPage.DashboardMessageTitleNoMessagesInInboxText));
                break;
            case "1 New Message":
                boolean messageReceived = contactPage.isElementPresent(ContactPage.DashboardMessageTileOneNewMessage);
                int retries = 0;
                while (!messageReceived && retries < 60) {
                    logger.info("Contact message not found yet. Refreshing page. Retry number "+retries);
                    retries++;
                    contactPage.refreshPage();
                    contactPage.waitForElementDisplayedByXpathWithTimeout("//button[contains(.,'View all messages')]", 30);
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkContactMessagesOnDashboardTile!");
        }
    }

    @Then("contact page reacts accordingly to {string}")
    public void contactPageReacts(String display_option) {
        logger.info("Checking on contact page");
        switch (display_option) {
            case "hide":
                assertFalse(contactPage.isElementPresent(ContactPage.StreetAddressText));
                assertFalse(contactPage.isElementPresent(ContactPage.AddressLocalityText));
                assertFalse(contactPage.isElementPresent(ContactPage.AddressRegionText));
                logger.info("Address details on contact page not displaying - pass");
                break;
            case "show":
                assertTrue(contactPage.isElementPresent(ContactPage.StreetAddressText));
                assertTrue(contactPage.isElementPresent(ContactPage.AddressLocalityText));
                assertTrue(contactPage.isElementPresent(ContactPage.AddressRegionText));
                logger.info("Address details on contact page displaying - pass");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for contactPageReacts!");
        }
    }

    public void checkErrorHandling() {
        logger.info("Checking error handling and then sending message");
        contactPage.click(ContactPage.ClubPageContactSendButton);
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your name"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your surname"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your email"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your message"));
    }

    public void inputNameAndSendAndThenVerifyResults() {
        Lorem lorem = LoremIpsum.getInstance();
        String RandomFirstNames = lorem.getFirstNameFemale()+" "+lorem.getFirstNameFemale();
        ContactFirstNamesHOLDER = RandomFirstNames;
        logger.info("ContactFirstName is "+ ContactFirstNamesHOLDER);
        contactPage.sendKeys(ContactPage.NameField, RandomFirstNames);
        contactPage.click(ContactPage.ClubPageContactSendButton);
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your surname"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your email"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your message"));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your name")));
    }

    public void inputLastNameAndSendAndThenVerifyResults() {
        Lorem lorem = LoremIpsum.getInstance();
        String RandomLastName = lorem.getCountry();  //use countries, last names can contain ' which breaks test code
        ContactLastNameHOLDER = RandomLastName;
        contactPage.sendKeys(ContactPage.ClubPageContactSurnameField, RandomLastName);
        contactPage.click(ContactPage.ClubPageContactSendButton);
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your email"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your message"));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your name")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your surname")));
    }

    public void inputInvalidEmailAddressAndSendAndThenVerifyResults() {
        contactPage.sendKeys(ContactPage.ClubPageContactEmailField, "wnzt4.hello");
        contactPage.click(ContactPage.ClubPageContactSendButton);
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter a valid email address"));
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your message"));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your name")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your surname")));
    }

    public void inputValidEmailAddressAndSendAndThenVerifyResults() {
        contactPage.sendKeys(ContactPage.ClubPageContactEmailField, "wnzt4.hello@inbox.testmail.app");
        ContactEmailHOLDER = "wnzt4.hello@inbox.testmail.app";
        contactPage.click(ContactPage.ClubPageContactSendButton);
        contactPage.findOnPage(String.format(ContactPage.ClubPageText, "Please enter your message"));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your name")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your surname")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your email")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter a valid email address")));
    }

    public void inputPhoneDetails() {
        ContactPhoneHOLDER = "00440" + RandomStringUtils.randomNumeric(10);
        contactPage.sendKeys(ContactPage.ClubPageContactPhoneField, ContactPhoneHOLDER);
    }

    public void inputMessageAndThenVerifyResults() {
        Lorem lorem = LoremIpsum.getInstance();
        ContactMessageTextHOLDER = lorem.getWords(45, 50);
        contactPage.sendKeys(ContactPage.ClubPageContactMessageField, ContactMessageTextHOLDER);
        contactPage.waitTwoSeconds();
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your name")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your surname")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your email")));
        assertFalse(contactPage.isElementDisplayed(String.format(ContactPage.ClubPageText, "Please enter your message")));
    }
}
