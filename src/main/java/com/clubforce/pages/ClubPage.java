package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;

public class ClubPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public ClubPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String ClubPageText = "//*[contains(text(),'%s')]";
    public static final String PoweredByClubforceText = "//p[contains(.,'Powered by Clubforce')]";
    public static final String MenuIcon = "//mat-icon[@role='img'][contains(.,'menu')]";
    public static final String ClubPageHamburger = "//span[@class='navbar-toggler-icon']";
    public static final String FacebookIconHeader = "(//*[@svgicon='facebook'])[1]";
    public static final String FacebookIconFooter = "(//*[@svgicon='facebook'])[2]";
    public static final String TwitterIconHeader = "(//*[@svgicon='twitter'])[1]";
    public static final String TwitterIconFooter = "(//*[@svgicon='twitter'])[2]";
    public static final String InstagramIconHeader = "(//*[@svgicon='instagram'])[1]";
    public static final String InstagramIconFooter = "(//*[@svgicon='instagram'])[2]";
    public static final String ContactText = "//*[contains(.,'Contact')]";
    public static final String ClubNavBarIconMobile = "//span[contains(@class,'navbar-toggler-icon')]";
    public static final String ClubNewsHeading = "//h2[contains(.,'Club news')]";
    public static final String AboutClubforceLinkText = "//div[@class='mat-list-item-content'][contains(.,'About Clubforce')]";
    public static final String ClubWebsiteCancelButtonOnPopUp = "//span[contains(.,'Cancel')]";
    public static final String ClubWebsitePagePageHeading = "//h1[contains(.,'PagePage')]";
    public static final String ClubWebsiteMenuClubLogo = "//*[contains(@itemprop,'logo')]";
    public static final String ClubforceWebsiteFindMyClubHeading = "//a[contains(.,'Find My Club')]";
    public static final String ClubWebsiteMoreMenuItemDropDown = "//span[@class='mat-button-wrapper'][contains(.,'More...')]";
    public static final String ClubWebsiteContactMenuItem = "//span[@class='mat-button-wrapper'][contains(.,'Contact')]";
    public static final String ClubNameInFooter = "//h3[contains(@class,'mat-subheading-2')]";

    //Methods
    public void verifyClubPageHomepageElements() {
        logger.info("verifyLottoBasicPageElements");
        assertTrue(isElementPresent("//h2[contains(.,'Latest news')]"));
        assertTrue(isElementPresent(String.format(ClubPageText, "Get the latest club notices, details on events, fixtures and results, and other club announcements.")));
    }
}


