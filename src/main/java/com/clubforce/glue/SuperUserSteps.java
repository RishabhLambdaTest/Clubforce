package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SuperUserSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();
    public static String XGBAccountNameHOLDER = " ";
    public static String XGBAccountUserMailHOLDER = " ";
    public static String ClubNameHOLDER = " ";
    public static String ClubMailHOLDER = " ";
    public static String ClubPhoneHOLDER = " ";
    public static String NameOfClub;

    WebDriverManager driverManager;

    public SuperUserSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.backofficePage = driverManager.backofficePage;
        this.websiteSettingsPage = driverManager.websiteSettingsPage;
        this.superUserPage = driverManager.superUserPage;
    }

    @Then("the SU {string} CSV file can be downloaded")
    public void suCSVFilesDownload(String reportType) {
        logger.info("If we are on Chrome we download SU CSV file");
        if (driverManager.agent.contains("chrome")) {
            switch (reportType) {
                case "Basic Report":
                    superUserPage.click("//button[contains(.,'"+reportType+"')]");
                    logger.info("Download clicked, wait a while and then check Chrome downloads");
                    superUserPage.waitThirtySeconds();
                    superUserPage.waitThirtySeconds();
                    logger.info("Checking file name downloaded is 'Basic Report-*****' ");
                    superUserPage.isDownloadedInChrome(Collections.singletonList("Basic-"), "Basic");
                    break;
                case "Financial Report":
                    superUserPage.click("//button[contains(.,'"+reportType+"')]");
                    superUserPage.click("//mat-datepicker-toggle[contains(@data-mat-calendar,'mat-datepicker-0')]");
                    superUserPage.click("//button[contains(@aria-label,'Previous month')]");
                    superUserPage.waitTwoSeconds();
                    superUserPage.click("(//td[@role='gridcell'])[1]");
                    superUserPage.click("(//td[@role='gridcell'])[7]");
                    superUserPage.waitTwoSeconds();
                    superUserPage.click("//button[contains(.,'Download Report')]");
                    logger.info("Download clicked, wait a while and then check Chrome downloads");
                    superUserPage.waitUntilElementInvisible("//mat-dialog-actions[contains(.,'cancel  Download Report')]",60);
                    superUserPage.waitThirtySeconds();
                    logger.info("Checking file name downloaded is 'Financial Report-*****' ");
                    superUserPage.isDownloadedInChrome(Collections.singletonList("Financial-"), "Financial");
                    break;
                default:
                    throw new NotFoundException("For some reason there is no case for suCSVFilesDownload!");
            }
        }
    }

    @Then("Checkout is {string} for club")
    public void suEnablesClubCheckout(String display_option) {
        superUserPage.click(SuperUserPage.Step2TileTextSU);
        superUserPage.click(SuperUserPage.Step3TileTextSU);
        superUserPage.click(SuperUserPage.Step4TileTextSU);
        superUserPage.findOnPage(SuperUserPage.AccountFeaturesHeading);
        switch (display_option) {
            case "disabled":
                if (superUserPage.isElementPresent(SuperUserPage.CheckoutFeatureToggleOFF)) {
                    logger.warn("Checkout toggle was already OFF, test continue");
                    superUserPage.waitOneSecond();
                } else {
                    superUserPage.click(BackofficePage.CheckoutFeatureToggle);
                    superUserPage.waitOneSecond();
                    superUserPage.click(WebsiteSettingsPage.UpdateTopButton);
                    superUserPage.waitUntilElementInvisible(BackofficePage.SuccessfullyUpdated,10);
                    superUserPage.waitFiveSeconds();
                    assertThat("Toggle change didn't work, still ON!", superUserPage.isElementDisplayed(SuperUserPage.CheckoutFeatureToggleOFF));
                    logger.info("Checkout set to OFF");
                    superUserPage.waitTwoSeconds();
                }
                break;
            case "enabled":
                if (superUserPage.isElementPresent(SuperUserPage.CheckoutFeatureToggleON)) {
                    logger.warn("Checkout toggle was already ON, test continue");
                    superUserPage.waitOneSecond();
                } else {
                    superUserPage.click(BackofficePage.CheckoutFeatureToggle);
                    superUserPage.waitOneSecond();
                    superUserPage.click(WebsiteSettingsPage.UpdateTopButton);
                    superUserPage.waitOneSecond();
                    superUserPage.waitUntilElementInvisible(BackofficePage.SuccessfullyUpdated, 10);
                    assertThat("Toggle change didn't work, still OFF!", superUserPage.isElementDisplayed(SuperUserPage.CheckoutFeatureToggleON));
                    logger.info("Checkout set to ON");
                    superUserPage.waitTwoSeconds();
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for suEnablesClubCheckout!");
        }
    }

    @And("PaymentProvider is {string} for club")
    public void suEnablesClubPaymentProvider(String display_option) {
        superUserPage.click(SuperUserPage.Step2TileTextSU);
        superUserPage.click(SuperUserPage.Step3TileTextSU);
        superUserPage.click(SuperUserPage.Step4TileTextSU);
        superUserPage.findOnPage(SuperUserPage.AccountFeaturesHeading);
        switch (display_option) {
            case "disabled":
                if (superUserPage.isElementPresent(SuperUserPage.PaymentProviderFeatureToggleOFF)) {
                    logger.warn("PaymentProvider toggle was already OFF, test continue");
                } else {
                    superUserPage.click(PaymentProviderPage.PaymentProviderFeatureToggle);
                    superUserPage.waitOneSecond();
                    superUserPage.click(WebsiteSettingsPage.UpdateTopButton);
                    superUserPage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully,10);
                    superUserPage.waitThreeSeconds();
                    assertThat("Toggle change didn't work, still ON!", superUserPage.isElementDisplayed(SuperUserPage.PaymentProviderFeatureToggleOFF));
                    logger.info("PaymentProvider set to OFF");
                }
                break;
            case "enabled":
                if (superUserPage.isElementPresent(SuperUserPage.PaymentProviderFeatureToggleON)) {
                    logger.warn("PaymentProvider toggle was already ON, test continue");
                } else {
                    superUserPage.click(PaymentProviderPage.PaymentProviderFeatureToggle);
                    superUserPage.waitOneSecond();
                    superUserPage.click(WebsiteSettingsPage.UpdateTopButton);
                    superUserPage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully,10);
                    superUserPage.waitThreeSeconds();
                    assertThat("Toggle change didn't work, still OFF!", superUserPage.isElementDisplayed(SuperUserPage.PaymentProviderFeatureToggleON));
                    logger.info("PaymentProvider set to ON");
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for suEnablesClubPaymentProvider!");
        }
    }


    @And("{string} is displayed on su accounts page")
    public void checkCurrencySymbolOnSUAccountsPage(String currencySymbol) {
        switch (currencySymbol) {
            case "Euro":
                superUserPage.findOnPage(SuperUserPage.EuroSymbolSUAccounts);
                break;
            case "Pound":
                superUserPage.findOnPage(SuperUserPage.PoundSymbolSUAccounts);
                break;
            case "n/a":
                superUserPage.findOnPage(SuperUserPage.NACurrencySUAccounts);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkCurrencySymbolOnSUAccountsPage!");
        }
    }

    @When("they search {string} club")
    public void suClubSearch(String club_name) {
        superUserPage.findOnPage(SuperUserPage.SUClubSearchField);
        logger.info("Searching for " + club_name);
        superUserPage.sendKeys(SuperUserPage.SUClubSearchField, club_name);
    }

    @Then("{string} club is displayed")
    public void suClubSearchResult(String club_name) {
        logger.info("Waiting for " + club_name);
        superUserPage.waitUntilElementInvisible(SuperUserPage.SUAccountPageLoadingState, 20);
        if (club_name.contains("NoSuchClub")) {
            superUserPage.findOnPage(SuperUserPage.SUEmptyStateText);
        } else {
            superUserPage.findOnPage("//*[contains(text(),'" + club_name + "')]");
            superUserPage.click("//*[contains(text(),'" + club_name + "')]");
            superUserPage.waitForElementDisplayedByXpathWithTimeout(SuperUserPage.SUAccountUpdateHeading,15);
            assertThat(
                    "Club name not as expected in SU details page!", backofficePage.getElementAttribute(SuperUserPage.SUClubNameField,
                            "value"), containsString(club_name));
        }
    }

    @Given("SuperUser creates a new {string} club with variables Governing Body {string} and Sport {string} and Product {string}")
    public void superUserCreatesANewClub(String country, String GB, String sport, String product) {
        if (envName.contains("prod")) {
            throw new IllegalArgumentException("Environment set to Prod, don't auto-create xgb accounts here!");
        }

        Lorem lorem = LoremIpsum.getInstance();
        superUserPage.findOnPage(SuperUserPage.SUNewAccountButton);
        superUserPage.click(SuperUserPage.SUNewAccountButton);

        logger.info("We are on step 1 of 4 for creation of new account, website and admin");
        superUserPage.findOnPage(SuperUserPage. SUCreateAccountHeading);

        logger.info("Checking error handling on step 1 of create account page");
        superUserPage.scrollPageToBottom();
        superUserPage.click(SuperUserPage.SUCreateButton);
        superUserPage.scrollPageToTop();
        //                         https://clubforce.atlassian.net/browse/CE-827
//        superUserPage.findOnPage(SuperUserPage.FixIssuesAndSubmitAgainErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterInNameErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterEmailErrorText1);
//        superUserPage.findOnPage(SuperUserPage.SelectCountryErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterInCountyErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterInCityErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterInFirstNameErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterInLastNameErrorText);
//        superUserPage.findOnPage(SuperUserPage.EnterEmailErrorText2);

        ClubNameHOLDER = "gen"+ country + product + RandomStringUtils.randomNumeric(6);
        ClubMailHOLDER = RandomStringUtils.randomNumeric(10)+"@clubforce.com";
        ClubPhoneHOLDER = "35383" + RandomStringUtils.randomNumeric(7);

        logger.info("Creating new club: " + ClubNameHOLDER);
        superUserPage.sendKeys(SuperUserPage.ClubNameField, ClubNameHOLDER);

        if (!GB.equals("NONE")) {
            superUserPage.click(SuperUserPage.SUGoverningBodiesDropDownField);

            if (SuperUserSteps.XGBAccountNameHOLDER.contains("zzz")) {
                logger.info("Using XGB account: "+XGBAccountNameHOLDER);
                logger.info("Scrolling inside dropdown");
                WebElement element= driverManager.driver.findElement(By.xpath("//span[@class='mat-option-text'][contains(.,'"+XGBAccountNameHOLDER+"')]"));
                driver.executeScript("arguments[0].scrollIntoView(true);",element);
                superUserPage.click("//span[@class='mat-option-text'][contains(.,'"+XGBAccountNameHOLDER+"')]");
            } else {
                logger.info("Using XGB account: "+GB);
                logger.info("Scrolling inside dropdown");
                WebElement element= driverManager.driver.findElement(By.xpath("//span[@class='mat-option-text'][contains(.,'" + GB + "')]"));
                driver.executeScript("arguments[0].scrollIntoView(true);",element);
                superUserPage.click("//span[@class='mat-option-text'][contains(.,'" + GB + "')]");
            }
            superUserPage.escape();
        }

        if (!sport.contains("NONE")) {
            logger.info("SU selects " + sport + " from sport dropdown");
            superUserPage.click(SuperUserPage.SUClubSportsAssocDropdownSports);
            superUserPage.click("//span[contains(.,'" + sport + "')]");
            superUserPage.escape();
        } else {
            logger.info("No sport selected, is: "+sport);
        }

        superUserPage.sendKeys(SuperUserPage.SUClubContactDetailsMail, ClubMailHOLDER);
        superUserPage.sendKeys(SuperUserPage.SUClubContactDetailsPhone, ClubPhoneHOLDER);
        superUserPage.sendKeys(SuperUserPage.SUClubContactDetailsWebsite, "https://www."+lorem.getWords(1)+".com");
        superUserPage.scrollPageDown();
        superUserPage.sendKeys(SuperUserPage.SUClubAddressStreet, "12 "+lorem.getCity());
        superUserPage.sendKeys(SuperUserPage.SUClubAddressCity, lorem.getCity());
        superUserPage.sendKeys(SuperUserPage.SUClubAddressCounty, lorem.getStateFull());
        superUserPage.sendKeys(SuperUserPage.SUClubAddressPostCode, lorem.getZipCode());
        superUserPage.click(SuperUserPage.SUClubAddressCountry);
        superUserPage.click("(//span[@class='mat-option-text'][contains(.,'"+country+"')])[1]");

        superUserPage.sendKeys(SuperUserPage.SUClubSocialMediaFB, "https://facebook.com/myTestClub");
        superUserPage.sendKeys(SuperUserPage.SUClubSocialMediaInsta, "https://instagram.com/myTestClub");
        superUserPage.sendKeys(SuperUserPage.SUClubSocialMediaTwitter, "https://twitter.com/myTestClub");
        superUserPage.scrollPageDown();
        superUserPage.sendKeys(SuperUserPage.SUClubRepresentativeFirstName, lorem.getFirstName());
        superUserPage.sendKeys(SuperUserPage.SUClubRepresentativeLastName, lorem.getLastName());
        superUserPage.sendKeys(SuperUserPage.SUClubRepresentativeMail, ClubMailHOLDER);
        superUserPage.sendKeys(SuperUserPage.SUClubRepresentativePhone, ClubPhoneHOLDER);
        superUserPage.click(SuperUserPage.SUClubRepresentativeDropdown);
        superUserPage.click(SuperUserPage.NoneAssignedOptionText);

        logger.info("Step 1 filled in, going to step 2 - users " + ClubNameHOLDER);
        superUserPage.click(SuperUserPage.SUCreateButton);
        superUserPage.waitForElementDisplayedByXpathWithTimeout(SuperUserPage.SUAddNewUserTitle,15);
        logger.info("Adding an admin for newly created account: "+ClubNameHOLDER);
        superUserPage.click(SuperUserPage.SUCreateUserButton);

        superUserPage.click(SuperUserPage.Step2TileTextSU);
        superUserPage.findOnPage(SuperUserPage.AddNewUserButtonOnStep2InSU);

        superUserPage.waitTwoSeconds();
        UsersPageSteps.RandomMailHolder = superUserPage.getElementAttribute("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'@clubforce.com')]", "textContent");
        logger.info("Name and mail is added. Mail is " + UsersPageSteps.RandomMailHolder);

        logger.info("Step 2 filled in, going to step 3 - website " + ClubNameHOLDER);
        superUserPage.click(SuperUserPage.SUWebsiteStepper);
        superUserPage.findOnPage(SuperUserPage.SUValidateSubdomainButton);

        superUserPage.sendKeys(SuperUserPage.SU_ClubWebsiteCreationSubDomainField, "pennybridgerugby");
        superUserPage.click(SuperUserPage.SUValidateSubdomainButton);
        superUserPage.findOnPage(SuperUserPage.SubdomainNotAvailableErrorText);
        superUserPage.sendKeys(SuperUserPage.SU_ClubWebsiteCreationSubDomainField, ClubNameHOLDER.toLowerCase().replaceAll(" ",""));
        superUserPage.click(SuperUserPage.SUValidateSubdomainButton);
        superUserPage.findOnPage(SuperUserPage.SUCreateSubdomainButton);
        superUserPage.click(SuperUserPage.SUCreateSubdomainButton);
        superUserPage.findOnPage(SuperUserPage.WebsiteSuccessfullyCreatedNotification);

        logger.info("Step 3 filled in, we are taken to step 4 - features ");
        /////////////////////////////////////////////////////////////// work around TODO remove when https://clubforce.atlassian.net/browse/CE-1124 is fixed
        superUserPage.click(SuperUserPage.Step4TileTextSU);
        ////////////////////////////////////////////////////////////// change to click update button on step 3

        superUserPage.findOnPage(SuperUserPage.LottoFeatureToggle);

        logger.info("Enable features");
        superUserPage.click(SuperUserPage.LottoFeatureToggle);
        superUserPage.click(SuperUserPage.PaymentProviderFeatureToggle);
        superUserPage.click(SuperUserPage.CheckoutFeatureToggle);
        superUserPage.click(SuperUserPage.MembershipFeatureToggle);
        superUserPage.click(SuperUserPage.ComortaisFeatureToggle);
        superUserPage.click(SuperUserPage.SUFeaturesUpdateButton);
        superUserPage.findOnPage(SuperUserPage.FeaturesUpdatedSuccessfullyNotification);
    }

    @Then("new club website can be visited")
    public void newClubIsCreated() {
        logger.info("Going to website for newly created account: "+ClubNameHOLDER);

        if (envName.contains("test")) {
            superUserPage.goTo_URL("https://"+ClubNameHOLDER+".test.clubforce.io");
        }
        if (envName.contains("sandbox")) {
            superUserPage.goTo_URL("https://"+ClubNameHOLDER+".sandbox.clubforce.io");
        }
        if (envName.contains("prod")) {
            superUserPage.goTo_URL("https://"+ClubNameHOLDER+".clubforce.com");
        }
        assertTrue(driverManager.driver.getCurrentUrl().contains(ClubNameHOLDER.toLowerCase()), "Club name is : " + ClubNameHOLDER);
        superUserPage.waitFiveSeconds();
        superUserPage.acceptCookies();
        superUserPage.findOnPage(BackofficePage.LottoButton);
    }

    @Then("there are {string} prod clubs")
    public void prodClubsAreCounted(String clubNumber) {
        superUserPage.waitThreeSeconds();
        superUserPage.click(SuperUserPage.SUAccountSearchHeading);
        if (envName.contains("prod")) {
            logger.info("Checking number of prod clubs");
            superUserPage.findOnPage("//div[@class='mat-paginator-range-label'][contains(.,'1 – 10 of "+clubNumber+"')]");
            logger.info("There are "+clubNumber+" prod clubs, as expected");
        } else {
            logger.error("Environment is not prod. Not testing anywhere else");
        }
    }

    @And("SuperUser selects the latest complete {string} account")
    public void superUserSelectsLatestProductAccount(String productType) {

        superUserPage.sendKeys(SuperUserPage.SUSearchField, productType);
        superUserPage.click(SuperUserPage.AllStatusFilter);
        superUserPage.click(SuperUserPage.CompleteStatusInDropDown);
        superUserPage.findOnPage(SuperUserPage.CompleteStatusInRow1OfTable);

        logger.info("Set pagination to 25");
        superUserPage.scrollPageToBottom();
        backofficePage.setPaginationTo25();

        logger.info("Only click a club that doesn't have 0 purchases");
        superUserPage.click(
                "//*[contains(text(),'€1')] | //*[contains(text(),'€2')] | //*[contains(text(),'€3')] " +
                        "| //*[contains(text(),'€4')] | //*[contains(text(),'€5')] | //*[contains(text(),'€6')] | //*[contains(text(),'€7')] " +
                        "| //*[contains(text(),'€8')] | //*[contains(text(),'€9')] | //*[contains(text(),'£1')] | //*[contains(text(),'£2')]" +
                        "| //*[contains(text(),'£3')] |  //*[contains(text(),'£4')] | //*[contains(text(),'£5')] | //*[contains(text(),'£6')]" +
                        "| //*[contains(text(),'£7')] | //*[contains(text(),'£8')] | //*[contains(text(),'£9')]");

        superUserPage.waitForElementDisplayedByXpathWithTimeout(SuperUserPage.SUClubNameField,10);
        NameOfClub = superUserPage.getElementAttribute(SuperUserPage.SUClubNameField, "value");
        logger.info("Club name is : " + NameOfClub);
    }

    @And("SuperUser selects the previous club")
    public void selectPreviousMembershipClubUsed(){
        logger.info("Search for club");
        superUserPage.sendKeys(SuperUserPage.SUSearchField, NameOfClub);
        superUserPage.click("//*[contains(text(),'"+NameOfClub+"')]");
    }

    @Then("they can view {string} membership transaction fee and card charges")
    public void viewMembershipTransactionFeeAndCardCharges(String clubName) {
        superUserPage.waitTwoSeconds();
        if(!superUserPage.isElementDisplayed(SuperUserPage.AccountFeaturesHeading)){
            goToStepperSU("Step 4", clubName);
        }

        switch (clubName){
            case "Ulster Rugby":
            case "Leopards Rugby":
                assertFalse(superUserPage.isElementDisplayed(SuperUserPage.MembershipFeatureToggleOFF));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipFeatureToggleON));
                logger.info("Membership toggle in on for " + clubName);
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipTransactionFeeFieldTitle));
                logger.info("Membership transaction field is displayed for " + clubName);
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipChargeAmountFieldTitle));
                logger.info("Membership charge amount field is displayed for " + clubName);
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.EditFeesButton));
                logger.info("Membership edit fees button is displayed for " + clubName);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for viewMembershipTransactionFeeAndCardCharges!");
        }
    }

    @Then("they can edit {string} membership transaction fee to {string}")
    public void editMembershipTransactionFee(String clubName, String feePercentage){
        superUserPage.waitTwoSeconds();
        if(!superUserPage.isElementDisplayed(SuperUserPage.AccountFeaturesHeading)){
            goToStepperSU("Step 4", clubName);
        }

        switch (clubName){
            case "Ulster Rugby":
            case "Leopards Rugby":
                assertFalse(superUserPage.isElementDisplayed(SuperUserPage.MembershipFeatureToggleOFF));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipFeatureToggleON));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipTransactionFeeFieldTitle));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipChargeAmountFieldTitle));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.EditFeesButton));

                if(!superUserPage.isElementDisplayed(SuperUserPage.EditFeesPopUpHeading)){
                    superUserPage.click(SuperUserPage.EditFeesButton);
                    superUserPage.findOnPage(SuperUserPage.EditFeesPopUpHeading);
                }

                logger.info("Click edit fees buttons");
                superUserPage.findOnPage(SuperUserPage.EditFeesPopUpHeading);
                superUserPage.clear(SuperUserPage.TransactionFeePercentageFieldPopUp);
                superUserPage.sendKeys(SuperUserPage.TransactionFeePercentageFieldPopUp,feePercentage);
                superUserPage.click(SuperUserPage.UpdateFeesButton);
                superUserPage.findOnPage(SuperUserPage.MembershipFeesUpdatedSuccessfullyNotification);
                superUserPage.findOnPage("//strong[contains(.,'"+feePercentage+"%')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for editMembershipTransactionFee!");
        }
    }

    @Then("they can edit {string} membership card charges to {string}")
    public void editMembershipCardCharges(String clubName, String feeAmount){
        superUserPage.waitTwoSeconds();
        if(!superUserPage.isElementDisplayed(SuperUserPage.AccountFeaturesHeading)){
            goToStepperSU("Step 4", clubName);
        }

        switch (clubName){
            case "Ulster Rugby":
            case "Leopards Rugby":
                assertFalse(superUserPage.isElementDisplayed(SuperUserPage.MembershipFeatureToggleOFF));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipFeatureToggleON));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipTransactionFeeFieldTitle));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipChargeAmountFieldTitle));
                assertTrue(superUserPage.isElementDisplayed(SuperUserPage.EditFeesButton));

                if(!superUserPage.isElementDisplayed(SuperUserPage.EditFeesPopUpHeading)){
                    superUserPage.click(SuperUserPage.EditFeesButton);
                    superUserPage.findOnPage(SuperUserPage.EditFeesPopUpHeading);
                }

                logger.info("Click edit fees buttons");
                superUserPage.findOnPage(SuperUserPage.EditFeesPopUpHeading);
                superUserPage.clear(SuperUserPage.ChargeAmountFieldPopUp);
                superUserPage.sendKeys(SuperUserPage.ChargeAmountFieldPopUp,feeAmount);
                superUserPage.click(SuperUserPage.UpdateFeesButton);
                superUserPage.findOnPage(SuperUserPage.MembershipFeesUpdatedSuccessfullyNotification);
                superUserPage.findOnPage("//strong[contains(.,'"+feeAmount+"')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for editMembershipCardCharges!");
        }
    }

    @Then("they go to {string} in SU for {string}")
    public void goToStepperSU(String stepper, String club) {
        superUserPage.click("//*[contains(text(),'"+club+"')]");
        superUserPage.waitFiveSeconds();
        switch (stepper) {
            case "Step 2":
                superUserPage.click(SuperUserPage.Step2TileTextSU);
                break;
            case "Step 3":
                superUserPage.click(SuperUserPage.Step3TileTextSU);
                break;
            case "Step 4":
                superUserPage.click(SuperUserPage.Step4TileTextSU);
                break;
            default:
        }
    }

    @Then("Membership is {string} for club")
    public void suEnablesClubMembership(String display_option) {
        superUserPage.click(SuperUserPage.Step2TileTextSU);
        superUserPage.click(SuperUserPage.Step3TileTextSU);
        superUserPage.click(SuperUserPage.Step4TileTextSU);
        superUserPage.findOnPage(SuperUserPage.MembershipFeatureToggle);
        switch (display_option) {
            case "disabled":
                if (superUserPage.isElementDisplayed(SuperUserPage.MembershipToggleON)) {
                    superUserPage.click(SuperUserPage.MembershipFeatureToggle);
                    superUserPage.click(BackofficePage.SUUpdateButton);
                    superUserPage.waitFiveSeconds();
                    assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipToggleOFF));
                    superUserPage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully, 10);
                } else {
                    assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipToggleOFF));
                }
                break;
            case "enabled":
                if (superUserPage.isElementDisplayed(SuperUserPage.MembershipToggleOFF)) {
                    superUserPage.click(SuperUserPage.MembershipFeatureToggle);
                    superUserPage.click(BackofficePage.SUUpdateButton);
                    superUserPage.waitFiveSeconds();
                    assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipToggleON));
                    superUserPage.waitUntilElementInvisible(BackofficePage.UpdatedSuccessfully, 10);
                } else {
                    assertTrue(superUserPage.isElementDisplayed(SuperUserPage.MembershipToggleON));
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for suEnablesClubMembership!");
        }
    }

    @Given("SuperUser creates a new {string} XGB account")
    public void superUserCreatesANewXGBAccount(String country) {
        if (envName.contains("prod")) {
            throw new IllegalArgumentException("Environment set to Prod, don't auto-create xgb accounts here!");
        }

        Lorem lorem = LoremIpsum.getInstance();
        superUserPage.waitFiveSeconds();
        superUserPage.click(UsersPage.SUXGBSectionButton);
        superUserPage.click("//button[contains(.,'new account')]");

        superUserPage.waitForElementDisplayedByXpathWithTimeout("//input[contains(@formcontrolname,'name')]", 5);
        logger.info("We are on new account creation page");
        XGBAccountNameHOLDER = "zzz_xgb"+ RandomStringUtils.randomNumeric(7);
        logger.info("Creating new XGB account: " + XGBAccountNameHOLDER);
        superUserPage.sendKeys(ContactPage.NameField, XGBAccountNameHOLDER);
        superUserPage.sendKeys("//input[contains(@formcontrolname,'address')]", lorem.getZipCode() + " "+ lorem.getFirstNameMale()+" street");
        superUserPage.sendKeys("//input[contains(@formcontrolname,'city')]", lorem.getCity());
        superUserPage.sendKeys(SuperUserPage.SUClubAddressCounty, lorem.getStateFull());
        superUserPage.sendKeys(SuperUserPage.SUClubAddressPostCode, lorem.getZipCode());
        superUserPage.click(SuperUserPage.SUClubAddressCountry);
        superUserPage.click("(//span[@class='mat-option-text'][contains(.,'"+country+"')])[1]");

        superUserPage.click("//button[contains(.,'Create arrow_forward')]");
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//span[@class='ng-star-inserted'][contains(.,'Account created')]", 5);

        logger.info("Adding an admin for newly created account: "+XGBAccountNameHOLDER);
        superUserPage.click("//span[contains(.,'Step 2')]");
        superUserPage.click("//button[contains(.,'new user')]");

        superUserPage.sendKeys("//input[contains(@formcontrolname,'name')]", lorem.getFirstName()+" "+lorem.getLastName());
        XGBAccountUserMailHOLDER = XGBAccountNameHOLDER+"@clubforce.com";
        superUserPage.sendKeys("//input[contains(@formcontrolname,'email')]", XGBAccountUserMailHOLDER);
        superUserPage.sendKeys("//input[contains(@formcontrolname,'password')]", "b3deG2FnmrEy");
        superUserPage.click("//button[contains(.,'Create user arrow_forward')]");
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//div[@class='cf-collection-table-tbody ng-star-inserted'][contains(.,'Name   Email "+XGBAccountUserMailHOLDER+"  Created')]", 5);
        logger.info("User added; "+XGBAccountUserMailHOLDER);
        superUserPage.click("//span[@class='ms-2'][contains(.,'Club accounts')]");
        superUserPage.waitForElementDisplayedByXpathWithTimeout(UsersPage.SUAddButton, 5);
    }

    @Given("SuperUser goes to XGB accounts page")
    public void SuperUserGoToXGBAccountsPage() {
        superUserPage.click(UsersPage.SUXGBSectionButton);
        superUserPage.waitForElementDisplayedByXpathWithTimeout("//h1[@class='cf-ui-content-title'][contains(.,'xGB accounts')]", 5);
        superUserPage.waitForElementDisplayedByXpathWithTimeout("(//strong[contains(.,'Name')])[1]", 5);
//        superUserPage.waitForElementDisplayedByXpathWithTimeout("//div[@class='col-md p-3 col-6'][contains(.,'XGB')]", 5);
        superUserPage.findElementRightOf("//h1[@class='cf-ui-content-title'][contains(.,'xGB accounts')]","//button[@routerlink='create'][contains(.,'new account')]");
    }
}
