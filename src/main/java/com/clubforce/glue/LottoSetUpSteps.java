package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.LottoPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LottoSetUpSteps extends WebDriverManager {

    WebDriverManager driverManager;

    public LottoSetUpSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.lottoPage = driverManager.lottoPage;
    }

    private static final Logger logger = LogManager.getLogger();
    public static String ShortDescriptionHOLDER;
    public static String SelectedDateHOLDER;
    public static String SelectedScheduleHOLDER;
    @When("they fill out the Lotto Information pages")
    public void adminFillsOutLottoInformation() {
        Lorem lorem = LoremIpsum.getInstance();
        lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.LottoInformationStepperHeading,60);
        lottoPage.verifyLottoBasicPageElements();

        logger.info("Asserting club name shows in Lotto name field");
        logger.info("LottoName is: " + lottoPage.getElementAttribute(BackofficePage.Form_Name, "value"));
        assertThat("Name not as expected!", lottoPage.getElementAttribute(BackofficePage.Form_Name, "value"), containsString(SuperUserSteps.ClubNameHOLDER));
        logger.info("Club name shows in Lotto name field");

        logger.info("Making club name unique");
        LottoSteps.LottoNameSuffixHOLDER = "Lotto " + SeleniumUtilities.getDateTimeFormat("MMM dd ") + RandomStringUtils.randomAlphabetic(2);
        lottoPage.clearInputFieldUsingBackSpaceKey(BackofficePage.Form_Name);
        lottoPage.sendKeys(BackofficePage.Form_Name, LottoSteps.LottoNameSuffixHOLDER);

        logger.info("Check validation for description field");
        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.LottoSetUpStepOneLottoSettingsButton);
        lottoPage.waitThreeSeconds();
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DescriptionIsRequiredErrorMessage));

        logger.info("Adding lotto description");
        ShortDescriptionHOLDER = lorem.getWords(10, 10);
        lottoPage.sendKeys(LottoPage.LottoDescriptionField, ShortDescriptionHOLDER);
        lottoPage.click(LottoPage.LottoImageInformationText); //de focus

        if (driverManager.agent.contains("safari")) {
            logger.info("Browser is Safari, skip uploading lottoImage");
        } else {
            logger.info("Uploading new lotto image");
            lottoPage.findOnPage(BackofficePage.DisabledRemoveImageButton);
            if (envName.contains("sandbox")) {
                lottoPage.findOnPage("//img[@src='https://d3ldp2qb41s5r8.cloudfront.net/lotto_product.jpg']");
            } else {
                lottoPage.findOnPage("//img[@src='https://d35svmxjfditt5.cloudfront.net/lotto_product.jpg']");
            }

            logger.info("Click upload button");
            lottoPage.uploadFileUsingJSExec("lottoBall.jpg", LottoPage.LottoImageFilePath);
            lottoPage.click(BackofficePage.UploadButtonOnCropper);
            lottoPage.click(BackofficePage.ImageHelpText); //to defous field
            logger.info("Removing uploaded lotto image");
            lottoPage.click(BackofficePage.EnabledRedRemoveImageButton);
            lottoPage.click(BackofficePage.RemoveImageButton);
            lottoPage.waitTwoSeconds();
            if (envName.contains("sandbox")) {
                lottoPage.findOnPage("//img[@src='https://d3ldp2qb41s5r8.cloudfront.net/lotto_product.jpg']");
            } else {
                lottoPage.findOnPage("//img[@src='https://d35svmxjfditt5.cloudfront.net/lotto_product.jpg']");
            }
            logger.info("Uploading new lotto image again");
            logger.info("Click upload button");
            lottoPage.uploadFileUsingJSExec("lottoBall.jpg", LottoPage.LottoImageFilePath);
            lottoPage.click(BackofficePage.UploadButtonOnCropper);
            lottoPage.click(BackofficePage.ImageHelpText); //to defous field
            logger.info("Uploaded lottoImage");
        }

        logger.info("Go to second page");
        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.NextLottoSettingsBottomButton);
        lottoPage.verifyLottoSettingsPageElements();

        logger.info("Go back to first page");
        lottoPage.click(LottoPage.LottoSepUpLottoInformationStepper1);
        lottoPage.findOnPage(BackofficePage.Form_Name);
        lottoPage.verifyLottoBasicPageElements();
    }

    @When("they fill out the {string} Lotto Settings pages")
    public void adminFillsOutLottoSettings(String country) {
        switch (country) {
            case "Ireland":
                adminFillsOutIrelandClubLottoSettingsPage();
                break;
            case "England":
            case "Northern":
            case "Scotland":
            case "Wales":
                adminFillsOutUnitedKingdomClubLottoSettingsPage();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminFillsOutLottoSettings!");
        }
    }

    public void adminFillsOutIrelandClubLottoSettingsPage() {
        logger.info("Go to second page again");
        lottoPage.click(LottoPage.NextLottoSettingsBottomButton);
        lottoPage.verifyLottoSettingsPageElements();

        logger.info("Enter lotto details");
        logger.info("Set date to next month, 25th");
        lottoPage.click(LottoPage.LottoDatePicker);
        lottoPage.click(LottoPage.LottoNextMonthDateOption);
        lottoPage.click(LottoPage.Pick25thDayOfMonth);
        SelectedDateHOLDER = lottoPage.getElementAttribute(LottoPage.LottoDateField , "value");
        logger.info("SelectedDateHOLDER "+SelectedDateHOLDER);

        logger.info("Set cut-off time to 2pm");
        lottoPage.waitTwoSeconds();
        lottoPage.sendKeys(LottoPage.LottoCutOffTime,"1400");
        lottoPage.click(LottoPage.ScheduleDrawHeading);

        logger.info("Set Schedule to every month on the 25th");
        lottoPage.click(LottoPage.LottoScheduleDropDown);
        lottoPage.click(LottoPage.EveryMonthOnThe25thOption);  //https://clubforce.atlassian.net/browse/CP-1243
        SelectedScheduleHOLDER = lottoPage.getElementAttribute(LottoPage.LottoScheduleField, "value");

        logger.info("Set range to numbers");
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.PickRangeDropDown);
        lottoPage.click(LottoPage.PickRangeNumberOption);

        logger.info("Set start and end range");
        logger.info("Error handling range");
        lottoPage.click(LottoPage.EndRangeDropDown);
        lottoPage.click(LottoPage.NumberOption24);
        lottoPage.sendKeys(LottoPage.StartRangeField, "30");
        lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.StartRangeMustBeLessThanEndRangeErrorText,5);

        lottoPage.sendKeys(LottoPage.StartRangeField, "200");
        lottoPage.click(LottoPage.EndRangeDropDown);
        lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.ValueMustBeBetween1And60ErrorText,5);
        lottoPage.sendKeys(LottoPage.EndRangeDropDown, "100");
        lottoPage.findOnPage(LottoPage.ValueMustBeBetween1And60ErrorText);
        lottoPage.findOnPage(LottoPage.StartRangeMustBeLessThanEndRangeErrorText);
        lottoPage.sendKeys(LottoPage.StartRangeField, "11");
        lottoPage.waitTwoSeconds();
        assertFalse(lottoPage.isElementDisplayed(LottoPage.StartRangeMustBeLessThanEndRangeErrorText));

        logger.info("Set range to letters");
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.LottoPickRangeText);
        lottoPage.click(LottoPage.PickRangeLettersOption);
        lottoPage.click(LottoPage.LottoNumberOfPicks);
        lottoPage.click(LottoPage.NumberOption5);
        lottoPage.click(LottoPage.PickRangeHeading);

        logger.info("Set prizes");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField1));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField2));
        lottoPage.sendKeys(LottoPage.PrizeAmountField1, "500");

        logger.info("Add 2 prize boxes");
        lottoPage.centreElement(LottoPage.AddAnotherPrizeButton);
        lottoPage.click(LottoPage.AddAnotherPrizeButton);
        lottoPage.centreElement(LottoPage.AddAnotherPrizeButton);
        lottoPage.click(LottoPage.AddAnotherPrizeButton);
        lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.PrizeAmountField3,5);
        assertFalse(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField4));
        lottoPage.sendKeys(LottoPage.PrizeAmountField2, "300.00");
        lottoPage.sendKeys(LottoPage.PrizeAmountField3, "20");
        lottoPage.click(LottoPage.PrizesHeading);

        lottoPage.click(LottoPage.PrizeTypeDropDown);
        lottoPage.click(LottoPage.Match4Option);
        lottoPage.click(LottoPage.PrizesHeading);
        lottoPage.click(LottoPage.PrizeTypeDropDown);
        lottoPage.click(LottoPage.Match3Option);
        lottoPage.click(LottoPage.PrizesHeading);

        logger.info("Go to third page");
        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.NextLottoDiscountBottomButton);

        logger.info("Price per line");
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField , "  ");
        lottoPage.escape();
        lottoPage.findOnPage(LottoPage.PriceMustBeHigherThanErrorText);

        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField, "abcd");
        lottoPage.waitForElementDisplayedByXpathWithTimeout(LottoPage.PriceMustBeHigherThanErrorText,5);
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField, "1");

        logger.info("Verify 3 for a fiver");
        lottoPage.click(LottoPage.LottoSetupHeading);
        lottoPage.findOnPage(BackofficePage.FormControlNameTitle);
        assertThat(lottoPage.getElementAttribute(BackofficePage.FormControlNameTitle, "value"),containsString("3 for fiver"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfDrawsField, "value"),containsString("1"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfLinesField, "value"),containsString("3"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoTicketPricePerLineField, "value"),containsString("1"));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.NoDiscountText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        String ticketOptionTitle1 = "Automation lotto ticket title";
        int numberOfDraws1 = 7;
        int numberOfLines1 = 3;
        logger.info("Edit 3 for a fiver");
        lottoPage.sendKeys(BackofficePage.FormControlNameTitle, ticketOptionTitle1);
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField, "7");
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField, "3");
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoTicketPricePerLineField, "value"),containsString("1"));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DiscountText));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.NoDiscountText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        logger.info("Verify price cannot be lower than 5");
        lottoPage.centreElement(LottoPage.LottoTicketPricePerLineField2);
        lottoPage.clearInputFieldUsingBackSpaceKey(LottoPage.LottoTicketPricePerLineField2);
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField2, "3");
        lottoPage.findOnPage(LottoPage.ValueOfPricePerTicketErrorMessage);

        logger.info("Remove 3 for a fiver");
        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.RemoveTicketOption);
        assertFalse(lottoPage.isElementDisplayed(BackofficePage.FormControlNameTitle));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.LottoNumberOfDrawsField));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.LottoNumberOfLinesField));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        logger.info("Add a replacement row");
        lottoPage.centreElement(LottoPage.AddNewTicketButton);
        lottoPage.click(LottoPage.AddNewTicketButton);
        lottoPage.sendKeys(BackofficePage.FormControlNameTitle, ticketOptionTitle1);
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField, "7");
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField, "3");
        lottoPage.sendKeys(LottoPage.LottoTicketPricerPerTicketLineField2 , "5");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DiscountText));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.NoDiscountText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        String ticketOptionTitle2 = "Automation_Ballán Álainn 韓國語 Lotto title";
        int numberOfDraws2 = 6;
        int numberOfLines2 = 4;
        logger.info("Add a second row");
        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.AddNewTicketButton);
        lottoPage.sendKeys(BackofficePage.FormControlNameTitle2, ticketOptionTitle2);
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField2, "6");
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField2, "4");
        lottoPage.sendKeys(LottoPage.LottoTicketPricerPerTicketLineField3, "5");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DiscountText2));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.NoDiscountText2 ));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton2));

        logger.info("Submit, cancel and check, then Submit again");
        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.SaveAndPublishLottoButton);
        lottoPage.findOnPage(LottoPage.ConfirmTicketOptionsTicket);
        lottoPage.findOnPage(LottoPage.SavingAndPublishingLottoInformationText);
        lottoPage.findOnPage("//strong[contains(.,'"+ticketOptionTitle1+"')]");
        lottoPage.findOnPage("//div[contains(.,'"+numberOfDraws1+" draws with "+numberOfLines1+" lines = €5.00')]");
        lottoPage.findOnPage("//strong[contains(.,'"+ticketOptionTitle2+"')]");
        lottoPage.findOnPage("//div[contains(.,'"+numberOfDraws2+" draws with "+numberOfLines2+" lines = €5.00')]");
        lottoPage.click(LottoPage.LottoPopUpCancelButton);
        assertThat(lottoPage.getElementAttribute(BackofficePage.FormControlNameTitle, "value"),containsString(ticketOptionTitle1));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfDrawsField, "value"),containsString("7"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfLinesField, "value"),containsString("3"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoTicketPricePerLineField, "value"),containsString("1"));

        lottoPage.scrollPageToBottom();
        lottoPage.click(LottoPage.SaveAndPublishLottoButton);
        lottoPage.findOnPage(LottoPage.ConfirmTicketOptionsTicket);
        lottoPage.findOnPage(LottoPage.SavingAndPublishingLottoInformationText);
        lottoPage.click(LottoPage.LottoSaveAndPublishButton);
        lottoPage.waitUntilElementInvisible(LottoPage.LottoSuccessfullyCreatedNotification, 5);
        logger.info("Expecting to redirect to Draws page");

        if (!lottoPage.isElementDisplayed(LottoPage.CurrentDrawHeading)) {
            logger.error("Did not redirect as expected");
        } else {
            logger.info("Arrived on Draws page");
        }

        if (!lottoPage.isElementDisplayed(BackofficePage.LottoDrawsTile)) {
            lottoPage.click(BackofficePage.LottoLeftNav);
            lottoPage.findOnPage(LottoPage.LottoDrawsSubSection);
            lottoPage.findOnPage(BackofficePage.LottoDetailsLeftNav);
        }

        int dayOfFirstDraw = 25;
        lottoPage.click(BackofficePage.DashboardTitleLeftNav);
        lottoPage.findOnPage("//p[contains(.,'Current draw: "+dayOfFirstDraw+"')]");

        logger.info("Find current draw revenue");
        lottoPage.findOnPage(LottoPage.CurrentDrawRevenueHeading);
        lottoPage.findOnPage(LottoPage.PriceAmount000);
        lottoPage.findOnPage(LottoPage.SincePreviousDrawText);
    }

    public void adminFillsOutUnitedKingdomClubLottoSettingsPage() {
        logger.info("Go to second page again");
        lottoPage.click(LottoPage.NextLottoSettingsBottomButton);
        lottoPage.verifyLottoSettingsPageElements();

        logger.info("Enter lotto details");
        lottoPage.waitTwoSeconds();
        logger.info("Set date to next month, 25th");
        lottoPage.click(LottoPage.LottoDatePicker);
        lottoPage.click(LottoPage.LottoNextMonthDateOption);
        lottoPage.click(LottoPage.Pick25thDayOfMonth);
        SelectedDateHOLDER = lottoPage.getElementAttribute(LottoPage.LottoDateField, "value");
        logger.info("SelectedDateHOLDER "+SelectedDateHOLDER);

        logger.info("Set cut-off time to 2pm");
        lottoPage.sendKeys(LottoPage.LottoCutOffTime,"1400");
        lottoPage.click(LottoPage.ScheduleDrawHeading);

        logger.info("Set Schedule to every month on the 25th");
        lottoPage.click(LottoPage.LottoScheduleDropDown);
        lottoPage.waitOneSecond();
        lottoPage.click(LottoPage.EveryMonthOnThe25thOption);  //https://clubforce.atlassian.net/browse/CP-1243
        SelectedScheduleHOLDER = lottoPage.getElementAttribute(LottoPage.LottoDateField, "value");

        logger.info("Set range to numbers");
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.PickRangeDropDown);
        lottoPage.click(LottoPage.PickRangeNumberOption);

        logger.info("Set start and end range");
        logger.info("Error handling range");
        lottoPage.click(LottoPage.EndRangeDropDown);
        lottoPage.click(LottoPage.NumberOption24);
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.sendKeys(LottoPage.StartRangeField, "30");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.StartRangeMustBeLessThanEndRangeErrorText));
        lottoPage.sendKeys(LottoPage.StartRangeField, "200");
        lottoPage.click(LottoPage.EndRangeDropDown);
        assertTrue(lottoPage.isElementDisplayed(LottoPage.ValueMustBeBetween1And60ErrorText));
        lottoPage.sendKeys(LottoPage.EndRangeDropDown, "100");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.ValueMustBeBetween1And60ErrorText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.StartRangeMustBeLessThanEndRangeErrorText));
        lottoPage.sendKeys(LottoPage.StartRangeField, "11");
        assertFalse(lottoPage.isElementDisplayed(LottoPage.StartRangeMustBeLessThanEndRangeErrorText));

        logger.info("Set range to letters");
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.LottoPickRangeText);
        lottoPage.click(LottoPage.PickRangeLettersOption);
        lottoPage.click(LottoPage.LottoNumberOfPicks);
        lottoPage.click(LottoPage.NumberOption5);
        lottoPage.click(LottoPage.PickRangeHeading);

        logger.info("Set prizes");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField1));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField2));
        lottoPage.sendKeys(LottoPage.PrizeAmountField1, "500");

        logger.info("Add 2 prize boxes");
        lottoPage.centreElement(LottoPage.AddAnotherPrizeButton);
        lottoPage.click(LottoPage.AddAnotherPrizeButton);
        lottoPage.click(LottoPage.AddAnotherPrizeButton);
        assertTrue(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField3));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.PrizeAmountField4));
        lottoPage.sendKeys(LottoPage.PrizeAmountField2, "300.00");
        lottoPage.sendKeys(LottoPage.PrizeAmountField3, "20");
        lottoPage.click(LottoPage.PrizesHeading);
        lottoPage.click(LottoPage.PrizeTypeDropDown);
        lottoPage.click(LottoPage.Match4Option);
        lottoPage.click(LottoPage.PrizesHeading);
        lottoPage.click(LottoPage.PrizeTypeDropDown);
        lottoPage.click(LottoPage.Match3Option);
        lottoPage.click(LottoPage.PrizesHeading);

        logger.info("Go to third page");
        lottoPage.click(LottoPage.NextLottoDiscountBottomButton);

        logger.info("Price per line");
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField , "  ");
        lottoPage.escape();
        lottoPage.findOnPage(LottoPage.PriceMustBeHigherThanErrorText);
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField, "abcd");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.PriceMustBeHigherThanErrorText));
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField, "1");

        logger.info("Verify 3 for a fiver");
        lottoPage.click(LottoPage.LottoSetupHeading);
        lottoPage.findOnPage(BackofficePage.FormControlNameTitle);
        assertThat(lottoPage.getElementAttribute(BackofficePage.FormControlNameTitle, "value"),containsString("3 for fiver"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfDrawsField, "value"),containsString("1"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfLinesField, "value"),containsString("3"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoTicketPricePerLineField, "value"),containsString("1"));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.NoDiscountText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        String ticketOptionTitle1 = "Automation lotto ticket title";
        int numberOfDraws1 = 7;
        int numberOfLines1 = 3;
        logger.info("Edit 3 for a fiver");
        lottoPage.sendKeys(BackofficePage.FormControlNameTitle, ticketOptionTitle1);
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField, "7");
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField, "3");
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoTicketPricePerLineField, "value"),containsString("1"));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DiscountText));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.NoDiscountText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        logger.info("Verify price cannot be lower than 5");
        lottoPage.centreElement(LottoPage.LottoTicketPricePerLineField2);
        lottoPage.clearInputFieldUsingBackSpaceKey(LottoPage.LottoTicketPricePerLineField2);
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField2, "3");
        assertFalse(lottoPage.isElementDisplayed(LottoPage.ValueOfPricePerTicketErrorMessage));

        logger.info("Remove 3 for a fiver");
        lottoPage.click(LottoPage.RemoveTicketOption);
        assertFalse(lottoPage.isElementDisplayed(BackofficePage.FormControlNameTitle));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.LottoNumberOfDrawsField));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.LottoNumberOfLinesField));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        logger.info("Add a replacement row");
        lottoPage.click(LottoPage.AddNewTicketButton);
        lottoPage.sendKeys(BackofficePage.FormControlNameTitle, ticketOptionTitle1);
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField, "7");
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField, "3");
        lottoPage.sendKeys(LottoPage.LottoTicketPricerPerTicketLineField2 , "5");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DiscountText));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.NoDiscountText));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton));

        String ticketOptionTitle2 = "Automation_Ballán Álainn 韓國語 Lotto title";
        int numberOfDraws2 = 6;
        int numberOfLines2 = 4;
        logger.info("Add a second row");
        lottoPage.centreElement(LottoPage.AddNewTicketButton);
        lottoPage.click(LottoPage.AddNewTicketButton);
        lottoPage.sendKeys(BackofficePage.FormControlNameTitle2, ticketOptionTitle2);
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField2, "6");
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField2, "4");
        lottoPage.sendKeys(LottoPage.LottoTicketPricerPerTicketLineField3, "5");
        assertTrue(lottoPage.isElementDisplayed(LottoPage.DiscountText2));
        assertFalse(lottoPage.isElementDisplayed(LottoPage.NoDiscountText2 ));
        assertTrue(lottoPage.isElementDisplayed(LottoPage.RemoveTicketOptionButton2));

        logger.info("Submit, cancel and check, then Submit again");
        lottoPage.centreElement(LottoPage.SaveAndPublishLottoButton);
        lottoPage.click(LottoPage.SaveAndPublishLottoButton);
        lottoPage.findOnPage(LottoPage.ConfirmTicketOptionsTicket);
        lottoPage.findOnPage(LottoPage.SavingAndPublishingLottoInformationText);
        lottoPage.findOnPage("//strong[contains(.,'"+ticketOptionTitle1+"')]");
        lottoPage.findOnPage("//div[contains(.,'"+numberOfDraws1+" draws with "+numberOfLines1+" lines = £5.00')]");
        lottoPage.findOnPage("//strong[contains(.,'"+ticketOptionTitle2+"')]");
        lottoPage.findOnPage("//div[contains(.,'"+numberOfDraws2+" draws with "+numberOfLines2+" lines = £5.00')]");
        lottoPage.click(LottoPage.LottoPopUpCancelButton);
        assertThat(lottoPage.getElementAttribute(BackofficePage.FormControlNameTitle, "value"),containsString(ticketOptionTitle1));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfDrawsField, "value"),containsString("7"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoNumberOfLinesField, "value"),containsString("3"));
        assertThat(lottoPage.getElementAttribute(LottoPage.LottoTicketPricePerLineField, "value"),containsString("1"));
        lottoPage.click(LottoPage.SaveAndPublishLottoButton);
        lottoPage.findOnPage(LottoPage.ConfirmTicketOptionsTicket);
        lottoPage.findOnPage(LottoPage.SavingAndPublishingLottoInformationText);
        lottoPage.click(LottoPage.LottoSaveAndPublishButton);
        lottoPage.waitUntilElementInvisible(LottoPage.LottoSuccessfullyCreatedNotification, 5);
        logger.info("Expecting to redirect to Draws page");

        if (!lottoPage.isElementDisplayed(LottoPage.CurrentDrawHeading)) {
            logger.error("Did not redirect as expected");
        } else {
            logger.info("Arrived on Draws page");
        }

        if (!lottoPage.isElementDisplayed(BackofficePage.LottoDrawsTile)) {
            lottoPage.click(BackofficePage.LottoLeftNav);
            lottoPage.findOnPage(LottoPage.LottoDrawsSubSection);
            lottoPage.findOnPage(BackofficePage.LottoDetailsLeftNav);
        }

        int dayOfFirstDraw = 25;
        lottoPage.click(BackofficePage.DashboardTitleLeftNav);
        lottoPage.findOnPage("//p[contains(.,'Current draw: "+dayOfFirstDraw+"')]");

        logger.info("Find current draw revenue");
        lottoPage.findOnPage(LottoPage.CurrentDrawRevenueHeading);
        lottoPage.findOnPage(LottoPage.PriceAmount000);
        lottoPage.findOnPage(LottoPage.SincePreviousDrawText);
    }
}
