package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.RevenueReportPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class RevenueReportSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public RevenueReportSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.revenueReportPage = driverManager.revenueReportPage;
    }

    @Then("ClubAdmin can search by source")
    public void searchBySourceRevenuePage(){
        verifyRevenuePageElements();
        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Pat");

        logger.info("Selecting terminal source option");
        revenueReportPage.click(RevenueReportPage.RevenueReportAllSourceFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportSourceTerminalOption);
        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Patrick Maverick")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        logger.info("Clearing search field");
        revenueReportPage.clearInputFieldUsingBackSpaceKey(RevenueReportPage.RevenueReportSearchBar);

        logger.info("Selecting website source option");
        revenueReportPage.click(RevenueReportPage.RevenueReportTerminalSourceFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportSourceWebsiteOption);

        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Tra");
        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementPresent(String.format(RevenueReportPage.RevenueReportPageText, "Tracey Patterson")));
        assertFalse(revenueReportPage.isElementPresent(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        logger.info("Clearing search field");
        revenueReportPage.clearInputFieldUsingBackSpaceKey(RevenueReportPage.RevenueReportSearchBar);

        logger.info("Selecting ALL option");
        revenueReportPage.click(RevenueReportPage.RevenueReportWebsiteSourceFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportSourceAllOption);

        logger.info("Enter random letters into search field");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "kjhu");
        revenueReportPage.waitThreeSeconds();

        logger.info("Checking if default empty state is displayed");
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
    }

    @Then("ClubAdmin can search by type")
    public void searchByTypeRevenueReport(){
        verifyRevenuePageElements();
        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Dia");
        revenueReportPage.click(RevenueReportPage.RevenueReportAllTypeFilter);

        logger.info("Click payment option");
        revenueReportPage.click(RevenueReportPage.RevenueReportPaymentTypeOption);
        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Dianne Peterson")));
        assertTrue(revenueReportPage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Payment')])[1]"));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        logger.info("Click refund option");
        revenueReportPage.clear(RevenueReportPage.RevenueReportSearchBar);
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Pen");
        revenueReportPage.click(RevenueReportPage.RevenueReportPaymentTypeFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportRefundTypeOption);
        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Pennybridge Admin")));
        assertTrue(revenueReportPage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Refund')])[1]"));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        revenueReportPage.clear(RevenueReportPage.RevenueReportSearchBar);
        revenueReportPage.click(RevenueReportPage.RevenueReportRefundTypeFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportTypeAllOption);
        revenueReportPage.waitThreeSeconds();
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
    }

    @Then("ClubAdmin can search using date ranges")
    public void searchByDateRangesRevenueReport(){
        verifyRevenuePageElements();
        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Dar");

        logger.info("Select dates for taken date filter");
        revenueReportPage.click(RevenueReportPage.RevenueReportTakenDateFilter);
        revenueReportPage.waitTwoSeconds();
        logger.info("Checking next month button is set to disabled");
        assertThat("Next month button is NOT set to DISABLED!", revenueReportPage.getElementAttribute(RevenueReportPage.NextMonthArrow, "disabled"), containsString("true"));

        revenueReportPage.click(RevenueReportPage.PreviousMonthArrow);
        revenueReportPage.waitHalfSecond();
        revenueReportPage.click(RevenueReportPage.Date1stOfMonth);
        revenueReportPage.waitHalfSecond();
        revenueReportPage.click(RevenueReportPage.NextMonthArrow);
        revenueReportPage.waitTwoSeconds();
        revenueReportPage.click(RevenueReportPage.Date1stOfNextMonth);

        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
        revenueReportPage.click(RevenueReportPage.DateFilterClearButton);
        revenueReportPage.waitFifteenSeconds();

        logger.info("Click due date filter");
        revenueReportPage.click(RevenueReportPage.RevenueReportDueDateFilter);

        logger.info("Checking previous month button is set to disabled");
        assertThat("Previous month button is NOT set to DISABLED!", revenueReportPage.getElementAttribute(RevenueReportPage.PreviousMonthArrow, "disabled"), containsString("true"));

        logger.info("Select dates for due date filter");
        revenueReportPage.click(RevenueReportPage.DueDateFromNextMonthArrow);
        revenueReportPage.click(RevenueReportPage.Date1stOfMonth);
        revenueReportPage.click(RevenueReportPage.DueDateFromNextMonthArrow);
        revenueReportPage.click(RevenueReportPage.Date1stOfMonth);
        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
    }

    @Then("ClubAdmin can search by order status")
    public void searchByStatusRevenueReport(){
        revenueReportPage.waitThirtySeconds();
        verifyRevenuePageElements();
        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Dar");
        revenueReportPage.click(RevenueReportPage.RevenueReportAllStatusFilter);

        logger.info("Click cancelled option");
        revenueReportPage.click(RevenueReportPage.RevenueReportCancelledStatusOption);
        revenueReportPage.waitThreeSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Darragh Lally")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.CancelledBadge));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.CompleteBadge));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        logger.info("Click complete option");
        revenueReportPage.click(RevenueReportPage.RevenueReportCancelledStatusFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportCompleteStatusOption);
        revenueReportPage.waitFiveSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Darragh Lally")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.CompleteBadge));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.CancelledBadge));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
    }

    @Then("ClubAdmin can search by product")
    public void searchByProduct(){
        verifyRevenuePageElements();
        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Penny");

        logger.info("Click product filter");
        revenueReportPage.click(RevenueReportPage.ProductAllFilter);
        revenueReportPage.click(RevenueReportPage.ProductMembershipOption);
        revenueReportPage.waitThreeSeconds();

        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Pennybridge Admin")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "543773")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        logger.info("Clear search bar");
        revenueReportPage.clear(RevenueReportPage.RevenueReportSearchBar);

        logger.info("Enter name into search bar");
        revenueReportPage.sendKeys(RevenueReportPage.RevenueReportSearchBar, "Dar");
        revenueReportPage.click(RevenueReportPage.ProductMembershipFilter);
        revenueReportPage.click(RevenueReportPage.ProductLottoOption);
        revenueReportPage.waitThreeSeconds();

        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Darragh Lally")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "299556")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "299555")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));

        logger.info("Clear search bar");
        revenueReportPage.clear(RevenueReportPage.RevenueReportSearchBar);
        revenueReportPage.click(RevenueReportPage.ProductLottoFilter);
        revenueReportPage.click(RevenueReportPage.ProductAllOption);
        revenueReportPage.waitThreeSeconds();
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
    }

    @Then("ClubAdmin can search using all filters")
    public void searchUsingAllFiltersRevenueReport(){
        verifyRevenuePageElements();
        logger.info("Filter by website source");
        revenueReportPage.click(RevenueReportPage.RevenueReportAllSourceFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportSourceWebsiteOption);
        revenueReportPage.click(RevenueReportPage.RevenueReportHeading);

        logger.info("Filter by lotto product");
        revenueReportPage.click(RevenueReportPage.ProductAllFilter);
        revenueReportPage.click(RevenueReportPage.ProductLottoOption);
        revenueReportPage.click(RevenueReportPage.RevenueReportHeading);

        logger.info("Filter by complete status");
        revenueReportPage.click(RevenueReportPage.RevenueReportAllStatusFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportCompleteStatusOption);
        revenueReportPage.click(RevenueReportPage.RevenueReportHeading);

        logger.info("Filter by payment type");
        revenueReportPage.click(RevenueReportPage.RevenueReportAllTypeFilter);
        revenueReportPage.click(RevenueReportPage.RevenueReportPaymentTypeOption);
        revenueReportPage.click(RevenueReportPage.RevenueReportHeading);

        logger.info("Filter by taken date");
        revenueReportPage.click(RevenueReportPage.RevenueReportTakenDateFilter);
        revenueReportPage.click(RevenueReportPage.CalenderYearButton);
        revenueReportPage.click(RevenueReportPage.CalenderYear2023);
        revenueReportPage.click(RevenueReportPage. CalenderMonthApril);
        revenueReportPage.click(RevenueReportPage.Calender18thMonth);
        revenueReportPage.click(RevenueReportPage.Calender19thMonth);

        revenueReportPage.waitFiveSeconds();
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Pennybridge Admin")));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "543773")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.OrderItemAndOrderAmount));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Apr 18, 2023")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.TypeColumnPaymentText));
        assertTrue(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "1 – 1 of 1")));
        assertTrue(revenueReportPage.isElementDisplayed(RevenueReportPage.CompleteBadge));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.CancelledBadge));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "No revenue reports yet")));
        assertFalse(revenueReportPage.isElementDisplayed(String.format(RevenueReportPage.RevenueReportPageText, "Revenue reports will appear here")));
        assertFalse(revenueReportPage.isElementDisplayed(RevenueReportPage.RevenueReportEmptyStateIcon));
    }

    @Then("ClubAdmin can download revenue report CSV")
    public void downloadCSVRevenueReport(){
        verifyRevenuePageElements();
        logger.info("Downloading revenue report CSV file");
        downloadRevenueCSVFileIfChrome();
    }

    @When("they go to order details page by clicking order ID link")
    public void clickOnOrderIDOnRevenueReport(){
        verifyRevenuePageElements();
        revenueReportPage.click(RevenueReportPage.ProductAllFilter);
        revenueReportPage.click(RevenueReportPage.ProductLottoOption);
        revenueReportPage.waitThreeSeconds();
        revenueReportPage.findOnPage(RevenueReportPage.OrderIDRow1);
        String orderID = revenueReportPage.getElementAttribute(RevenueReportPage.OrderIDRow1, "textContent");
        revenueReportPage.click(RevenueReportPage.OrderIDRow1);
        revenueReportPage.findOnPage(RevenueReportPage.OrderDetailsHeadingBackoffice);
        revenueReportPage.findOnPage("//span[@data-test='order-details.orderId'][contains(.,'"+orderID.substring(1, orderID.length()-1)+"')]");
    }

    @And("they click the back button on order details page")
    public void clickBackButton(){
        revenueReportPage.findOnPage(RevenueReportPage.OrderDetailsHeadingBackoffice);
        logger.info("Clicking back button on order details page");
        revenueReportPage.click(RevenueReportPage.OrderDetailsPageBackButton);
    }

    @Then("they are brought back to the Revenue port page with the same filters as the previously had")
    public void checkAdminIsBackOnRevenuePage(){
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportHeading);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportSearchBar);
        revenueReportPage.findOnPage(RevenueReportPage.TotalRevenueTile);
        revenueReportPage.findOnPage(RevenueReportPage.TotalRevenueTooltip);
        revenueReportPage.findOnPage(RevenueReportPage.PendingRevenueTile);
        revenueReportPage.findOnPage(RevenueReportPage.PendingRevenueTooltip);
        revenueReportPage.findOnPage(RevenueReportPage.ProductLottoFilter);
        logger.info("Filters chosen remain");
    }

    public void verifyRevenuePageElements(){
        logger.info("Verify page elements");
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportHeading);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportInformationText);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportAllSourceFilter);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportAllTypeFilter);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportDueDateFilter);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportTakenDateFilter);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportOrderIDColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportCustomerColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportAmountColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportTakenDateColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportDueDateColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportTypeColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportStatusColumnHeader);
        revenueReportPage.findOnPage(RevenueReportPage.RevenueReportSearchBar);
        revenueReportPage.findOnPage(RevenueReportPage.TotalRevenueTile);
        revenueReportPage.findOnPage(RevenueReportPage.TotalRevenueTooltip);
        revenueReportPage.findOnPage(RevenueReportPage.PendingRevenueTile);
        revenueReportPage.findOnPage(RevenueReportPage.PendingRevenueTooltip);
    }

    public void downloadRevenueCSVFileIfChrome(){
        if (driverManager.agent.contains("chrome")) {
            revenueReportPage.waitThreeSeconds();
            logger.info("If we are on Chrome we also download CSV");
            revenueReportPage.click("//button[contains(.,'download DOWNLOAD CSV')]");
            revenueReportPage.waitThirtySeconds();
            logger.info("Checking file name downloaded is 'revenue_report-*****' ");
            revenueReportPage.isDownloadedInChrome(Collections.singletonList("revenue_report-"), "revenue_report");
        }
    }
}
