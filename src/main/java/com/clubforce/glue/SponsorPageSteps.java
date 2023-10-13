package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.SponsorPage;
import com.clubforce.pages.WebsiteSettingsPage;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SponsorPageSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public SponsorPageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.sponsorPage =driverManager.sponsorPage;
    }

    @And("ClubAdmin creates a sponsor")
    public void createSponsor(){
        logger.info("Admin add Sponsors");
        sponsorPage.findOnPage(SponsorPage.SponsorHeading);
        sponsorPage.click(SponsorPage.AddSponsorButton);
        sponsorPage.click(SponsorPage.SponsorNameField);
        sponsorPage.click(SponsorPage.AddSponsorHeading);
        sponsorPage.findOnPage(SponsorPage.SponsorNameInformationText);
        sponsorPage.sendKeys(SponsorPage.SponsorNameField, "Clubforce");
        sponsorPage.click(SponsorPage.AddSponsorHeading);
        sponsorPage.sendKeys(SponsorPage.SponsorLinkField, "https://www.clubforce.com/");
        sponsorPage.click(SponsorPage.AddSponsorHeading);

        logger.info("Click upload button");
        sponsorPage.uploadFileUsingJSExec("sponsorImage.jpg", SponsorPage.SponsorsPageFilePath);
        sponsorPage.click(BackofficePage.UploadButtonOnCropper);
        sponsorPage.click(WebsiteSettingsPage.PublishButton);
        sponsorPage.findOnPage(SponsorPage.SponsorSuccessNotification);
        sponsorPage.click(SponsorPage.NotificationCloseIcon);
    }
}
