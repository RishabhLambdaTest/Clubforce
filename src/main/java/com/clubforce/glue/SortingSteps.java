package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SortingSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public SortingSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficePage = driverManager.backofficePage;
        this.loginPage = driverManager.loginPage;
        this.myAccountPage = driverManager.myAccountPage;
    }

    @And("sorting works for {string}")
    public void sortingIcons(String sortingPage) {
        logger.info("Click the " +  sortingPage + " sorting icons");
        backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
        switch (sortingPage) {
            case "articles":
                backofficePage.click("//span[contains(.,'Title')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Title')]",5);
                String articlesTitleBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Articles title before sorting click : " + articlesTitleBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Title')]");
                String articlesTitleAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Articles title after sorting click : " + articlesTitleAfterClick);
                backofficePage.waitThreeSeconds();
                assertFalse(articlesTitleBeforeSecondClick.equalsIgnoreCase(articlesTitleAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Published date')]",5);
                backofficePage.click("//span[contains(.,'Published date')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Published date')]",5);
                String articlePublishedDateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Published date before sorting click : " + articlePublishedDateBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Published date')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Published date')]",5);
                String articlePublishedDateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Published date after sorting click : " + articlePublishedDateAfterClick);
                backofficePage.waitThreeSeconds();
                assertFalse(articlePublishedDateBeforeSecondClick.equalsIgnoreCase(articlePublishedDateAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Last updated')]",5);
                backofficePage.click("//span[contains(.,'Last updated')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Last updated')]",5);
                String articleLastUpdatedBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Last updated before sorting click : " + articleLastUpdatedBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Last updated')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Last updated')]",5);
                String articleLastUpdatedAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                backofficePage.waitThreeSeconds();
                logger.info("Last updated after sorting click : " +articleLastUpdatedAfterClick);
                assertFalse(articleLastUpdatedBeforeSecondClick.equalsIgnoreCase(articleLastUpdatedAfterClick));
                break;
            case "sections":
                backofficePage.clickElementBelow("//h1[contains(.,'Sections')]", "//span[contains(.,'Title')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Title')])[1]",5);
                String sectionsTitleBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Sections title before sorting click : " + sectionsTitleBeforeSecondClick);
                backofficePage.clickElementBelow("//h1[contains(.,'Sections')]", "//span[contains(.,'Title')]");
                backofficePage.waitThreeSeconds();
                String sectionTitleAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Sections title after sorting click : " + sectionTitleAfterClick);
                assertFalse(sectionsTitleBeforeSecondClick.equalsIgnoreCase(sectionTitleAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Created date')])[1]",5);
                backofficePage.click("(//span[contains(.,'Created date')])[1]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Created date')])[1]",5);
                String sectionsCreatedDateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Created date before sorting click : " + sectionsCreatedDateBeforeSecondClick);
                backofficePage.click("(//span[contains(.,'Created date')])[1]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Created date')])[1]",5);
                backofficePage.waitThreeSeconds();
                String sectionsCreatedDateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Created date after sorting click : " + sectionsCreatedDateAfterClick);
                assertFalse(sectionsCreatedDateBeforeSecondClick.equalsIgnoreCase(sectionsCreatedDateAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Last updated')])[1]",5);
                backofficePage.click("(//span[contains(.,'Last updated')])[1]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Last updated')])[1]",5);
                String sectionsLastUpdatedBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Last updated before sorting click : " + sectionsLastUpdatedBeforeSecondClick);
                backofficePage.click("(//span[contains(.,'Last updated')])[1]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Last updated')])[1]",5);
                backofficePage.waitThreeSeconds();
                String sectionsLastUpdatedAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Last updated after sorting click : " + sectionsLastUpdatedAfterClick);
                assertFalse(sectionsLastUpdatedBeforeSecondClick.equalsIgnoreCase(sectionsLastUpdatedAfterClick));
                break;
            case "pages":
                String numberOfRecordsInSectionsTable = backofficePage.getElementAttribute("//div[@class='mat-paginator-range-label'][contains(.,'')]", "textContent");
                logger.info("Number of rows in sections table pagination : " + numberOfRecordsInSectionsTable);
                String sectionRows = numberOfRecordsInSectionsTable.substring(numberOfRecordsInSectionsTable.indexOf("f") + 2 ,numberOfRecordsInSectionsTable.length()-1);
                logger.info("Number of rows in sections table : " + sectionRows);
                int numberOfSectionRows =Integer.parseInt(sectionRows);
                logger.info("Number of rows in sections table : " + numberOfSectionRows);

                int pagesColumn1Row1 = (numberOfSectionRows*4) + 1;
                logger.info("Get the column number for for the first column in the first row of the pages section: " + pagesColumn1Row1);

                backofficePage.click("(//span[contains(.,'Title')])[2]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Title')])[2]",5);
                String pagesTitleBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])["+pagesColumn1Row1+"]", "textContent");
                logger.info("Pages title before sorting click : " + pagesTitleBeforeSecondClick);
                backofficePage.click("(//span[contains(.,'Title')])[2]");
                backofficePage.waitThreeSeconds();
                String pagesTitleAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])["+pagesColumn1Row1+"]", "textContent");
                logger.info("Pages title after sorting click : " + pagesTitleAfterClick);
                assertFalse(pagesTitleBeforeSecondClick.equalsIgnoreCase(pagesTitleAfterClick));

                int pagesColumn2Row1 = (numberOfSectionRows*4) + 2;
                logger.info("Get the column number fo the second column in the first row of the pages section: " + pagesColumn2Row1);

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Created date')])[2]",5);
                backofficePage.click("(//span[contains(.,'Created date')])[2]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Created date')])[2]",5);
                String pagesCreatedDateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])["+pagesColumn2Row1+"]","textContent");
                logger.info("Pages created date before sorting click : " + pagesCreatedDateBeforeSecondClick);
                backofficePage.click("(//span[contains(.,'Created date')])[2]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Created date')])[2]",5);
                backofficePage.waitThreeSeconds();
                String pagesCreatedDateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])["+pagesColumn2Row1+"]","textContent");
                logger.info("Pages created date after sorting click : " + pagesCreatedDateAfterClick);
                assertFalse(pagesCreatedDateBeforeSecondClick.equalsIgnoreCase(pagesCreatedDateAfterClick));

                int pagesColumn3Row1 = (numberOfSectionRows*4) + 3;
                logger.info("Get the column number fo the third column in the first row of the pages section: " + pagesColumn3Row1);

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Last updated')])[2]",5);
                backofficePage.click("(//span[contains(.,'Last updated')])[2]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Last updated')])[2]",5);
                String pagesLastUpdatedBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])["+pagesColumn3Row1+"]","textContent");
                logger.info("Pages last updated before sorting click : " + pagesLastUpdatedBeforeSecondClick);
                backofficePage.click("(//span[contains(.,'Last updated')])[2]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("(//span[contains(.,'Last updated')])[2]",5);
                backofficePage.waitThreeSeconds();
                String pagesLastUpdatedAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])["+pagesColumn3Row1+"]","textContent");
                logger.info("Pages last updated after sorting click : " + pagesLastUpdatedAfterClick);
                assertFalse(pagesLastUpdatedBeforeSecondClick.equalsIgnoreCase(pagesLastUpdatedAfterClick));
                break;
            case "promotions":
                backofficePage.click("//span[contains(.,'Title')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Title')]",5);
                String promotionsTitleBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Promotions title before sorting click : " + promotionsTitleBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Title')]");
                String promotionsTitleAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Promotions title after sorting click : " + promotionsTitleAfterClick);
                assertFalse(promotionsTitleBeforeSecondClick.equalsIgnoreCase(promotionsTitleAfterClick));
                break;
            case "users":
                backofficePage.click(BackofficePage.NameColumnHeader);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.NameColumnHeader,5);
                String userNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("First user in name column before sorting click : " + userNameBeforeSecondClick);
                backofficePage.click(BackofficePage.NameColumnHeader);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.NameColumnHeader,5);
                String userNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("First user in name column after sorting click : " + userNameAfterClick);
                assertFalse(userNameBeforeSecondClick.equalsIgnoreCase(userNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.EmailColumnHeader,5);
                backofficePage.click(BackofficePage.EmailColumnHeader);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.EmailColumnHeader,5);
                String emailBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                logger.info("First email in email column before sorting click : " + emailBeforeSecondClick);
                backofficePage.click(BackofficePage.EmailColumnHeader);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.EmailColumnHeader,5);
                String emailAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                logger.info("First email in email column after sorting click : " + emailAfterClick);
                assertFalse(emailBeforeSecondClick.equalsIgnoreCase(emailAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.CreatedColumnHeader,5);
                backofficePage.click(BackofficePage.CreatedColumnHeader);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.CreatedColumnHeader,5);
                String createdDateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("First date for created column before sorting click : " + createdDateBeforeSecondClick);
                backofficePage.click(BackofficePage.CreatedColumnHeader);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.CreatedColumnHeader,5);
                String createdDateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("First date for created column after sorting click : " + createdDateAfterClick);
                assertFalse(createdDateBeforeSecondClick.equalsIgnoreCase(createdDateAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.LastLoggedInDate,5);
                backofficePage.click(BackofficePage.LastLoggedInDate);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.LastLoggedInDate,5);
                String lastLoggedInDateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("First date for last logged in column before sorting click : " + lastLoggedInDateBeforeSecondClick);
                backofficePage.click(BackofficePage.LastLoggedInDate);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.LastLoggedInDate,5);
                String lastLoggedInDateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("First date for last logged in column after sorting click : " + lastLoggedInDateAfterClick);
                assertFalse( lastLoggedInDateBeforeSecondClick.equalsIgnoreCase(lastLoggedInDateAfterClick));
                break;
            case "ordersBO":
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'ID')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'ID')]",5);
                String IDBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Order ID before sorting click : " + IDBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'ID')]");
                String IDAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Order ID after sorting click : " + IDAfterClick);
                assertFalse(IDBeforeSecondClick.equalsIgnoreCase(IDAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'Name')]",5);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Name')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'Name')]",5);
                String nameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Name before sorting click : " + nameBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Name')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'Name')]",5);
                String nameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Name after sorting click : " + nameAfterClick);
                assertFalse(nameBeforeSecondClick.equalsIgnoreCase(nameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Amount')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String ordersAmountBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Order amount before sorting click : " + ordersAmountBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Amount')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String ordersAmountAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Order amount after sorting click : " + ordersAmountAfterClick);
                assertFalse(ordersAmountBeforeSecondClick.equalsIgnoreCase(ordersAmountAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Date')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String ordersDateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]","textContent");
                logger.info("Order date before sorting click : " + ordersDateBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Date')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String orderDateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]","textContent");
                logger.info("Order date after sorting click : " + orderDateAfterClick);
                assertFalse(ordersDateBeforeSecondClick.equalsIgnoreCase(orderDateAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Payment status')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String paymentStatusBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[5]","textContent");
                logger.info("Payment status before sorting click : " + paymentStatusBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Payment status')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String paymentStatusAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[5]","textContent");
                logger.info("Payment status after sorting click : " + paymentStatusAfterClick);
                assertFalse(paymentStatusBeforeSecondClick.equalsIgnoreCase(paymentStatusAfterClick));
                break;
            case "auto renewals":
                backofficePage.click("//span[contains(.,'Member name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String autoRenewalMemberNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Member name before sorting click : " + autoRenewalMemberNameBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Member name')]");
                String autoRenewalMemberNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Member name after sorting click : " + autoRenewalMemberNameAfterClick);
                assertFalse(autoRenewalMemberNameBeforeSecondClick.equalsIgnoreCase(autoRenewalMemberNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[contains(.,'Ticket cost')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String ticketCostBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Ticket cost before sorting click : " + ticketCostBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Ticket cost')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String ticketCostAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Ticket cost after sorting click : " + ticketCostAfterClick);
                assertFalse(ticketCostBeforeSecondClick.equalsIgnoreCase(ticketCostAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[contains(.,'Renewal count')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String renewalCountBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("Renewal count before sorting click : " + renewalCountBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Renewal count')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String renewalCountAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("Renewal count after sorting click : " + renewalCountAfterClick);
                assertFalse(renewalCountBeforeSecondClick.equalsIgnoreCase(renewalCountAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[contains(.,'Total revenue')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String totalRevenueBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("Total revenue before sorting click : " + totalRevenueBeforeSecondClick );
                backofficePage.click("//span[contains(.,'Total revenue')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String totalRevenueAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("Total revenue after sorting click : " + totalRevenueAfterClick);
                assertFalse(totalRevenueBeforeSecondClick.equalsIgnoreCase(totalRevenueAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[contains(.,'Date of first renewal')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String dateOfFirstRenewalBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[5]", "textContent");
                logger.info("Date of first renewal before sorting click : " + dateOfFirstRenewalBeforeSecondClick );
                backofficePage.click("//span[contains(.,'Date of first renewal')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String dateOfFirstRenewalAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[5]", "textContent");
                logger.info("Date of first renewal after sorting click : " + dateOfFirstRenewalAfterClick);
                assertFalse(dateOfFirstRenewalBeforeSecondClick.equalsIgnoreCase(dateOfFirstRenewalAfterClick));

                backofficePage.refreshPage();
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'Status')]",5);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Status')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'Status')]",5);
                String statusBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[6]", "textContent");
                logger.info("Status before sorting click : " + statusBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Status')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[@data-test='table.header-item'][contains(.,'Status')]",5);
                String statusAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[6]", "textContent");
                logger.info("Status after sorting click : " + statusAfterClick);
                assertFalse(statusBeforeSecondClick.equalsIgnoreCase(statusAfterClick));
                break;
            case "revenues":
                backofficePage.click("//span[contains(.,'Order ID')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String orderIDBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Order ID before sorting click : " + orderIDBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Order ID')]");
                String orderIDAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Order ID after sorting click : " + orderIDAfterClick);
                assertFalse(orderIDBeforeSecondClick.equalsIgnoreCase(orderIDAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[contains(.,'Customer')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String customerNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Customer name before sorting click : " + customerNameBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Customer')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String customerNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Customer name after sorting click : " + customerNameAfterClick);
                assertFalse(customerNameBeforeSecondClick.equalsIgnoreCase(customerNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[contains(.,'Amount')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String amountBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("Amount before sorting click : " + amountBeforeSecondClick);
                backofficePage.click("//span[contains(.,'Amount')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String amountAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("Amount after sorting click : " + amountAfterClick);
                assertFalse(amountBeforeSecondClick.equalsIgnoreCase(amountAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("(//span[contains(.,'Taken date')])[2]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String dateBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("Date before sorting click : " + dateBeforeSecondClick );
                backofficePage.click("(//span[contains(.,'Taken date')])[2]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String dateAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("Date after sorting click : " + dateAfterClick);
                assertFalse(dateBeforeSecondClick .equalsIgnoreCase(dateAfterClick));
                break;
            case "memberships reports":
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Member name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String memberNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Member name before sorting click : " + memberNameBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Member name')]");
                String memberNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Member name after sorting click : " + memberNameAfterClick);
                assertFalse(memberNameBeforeSecondClick.equalsIgnoreCase(memberNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Gender')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String genderBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Gender before sorting click : " + genderBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Gender')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String genderAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Gender after sorting click : " + genderAfterClick);
                assertFalse(genderBeforeSecondClick.equalsIgnoreCase(genderAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'DOB')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String dateOfBirthBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("DOB before sorting click : " + dateOfBirthBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'DOB')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String dateOfBirthAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("DOB after sorting click : " + dateOfBirthAfterClick);
                assertFalse(dateOfBirthBeforeSecondClick.equalsIgnoreCase(dateOfBirthAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Plan name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String planNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("Plan name before sorting click : " + planNameBeforeSecondClick );
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Plan name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String planNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]", "textContent");
                logger.info("Plan name after sorting click : " + planNameAfterClick);
                assertFalse(planNameBeforeSecondClick .equalsIgnoreCase(planNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Membership status')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String membershipStatusBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[6]", "textContent");
                logger.info("Membership status before sorting click : " + membershipStatusBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Membership status')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String membershipStatusAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[6]", "textContent");
                logger.info("Membership status after sorting click : " + membershipStatusAfterClick);
                assertFalse(membershipStatusBeforeSecondClick .equalsIgnoreCase(membershipStatusAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Guardian name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String guardianNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[7]", "textContent");
                logger.info("Guardian name before sorting click : " + guardianNameBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Guardian name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String guardianNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[7]", "textContent");
                logger.info("Guardian name after sorting click : " + guardianNameAfterClick);
                assertFalse(guardianNameBeforeSecondClick .equalsIgnoreCase(guardianNameAfterClick));
                break;
            case "members":
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'First name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String firstNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("First name before sorting click : " + firstNameBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'First name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String firstNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("First name after sorting click : " + firstNameAfterClick);
                assertFalse(firstNameBeforeSecondClick.equalsIgnoreCase(firstNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Last name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String lastNameBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                logger.info("Last name before sorting click : " + lastNameBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'Last name')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String lastNameAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                logger.info("Last name after sorting click : " + lastNameAfterClick);
                assertFalse(lastNameBeforeSecondClick.equalsIgnoreCase(lastNameAfterClick));

                backofficePage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'DOB')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String memberDOBeforeSecondClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("DOB before sorting click : " + memberDOBeforeSecondClick);
                backofficePage.click("//span[@data-test='table.header-item'][contains(.,'DOB')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String memberDOBAfterClick = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]", "textContent");
                logger.info("DOB after sorting click : " + memberDOBAfterClick);
                assertFalse(memberDOBeforeSecondClick.equalsIgnoreCase(memberDOBAfterClick));
                break;
            case "ordersMA":
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'ID')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String IDBeforeSecondClickMA = myAccountPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Order ID before sorting click : " + IDBeforeSecondClickMA);
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'ID')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String IDAfterClickMA = myAccountPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
                logger.info("Order ID after sorting click : " + IDAfterClickMA);
                assertFalse(IDBeforeSecondClickMA.equalsIgnoreCase(IDAfterClickMA));

                myAccountPage.refreshPage();
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'Amount')]");
                backofficePage.findOnPage(BackofficePage.ItemsPerPageText);
                String ordersAmountBeforeSecondClickMA = myAccountPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Order amount before sorting click : " + ordersAmountBeforeSecondClickMA);
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'Amount')]");
                myAccountPage.waitThreeSeconds();
                String ordersAmountAfterClickMA = myAccountPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]","textContent");
                logger.info("Order amount after sorting click : " + ordersAmountAfterClickMA);
                assertFalse(ordersAmountBeforeSecondClickMA.equalsIgnoreCase(ordersAmountAfterClickMA));

                myAccountPage.refreshPage();
                myAccountPage.waitThreeSeconds();
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'Date')]");
                myAccountPage.waitThreeSeconds();
                String ordersDateBeforeSecondClickMA = myAccountPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Order date before sorting click : " + ordersDateBeforeSecondClickMA);
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'Date')]");
                myAccountPage.waitThreeSeconds();
                String orderDateAfterClickMA = myAccountPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[3]","textContent");
                logger.info("Order date after sorting click : " + orderDateAfterClickMA);
                assertFalse(ordersDateBeforeSecondClickMA.equalsIgnoreCase(orderDateAfterClickMA));

                myAccountPage.refreshPage();
                myAccountPage.waitThreeSeconds();
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'Payment status')]");
                myAccountPage.waitThreeSeconds();
                String paymentStatusBeforeSecondClickMA = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]","textContent");
                logger.info("Payment status before sorting click : " + paymentStatusBeforeSecondClickMA);
                myAccountPage.click("//span[@data-test='table.header-item'][contains(.,'Payment status')]");
                myAccountPage.waitThreeSeconds();
                String paymentStatusAfterClickMA = backofficePage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[4]","textContent");
                logger.info("Payment status after sorting click : " + paymentStatusAfterClickMA);
                assertFalse(paymentStatusBeforeSecondClickMA.equalsIgnoreCase(paymentStatusAfterClickMA));
                break;
            default:
                throw new NotFoundException("For some reason there is no case sortingIcons");
        }
    }

    @Then("they can use filter to view {string} results")
    public void checkFilteringForSU(String status) {
        backofficePage.waitFiveSeconds();
        switch (status) {
            case "Pending SU":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Pending')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Pending')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])["+i+"]"));
                }
                break;
            case "Complete SU":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Complete')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Pending')])["+i+"]"));
                }
                break;
            case "Complete BO":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Complete')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//p[contains(.,'Complete')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Pending')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Cancelled')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'New')])["+i+"]"));
                }
                break;
            case "Pending BO":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Pending')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//p[contains(.,'Pending')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Complete')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Cancelled')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'New')])["+i+"]"));
                }
                break;
            case "Cancelled BO":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Cancelled')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//p[contains(.,'Cancelled')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Complete')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Pending')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'New')])["+i+"]"));
                }
                break;
            case "New BO":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'New')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Cancelled')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Complete')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//p[contains(.,'Pending')])["+i+"]"));
                }
                break;
            case "Complete MA":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Complete')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Pending')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Cancelled')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'New')])["+i+"]"));
                }
                break;
            case "Pending MA":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Pending')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=2; i++){
                    backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Pending')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Cancelled')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'New')])["+i+"]"));
                }
                break;
            case "Cancelled MA":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Cancelled')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=3; i++){
                    backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Cancelled')])["+i+"]");
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Pending')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'New')])["+i+"]"));
                }
                break;
            case "New MA":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'New')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Cancelled')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Pending')])["+i+"]"));
                    assertFalse(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])["+i+"]"));
                }
                break;
            case "Refunded MA":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Refunded')]");
                backofficePage.waitThreeSeconds();
                for(int i = 4; i <=8; i+=2){
                    backofficePage.findOnPage("(//span[contains(.,'Refunded')])["+i+"]");
                }
                break;
            case "Auto renewal MA":
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Auto renewal')]");
                backofficePage.waitThreeSeconds();
                for(int i = 1; i <=10; i++){
                    backofficePage.findOnPage("(//span[@class='badge bg-secondary me-2'][contains(.,'Auto-renew')])["+i+"]");
                    logger.info("Auto renewal badge : " + i + " is displayed");
                }
                break;
            case "Inactive":
                backofficePage.click("//select[contains(@id,'mat-input-1')]");
                backofficePage.click("//*[contains(text(),'Inactive')]");
                backofficePage.waitThreeSeconds();
                backofficePage.findOnPage("(//div[@class='col-md p-3 col-6'][contains(.,'Inactive')])[1]");
                assertFalse(backofficePage.isElementDisplayed("//*[contains(text(),'Active')]"));
                assertFalse(backofficePage.isElementDisplayed("//h2[contains(.,'No orders found')]"));
                break;
            case "Active":
                backofficePage.click("//select[contains(@id,'mat-input-1')]");
                backofficePage.click("//*[contains(text(),'Active')]");
                backofficePage.waitThreeSeconds();
                backofficePage.findOnPage("(//div[@class='col-md p-3 col-6'][contains(.,'Active')])[1]");
                assertFalse(backofficePage.isElementDisplayed("//*[contains(text(),'Inactive')]"));
                assertFalse(backofficePage.isElementDisplayed("//h2[contains(.,'No orders found')]"));
                break;
            default:
        }
    }
}
