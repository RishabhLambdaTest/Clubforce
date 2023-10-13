package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.DiscountCodePage;
import com.clubforce.pages.ProductsPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.testng.Assert.assertFalse;

public class DiscountCodeSteps extends WebDriverManager {

    public static final Logger logger = LogManager.getLogger();
    public static String discountCodeName;
    public static String validFromYear;
    public static String validFromMonth;
    public static String validFromDay;
    public static String validToYear;
    public static String validToMonth;
    public static String validToDay;
    public static String discountAmount;
    public static String discountPercent;
    public static String singleDiscountCodeRow1;
    public static String multiDiscountCodeRow1;
    public static boolean isPercentDiscount = false;
    public static boolean isAmountDiscount = false;
    public static boolean isMultiDiscount = false;

    WebDriverManager driverManager;

    public DiscountCodeSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.discountCodePage = driverManager.discountCodePage;
    }

    @Then("the default empty state is displayed for discount codes pages")
    public void checkEmptyStateForDiscountsPage() {
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodesHeading);
        verifyDiscountCodePageElements();
        discountCodePage.findOnPage(DiscountCodePage.NoDiscountsEmptyStateText);
        discountCodePage.click(DiscountCodePage.ActiveDiscountsTab);
        discountCodePage.findOnPage(DiscountCodePage.NoDiscountsEmptyStateText);
    }

    @Then("ClubAdmin can create a new {string} use {string} discount code")
    public void createDiscount(String discountCodeType,String typeOfDiscount) {
        verifyDiscountCodePageElements();
        discountCodePage.click(DiscountCodePage.NewDiscountButton);
        verifyCreateDiscountCodePageElements();

        boolean selectStateOfSingleRadioButton =  discountCodePage.findOnPage(DiscountCodePage.SingleUseCodeRadioButton).isSelected();
        boolean selectStateOfMultiRadioButton =  discountCodePage.findOnPage(DiscountCodePage.MultiUseCodeRadioButton).isSelected();

        switch (discountCodeType){
            case "single":
                logger.info("Is single use code radio button selected : " + selectStateOfSingleRadioButton);
                if(!selectStateOfSingleRadioButton){
                    logger.info("Clicking single select radio button");
                    discountCodePage.click(DiscountCodePage.SingleUseCodeRadioButtonText);
                }

                discountCodePage.click(DiscountCodePage.CreateButton);
                verifyErrorMessagesForSingleUseDiscountCodes();

                assertFalse(discountCodePage.isElementDisplayed(DiscountCodePage.LimitField));

                logger.info("Fill in number of coupons field");
                discountCodePage.sendKeys(DiscountCodePage.NumberOfCouponsField, "2");
                break;
            case "multi":
                logger.info("Is multi use code radio button selected : " + selectStateOfMultiRadioButton);
                if(!selectStateOfMultiRadioButton){
                    logger.info("Clicking multi code select radio button");
                    discountCodePage.click(DiscountCodePage.MultiUseCodeRadioButtonText);
                }

                discountCodePage.click(DiscountCodePage.CreateButton);
                verifyErrorMessagesForMultiUseDiscountCodes();

                assertFalse(discountCodePage.isElementDisplayed(DiscountCodePage.NumberOfCouponsField));

                logger.info("Fill in number of uses field");
                discountCodePage.sendKeys(DiscountCodePage.LimitField, "2");

                logger.info("Select a discount code");
                discountCodePage.click(DiscountCodePage.DiscountCodeRefreshButton);
                break;
            default:
        }

        logger.info("Fill in discount name field");
        discountCodeName = "DiscountCode " + SeleniumUtilities.getDateTimeFormat("ddMMMyyyyHHmmss");
        discountCodePage.sendKeys(DiscountCodePage.DiscountNameField, discountCodeName);
        logger.info("Discount code name is : " + discountCodeName);

        switch (typeOfDiscount){
            case "amount":
                logger.info("Select discount type");
                discountCodePage.click(DiscountCodePage.TypeOfDiscountDropDownField);
                discountCodePage.click(DiscountCodePage.AmountDiscountTypeDropDownOption);

                logger.info("Fill in discount amount");
                discountCodePage.findOnPage(DiscountCodePage.CurrencySymbol);
                int minAmount = 5 ;
                int maxAmount = 50;
                double amount = Math.random()*(maxAmount-minAmount+1)+minAmount;
                discountAmount = new DecimalFormat("#.0#").format(amount);
                discountCodePage.sendKeys(DiscountCodePage.DiscountAmountField, discountAmount);
                isPercentDiscount = false;
                isAmountDiscount = true;
                break;
            case "percentage":
                logger.info("Select discount type");
                discountCodePage.click(DiscountCodePage.TypeOfDiscountDropDownField);
                discountCodePage.click(DiscountCodePage.PercentageDiscountTypeDropDownOption);

                logger.info("Fill in discount amount");
                discountCodePage.findOnPage(DiscountCodePage.PercentageSymbol);
                discountCodePage.sendKeys(DiscountCodePage.DiscountAmountField, "101");
                discountCodePage.click(DiscountCodePage.DiscountDetailsSubHeading);
                discountCodePage.findOnPage(DiscountCodePage.InvalidValueErrorText);
                discountCodePage.clear(DiscountCodePage.DiscountAmountField);
                int minPercent = 1 ;
                int maxPercent = 99;
                int discountPercentage = (int) (Math.random()*(maxPercent-minPercent+1)+minPercent);
                discountPercent = Integer.toString(discountPercentage);
                discountCodePage.sendKeys(DiscountCodePage.DiscountAmountField, discountPercent);
                isPercentDiscount = true;
                isAmountDiscount = false;
                break;
            default:
        }

        logger.info("Set the valid from date - todays date");
        getDates();
        discountCodePage.click(DiscountCodePage.ValidFromDropDown);
        while(!discountCodePage.isElementDisplayed("//td[@class='mat-calendar-body-label'][contains(.,'"+validFromYear+"')]")){
            discountCodePage.click(DiscountCodePage.NextYearButton);
        }
        discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today'][contains(.,'"+validFromMonth.toUpperCase()+"')]");
        discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today'][contains(.,'"+validFromDay+"')]");

        logger.info("Set the valid to date - to tomorrow");
        discountCodePage.click(DiscountCodePage.ValidToDropDown);
        while(!discountCodePage.isElementDisplayed("//td[@class='mat-calendar-body-label'][contains(.,'"+validToYear+"')]")){
            discountCodePage.click(DiscountCodePage.NextYearButton);
        }
        discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today'][contains(.,'"+validToMonth.toUpperCase()+"')]");
        discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+validToDay+"')]");

        logger.info("Click create button");
        discountCodePage.click(DiscountCodePage.CreateButton);

        logger.info("Check club admin is back on discount codes page and that the discount codes are created");
        discountCodePage.findOnPage(DiscountCodePage.DiscountNameColumnHeader);
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodeColumnHeader);
        discountCodePage.findOnPage(DiscountCodePage.ValidFromColumnHeader);
        discountCodePage.findOnPage(DiscountCodePage.ValidToColumnHeader);
        discountCodePage.findOnPage(DiscountCodePage.AmountColumnHeader);
        discountCodePage.findOnPage(DiscountCodePage.ActionsColumnHeader);

        switch (discountCodeType){
            case "single":
                logger.info("Check coupon is displayed in table");
                discountCodePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountCodeName+"')])[1]");
                discountCodePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountCodeName+"')])[2]");
                if(typeOfDiscount.equals("percentage")){
                    discountCodePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountPercent+"')])[1]");
                    discountCodePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountPercent+"')])[2]");
                }else{
                    discountCodePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountCodeName+"')])[1]");
                    discountCodePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountCodeName+"')])[2]");
                }
                singleDiscountCodeRow1 = discountCodePage.getElementAttribute(DiscountCodePage.DiscountCodeRow1, "textContent");
                singleDiscountCodeRow1 = singleDiscountCodeRow1.replaceAll("copy", "");
                singleDiscountCodeRow1 = singleDiscountCodeRow1.substring(1);
                logger.info("Single discount code is :" + singleDiscountCodeRow1);
                isMultiDiscount = false;
                break;
            case "multi":
                logger.info("Check coupon is displayed in table");
                discountCodePage.waitForElementDisplayedByXpathWithTimeout("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountCodeName+"')]",30);
                if(typeOfDiscount.equals("amount")){
                    discountCodePage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountAmount+"')]");
                }else{
                    discountCodePage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountPercent+"')]");
                }

                multiDiscountCodeRow1 = discountCodePage.getElementAttribute(DiscountCodePage.DiscountCodeRow1, "textContent");
                multiDiscountCodeRow1 = multiDiscountCodeRow1.replaceAll("copy", "");
                multiDiscountCodeRow1 = multiDiscountCodeRow1.substring(1);
                logger.info("Multi discount code is :" + multiDiscountCodeRow1);
                isMultiDiscount = true;
                break;
            default:
        }
    }

    @Then("ClubAdmin can deactivate an active discount code")
    public void deactivateDiscountCode(){
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodesHeading);
        verifyDiscountCodePageElements();

        logger.info("Check if empty state is displayed");
        if(discountCodePage.isElementDisplayed(DiscountCodePage.NoDiscountsEmptyStateText)){
            createDiscount("single", "percentage");
        }

        logger.info("Check if there are any active discounts");
        if(!discountCodePage.isElementDisplayed(DiscountCodePage.FirstCopyIcon)){
            createDiscount("multi", "amount");
        }

        int i = 1;
        while(!discountCodePage.isElementDisplayed(DiscountCodePage.DeactivateButtonPopUp)){
            discountCodePage.click("(//button[contains(.,'keyboard_arrow_down')])["+i+"]");
            discountCodePage.waitOneSecond();

            if(discountCodePage.isElementDisplayed(DiscountCodePage.FirsDeactivatedDeactivateButton)){
                discountCodePage.click(DiscountCodePage.FirsDeactivatedDeactivateButton);
                discountCodePage.findOnPage(DiscountCodePage.DeactivateDiscountCodeHeading);
            }else{
                logger.info("Button is not clickable for row : " + i);
                discountCodePage.click(DiscountCodePage.ActionsColumnHeader);
                i++;
            }

            if(i == 10){
                discountCodePage.click(BackofficePage.PaginationNextButton);
                i = 1;
            }
        }

        logger.info("Deactivate button is activate in row : " + i);
        discountCodePage.click(DiscountCodePage.DeactivateButtonPopUp);
    }

    @Then("ClubAdmin can edit an {string} discount code")
    public void editDiscountCode(String discountStatus){
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodesHeading);
        discountCodePage.waitUntilElementInvisible("(//span[@aria-valuetext='Loading...'])[1]", 40);
        verifyDiscountCodePageElements();

        logger.info("Check if empty state is displayed");
        if(discountCodePage.isElementDisplayed(DiscountCodePage.NoDiscountsEmptyStateText)){
            createDiscount("single", "percentage");
        }

        logger.info("Check if there are any active discounts");
        if(!discountCodePage.isElementDisplayed(DiscountCodePage.FirstCopyIcon)){
            createDiscount("multi", "amount");
        }

        switch (discountStatus){
            case "active":
                logger.info("Check if a code is active or deactivated");
                int i = 1;
                while(!discountCodePage.isElementDisplayed(DiscountCodePage.FirsDeactivatedDeactivateButton)){
                    discountCodePage.click(DiscountCodePage.ActionsColumnHeader);
                    discountCodePage.click("(//button[contains(.,'keyboard_arrow_down')])["+i+"]");
                    discountCodePage.waitOneSecond();
                    logger.info("Deactivate button is not clickable for row : " + i);
                    i++;

                    if(i == 10){
                        discountCodePage.click(BackofficePage.PaginationNextButton);
                        i = 1;
                    }
                }
                int editNum = i-1;
                discountCodePage.click("(//li[contains(.,'Edit')])["+editNum+"]");
                discountCodePage.findOnPage(DiscountCodePage.DiscountDetailsSubHeading);
                discountCodePage.findOnPage(DiscountCodePage.ValidToDropDown);
                getDates();
                discountCodePage.click(DiscountCodePage.ValidToDropDown);
                discountCodePage.click(DiscountCodePage.NextYearButton);
                discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+validFromMonth.toUpperCase()+"')]");
                discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+validFromDay+"')]");
                discountCodePage.click(DiscountCodePage.UpdateButton);
                break;
            case "expired":
                discountCodePage.findOnPage(DiscountCodePage.ActiveDiscountsTab);
                discountCodePage.click(DiscountCodePage.InactiveDiscountsTab);
                discountCodePage.waitUntilElementInvisible("(//span[@aria-valuetext='Loading...'])[1]", 40);

                if(discountCodePage.isElementDisplayed(DiscountCodePage.NoDiscountsEmptyStateText)){
                    discountCodePage.click(DiscountCodePage.ActiveDiscountsTab);
                    discountCodePage.waitUntilElementInvisible("(//span[@aria-valuetext='Loading...'])[1]", 40);
                }

                if(discountCodePage.isElementDisplayed(DiscountCodePage.NoDiscountsEmptyStateText)){
                    logger.info("Create a new discount");
                    createDiscount("multi","amount");
                }

                logger.info("Now we will deactivate discount so that discount can move to inactive tab");
                deactivateDiscountCode();
                discountCodePage.click(DiscountCodePage.InactiveDiscountsTab);

                logger.info("Edit discount in row 1");
                String inactiveDiscountCodeFromRow1 = discountCodePage.getElementAttribute("(//div[contains(@class,'col-md p-3 text-break text-truncate col-6')])[2]", "textContent");
                discountCodePage.click(DiscountCodePage.InactiveDiscountDropDownRow1);
                discountCodePage.click(DiscountCodePage.FirstEditButton);
                getDates();
                discountCodePage.click(DiscountCodePage.ValidToDropDown);
                discountCodePage.click(DiscountCodePage.NextYearButton);
                discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+validFromMonth.toUpperCase()+"')]");
                discountCodePage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'"+validFromDay+"')]");
                discountCodePage.click(DiscountCodePage.UpdateButton);
                discountCodePage.waitFiveSeconds();

                if(discountCodePage.isElementDisplayed("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+inactiveDiscountCodeFromRow1+"')]")){
                    logger.info("Discount is still on inactive tab. Check if limit is met");
                    String limitDetails = discountCodePage.getElementAttribute("(//div[contains(@class,'col-md p-3 text-break text-truncate col-6')])[6]","textContent");
                    char c = '/';
                    int index = limitDetails.indexOf(c);
                    String beginningLimit = "null";
                    String endLimit = "null";
                    if (index != -1) {
                        beginningLimit = limitDetails.substring(1, index);
                        logger.info("Number before / :" + beginningLimit);
                        endLimit = limitDetails.substring(index+2);
                        logger.info("Number after / :" + endLimit);
                    }

                    if(!beginningLimit.equals(endLimit)){
                        logger.info("If limit is not set then reactivate code");
                        discountCodePage.click(DiscountCodePage.InactiveDiscountDropDownRow1);
                        discountCodePage.click(DiscountCodePage.FirstActivateButton);
                    }
                }

                discountCodePage.click(DiscountCodePage.ActiveDiscountsTab);
                discountCodePage.findOnPage("//*[contains(text(),'"+inactiveDiscountCodeFromRow1+"')]");
                logger.info("Discount is on active tab and can now be used");
                break;
            default:
        }
    }

    @Then("purchase membership plan with {string} discount code using {string} card")
    public void purchasePlanWitDiscountCode(String typeOfDiscount, String card){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Filling in card details");
        discountCodePage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Hello,')]", 10);
        discountCodePage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.CheckoutPageNameOnCard, 10);
        discountCodePage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
        discountCodePage.waitTwoSeconds();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        discountCodePage.waitFiveSeconds();
        discountCodePage.sendKeys(ProductsPage.CheckoutPageCardNumber, card);
        discountCodePage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
        discountCodePage.sendKeys(ProductsPage.CheckoutPageCVC, "123");

        driverManager.driver.switchTo().parentFrame();
        logger.info("Add discount code");
        if (typeOfDiscount.equals("single use")){
            discountCodePage.sendKeys(ProductsPage.DiscountCodeField, singleDiscountCodeRow1);
        }else{
            discountCodePage.sendKeys(ProductsPage.DiscountCodeField, multiDiscountCodeRow1);
        }
        discountCodePage.click(ProductsPage.DiscountCodeApplyButton);
        discountCodePage.findOnPage(ProductsPage.DiscountCodeAppliedTextAndCheckIcon);
        discountCodePage.findOnPage(ProductsPage.DiscountCodeRemoveButton);
        discountCodePage.click(ProductsPage.DiscountsExpandIcon);
        discountCodePage.findOnPage("//span[contains(.,'"+discountCodeName+"')]");
        discountCodePage.waitFiveSeconds();
        discountCodePage.click(ProductsPage.CheckoutPayNowButton);

        logger.info("Checking that confirmation page is displayed");
        discountCodePage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.ConfirmationPageTitle, 20);
        discountCodePage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.ConfirmationPageSubTitle, 20);
        discountCodePage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.ConfirmationPageOrderSummaryTitle, 20);

        logger.info("Extracting order date, time and ID");
        MembershipSteps.ORDER_DATE_ID_HOLDER = discountCodePage.getElementAttribute("//p[contains(.,'Order placed on ')]", "textContent");
        logger.info("ORDER_DATE_ID_HOLDER = "+ ProductPurchaseSteps.ORDER_DATE_ID_HOLDER);
        discountCodePage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.MembershipPlansConfirmationPageHeading, 15);
        MembershipSteps.ORDER_ID = discountCodePage.getElementAttribute("//p[contains(.,'ID ')]", "textContent");
        MembershipSteps.ORDER_ID_HOLDER = MembershipSteps.ORDER_ID.substring(3, 9);
        logger.info("ORDER_ID_HOLDER = "+ MembershipSteps.ORDER_ID_HOLDER);

        discountCodePage.findOnPage("//*[contains(text(),'Discount ("+discountCodeName+"): -')]");
        //TODO annie - do calculations etc and check confirmation page too - Need to wait till sub total discussion to end
    }

    @Then("user attempts to use code and gets {string} error message")
    public void tryPurchasePlanWithUsedSingleCode(String errorMessage){
        logger.info("Filling in card details");
        Lorem lorem = LoremIpsum.getInstance();
        discountCodePage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Hello,')]", 10);
        discountCodePage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.CheckoutPageNameOnCard, 10);
        discountCodePage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
        discountCodePage.waitTwoSeconds();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        discountCodePage.waitFiveSeconds();
        discountCodePage.sendKeys(ProductsPage.CheckoutPageCardNumber, "4000000000000077");
        discountCodePage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
        discountCodePage.sendKeys(ProductsPage.CheckoutPageCVC, "123");

        driverManager.driver.switchTo().parentFrame();
        logger.info("Add discount code");
        discountCodePage.sendKeys(ProductsPage.DiscountCodeField, singleDiscountCodeRow1);
        discountCodePage.click(ProductsPage.DiscountCodeApplyButton);
        discountCodePage.findOnPage("//mat-error[contains(.,'"+errorMessage+"')]");
    }

    @And("ClubAdmin goes to discount code report page")
    public void goToDiscountCodeReportPage(){
        discountCodePage.click(DiscountCodePage.SeeReportButton);
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodeReportHeading);
        discountCodePage.findOnPage(DiscountCodePage.TotalDiscountsCodesUsedText);
    }

    @Then("ClubAdmin can download discount code csv file")
    public void exportDiscountCodeCSVFile(){
        if (driverManager.agent.contains("chrome")) {
            logger.info("As we are on Chrome we also download CSV");
            discountCodePage.findOnPage(DiscountCodePage.TotalDiscountsCodesUsedText);
            discountCodePage.click(DiscountCodePage.ExportCSVButton);
            discountCodePage.waitThirtySeconds();
            logger.info("Checking file name downloaded is 'discount_codes_report-*****' ");
            discountCodePage.isDownloadedInChrome(Collections.singletonList("discount_codes"), "discount");
        }
    }

    public void verifyDiscountCodePageElements() {
        logger.info("Verify discount code page elements (activate tab)");
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodesHeading);
        discountCodePage.findOnPage(DiscountCodePage.NewDiscountButton);
        discountCodePage.findOnPage(DiscountCodePage.ActiveDiscountsTab);
        discountCodePage.findOnPage(DiscountCodePage.InactiveDiscountsTab);
    }

    public void verifyCreateDiscountCodePageElements() {
        logger.info("Verify create discount code page elements");
        discountCodePage.findOnPage(DiscountCodePage.BackButton);
        discountCodePage.findOnPage(DiscountCodePage.CreateDiscountCodeHeading);
        discountCodePage.findOnPage(DiscountCodePage.DiscountDetailsSubHeading);
        discountCodePage.findOnPage(DiscountCodePage.SingleUseCodeRadioButton);
        discountCodePage.findOnPage(DiscountCodePage.SingleUseCodeExplanationText);
        discountCodePage.findOnPage(DiscountCodePage.MultiUseCodeRadioButton);
        discountCodePage.findOnPage(DiscountCodePage.MultiUseCodeExplanationText);
        discountCodePage.findOnPage(DiscountCodePage.DiscountNameField);
        discountCodePage.findOnPage(DiscountCodePage.TypeOfDiscountDropDownField);
        discountCodePage.findOnPage(DiscountCodePage.ValidFromDropDown);
        discountCodePage.findOnPage(DiscountCodePage.ValidToDropDown);
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodeField);
        discountCodePage.findOnPage(DiscountCodePage.DiscountCodeRefreshButton);
        discountCodePage.findOnPage(DiscountCodePage.CancelButton);
        discountCodePage.findOnPage(DiscountCodePage.CreateButton);
    }

    public void verifyErrorMessagesForSingleUseDiscountCodes(){
        logger.info("Verify all error messages display for mandatory fields for single use discount code");
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterNameErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterTypeOfDiscountErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterNumberOfCouponsErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterValidFromErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterValidToErrorText);
    }

    public void verifyErrorMessagesForMultiUseDiscountCodes(){
        logger.info("Verify all error messages display for mandatory fields for single use discount code");
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterNameErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterTypeOfDiscountErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterLimitText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterValidFromErrorText);
        discountCodePage.findOnPage(DiscountCodePage.PleaseEnterValidToErrorText);
    }

    public void getDates(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        logger.info("Today's current date is : " + currentDate);
        String formattedDate = currentDate.format(formatter);
        validFromYear = formattedDate.substring(7,11);
        logger.info("Valid from year : " + validFromYear);
        validFromMonth = formattedDate.substring(3,6);
        logger.info("Valid from month : " + validFromMonth);
        if (formattedDate.substring(0,1).contains("0")) {
            validFromDay = formattedDate.substring(1,2);
            logger.info("Valid from day : " + validFromDay);
        } else {
            validFromDay = formattedDate.substring(0,2);
            logger.info("Valid from day : " + validFromDay);
        }

        LocalDate tomorrow = currentDate.plusDays(1);
        String formattedTomorrowDate = tomorrow.format(formatter);
        validToYear = formattedTomorrowDate.substring(7,11);
        logger.info("Valid to year : " + validToYear);
        validToMonth = formattedTomorrowDate.substring(3,6);
        logger.info("Valid to month : " + validToMonth);
        if (formattedDate.substring(0,1).contains("0")) {
            validToDay = formattedTomorrowDate.substring(1,2);
            logger.info("Valid to day : " + validToDay);
        } else {
            validToDay = formattedTomorrowDate.substring(0,2);
            logger.info("Valid to day : " + validToDay);
        }
    }
}
