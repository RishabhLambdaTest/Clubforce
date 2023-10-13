package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LottoPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public LottoPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String LottoPageText = "//*[contains(text(),'%s')]";
    public static final String LottoMenuItem = "//span[@class='mat-button-wrapper'][contains(.,'Lotto')]";
    public static final String LottoConfirmButton = "//span[contains(.,'Confirm')]";
    public static final String FirstLottoBallDisplayingNumber1 = "//button[contains(.,'1')]";
    public static final String NextLottoSettingsBottomButton = "//span[contains(.,'Lotto Settings')]";
    public static final String NextLottoDiscountBottomButton = "//span[contains(.,'Ticket Options')]";
    public static final String SaveAndPublishLottoButton = "//button[contains(.,'Save and Publish')]";
    public static final String ReportsOrdersSubSection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Orders')]";
    public static final String RevenueReportOption = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Revenue report')]";
    public static final String LottoSection = "//span[@class='ms-2'][contains(.,'Lotto')]";
    public static final String LottoDrawsSubSection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Draws')]";
    public static final String LottoPastDrawRow = "(//td[@role='gridcell'])[1]";
    public static final String LottoWinningPicksButton = "//button[contains(.,'Winning Picks arrow_forward')]";
    public static final String LottoDescriptionField = "//textarea[contains(@formcontrolname,'short_description')]";
    public static final String LottoDatePicker = "//button[contains(@class,'mat-focus-indicator mat-icon-button mat-button-base')]";
    public static final String LottoNextMonthDateOption = "//button[contains(@aria-label,'Next month')]";
    public static final String Pick25thDayOfMonth = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'25')]";
    public static final String LottoCutOffTime = "//input[@formcontrolname='time']";
    public static final String LottoScheduleType = "(//div[contains(.,'Select schedule type')])[18]";
    public static final String LottoSchedule25thOfEveryMonth = "//span[@class='mat-option-text'][contains(.,'Every month on the 25th')]";
    public static final String LottoPickRangeText = "(//div[contains(.,'Pick range')])[20]";
    public static final String LottoPickNumbers1To32Option = "//span[@class='mat-option-text'][contains(.,'Numbers (e.g. 1-32)')]";
    public static final String LottoEndRangeText = "//input[contains(@formcontrolname,'end')]";
    public static final String LottoEndRangeOption24 = "//span[contains(.,'24')]";
    public static final String LottoNumberOfPicks = "(//div[contains(.,'How many picks')])[18]";
    public static final String LottoNumberOfPicksOption6 = "//span[@class='mat-option-text'][contains(.,'6')]";
    public static final String LottoPrizeAmountText = "//input[contains(@formcontrolname,'amount')]";
    public static final String LottoDiscountSettingsButton = "//span[contains(.,'Ticket Options')]";
    public static final String LottoTicketPricePerLineField = "//input[contains(@formcontrolname,'price')]";
    public static final String LottoTicketPricerPerTicketLineField2 = "(//input[contains(@formcontrolname,'price')])[2]";
    public static final String LottoTicketPricerPerTicketLineField3 = "(//input[contains(@formcontrolname,'price')])[3]";
    public static final String LottoTicketPricePerLineField2 = "(//input[@formcontrolname='price'])[2]";
    public static final String LottoNumberOfDrawsField = "//input[contains(@formcontrolname,'draws')]";
    public static final String LottoNumberOfDrawsField2 = "(//input[contains(@formcontrolname,'draws')])[2]";
    public static final String LottoNumberOfLinesField = "//input[contains(@formcontrolname,'lines')]";
    public static final String LottoNumberOfLinesField2 = "(//input[contains(@formcontrolname,'lines')])[2]";
    public static final String LottoSaveAndPublishButton = "//button[contains(.,'Save and Publish Lotto')]";
    public static final String LottoGoBackButton = "//span[contains(.,'Cancel')]";
    public static final String LottoInformationHeader = "//strong[contains(.,'Lotto Information')]";
    public static final String LottoDrawsEditIcon = "(//mat-icon[contains(.,'edit')])[1]";
    public static final String JackpotAmountField = "//input[@data-placeholder='Enter Jackpot Amount']";
    public static final String PrizesHeading = "//h3[contains(.,'Prizes')]";
    public static final String LottoDrawsHeading = "//h1[contains(.,'Lotto draws')]";
    public static final String CurrentDrawJackpotLabel = "(//span[contains(.,'Jackpot')])[1]";
    public static final String AutoRenewalCheckBoxChecked = "//span[@class='mat-checkbox-inner-container']//input[contains(@aria-checked,'true')]";
    public static final String AddToCartButton = "//button[contains(.,'Add to cart')]";
    public static final String QuickPickButton = "(//button[contains(.,'Quick Pick')])[1]";
    public static final String SelectButton = "//button[contains(.,'Select')]";
    public static final String FirstLottoPicksCircle = "(//button[contains(@data-test,'lottery-product.lineOptionButton')])[1]";
    public static final String LottoPickNumber1 = "(//button[@data-test='products-dialog.pickOptionButton'])[1]";
    public static final String LottoPickNumber2 = "(//button[@data-test='products-dialog.pickOptionButton'])[2]";
    public static final String LottoPickNumber3 = "(//button[@data-test='products-dialog.pickOptionButton'])[3]";
    public static final String LottoPickNumber4 = "(//button[@data-test='products-dialog.pickOptionButton'])[4]";
    public static final String LottoPickNumber5 = "(//button[@data-test='products-dialog.pickOptionButton'])[5]";
    public static final String FirstClearButton = "(//button[contains(.,'Clear')])[1]";
    public static final String SecondClearButton = "(//button[contains(.,'Clear')])[2]";
    public static final String ThirdClearButton = "(//button[contains(.,'Clear')])[3]";
    public static final String RenewOnceExpiredDialog = "//mat-dialog-container[contains(.,'Are you happy for your lotto ticket to automatically renew once expired? No  Yes')]";
    public static final String NoButton = "//span[contains(@id,'dialog--cancel')]";
    public static final String YesButton = "//span[contains(.,'Yes')]";
    public static final String AutoRenewCheckBox = "//span[@class='mat-checkbox-inner-container']";
    public static final String AutoRenewCheckBoxUnChecked = "//span[@class='mat-checkbox-inner-container']//input[contains(@aria-checked,'false')]";
    public static final String CurrentDrawPlayslipButton = "(//button[contains(@data-test,'lottery-draws-list.playslipButton')])[1]";
    public static final String AgeVerificationPopUp = "//mat-dialog-content[contains(.,'To play lotto you have to be over 18 years of age.')]";
    public static final String SuccessfullyAddedToCartNotification = "//span[contains(.,'successfully added to the cart')]";
    public static final String LottoQLEditorBlank = "//div[contains(@class,'ql-editor ql-blank')]";
    public static final String LottoInformationStepperHeading = "//strong[contains(.,'Lotto Information')]";
    public static final String LottoSetUpStepOneLottoSettingsButton = "//span[contains(@id,'bo--lotto--setup--information--next')]";
    public static final String DescriptionIsRequiredErrorMessage = "//mat-error[contains(.,'Description is required')]";
    public static final String LottoImageInformationText = "//small[contains(.,'One image only. Minimum size must be 1600 pixels x 900 pixels, this is to allow your users’ social sharing')]";
    public static final String LottoImageFilePath = "//input[@type='file']";
    public static final String LottoSepUpLottoInformationStepper1 = "//*[contains(text(),'Lotto Information')]";
    public static final String LottoDateField = "//input[contains(@data-mat-calendar,'mat-datepicker-0')]";
    public static final String ScheduleDrawHeading = "//h3[contains(.,'Schedule draw')]";
    public static final String LottoScheduleDropDown = "(//*[contains(@class,'mat-select-placeholder')])[1]";
    public static final String EveryMonthOnThe25thOption = "//span[contains(.,'Every month on the 25th')]";
    public static final String LottoScheduleField = "//input[contains(@data-mat-calendar,'mat-datepicker-0')]";
    public static final String PickRangeHeading = "//h3[contains(.,'Pick range')]";
    public static final String PickRangeDropDown = "(//*[contains(@class,'mat-select-placeholder')])[1]";
    public static final String PickRangeNumberOption = "//span[contains(.,'Numbers (e.g. 1-32)')]";
    public static final String PickRangeLettersOption = "//span[@class='mat-option-text'][contains(.,'Letters A-Z')]";
    public static final String EndRangeDropDown = "//input[contains(@formcontrolname,'end')]";
    public static final String StartRangeField = "(//input[contains(@formcontrolname,'start')])[2]";
    public static final String NumberOption24 = "//span[@class='mat-option-text'][contains(.,'24')]";
    public static final String NumberOption5 = "//span[@class='mat-option-text'][contains(.,'5')]";
    public static final String StartRangeMustBeLessThanEndRangeErrorText = "//mat-error[contains(.,'Start range value must be less than End range value')]";
    public static final String ValueMustBeBetween1And60ErrorText = "//mat-error[contains(.,'Value must be between 1 and 60')]";
    public static final String PrizeAmountField1 = "(//input[contains(@formcontrolname,'amount')])[1]";
    public static final String PrizeAmountField2 = "(//input[contains(@formcontrolname,'amount')])[2]";
    public static final String PrizeAmountField3 = "(//input[contains(@formcontrolname,'amount')])[3]";
    public static final String PrizeAmountField4 = "(//input[contains(@formcontrolname,'amount')])[4]";
    public static final String AddAnotherPrizeButton = "//span[contains(.,'Add Another Prize')]";
    public static final String PrizeTypeDropDown = "(//span[contains(@class,'mat-select-placeholder mat-select-min-line')])[1]";
    public static final String Match4Option = "//span[contains(.,'Match 4')]";
    public static final String Match3Option = "//span[contains(.,'Match 3')]";
    public static final String PriceMustBeHigherThanErrorText = "//mat-error[contains(.,'Price has to be higher than')]";
    public static final String LottoSetupHeading = "//h1[contains(.,'Lotto setup')]";
    public static final String NoDiscountText = "//strong[contains(.,'No Discount')]";
    public static final String NoDiscountText2 = "(//strong[contains(.,'No Discount')])[2]";
    public static final String RemoveTicketOptionButton = "//button[contains(.,'indeterminate_check_box Remove ticket option')]";
    public static final String RemoveTicketOptionButton2 = "(//button[contains(.,'indeterminate_check_box Remove ticket option')])[2]";
    public static final String DiscountText = "//strong[contains(.,'Discount')]";
    public static final String DiscountText2 = "(//strong[contains(.,'Discount')])[2]";
    public static final String ValueOfPricePerTicketErrorMessage = "//mat-error[contains(.,'Value of your price per ticket must be higher than €5.00 (Your price per line × Number of lines)')]";
    public static final String RemoveTicketOption = "//button[contains(.,'Remove ticket option')]";
    public static final String AddNewTicketButton = "//button[contains(.,'Add new ticket')]";
    public static final String ConfirmTicketOptionsTicket = "//*[contains(text(),'Confirm Ticket Options')]";
    public static final String SavingAndPublishingLottoInformationText = "//mat-hint[contains(.,'Once Saved and Published your lotto will be publicly available to play.')]";
    public static final String SincePreviousDrawText = "//p[contains(.,'Since previous draw')]";
    public static final String PriceAmount000 = "//p[contains(.,'0.00')]";
    public static final String CurrentDrawRevenueHeading = "//h4[contains(.,'Current draw revenue')]";
    public static final String LottoSuccessfullyCreatedNotification = "//span[contains(.,'Successfully created.')]";
    public static final String CurrentDrawHeading = "//h2[contains(.,'Current draw')]";
    public static final String LottoPopUpCancelButton = "//span[contains(.,'Cancel')]";

    //Methods
    public void verifyLottoBasicPageElements() {
        logger.info("verifyLottoBasicPageElements");
        assertTrue(isElementPresent(String.format(LottoPageText, "Lotto Information")));
        assertTrue(isElementPresent(String.format(LottoPageText, "Description")));
        assertTrue(isElementPresent(String.format(LottoPageText, "Image")));
        assertTrue(isElementPresent(String.format(LottoPageText, "One image only. Minimum size must be 1600 pixels x 900 pixels, this is to allow your users’ social sharing")));
    }

    public void verifyLottoSettingsPageElements() {
        logger.info("verifyLottoSettingsPageElements");
        assertTrue(isElementPresent(String.format(LottoPageText, "Schedule draw")));
        assertTrue(isElementPresent(String.format(LottoPageText, "Pick range")));
        assertTrue(isElementPresent(String.format(LottoPageText, "Prizes")));
    }

    public void verifyLottoPurchasePageItems() {
        logger.info("verifyLottoDiscountPageElements");
        waitTwoSeconds();
        findOnPage("//span[contains(.,'Jackpot')]");
        findOnPage("(//div[contains(.,'Sales close')])[11]");
        findOnPage("//span[@class='mat-checkbox-inner-container']//input[contains(@aria-checked,'true')]");
        findOnPage("//mat-icon[@role='img'][contains(.,'help_outline')]");
    }

    public void downloadPayoutReportIfChrome() {
        if (driverManager.agent.contains("chrome")) {
            waitThreeSeconds();
            logger.info("If we are on Chrome we also download CSV");
            click("(//button[contains(.,'download Export to CSV')])[1]");
            waitThirtySeconds();
            logger.info("Checking file name downloaded is 'payouts-*****' ");
            isDownloadedInChrome(Collections.singletonList("payouts-"), "payouts");
        }
    }

    public void downloadPlayslipsCSVFileIfChrome() {
        if (driverManager.agent.contains("chrome")) {
            waitThreeSeconds();
            logger.info("If we are on Chrome we also download CSV");
            click("//button[contains(.,'arrow_downward download playslips (csv)')]");
            waitThirtySeconds();
            logger.info("Checking file name downloaded is 'draw-*****' ");
            waitFiveSeconds();
            isDownloadedInChrome(Collections.singletonList("draw-"), "draw");
        }
    }

    public void readInPlayerDetailsFromPage() {
        assertFalse(isElementPresent("//div[@class='col'][contains(.,'0 playslips were entered for this draw')]"));
        scrollPageToBottom();
        waitTwoSeconds();
        logger.info("Read page, click next until next button becomes disabled");

        List<String> pageMemberDetails = new ArrayList<>();
        pageMemberDetails.add(getElementAttribute("//cf-collection-table[contains(.,'')]","textContent"));

        while (!isElementPresent("//button[contains(@aria-label,'Next page')][@disabled='true']")) {
            logger.info("Range: "+getElementAttribute("//div[@class='mat-paginator-range-label']","textContent"));
            click("//button[contains(@aria-label,'Next page')]");
            findOnPage("//cf-collection-table[contains(.,'')]");
            pageMemberDetails.add(getElementAttribute("//cf-collection-table[contains(.,'')]","textContent"));
        }
        logger.info("Array is "+pageMemberDetails);
    }

    public void comparePageAndCSV() {
//        readInPlayerDetailsFromPage();


    }
}


