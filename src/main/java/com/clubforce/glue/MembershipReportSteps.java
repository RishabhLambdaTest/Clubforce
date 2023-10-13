package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.MembershipReportPage;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import java.util.Collections;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MembershipReportSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public MembershipReportSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.membershipReportPage =  driverManager.membershipReportPage;
        this.membershipPage = driverManager.membershipPage;
    }

    @Then("ClubAdmin can search by membership status")
    public void filterByMembershipStatus(){
        verifyMembershipPageElements();

        logger.info("Filter for expired plans");
        membershipReportPage.click(MembershipReportPage.MembershipReportStatusAllFilter);
        membershipReportPage.click(MembershipReportPage.MembershipStatusExpiredOption);
        membershipReportPage.waitThreeSeconds();
        for(int i = 1; i <= 7; i++) { //https://clubforce.atlassian.net/browse/CE-555
            membershipReportPage.findOnPage("(//p[@class='badge my-auto p-2 pill same-width ng-star-inserted bg-danger'][contains(.,'Expired')])["+i+"]");
            logger.info("Expired plan : " + i + " is displayed in membership status column");
        }

        logger.info("Filter for plans in arrears");
        membershipReportPage.click(MembershipReportPage.MembershipReportStatusExpiredFilter);
        membershipReportPage.click(MembershipReportPage.MembershipStatusArrearsOption);
        membershipReportPage.waitThreeSeconds();
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "No membership reports yet")));
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Membership reports will appear here")));
        assertTrue(membershipReportPage.isElementDisplayed(MembershipReportPage.MembershipReportsPageDefaultEmptyStateIcon));

        logger.info("Filter for active plans");
        membershipReportPage.click(MembershipReportPage.MembershipReportStatusArrearsFilter);
        membershipReportPage.click(MembershipReportPage.MembershipStatusActiveOption);
        membershipReportPage.waitThreeSeconds();
        for(int i = 1; i <10; i++) {
            membershipReportPage.findOnPage("(//p[@class='badge bg-success my-auto p-2 pill same-width ng-star-inserted'][contains(.,'Active')])["+i+"]");
            logger.info("Active plan : " + i + " is displayed in membership status column");
        }

        membershipReportPage.click(MembershipReportPage.MembershipReportStatusActiveFilter);
        membershipReportPage.click(MembershipReportPage.MembershipStatusAllOption);
        membershipReportPage.waitThreeSeconds();
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "No membership reports yet")));
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Membership reports will appear here")));
        assertFalse(membershipReportPage.isElementDisplayed(MembershipReportPage.MembershipReportsPageDefaultEmptyStateIcon));
    }

    @Then("ClubAdmin can search for membership records by gender")
    public void filterByMembershipGender(){
        verifyMembershipPageElements();

        logger.info("Select prefer not to say option");
        membershipReportPage.click(MembershipReportPage.MembershipGenderAllFilter);
        membershipReportPage.click(MembershipReportPage.GenderPreferNotToSayOption);
        membershipReportPage.escape();
        membershipReportPage.waitThreeSeconds();

        for(int i = 1; i < 10; i++){
          membershipReportPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Prefer Not To Say')])["+i+"]");
          logger.info("Row : " + i + " displays Prefer Not To Say");
        }

        membershipReportPage.click(MembershipReportPage.MembershipGenderPreferNotToSayFilter);
        logger.info("Select female");
        membershipReportPage.click(MembershipReportPage.GenderFemaleOption);
        logger.info("Deselect prefer not to say");
        membershipReportPage.click(MembershipReportPage.GenderPreferNotToSayOption);
        membershipReportPage.escape();
        membershipReportPage.waitThreeSeconds();

        for(int j = 1; j < 10; j++){
            membershipReportPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Female')])["+j+"]");
            logger.info("Row : " + j + " displays Female");
        }

        membershipReportPage.click(MembershipReportPage.MembershipReportFemaleFilter);
        logger.info("Select male");
        membershipReportPage.click(MembershipReportPage.GenderMaleOption);
        logger.info("Deselect female");
        membershipReportPage.click(MembershipReportPage.GenderFemaleOption);
        membershipReportPage.escape();
        membershipReportPage.waitThreeSeconds();

        for(int j = 1; j < 10; j++){
          membershipReportPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Male')])["+j+"]");
          logger.info("Row : " + j + " displays Male");
        }
    }

    @Then("ClubAdmin can search for membership record by DOB range")
    public void filterByMembershipDOBRange(){
        verifyMembershipPageElements();

        logger.info("Click DOB filter");
        membershipReportPage.click(MembershipReportPage.MembershipReportDOBFilter);
        membershipReportPage.click(MembershipReportPage.DateButtonArrow);
        membershipReportPage.click(MembershipReportPage.DatePreviousYearsArrowButton);

        logger.info("Selecting year 2003, month December, days 11th -12th");
        membershipReportPage.click(MembershipReportPage.Year1993Button);
        membershipReportPage.waitOneSecond();
        membershipReportPage.click(MembershipReportPage.JanuaryMonthButton);
        membershipReportPage.waitOneSecond();
        membershipReportPage.click(MembershipReportPage.MembershipReportDate1stOfMonth);
        membershipReportPage.waitOneSecond();
        membershipReportPage.click(MembershipReportPage.MembershipReportDate2ndOfMonth);
        membershipReportPage.click(MembershipReportPage.MembershipReportHeading);
        membershipReportPage.waitThreeSeconds();
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "No membership reports yet")));
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Membership reports will appear here")));
        assertFalse(membershipReportPage.isElementDisplayed(MembershipReportPage.MembershipReportsPageDefaultEmptyStateIcon));
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "donnaadmin")));
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Male")));
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Jan 1, 1993")));
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Priced parent plan")));
        assertTrue(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "n/a")));

        logger.info("Clear DOB filter");
        membershipReportPage.click(MembershipReportPage.MembershipReportDOBClearCrossIcon);
        membershipReportPage.waitTenSeconds();
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "No membership reports yet")));
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Membership reports will appear here")));
        assertFalse(membershipReportPage.isElementDisplayed(MembershipReportPage.MembershipReportsPageDefaultEmptyStateIcon));
        assertFalse(membershipReportPage.isElementDisplayed(String.format(MembershipReportPage.MembershipReportPageText, "Jan 1, 1993")));
    }

    @Then("ClubAdmin can search for membership record by plan name")
    public void filterByMembershipPlanName(){
        verifyMembershipPageElements();

        logger.info("Click plan name filter");
        membershipReportPage.click(MembershipReportPage.PlanNameAllFilter);
        membershipReportPage.click(MembershipReportPage.PricePlanForAutomationPlanNameOption);
        membershipReportPage.escape();
        membershipReportPage.waitThreeSeconds();

        for(int i = 1; i <= 10; i++){
           membershipReportPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Priced plan for automation')])["+i+"]");
           logger.info("Priced plan for automation id displayed in row " + i);
        }

        membershipReportPage.click(MembershipReportPage.PricedPlanPlanNameFilter);
        membershipReportPage.click(MembershipReportPage.AdultPlayingPlanNameOption);
        membershipReportPage.escape();
        membershipReportPage.waitThreeSeconds();

        logger.info("Clicking sort arrow for plan name");
        membershipReportPage.click(MembershipReportPage.PlanNameColumnHeader);
        membershipReportPage.waitThreeSeconds();
        for(int j = 1; j <= 2; j++){
            logger.info("Checking row number : " + j);
            membershipReportPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Adult Playing')])["+j+"]");
            logger.info("Adult playing is displayed in row :" + j);
        }

        logger.info("Clicking sort arrow again for plan name");
        membershipReportPage.click(MembershipReportPage.PlanNameColumnHeader);
        membershipReportPage.waitThreeSeconds();
        for(int k = 1; k <= 10; k++){
            logger.info("Checking row number : " + k);
            membershipReportPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Priced plan for automation')])["+k+"]");
            logger.info("Priced plan for automation :" + k);
        }

    }

    @Then("ClubAdmin can download membership {string} report CSV")
    public void downloadCSVMembershipReport(String type){
        verifyMembershipPageElements();
        switch (type){
            case "full":
                downloadMembershipFULLReportCSVFileIfChrome();
                break;
            case "quick":
                logger.info("Downloading report CSV file");
                downloadMembershipQuickReportCSVFileIfChrome();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for downloadCSVMembershipReport!");
        }

    }

    public void verifyMembershipPageElements(){
        logger.info("Verify page elements");
        membershipReportPage.findOnPage(MembershipReportPage.MembershipReportHeading);
        membershipReportPage.findOnPage(MembershipReportPage.MembershipReportsSubHeading);
        membershipReportPage.findOnPage(MembershipReportPage.TotalRegisteredMembersTileHeading);
        membershipReportPage.findOnPage(MembershipReportPage.TotalRegisteredMembersLastUpdatedText);
        membershipReportPage.findOnPage(MembershipReportPage.ArrearsTileHeading);
        membershipReportPage.findOnPage(MembershipReportPage.PlayerNonPlayerTileHeading);
        membershipReportPage.findOnPage(MembershipReportPage.PlayerNonPlayerLastUpdatedText);
        membershipReportPage.findOnPage(MembershipReportPage.MemberNameColumnHeader);
        membershipReportPage.findOnPage(MembershipReportPage.GenderColumnHeader);
        membershipReportPage.findOnPage(MembershipReportPage.DOBColumnHeader);
        membershipReportPage.findOnPage(MembershipReportPage.PlanNameColumnHeader);
        membershipReportPage.findOnPage(MembershipReportPage.MembershipStatusColumnHeader);
        membershipReportPage.findOnPage(MembershipReportPage.GuardianNameColumnHeader);
        membershipReportPage.findOnPage(MembershipReportPage.MembershipGenderAllFilter);
    }

    public void downloadMembershipQuickReportCSVFileIfChrome(){
        if (driverManager.agent.contains("chrome")) {
            membershipReportPage.waitThreeSeconds();
            logger.info("If we are on Chrome we also download CSV");
            membershipReportPage.click(MembershipReportPage.MembershipQuickReportExportToCSVButton);
            membershipReportPage.findOnPage(MembershipReportPage.ExportPopUpHeading);
            membershipReportPage.click(MembershipReportPage.MembershipPopUpExportButton);
            membershipReportPage.waitThirtySeconds();
            logger.info("Checking file name downloaded is 'Quick report'");
            membershipReportPage.isDownloadedInChrome(Collections.singletonList("Quick report"), "Quick report");
        }
    }

    public void downloadMembershipFULLReportCSVFileIfChrome(){
        if (driverManager.agent.contains("chrome")) {
            membershipReportPage.waitThreeSeconds();
            logger.info("If we are on Chrome we also download CSV");
            membershipReportPage.click(MembershipReportPage.MembershipFullReportExportCSVButton);
            membershipReportPage.findOnPage(MembershipReportPage.ExportPopUpHeading);
            membershipReportPage.click(MembershipReportPage.MembershipPopUpExportButton);
            membershipReportPage.waitThirtySeconds();
            logger.info("Checking file name downloaded is 'Members registration Full Report'");
            membershipReportPage.isDownloadedInChrome(Collections.singletonList("Members registration Full Report"), "Members registration Full Report");
        }
    }

}
