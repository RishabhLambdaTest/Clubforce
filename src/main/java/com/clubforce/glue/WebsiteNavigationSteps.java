package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.WebsiteNavigationPage;
import com.clubforce.pages.WebsiteSettingsPage;
import io.cucumber.java.en.And;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WebsiteNavigationSteps extends WebDriverManager {

    WebDriverManager driverManager;

    private static final Logger logger = LogManager.getLogger();

    public WebsiteNavigationSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.websiteNavigationPage = driverManager.websiteNavigationPage;
    }

    public static String MenuTitle;
    public static String MenuURL;

    @And("ClubAdmin creates multiple menu items")
    public void createMultipleMenuItems(){
        logger.info("Admin creates menu items");
        websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationHeading);

        logger.info("Verify website navigation page elements");
        verifyWebsiteNavigationPageElements();

        logger.info("Removing all website navigation items on page");
        deleteAllMenuItemsOnWebsiteNavigationPage();


        int i = 0;
        while (i < 8) {
            websiteNavigationPage.waitUntilElementInvisible(WebsiteNavigationPage.WebsiteNavigationMenuTitleField,15);
            websiteNavigationPage.click(WebsiteNavigationPage.AddWebsiteNavigationItemButton);
            websiteNavigationPage.waitForElementDisplayedByXpathWithTimeout(WebsiteNavigationPage.WebsiteNavigationMenuTitleField,10);

            logger.info("Enter in menu item title");
            String MenuTitle = "Menu item " + RandomStringUtils.randomNumeric(3);
            websiteNavigationPage.sendKeys(WebsiteNavigationPage.WebsiteNavigationMenuTitleField, MenuTitle);

            logger.info("Select URL as menu item content type");
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuItemSelector);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuURLItemSelector);

            logger.info("Enter in menu item domain");
            String MenuDomain = RandomStringUtils.randomNumeric(20);
            MenuDomain = "https://" + MenuDomain + ".com";
            websiteNavigationPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuURLField, MenuDomain);

            if (envName.contains("test")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("sandbox")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("prod")) {
                websiteNavigationPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
            }

            websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationSuccessfullyCreatedNotification);
            i++;
        }
    }

    @And("they add a Lotto Menu item")
    public void addLottoToMenu() {
        logger.info("Verify website navigation page elements");
        verifyWebsiteNavigationPageElements();

        logger.info("Removing all website navigation items on page");
        deleteAllMenuItemsOnWebsiteNavigationPage();

        websiteNavigationPage.waitUntilElementInvisible(WebsiteNavigationPage.WebsiteNavigationMenuTitleField,15);
        websiteNavigationPage.click(WebsiteNavigationPage.AddWebsiteNavigationItemButton);
        websiteNavigationPage.waitForElementDisplayedByXpathWithTimeout(WebsiteNavigationPage.WebsiteNavigationMenuTitleField,10);

        if (LottoSteps.LottoNameSuffixHOLDER == null) {
            LottoSteps.LottoNameSuffixHOLDER = "Lotto";
        }

        websiteNavigationPage.sendKeys(WebsiteNavigationPage.WebsiteNavigationMenuTitleField, LottoSteps.LottoNameSuffixHOLDER);
        websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuItemSelector);
        websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationProductItemSelector);
        websiteNavigationPage.click(WebsiteNavigationPage.ProductLinkDropdown);
        websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationLottoMenuItemSelector);
        websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
        websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationSuccessfullyCreatedNotification);
        logger.info("Added Lotto website navigation menu link: "+ LottoSteps.LottoNameSuffixHOLDER);
    }

    @And("ClubAdmin adds menu items for club")
    public void addsMenuItems() {
        logger.info("Admin creates menu items");
        websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationHeading);

        logger.info("Verify website navigation page elements");
        verifyWebsiteNavigationPageElements();

        logger.info("Removing all website navigation items on page");
        deleteAllMenuItemsOnWebsiteNavigationPage();

        int i = 1;
        int finalNumber = 2;
        while (i <= finalNumber) {
            websiteNavigationPage.waitUntilElementInvisible(WebsiteNavigationPage.WebsiteNavigationMenuTitleField,15);
            websiteNavigationPage.click(WebsiteNavigationPage.AddWebsiteNavigationItemButton);
            websiteNavigationPage.waitForElementDisplayedByXpathWithTimeout(WebsiteNavigationPage.WebsiteNavigationMenuTitleField,10);

            websiteNavigationPage.click(WebsiteSettingsPage.WebsiteSettingsMenuTitleField);

            MenuTitle = "Menu item "+ i + RandomStringUtils.randomNumeric(2);
            logger.info("Menu title " +i+ " is " + MenuTitle);
            websiteNavigationPage.sendKeys(WebsiteNavigationPage.WebsiteNavigationMenuTitleField, MenuTitle);

            MenuURL = "https://www.clubforce.com";
            logger.info("Select URL as menu item content type");
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuItemSelector);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuURLItemSelector);
            websiteNavigationPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuURLField, MenuURL);

            if (envName.contains("test")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("sandbox")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("prod")) {
                websiteNavigationPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
            }

            websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationSuccessfullyCreatedNotification);

            MenuTitle = "PagePage" + i;
            logger.info("Menu title " +i+ " is " + MenuTitle);
            logger.info("Menu Page item is "+ MenuTitle);
            websiteNavigationPage.click(WebsiteNavigationPage.AddWebsiteNavigationItemButton);
            websiteNavigationPage.sendKeys(WebsiteNavigationPage.WebsiteNavigationMenuTitleField, MenuTitle);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuItemSelector);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteSettingMenuPageItemSelector);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteSettingsContentPageLink);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteSettingContentLinkPagePageOption);

            if (envName.contains("test")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("sandbox")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("prod")) {
                websiteNavigationPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
            }

            websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationSuccessfullyCreatedNotification);
            websiteNavigationPage.click(WebsiteNavigationPage.AddWebsiteNavigationItemButton);

            LottoSteps.LottoNameSuffixHOLDER = "Lotto" + i;
            logger.info("Menu title " +i+ " is " + LottoSteps.LottoNameSuffixHOLDER);

            websiteNavigationPage.sendKeys(WebsiteNavigationPage.WebsiteNavigationMenuTitleField, LottoSteps.LottoNameSuffixHOLDER);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationMenuItemSelector);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationProductItemSelector);
            websiteNavigationPage.click(WebsiteNavigationPage.ProductLinkDropdown);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationLottoMenuItemSelector);

            if (envName.contains("test")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("sandbox")) {
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
            }

            if (envName.contains("prod")) {
                websiteNavigationPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
            }

            websiteNavigationPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationSuccessfullyCreatedNotification);
            logger.info("Added Lotto website navigation menu link: "+ LottoSteps.LottoNameSuffixHOLDER);
            i++;
        }
    }

    public void deleteAllMenuItemsOnWebsiteNavigationPage(){
        while (websiteNavigationPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsItemCheckbox)) {
            logger.info("Menu item found, removing");
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPageMenuItemCheckbox);
            websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPageRemoveButton);
        }
        assertFalse(websiteNavigationPage.isElementDisplayed(WebsiteNavigationPage.WebsiteNavigationPageMenuItemCheckbox));
    }

    public void verifyWebsiteNavigationPageElements(){
        assertTrue(websiteNavigationPage.isElementDisplayed(WebsiteNavigationPage.WebsiteNavigationHeading));
        assertTrue(websiteNavigationPage.isElementDisplayed(WebsiteNavigationPage.WebsiteNavigationInformationText));
        assertTrue(websiteNavigationPage.isElementDisplayed(WebsiteNavigationPage.AddWebsiteNavigationItemButton));
    }
}
