package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.MembershipDiscountPage;
import com.clubforce.pages.MembershipPage;
import com.clubforce.pages.ProductsPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.testng.Assert.*;

public class MembershipDiscountSteps extends WebDriverManager {

    public static final Logger logger = LogManager.getLogger();
    public static String discountName;
    public static String discountPercentage1;
    public static String discountPercentage2;
    public static String discountAmount1;
    public static String discountAmount2;
    public static String totalPrice;
    public static String discountPrice;
    public static String subTotalPrice;
    WebDriverManager driverManager;

    public MembershipDiscountSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.membershipDiscountPage = driverManager.membershipDiscountPage;
        this.membershipPage = driverManager.membershipPage;
    }

    @Then("the default empty state is displayed for discounts page")
    public void checkEmptyStateForDiscountsPage(){
        membershipDiscountPage.findOnPage(MembershipDiscountPage.DiscountsHeading);
        verifyDiscountsPageElements();
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.NoDiscountsText));
    }

    @And("they proceed to {string} of multi plan creation")
    public void goTofMultiPlanDiscountSetUpStep(String stepNumber){
        switch (stepNumber){
            case "Step 1":
                membershipDiscountPage.click(MembershipDiscountPage.NewDiscountButton);
                membershipDiscountPage.findOnPage(String.format(MembershipDiscountPage.DiscountPageText, "Step 1 of 2 - Select plans to be included in the discount"));
                verifyNewDiscountStep1PageElements();
                logger.info("On step 1 of discount set up");
                break;
            case "Step 2":
                if(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.NewDiscountButton)){
                    membershipDiscountPage.click(MembershipDiscountPage.NewDiscountButton);
                    membershipDiscountPage.findOnPage(String.format(MembershipDiscountPage.DiscountPageText, "Step 1 of 2 - Select plans to be included in the discount"));
                }

                boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(MembershipDiscountPage.NextStepButton)).isEnabled();

                if(isButtonEnabled){
                    membershipPage.scrollPageToTop();
                    membershipDiscountPage.click(MembershipDiscountPage.NextStepButton);
                }

                membershipDiscountPage.findOnPage(MembershipDiscountPage.CreateNewDiscountHeadingStep2);
                verifyNewDiscountStep2PageElements();
                logger.info("On step 2 of discount set up");
                break;
            default:
        }
    }

    @And("they select plans for discount")
    public void selectPlansForDiscounts(){
        int i = 1;
        String planNameInRow = membershipDiscountPage.getElementAttribute("(//span[@class='d-block text-truncate ng-star-inserted'])["+i+"]", "textContent");
        planNameInRow = planNameInRow.substring(1, planNameInRow.length()-1);
        logger.info("Plan name "+planNameInRow+" is row : " + i);

        while (!planNameInRow.equalsIgnoreCase(MembershipSteps.NEW_PLAN_NAME)){
            i++;
            planNameInRow = membershipDiscountPage.getElementAttribute("(//span[@class='d-block text-truncate ng-star-inserted'])["+i+"]", "textContent");
            planNameInRow = planNameInRow.substring(1, planNameInRow.length()-1);
            logger.info("Plan name "+planNameInRow+" is row : " + i);
        }

        logger.info("The plan we looking for is in row " +i+ " so we need to click the checkbox in row " +i);
        i = (i*2) + 1;
        membershipDiscountPage.centreElement("(//span[contains(@class,'mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin')])["+i+"]");
        membershipPage.waitTwoSeconds();
        membershipDiscountPage.click("(//span[contains(@class,'mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin')])["+i+"]");
    }

    @And("they set {string} multiplan discounts for the plans")
    public void setDiscountsForPlans(String DiscountType){
        verifyNewDiscountStep2PageElements();
        membershipDiscountPage.findOnPage(MembershipDiscountPage.PlansSelectedHeading);
        discountName = "Multiplan discount " + RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3);
        membershipDiscountPage.click(MembershipDiscountPage.DiscountNameField);
        membershipDiscountPage.click(MembershipDiscountPage.CreateNewDiscountHeadingStep2);
        membershipPage.waitThreeSeconds();
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.PleaseEnterDiscountNameText));
        membershipDiscountPage.sendKeys(MembershipDiscountPage.DiscountNameField, discountName);
        membershipDiscountPage.click(MembershipDiscountPage.TypeOfDiscountDropDownField);

        switch (DiscountType){
            case "percentage":
                membershipDiscountPage.click(MembershipDiscountPage.TypeOfDiscountPercentageOption);
                membershipDiscountPage.findOnPage(MembershipDiscountPage.PercentageSymbol);
                membershipDiscountPage.findOnPage(MembershipDiscountPage.NumberOfPlansFirstField);
                membershipDiscountPage.click(MembershipDiscountPage.DiscountValueFirstField);
                membershipDiscountPage.click(MembershipDiscountPage.CreateNewDiscountHeadingStep2);
                membershipDiscountPage.findOnPage(MembershipDiscountPage.PleaseEnterDiscountErrorText);
                logger.info("Adding a second discount");
                membershipDiscountPage.click(MembershipDiscountPage.AddDiscountButton);
                membershipDiscountPage.findOnPage(MembershipDiscountPage.NumberOfPlansSecondField);
                discountPercentage1 = "10";
                discountPercentage2 = "20";
                membershipDiscountPage.sendKeys(MembershipDiscountPage.DiscountValueFirstField , discountPercentage1);
                membershipDiscountPage.sendKeys(MembershipDiscountPage.DiscountValueSecondField , discountPercentage2);
                break;
            case "amount":
                membershipDiscountPage.click(MembershipDiscountPage.TypeOfDiscountAmountOption);
                logger.info("Adding a second discount");
                membershipDiscountPage.findOnPage(MembershipDiscountPage.NumberOfPlansFirstField);
                membershipDiscountPage.click(MembershipDiscountPage.DiscountValueFirstField);
                membershipDiscountPage.click(MembershipDiscountPage.CreateNewDiscountHeadingStep2);
                membershipDiscountPage.findOnPage(MembershipDiscountPage.PleaseEnterDiscountErrorText);
                logger.info("Adding a second discount");
                membershipDiscountPage.click(MembershipDiscountPage.AddDiscountButton);
                membershipDiscountPage.findOnPage(MembershipDiscountPage.NumberOfPlansSecondField);
                discountAmount1 = "25";
                discountAmount2 = "50";
                membershipDiscountPage.sendKeys(MembershipDiscountPage.DiscountValueFirstField , discountAmount1);
                membershipDiscountPage.sendKeys(MembershipDiscountPage.DiscountValueSecondField , discountAmount2 );
                break;
            default:
        }

    }

    @And("they can publish the multi plan discounts")
    public void publishMultiPlanDiscount(){
        logger.info("Click create button");
        membershipDiscountPage.click(MembershipDiscountPage.CreateButton);
        membershipDiscountPage.findOnPage(MembershipDiscountPage.DiscountsHeading);
        assertTrue(membershipDiscountPage.isElementDisplayed("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountName+"')]"));
        verifyDiscountsPageElements();
    }

    @And("ClubAdmin edits a discount")
    public void editDiscount(){
        logger.info("Edit the 1st discount in the table on discounts page");
        String nameOfDiscountInFirstRow = membershipDiscountPage.getElementAttribute("//div[@class='col-md p-3 text-break text-truncate col-6']", "textContent");
        nameOfDiscountInFirstRow =nameOfDiscountInFirstRow.substring(1,nameOfDiscountInFirstRow.length()-1);
        logger.info("Name of discount in row 1 of table : " + nameOfDiscountInFirstRow);
        membershipDiscountPage.click(MembershipDiscountPage.FirstEditButton);
        logger.info("Clicked first edit button and now proceeding to step 1 of editing discount");
        membershipDiscountPage.findOnPage("//h1[@class='mb-0'][contains(.,'Edit discount: "+nameOfDiscountInFirstRow+"')]");
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.Step1CreateDiscountInformationText));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.NextStepButton));
        logger.info("For now we not adding or removing plans. We just going to edit details on step 2");
        membershipDiscountPage.click(MembershipDiscountPage.NextStepButton);
        membershipDiscountPage.findOnPage(MembershipDiscountPage.PlansSelectedHeading);
        String nameOfPlanUnderPlansSelected = membershipDiscountPage.getElementAttribute("(//div[contains(@class,'col-5')])[1]", "textContent");
        assertNotEquals(nameOfPlanUnderPlansSelected, "null");

        logger.info("Change discount name");
        discountName = "Updated discount " + RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3);
        membershipDiscountPage.clear(MembershipDiscountPage.DiscountNameField);
        membershipDiscountPage.sendKeys(MembershipDiscountPage.DiscountNameField, discountName);

        if(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.TypeOfDiscountAmount)){
            logger.info("Type of discount currently is amount");
            membershipDiscountPage.click(MembershipDiscountPage.TypeOfDiscountAmount);
            logger.info("Change to percentage");
            membershipDiscountPage.click(MembershipDiscountPage.PercentageDropDownOption);
            assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.PercentageSymbol));
        }else{
            logger.info("Type of discount currently is percentage");
            membershipDiscountPage.click(MembershipDiscountPage.TypeOfDiscountPercentage);
            logger.info("Change to amount");
            membershipDiscountPage.click(MembershipDiscountPage.AmountDropDownOption);
            assertFalse(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.PercentageSymbol));
        }

        int i = 1;
        while (membershipDiscountPage.isElementDisplayed("(//mat-icon[@role='img'][contains(.,'remove_circle_outline')])["+i+"]")){
            membershipDiscountPage.clear("(//input[contains(@formcontrolname,'discount_amount')])["+i+"]");
            membershipDiscountPage.sendKeys("(//input[contains(@formcontrolname,'discount_amount')])["+i+"]" , "2"+i);
            i++;
        }

    }

    @And("they can re publish the multi plan discounts")
    public void rePublishMultiPlanDiscount(){
        logger.info("Click create button");
        membershipDiscountPage.click(MembershipDiscountPage.UpdateButton);
        membershipDiscountPage.findOnPage(MembershipDiscountPage.DiscountsHeading);
        assertTrue(membershipDiscountPage.isElementDisplayed("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+discountName+"')]"));
        verifyDiscountsPageElements();
    }

    @And("ClubAdmin deactivates a discount on discount page")
    public void deactivateADiscountOnDiscountPage(){
        int i = 1;
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath("(//button[contains(.,'Deactivate')])["+i+"]")).isEnabled();
        logger.info("Is deactivate button disabled for row "+i+" :- " + isButtonEnabled);

        while(!isButtonEnabled) {
            i++;
            isButtonEnabled = driverManager.driver.findElement(By.xpath("(//button[contains(.,'Deactivate')])["+i+"]")).isEnabled();
        }

        membershipDiscountPage.click("(//button[contains(.,'Deactivate')])["+i+"]");
        membershipDiscountPage.click(MembershipDiscountPage.DeactivateButtonInPopUp);
        membershipDiscountPage.findOnPage(MembershipDiscountPage.DeactivationSuccessNotification);
    }

    @And("add {string} plans with {string} discount to cart")
    public void addDiscountPlansToCart(String numberOfPlans, String discountType){
        logger.info("Verify that summary is correct and that the discount "+discountName+" is displayed");
        assertTrue(membershipDiscountPage.isElementDisplayed("//span[contains(.,'Discount ("+discountName+")')]"));

        String membershipSubTotalPrice = membershipDiscountPage.getElementAttribute(
                "//*[contains(@class, 'justify-content-between') and contains(.//*, 'Subtotal')]", "textContent");
        subTotalPrice = membershipSubTotalPrice.substring(10, membershipSubTotalPrice.length() - 1);
        logger.info("Membership subtotal price for the plan that are being purchased :" + subTotalPrice);

        String membershipDiscountPrice = membershipDiscountPage.getElementAttribute(
                "//*[contains(@class, 'justify-content-between') and contains(.//*, 'Discount')]", "textContent");
        discountPrice = membershipDiscountPrice.substring(38, membershipDiscountPrice.length() - 1);
        logger.info("Membership discount price for the plan that are being purchased :" + discountPrice);

        String membershipPrice = membershipDiscountPage.getElementAttribute(
                "//*[contains(@class, 'justify-content-between') and contains(.//*, 'Total')]", "textContent");
        totalPrice = membershipPrice.substring(7, membershipPrice.length() - 1);
        logger.info("Membership total price for the one plan that is being purchased :" + totalPrice);

        logger.info("Check calculations");
        int numPlans = Integer.parseInt(numberOfPlans);
        double subTotal= Double.parseDouble(subTotalPrice);
        double discount = Double.parseDouble(discountPrice);
        double total = Double.parseDouble(totalPrice);

        switch(discountType){
            case "percentage":
                double expectedDiscountAmountForPercentage;
                double totalCalculationForPercentage;
                double percent1 = Double.parseDouble(discountPercentage1);
                double percent2 = Double.parseDouble(discountPercentage2);

                if(numPlans == 2){
                    expectedDiscountAmountForPercentage =  subTotal/percent1;
                    logger.info("Sub total - " + subTotal + " - divided by percentage - " + percent1 + " -");
                    logger.info("Expected discount amount is : " + expectedDiscountAmountForPercentage);
                }else{
                    expectedDiscountAmountForPercentage =  subTotal/percent2;
                    logger.info("Sub total - " + subTotal + " - divided by percentage - " + percent2 + " -");
                    logger.info("Expected discount amount is : " + expectedDiscountAmountForPercentage);
                }

                BigDecimal expectedPercentage = new BigDecimal(expectedDiscountAmountForPercentage).setScale(2, RoundingMode.HALF_DOWN);
                double newNum = expectedPercentage.doubleValue();
                logger.info("Rounded down to 2 decimal value " + newNum);

                assertEquals(newNum, discount);
                totalCalculationForPercentage = subTotal - discount;
                totalCalculationForPercentage = Math.round(totalCalculationForPercentage * 100.0) / 100.0;
                logger.info("Sub total ("+subTotal+") - discount ("+discount+") = total ("+totalCalculationForPercentage+")");
                assertEquals(totalCalculationForPercentage, total);
                break;
            case "amount":
                int amount1 = Integer.parseInt(discountAmount1);
                int amount2 = Integer.parseInt(discountAmount2);
                double totalCalculationForAmount;
                if(numPlans== 2){
                    assertEquals(amount1,discount);
                    totalCalculationForAmount = subTotal - amount1;
                }else{
                    assertEquals(amount2,discount);
                    totalCalculationForAmount = subTotal - amount2;
                }
                assertEquals(totalCalculationForAmount, total);
                break;
            default:
        }

        logger.info("Add plans to cart");
        membershipDiscountPage.click(ProductsPage.AddToCartMembershipButton);
        membershipDiscountPage.findOnPage(ProductsPage.ShoppingCartCheckoutButton);
        membershipDiscountPage.click(ProductsPage.ShoppingCartCheckoutButton);
        membershipDiscountPage.findOnPage(ProductsPage.FirstDeleteBinIcon);
        logger.info("Membership plans are successfully added to the cart");
    }

    @And("{string} plans with {string} discount are purchased with {string} card")
    public void purchasePlansWithDiscounts(String numberOfPlans,String discountType, String card){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Filling in card details");
        membershipDiscountPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Hello,')]", 10);
        membershipDiscountPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.CheckoutPageNameOnCard, 10);
        membershipDiscountPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
        membershipDiscountPage.waitTwoSeconds();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        membershipDiscountPage.waitFiveSeconds();
        membershipDiscountPage.sendKeys(ProductsPage.CheckoutPageCardNumber, card);
        membershipDiscountPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
        membershipDiscountPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
        membershipDiscountPage.waitFiveSeconds();
        driverManager.driver.switchTo().parentFrame();
        membershipDiscountPage.click(ProductsPage.CheckoutPayNowButton);
        membershipDiscountPage.waitTenSeconds();

        String membershipSubTotalPrice = membershipDiscountPage.getElementAttribute(
                "//strong[contains(.,'Subtotal:')]", "textContent");
        subTotalPrice = membershipSubTotalPrice.substring(12, membershipSubTotalPrice.length() - 1);
        logger.info("Membership subtotal price for the plan that are being purchased on confirmation page:" + subTotalPrice);

        String membershipDiscountPrice = membershipDiscountPage.getElementAttribute(
                "//strong[contains(.,'Discount (')]", "textContent");
        discountPrice = membershipDiscountPrice.substring(41, membershipDiscountPrice.length() - 1);
        logger.info("Membership discount price for the plan that are being purchased on confirmation page:" + discountPrice);

        String membershipPrice = membershipDiscountPage.getElementAttribute(
                "//div[@class='text-end h4 py-1 pe-3'][contains(.,'Total:')]", "textContent");
        totalPrice = membershipPrice.substring(9, membershipPrice.length() - 1);
        logger.info("Membership total price for the one plan that is being purchased on confirmation page:" + totalPrice);

        logger.info("Check calculations");
        int numPlans = Integer.parseInt(numberOfPlans);
        double subTotal= Double.parseDouble(subTotalPrice);
        double discount = Double.parseDouble(discountPrice);
        double total = Double.parseDouble(totalPrice);

        switch(discountType) {
            case "percentage":
                double expectedDiscountAmountForPercentage;
                double totalCalculationForPercentage;
                double percent1 = Double.parseDouble(discountPercentage1);
                double percent2 = Double.parseDouble(discountPercentage2);

                if (numPlans == 2) {
                    expectedDiscountAmountForPercentage = subTotal / percent1;
                    logger.info("Expected discount amount is : " + expectedDiscountAmountForPercentage);
                } else {
                    expectedDiscountAmountForPercentage = subTotal / percent2;
                    logger.info("Expected discount amount is : " + expectedDiscountAmountForPercentage);
                }

                BigDecimal expectedPercentage = new BigDecimal(expectedDiscountAmountForPercentage).setScale(2, RoundingMode.DOWN);
                double newNum = expectedPercentage.doubleValue();
                logger.info("Rounded down to 2 decimal value " + newNum);

                assertEquals(newNum, discount);
                totalCalculationForPercentage = subTotal - discount;
                totalCalculationForPercentage = Math.round(totalCalculationForPercentage * 100.0) / 100.0;
                logger.info("Sub total (" + subTotal + ") - discount (" + discount + ") = total (" + totalCalculationForPercentage + ")");
                assertEquals(totalCalculationForPercentage, total);
                break;
            case "amount":
                int amount1 = Integer.parseInt(discountAmount1);
                int amount2 = Integer.parseInt(discountAmount2);
                double totalCalculationForAmount;
                if (numPlans == 2) {
                    assertEquals(amount1, discount);
                    totalCalculationForAmount = subTotal - amount1;
                } else {
                    assertEquals(amount2, discount);
                    totalCalculationForAmount = subTotal - amount2;
                }
                assertEquals(totalCalculationForAmount, total);
                break;
            default:
        }
    }

    @And("{string} adult plans with discount are filled in")
    public void fillInAllPlanDetails(String number){
        int numberOfPlans = Integer.parseInt(number);
        int i = 0;
        int num = 1;

        Lorem lorem = LoremIpsum.getInstance();

        while(numberOfPlans != i){
            logger.info("Filling in plan number " + num);
            membershipDiscountPage.click("(//button[@data-test='plan-details-member.addEditButton'][contains(.,'add_circle_outline add member')])["+num+"]");
            membershipDiscountPage.findOnPage(MembershipPage.MemberDetailsText);
            membershipPage.checkAndFillAdultDOB();
            membershipPage.clickElementBelow(MembershipPage.GenderQuestionOnForm, MembershipPage.MaleGenderCheckboxOption);

            MembershipSteps.FIRST_NAME_PLAN1 = lorem.getFirstNameFemale();
            membershipDiscountPage.clearInputFieldUsingBackSpaceKey(MembershipPage.FirstNameFormField);
            membershipDiscountPage.sendKeys(MembershipPage.FirstNameFormField,lorem.getFirstNameMale());

            membershipPage.checkAndFillCommunications();
            membershipPage.checkAndFillAgreements();
            membershipPage.checkAndFillSpecialQuestions();

            if(membershipDiscountPage.isElementDisplayed(MembershipPage.AddMemberButtonOnPopUpForm)){
                membershipDiscountPage.click(MembershipPage.AddMemberButtonOnPopUpForm);
            }

            if(membershipDiscountPage.isElementDisplayed(MembershipPage.NextButton)){
                membershipDiscountPage.click(MembershipPage.NextButton);
            }
            i++;
            num++;
        }
    }

    @And("user selects {string} plans for discount")
    public void selectNumberOfPlans(String number){
        int numberOfPlans = Integer.parseInt(number);
        int i = 1;


        while(numberOfPlans != i){
            membershipPage.click(MembershipPage.AddCircleIconInSummaryBox);
            i++;
        }
    }

    public void verifyDiscountsPageElements(){
        logger.info("Verify discount page elements");
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsHeading));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsInformationText));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsNameColumnHeader));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsStatusColumnHeader));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsActiveFromColumnHeader));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsActiveToColumnHeader));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountsActionsColumnHeader ));
    }

    public void verifyNewDiscountStep1PageElements(){
        logger.info("Verify step 1 elements");
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.BackButton));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.CreateNewDiscountHeadingStep1));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.Step1CreateDiscountInformationText));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.PlanColumnHeaderStep1));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.StartDateColumnHeaderStep1));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.EndDateColumnHeaderStep1));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.StatusColumnHeaderStep1));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.PriceColumnHeaderStep1));
    }

    public void verifyNewDiscountStep2PageElements(){
        logger.info("Verify step 2 elements");
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.BackButton));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.CreateNewDiscountHeadingStep2));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.Step2CreateDiscountInformationText));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountDetailsHeading));
        assertTrue(membershipDiscountPage.isElementDisplayed(MembershipDiscountPage.DiscountValueHeading));
    }

}
