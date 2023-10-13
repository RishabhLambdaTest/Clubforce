package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.Color;

import static org.testng.Assert.*;
import static org.testng.Assert.assertFalse;

public class ThemePageSteps extends WebDriverManager {
    private static final Logger logger = LogManager.getLogger();

    private final Color Blue = Color.fromString("rgba(13, 110, 253, 1)");
    private final Color Teal = Color.fromString("rgba(96, 205, 174, 1)");
    private final Color Red = Color.fromString("rgba(220, 53, 69, 1)");
    private final Color Purple = Color.fromString("rgba(111, 66, 193, 1)");
    private final Color Amber = Color.fromString("rgba(253, 126, 20, 1)");
    private final Color Green = Color.fromString("rgba(25, 135, 84, 1)");

    WebDriverManager driverManager;

    public ThemePageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.themePage = driverManager.themePage;
        this.websiteSettingsPage = driverManager.websiteSettingsPage;
    }

    @And("ClubAdmin sets up theme page for club")
    public void setupThemePageForNewClub(){
        logger.info("Check that we are on the theme page");
        themePage.findOnPage(ThemePage.ThemeHeading);
        verifyThemePageElements();

        logger.info("Select purple from primary colour dropdown field");
        themePage.click(ThemePage.PrimaryColourFieldDropDownFieldDefault);
        themePage.click(ThemePage.ThemeColourPurple);
        themePage.click(ThemePage.ColourSchemeHeading);

        logger.info("Select amber from primary colour dropdown field");
        themePage.click(ThemePage.SecondaryColourFieldDropDownFieldDefault);
        themePage.click(ThemePage.ThemeColourAmber);
        themePage.click(ThemePage.ColourSchemeHeading);

        logger.info("Uploaded logoImage");
        themePage.dateUploadLogoImage();
        themePage.click(BackofficePage.UploadButtonOnCropper);
        themePage.click(ThemePage.ThemePageTopUpdateButton);
        themePage.findOnPage(ThemePage.ThemeUpdatedSuccessMessage);
        themePage.click(ThemePage.NotificationCloseIcon);
        themePage.findOnPage(BackofficePage.LeftNavClubLogoImage);
        themePage.findOnPage(BackofficePage.ClubLogoImage);
        logger.info("Club colours are purple (primary colour) and amber (secondary colour)");
    }

    @Then("{string} changes primary club colour to {string}")
    public void adminChangesPrimaryClubColour(String user,String colour) {
        logger.info("User changes primary colour on theme page");
        themePage.findOnPage(ThemePage.ColourSchemeHeading);

        if (user.equals("SuperUser")) {
            themePage.click(ThemePage.SUThemeStepPrimaryColourDropDownField);
            themePage.waitOneSecond();
        } else {
            themePage.click(ThemePage.PrimaryColourFieldDropDownField);
        }

        switch (colour) {
            case "Blue":
                themePage.click(ThemePage.ThemeColourBlue);
                themePage.click(ThemePage.ColourSchemeHeading);
                break;
            case "Red":
                themePage.click(ThemePage.ThemeColourRed);
                themePage.click(ThemePage.ColourSchemeHeading);
                break;
            case "Amber":
                themePage.click(ThemePage.ThemeColourAmber);
                themePage.click(ThemePage.ColourSchemeHeading);
                break;
            default:
        }

        if(themePage.isElementDisplayed(ThemePage.ThemePageUpdateTopButton)){
            themePage.click(ThemePage.ThemePageTopUpdateButton);
        }else{
            themePage.click(ThemePage.SUThemePageUpdateButton);
        }

        themePage.findOnPage(ThemePage.ThemeUpdatedSuccessMessage);
        themePage.click(ThemePage.NotificationCloseIcon);
        logger.info("Primary colour has been changed on theme page");
    }

    @Then("{string} changes secondary club colour to {string}")
    public void adminChangesSecondaryClubColour(String user,String colour) {
        logger.info("User changes secondary colour on theme page");
        themePage.findOnPage(ThemePage.ColourSchemeHeading);

        if (user.equals("SuperUser")) {
            themePage.click(ThemePage.SUThemeStepSecondaryColourDropDownField);
        } else {
            themePage.click(ThemePage.TealDefaultSecondaryColourDropdownField);
        }

        switch (colour) {
            case "Teal":
                themePage.click(ThemePage.ThemeColourTeal);
                themePage.click(ThemePage.ColourSchemeHeading);
                break;
            case "Purple":
                themePage.click(ThemePage.ThemeColourPurple);
                themePage.click(ThemePage.ColourSchemeHeading);
                break;
            case "Green":
                themePage.click(ThemePage.ThemeColourGreen);
                themePage.click(ThemePage.ColourSchemeHeading);
                break;
            default:
        }

        if(themePage.isElementDisplayed(ThemePage.ThemePageUpdateTopButton)){
            themePage.click(ThemePage.ThemePageTopUpdateButton);
        }else{
            themePage.click(ThemePage.SUThemePageUpdateButton);
        }

        themePage.findOnPage(ThemePage.ThemeUpdatedSuccessMessage);
        themePage.click(ThemePage.NotificationCloseIcon);
        logger.info("Secondary colour has been changed on theme page");
    }

    @Then("dashboard displays {string} in first circle")
    public void checkClubColoursOnDashboard(String colour) {
        Color colourCircle1 = Color.fromString(themePage.findOnPage(BackofficeDashboardPage.ThemeDashboardFirstCircle).getCssValue("background-color"));
        logger.info(colourCircle1);
        switch (colour) {
            case "Blue":
                assertEquals(colourCircle1,Blue);
                break;
            case "Red":
                assertEquals(colourCircle1,Red);
                break;
            case "Amber":
                assertEquals(colourCircle1,Amber);
                break;
            default:
        }
    }
    @Then("dashboard displays {string} in second circle")
    public void checkClubSecondaryColoursOnDashboard(String colour) {
        Color colourCircle2 = Color.fromString(themePage.findOnPage(BackofficeDashboardPage.ThemeDashboardSecondCircle).getCssValue("background-color"));
        logger.info(colourCircle2);
        switch (colour) {
            case "Blue":
                assertEquals(colourCircle2,Teal);
                break;
            case "Purple":
                assertEquals(colourCircle2,Purple);
                break;
            case "Green":
                assertEquals(colourCircle2,Green);
                break;
            default:
        }
    }

    @Then("{string} and {string} are displayed on website")
    public void checkPrimaryColourIsDisplayed(String colour1, String colour2) {
        Color articleColour = Color.fromString(themePage.findOnPage("(//mat-card[contains(@itemtype,'https://schema.org/Article')])[1]").getCssValue("background-color"));
        logger.info(articleColour);

        Color loadMoreButtonColour = Color.fromString(themePage.findOnPage(ArticlePage.ArticlePageLoadMoreButton).getCssValue("background-color"));
        logger.info(loadMoreButtonColour);

        switch (colour1) {
            case "Blue":
                assertEquals(articleColour,Blue);
                assertEquals(loadMoreButtonColour,Blue);
                break;
            case "Red":
                assertEquals(articleColour,Red);
                assertEquals(loadMoreButtonColour,Red);
                break;
            case "Amber":
                assertEquals(articleColour,Amber);
                assertEquals(loadMoreButtonColour,Amber);
                break;
            default:
        }
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoMenuItem,5);
        themePage.click(LottoPage.LottoMenuItem);
        themePage.click(LottoPage.LottoConfirmButton);
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.FirstLottoPicksCircle, 5);
        themePage.click(LottoPage.FirstLottoPicksCircle);
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoPickNumber1,5);
        themePage.click(LottoPage.LottoPickNumber1);
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoPickNumber2,5);
        themePage.click(LottoPage.LottoPickNumber2);
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoPickNumber3,5);
        themePage.click(LottoPage.LottoPickNumber3);
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoPickNumber4,5);
        themePage.click(LottoPage.LottoPickNumber4);
        themePage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoPickNumber5,5);
        themePage.click(LottoPage.LottoPickNumber5);
        themePage.click(LottoPage.SelectButton);

        Color lottoBallColour = Color.fromString(websiteSettingsPage.findOnPage(LottoPage.FirstLottoBallDisplayingNumber1).getCssValue("background-color"));
        logger.info(lottoBallColour);

        switch (colour2) {
            case "Green":
                assertEquals(lottoBallColour,Green);
                break;
            case "Teal":
                assertEquals(lottoBallColour,Teal);
                break;
            case "Purple":
                assertEquals(lottoBallColour,Purple);
                break;
            default:
        }
    }

    @When("admin set address toggle to {string}")
    public void adminSetAddressDisplay(String display_option) {
        themePage.findOnPage(ThemePage.ThemeHeading);
        switch (display_option) {
            case "hide":
                if (themePage.isElementPresent(ThemePage.ShowAddressToggleOFF)) {
                    logger.warn("Show address toggle was already OFF, test continue");
                } else {
                    themePage.click(ThemePage.ThemeAddressToggle);
                    themePage.waitHalfSecond();
                    themePage.click(ThemePage.ThemePageUpdateTopButton);
                    themePage.waitForElementDisplayedByXpathWithTimeout(ThemePage.ThemeUpdatedSuccessMessage, 10);
                    themePage.click(ThemePage.NotificationCloseIcon);
                    logger.info("Show address set to OFF");
                }
                assertFalse(themePage.isElementPresent(ThemePage.SearchForLocationField), "Search field is displayed in BO");
                assertFalse( themePage.isElementPresent(ThemePage.ThemePageMap), "Map is displayed in BO");
                break;
            case "show":
                if (themePage.isElementPresent(ThemePage.ShowAddressToggleON)) {
                    logger.warn("Show address toggle was already ON, test continue");
                } else {
                    themePage.click(ThemePage.ThemeAddressToggle);
                    themePage.waitHalfSecond();
                    themePage.click(ThemePage.ThemePageUpdateTopButton);
                    themePage.waitFiveSeconds();
                    themePage.waitForElementDisplayedByXpathWithTimeout(ThemePage.ThemeUpdatedSuccessMessage, 10);
                    themePage.click(ThemePage.NotificationCloseIcon);
                    logger.info("Show address set to ON");
                }
                themePage.waitForElementDisplayedByXpathWithTimeout(ThemePage.ThemeHeading,15);
                logger.info("On the Theme page a search box and a map should now show");
                assertTrue(themePage.isElementPresent(ThemePage.SearchForLocationField), "Search field is not displayed in BO");
                assertTrue(themePage.isElementPresent(ThemePage.ThemePageMap), "Map is not displayed in BO");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminSetAddressDisplay!");
        }
        logger.info("Toggle set");
    }

    public void verifyThemePageElements(){
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemeHeading));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemeHeading));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemePageDescription));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemePageClubNameInformation));
        assertTrue(themePage.isElementDisplayed(ThemePage.ColourSchemeHeading));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemePageColourSchemeInformation));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemePagePrimaryColourInformation));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemePageSecondaryColourInformation));
        assertTrue(themePage.isElementDisplayed(ThemePage.AddressSettingsHeadingThemePage));
        assertTrue(themePage.isElementDisplayed(ThemePage.AddressSettingsInformation));
        assertTrue(themePage.isElementDisplayed(ThemePage.ThemePageLogoHeading));
        assertTrue(themePage.isElementDisplayed(ThemePage.LogoInformation));
    }
}
