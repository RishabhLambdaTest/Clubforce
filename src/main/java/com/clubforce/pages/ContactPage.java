package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public ContactPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String ClubPageText = "//*[contains(text(),'%s')]";
    public static final String ClubPageContactLink = "//span[contains(.,'Contact')]";
    public static final String ContactLinkForClubPage = "//span[@class='mat-button-wrapper'][contains(.,'Contact')]";
    public static final String NameField = "//input[@formcontrolname='name']";
    public static final String ClubPageContactSurnameField = "//input[@formcontrolname='surname']";
    public static final String ClubPageContactEmailField = "//input[contains(@formcontrolname,'email')]";
    public static final String ClubPageContactPhoneField = "//input[contains(@formcontrolname,'phone')]";
    public static final String ClubPageContactMessageField = "//textarea[contains(@formcontrolname,'message')]";
    public static final String ClubPageContactSendButton = "//button[contains(.,'Send')]";
    public static final String UnreadMailIcon = "//mat-icon[@role='img'][contains(.,'markunread')]";
    public static final String StreetAddressText = "//span[contains(@itemprop,'streetAddress')]";
    public static final String AddressLocalityText = "//span[contains(@itemprop,'addressLocality')]";
    public static final String AddressRegionText = "//span[contains(@itemprop,'addressRegion')]";
    public static final String ReadMoreMailChevron = "(//button[contains(.,'Read more expand_more')])[1]";
    public static final String DashboardMessageTileNoNewMessagesText = "//h2[contains(.,'No new messages')]";
    public static final String DashboardMessageTitleNoMessagesInInboxText = "//p[contains(.,'There are no new messages in your inbox')]";
    public static final String DashboardMessageTileOneNewMessage = "//h2[contains(.,'1 new message')]";

    //Methods
    public void verifyClubPageElements() {
        logger.info("Verifying text and images on the club page");
        findOnPage("//img[contains(@itemprop,'logo')]");
        findOnPage("//span[contains(.,'Contact')]");
        findOnPage("//mat-icon[contains(.,'account_circle')]");
    }
 }
