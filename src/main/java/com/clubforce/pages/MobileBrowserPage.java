package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobileBrowserPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public MobileBrowserPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String AccountHeaderLink = "//span[contains(.,'Account')]";
    public static final String MobilePageText = "//*[contains(text(),'%s')]";
    public static final String MobilePageImage = "//img[contains(@src, '%s')]";
    public static final String MobilePageTitle = "//h1[contains(.,'Dashboard')]";
    public static final String MobilePageSportsCard = "(//mat-card-content[@class='mat-card-content'])[1]";
    public static final String MobilePageSportsCardPrice = "(//p[@class='price'])[1]";

    //Methods
    public void verifyMobileBrowserWebPageElements() {
        logger.info("Verifying text on the mobile club web page");
        findOnPage("//h2[contains(.,'Club news')]");
        findOnPage("//p[contains(.,'Powered by Clubforce')]");
    }

    public void verifyMobileHamburgerSideNavElements() {
        logger.info("Verifying elements on the mobile sideNav");
        click(ClubPage.ClubPageHamburger);
        waitThreeSeconds();
        findOnPage("//span[contains(.,'Contact')]");
        findOnPage("(//mat-icon[@role='img'][contains(.,'Facebook icon')])[1]");
        findOnPage("(//mat-icon[@role='img'][contains(.,'Twitter icon')])[1]");
        findOnPage("(//mat-icon[@role='img'][contains(.,'Instagram icon')])[1]");
        findOnPage("//mat-icon[@role='img'][contains(.,'shopping_cart0')]");
        findOnPage("//mat-icon[@role='img'][contains(.,'account_circle')]");


    }
}
