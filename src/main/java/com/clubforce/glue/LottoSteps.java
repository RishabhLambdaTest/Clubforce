package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class LottoSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();
    public static String LottoNameSuffixHOLDER;
    public static String LottoTotalPriceHOLDER;
    private String cutOffDate;
    private String cutOffDay;
    private int day;
    private int nextDay;
    public static int totalDraws;

    WebDriverManager driverManager;

    public LottoSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.lottoPage = driverManager.lottoPage;
        this.backofficePage = driverManager.backofficePage;
        this.loginPage = driverManager.loginPage;
    }

    @Then("Lotto is {string} for club")
    public void suEnablesClubLotto(String display_option) {
        moveToStep4Features();
        switch (display_option) {
            case "disabled":
                if (backofficePage.isElementPresent(BackofficePage.SULottoFeatureToggleOFF)) {
                    logger.warn("Lotto toggle was already OFF, test continue");
                } else {
                    disableLottoFeatureToggle();
                    logger.info("Lotto set to OFF");
                }
                break;
            case "enabled":
                if (backofficePage.isElementPresent(BackofficePage.SULottoFeatureToggleON)) {
                    logger.warn("Lotto toggle was already ON, test continue");
                } else {
                    enableLottoFeatureToggle();
                    logger.info("Lotto set to ON");
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for suEnablesClubLotto!");
        }
    }

    @Then("Lotto option is {string}")
    public void lottoVisibilityForClubWithLottoAlreadySetUp(String display_option) {
        backofficePage.findOnPage(BackofficePage.DashboardHeading);
        backofficePage.waitTwoSeconds();
        switch (display_option) {
            case "not visible":
                assertFalse(backofficePage.isElementDisplayed(BackofficeDashboardPage.ManageLottoDashboardText));
                assertFalse(backofficePage.isElementDisplayed(BackofficeDashboardPage.ManageDrawsDashboardButton));
                assertFalse(backofficePage.isElementPresent(BackofficePage.LottoLeftNav));
                break;
            case "visible":
                backofficePage.refreshPage();
                backofficePage.waitTenSeconds();
                assertTrue(backofficePage.isElementDisplayed(BackofficeDashboardPage.ManageLottoDashboardText));
                assertTrue(backofficePage.isElementDisplayed(BackofficeDashboardPage.ManageDrawsDashboardButton));
                if (driverManager.agent.contains("appium")) {
                    backofficePage.click(BackofficePage.BackofficeHamburger);
                }
                assertTrue(backofficePage.isElementPresent(BackofficePage.LottoLeftNav));
                backofficePage.click(BackofficePage.LottoLeftNav);
                backofficePage.waitOneSecond();
                assertTrue(backofficePage.isElementDisplayed(BackofficePage.LottoDrawsTile));
                assertTrue(backofficePage.isElementPresent(BackofficePage.LottoDetailsLeftNav));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for lottoVisibilityForClubWithLottoAlreadySetUp!");
        }
    }

    @Then("Lotto option is {string} for {string}")
    public void lottoVisibilityForClubWithoutLottoSetUp(String display_option, String club) {
        backofficePage.findOnPage(BackofficePage.DashboardHeading);
        backofficePage.refreshPage();
        backofficePage.findOnPage(BackofficePage.DashboardHeading);
        if (driverManager.agent.contains("appium")) {
            backofficePage.click(BackofficePage.BackofficeHamburger);
        }
        backofficePage.waitTwoSeconds();
        switch (display_option) {
            case "not visible":
                assertFalse(backofficePage.isElementPresent(BackofficePage.LottoLeftNav));
                assertFalse(backofficePage.isElementPresent(BackofficePage.SetUpLottoLeftNav));
                break;
            case "visible":
                assertTrue(backofficePage.isElementPresent(BackofficePage.LottoLeftNav));
                backofficePage.click(BackofficePage.LottoLeftNav);
                assertTrue(backofficePage.isElementPresent(BackofficePage.SetUpLottoLeftNav));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for lottoVisibilityForClubWithoutLottoSetUp!");
        }
    }

    @Then("they can update the lotto page")
    public void lottoPageIsUpdated() {
        logger.info("Check on /products");
        lottoPage.goToBackofficeURL("/products");
        lottoPage.sendKeys("//input[@id='mat-input-0']", LottoNameSuffixHOLDER);
        lottoPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);

        logger.info("Check product column is Lotto");
        lottoPage.waitOneSecond();
        assertTrue(lottoPage.isElementDisplayed("(//td[@role='gridcell'][contains(.,'Lotto')])[1]"));

        logger.info("Check update date is empty");
        assertThat("Date not as expected!", lottoPage.getElementAttribute("(//td[@role='gridcell'])[4]", "textContent"), is( ""));

        lottoPage.click("//td[@role='gridcell'][contains(.,'"+LottoNameSuffixHOLDER+"')]");
        lottoPage.click(LottoPage.NextLottoSettingsBottomButton);
        lottoPage.waitTwoSeconds();
        lottoPage.click(LottoPage.NextLottoDiscountBottomButton);
        lottoPage.waitTwoSeconds();
        lottoPage.click(LottoPage.SaveAndPublishLottoButton);

        lottoPage.findOnPage("//h2[@data-test='confirm-dialog.page'][contains(.,'Confirm Ticket Options')]");
        lottoPage.findOnPage("//mat-hint[contains(.,'Once Saved and Published your lotto will be publicly available to play.')]");
        lottoPage.click("//span[@class='mat-button-wrapper'][contains(.,'Save and Publish Lotto')]");
        lottoPage.findOnPage("//span[contains(.,'Successful update.')]");

        logger.info("Go and check updated date on /products");
        lottoPage.goToBackofficeURL("/products");
        lottoPage.sendKeys("//input[@id='mat-input-0']", LottoNameSuffixHOLDER);
        lottoPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);

        logger.info("Check update date is populated");
        String day = SeleniumUtilities.getDateTimeFormat("dd");
        switch (day) {
            case "01":
            case "02":
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                assertThat("Date not as expected!", lottoPage.getElementAttribute("(//td[@role='gridcell'])[4]",
                        "textContent"), is(SeleniumUtilities.getDateTimeFormat("MMM d, yyyy")));
                break;
            default:
                assertThat("Date not as expected!", lottoPage.getElementAttribute("(//td[@role='gridcell'])[4]",
                        "textContent"), is(SeleniumUtilities.getDateTimeFormat("MMM dd, yyyy")));
        }
    }

    @And("they follow the Lotto link for {string} club")
    public void clickLottoLinkClubPageHeader(String country) {
        if (LottoNameSuffixHOLDER == null) {
            LottoNameSuffixHOLDER = "Lotto";
            logger.info("No Lotto details given from previous tests, setting name to: "+LottoNameSuffixHOLDER);
        }

        if (driverManager.agent.contains("appium")) {
            lottoPage.click("//span[contains(@class,'navbar-toggler-icon')]");
            lottoPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Lotto')]", 15);
            lottoPage.click("//*[contains(text(),'Lotto')]");
        } else {
            logger.info("On desktop club page, clicking Lotto header link named: "+LottoNameSuffixHOLDER);
            lottoPage.click("//span[@class='mat-button-wrapper'][contains(.,'Lotto')]");
        }
        lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.AgeVerificationPopUp, 15);

        logger.info("Confirming we are over 18");
        lottoPage.click("//button[contains(.,'Confirm')]");
        lottoPage.verifyLottoPurchasePageItems();

        logger.info("Check that Gamble Aware notification is displayed for UK clubs");
        switch (country) {
            case "Ireland":
                assertFalse(lottoPage.isElementDisplayed("//p[contains(.,'For customers in the United Kingdom, " +
                        "Clubforce is licensed and regulated by the  Gambling Commission (Licence number 000-057100-R-333133-001)  " +
                        "GambleAware')]"));
                break;
            case "England":
            case "Wales":
            case "Scotland":
                assertTrue(lottoPage.isElementDisplayed("//p[contains(.,'For customers in the United Kingdom, " +
                        "Clubforce is licensed and regulated by the  Gambling Commission (Licence number 000-057100-R-333133-001)  " +
                        "GambleAware')]"));
                break;
            default:
        }
    }

    @And("they can add {string} Lotto to cart {string} for {string} club")
    public void addLottoProductToCart(String winner, String autoRenewal, String clubName) {
//        lottoPage.userClosesMobileMenuHeader();
        lottoPage.acceptCookies();

        if (clubName.equals("Durban")) {
            logger.info("Check that ticket options/lotto balls are already displayed");
            assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[1]"));
            assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[2]"));
            assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[3]"));

            logger.info("Opening ticket options drop down");
            if (driverManager.agent.contains("appium")) {
                lottoPage.click("(//*[contains(text(),'3 for fiver')])[1]");
            } else {
                lottoPage.click("(//*[contains(text(),'3 for fiver')])[2]");
            }
            logger.info("Selecting 5 for a tenner");
            lottoPage.click("//span[@class='mat-option-text'][contains(.,'5 for a tenner')]");

            logger.info("Check that ticket options/lotto balls are displayed for 5 for a tenner");
            assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[1]"));
            assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[2]"));
            assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[3]"));

            logger.info("Opening ticket options drop down again");
            lottoPage.click("(//span[contains(.,'5 for a tenner')])[2]");

            logger.info("Selecting 3 for a fiver ticket option");
            lottoPage.click("//span[@class='mat-option-text'][contains(.,'3 for fiver')]");
        }

        switch (autoRenewal) {
            case "without autoRenewal":
                logger.info("Verifying items on Club Lotto purchase page");
                lottoPage.verifyLottoPurchasePageItems();

                logger.info("Checking auto renewal is set to ON");
                assertThat("Auto renewal is NOT set to Active!", lottoPage.isElementDisplayed(LottoPage.AutoRenewalCheckBoxChecked));

                logger.info("Checking Add to Cart button is set to Inactive");
                assertThat("Add to Cart button is NOT set to Inactive!", lottoPage.getElementAttribute(LottoPage.AddToCartButton, "disabled"), containsString("true"));

                logger.info("Select option on Lotto purchase page");

                if (winner.equals("winning")) {
                    logger.info("Choose numbers 1 to 5 and two QPs");
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.FirstLottoPicksCircle);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.LottoPickNumber1);
                    lottoPage.click(LottoPage.LottoPickNumber2);
                    lottoPage.click(LottoPage.LottoPickNumber3);
                    lottoPage.click(LottoPage.LottoPickNumber4);
                    lottoPage.click(LottoPage.LottoPickNumber5);

                    lottoPage.click(LottoPage.SelectButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.click(LottoPage.QuickPickButton);
                } else {
                    logger.info("Choose three quick picks");
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                }

                logger.info("Verifying QP buttons have changed");
                assertTrue(lottoPage.isElementDisplayed(LottoPage.FirstClearButton));
                assertTrue(lottoPage.isElementDisplayed(LottoPage.SecondClearButton));

                logger.info("Clicking Add to Cart button expecting auto renewal warning");
                lottoPage.waitTwoSeconds();
                lottoPage.scrollPageToBottom();
                lottoPage.click(LottoPage.AddToCartButton);
                lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.RenewOnceExpiredDialog, 10);
                lottoPage.click(LottoPage.NoButton);

                logger.info("Turn off auto renewal");
                lottoPage.click(LottoPage.AutoRenewCheckBox);
                assertThat("Auto renewal was NOT disabled!", lottoPage.isElementDisplayed(LottoPage.AutoRenewCheckBoxUnChecked));

                logger.info("Move total price to LottoTotalPriceHOLDER");
                lottoPage.findOnPage("(//span[contains(@class,'text-align-right')])[5]");
                String lottoPrice = lottoPage.getElementAttribute("(//span[contains(@class,'text-align-right')])[5]", "textContent");
                LottoTotalPriceHOLDER = lottoPrice.replaceAll(" ", "");
                logger.info("Total price for lotto ticket : " + LottoTotalPriceHOLDER + ".");

                logger.info("Clicking Add to Cart button again, without auto renewal");
                lottoPage.click(LottoPage.AddToCartButton);
                break;
            case "with autoRenewal":
                logger.info("Clicking Add to Cart button again, with auto renewal");
                logger.info("Verifying items on Club Lotto purchase page");
                lottoPage.verifyLottoPurchasePageItems();
                logger.info("Checking auto renewal is set to ON");
                assertThat("Auto renewal is NOT set to Active!", lottoPage.isElementDisplayed(LottoPage.AutoRenewalCheckBoxChecked));

                logger.info("Checking Add to Cart button is set to Inactive");
                assertThat("Add to Cart button is NOT set to Inactive!", lottoPage.getElementAttribute(LottoPage.AddToCartButton, "disabled"), containsString("true"));

                if (winner.equals("winning")) {
                    logger.info("Choose 1,2,3 and two QPs");
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.FirstLottoPicksCircle);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.LottoPickNumber1);
                    lottoPage.click(LottoPage.LottoPickNumber2);
                    lottoPage.click(LottoPage.LottoPickNumber3);
                    lottoPage.click(LottoPage.SelectButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.waitOneSecond();
                } else {
                    logger.info("Choose three quick picks");
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                    lottoPage.waitOneSecond();
                    lottoPage.click(LottoPage.QuickPickButton);
                }

                logger.info("Verifying QP buttons have changed");
                lottoPage.waitTwoSeconds();
                assertTrue(lottoPage.isElementDisplayed(LottoPage.FirstClearButton));
                assertTrue(lottoPage.isElementDisplayed(LottoPage.SecondClearButton));
                assertTrue(lottoPage.isElementDisplayed(LottoPage.ThirdClearButton));

                lottoPage.scrollPageToBottom();
                logger.info("Clicking Add to Cart button expecting auto renewal warning");
                lottoPage.waitOneSecond();
                lottoPage.click(LottoPage.AddToCartButton);
                lottoPage.findOnPage(LottoPage.RenewOnceExpiredDialog);
                lottoPage.click(LottoPage.YesButton);

                logger.info("Move total price to LottoTotalPriceHOLDER");
                String lottoPriceAutoRenewal = lottoPage.getElementAttribute("(//span[contains(@class,'text-align-right')])[5]", "textContent");
                LottoTotalPriceHOLDER = lottoPriceAutoRenewal.replaceAll(" ", "");                break;
            default:
                throw new NotFoundException("For some reason there is no case for addLottoProductToCart!");
        }
        if ((driverManager.agent.contains("appium"))) {
            logger.info("Clicking Add to Cart button again, without auto renewal");
        } else {
            lottoPage.waitUntilElementInvisible(LottoPage.SuccessfullyAddedToCartNotification,6);
        }
    }

    @And("they can set Lotto winner details")
    public void adminSetsLottoWinnerDetails() {
        logger.info("Checking details on Manage Lotto Draws page");
        backofficePage.findOnPage("//div[@fxflex='50'][contains(.,'Draw Published')]");
        backofficePage.findOnPage("//span[@fxlayoutalign='center center'][contains(.,'checked Completed')]");
        backofficePage.findOnPage("//div[@fxflex='50'][contains(.,'Results Published')]");
        backofficePage.findOnPage("(//span[@class='ng-star-inserted'][contains(.,'Pending')])[1]");
        backofficePage.findOnPage("//div[@fxflex='50'][contains(.,'Players Notified')]");
        backofficePage.findOnPage("(//span[@class='ng-star-inserted'][contains(.,'Pending')])[2]");

        logger.info("Click into draw");
        lottoPage.click("//a[contains(.,'Jul 27, 2021')]");
        lottoPage.findOnPage("//div[contains(.,'Pick Winning Numbers')]");
        assertThat(lottoPage.getElementAttribute("//mat-hint[contains(@class,'mat-hint')]", "textContent"),containsString("Enter numbers manually or 'Quick Pick' to automatically generate winning numbers"));

        logger.info("Choose winning letters");
        lottoPage.click("//span[@class='mat-button-wrapper'][contains(.,'Enter Letters')]");

        logger.info("First check I can select, and unselect a letter");
        lottoPage.waitTwoSeconds();
        lottoPage.click("//button[contains(.,'A')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'1/5 Selected')]");
        lottoPage.click("//button[contains(.,'A')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'0/5 Selected')]");
        lottoPage.waitOneSecond();

        logger.info("Choose winning letters");
        lottoPage.click("//button[contains(.,'A')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'1/5 Selected')]");
        lottoPage.waitOneSecond();
        lottoPage.click("//button[contains(.,'B')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'2/5 Selected')]");
        lottoPage.waitOneSecond();
        lottoPage.click("//button[contains(.,'T')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'3/5 Selected')]");
        lottoPage.waitOneSecond();
        lottoPage.click("//button[contains(.,'Y')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'4/5 Selected')]");
        lottoPage.waitOneSecond();
        lottoPage.click("//button[contains(.,'V')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'5/5 Selected')]");
        lottoPage.waitOneSecond();
        logger.info("Checking I cannot add more letters and close modal");
        lottoPage.click("//button[contains(.,'X')]");
        lottoPage.findOnPage("//section[@fxlayoutalign='end center'][contains(.,'5/5 Selected')]");
        lottoPage.click("(//span[contains(.,'Enter Letters')])[2]");

        logger.info("Check numbers display on page");
        lottoPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'A')]");
        lottoPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'B')]");
        lottoPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'T')]");
        lottoPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'Y')]");
        lottoPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'V')]");

        logger.info("Go to List of winners page");
        lottoPage.click(LottoPage.LottoWinningPicksButton);
    }

    @And("they can open a past draw")
    public void adminOpenPastDraws() {
        logger.info("Admin opens past Lotto draws");
        lottoPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);
        lottoPage.click(LottoPage.LottoPastDrawRow);
        lottoPage.findOnPage(LottoPage.LottoWinningPicksButton);
    }

    @Then("they can publish lotto for the current draw")
    public void publishLottoForCurrentDraw() {
        logger.info("Check if current draw is published");
        backofficePage.findOnPage(BackofficePage.ItemsPerPageText);

        if (!lottoPage.isElementDisplayed("//*[contains(text(),'1000.00')]")) {
            lottoPage.click(LottoPage.LottoDrawsEditIcon);
            lottoPage.sendKeys(LottoPage.JackpotAmountField, "1000");
            lottoPage.click(LottoPage.PrizesHeading);
        }

        logger.info("Click save button");
        lottoPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
        lottoPage.findOnPage("//h4[contains(.,'Lottery successfully updated!')]");
        lottoPage.click("//mat-icon[contains(.,'close')]");

        assertThat(lottoPage.getElementAttribute("//input[contains(@placeholder,'Enter Jackpot Amount')]", "value"),containsString("1000"));

        if (lottoPage.isElementDisplayed("(//input[contains(@data-placeholder,'Enter Match Amount')])[1]")) {
            if (!lottoPage.isElementDisplayed("//*[contains(text(),'400.00')]")) {
                lottoPage.click(LottoPage.LottoDrawsEditIcon);
                lottoPage.sendKeys("(//input[contains(@data-placeholder,'Enter Match Amount')])[1]", "400");
                lottoPage.click(LottoPage.PrizesHeading);
            }
        }

        logger.info("Click save button");
        lottoPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
        lottoPage.findOnPage("//h4[contains(.,'Lottery successfully updated!')]");
        lottoPage.click("//mat-icon[contains(.,'close')]");

        if (lottoPage.isElementDisplayed("(//input[contains(@data-placeholder,'Enter Match Amount')])[2]")) {
            if (!lottoPage.isElementDisplayed("//*[contains(text(),'300.00')]")) {
                lottoPage.click(LottoPage.LottoDrawsEditIcon);
                lottoPage.sendKeys("(//input[contains(@data-placeholder,'Enter Match Amount')])[2]", "300");
                lottoPage.click(LottoPage.PrizesHeading);
            }
        }

        logger.info("Click save button");
        lottoPage.click(WebsiteSettingsPage.WebsiteSettingsSaveButton);
        lottoPage.findOnPage("//h4[contains(.,'Lottery successfully updated!')]");
        lottoPage.click("//mat-icon[contains(.,'close')]");

        assertThat(lottoPage.getElementAttribute("//input[contains(@placeholder,'Enter Jackpot Amount')]", "value"),containsString("1000.00"));

        if (lottoPage.isElementDisplayed("(//input[contains(@data-placeholder,'Enter Match Amount')])[1]")){
            assertThat(lottoPage.getElementAttribute("(//input[contains(@data-placeholder,'Enter Match Amount')])[1]", "value"),containsString("400.00"));
        }
        if (lottoPage.isElementDisplayed("(//input[contains(@data-placeholder,'Enter Match Amount')])[2]")){
            assertThat(lottoPage.getElementAttribute("(//input[contains(@data-placeholder,'Enter Match Amount')])[2]", "value"),containsString("300.00"));
        }
    }

    @And("PlaySlip number have changed to {string}")
    public void PlaySlipNumberHasChanged(String numbers) {
        logger.info("Checking PlaySlip numbers");
        if (numbers.equals("1")) {
            lottoPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'View "+numbers+" playslips')]");
        } else {
            throw new NotFoundException("For some reason there is no case for adminPicksLottoWinner!");
        }
    }

    @And("they pick {string} for the daily Lotto")
    public void adminPicksLottoWinner(String winner) {
        switch (winner) {
            case "a lotto winner":

                logger.info("Check if winning numbers notification is displayed");
                lottoPage.waitTwoSeconds();
                if (lottoPage.isElementDisplayed("(//b[contains(.,'Go to draw')])[1]")) {
                    lottoPage.click("(//b[contains(.,'Go to draw')])[1]");
                    lottoPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Date & Time')]",30);
                    if (lottoPage.isElementDisplayed("//div[@class='d-inline-flex align-items-center'][contains(.,'info Please update your jackpot amount before you continue')]")) {
                        lottoPage.sendKeys("//input[contains(@data-placeholder,'Enter Jackpot Amount')]","100");
                    }
                    lottoPage.click("//button[contains(.,'Winning Picks arrow_forward')]");

                    logger.info("Check if winning numbers have been selected");
                    lottoPage.waitFiveSeconds();
                    if (lottoPage.isElementDisplayed("//div[@class='ng-star-inserted'][contains(.,'1')]")) {
                        logger.warn("Winning numbers have already been selected");
                        lottoPage.click("//button[contains(.,'List of Winners arrow_forward')]");
                    } else {
                        lottoPage.click("(//button[@data-test='picks-step.pickButton'])[1]");
                        lottoPage.waitFiveSeconds();

                        logger.info("Selecting ball 1");
                        lottoPage.click("(//button[contains(.,'1')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'1/5')]"));

                        logger.info("Selecting ball 2");
                        lottoPage.click("(//button[contains(.,'2')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'2/5')]"));

                        logger.info("Deselecting ball 2");
                        lottoPage.click("(//button[contains(.,'2')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'1/5')]"));

                        logger.info("Selecting ball 2 again");
                        lottoPage.click("(//button[contains(.,'2')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'2/5')]"));

                        logger.info("Selecting ball 3");
                        lottoPage.click("(//button[contains(.,'3')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'3/5')]"));

                        logger.info("Selecting ball 4");
                        lottoPage.click("(//button[contains(.,'4')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'4/5')]"));

                        logger.info("Selecting ball 5");
                        lottoPage.click("(//button[contains(.,'5')])[1]");
                        lottoPage.waitTwoSeconds();
                        assertTrue(lottoPage.isElementDisplayed("//*[contains(.,'5/5')]"));

                        logger.info("Clicking done button");
                        lottoPage.click("//button[contains(.,'Done')]");
                        lottoPage.waitFiveSeconds();

                        logger.info("Verify on Winning numbers page display 1, 2, 3");
                        assertTrue(lottoPage.isElementDisplayed("//button[@data-test='picks-step.pickButton'][contains(.,'1')]"));
                        assertTrue(lottoPage.isElementDisplayed("//button[@data-test='picks-step.pickButton'][contains(.,'2')]"));
                        assertTrue(lottoPage.isElementDisplayed("//button[@data-test='picks-step.pickButton'][contains(.,'3')]"));
                        assertTrue(lottoPage.isElementDisplayed("//button[@data-test='picks-step.pickButton'][contains(.,'4')]"));
                        assertTrue(lottoPage.isElementDisplayed("//button[@data-test='picks-step.pickButton'][contains(.,'5')]"));

                        logger.info("Proceeding to winners page");
                        lottoPage.click("//button[contains(.,'List of Winners arrow_forward')]");
                        lottoPage.waitTenSeconds();
                        if (lottoPage.isElementDisplayed("//span[contains(.,'Processing resultsRefresh the page in a few moments')]")) {
                            logger.info("Draws are taking time to process, so waiting 6 minutes");
                            lottoPage.click("//button[contains(.,'Back to draws')]");
                            lottoPage.waitThirtySeconds();
                            lottoPage.waitThirtySeconds();
                            lottoPage.refreshPage();
                            lottoPage.waitThirtySeconds();
                            lottoPage.waitThirtySeconds();
                            lottoPage.refreshPage();
                            lottoPage.waitThirtySeconds();
                            lottoPage.waitThirtySeconds();
                            lottoPage.refreshPage();
                            lottoPage.waitThirtySeconds();
                            lottoPage.waitThirtySeconds();
                            lottoPage.refreshPage();
                            lottoPage.waitThirtySeconds();
                            lottoPage.waitThirtySeconds();
                            lottoPage.refreshPage();
                            lottoPage.waitThirtySeconds();
                            lottoPage.waitThirtySeconds();
                            logger.info("Wait is complete, going back to winners page");
                            lottoPage.click("(//b[contains(.,'Go to draw')])[1]");
                            lottoPage.click("//button[contains(.,'Winning Picks arrow_forward')]");
                            lottoPage.click("//button[contains(.,'List of Winners arrow_forward')]");
                        }
                    }
                } else {
                    logger.info("Open last past draw");
                    lottoPage.click("(//button[contains(.,'More details')])[1]");
                    lottoPage.waitTwoSeconds();
                    lottoPage.click("//button[contains(.,'Winning Picks arrow_forward')]");

                    logger.info("Check if winning numbers have been selected");
                    lottoPage.waitTwoSeconds();
                    if (lottoPage.isElementDisplayed("//span[@class='mat-button-wrapper'][contains(.,'1')]")) {
                        logger.warn("Winning numbers have already been selected");
                        lottoPage.click("//button[contains(.,'List of Winners arrow_forward')]");
                    }
                }
                break;
            case "a lucky dip winner":
                lottoPage.waitFiveSeconds();
                if (lottoPage.isElementDisplayed("//h2[contains(.,'Lucky Dip 2')]")) {
                    logger.warn("Lucky dip winner has already been chosen");
                    lottoPage.click("//button[contains(.,'Finalize draw')]");
                } else {
                    logger.info("Setting " + winner);
                    lottoPage.findOnPage("//h2[contains(.,'Jackpot')]");
                    assertTrue(lottoPage.isElementDisplayed("//div[@class='fw-bold'][contains(.,'Desmond15 Logan')]"));
                    assertTrue(lottoPage.isElementDisplayed("//span[contains(.,'01')]"));
                    assertTrue(lottoPage.isElementDisplayed("//span[contains(.,'02')]"));
                    assertTrue(lottoPage.isElementDisplayed("//span[contains(.,'03')]"));
                    assertTrue(lottoPage.isElementDisplayed("//span[contains(.,'04')]"));
                    assertTrue(lottoPage.isElementDisplayed("//span[contains(.,'05')]"));
                    lottoPage.findOnPage("(//strong[contains(.,'Playslip ID:')])[1]");
                    lottoPage.findOnPage("(//strong[contains(.,'E-Mail:')])[1]");
                    lottoPage.findOnPage("(//strong[contains(.,'Phone Number:')])[1]");
                    if (!lottoPage.isElementDisplayed("//h2[contains(.,'Lucky Dip 2')]")) {
                        lottoPage.click("//span[@class='mat-slide-toggle-content'][contains(.,'Lucky dip winners')]");
                        lottoPage.sendKeys("//input[contains(@formcontrolname,'quantity')]", "2");
                        lottoPage.click("//button[contains(.,'Select lucky dip winners')]");
                        lottoPage.waitTwoSeconds();
                        lottoPage.findOnPage("//button[contains(.,'Select lucky dip winners')]");
                        lottoPage.findOnPage("//div[@class='cf-ui-content-content'][contains(.,'Please confirm  You are picking 2 lucky dip winners.')]");
                        lottoPage.click("//span[contains(.,'Correct')]");
                        lottoPage.waitFiveSeconds();
                        lottoPage.findOnPage("//span[contains(.,'Lucky dips have been generated.')]");
                        lottoPage.findOnPage("//h2[contains(.,'Lucky Dip 2')]");
                    }
                    lottoPage.click("//button[contains(.,'Finalize draw')]");
                    lottoPage.findOnPage("//h4[contains(.,'Finalise draw & notify players')]");
                    lottoPage.waitFiveSeconds();
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminPicksLottoWinner!");
        }
    }

    @Then("they finish the daily Lotto, preview and notify players")
    public void ClubAdminFinishAndNotifyWinners() {
        logger.info("Finish and notify winners");
        lottoPage.waitFiveSeconds();

        if (lottoPage.isElementDisplayed("//div[@class='ql-editor'][contains(.,'Our lotto took place this week. ')]")) {
            lottoPage.findOnPage("//h4[contains(.,'Finalise draw & notify players')]");
            lottoPage.sendKeys("//input[contains(@formcontrolname,'subject')]", "Results mail for club Subject line");
            lottoPage.clear("//div[@class='ql-editor'][contains(.,'Our lotto took place this week. ')]");
            lottoPage.sendKeys(LottoPage.LottoQLEditorBlank, "Results mail for club Message line");
            lottoPage.waitTwoSeconds();
            lottoPage.click("//button[contains(.,'Preview')]");
            lottoPage.waitTwoSeconds();
            lottoPage.findOnPage("//h2[@data-test='confirm-dialog.page'][contains(.,'Please confirm')]");
            lottoPage.click("//span[contains(.,'Save and send emails')]");
            lottoPage.waitFiveSeconds();
            lottoPage.findOnPage("//h1[contains(.,'Lotto draws')]");
            lottoPage.findOnPage("//span[contains(.,'Notifications sent.')]");

            logger.info("Do verification in mailtrap, for now manually check there");
            loginPage.accessMailTrapInbox();

            logger.info("Looking for lotto purchase mail");
            lottoPage.click("(//span[@class='to_email'][contains(.,'membertest17@clubforce.com')])[1]");
            lottoPage.waitThreeSeconds();
            driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
            lottoPage.waitFiveSeconds();
            lottoPage.findOnPage("//p[contains(.,'Results mail for club Message line')]");
            lottoPage.findOnPage("//div[normalize-space()='1']");
            lottoPage.findOnPage("//div[normalize-space()='2']");
            lottoPage.findOnPage("//div[normalize-space()='3']");
            lottoPage.findOnPage("//div[normalize-space()='4']");
            lottoPage.findOnPage("//div[normalize-space()='5']");
            logger.warn("Notification has already been sent");
        }
    }

    @And("Lotto payout report can be downloaded")
    public void LottoPayoutReportDownload () {
        logger.info("Downloading the first lotto payout report");
        lottoPage.downloadPayoutReportIfChrome();
    }

    @And("Playslips CSV file can be downloaded")
    public void downloadCSVFileForPlayslips() {
        logger.info("Downloading playslips CSV file");
        lottoPage.downloadPlayslipsCSVFileIfChrome();
//        lottoPage.comparePageAndCSV();
    }

    @And("ClubAdmin changes {string} for club")
    public void changedCutOffPeriodForCLub(String cutOffPeriod) {
        logger.info("Open current draw");
        lottoPage.waitTwoSeconds();
        lottoPage.click("(//mat-icon[contains(.,'edit')])[1]");
        lottoPage.waitTenSeconds();
        lottoPage.findOnPage("//h3[contains(.,'Date & Time')]");


        switch (cutOffPeriod) {
            case "Time":
                logger.info("Selecting the time from the time picker dropdown");
                lottoPage.waitOneSecond();
                lottoPage.sendKeys("//input[contains(@formcontrolname,'time')]","0800");
                lottoPage.click(LottoPage.PrizesHeading);
                break;
            case "Date":
                //if the date is before the 1st and 9th of month, the / will need to be removed
                cutOffDate =lottoPage.getElementAttribute("//input[contains(@formcontrolname,'start')]", "value");
                cutOffDay = cutOffDate.substring(0,2);
                if (cutOffDay.contains("/")) {
                    cutOffDay = cutOffDay.replaceAll("/","");
                }
                day = Integer.parseInt(cutOffDay);
                nextDay = day+1;

                logger.info("Selecting the date from the date picker");
                lottoPage.waitTenSeconds();
                lottoPage.click(LottoPage.LottoDatePicker);
                lottoPage.waitTwoSeconds();
                logger.info("If the date is the 30th/31st, the date will change to the 3rd of the next month");
                if (nextDay > 29) {
                    nextDay = 3;
                    lottoPage.click("//button[@aria-label='Next month']");
                }
                lottoPage.waitTwoSeconds();
                lottoPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+nextDay+"')]");
                logger.info("Date picked is: " + nextDay);
                break;
            case "DateAndTime":
                //if the date is before the 1st and 9th of month, the / will need to be removed
                cutOffDate =lottoPage.getElementAttribute("//input[contains(@formcontrolname,'start')]", "value");
                cutOffDay = cutOffDate.substring(0,2);
                if (cutOffDay.contains("/")) {
                    cutOffDay =cutOffDay.replaceAll("/","");
                }
                day = Integer.parseInt(cutOffDay);
                nextDay = day+1;

                logger.info("Selecting the date from the date picker");
                lottoPage.waitTenSeconds();
                lottoPage.click(LottoPage.LottoDatePicker);
                lottoPage.waitTwoSeconds();
                logger.info("If the date is the 30th/31st, the date will change to the 3rd of the next month");
                if (nextDay > 29) {
                    nextDay = 3;
                    lottoPage.click("//button[@aria-label='Next month']");
                }
                lottoPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+nextDay+"')]");

                logger.info("Selecting the time from the time picker dropdown");
                lottoPage.waitOneSecond();
                lottoPage.sendKeys("//input[contains(@formcontrolname,'time')]","0900");
                break;
            default:
        }

        logger.info("Saving the changes");
        lottoPage.waitOneSecond();
        lottoPage.click("//button[contains(.,'Save')]");
        lottoPage.findOnPage("//h4[contains(.,'Lottery successfully updated!')]");
        logger.info("The changes made to the date and time have been saved");
    }

    @And("ClubAdmin changes {string} back")
    public void changeDateBackADay(String cutOffPeriod) {
        logger.info("Open current draw");
        lottoPage.waitTwoSeconds();
        lottoPage.click("(//mat-icon[contains(.,'edit')])[1]");
        lottoPage.waitTenSeconds();
        lottoPage.findOnPage("//h3[contains(.,'Date & Time')]");


        switch (cutOffPeriod) {
            case "Time":
                logger.info("Not changing the time back");
                break;
            case "Date":
                //if the date is before the 1st and 9th of month, the / will need to be removed
                cutOffDate =lottoPage.getElementAttribute("//input[contains(@formcontrolname,'start')]", "value");
                logger.info("Cut off date is" + cutOffDate);
                String cutOffDayOnly = cutOffDate.substring(0,2);
                if (cutOffDayOnly.contains("/")) {
                    cutOffDayOnly = cutOffDayOnly.replaceAll("/","");
                }
                day = Integer.parseInt(cutOffDayOnly);
                logger.info("The current cut off day is :" + day);
                nextDay = day-1;
                logger.info("Changing day to : " + nextDay);

                logger.info("Selecting the date from the date picker");
                lottoPage.waitTenSeconds();
                lottoPage.click(LottoPage.LottoDatePicker);
                lottoPage.waitTwoSeconds();
                if (nextDay != 1) {
                    lottoPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+nextDay+"')]");
                }
                break;
            case "DateAndTime":
                //if the date is before the 1st and 9th of month, the / will need to be removed
                cutOffDate =lottoPage.getElementAttribute("//input[contains(@formcontrolname,'start')]", "value");
                cutOffDay = cutOffDate.substring(0,1);
                String DayOnly = cutOffDate.substring(0,2);
                if (DayOnly.contains("/")) {
                    DayOnly = DayOnly.replaceAll("/","");
                }
                day = Integer.parseInt(DayOnly);
                nextDay = day-1;

                logger.info("Selecting the date from the date picker");
                lottoPage.waitTenSeconds();
                lottoPage.click(LottoPage.LottoDatePicker);
                lottoPage.waitTwoSeconds();
                if (nextDay != 1) {
                    lottoPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+nextDay+"')]");
                }
                logger.info("Not changing the time back");
                break;
            default:
        }

        logger.info("Saving the changes");
        lottoPage.waitOneSecond();
        lottoPage.click("//button[contains(.,'Save')]");
        lottoPage.findOnPage("//h4[contains(.,'Lottery successfully updated!')]");
        logger.info("The changes made to the date and time have been saved");
    }

    @Then("{string} is updated on Dashboard")
    public void checkCutOffPeriodOnDashboard(String cutOffPeriod) {
        lottoPage.waitTwoSeconds();
        lottoPage.click("//span[@class='ms-2'][contains(.,'Dashboard')]");
        lottoPage.refreshPage();
        lottoPage.waitFiveSeconds();
        switch (cutOffPeriod) {
            case "Time":
                logger.info("Checking that the TIME is correct on the dashboard");
                lottoPage.findOnPage("//p[@class='mat-body-1'][contains(.,'08:00')]");
                break;
            case "Date":
                if (nextDay <= 9 ) { //on the dashboard there is a 0 before the day if the day is between the 1st and the 9th of the month
                    String dayNumber = "0" + nextDay;
                    backofficePage.findOnPage("//p[contains(.,'Current draw: "+dayNumber+"')]");
                } else {
                    backofficePage.findOnPage("//p[contains(.,'Current draw: "+nextDay+"')]");
                }
                break;
            case "DateAndTime":
                logger.info("Checking that the DATE is correct on the dashboard");
                if (nextDay <= 9 ) { //on the dashboard there is a 0 before the day if the day is between the 1st and the 9th of the month
                    String dayNumber = "0" + nextDay;
                    backofficePage.findOnPage("//p[contains(.,'Current draw: "+dayNumber+"')]");
                } else {
                    backofficePage.findOnPage("//p[contains(.,'Current draw: "+nextDay+"')]");
                }
                logger.info("Checking that the TIME is correct on the dashboard");
                lottoPage.findOnPage("//p[@class='mat-body-1'][contains(.,'09:00')]");
                break;
            default:
        }

    }

    @And("{string} is updated on Website")
    public void checkChangesAreUpdatedOnWebsite(String cutOffPeriod) {
        lottoPage.goToClubURL("/");
        lottoPage.waitTwoSeconds();
        lottoPage.click("//span[contains(.,'Lotto')]");
        lottoPage.waitTwoSeconds();
        lottoPage.click("//span[contains(.,'Confirm')]");
        lottoPage.waitTwoSeconds();

        switch (cutOffPeriod) {
            case "Time":
                lottoPage.waitTwoSeconds();
                lottoPage.findOnPage("(//div[contains(.,'8:00')])[11]");
                break;
            case "Date":
                lottoPage.waitTwoSeconds();
                lottoPage.findOnPage("(//div[contains(.,'Sales close "+nextDay+"')])[11]");
                break;
            case "DateAndTime":
                lottoPage.waitTwoSeconds();
                lottoPage.findOnPage("(//div[contains(.,'Sales close "+nextDay+"')])[11]");
                lottoPage.waitOneSecond();
                lottoPage.findOnPage("(//div[contains(.,'9:00')])[11]");
                break;
            default:
        }
    }

    @And("they can add 3 Lotto to cart")
    public void addThreeLottoProductsToCart() {
//        lottoPage.userClosesMobileMenuHeader();

        logger.info("Check that ticket options/lotto balls are already displayed");
        assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[1]"));
        assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[2]"));
        assertTrue(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[3]"));
        assertFalse(lottoPage.isElementDisplayed("(//button[contains(.,'Quick Pick')])[4]"));

        logger.info("Add 1 Lotto");
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.AutoRenewCheckBox);
        lottoPage.click(LottoPage.AddToCartButton);
        lottoPage.waitForElementDisplayedByXpathWithTimeout("//mat-icon[@role='img'][contains(.,'delete')]",5);
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[1]"));
        assertFalse(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[2]"));
        lottoPage.click(ProductsPage.ShoppingCartContinueShoppingButton);
        logger.info("Added 1 Lotto");

        logger.info("Add 2 Lotto");
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.AddToCartButton);
        lottoPage.waitForElementDisplayedByXpathWithTimeout("//mat-icon[@role='img'][contains(.,'delete')]",5);
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[1]"));
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[2]"));
        assertFalse(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[3]"));
        lottoPage.click(ProductsPage.ShoppingCartContinueShoppingButton);
        logger.info("Added 2 Lotto");

        logger.info("Add 3 Lotto");
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.QuickPickButton);
        lottoPage.click(LottoPage.AddToCartButton);
        lottoPage.waitForElementDisplayedByXpathWithTimeout("//mat-icon[@role='img'][contains(.,'delete')]",5);
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[1]"));
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[2]"));
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[3]"));
        assertFalse(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[4]"));
        logger.info("Added 3 Lotto");
    }

    @When("they remove 1 Lotto")
    public void userRemovesOneLotto() {
        lottoPage.click("(//mat-icon[@role='img'][contains(.,'delete')])[1]");
    }

    @Then("only 2 Lotto remain")
    public void twoLottoRemain() {
        lottoPage.waitTwoSeconds();
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[1]"));
        assertTrue(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[2]"));
        assertFalse(lottoPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'delete')])[3]"));    }

    public void moveToStep4Features() {
        lottoPage.click(SuperUserPage.Step2TileTextSU);
        lottoPage.waitOneSecond();
        lottoPage.click(SuperUserPage.Step3TileTextSU);
        lottoPage.waitOneSecond();
        lottoPage.click(SuperUserPage.Step4TileTextSU);
        lottoPage.waitTenSeconds();
        lottoPage.findOnPage(BackofficePage.LottoFeatureToggle);
    }

    public void disableLottoFeatureToggle() {
        backofficePage.click(BackofficePage.LottoFeatureToggle);
        backofficePage.waitOneSecond();
        backofficePage.click(WebsiteSettingsPage.UpdateTopButton);
        backofficePage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully,10);

        if (backofficePage.isElementDisplayed(BackofficePage.SULottoFeatureToggleON)) {
            logger.warn("Toggle change didn't work, still ON! Trying again!");
            backofficePage.click(BackofficePage.LottoFeatureToggle);
            backofficePage.waitTwoSeconds();
            backofficePage.click(WebsiteSettingsPage.UpdateTopButton);
            backofficePage.waitTwoSeconds();
            assertThat("Tried again, toggle change didn't work, still ON! ", backofficePage.isElementDisplayed(BackofficePage.SULottoFeatureToggleOFF));
        }
    }

    public void enableLottoFeatureToggle() {
        backofficePage.waitOneSecond();
        backofficePage.click(BackofficePage.LottoFeatureToggle);
        backofficePage.waitOneSecond();
        backofficePage.click(WebsiteSettingsPage.UpdateTopButton);
        backofficePage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully,10);

        if (backofficePage.isElementDisplayed(BackofficePage.SULottoFeatureToggleOFF)) {
            logger.warn("Toggle change didn't work, still ON! Trying again!");
            backofficePage.click(BackofficePage.LottoFeatureToggle);
            backofficePage.waitTwoSeconds();
            backofficePage.click(WebsiteSettingsPage.UpdateTopButton);
            backofficePage.waitTwoSeconds();
            assertThat("Tried again, toggle change didn't work, still ON!", backofficePage.isElementDisplayed(BackofficePage.SULottoFeatureToggleOFF));
        }
    }

    @Then("ClubAdmin checks how many draws have occurred")
    public void calculateTotalDraws() {
        lottoPage.waitFiveSeconds();
        if (lottoPage.isElementDisplayed("//div[contains(@class,'mat-paginator-page-size-label')]")) {
            String pagination = lottoPage.getElementAttribute("//div[@class='mat-paginator-range-label']", "textContent");
            logger.info("Pagination: " + pagination);
            String numberOfPastDraws = pagination.substring(pagination.indexOf("of") + 3, pagination.length()-1);
            logger.info("Total number of past draws: " + numberOfPastDraws);
            totalDraws = Integer.parseInt(numberOfPastDraws) + 1;
        }else {
            totalDraws = 1;
        }
        logger.info("Got the total number of draws including current draw: " + totalDraws);
    }

    @Then("renewal count is correct for each user")
    public void checkRenewalCount() {
        lottoPage.waitFiveSeconds();
        logger.info("Check recurring order is correct for 3 for a fiver auto renewal");
        String renewalCount3ForFiver = lottoPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[27]", "textContent");
        logger.info("Danny Smith 3 for a fiver renewal count: " + renewalCount3ForFiver);
        int threeForAFiver = Integer.parseInt(renewalCount3ForFiver.substring(1,renewalCount3ForFiver.length()-1));
        assertEquals(threeForAFiver, totalDraws);
        logger.info("Total draws: ("+totalDraws+") equals renewal count  for 3 for a fiver order: (" + threeForAFiver+") \n");

        logger.info("Check recurring order is correct for 1 line 5 draws");
        String renewalCountFor1Line5Draws = lottoPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[21]", "textContent");
        logger.info("Andy Steele 1 line 5 draws: " + renewalCountFor1Line5Draws);
        int oneLineFiveDraws = Integer.parseInt(renewalCountFor1Line5Draws.substring(1,renewalCountFor1Line5Draws.length()-1));
        int calculation1 = calculateRenwalCount(5);
        assertEquals(calculation1,oneLineFiveDraws);
        logger.info("Calculation result: (" + calculation1 + ") equals renewal count for 1 line 5 draws: (" + oneLineFiveDraws+") \n");

        logger.info("Check recurring order is correct for 1 line 10 draws");
        String renewalCountFor1Line10Draws = lottoPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[15]", "textContent");
        logger.info("Eileen Mahood 1 line 10 draws: " + renewalCountFor1Line10Draws);
        int oneLineTenDraws = Integer.parseInt(renewalCountFor1Line10Draws.substring(1,renewalCountFor1Line10Draws.length()-1));
        int calculation2 = calculateRenwalCount(10);
        assertEquals(calculation2,oneLineTenDraws);
        logger.info("Calculation result: (" + calculation2 + ") equals renewal count for 1 line 10 draws: (" + oneLineTenDraws+") \n");

        logger.info("Check recurring order is correct for 1 line half yearly (25 draws)");
        String renewalCountFor1Line25Draws = lottoPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[9]", "textContent");
        logger.info("Kiara Daniels 1 line 25 draws: " + renewalCountFor1Line25Draws);
        int oneLineTwentyFiveDraws = Integer.parseInt(renewalCountFor1Line25Draws.substring(1, renewalCountFor1Line25Draws.length()-1));
        int calculation3 = calculateRenwalCount(25);
        assertEquals(calculation3,oneLineTwentyFiveDraws);
        logger.info("Calculation result: (" + calculation3 + ") equals renewal count for 1 line 25 draws: (" + oneLineTwentyFiveDraws+") \n");

        logger.info("Check recurring order is correct for 1 line yearly (52 draws)");
        String renewalCountFor1Line52Draws = lottoPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
        logger.info("Bailey Haddon 1 line 52 draws: " + renewalCountFor1Line52Draws);
        int oneLineFiftyTwoDraws = Integer.parseInt(renewalCountFor1Line52Draws.substring(1,renewalCountFor1Line52Draws.length()-1));
        int calculation4 = calculateRenwalCount(52);
        assertEquals(calculation4,oneLineFiftyTwoDraws);
        logger.info("Calculation result: (" + calculation4 + ") equals renewal count for 1 line 25 draws: (" + oneLineFiftyTwoDraws+") \n");
    }

    @Then("Orders are displayed correctly for each user")
    public void checkOrderCount() {
        lottoPage.waitFiveSeconds();
        lottoPage.sendKeys("//input[contains(@filtername,'member')]","Danny");

        lottoPage.waitTwoSeconds();
        logger.info("Check order is correct for 3 for a fiver auto renewal");
        String renewalCount3ForFiver = lottoPage.getElementAttribute("//div[contains(@class,'mat-paginator-range-label')]", "textContent");
        String renewalCount3ForFiver2 =renewalCount3ForFiver.substring(renewalCount3ForFiver.lastIndexOf("f") + 2, renewalCount3ForFiver.length()-1);
        logger.info("Danny Smith 3 for a fiver  count: " + renewalCount3ForFiver);
        int threeForAFiver = Integer.parseInt(renewalCount3ForFiver2);
        assertEquals(threeForAFiver, totalDraws);
        logger.info("Total draws: ("+totalDraws+") equals count  for 3 for a fiver order: (" + threeForAFiver+") \n");

        lottoPage.waitOneSecond();
        lottoPage.clearInputFieldUsingBackSpaceKey("//input[contains(@filtername,'member')]");
        lottoPage.waitOneSecond();
        lottoPage.sendKeys("//input[contains(@filtername,'member')]","Andy");

        logger.info("Check order is correct for 1 line 5 draws");
        lottoPage.waitThreeSeconds();
        String renewalCountFor1Line5Draws = lottoPage.getElementAttribute("//div[contains(@class,'mat-paginator-range-label')]", "textContent");
        String renewalCountFor1Line5Draws2 = renewalCountFor1Line5Draws.substring(renewalCountFor1Line5Draws.lastIndexOf("f") + 2, renewalCountFor1Line5Draws.length()-1);
        logger.info("Andy Steele 1 line 5 draws: " + renewalCountFor1Line5Draws2);
        int oneLineFiveDraws = Integer.parseInt(renewalCountFor1Line5Draws2);
        int calculation1 = calculateRenwalCount(5);
        assertEquals(calculation1,oneLineFiveDraws);
        logger.info("Calculation result: (" + calculation1 + ") equals count for 1 line 5 draws: (" + oneLineFiveDraws+") \n");

        lottoPage.waitOneSecond();
        lottoPage.clearInputFieldUsingBackSpaceKey("//input[contains(@filtername,'member')]");
        lottoPage.waitOneSecond();
        lottoPage.sendKeys("//input[contains(@filtername,'member')]","Eileen");

        logger.info("Check order is correct for 1 line 10 draws");
        lottoPage.waitThreeSeconds();
        String renewalCountFor1Line10Draws = lottoPage.getElementAttribute("//div[contains(@class,'mat-paginator-range-label')]", "textContent");
        String renewalCountFor1Line10Draws2 = renewalCountFor1Line10Draws.substring(renewalCountFor1Line10Draws.lastIndexOf("f") + 2, renewalCountFor1Line10Draws.length()-1);
        logger.info("Eileen Mahood 1 line 10 draws: " + renewalCountFor1Line10Draws2);
        int oneLineTenDraws = Integer.parseInt(renewalCountFor1Line10Draws2);
        int calculation2 = calculateRenwalCount(10);
        assertEquals(calculation2,oneLineTenDraws);
        logger.info("Calculation result: (" + calculation2 + ") equals count for 1 line 10 draws: (" + oneLineTenDraws+") \n");

        lottoPage.waitOneSecond();
        lottoPage.clearInputFieldUsingBackSpaceKey("//input[contains(@filtername,'member')]");
        lottoPage.waitOneSecond();
        lottoPage.sendKeys("//input[contains(@filtername,'member')]","Kiara");

        logger.info("Check order is correct for 1 line half yearly (25 draws)");
        lottoPage.waitThreeSeconds();
        String renewalCountFor1Line25Draws = lottoPage.getElementAttribute("//div[contains(@class,'mat-paginator-range-label')]", "textContent");
        String renewalCountFor1Line25Draws2 = renewalCountFor1Line25Draws.substring(renewalCountFor1Line25Draws.lastIndexOf("f") + 2, renewalCountFor1Line25Draws.length()-1);
        logger.info("Kiara Daniels 1 line 25 draws: " + renewalCountFor1Line25Draws2);
        int oneLineTwentyFiveDraws = Integer.parseInt(renewalCountFor1Line25Draws2);
        int calculation3 = calculateRenwalCount(25);
        assertEquals(calculation3,oneLineTwentyFiveDraws);
        logger.info("Calculation result: (" + calculation3 + ") equals count for 1 line 25 draws: (" + oneLineTwentyFiveDraws+") \n");

        lottoPage.waitOneSecond();
        lottoPage.clearInputFieldUsingBackSpaceKey("//input[contains(@filtername,'member')]");
        lottoPage.waitOneSecond();
        lottoPage.sendKeys("//input[contains(@filtername,'member')]","Bailey");

        logger.info("Check order is correct for 1 line yearly (52 draws)");
        lottoPage.waitThreeSeconds();
        String renewalCountFor1Line52Draws = lottoPage.getElementAttribute("//div[contains(@class,'mat-paginator-range-label')]", "textContent");
        String renewalCountFor1Line52Draws2 = renewalCountFor1Line52Draws.substring(renewalCountFor1Line52Draws.lastIndexOf("f") + 2, renewalCountFor1Line52Draws.length()-1);
        logger.info("Bailey Haddon 1 line 52 draws: " + renewalCountFor1Line52Draws2);
        int oneLineFiftyTwoDraws = Integer.parseInt(renewalCountFor1Line52Draws2);
        int calculation4 = calculateRenwalCount(52);
        assertEquals(calculation4,oneLineFiftyTwoDraws);
        logger.info("Calculation result: (" + calculation4 + ") equals count for 1 line 25 draws: (" + oneLineFiftyTwoDraws+") \n");
    }

    public int calculateRenwalCount(int drawNumber) {
        int calculation;
        if (totalDraws % drawNumber == 0) {
            logger.info("Calculation is total number of draws (" + totalDraws + ") divided by how many times the ticket is entered into a draw (" + drawNumber + ")");
            calculation = totalDraws/drawNumber;
            logger.info("Calculation result: " + (totalDraws/drawNumber));
            return calculation;
        } else {
            logger.info("Calculation is total number of draws (" + totalDraws + ") divided by how many times the ticket is entered into a draw (" + drawNumber + ")");
            double result = (double)totalDraws/drawNumber;
            logger.info("Calculation result: " + result);
            int total = (int) Math.ceil((double)totalDraws/drawNumber);
            logger.info("Rounding up would make new result: " + total);
            return total;
        }
    }

    @Then("ClubAdmin can {string} ticket option")
    public void performActionForLottoTicket(String action) {
        switch (action) {
            case "add new":
                if (!lottoPage.isElementDisplayed("(//input[contains(@formcontrolname,'title')])[3]")) {
                    int number1 = 3;
                    int number2 = 5;

                    logger.info("Add new ticket option");
                    lottoPage.scrollPageToBottom();
                    lottoPage.click("//button[contains(.,'Add new ticket type')]");
                    lottoPage.waitFiveSeconds();
                    assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));
                    lottoPage.sendKeys("(//input[contains(@formcontrolname,'title')])[3]", ""+number1+" draws "+number2+" lines");
                    lottoPage.sendKeys("(//input[contains(@formcontrolname,'draws')])[3]", ""+number1+"");
                    lottoPage.sendKeys("(//input[contains(@formcontrolname,'lines')])[3]", ""+number2+"");
                    lottoPage.sendKeys("(//input[contains(@formcontrolname,'price')])[4]", "5");
                    lottoPage.waitTwoSeconds();
                    assertTrue(lottoPage.isElementDisplayed("(//strong[contains(.,'Discount')])[2]"));
                    assertFalse(lottoPage.isElementDisplayed("(//strong[contains(.,'No Discount')])[2]"));
                    ProductPurchaseSteps.ADDED_LOTTO_TICKET_OPTION = ""+number1+" draws "+number2+" lines";

                    lottoPage.scrollPageToBottom();
                    lottoPage.click("//span[contains(.,'Save and Publish')]");
                    lottoPage.click("//span[contains(@id,'bo--lotto--setup--dialog--confirm')]");
                } else {
                    ProductPurchaseSteps.ADDED_LOTTO_TICKET_OPTION = lottoPage.getElementAttribute("(//input[contains(@formcontrolname,'title')])[3]", "value");
                }
                break;
            case "finish":
                lottoPage.waitTwoSeconds();
                lottoPage.scrollPageToBottom();
                assertFalse(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));
                lottoPage.click("(//span[contains(@class,'mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin')])[4]");
                lottoPage.click("//span[contains(.,'Yes, continue')]");
                lottoPage.click("//span[contains(.,'Save and Publish')]");
                lottoPage.waitTwoSeconds();
                assertFalse(lottoPage.isElementDisplayed("//strong[contains(.,'"+ProductPurchaseSteps.ADDED_LOTTO_TICKET_OPTION+"')]"));
                lottoPage.click("//span[contains(.,'Save and Publish Lotto')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for performActionForLottoTicket!");
        }
    }

    @Then("Clubmember can view order details for {string} playslip")
    public void checkOrderDetailsForPlayslip(String status) {
        lottoPage.waitThirtySeconds();
        switch (status) {
            case "Active":
                logger.info("Clicking active ticker order details button");
                lottoPage.click("(//button[contains(.,'Order details')])[1]");

                logger.info("Verifying that club member is now on the correct order details page");
                lottoPage.findOnPage("//h1[contains(.,'Order details')]");
                lottoPage.findOnPage("//h2[contains(.,'General info')]");
                lottoPage.findOnPage("//*[contains(text(),'"+ProductPurchaseSteps.ACTIVE_PLAYSLIP_ID+"')]");
                lottoPage.findOnPage("//*[contains(text(),'"+ ProductPurchaseSteps.ACTIVE_TICKET_OPTION_NAME +"')]");

                break;
            case "Inactive":
                //TODO once pagination is implemented then I will add this code and do checks here for existing account holder
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkOrderDetailsForPlayslip!");
        }
    }

    @Then("lotto selection is random when selected {int} times")
    public void lottoRandomnessTest(Integer tries) {
        // For a many times make lotto selection and check there are no duplicate selections

        int retries = 1;
        while (retries < tries) {
            logger.info("Try number "+retries);
            lottoPage.refreshPage();
            lottoPage.waitTwoSeconds();
            lottoPage.findOnPage(LottoPage.AgeVerificationPopUp);
            logger.info("Confirming we are over 18");
            lottoPage.click("//button[contains(.,'Confirm')]");

            //Row 1
            lottoPage.click("(//button[contains(.,'Quick Pick')])[1]");
            List<String> row1 = new ArrayList<>();
            row1.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[1]", "textContent"));
            row1.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[2]", "textContent"));
            row1.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[3]", "textContent"));
            row1.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[4]", "textContent"));
            row1.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[5]", "textContent"));
            logger.info("Row 1 is " + row1);

            // Row 2
            lottoPage.click("(//button[contains(.,'Quick Pick')])[1]");
            List<String> row2 = new ArrayList<>();
            row2.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[6]", "textContent"));
            row2.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[7]", "textContent"));
            row2.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[8]", "textContent"));
            row2.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[9]", "textContent"));
            row2.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[10]", "textContent"));
            logger.info("Row 2 is " + row2);

            // Row 3
            lottoPage.click("(//button[contains(.,'Quick Pick')])[1]");
            List<String> row3 = new ArrayList<>();
            row3.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[11]", "textContent"));
            row3.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[12]", "textContent"));
            row3.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[13]", "textContent"));
            row3.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[14]", "textContent"));
            row3.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[15]", "textContent"));
            logger.info("Row 3 is " + row3);

            // Assert
            assertNotSame(row1, row2);
            assertNotSame(row1, row3);
            assertNotSame(row2, row3);

            // -----------------------------------------------------------------------------------------------------
            // Now try same test but only clear row 2 and 3

            lottoPage.click("(//button[contains(.,'Clear')])[2]");
            lottoPage.click("(//button[contains(.,'Clear')])[2]");
            lottoPage.waitTwoSeconds();
            lottoPage.click("(//button[contains(.,'Quick Pick')])[1]");
            lottoPage.waitTwoSeconds();
            lottoPage.click("(//button[contains(.,'Quick Pick')])[1]");
            lottoPage.waitTwoSeconds();
            logger.info("Row 1 is still " + row1);

            List<String> row2b = new ArrayList<>();
            row2b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[6]", "textContent"));
            row2b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[7]", "textContent"));
            row2b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[8]", "textContent"));
            row2b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[9]", "textContent"));
            row2b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[10]", "textContent"));
            logger.info("Row 2b is now " + row2b);

            List<String> row3b = new ArrayList<>();
            row3b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[11]", "textContent"));
            row3b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[12]", "textContent"));
            row3b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[13]", "textContent"));
            row3b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[14]", "textContent"));
            row3b.add(lottoPage.getElementAttribute("(//button[contains(@class,'mat-button--club--secondary')])[15]", "textContent"));
            logger.info("Row 3b is now " + row3b);

            // Assert
            assertNotSame(row1, row2b);
            assertNotSame(row1, row3b);
            assertNotSame(row2b, row3b);

            retries++;
        }
    }

    @Then("they can use the pagination for {string} tickets section")
    public void checkPaginationWorksForEachLottoSection(String section){
        switch (section){
            case "active":
                logger.info("Testing active ticket pagination");
                lottoPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);
                lottoPage.waitFifteenSeconds();
                lottoPage.findOnPage("(//div[contains(@class,'mat-paginator-range-label')])[1]");
                String activeLottoPagination = lottoPage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[1]", "textContent");
                logger.info(activeLottoPagination);
                assertTrue(activeLottoPagination.contains(" 1 – 10 of"));
                lottoPage.click("(//button[@aria-label='Next page'])[1]");
                lottoPage.waitFifteenSeconds();
                lottoPage.findOnPage("(//div[contains(@class,'mat-paginator-range-label')])[1]");
                String activeLottoPagination2 = lottoPage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[1]", "textContent");
                assertTrue(activeLottoPagination2.contains(" 11 – 20 of"));
                assertFalse(activeLottoPagination2.contains(" 1 – 10 of"));
                lottoPage.click("//button[contains(@aria-label,'Previous page')][1]");
                lottoPage.waitFifteenSeconds();
                lottoPage.findOnPage("(//div[contains(@class,'mat-paginator-range-label')])[1]");
                String activeLottoPagination3 = lottoPage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[1]", "textContent");
                assertTrue(activeLottoPagination3.contains(" 1 – 10 of"));
                assertFalse(activeLottoPagination3.contains(" 11 – 20 of"));
                break;
            case "history":
                logger.info("Testing history ticket pagination");
                lottoPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);
                lottoPage.waitFifteenSeconds();
                lottoPage.findOnPage("(//div[contains(@class,'mat-paginator-range-label')])[2]");
                String historyLottoPagination = lottoPage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[2]", "textContent");
                logger.info(historyLottoPagination);
                assertTrue(historyLottoPagination.contains(" 1 – 10 of"));
                lottoPage.click("(//button[@aria-label='Next page'])[2]");
                lottoPage.waitFifteenSeconds();
                lottoPage.findOnPage("(//div[contains(@class,'mat-paginator-range-label')])[2]");
                String historyLottoPagination2 = lottoPage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[2]", "textContent");
                assertTrue(historyLottoPagination2.contains(" 11 – 20 of"));
                assertFalse(historyLottoPagination2.contains(" 1 – 10 of"));
                lottoPage.click("(//button[@aria-label='Previous page'])[2]");
                lottoPage.waitFifteenSeconds();
                lottoPage.findOnPage("(//div[contains(@class,'mat-paginator-range-label')])[2]");
                String historyLottoPagination3 = lottoPage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[2]", "textContent");
                assertTrue(historyLottoPagination3.contains(" 1 – 10 of"));
                assertFalse(historyLottoPagination3.contains(" 11 – 20 of"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkPaginationWorksForEachLottoSection!");
        }
    }
}
