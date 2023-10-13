package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.PayoutPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertTrue;

public class PayoutSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();
    WebDriverManager driverManager;

    public PayoutSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.payoutPage= driverManager.payoutPage;
    }

    @And("{string} is displayed on payouts page")
    public void checkCurrencySymbolOnPayoutsPage(String currencySymbol) {
        switch (currencySymbol) {
            case "Euro":
                payoutPage.findOnPage(PayoutPage.PayoutsPageEuroSymbol);
                break;
            case "Pound":
                payoutPage.findOnPage(PayoutPage.PayoutsPagePoundSymbol);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkCurrencySymbolOnPayoutsPage!");
        }
    }

    @Then("Lotto payout reports are available")
    public void LottoPayoutReportsAreAvailable () {
        logger.info("Looking for lotto payout reports");
        payoutPage.findOnPage(PayoutPage.PayoutPageHeading);
        verifyLottoPayoutPageElements();
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPagePaidStatusBadge));

//    Check dates to see if a new report was added on a Monday
        logger.info("Weekday is "+ SeleniumUtilities.getDateTimeFormat("EEEEEE"));
        if (SeleniumUtilities.getDateTimeFormat("EEEEEE").contains("Monday")) {
            logger.info("Today is Monday, checking there is a payout report for today");
            assertTrue(payoutPage.isElementDisplayed("//span[contains(.,'"+SeleniumUtilities.getDateTimeFormat("dd-MM-yyyy")+"')]"));
        } else {
            logger.info("Today is not Monday, NOT checking there is a payout report for today");
        }
    }

    public void verifyLottoPayoutPageElements() {
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageHeading));
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageInformationText));
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageAmountColumnHeader));
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageDateColumnHeader));
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageStatusColumnHeader));
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageAccountColumnHeader));
        assertTrue(payoutPage.isElementDisplayed(PayoutPage.PayoutPageDetailsColumnHeader));
    }
}
