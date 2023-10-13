package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class WebsiteSettingsSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public WebsiteSettingsSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.websiteSettingsPage = driverManager.websiteSettingsPage;
        this.themePage = driverManager.themePage;
        this.websiteNavigationPage = driverManager.websiteNavigationPage;
    }

    private String BannerTitleHOLDER;
    private String BannerContentHOLDER;
    public String BannerViewMoreURLHOLDER;
    private String BannerViewMoreHOLDER;
    private String MenuTitleHOLDER;
    public String MenuURLHOLDER;
    private String MenuDomainHOLDER;
    private String ClubNameHOLDER;
    private String SponsorNameHOLDER;
    private String SponsorURLHOLDER;

    @When("they change {string} of their club")
    public void adminChangeWebSettings(String change_item) {
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Making changes to "+change_item);
        switch (change_item) {
            case "banner details":
                BannerTitleHOLDER = lorem.getTitle(5);
                websiteSettingsPage.findOnPage(WebsiteSettingsPage.WebsiteSettingsBannerTitleField);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsBannerTitleField, BannerTitleHOLDER);
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateTopButton);

                BannerContentHOLDER = lorem.getWords(11, 11);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsContentTitleField, BannerContentHOLDER);
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateTopButton);

                BannerViewMoreHOLDER = RandomStringUtils.randomNumeric(22);
                BannerViewMoreURLHOLDER = "https://"+ BannerViewMoreHOLDER +".com";

                websiteSettingsPage.waitTwoSeconds();
                if (!websiteSettingsPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsViewMoreURLField)) {
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonDropdown);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingViewMoreButtonURLOption);
                    assertTrue(websiteSettingsPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsViewMoreURLField));
                }
                websiteSettingsPage.clearInputFieldUsingBackSpaceKey(WebsiteSettingsPage.WebsiteSettingsViewMoreURLField);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsViewMoreURLField, BannerViewMoreURLHOLDER);
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateBottomButton);

                logger.info("Replacing the bannerImage");
                if (websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsBannerRemoveImageButton)) {
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsBannerRemoveImageButton);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsRemoveButton);
                    websiteSettingsPage.findOnPage(WebsiteSettingsPage.WebsiteSettingsBannerRemoveImageButton);
                }

                assertThat(websiteSettingsPage.getElementAttribute(WebsiteSettingsPage.WebsiteSettingsBannerTitleField, "value"),containsString(BannerTitleHOLDER));
                assertThat(websiteSettingsPage.getElementAttribute(WebsiteSettingsPage.WebsiteSettingsContentTitleField, "value"),containsString(BannerContentHOLDER));
                assertThat(websiteSettingsPage.getElementAttribute(WebsiteSettingsPage.WebsiteSettingsURLField, "value"),containsString(BannerViewMoreURLHOLDER));
                logger.info("Uploaded bannerImage");

                logger.info("Click upload button");
                websiteSettingsPage.uploadFileUsingJSExec("bannerImage.jpg", BannerPage.BannerPageFilePath);
                websiteSettingsPage.click(BackofficePage.UploadButtonOnCropper);
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateBottomButton);
                break;
            case "disable View More button":
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonDropdown);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonNoButtonOption);
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateBottomButton);
                break;
            case "enable View More button":
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonDropdown);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingViewMoreButtonURLOption);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsURLField, "https://clubforce.com");
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateBottomButton);
                break;
            case "menu item instances":

                while (websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsItemCheckbox)) {
                    logger.info("Menu item found, removing");
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsItemCheckbox);
                    websiteSettingsPage.findOnPage(WebsiteSettingsPage.WebsiteSettingsItemRemoveButton);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsItemRemoveButton);
                }

                int i = 1;
                int finalNumber = 8;
                while (i <= finalNumber) {
                    websiteSettingsPage.findOnPage(WebsiteSettingsPage.AddNavigationItemButton);
                    websiteSettingsPage.click(WebsiteSettingsPage.AddNavigationItemButton);
                    websiteSettingsPage.findOnPage(WebsiteSettingsPage.WebsiteSettingsMenuTitleField);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMenuTitleField);

                    MenuTitleHOLDER = "Menu item "+ finalNumber + i + RandomStringUtils.randomNumeric(2);
                    websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuTitleField, MenuTitleHOLDER);

                    MenuDomainHOLDER = RandomStringUtils.randomNumeric(20);
                    MenuURLHOLDER = "https://"+MenuDomainHOLDER+".com";
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMenuItemSelector);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingMenuURLItemSelector);
                    websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuURLField, MenuURLHOLDER);

                    websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
                    websiteSettingsPage.findOnPage(WebsiteNavigationPage.WebsiteNavigationSuccessfullyCreatedNotification);
                    websiteSettingsPage.click(WebsiteNavigationPage.WebsiteNavigationPageCloseIcon);
                    i++;
                }
                break;
            case "menu URL item":
                websiteSettingsPage.waitTwoSeconds();
                while (websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsItemCheckbox)) {
                    logger.info("Menu item found, removing");
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsItemCheckbox);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsItemRemoveButton);
                }

                assertFalse(websiteSettingsPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsItemCheckbox));
                websiteSettingsPage.click(WebsiteSettingsPage.AddNavigationItemButton);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMenuTitleField);

                MenuTitleHOLDER = "Menu URL "+RandomStringUtils.randomNumeric(3);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuTitleField, MenuTitleHOLDER);

                MenuDomainHOLDER = RandomStringUtils.randomNumeric(20);
                MenuURLHOLDER = "https://"+MenuDomainHOLDER+".com";
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMenuItemSelector);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingMenuURLItemSelector);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuURLField, MenuURLHOLDER);
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);

                websiteSettingsPage.findOnPage(WebsiteSettingsPage.FooterSuccessMessage);
                break;
            case "menu Page item":
                logger.info("This will add a new item called 'PagePage'");
                websiteSettingsPage.waitTwoSeconds();
                while (websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsItemCheckbox)) {
                    logger.info("Menu item found, removing");
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsItemCheckbox);
                    websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsItemRemoveButton);
                }

                assertFalse(websiteSettingsPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsItemCheckbox));
                websiteSettingsPage.click(WebsiteSettingsPage.AddNavigationItemButton);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMenuTitleField);

                MenuTitleHOLDER = "PagePage";
                logger.info("Menu Page item is "+MenuTitleHOLDER);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsMenuTitleField, MenuTitleHOLDER);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMenuItemSelector);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingMenuPageItemSelector);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsContentPageLink);
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingContentLinkPagePageOption);
                websiteNavigationPage.click(WebsiteNavigationPage.WebsiteNavigationPopUpCreateButton);
                websiteSettingsPage.findOnPage(WebsiteSettingsPage.FooterSuccessMessage);
                break;
            case "club name":
                logger.info("Before change club name is "+websiteSettingsPage.getElementAttribute(WebsiteSettingsPage.WebsiteSettingsClubNameField,"value"));
                ClubNameHOLDER = "pennybridgerugby "+lorem.getFirstNameMale();
                logger.info("New BO club name is "+ClubNameHOLDER);
                websiteSettingsPage.clearInputFieldUsingBackSpaceKey(WebsiteSettingsPage.WebsiteSettingsClubNameField);
                websiteSettingsPage.click(ThemePage.ThemePageDescription);
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsClubNameField, ClubNameHOLDER);
                websiteSettingsPage.click(ThemePage.ThemePageDescription);
                websiteSettingsPage.click(WebsiteSettingsPage.UpdateTopButton);
                websiteSettingsPage.findOnPage(ThemePage.ThemeUpdatedSuccessMessage);
                websiteSettingsPage.click(ThemePage.NotificationCloseIcon);
                break;
            case "sponsor":
                websiteSettingsPage.findOnPage("//button[contains(.,'Add Sponsor add')]");
                websiteSettingsPage.waitTwoSeconds(); // for spomsors to load
                while (websiteSettingsPage.isElementPresent(WebsiteSettingsPage.DeleteIcon)) {
                    logger.info("A sponsor is present, removing");
                    websiteSettingsPage.click(WebsiteSettingsPage.DeleteIcon);
                }

                logger.info("Adding a sponsor");
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsAddButton);
                SponsorNameHOLDER = lorem.getStateFull();
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsSponsorNameField, SponsorNameHOLDER);
                SponsorURLHOLDER = "https://"+RandomStringUtils.randomNumeric(20)+".com";
                websiteSettingsPage.sendKeys(WebsiteSettingsPage.WebsiteSettingsSponsorLinkField, SponsorURLHOLDER);
                logger.info("Uploaded sponsorImage");

                logger.info("Click upload button");
                websiteSettingsPage.uploadFileUsingJSExec("sponsorImage.jpg", SponsorPage.SponsorsPageFilePath);
                websiteSettingsPage.click(BackofficePage.UploadButtonOnCropper);
                websiteSettingsPage.click(WebsiteSettingsPage.PublishButton);
                websiteSettingsPage.findOnPage(WebsiteSettingsPage.WebsiteSettingsSponsorSuccessMessage);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminChangeWebSettings!");
        }
    }

    @Then("their website {string} updates as expected")
    public void adminVerifiesWebSettings(String change_item) {
        websiteSettingsPage.goToClubURL("");
        websiteSettingsPage.acceptCookies();
        logger.info("Verifying changes to "+change_item);
        switch (change_item) {
            case "banner details":
                assertTrue(websiteSettingsPage.isElementPresent(String.format(WebsiteSettingsPage.WebsiteSettingsPageText, BannerTitleHOLDER)));
                assertTrue(websiteSettingsPage.isElementPresent(String.format(WebsiteSettingsPage.WebsiteSettingsPageText, BannerContentHOLDER)));
                logger.info("Verify 'View More' URL");
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonClubPage);
                assertTrue(driverManager.driver.getCurrentUrl().contains(BannerViewMoreHOLDER));
                break;
            case "disable View More button":
                assertFalse(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonClubPage));
                break;
            case "enable View More button":
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsViewMoreButtonClubPage);
                assertTrue(driverManager.driver.getCurrentUrl().contains("clubforce.com"));
                break;
            case "menu item instances":
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMenuMaxInstance1));
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMenuMaxInstance2));
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMenuMaxInstance3));
                assertFalse(websiteSettingsPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsMenuMaxInstance4));
                websiteSettingsPage.click(WebsiteSettingsPage.WebsiteSettingsMoreDropDown);
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMoreDropdownMenuMaxInstance1));
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMoreDropdownMenuMaxInstance2));
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMoreDropdownMenuMaxInstance3));
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMoreDropdownMenuMaxInstance4));
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.WebsiteSettingsMoreDropdownMenuMaxInstance5));
                break;
            case "menu URL item":
                assertTrue(websiteSettingsPage.isElementPresent(String.format(WebsiteSettingsPage.WebsiteSettingsPageText, MenuTitleHOLDER)));
                websiteSettingsPage.click("//span[contains(.,'"+MenuTitleHOLDER+"')]");
                websiteSettingsPage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains(MenuDomainHOLDER));
                break;
            case "menu Page item":
                websiteSettingsPage.click("//span[contains(.,'"+MenuTitleHOLDER+"')]");
                assertTrue(driverManager.driver.getCurrentUrl().contains("pages/pagepage"));
                break;
            case "club name":
                assertThat(websiteSettingsPage.getElementAttribute(ClubPage.ClubNameInFooter, "textContent"),containsString(ClubNameHOLDER));
                break;
            case "sponsor":
                assertTrue(websiteSettingsPage.isElementPresent("//img[contains(@alt,'"+SponsorNameHOLDER+"')]"));
                logger.info("Check image URL works");
                websiteSettingsPage.click("//img[contains(@alt,'"+SponsorNameHOLDER+"')]");
                websiteSettingsPage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains(SponsorURLHOLDER));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for storePageDetailsCheck!");
        }
    }

    @Then("admin changes logo")
    public void adminUpdatesClubLogo() throws Exception {
        if (!driverManager.agent.contains("lambdatest")) {
            throw new Exception("Profile not set to lambdatest - aborting test as visuals will be wrong.");
        } else {
            logger.info("Click upload button");
            themePage.dateUploadLogoImage();
            websiteSettingsPage.click(BackofficePage.UploadButton);
            websiteSettingsPage.waitOneSecond();
            websiteSettingsPage.click(WebsiteSettingsPage.UpdateTopButton);
            websiteSettingsPage.findOnPage(WebsiteSettingsPage.ThemeUpdatedSnackbar);

            logger.info("Refresh page and check logo is updated in BO");
            websiteSettingsPage.refreshPage();
            websiteSettingsPage.findOnPage(WebsiteSettingsPage.WebsiteSettingUploadImage);

            logger.info("Get image src and open it in new tab");
            String imageURL = websiteSettingsPage.getElementAttribute(ThemePage.LogoImageOnThemePage, "src");
            ((JavascriptExecutor) driverManager.driver).executeScript("window.open()");
            websiteSettingsPage.switchToBrowserTab(1);
            websiteSettingsPage.goTo_URL(imageURL);

            themePage.dateCompareLogoImages();

            driverManager.driver.close();
            websiteSettingsPage.switchToBrowserTab(0);
        }
    }

    @Then("logo is changed")
    public void clubLogoIsUpdated() throws Exception {
        websiteSettingsPage.refreshPage();
        logger.info("Get image src and open it in new tab");
        String imageURL = websiteSettingsPage.getElementAttribute(ClubPage.ClubWebsiteMenuClubLogo,"src");
        ((JavascriptExecutor) driverManager.driver).executeScript("window.open()");
        websiteSettingsPage.switchToBrowserTab(1);
        websiteSettingsPage.goTo_URL(imageURL);
        themePage.dateCompareLogoImages();
    }

    @Then("club homepage reacts accordingly to {string}")
    public void clubHomePageLogoFrameShow(String change_item) {
        websiteSettingsPage.waitThirtySeconds();
        switch (change_item) {
            case "hide":
                assertFalse(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.LogoFrameColour));
                break;
            case "show" :
                assertTrue(websiteSettingsPage.isElementPresent(WebsiteSettingsPage.LogoFrameColour));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminChangeLogoColourToggle!");
        }
    }
}
