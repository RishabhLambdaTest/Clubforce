package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.ClubPage;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClubHomepageSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public ClubHomepageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.clubPage = driverManager.clubPage;
    }

    @Then("menu items are displayed as expected on club website")
    public void checkAllMenuItemsWork() {
        String LottoMenuItemText1 = "Lotto1";
        String LottoMenuItemText2 = "Lotto2";
        String PageMenuItem1 = "PagePage1";
        String PageMenuItem2 = "PagePage2";
        String URLMenuItem1 = "Menu item 1";
        String URLMenuItem2 = "Menu item 2";

        logger.info("Clicking the second lotto menu item");
        clubPage.click("(//span[@class='mat-button-wrapper'][contains(.,'"+LottoMenuItemText2+"')])[1]");
        clubPage.click(ClubPage.ClubWebsiteCancelButtonOnPopUp);
        logger.info("Second lotto menu item works as expected");

        logger.info("Clicking the second page menu item");
        clubPage.click("//span[@class='mat-button-wrapper'][contains(.,'"+PageMenuItem2 +"')]");
        clubPage.findOnPage(ClubPage.ClubWebsitePagePageHeading);
        clubPage.click(ClubPage.ClubWebsiteMenuClubLogo);
        logger.info("Second page menu item works as expected");

        logger.info("Clicking the second URL menu item");
        clubPage.click("//span[@class='mat-button-wrapper'][contains(.,'"+URLMenuItem2+"')]");
        clubPage.switchToBrowserTab(1);
        clubPage.findOnPage(ClubPage.ClubforceWebsiteFindMyClubHeading);
        clubPage.switchToBrowserTab(0);
        logger.info("Second URL menu item works as expected");

        logger.info("Click contact menu item");
        clubPage.click(ClubPage.ClubWebsiteContactMenuItem);
        clubPage.click(ClubPage.ClubWebsiteMenuClubLogo);
        logger.info("Contact menu item works as expected");

        logger.info("Clicking the first lotto menu item");
        clubPage.click(ClubPage.ClubWebsiteMoreMenuItemDropDown);
        clubPage.click("(//span[@class='mat-button-wrapper'][contains(.,'"+LottoMenuItemText1+"')])[1]");
        clubPage.click(ClubPage.ClubWebsiteCancelButtonOnPopUp);
        logger.info("First lotto menu item works as expected");

        logger.info("Clicking the first page menu item");
        clubPage.click(ClubPage.ClubWebsiteMoreMenuItemDropDown);
        clubPage.click("//span[@class='mat-button-wrapper'][contains(.,'"+PageMenuItem1+"')]");
        clubPage.findOnPage(ClubPage.ClubWebsitePagePageHeading);
        clubPage.click(ClubPage.ClubWebsiteMenuClubLogo);
        logger.info("First page menu item works as expected");

        logger.info("Clicking the first URL menu item");
        clubPage.click(ClubPage.ClubWebsiteMoreMenuItemDropDown);
        clubPage.click("//span[@class='mat-button-wrapper'][contains(.,'"+URLMenuItem1+"')]");
        clubPage.switchToBrowserTab(1);
        clubPage.findOnPage(ClubPage.ClubforceWebsiteFindMyClubHeading);
        clubPage.switchToBrowserTab(2);
        clubPage.findOnPage(ClubPage.ClubforceWebsiteFindMyClubHeading);
        logger.info("First URL menu item works as expected");
    }


}
