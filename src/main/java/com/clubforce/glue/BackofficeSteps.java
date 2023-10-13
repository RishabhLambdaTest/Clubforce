package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

public class BackofficeSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public BackofficeSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficePage = driverManager.backofficePage;
        this.lottoPage = driverManager.lottoPage;
        this.connectPage = driverManager.connectPage;
    }

    @When("ClubAdmin go to Backoffice {string} page")
    public void userGoesToBackofficePage(String page) {
        logger.info("Check if Left nav bar is open");
        backofficePage.openBackOfficeLeftSideNav();
        backofficePage.findOnPage("//span[@class='ms-2'][contains(.,'Settings')]");
        logger.info("Going to "+page+" page");
        switch (page) {
            case "Website Articles":
                if (!(backofficePage.isElementDisplayed(BackofficePage.LeftNavNewsArticles))) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(BackofficePage.LeftNavNewsArticles);
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Articles"));
                break;
            case "Website Sections and Pages":
                if (!backofficePage.isElementDisplayed(BackofficePage.LeftNavSectionsAndPages)) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(BackofficePage.LeftNavSectionsAndPages);
                backofficePage.waitHalfSecond();
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Pages"));
                break;
            case "Website About Us":
                if (!(backofficePage.isElementDisplayed(BackofficePage.LeftNavAboutUs))) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(BackofficePage.LeftNavAboutUs);
                backofficePage.waitHalfSecond();
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Short title for your article"));
                break;
            case "Website Banner":
                if (!(backofficePage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsBannerSection))) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(WebsiteSettingsPage.WebsiteSettingsBannerSection);
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Text placed on image on homepage only."));
                break;
            case "Website Navigation":
                if (!backofficePage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsMenuSection)) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(WebsiteSettingsPage.WebsiteSettingsMenuSection);
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Add external links to your menu, e.g. your existing lotto page. Drag to change order."));
                break;
            case "Website Sponsor":
                if (!(backofficePage.isElementDisplayed(BackofficePage.BackofficeLeftNavSponsors))) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(BackofficePage.BackofficeLeftNavSponsors);
                backofficePage.findOnPage("//h1[contains(.,'Sponsors')]");
                break;
            case "Contact Messages":
                backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                backofficePage.click(BackofficePage.BackofficeLeftNavContactMessages);
                backofficePage.findOnPage(BackofficePage.MessagesHeading);
                break;
            case "Account":
                backofficePage.click(BackofficePage.BackofficeLeftNavSettings);
                backofficePage.click(BackofficePage.BackofficeLeftNavAccount);
                backofficePage.findOnPage(BackofficePage.AccountUpdateHeading);
                break;
            case "Users":
                backofficePage.click(BackofficePage.BackofficeLeftNavSettings);
                backofficePage.click(BackofficePage.LeftNavUsersText);
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Created"));
                break;
            case "Revenue report":
                backofficePage.click(BackofficePage.LeftNavFinance);
                backofficePage.click(LottoPage.RevenueReportOption);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Revenue report"));
                break;
            case "Finance Orders":
                backofficePage.click(BackofficePage.LeftNavFinance);
                backofficePage.click(LottoPage.ReportsOrdersSubSection);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Orders"));
                break;
            case "Auto Renewals":
                if (!backofficePage.isElementDisplayed(BackofficePage.LeftNavAutoRenewals)) {
                    backofficePage.click(BackofficePage.LottoLeftNav);
                }
                backofficePage.click(BackofficePage.LeftNavAutoRenewals);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Auto renewal playslips"));
                break;
            case "Payouts":
                if (!backofficePage.isElementDisplayed(BackofficePage.LeftNavPayouts)) {
                    backofficePage.click(BackofficePage.LeftNavFinance);
                }
                backofficePage.click(BackofficePage.LeftNavPayouts);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Payout reports"));
                break;
            case "Lotto Draws":
                if (!backofficePage.isElementDisplayed(LottoPage.LottoDrawsSubSection)) {
                    backofficePage.click(LottoPage.LottoSection);
                }
                backofficePage.click(LottoPage.LottoDrawsSubSection);
                backofficePage.findOnPage(LottoPage.CurrentDrawJackpotLabel);
                backofficePage.findOnPage(LottoPage.LottoDrawsEditIcon);
                break;
            case "Lotto Information":
                if (!backofficePage.isElementDisplayed(BackofficePage.LottoDetailsLeftNav)) {
                    backofficePage.click(LottoPage.LottoSection);
                }
                backofficePage.click(BackofficePage.LottoDetailsLeftNav);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Lotto setup"));
                break;
            case "Lotto Settings":
                if (!backofficePage.isElementDisplayed(BackofficePage.LottoDetailsLeftNav)) {
                    backofficePage.click(LottoPage.LottoSection);
                }
                backofficePage.click(BackofficePage.LottoDetailsLeftNav);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Lotto setup"));
                backofficePage.click("//strong[@class='ng-star-inserted'][contains(.,'Lotto Settings')]");
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Schedule draw"));
                break;
            case "Lotto Ticket Options":
                if (!backofficePage.isElementDisplayed(BackofficePage.LottoDetailsLeftNav)) {
                    backofficePage.click(LottoPage.LottoSection);
                }
                backofficePage.click(BackofficePage.LottoDetailsLeftNav);
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Lotto setup"));
                backofficePage.click("//strong[@class='ng-star-inserted'][contains(.,'Lotto Settings')]");
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Schedule draw"));
                backofficePage.click("//strong[@class='ng-star-inserted'][contains(.,'Lotto Ticket Options')]");
                backofficePage.findOnPage(String.format(LottoPage.LottoPageText, "Ticket types"));
                break;
            case "Lotto Playslip":
                if (!backofficePage.isElementDisplayed(LottoPage.LottoDrawsSubSection)){
                    backofficePage.click(LottoPage.LottoSection);
                }
                backofficePage.click(LottoPage.LottoDrawsSubSection);
                backofficePage.findOnPage(LottoPage.LottoDrawsHeading);
                backofficePage.waitTwoSeconds();
                backofficePage.click(LottoPage.CurrentDrawPlayslipButton);
                break;
            case "Lotto Details":
                backofficePage.waitTwoSeconds();
                backofficePage.click(BackofficePage.LottoLeftNav);
                backofficePage.waitTwoSeconds();
                backofficePage.click(BackofficePage.SetUpLottoLeftNav);
                backofficePage.findOnPage(LottoPage.LottoInformationHeader);
                break;
            case "Products":
                backofficePage.click(BackofficePage.LeftNavProducts);
                backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Product type"));
                break;
            case "Promotions":
                backofficePage.click(PromotionsPage.PromotionsLeftNav);
                break;
            case "Website Theme":
                backofficePage.waitTwoSeconds();
                if (!(backofficePage.isElementDisplayed(BackofficePage.LeftNavTheme))) {
                    backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                }
                backofficePage.click(BackofficePage.LeftNavTheme);
                backofficePage.findOnPage(BackofficePage.ThemeHeading);
                break;
            case "Comortais":
                backofficePage.waitTwoSeconds();
                backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
                backofficePage.click(BackofficePage.LeftNavComortais);
                backofficePage.findOnPage(BackofficePage.ComortaisHeading);
                break;
            case "Membership plans":
                if(!backofficePage.isElementDisplayed(BackofficePage.BackofficeLeftNavMembershipSelection)){
                    backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                }
                backofficePage.waitTwoSeconds();
                if (backofficePage.isElementDisplayed("//li[@data-test='button.Membership setup']")) {
                    throw new NotFoundException("Membership not set up yet, aborting test");
                }
                backofficePage.click(BackofficePage.BackofficeLeftNavMembershipSelection);
                backofficePage.waitForSkeletonLoader();
                break;
            case "Membership reports":
                backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.BackofficeLeftNavMembershipReportSelection, 5);
                backofficePage.click(BackofficePage.BackofficeLeftNavMembershipReportSelection);
                break;
            case "Multi-plan discounts":
                backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                if(!backofficePage.isElementDisplayed(BackofficePage.BackofficeLeftNavMembershipDiscountsSelection)){
                    backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                }
                backofficePage.findOnPage(BackofficePage.BackofficeLeftNavMembershipDiscountsSelection);
                backofficePage.click(BackofficePage.BackofficeLeftNavMembershipDiscountsSelection);
                break;
            case "Discount codes":
                backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                if(!backofficePage.isElementDisplayed(BackofficePage.BackofficeLeftNavDiscountCodesSelection)){
                    backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                }
                backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.BackofficeLeftNavDiscountCodesSelection, 5);
                backofficePage.click(BackofficePage.BackofficeLeftNavDiscountCodesSelection);
                break;
            case "Set Up Membership":
                backofficePage.click(BackofficePage.BackofficeLeftNavMembership);
                backofficePage.waitOneSecond();
                backofficePage.click(BackofficePage.BackofficeLeftNavSetUpMembershipSelection);
                break;
            case "Email":
                backofficePage.click(BackofficePage.BackofficeLeftNavEmail);
                backofficePage.findOnPage(BackofficePage.EmailHeading);
                break;
            case "Connect groups":
                backofficePage.waitFiveSeconds();
                backofficePage.click(BackofficePage.BackofficeConnectGroupsLeftnav);
                backofficePage.findOnPage(ConnectPage.ConnectGroupHeading);
                break;
            case "Dashboard":
                backofficePage.click(BackofficePage.DashboardTitleLeftNav);
                break;
            case "Search":
                backofficePage.click(BackofficePage.BackofficeSearchPage);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for userGoesToBackofficePage!");
        }
    }

    @And("{string} is displayed on orders page")
    public void checkCurrencySymbolOnOrdersPage(String currencySymbol) {
        switch (currencySymbol) {
            case "Euro":
                backofficePage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'€')]");
                break;
            case "Pound":
                backofficePage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'£')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkCurrencySymbolOnOrdersPage!");
        }
    }

    @And("{string} is displayed on auto renewals page")
    public void checkCurrencySymbolOnAutoRenewalsPage(String currencySymbol) {
        switch (currencySymbol) {
            case "Euro":
                backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'€')])[1]");
                backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'€')])[2]");
                break;
            case "Pound":
                backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'£')])[1]");
                backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'£')])[2]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkCurrencySymbolOnRecurringOrdersPage!");
        }
    }

}
