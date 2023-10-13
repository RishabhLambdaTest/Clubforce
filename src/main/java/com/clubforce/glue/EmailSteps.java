package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.EmailPage;
import com.clubforce.util.HttpConnectionUtil;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class EmailSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public EmailSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.emailPage = driverManager.emailPage;
    }

    private String EmailSubjectHOLDER = " ";
    private String EmailContentHOLDER = " ";



    @And("ClubAdmin selects to compose a new email")
    public void caClicksComposeEmail() {
        if (emailPage.isElementDisplayed("//h2[contains(.,'No emails')]")) {
            logger.info("This club have no emails yet");
        } else {
            logger.info("This club has emails");
            verifyEmailPageItems();
        }

        emailPage.click(EmailPage.EmailComposeButton);
        emailPage.findOnPage(EmailPage.EmailNewMessageHeading);
        verifyNewMessagePageItems();
    }

    @And("they compose and send an email to recipient {string}")
    public void caComposesEmail(String recipient) {
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Club admin check error handling");
        emailPage.click(EmailPage.EmailPreviewAndSendButton);
        emailPage.findOnPage(EmailPage.EmailErrorChooseARecipient);
        emailPage.findOnPage(EmailPage.EmailErrorEnterSubject);
        emailPage.findOnPage(EmailPage.EmailErrorTypeYourMessage);
        logger.info("Club admin choose recipients for email");

//        Mail test club Sandbox qaMailTestClub
//        https://genirelandpromo734576.sandbox.clubforce.io
//
//        MembershipGroup wnzt4.membership@inbox.testmail.app
//        LottoGroup wnzt4.lotto@inbox.testmail.app
//        NotInAnyGroupUser
//        BothGroups

        switch (recipient) {
            case "MembershipGroup":
                emailPage.click(EmailPage.EmailSelectMailingListField);
                emailPage.waitTwoSeconds();
                emailPage.click(EmailPage.EmailDropdownChoiceMembers);
                break;
            case "LottoGroup":
                emailPage.click(EmailPage.EmailSelectMailingListField);
                emailPage.waitTwoSeconds();
                emailPage.click(EmailPage.EmailDropdownChoiceLottoPlayers);
                break;
            case "NotInAnyGroupUser":
                emailPage.sendKeys(EmailPage.EmailSelectMailingListField, "wnzt4.NotInAnyGroupUser@inbox.testmail.app");
                emailPage.waitTwoSeconds();
                emailPage.pressEnter(EmailPage.EmailSelectMailingListField);
                break;
            case "BothGroups":
                emailPage.click(EmailPage.EmailSelectMailingListField);
                emailPage.waitTwoSeconds();
                emailPage.click(EmailPage.EmailDropdownChoiceMembers);
                emailPage.click(EmailPage.EmailSelectMailingListField);
                emailPage.waitTwoSeconds();
                emailPage.click(EmailPage.EmailDropdownChoiceLottoPlayers);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for caComposesEmail!");
        }

        logger.info("Club admin composes subject");
        EmailSubjectHOLDER = "Subject for "+recipient+": "+lorem.getWords(3,5);
        emailPage.click(EmailPage.EmailNewMessageHeading); // defocus to close the recipient dropdown
        emailPage.sendKeys(EmailPage.EmailSubjectField, EmailSubjectHOLDER);
        emailPage.waitTwoSeconds();
        emailPage.findOnPage("//p[@class='m-0'][contains(.,'"+EmailSubjectHOLDER+"')]");

        logger.info("Club admin composes content");
        EmailContentHOLDER = "Content: "+lorem.getWords(20,500);
        emailPage.sendKeys(EmailPage.EmailContentField, EmailContentHOLDER);

        logger.info("Club admin sends the email");
        emailPage.click(EmailPage.EmailPreviewAndSendButton);
        emailPage.findOnPage("(//p[contains(.,'"+EmailSubjectHOLDER+"')])[2]");
        emailPage.findOnPage("(//p[contains(.,'"+EmailContentHOLDER+"')])[2]");
        emailPage.click(EmailPage.EmailPreviewAndSendBackButton);
        emailPage.waitUntilElementInvisible(EmailPage.EmailPreviewAndSendBackButton,5);
        emailPage.click(EmailPage.EmailPreviewAndSendButton);
        emailPage.findOnPage("(//p[contains(.,'"+EmailSubjectHOLDER+"')])[2]");
        emailPage.findOnPage("(//p[contains(.,'"+EmailContentHOLDER+"')])[2]");
        emailPage.click(EmailPage.EmailPreviewAndSendSendButton);

        emailPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Email')]", 5);
        verifyEmailPageItems();
        assertFalse(emailPage.isElementPresent("//*[contains(text(),'Error sending email')]"));
        assertTrue(emailPage.isElementPresent("//span[contains(@title,'"+EmailSubjectHOLDER+"')]")); // mail is not on frontpage, too many mails
        assertTrue(emailPage.isElementDisplayed("(//span[contains(.,'"+ SeleniumUtilities.getDateTimeFormat("dd/MM/yyyy")+"')])[1]"));
        assertTrue(emailPage.isElementDisplayed("(//span[contains(@title,'Glenn McCullough')])[1]"));
    }

    @And("recipients gets mail from ClubAdmin")
    public void recipientsGetsMailFromClubAdmin() {
        logger.info("Club admin verifies BO mail page contains the sent mail info");
        emailPage.waitThirtySeconds();
        emailPage.waitThirtySeconds();
        String allMailsInAStringFromTestMailApp = HttpConnectionUtil.sendSimpleGETRequest(
                "https://api.testmail.app/api/json?apikey=2a835032-6507-4e9e-8da9-153c8a33d44f&namespace=wnzt4&pretty=true").toString();

        assertThat("EmailSubjectHOLDER not found!", allMailsInAStringFromTestMailApp.contains(EmailSubjectHOLDER));
        assertThat("EmailContentHOLDER not found!", allMailsInAStringFromTestMailApp.contains(EmailContentHOLDER));
    }

    public void verifyEmailPageItems() {
        emailPage.findOnPage(EmailPage.EmailPageHeaderDate);
        emailPage.findOnPage(EmailPage.EmailPageHeaderSender);
        emailPage.findOnPage(EmailPage.EmailPageHeaderSubject);
        emailPage.findOnPage(EmailPage.EmailPageHeaderRecipients);
    }

    public void verifyNewMessagePageItems() {
        emailPage.findOnPage(EmailPage.EmailChooseRecipientsHeading);
        emailPage.findOnPage(EmailPage.EmailSelectMailingListField);
        emailPage.findOnPage(EmailPage.EmailWriteYourMessageHeading);
        emailPage.findOnPage(EmailPage.EmailPreviewAndSendButton);
    }
}
