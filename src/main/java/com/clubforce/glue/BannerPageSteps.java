package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.BannerPage;
import com.clubforce.pages.WebsiteSettingsPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;

public class BannerPageSteps extends WebDriverManager {

    WebDriverManager driverManager;

    public BannerPageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.bannerPage = driverManager.bannerPage;
    }

    private static final Logger logger = LogManager.getLogger();

    @And("ClubAdmin sets up banner for club")
    public void setUpClubBanner(){
        Lorem lorem = LoremIpsum.getInstance();

        logger.info("ClubAdmin adds banner text");
        bannerPage.waitForElementDisplayedByXpathWithTimeout(BannerPage.BannerPageHeading,15);
        verifyBannerPageElements();

        String BannerTitle = lorem.getTitle(5);
        bannerPage.clearInputFieldUsingBackSpaceKey(BackofficePage.FormControlNameTitle);
        bannerPage.sendKeys(BackofficePage.FormControlNameTitle, BannerTitle);

        logger.info("Click upload button");
        bannerPage.uploadFileUsingJSExec("bannerImage.jpg", BannerPage.BannerPageFilePath);
        bannerPage.click(BackofficePage.UploadButtonOnCropper);

        String BannerContent = lorem.getWords(11, 11);
        bannerPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsContentTitleField, BannerContent);
        bannerPage.scrollPageToTop();
        bannerPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
        bannerPage.findOnPage(BannerPage.BannerCreatedSuccessMessage);
    }

    public void verifyBannerPageElements(){
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerPageHeading));
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerInformationText));
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerTitleInformationText));
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerContentFieldInformationText));
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerButtonInformationText));
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerImageHeading));
        assertTrue(bannerPage.isElementDisplayed(BannerPage.BannerImageInformationText));
    }
}
