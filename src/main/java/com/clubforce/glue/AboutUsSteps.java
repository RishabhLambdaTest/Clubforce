package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficeAboutUsPage;
import com.clubforce.pages.BackofficePage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class AboutUsSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public AboutUsSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficeAboutUsPage =  driverManager.backofficeAboutUsPage;
    }

    private static String AboutUsRandomTitleHOLDER;
    private static String AboutUsRandomSummaryHOLDER;
    private static String AboutUsRandomContentHOLDER;

    @And("ClubAdmin {string} an About Us page")
    public void adminUpdatesOrCreatesAboutUs(String action) {
        Lorem lorem = LoremIpsum.getInstance();

        logger.info("Updating "+clubAdminUsername+"'s About Us");
        AboutUsRandomTitleHOLDER = lorem.getTitle(3,3);
        backofficeAboutUsPage.sendKeys(BackofficePage.FormControlNameTitle,  AboutUsRandomTitleHOLDER);

        logger.info("Updated About Us title");
        AboutUsRandomSummaryHOLDER = lorem.getTitle(4,6);
        backofficeAboutUsPage.sendKeys(BackofficePage.FormControlNameSummary, AboutUsRandomSummaryHOLDER);

        logger.info("Updated About Us summary");
        AboutUsRandomContentHOLDER = lorem.getWords(100, 100);
        backofficeAboutUsPage.clear(BackofficeAboutUsPage.AboutUsQLEditorFilled);
        backofficeAboutUsPage.sendKeys(BackofficeAboutUsPage.AboutUsQLEditorBlank, AboutUsRandomContentHOLDER);
        backofficeAboutUsPage.click(BackofficeAboutUsPage.AboutUsAddVideoButton);
        backofficeAboutUsPage.sendKeys(BackofficeAboutUsPage.AboutUsEmbedVideo, "https://www.youtube.com/watch?v=WQOmyoQ7ISE");
        backofficeAboutUsPage.click("//a[contains(@class,'ql-action')]");

        if (envName.contains("test")){

            if(action.equalsIgnoreCase("updates")){
                logger.info("Delete current aboutUsImage");
                backofficeAboutUsPage.click(BackofficeAboutUsPage.AboutUsDeleteImageButton);
                backofficeAboutUsPage.click(BackofficeAboutUsPage.AboutUsRemoveImageButton);
            }

            logger.info("Upload aboutUsImage");
            backofficeAboutUsPage.uploadFileUsingJSExec("aboutUsImage.jpg", BackofficeAboutUsPage.AboutUsPageFilePath);
            backofficeAboutUsPage.click(BackofficeAboutUsPage.AboutUsUploadImageButton);
        }

        backofficeAboutUsPage.waitTwoSeconds();
        backofficeAboutUsPage.scrollPageToBottom();
        backofficeAboutUsPage.click(BackofficePage.FooterPublishButton);

        if(action.equalsIgnoreCase("updates")){
            backofficeAboutUsPage.waitForElementDisplayedByXpathWithTimeout(BackofficeAboutUsPage.AboutUsSavedSuccessNotification,30);
        }else{
            backofficeAboutUsPage.waitForElementDisplayedByXpathWithTimeout(BackofficeAboutUsPage.AboutUsCreatedSuccessNotification,30);
        }

        assertThat(backofficeAboutUsPage.getElementAttribute(BackofficePage.FormControlNameTitle, "value"),is( AboutUsRandomTitleHOLDER));
        assertThat(backofficeAboutUsPage.getElementAttribute(BackofficePage.FormControlNameSummary, "value"),is(AboutUsRandomSummaryHOLDER));
        assertThat(backofficeAboutUsPage.getElementAttribute(BackofficeAboutUsPage.AboutUsQLEditorFilled, "textContent"),is(AboutUsRandomContentHOLDER));
        logger.info("About us displaying correctly");
    }
    @Then("the About Us on the website is updated")
    public void websiteAboutUsIsUpdate() {
        logger.info("Going to club website");
        backofficeAboutUsPage.goToClubURL("");
        logger.info("Deleting cookies");
        backofficeAboutUsPage.deleteAllCookies();
        logger.info("Refreshing club website page");
        backofficeAboutUsPage.refreshPage();

        logger.info("Verifying " + clubAdminUsername + "'s About Us on webpage");
        backofficeAboutUsPage.waitTwoSeconds();
        backofficeAboutUsPage.centreElement(BackofficeAboutUsPage.AboutUsReadMoreButton);
        backofficeAboutUsPage.click(BackofficeAboutUsPage.AboutUsReadMoreButton);
        backofficeAboutUsPage.waitForElementDisplayedByXpathWithTimeout(BackofficeAboutUsPage.AboutUsHeadingWebsite, 15);
        assertTrue(backofficeAboutUsPage.isElementPresent(String.format(BackofficeAboutUsPage.AboutUsPageText,  AboutUsRandomTitleHOLDER)), AboutUsRandomTitleHOLDER + " is displayed");
        assertTrue(backofficeAboutUsPage.isElementPresent(String.format(BackofficeAboutUsPage.AboutUsPageText, AboutUsRandomSummaryHOLDER)),AboutUsRandomSummaryHOLDER  +" is displayed");
        assertTrue(backofficeAboutUsPage.isElementPresent(String.format(BackofficeAboutUsPage.AboutUsPageText, AboutUsRandomContentHOLDER)), AboutUsRandomContentHOLDER  +" is displayed");
    }
}
