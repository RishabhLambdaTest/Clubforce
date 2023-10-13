package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.LottoPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LottoCalculationCheckSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public LottoCalculationCheckSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.myAccountPage = driverManager.myAccountPage;
        this.backofficePage = driverManager.backofficePage;
        this.lottoPage = driverManager.lottoPage;
    }

    @Then("user fills out the lotto information and settings pages")
    public void userEntersInLottoDetails() {

        logger.info("Adding lotto description");
        Lorem lorem = LoremIpsum.getInstance();
        String ShortDescriptionHOLDER = lorem.getWords(10, 10);
        lottoPage.waitTwoSeconds();
        lottoPage.sendKeys(LottoPage.LottoDescriptionField, ShortDescriptionHOLDER);

        logger.info("Go to second page");
        lottoPage.click(LottoPage.NextLottoSettingsBottomButton);

        logger.info("Enter lotto details");
        lottoPage.waitTwoSeconds();
        logger.info("Set date to next month, 25th");
//        lottoPage.findOnPage(LottoPage.LottoFirstDrawText);
        lottoPage.waitOneSecond();
        lottoPage.click(LottoPage.LottoDatePicker);
        lottoPage.waitOneSecond();
        lottoPage.click(LottoPage.LottoNextMonthDateOption);
        lottoPage.waitOneSecond();
        lottoPage.click(LottoPage.Pick25thDayOfMonth);

        String SelectedDateHOLDER = lottoPage.getElementAttribute("//input[contains(@data-mat-calendar,'mat-datepicker-0')]", "value");
        logger.info("SelectedDateHOLDER "+SelectedDateHOLDER);
        logger.info("Set cut-off time to 6pm");
        lottoPage.waitTwoSeconds();
        lottoPage.sendKeys(LottoPage.LottoCutOffTime,"1800");
        lottoPage.click("//h3[contains(.,'Schedule draw')]");

        logger.info("Set Schedule to every month on the 25th");
        lottoPage.waitTwoSeconds();
        lottoPage.click(LottoPage.LottoScheduleType);
        lottoPage.waitOneSecond();
        lottoPage.click(LottoPage.LottoSchedule25thOfEveryMonth);

        logger.info("Set start and end range");
        lottoPage.waitTwoSeconds();
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.LottoPickRangeText);
        lottoPage.click(LottoPage.LottoPickNumbers1To32Option);
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.LottoEndRangeText);
        lottoPage.click(LottoPage.LottoEndRangeOption24);
        lottoPage.click(LottoPage.PickRangeHeading);
        lottoPage.click(LottoPage.LottoNumberOfPicks);
        lottoPage.click(LottoPage.LottoNumberOfPicksOption6);

        logger.info("Fill in prize details");
        lottoPage.waitTwoSeconds();
        lottoPage.click(LottoPage.PrizesHeading);
        lottoPage.sendKeys(LottoPage.LottoPrizeAmountText, "300");
        lottoPage.click(LottoPage.PrizesHeading);

        logger.info("Check validation works");
        lottoPage.click(LottoPage.AddAnotherPrizeButton);
        lottoPage.sendKeys("(//input[contains(@formcontrolname,'amount')])[2]", "200");
        lottoPage.click("(//div[contains(.,'Select winning criteria')])[22]");
        lottoPage.click("//span[@class='mat-option-text'][contains(.,'Jackpot')]");
        assertTrue(lottoPage.isElementPresent("//mat-error[@aria-atomic='true'][contains(.,'Oops, looks like you have a duplicate prize selected - Jackpot. Please select another prize option')]"));
        lottoPage.click("//mat-icon[contains(.,'delete_outline')]");
        assertFalse(lottoPage.isElementPresent("//mat-error[@aria-atomic='true'][contains(.,'Oops, looks like you have a duplicate prize selected - Jackpot. Please select another prize option')]"));
        lottoPage.click("(//div[contains(.,'Jackpot')])[20]");
        lottoPage.waitOneSecond();
        lottoPage.click("//span[@class='mat-option-text'][contains(.,'Match 3')]");
        lottoPage.waitOneSecond();
        assertTrue(lottoPage.isElementPresent("//mat-error[@aria-atomic='true'][contains(.,'Please enter your jackpot amount')]"));
        lottoPage.waitOneSecond();
        lottoPage.click("(//div[contains(.,'Match 3')])[20]");
        lottoPage.waitOneSecond();
        lottoPage.click("//span[@class='mat-option-text'][contains(.,'Jackpot')]");
        assertFalse(lottoPage.isElementPresent("//mat-error[@aria-atomic='true'][contains(.,'Please enter your jackpot amount')]"));

    }

    @And("they fill in lotto ticket options with price per line set to {string} {string} {string} {string}")
    public void userFillsInLottoTicketOptions(String pricePerLine , String draws, String lines, String pricePerTicket) {
        logger.info("Going to Discount settings Page");
        lottoPage.click(LottoPage.LottoDiscountSettingsButton);
        lottoPage.sendKeys(LottoPage.LottoTicketPricePerLineField, pricePerLine);
        lottoPage.click("//h1[contains(.,'Lotto setup')]"); //defocus
        lottoPage.waitTwoSeconds();
        lottoPage.sendKeys(LottoPage.LottoNumberOfDrawsField, draws);
        lottoPage.waitTwoSeconds();
        lottoPage.sendKeys(LottoPage.LottoNumberOfLinesField, lines);
        lottoPage.waitTwoSeconds();
        lottoPage.clearInputFieldUsingBackSpaceKey(LottoPage.LottoTicketPricerPerTicketLineField2);
        lottoPage.sendKeys(LottoPage.LottoTicketPricerPerTicketLineField2, pricePerTicket);
        //lottoPage.click(LottoPage.LottoCostBeforeDiscountText); //defocus
        lottoPage.waitTwoSeconds();

        switch (pricePerLine) {
            case "1":
                logger.info("Check cost before discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€1.00')]");
                logger.info("Check discount");
                lottoPage.isElementDisplayed(LottoPage.NoDiscountText);
                lottoPage.click("//span[contains(@id,'bo--lotto--setup--ticket-options--next')]");
                lottoPage.waitTwoSeconds();
                lottoPage.click("(//div[contains(.,'Confirm Ticket Options  3 for fiver  1 draws with 1 lines = €5.00 Once Saved and Published your lotto will be publicly available to play.')])[6]");
                lottoPage.click(LottoPage.LottoGoBackButton);
                break;
            case "2":
                lottoPage.isElementDisplayed("//span[contains(.,'€8.00')]");
                logger.info("Check discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€2.00')]");
                lottoPage.click("//span[contains(@id,'bo--lotto--setup--ticket-options--next')]");
                lottoPage.findOnPage("(//div[contains(.,'Confirm Ticket Options  3 for fiver  2 draws with 2 lines = €6.00 Once Saved and Published your lotto will be publicly available to play.')])[6]");
                lottoPage.click(LottoPage.LottoGoBackButton);
                break;
            case "3":
                logger.info("Check cost before discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€27.00')]");
                logger.info("Check discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€20.00')]");
                lottoPage.click("//span[contains(@id,'bo--lotto--setup--ticket-options--next')]");
                lottoPage.findOnPage("(//div[contains(.,'Confirm Ticket Options  3 for fiver  3 draws with 3 lines = €7.00 Once Saved and Published your lotto will be publicly available to play.')])[6]");
                lottoPage.click(LottoPage.LottoGoBackButton);
                break;
            case "4":
                logger.info("Check cost before discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€64.00')]");
                logger.info("Check discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€56.00')]");
                lottoPage.click("//span[contains(@id,'bo--lotto--setup--ticket-options--next')]");
                lottoPage.findOnPage("(//div[contains(.,'Confirm Ticket Options  3 for fiver  4 draws with 4 lines = €8.00 Once Saved and Published your lotto will be publicly available to play.')])[6]");
                lottoPage.click(LottoPage.LottoGoBackButton);
                break;
            case "5":
                logger.info("Check cost before discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€125.00')]");
                logger.info("Check discount");
                lottoPage.isElementDisplayed("//span[contains(.,'€116.00')]");
                lottoPage.click("//span[contains(@id,'bo--lotto--setup--ticket-options--next')]");
                lottoPage.findOnPage("(//div[contains(.,'Confirm Ticket Options  3 for fiver  5 draws with 5 lines = €9.00 Once Saved and Published your lotto will be publicly available to play.')])[6]");
                lottoPage.click(LottoPage.LottoGoBackButton);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for userFillsInLottoTicketOptions");
        }
    }
}
