package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CommonSteps extends WebDriverManager {

    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public CommonSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
    }
    public static String scenarioName = " ";

    @Before()
    public void beforeScenario(Scenario scenario) throws MalformedURLException {
        //set properties
        driverManager.setProperties();
        // create browser instance
        driverManager.launchDevice(getScenarioName(scenario));
        scenarioName = getScenarioName(scenario);
        logger.info("scenarioName: "+scenarioName);
        // initialise page objects
        driverManager.accountPage = new AccountPage(driverManager);
        driverManager.articlePage = new ArticlePage(driverManager);
        driverManager.backofficePage = new BackofficePage(driverManager);
        driverManager.clubPage = new ClubPage(driverManager);
        driverManager.comortaisPage = new ComortaisPage(driverManager);
        driverManager.connectPage = new ConnectPage(driverManager);
        driverManager.contactPage = new ContactPage(driverManager);
        driverManager.emailPage = new EmailPage(driverManager);
        driverManager.loginPage = new LoginPage(driverManager);
        driverManager.lottoPage = new LottoPage(driverManager);
        driverManager.mobileBrowserPage = new MobileBrowserPage(driverManager);
        driverManager.myAccountPage = new MyAccountPage(driverManager);
        driverManager.paymentProviderPage = new PaymentProviderPage(driverManager);
        driverManager.productsPage = new ProductsPage(driverManager);
        driverManager.promotionsPage = new PromotionsPage(driverManager);
        driverManager.usersPage = new UsersPage(driverManager);
        driverManager.websiteSettingsPage = new WebsiteSettingsPage(driverManager);
        driverManager.xgbTemplatePage = new xgbTemplatePage(driverManager);
        driverManager.xgbDashboardPage = new xgbDashboardPage(driverManager);
        driverManager.revenueReportPage = new RevenueReportPage(driverManager);
        driverManager.membershipReportPage = new MembershipReportPage(driverManager);
        driverManager.sectionsAndPagesPage = new SectionsAndPagesPage(driverManager);
        driverManager.backofficeDashboardPage = new BackofficeDashboardPage(driverManager);
        driverManager.backofficeAboutUsPage = new BackofficeAboutUsPage(driverManager);
        driverManager.themePage = new ThemePage(driverManager);
        driverManager.bannerPage = new BannerPage(driverManager);
        driverManager.sponsorPage = new SponsorPage(driverManager);
        driverManager.websiteNavigationPage = new WebsiteNavigationPage(driverManager);
        driverManager.superUserPage = new SuperUserPage(driverManager);
        driverManager.payoutPage = new PayoutPage(driverManager);
        driverManager.membershipDiscountPage = new MembershipDiscountPage(driverManager);
        driverManager.membershipRegistrationPage = new MembershipRegistrationPage(driverManager);
        driverManager.membershipPage = new MembershipPage(driverManager);
        driverManager.discountCodePage = new DiscountCodePage(driverManager);

        // local class pages
        this.accountPage = driverManager.accountPage;
        this.clubPage = driverManager.clubPage;
        this.connectPage = driverManager.connectPage;
        this.contactPage = driverManager.contactPage;
        this.backofficePage = driverManager.backofficePage;
        this.loginPage = driverManager.loginPage;
        this.lottoPage = driverManager.lottoPage;
        this.mobileBrowserPage = driverManager.mobileBrowserPage;
        this.myAccountPage = driverManager.myAccountPage;
        this.paymentProviderPage = driverManager.paymentProviderPage;
        this.usersPage = driverManager.usersPage;
        this.websiteSettingsPage = driverManager.websiteSettingsPage;
        this.xgbTemplatePage = driverManager.xgbTemplatePage;
        this.xgbDashboardPage = driverManager.xgbDashboardPage;
    }

    //Set 'order' as this needs to happen before launching the browser
    @Before(value = "@IgnoreInvalidCerts", order = 0)
    public void beforeIgnoreInvalidCerts() {
        driverManager.ignoreInvalidCerts = true;
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        driverManager.tearDownTest(scenario);
    }

    @Given("user goes to Backoffice URL {string}")
    public void userGoesToWebURL(String fullURL) {
        logger.info("Going to URL: " + fullURL);
        loginPage.goToBackofficeURL(fullURL);
    }

    @And("user go to club homepage")
    public void userGoesToClubHomepage() {
        logger.info("Going to club homepage" );
        loginPage.goToClubURL("/");
        loginPage.waitFiveSeconds();
        backofficePage.acceptCookies();
        clubPage.verifyClubPageHomepageElements();
    }

    @And("user go to lotto club homepage")
    public void userGoesToLottoClubHomepage() {
        logger.info("Going to lotto club homepage" );
        loginPage.goToLottoClubURL();
        loginPage.waitTwoSeconds();
        backofficePage.acceptCookies();
    }

    @And("user go to membership club purchase page")
    public void userGoesToMembershipClubHomepage() {
        logger.info("Going to membership club purchase page" );
        loginPage.goToMembershipClubURL("/products/membership");
        loginPage.waitTwoSeconds();
        backofficePage.acceptCookies();
    }

    @Given("user go to club homepage URL {string}")
    public void userGoesToClubHomepageURL(String url) {
        logger.info("Going to club homepage" );
        loginPage.goToClubURL(url);
        loginPage.waitOneSecond();
    }

    @And("user refreshes page")
    public void userRefreshPage() {
        loginPage.refreshPage();
    }

    @And("user clears cookies")
    public void userClearsCookies() {
        logger.info("Clearing cookies" );
        loginPage.deleteAllCookies();
        loginPage.deleteAllCookies();
    }

    @And("member go to the current club homepage")
    public void userGoesToCurrentClubHomepageURL() {
        logger.info("Going to the current club homepage");

        String FullURL = driverManager.driver.getCurrentUrl();

        loginPage.goTo_URL(FullURL);
        logger.info("Going to the current club homepage");
    }

    @And("member go to the current club homepage membership purchase page")
    public void userGoesToCurrentClubMembershipPurchaseURL() {
        backofficePage.click("//img[contains(@itemprop,'logo')]");
        backofficePage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Membership')]",5);
        assertTrue(backofficePage.isElementPresent("(//span[contains(.,'Membership')])[1]"));
        assertFalse(backofficePage.isElementPresent("(//span[contains(.,'Membership')])[2]"));
        backofficePage.click("//span[contains(.,'Membership')]");
    }

    @When("user goes to URL {string}")
    public void userClicksBOProfileIcon(String visitURL) {
        logger.info("Going to URL: " + visitURL );
        backofficePage.goTo_URL(visitURL);
    }

    @And("CA goes to club homepage via dashboard")
    public void caGoToClubHomepageViaDashboard() {
        logger.info("Going to Dashboard" );
        backofficePage.click("//span[@class='ms-2'][contains(.,'Dashboard')]");
        backofficePage.click("//button[@data-test='cf-dashboard-tile.button'][contains(.,'View website')]");
    }

    @And("they click homepage account icon")
    public void userClicksHomepageProfileIcon() {
        logger.info("Click homepage account icon" );
        loginPage.waitTwoSeconds();
        if (driverManager.agent.contains("appium")) {
            loginPage.click("//span[contains(@class,'navbar-toggler-icon')]");
        }
        loginPage.click(LoginPage.HomepageProfileIcon);
    }

    @And("they click BO account icon")
    public void userClicksBOProfileIcon() {
        logger.info("Click BO account icon" );
        loginPage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.BO_MenuDots, 5);
        loginPage.click(BackofficePage.BO_MenuDots);
    }

    @Then("they are redirected to {string}")
    public void currentURLContains(String landingURL) {
        String LandingURL = driverManager.driver.getCurrentUrl();
        loginPage.waitFiveSeconds();
        assertTrue(LandingURL.contains(landingURL));
        logger.info("Expected landing URL found");
    }

    @And("they create a {string} product page")
    public void adminCreatesProduct(String productType) {
        logger.info("Creating "+productType+" page");
        if (!backofficePage.isElementDisplayed(BackofficePage.DashboardTitleLeftNav)) {
            backofficePage.click(BackofficePage.BackofficeHamburger);
            backofficePage.findOnPage(BackofficePage.DashboardTitleLeftNav);
        }

        switch (productType) {
            case "Lotto":
                loginPage.click(LottoPage.LottoSection);
                backofficePage.waitOneSecond();
                loginPage.click(BackofficePage.SetUpLottoLeftNav);
                break;
            case "Membership":
                backofficePage.waitOneSecond();
                // not implemented yet
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminCreatesProduct!");
        }
    }

    @Given("basic club page content is displayed as expected")
    public void basicClubPageContentShows() throws IOException {
        File file = new File("src/main/resources/files/prodURLs.txt");
        BufferedReader bufRdr;
        bufRdr = new BufferedReader(new FileReader(file));
        String fileURL;

        while((fileURL = bufRdr.readLine()) != null) {
            String fileHTTPURL = "http://"+fileURL;
            HttpURLConnection httpConnection = (HttpURLConnection)new URL(fileHTTPURL).openConnection();
            httpConnection.setRequestMethod("HEAD");
            httpConnection.connect();
            int httpCode = httpConnection.getResponseCode();
            if (httpCode == 301){
                logger.info("Expected 301 HTTP response code for "+fileHTTPURL+" is "+httpCode);
            } else {
                logger.error("ERROR, expected 301 HTTP response code for "+fileHTTPURL+" is "+httpCode);
            }

            String fileHTTPSURL = "https://"+fileURL;
            HttpURLConnection connection = (HttpURLConnection)new URL(fileHTTPSURL).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int httpsCode = connection.getResponseCode();
            if (httpsCode == 200){
                logger.info("Expected 200 HTTPS response code for "+fileHTTPSURL+" is "+httpsCode);
            } else {
                logger.error("ERROR, expected 200 HTTPS response code for "+fileHTTPSURL+" is "+httpsCode);
            }

            loginPage.deleteAllCookies();
            loginPage.waitTwoSeconds();

            loginPage.goTo_URL(fileHTTPSURL);

            if (!(clubPage.isElementDisplayed(ClubPage.ContactText))) {
                clubPage.takePageScreenshot("screenshot_"+fileHTTPSURL);
                throw new NotFoundException("ClubPage.ContactText missing, took screenshot");
            } else {
                logger.info("Contact link show for "+fileHTTPSURL+", PASS");
            }

//        logger.info("Checking for Membership header");
//        loginPage.goTo_URL(clubURL+"/products/membership");
//        loginPage.waitForElementDisplayedByXpathWithTimeout(MembershipPage.Step1SelectPlansHeading,10);
//        logger.info("Membership header shows, PASS");
        }
    }

    @Given("click hamburger to toggle leftNav")
    public void hamburgerToggleLeftNav() {
        logger.info("Check to see if Leftnav bar is open");
        if (!backofficePage.isElementDisplayed(BackofficePage.BackofficeLeftNavDashboard)) {
            logger.info("Click hamburger, check leftNav fully opens");
            if (backofficePage.isElementDisplayed(BackofficePage.BackofficeHamburger)) {
                backofficePage.click(BackofficePage.BackofficeHamburger);
            }
            if (backofficePage.isElementDisplayed(BackofficePage.BackofficeHamburgerMobile)) {
                backofficePage.click(BackofficePage.BackofficeHamburgerMobile);
            }
            backofficePage.waitTwoSeconds();
        }
        assertTrue(loginPage.isElementDisplayed(BackofficePage.BackofficeLeftNavDashboard),"Leftnav text not present when expected!");
        logger.info("Left Nav is open");
        loginPage.waitOneSecond();
        logger.info("Click closer, check leftNav fully closes");
        loginPage.click(BackofficePage.BackofficeLeftNavSettings);
        loginPage.click(BackofficePage.BackofficeLeftNavAccount);//mobile
        loginPage.waitTwoSeconds();
        if (loginPage.isElementDisplayed(BackofficePage.BackofficeLeftNavSettings)) { //sandbox & prod. Remove this when the same as test environment
            loginPage.click(BackofficePage.BackOfficeHamburgerOpen);
        }
        loginPage.waitTwoSeconds();
        assertFalse(loginPage.isElementDisplayed(BackofficePage.BackofficeLeftNavSettings),"Leftnav text present when not expected!");
    }

    @When("I google {string}")
    public void googleSearch(String searchString) {
        logger.info("Search Google for "+searchString);
        loginPage.goTo_Google();
        acceptGooglePopups();
        loginPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(@name,'q')]", 15);
        loginPage.sendKeys("//*[contains(@name,'q')]", searchString);
        loginPage.click("//div[@class='uU7dJb']");
        loginPage.click("(//input[@type='submit'])[3]");
    }

    @Then("{string} is found on google")
    public void stringIsFoundOnGoogleResultsPage(String searchResult) {
        logger.info("Waiting for Google result for "+searchResult);
        loginPage.waitThreeSeconds();
        loginPage.findOnPage("//*[contains(.,'"+searchResult+"')]");
    }

    @And("they scroll to page element {string}")
    public void scrollToElement(String element) {
        loginPage.waitTwoSeconds();
        logger.info("Scrolling to " +element );
        loginPage.centreElement(element);
        loginPage.waitTwoSeconds();
    }

    public void acceptGooglePopups() {
        logger.info("Checking for and accepting Google popups");
        loginPage.waitFiveSeconds();
        if (loginPage.isElementDisplayed("//button[contains(.,'Reject all')]")) {
            logger.info("Cookie warning Reject all");
            loginPage.click("//button[contains(.,'Reject all')]");
            loginPage.waitUntilElementInvisible("//button[contains(.,'Reject all')]", 5);
        }
        if (loginPage.isElementDisplayed("//button[contains(.,'Alles afwijzen')]")) {
            logger.info("Cookie warning Alles afwijzen");
            loginPage.click("//button[contains(.,'Alles afwijzen')]");
            loginPage.waitUntilElementInvisible("//button[contains(.,'Alles afwijzen')]", 5);
        }
        if (loginPage.isElementDisplayed("//*[contains(text(),'Ik ga akkoord')]")) {
            logger.info("Popup found, 'Ik ga akkoord'");
            loginPage.click("//*[contains(text(),'Ik ga akkoord')]");
            loginPage.waitUntilElementInvisible("//*[contains(text(),'Ik ga akkoord')]", 5);
        }
        if (loginPage.isElementDisplayed("//*[contains(text(),'I agree')]")) {
            logger.info("Popup found, 'I agree'");
            loginPage.click("//*[contains(text(),'I agree')]");
            loginPage.waitUntilElementInvisible("//*[contains(text(),'I agree')]", 5);
        }
    }

    @Then("they can find Apple text string {string}")
    public void lookForAppleTextString(String textString) {
        logger.info("Looking for text string: " + textString );
        assertTrue(backofficePage.isElementPresent("//p[contains(.,'"+textString+"')]"));
        logger.info("Text string: " + textString + " found!" );
    }

    @Then("they can find Google text string {string}")
    public void lookForGoogleTextString(String textString) {
        logger.info("Looking for text string: " + textString );
        backofficePage.clickElementRightOf("//h2[contains(.,'About this app')]","//i[contains(.,'arrow_forward')]");
        backofficePage.findOnPage("//div[@class='reAt0'][contains(.,'"+textString+"')]");
        logger.info("Text string: " + textString + " found!" );
    }

    @Then("the footer link {string} works as expected")
    public void verifyClubPageFooterElements(String footerLink) {
        logger.info("Verifying elements on website footer page");
        backofficePage.scrollPageToBottom();
        backofficePage.findOnPage(String.format(BackofficePage.BackofficePageText, "Powered by Clubforce"));

        switch (footerLink) {
            case "About Clubforce":
                backofficePage.click(ClubPage.AboutClubforceLinkText);
                backofficePage.waitTwoSeconds();
                backofficePage.switchToBrowserTab(1);
                backofficePage.findOnPage(String.format(BackofficePage.BackofficePageText, "Built for volunteers by volunteers"));
                break;
            case "Terms & Conditions":
                backofficePage.click("//div[@class='mat-list-item-content'][contains(.,'Terms & Conditions')]");
                backofficePage.waitTwoSeconds();
                backofficePage.switchToBrowserTab(1);
                backofficePage.findOnPage(String.format(BackofficePage.BackofficePageText, "Clubforce T&Cs"));
                break;
            case "Privacy Policy":
                backofficePage.click("//div[@class='mat-list-item-content'][contains(.,'Privacy Policy')]");
                backofficePage.waitTwoSeconds();
                backofficePage.switchToBrowserTab(1);
                backofficePage.findOnPage(String.format(BackofficePage.BackofficePageText, "Clubforce Privacy Policy"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for verifyClubPageFooterElements!");
        }
    }

    @Then("they can use the pagination")
    public void paginationIsUsed() {
        logger.info("Testing pagination");
        backofficePage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);
        backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.PaginationNumberingText1to10,30);
        assertFalse(backofficePage.isElementPresent(String.format(BackofficePage.PaginationNumbering, "11 – 20")));
        backofficePage.click(BackofficePage.PaginationNextButton);
        backofficePage.findOnPage(BackofficePage.PaginationNumberingText11to20);
        assertTrue(backofficePage.isElementPresent(String.format(BackofficePage.PaginationNumbering, "11 – 20")));
        assertFalse(backofficePage.isElementPresent(String.format(BackofficePage.PaginationNumbering, "1 – 10")));
        backofficePage.scrollPageDown();
        backofficePage.findOnPage(BackofficePage.PaginationPreviousButton);
        backofficePage.click(BackofficePage.PaginationPreviousButton);
        backofficePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.PaginationNumberingText1to10,30);
        assertFalse(backofficePage.isElementPresent(String.format(BackofficePage.PaginationNumbering, "11 – 20")));
    }
}
