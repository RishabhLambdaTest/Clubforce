package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class MembershipSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    public static String BUNDLE_NAME_HOLDER;
    public static String EDITED_BUNDLE_NAME_HOLDER;
    public static String FIRST_NAME_PLAN1;
    public static String LAST_NAME_PLAN1;
    public static String PHONE_NUMBER_PLAN1;
    public static String EMAIL_PLAN1;
    public static String STREET_ADDRESS_PLAN1;
    public static String TOWN_PLAN1;
    public static String POSTCODE_PLAN1;
    public static String COUNTY_PLAN1;
    public static String COUNTRY_PLAN1;
    public static String Allergies_PLAN1;
    public static String Medication_PLAN1;
    public static String EmergencyContactName_PLAN1;
    public static String EmergencyContactNumber_PLAN1;
    public static String IDNumber_PLAN1;
    public static String ORDER_DATE_ID_HOLDER;
    public static String ORDER_ID_HOLDER;
    public static String NEW_PLAN_NAME;
    public static String PLAN_PRICE;
    public static String INSTALMENT_MONTHS_HOLDER;
    public static String INSTALMENT_PLAN_PAY_NOW_PRICE;
    public static String INSTALMENT_PLAN_FULL_PRICE;
    public static String UNPUBLISHED_PLAN_NAME;
    public static String PLAN_TO_DUPLICATE;
    public static String PLAN_NAME;
    public static String PLAN_DESCRIPTION;
    public static String MAX_SUBSCRIPTIONS;
    public static String START_DATE;
    public static String END_DATE;
    public static String PLAN_STATUS;
    public static String SUBSCRIPTION_AMOUNT;
    public static Boolean IS_FREE_PLAN;
    public static String MEMBERSHIP_TOTAL_PRICE_HOLDER;
    public static String ORDER_ID;
    public static String REG_DET_PHONE;
    public static String REG_DET_TOWN;
    public static String REG_DET_CONTACT_NAME;
    public static String REG_DET_CONTACT_NUMBER;
    public static String IsBundleHOLDER = "false";
    public static String BuyPlanURLHOLDER;
    public static String CapPriceHOLDER;
    public static String PriceInBundleHOLDER1;
    public static String DropdownQuestionTitleHOLDER1 = "";
    public static String DropdownQuestionFirstNameHOLDER1 = "";
    public static String DropdownQuestionFirstNameHOLDER2 = "";
    public static String FreeTextQuestionTitleHOLDER1 = "";
    public static String FreeTextQuestionAnswerHOLDER1 = "";
    public static String specialQuestionsFlagHOLDER = "";
    public static String SingleSelectQuestionTitleHOLDER = "";
    public static String SingleSelectQuestionOption1HOLDER = "";
    public static String SingleSelectQuestionOption2HOLDER = "";
    public static String SingleSelectURLHOLDER = "";
    public static boolean isInstalmentPlanEdited = false;
    public static boolean isJuvenilePlan = false;
    public static boolean isBundle = false;

    int dayNumber;
    WebDriverManager driverManager;

    public MembershipSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.clubPage = driverManager.clubPage;
        this.accountPage = driverManager.accountPage;
        this.backofficePage = driverManager.backofficePage;
        this.productsPage = driverManager.productsPage;
        this.membershipPage = driverManager.membershipPage;
    }

    @Then("Membership set up is {string} for {string}")
    public void checkIfMembershipDisplayed(String display_option, String club) {
        membershipPage.findOnPage(BackofficePage.DashboardHeading);
        membershipPage.refreshPage();
        membershipPage.findOnPage(BackofficePage.DashboardHeading);
        if (driverManager.agent.contains("appium")) {
            membershipPage.click(BackofficePage.BackofficeHamburger);
        }
        switch (display_option) {
            case "not visible":
                membershipPage.waitTwoSeconds();
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.NoMembershipPlanSetUpText));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.CreateYourMembershipPlanText));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.SetUpMembershipButton));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.MembershipLeftNav));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.MembershipViewPlansDashboardButton));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.MembershipGraphInsightsIcon));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.MembershipPlansGraphListIcon));
                assertFalse(membershipPage.isElementDisplayed(BackofficePage.MembershipDashboardSignUpsHeading));
                logger.info("Membership is NOT visible for " + club);
                break;
            case "visible":
                membershipPage.waitTwoSeconds();
                membershipPage.click(BackofficePage.DashboardHeading);
                membershipPage.findOnPage(BackofficePage.NoMembershipPlanSetUpText);
                membershipPage.findOnPage(BackofficePage.SetUpMembershipButton);
                if (driverManager.agent.contains("appium")) {
                    membershipPage.click(BackofficePage.BackofficeHamburger);
                }
                membershipPage.click(BackofficePage.MembershipLeftNav);
                membershipPage.findOnPage(BackofficePage.LeftNavSetUpMembershipOption);
                logger.info("Membership is visible for " + club);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkIfMembershipDisplayed!");
        }
    }

    @And("pending plan is not visible on club website")
    public void checkPendingPlanOnClubWebsite(){
        getMembershipLinkOnMembershipPlansPage();
        goToMembershipPageOnWebsite();

        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'" + PLAN_PRICE + "')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Plan description for " + NEW_PLAN_NAME + "')]"));
        logger.info("Pending plan is not displayed on club website");
    }

    @And("{string} plan is visible on CW using URL from Published Plans page")
    public void membershipPlansAreVisibleFromURL(String planType){
        getMembershipLinkOnMembershipPlansPage();
        goToMembershipPageOnWebsite();
        signOutOfAccountOnStep1MembershipPlanFlow();

        switch (planType) {
            case "Promo":
                membershipPage.findOnPage(MembershipPage.Step1SelectPlansHeading);
                break;
            case "bundled":
                membershipPage.findOnPage(MembershipPage.Step1SelectPlansHeading);
                membershipPage.findOnPage("//h3[@data-test='bundle-detail.name'][contains(.,'"+BUNDLE_NAME_HOLDER+"')]");
                break;
            case "edited bundle":
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'" + EDITED_BUNDLE_NAME_HOLDER + "')]",8);
                membershipPage.findOnPage(MembershipPage.ValidFrom170323To111123Dates);
                break;
            case "Free":
                membershipPage.findOnPage(MembershipPage.FreePriceText);
                membershipPage.findOnPage("//*[contains(text(),'" + NEW_PLAN_NAME + "')]");
                membershipPage.findOnPage("//*[contains(text(),'Plan description for " + NEW_PLAN_NAME + "')]");
                break;
            case "paid":
                membershipPage.findOnPage("//*[contains(text(),'" + PLAN_PRICE + "')]");
                membershipPage.findOnPage("//*[contains(text(),'" + NEW_PLAN_NAME + "')]");
                membershipPage.findOnPage("//*[contains(text(),'Plan description for " + NEW_PLAN_NAME + "')]");
                break;
            case "instalment":
                membershipPage.findOnPage("//*[contains(text(),'" + NEW_PLAN_NAME + "')]");
                if(!INSTALMENT_PLAN_FULL_PRICE.contains(".")){
                    INSTALMENT_PLAN_FULL_PRICE = INSTALMENT_PLAN_FULL_PRICE + ".00";
                    logger.info("Instalment price on website should be : " + INSTALMENT_PLAN_FULL_PRICE);
                }
                membershipPage.findOnPage("//*[contains(text(),'" + INSTALMENT_PLAN_FULL_PRICE + "')]");
                membershipPage.findOnPage("//*[contains(text(),'Plan description for " + NEW_PLAN_NAME + "')]");
                break;
            case "Edited":
                if (IS_FREE_PLAN) {
                    membershipPage.findOnPage(MembershipPage.FreePriceText);
                } else {
                    String membershipPlanPrice = membershipPage.getElementAttribute("(//strong[@data-test='plan-detail.price'][contains(.,'')])[1]", "textContent");
                    membershipPlanPrice = membershipPlanPrice.substring(2, membershipPlanPrice.length()-1);
                    logger.info("Membership plan price on website " + membershipPlanPrice);
                    if(!PLAN_PRICE.contains(".")){
                        PLAN_PRICE = PLAN_PRICE + ".00";
                        logger.info("Plan price on website should be : " + PLAN_PRICE);
                    }
                    assertEquals(membershipPlanPrice,PLAN_PRICE);
                }
                membershipPage.findOnPage("//*[contains(text(),'" + NEW_PLAN_NAME + "')]");
                membershipPage.findOnPage("//*[contains(text(),'Plan description for " + NEW_PLAN_NAME + "')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for membershipPlansAreVisibleFromURL!");
        }
    }

    @And("ClubAdmin completes step 1 for a new {string} {string} plan with {string} max subscriptions")
    public void setUpStep1ForMembershipPlan(String planType, String planPriceType, String max_subs){
        logger.info("Wait for page to load properly");
        membershipPage.waitThreeSeconds();

        waitForMembershipPageToFinishLoading();

        logger.info("Page loaded, start new plan");
        if(membershipPage.isElementDisplayed(MembershipPage.NewPlanButton)){
            membershipPage.click(MembershipPage.NewPlanButton);
        }

        verifyPageElementsOnStep1OfMembershipPlanSetUp();
        assertFalse(membershipPage.isElementDisplayed("//button[@data-test='step-publish.publishPlanButton'][contains(.,'publish plan')]"));
        setPlanNameAndDescriptionBasedOnPlanPriceType(planPriceType, planType);

        logger.info("Set Max subscriptions");
        membershipPage.sendKeys(MembershipPage.SubscriptionField, max_subs);

        logger.info("Add Standard category");
        membershipPage.click(MembershipPage.FormTemplateField);
        membershipPage.waitTwoSeconds();
        membershipPage.click(MembershipPage.StandardFormTemplate);

        setPlanSubscriptionDates();
        setDateOfBirthDateRangesForMembershipPlan();
        membershipPage.scrollPageToTop();
        setPlanTypeFee(planPriceType,planType);

        if (planType.equals("juvenile")) {
            logger.info("Plan type is " + planType + " tick select this radio button");
            membershipPage.click(MembershipPage.JuvenilePlanRadioButton);
            isJuvenilePlan = true;
        }else{
            logger.info("Plan type is " + planType + " tick select this radio button");
            membershipPage.click(MembershipPage.AdultPlanRadioButton);
            isJuvenilePlan = false;
        }

        logger.info("Check no errors show");
        membershipPage.scrollPageToTop();
        membershipPage.scrollPageToBottom();
        assertFalse(membershipPage.isElementDisplayed(MembershipPage.PleaseEnterErrorText));
        logger.info("No errors show");
    }

    @When("ClubAdmin edits the previous instalment plan")
    public void editAnInstalmentPlan(){
        logger.info("Go into plan");
        membershipPage.clickElementRightOf("//*[contains(text(),'"+NEW_PLAN_NAME+"')]", "//button[contains(.,'Edit Details')]");

        logger.info("Going to step 1");
        membershipPage.findOnPage("//button[contains(.,'disable instalments')]");
        logger.info("We are on step 1 of edit flow");

        logger.info("If day is odd we select 5 months instalments, if day is even we select 10 months instalments");
        String day = SeleniumUtilities.getDateTimeFormat("dd");
        int dayDate = Integer.parseInt(day);
        if (dayDate % 2 == 0) {
            logger.info("Day is: " + dayDate + " which is even, 5 monthly instalments");
            INSTALMENT_MONTHS_HOLDER = "5 monthly instalments";
        } else {
            logger.info("Day is: " + dayDate + " which is odd, 10 months instalments");
            INSTALMENT_MONTHS_HOLDER = "10 monthly instalments";
        }

        //setting new plan price for instalment
        INSTALMENT_PLAN_FULL_PRICE = "1" + RandomStringUtils.randomNumeric(1) + "0";
        membershipPage.clear(MembershipPage.PriceField);
        membershipPage.sendKeys(MembershipPage.PriceField, INSTALMENT_PLAN_FULL_PRICE);
        logger.info("Paid instalment plan price: " + INSTALMENT_PLAN_FULL_PRICE);

        calculateInstalments();

        logger.info("Select " + INSTALMENT_MONTHS_HOLDER + " option from dropdown");
        membershipPage.click("//*[contains(text(),'"+INSTALMENT_MONTHS_HOLDER+"')]");

        INSTALMENT_PLAN_PAY_NOW_PRICE = membershipPage.getElementAttribute("(//span[contains(.,'"+INSTALMENT_MONTHS_HOLDER+" -')])[2]", "innerText");
        logger.info("INSTALMENT_PLAN_PAY_NOW_PRICE: " + INSTALMENT_PLAN_PAY_NOW_PRICE);

        NEW_PLAN_NAME = "Edited " +  INSTALMENT_MONTHS_HOLDER + " plan " + SeleniumUtilities.getDateTimeFormat("ddMMMyyyyHHmmss");
        membershipPage.clear("//input[contains(@formcontrolname,'name')]");
        membershipPage.sendKeys("//input[contains(@formcontrolname,'name')]", NEW_PLAN_NAME);
        membershipPage.clear("//textarea[contains(@type,'text')]");
        membershipPage.sendKeys("//textarea[contains(@type,'text')]", "Plan description for " + NEW_PLAN_NAME);
        isInstalmentPlanEdited = true;

        logger.info("Click Continue to go to next page with questions");
        membershipPage.scrollPageToBottom();
        membershipPage.click(MembershipPage.Step1ContinueButtonOnSetUp);
        membershipPage.waitUntilElementInvisible(MembershipPage.GeneralInformationText, 60);

        membershipPage.waitForElementDisplayedByXpathWithTimeout(MembershipPage.ReviewQuestionsHeading, 60);
        logger.info("Click continue button on step 2 to go publish");
        membershipPage.scrollPageToBottom();
        membershipPage.click(MembershipPage.ContinueButtonOnStep2OfSetup);
        membershipPage.waitUntilElementInvisible(MembershipPage.ContinueButtonOnStep2OfSetup,5);

        logger.info("Click I understand checkbox on step 3");
        membershipPage.click("//span[@class='mat-checkbox-label'][contains(.,'I understand')]");

        logger.info("Click publish plan button on step 3");
        membershipPage.click("//button[contains(.,'publish plan')]");
        membershipPage.waitUntilElementInvisible("//button[contains(.,'publish plan')]", 30);
        membershipPage.waitFiveSeconds();
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Your published plans!')]",100);
    }

    @And("ClubAdmin completes step 2 for a new {string} plan with {string} special questions")
    public void setUpStep2ForMembershipPlan(String planType, String special_questions){
        logger.info("Click Continue to go to next page with questions");
        membershipPage.scrollPageToBottom();
        membershipPage.click(MembershipPage.Step1ContinueButtonOnSetUp);
        membershipPage.waitUntilElementInvisible(MembershipPage.GeneralInformationText, 60);
        membershipPage.waitForElementDisplayedByXpathWithTimeout(MembershipPage.ReviewQuestionsHeading, 60);

        if (planType.equals("juvenile")) {
            logger.info("Plan type is " + planType + ", do verifyGuardianQuestionsAreMandatoryOnForm");
            verifyGuardianQuestionsAreMandatoryOnForm();
        } else {
            verifyGuardianQuestionsAreNotMandatoryOnForm();
        }

        verifyDefaultStandardQuestionsAreDisplayedOnForm();
        verifyOptionalStandardQuestionsAreNotDisplayed();

        logger.info("Go to Add questions library");
        membershipPage.click(MembershipPage.AddQuestionButton);
        membershipPage.findOnPage(MembershipPage.AddQuestionHeading);
        logger.info("We stand in Add questions library");

        logger.info("Check which questions, if any, we will add to the set of question we want to ask members");
        if (special_questions.contains("No")) {
            logger.info("'No' special questions have been selected, do a quick check anyway");
            verifyDefaultAdditionalStandardQuestionsAreDisplayedOnAddQuestionsForm();

            membershipPage.sendKeys(MembershipPage.QuestionSearchField, "Emergency");
            membershipPage.findOnPage(MembershipPage.EmergencyContactNameFormQuestion);
            membershipPage.findOnPage(MembershipPage.EmergencyContactNumberFormQuestion);
            assertFalse(membershipPage.isElementDisplayed(MembershipPage.FirstNameFormQuestion));
            membershipPage.clear(MembershipPage.QuestionSearchField);

            logger.info("Add playing kit size question");
            membershipPage.sendKeys(MembershipPage.QuestionSearchField, "Playing");
            membershipPage.findOnPage(MembershipPage.PlayingKitSizeFormQuestion);
            assertFalse(membershipPage.isElementDisplayed(MembershipPage.FirstNameFormQuestion));
            membershipPage.click(MembershipPage.IncludeButton);
            membershipPage.click(MembershipPage.SaveChangesButton);
            membershipPage.waitForElementDisplayedByXpathWithTimeout(MembershipPage.PlayingKitSizeTextOnForm, 30);
            membershipPage.scrollPageDown();

            logger.info("Delete playing kit size question");
            membershipPage.scrollPageDown();
            membershipPage.centreElement(MembershipPage.PlayingKitSizeTextOnForm);
            membershipPage.waitTwoSeconds();
            membershipPage.clickElementRightOf(MembershipPage.PlayingKitSizeTextOnForm, MembershipPage.DeleteIcon);
            membershipPage.waitTwoSeconds();
            membershipPage.findOnPage("//h2[contains(.,'Delete question')] | //h2[contains(.,'Delete Question')]");
            membershipPage.findOnPage(MembershipPage.PlayingKitSizeQuestionInDeletePopUp);
            membershipPage.click(MembershipPage.YesDeleteButton);
            membershipPage.waitUntilElementInvisible(MembershipPage.PlayingKitSizeTextOnForm, 10);
        }

        if (!special_questions.contains("No")) {
            specialQuestionsFlagHOLDER = "false";
            switch (special_questions) {
                case "freetext":
                    customQuestionFreeText();
                    specialQuestionsFlagHOLDER = "true freetext";
                    saveAndGoBackToReviewQuestionsPage();
                    membershipPage.centreElement("//strong[contains(.,'" + FreeTextQuestionTitleHOLDER1 + "')]");
                    break;
                case "dropdown":
                    customQuestionDropDown();
                    specialQuestionsFlagHOLDER = "true dropdown";
                    saveAndGoBackToReviewQuestionsPage();
                    membershipPage.centreElement("//strong[contains(.,'" + DropdownQuestionTitleHOLDER1 + "')]");
                    break;
                case "singleSelect":
                    customQuestionSingleSelect();
                    specialQuestionsFlagHOLDER = "true singleSelect";
                    saveAndGoBackToReviewQuestionsPage();
                    break;
            }
            membershipPage.waitOneSecond();
        }

//        logger.info("Make all questions required");
//        mP.scrollPageToBottom();
//        int i = 1;
//        while (mP.isElementDisplayed("(//span[@class='text-muted'][contains(.,'Optional question |')])["+i+"]")) {
//            mP.clickElementRightOf("(//span[@class='text-muted'][contains(.,'Optional question |')])["+i+"]", "//strong[contains(.,'Change')]");
//            logger.info("Made option question " + i + " a required question");
//            mP.waitTwoSeconds();
//        }
//        assertFalse(membershipPage.isElementDisplayed("(//span[@class='text-muted'][contains(.,'Optional question |')])["+i+"]"));
//        TODO make this work - Annie

        logger.info("Click continue button on step 2 to go publish");
        membershipPage.scrollPageToBottom();
        membershipPage.click(MembershipPage.ContinueButtonOnStep2OfSetup);
        membershipPage.waitUntilElementInvisible(MembershipPage.ContinueButtonOnStep2OfSetup,5);
    }

    @And("ClubAdmin unPublishes a plan")
    public void unPublishMembershipPlan() {
        logger.info("Assert there is at least one published plan");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("(//span[@data-test='membership-plans.status'][contains(.,'Published')])[1]",60);
        logger.info("There is at least one published plan");
        logger.info("Unpublish the plan: "+NEW_PLAN_NAME);
        membershipPage.centreElement("//*[contains(text(),'"+NEW_PLAN_NAME+"')]");
        membershipPage.waitTwoSeconds();
        membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'"+NEW_PLAN_NAME+"')]","//mat-icon[contains(.,'keyboard_arrow_down')]");
        membershipPage.waitTwoSeconds();
        membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'"+NEW_PLAN_NAME+"')]","//li[@data-test='membership-plans.unpublishMenuItem'][contains(.,'Unpublish')]");
        membershipPage.waitTwoSeconds();
        membershipPage.click("//span[contains(@id,'dialog--confirm')]");
        membershipPage.waitUntilElementInvisible("//span[contains(@id,'dialog--confirm')]", 5);
        logger.info("Assert it says Unpublished next to plan now");
        logger.info("Wait for page refresh");
        membershipPage.waitFiveSeconds();
    }

    @And("ClubAdmin unPublishes a bundle")
    public void unPublishMembershipBundle() {
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Your published plans!')]",15);
        membershipPage.centreElement("//div[@class='cf-ui-list-item-content col'][contains(.,'"+BUNDLE_NAME_HOLDER+"')]");
        membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'"+BUNDLE_NAME_HOLDER+"')]", "//button[contains(.,'Unpublish')]");
        membershipPage.waitFiveSeconds();
    }

    @And("ClubAdmin unPublishes all bundles")
    public void unPublishAllMembershipBundles() {
        membershipPage.scrollPageToBottom();
        while (membershipPage.isElementDisplayed("//div[@class='cf-ui-list-item-content row align-items-center'][contains(.,'Published  Edit Details  Unpublish')]")){
            membershipPage.clickElementRightOf("//span[@data-test='membership-plans.status'][contains(.,'Published')]","//button[contains(.,'Unpublish')]");
            membershipPage.waitFiveSeconds();
        }
    }

    @And("ClubAdmin edits and quickPublish plan on {string}")
    public void clubAdminQuickPublishPlan(String step) {
        logger.info("Go into plan");
        membershipPage.clickElementRightOf("//*[contains(text(),'"+NEW_PLAN_NAME+"')]", "//button[contains(.,'Edit Details')]");
        membershipPage.clearInputFieldUsingBackSpaceKey("//input[contains(@data-test,'stepEditForms.name')]");
        NEW_PLAN_NAME = "Quick publish adult paid plan " + SeleniumUtilities.getDateTimeFormat("ddMMMyyyyHHmmss");
        membershipPage.sendKeys("//input[contains(@data-test,'stepEditForms.name')]",NEW_PLAN_NAME);
        membershipPage.clearInputFieldUsingBackSpaceKey(MembershipPage.PlanDescriptionField);
        membershipPage.sendKeys(MembershipPage.PlanDescriptionField, "Plan description for " + NEW_PLAN_NAME);
        if (step.equals("step 2")){
            logger.info("Click Continue to go to next page with questions");
            membershipPage.scrollPageToBottom();
            membershipPage.click(MembershipPage.Step1ContinueButtonOnSetUp);
            membershipPage.waitUntilElementInvisible(MembershipPage.GeneralInformationText, 60);
            membershipPage.waitForElementDisplayedByXpathWithTimeout(MembershipPage.ReviewQuestionsHeading, 60);
            membershipPage.click("//button[contains(.,'add_circle_outlineAdd Question')]");
            customQuestionFreeText();
            saveAndGoBackToReviewQuestionsPage();
            membershipPage.centreElement("//strong[contains(.,'" + FreeTextQuestionTitleHOLDER1 + "')]");
        }
        membershipPage.click("//button[@data-test='step-publish.publishPlanButton'][contains(.,'publish plan')]");
        membershipPage.findOnPage("//h2[@data-test='confirm-dialog.page'][contains(.,'You are publishing a plan')]");
        membershipPage.click("//span[contains(.,'PUBLISH')]");
        membershipPage.findOnPage("//span[contains(.,'Plan successfully edited')]");
        membershipPage.findOnPage("//h1[contains(.,'Membership plans')]");
    }

    @And("ClubAdmin edits a plan")
    public void editPlanInBackoffice() {
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Membership plans')]",60);

        logger.info("Check how many bundles are already created");

        int bundleNumber = 1;
        if(membershipPage.isElementDisplayed("(//button[contains(.,'Unpublish')])[1]")){
            while(membershipPage.isElementDisplayed("(//button[contains(.,'Unpublish')])["+bundleNumber+"]")){
                logger.info("Bundle number : "+ bundleNumber);
                bundleNumber++;
                logger.info("Bundle number after increment: "+ bundleNumber);
            }
        }else{
            bundleNumber = 0;
        }

        int planNumber = bundleNumber+1;
        membershipPage.waitTwoSeconds();
        logger.info("Check status of first plan in the list");
        PLAN_STATUS = membershipPage.getElementAttribute("(//span[@data-test='membership-plans.status'])["+planNumber+"]", "textContent");
        logger.info("Plan status : " + PLAN_STATUS);

        PLAN_NAME = membershipPage.getElementAttribute("(//div[@class='cf-ui-list-item-content col'])["+planNumber+"]", "innerText");
        logger.info("There is at least 1 published plan, named "+PLAN_NAME);
        membershipPage.click("(//button[contains(.,'Edit Details')])["+planNumber+"]");;

        logger.info("Click on the edit details button for the first plan");

        membershipPage.waitForElementDisplayedByXpathWithTimeout("//input[contains(@formcontrolname,'name')]",60);

        logger.info("Change plan name");
        NEW_PLAN_NAME = "Edited Plan " + RandomStringUtils.randomNumeric(5);
        membershipPage.clearInputFieldUsingBackSpaceKey("//input[contains(@formcontrolname,'name')]");
        membershipPage.sendKeys("//input[contains(@formcontrolname,'name')]", NEW_PLAN_NAME);

        logger.info("Change plan description");
        membershipPage.clearInputFieldUsingBackSpaceKey("//textarea[contains(@type,'text')]");
        membershipPage.sendKeys("//textarea[contains(@type,'text')]", "Plan description for " + NEW_PLAN_NAME);

        if (membershipPage.isElementDisplayed("(//span[contains(.,'Free plan')])[2]")) {
            IS_FREE_PLAN = true;
        } else {
            IS_FREE_PLAN = false;
            PLAN_PRICE = membershipPage.getElementAttribute("//input[contains(@data-test,'stepEditForms.price')]", "value");
            PLAN_PRICE = "2" + RandomStringUtils.randomNumeric(2) + "." + RandomStringUtils.randomNumeric(2);
            membershipPage.clearInputFieldUsingBackSpaceKey("//input[contains(@data-test,'stepEditForms.price')]");
            membershipPage.sendKeys("//input[contains(@data-test,'stepEditForms.price')]", PLAN_PRICE);
            logger.info("Plan price is: " + PLAN_PRICE);
        }

        logger.info("Change max subscriptions");
        SUBSCRIPTION_AMOUNT = "1" + RandomStringUtils.randomNumeric(2);
        membershipPage.clearInputFieldUsingBackSpaceKey("//input[contains(@formcontrolname,'max')]");
        membershipPage.sendKeys("//input[contains(@formcontrolname,'max')]", SUBSCRIPTION_AMOUNT);

        logger.info("Click continue button on step 1");
        membershipPage.click("//button[contains(.,'continue')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//strong[contains(.,'Add Question')]",60);

        logger.info("Click continue button on step 2");
        membershipPage.waitTwoSeconds();
        membershipPage.click("//button[@data-test='stepEditForms.step2-continueButton']");
        membershipPage.waitFiveSeconds();

        logger.info("Click back button on step 3");
        membershipPage.click("//button[@data-test='step-publish.backButton'][contains(.,'back')]");

        logger.info("Checking all optional added questions are still set to non required");
        int j = 1;
        while (membershipPage.isElementDisplayed("(//span[@class='me-2'][contains(.,'Required')])[" + j + "]")) {
            assertTrue(membershipPage.isElementDisplayed("(//div[contains(@class,'mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin')])[" + j + "]//input[contains(@aria-checked,'false')]"));
            j++;
        }

        logger.info("Deleting all optional added questions");
        while (membershipPage.isElementDisplayed("(//mat-icon[contains(.,'delete')])[1]")) {
            membershipPage.click("(//mat-icon[contains(.,'delete')])[1]");
            membershipPage.click("//span[contains(.,'Yes, Delete')]");
            membershipPage.waitThreeSeconds();
        }


        logger.info("Click continue button on step 2");
        membershipPage.click("//button[@data-test='stepEditForms.step2-continueButton']");
        membershipPage.waitUntilElementInvisible("//button[@data-test='stepEditForms.step2-continueButton']",22);
    }

    @And("ClubAdmin discards changes using cancel button")
    public void discardChangesUsingCancelButton() {
        membershipPage.click("//button[contains(.,'Cancel')]");
        membershipPage.findOnPage("//h2[contains(.,'Exit without saving?')]");
        membershipPage.findOnPage("//mat-dialog-content[contains(.,'Changes that you made may not be saved.')]");
        membershipPage.click("//span[contains(.,'Cancel')]");
        membershipPage.waitOneSecond();
        membershipPage.click("//button[contains(.,'Cancel')]");
        membershipPage.findOnPage("//h2[contains(.,'Exit without saving?')]");
        membershipPage.findOnPage("//mat-dialog-content[contains(.,'Changes that you made may not be saved.')]");
        membershipPage.click("//span[contains(.,'Discard changes')]");
        membershipPage.findOnPage("//h1[contains(.,'Membership plans')]");
        membershipPage.findOnPage("//button[contains(.,'new plan')]");
    }

    @And("ClubAdmin discards changes by leaving section")
    public void exitTheMembershipFlow() {
        membershipPage.waitForSkeletonLoader();
        if(!membershipPage.isElementDisplayed(BackofficePage.BackofficeLeftNavMembershipSelection)){
            membershipPage.click(BackofficePage.BackofficeLeftNavMembership);
        }
        backofficePage.click(BackofficePage.BackofficeLeftNavMembershipSelection);
        membershipPage.findOnPage("//h2[contains(.,'Exit without saving?')]");
        membershipPage.findOnPage("//mat-dialog-content[contains(.,'Changes that you made may not be saved.')]");
        membershipPage.click("//span[contains(.,'Discard changes')]");
        membershipPage.findOnPage("//h1[contains(.,'Membership plans')]");
    }

    @Then("all of the edited changes are discarded")
    public void verifyChangesAreDiscarded() {
        assertFalse(membershipPage.isElementDisplayed("//div[@class='cf-ui-list-item-content col'][contains(.,'"+NEW_PLAN_NAME+"')]"),"Edited plan name is displayed and was not discarded");
        membershipPage.findOnPage("//span[@data-test='membership-plans.status'][contains(.,'"+PLAN_STATUS+"')]");
        membershipPage.click("//button[contains(.,'Edit Details')]");
        membershipPage.waitFiveSeconds();
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'"+NEW_PLAN_NAME+"')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Plan description for " + NEW_PLAN_NAME+"')]"));
    }

    @And("ClubAdmin duplicates a plan")
    public void createDuplicatePlan() {
        logger.info("Click edit details button");
        PLAN_TO_DUPLICATE = membershipPage.getElementAttribute("(//div[@class='cf-ui-list-item-content col'][contains(.,'plan')])[1]", "textContent").trim();
        logger.info("PLAN_TO_DUPLICATE "+PLAN_TO_DUPLICATE);
        membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'"+PLAN_TO_DUPLICATE+"')]","//button[contains(.,'Edit Details')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'General information')]", 60);
        PLAN_NAME = membershipPage.getElementAttribute("//input[contains(@formcontrolname,'name')]", "value");
        logger.info("PLAN_NAME "+PLAN_NAME);
        PLAN_DESCRIPTION = membershipPage.getElementAttribute("//textarea[contains(@formcontrolname,'description')]", "value");
        logger.info("PLAN_DESCRIPTION "+PLAN_DESCRIPTION);
        MAX_SUBSCRIPTIONS = membershipPage.getElementAttribute("//input[contains(@formcontrolname,'max')]", "value");
        logger.info("MAX_SUBSCRIPTIONS "+MAX_SUBSCRIPTIONS);
        START_DATE = membershipPage.getElementAttribute("(//input[contains(@placeholder,'dd/mm/yyyy')])[1]", "value");
        logger.info("START_DATE "+START_DATE);
        END_DATE = membershipPage.getElementAttribute("(//input[contains(@placeholder,'dd/mm/yyyy')])[2]", "value");
        logger.info("END_DATE "+END_DATE);
        if(!membershipPage.isElementDisplayed(BackofficePage.BackofficeLeftNavMembershipSelection)){
            membershipPage.click(BackofficePage.BackofficeLeftNavMembership);
        }
        membershipPage.click(BackofficePage.BackofficeLeftNavMembershipSelection);
        membershipPage.waitForSkeletonLoader();
        membershipPage.waitFiveSeconds();
        logger.info("Click duplicate");
        membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'" + PLAN_TO_DUPLICATE + "')]","//button[contains(.,'Duplicate')]");
        membershipPage.waitFiveSeconds();
        membershipPage.findOnPage("//div[@class='cf-ui-list-item-content col'][contains(.,'" + PLAN_TO_DUPLICATE + "_Duplicate')]");
    }

    @And("duplicate plan contains contain the correct information")
    public void checkDuplicatePlanContainsInformation() {
        membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'" + PLAN_TO_DUPLICATE + "_Duplicate')]", "//button[contains(.,'Edit Details')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'General information')]", 60);
        membershipPage.findOnPage("//*[contains(text(),'"+PLAN_TO_DUPLICATE+"_Duplicate')]");

        assertThat("PLAN_TO_DUPLICATE not as expected!", accountPage.getElementAttribute("//input[contains(@data-test,'stepEditForms.name')]",
                "value"), containsString(PLAN_TO_DUPLICATE));
        assertThat("PLAN_DESCRIPTION not as expected!", accountPage.getElementAttribute("//textarea[contains(@data-test,'stepEditForms.description')]",
                "value"), containsString(PLAN_DESCRIPTION));
        assertThat("MAX_SUBSCRIPTIONS not as expected!", accountPage.getElementAttribute("//input[contains(@data-test,'stepEditForms.max-subscription')]",
                "value"), containsString(MAX_SUBSCRIPTIONS));
        assertThat("START_DATE not as expected!", accountPage.getElementAttribute("//input[contains(@data-test,'plan-subscription.subscription-start')]",
                "value"), containsString(START_DATE));
        assertThat("END_DATE not as expected!", accountPage.getElementAttribute("//input[contains(@data-test,'plan-subscription.subscription-end')]",
                "value"), containsString(END_DATE));
    }

    @And("ClubAdmin deletes any duplicated plans")
    public void clubAdminDeletesDuplicatedMembershipPlan() {
        membershipPage.waitTwoSeconds();
        if (!membershipPage.isElementDisplayed("//h2[contains(.,'Your published plans!')]")){
            logger.info("Trying to get to Membership plans page");
            membershipPage.click("//li[@data-test='button.Membership plans']");
            membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Your published plans!')]", 60);
            membershipPage.waitTwoSeconds();
        }

        logger.info("Deleting duplicated plans");
        while (membershipPage.isElementDisplayed("//div[@class='cf-ui-list-item-content col'][contains(.,'_Duplicate')]")){
            membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'_Duplicate')]","//mat-icon[contains(.,'keyboard_arrow_down')]");
            membershipPage.clickElementRightOf("//div[@class='cf-ui-list-item-content col'][contains(.,'_Duplicate')]","//li[@data-test='membership-plans.deleteMenuItem'][contains(.,'Delete')]");
            membershipPage.click("//span[contains(@id,'dialog--confirm')]");
            membershipPage.waitOneSecond();
            membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Your published plans!')]", 60);
            membershipPage.waitTwoSeconds();
        }
    }

    @And("the plan will no longer be displayed on a website")
    public void checkPlanIsRemoved() {
        membershipPage.click("//span[@class='ms-2'][contains(.,'Dashboard')]");
        membershipPage.waitFiveSeconds();
        membershipPage.click("//h1[@data-test='dashboard.title'][contains(.,'Dashboard')]");
        membershipPage.scrollPageToBottom();
        membershipPage.click("//button[contains(.,'View website')]");
        membershipPage.switchToBrowserTab(1);
        membershipPage.waitFiveSeconds();
        assertTrue(membershipPage.isElementPresent("(//span[contains(.,'Membership')])[1]"));
        assertFalse(membershipPage.isElementPresent("(//span[contains(.,'Membership')])[2]"));
        membershipPage.click("//span[contains(.,'Membership')]");
        membershipPage.waitFiveSeconds();
        logger.info("Unpublished plan name: " + NEW_PLAN_NAME);
        membershipPage.scrollPageDown();
        membershipPage.scrollPageDown();
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'"+NEW_PLAN_NAME+"')]"));
    }

    @And("they publish {string}")
    public void publishNewPlan(String planType) {
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Publish Plan')]", 60);
        membershipPage.findOnPage("//*[contains(text(),'Select when you would like to publish your membership plan.')]");

        if (planType.equals("Edited Plan")) {
            String planStatus = PLAN_STATUS.replaceAll(" " ,"");
            if (planStatus.equals("Published")) {
                logger.info("Check that ClubAdmin cannot publish a published plan in the future");
                assertTrue(membershipPage.isElementDisplayed("//div[@data-test='cf-ui-radio-card'][contains(.,'Publish plan now Plan will be published when you click the Publish button below')]"));
            } else {
                assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'Pick a date/time in the future to publish your plan')]"));
            }
        }

        logger.info("Click I understand checkbox on step 3");
        membershipPage.click("//span[@class='mat-checkbox-label'][contains(.,'I understand')]");

        logger.info("Click publish plan button on step 3");
        membershipPage.click("//button[contains(.,'publish plan')]");
        membershipPage.waitUntilElementInvisible("//button[contains(.,'publish plan')]", 30);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Membership plans')]",100);
    }

    @Then("they publish plan with future date")
    public void publishPlanWithFutureDate(){
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Publish Plan')]", 60);
        membershipPage.findOnPage("//*[contains(text(),'Select when you would like to publish your membership plan.')]");

        logger.info("Selecting future date box");
        membershipPage.click("//div[@data-test='cf-ui-radio-card'][contains(.,'Publish plan at specific date and time  Pick a date/time in the future to publish your plan')]");

        logger.info("Select publication date");
        int i = 1;
        while(!membershipPage.isElementDisplayed("(//mat-icon[@role='img'][normalize-space()='keyboard_arrow_down'])["+i+"]")){
            i++;
        }
        membershipPage.click("(//mat-icon[@role='img'][normalize-space()='keyboard_arrow_down'])["+i+"]");
        membershipPage.click("//button[@aria-label='Next month']");
        membershipPage.click("(//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'1')])[1]");

        logger.info("Enter in publication time");
        membershipPage.clearInputFieldUsingBackSpaceKey("//input[contains(@formcontrolname,'time')]");
        membershipPage.sendKeys("//input[contains(@formcontrolname,'time')]","1000");

        logger.info("Click I understand checkbox on step 3");
        membershipPage.click("//span[@class='mat-checkbox-label'][contains(.,'I understand')]");

        logger.info("Click publish plan button on step 3");
        membershipPage.click("//button[contains(.,'publish plan')]");
        membershipPage.waitUntilElementInvisible("//button[contains(.,'publish plan')]", 30);
        membershipPage.waitFiveSeconds();
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Your published plans!')]",100);

        logger.info("Check that admin has been redirected to membership plans page");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Membership plans')]", 15);
    }

    @And("they select membership plan {string}")
    public void userSelectMembershipPlan(String plan_selection) {
        logger.info("Selecting " + plan_selection);
        productsPage.clickElementBelow("//h3[@data-test='plan-detail.name'][contains(.,'"+plan_selection+"')]", "//button[contains(.,'select plan')]");
    }

    @And("they select the first bundle")
    public void userSelectMembershipBundle() {
        IsBundleHOLDER = "true";

        logger.info("Error handling check");
        productsPage.scrollPageToBottom();
        productsPage.click("//button[contains(.,'select bundle')]");
        productsPage.findOnPage("//span[contains(.,'Select plans in a bundle')]");

        logger.info("Selecting the first bundle");
        productsPage.clickElementRightOf("//span[contains(.,'(optional)')]", "//mat-icon[@role='img'][contains(.,'add_circle')]");
        productsPage.click("//button[contains(.,'select bundle')]");
        logger.info("Bundle selected");
    }

    @And("they select membership bundle {string}")
    public void userSelectMembershipBundleName(String bundle_selection) {
        IsBundleHOLDER = "true";

        logger.info("Error handling check");
        productsPage.clickElementBelow("//h3[@data-test='bundle-detail.name'][contains(.,'"+bundle_selection+"')]", "//button[contains(.,'select bundle')]");
        productsPage.findOnPage("//span[contains(.,'Select plans in a bundle')]");

        logger.info("Selecting " + bundle_selection);
        productsPage.findOnPage("//h3[@data-test='bundle-detail.name'][contains(.,'"+bundle_selection+"')]");
        productsPage.clickElementRightOf("//span[contains(.,'(optional)')]", "//mat-icon[@role='img'][contains(.,'add_circle')]");
        productsPage.clickElementBelow("//h3[@data-test='bundle-detail.name'][contains(.,'"+bundle_selection+"')]", "//button[contains(.,'select bundle')]");
        logger.info("Bundle selected");
    }

    @And("Find Out More link works")
    public void userClickFindOutMoreLink() {
        checkMembershipFindOutMoreLink();
    }

    @And("user continue to membership forms")
    public void userContinueToMembershipForm() {
        membershipPage.scrollPageToTop();
        membershipPage.click("//button[contains(.,'continue')]");
        membershipPage.findOnPage("//strong[contains(.,'Step 2: Fill in membership forms')]");
    }

    @And("xgb enabled membership plan can be selected")
    public void xgbMembershipPlanIsSelected() {
        membershipPage.acceptCookies();
        logger.info("Selecting plan");
        membershipPage.click("//button[contains(.,'select plan')]");
    }

    @And("user click Continue on purchase plans page")
    public void continueToForms() {
        membershipPage.click("//button[@data-test='product-summary.continue'][contains(.,'continue')]");
        membershipPage.waitThreeSeconds();
    }

    @And("one plan is selected three times")
    public void buy3membershipPlans() {
        membershipPage.acceptCookies();
        logger.info("Adding 3 plans to cart");
        membershipPage.click("(//mat-icon[contains(.,'expand_more')])[1]");
        membershipPage.click("(//mat-icon[contains(.,'add_circle_outline')])[1]");
        membershipPage.click("(//mat-icon[contains(.,'add_circle_outline')])[1]");
        membershipPage.click("(//mat-icon[contains(.,'add_circle_outline')])[1]");
    }

    @And("{string} are filled in for {string} plan for {string} buyer")
    public void fillInPlanUserDetails(String adultOrJuvenileDetails, String adultOrJuvenilePlanType, String buyer) {
        Lorem lorem = LoremIpsum.getInstance();
        membershipPage.findOnPage("//h2[contains(.,'Summary')]");

        switch (adultOrJuvenileDetails) {
            case "adult member details":
                logger.info("Filling in "+adultOrJuvenileDetails);
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'add member')]",10);
                membershipPage.click("//*[contains(text(),'add member')]");
                membershipPage.findOnPage("//div[@class='fw-bold'][contains(.,'Member Details')]");
                FIRST_NAME_PLAN1 = membershipPage.getElementAttribute("//input[contains(@data-test,'dynamic-form-text-field.firstName')]", "value");
                logger.info("Name of member : " + FIRST_NAME_PLAN1);
                membershipPage.checkAndFillAdultDOB();
                membershipPage.clickElementBelow("//span[@class='pb-2'][contains(.,'Gender')]", "//span[@class='mat-radio-label-content'][contains(.,'Male')]");
                membershipPage.checkAndFillCommunications();
                membershipPage.checkAndFillAgreements();
                membershipPage.checkAndFillSpecialQuestions();
                membershipPage.click("//button[contains(.,'Add Member')]");
                break;
            case "guardian details":
                logger.info("Filling in "+adultOrJuvenileDetails);
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//button[contains(.,'add parent/guardian')]",10);
                membershipPage.click("//button[contains(.,'add parent/guardian')]");
                logger.info("For existing buyer and new user we only need to set gender");
                membershipPage.click("//label[@class='mat-radio-label'][contains(.,'Male')]");
                membershipPage.checkAndFillAdultDOB();
                membershipPage.checkAndFillCommunications();
                membershipPage.checkAndFillAgreements();
                membershipPage.checkAndFillSpecialQuestions();
                membershipPage.click("//button[contains(.,'Add Parent/Guardian')]");
                break;
            case "juvenile member details":
                logger.info("Filling in "+adultOrJuvenileDetails);
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'add member')]",10);
                membershipPage.click("//*[contains(text(),'add member')]");
                membershipPage.findOnPage("//mat-label[contains(.,'First name')]");

                if (adultOrJuvenilePlanType.contains("juvenile")) {
                    logger.info("Header is modal is for a "+adultOrJuvenilePlanType);
                    assertTrue(membershipPage.isElementDisplayed("//h5[contains(.,'person Juvenile Member')]"));
                    logger.info("Since this form is for a juvenile we need to fill in all details");

                    FIRST_NAME_PLAN1 = lorem.getFirstNameFemale();
                    membershipPage.sendKeys("//input[contains(@data-test,'dynamic-form-text-field.firstName')]", FIRST_NAME_PLAN1);
                    logger.info("First name: " + FIRST_NAME_PLAN1);

                    LAST_NAME_PLAN1 = lorem.getLastName();
                    membershipPage.sendKeys("//input[contains(@data-test,'dynamic-form-text-field.lastName')]", LAST_NAME_PLAN1);
                    logger.info("First name: " + LAST_NAME_PLAN1);

                    logger.info("Setting date of birth to 22nd of June 2010.");
                    membershipPage.click("//button[@aria-label='Open calendar']");
                    membershipPage.click("//span[@id='mat-calendar-button-0']");
                    membershipPage.click("//span[@id='mat-calendar-button-0']");
                    membershipPage.click("//button[@aria-label='Previous 24 years']");
                    membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2010')]");
                    membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'JUN')]");
                    membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'22')]");

                    logger.info("Setting gender to female");
                    membershipPage.clickElementBelow("//span[contains(.,'Gender')]", "//span[@class='mat-radio-label-content'][contains(.,'Female')]");
                } else {
                    logger.info("Header is modal is for a "+adultOrJuvenilePlanType);
                    assertTrue(membershipPage.isElementDisplayed("//h5[contains(.,'person Adult Member')]"));
                }
                membershipPage.click("//button[contains(.,'Add Member')]");
                break;
        }
    }

    @And("{string} plan details filled in for {string} buyer")
    public void membershipPlanDetailsAreFilledIn(String plan_type, String buyer) {
        membershipPage.findOnPage(MembershipPage.MembershipAddToCartButton);
        membershipPage.findOnPage("//*[contains(text(),'add member')]");

        switch (plan_type) {
            case "single adult Standard":
                membershipPage.click("//*[contains(text(),'add member')]");
                membershipPage.findOnPage("//mat-label[contains(.,'First name')]");

                // check error handling
                membershipPage.click("//span[contains(.,'Gender')]");
                membershipPage.click("//button[contains(.,'Add Member')]");
                membershipPage.scrollPageToTop();
                membershipPage.findOnPage("//mat-error[contains(.,'Gender is mandatory')]");
                membershipPage.findOnPage("//mat-error[contains(.,'I consent for member information to be retained by the Club and submitted to the relevant Governing Body for administrative and statistical purposes for such period as the membership subsists is mandatory')]");
                membershipPage.click("//span[@class='mat-radio-label-content'][contains(.,'Male')]");
                membershipPage.click("//button[contains(.,'Add Member')]");
                break;
            case "signup user":
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//button[contains(.,'add member')]",10);
                membershipPage.click("//button[contains(.,'add member')]");
                logger.info("Setting date of birth to 17 March 2001.");
                membershipPage.click("//button[@aria-label='Open calendar']");
                membershipPage.click("//div[@class='mat-calendar-arrow mat-calendar-invert']");
                membershipPage.click("//div[contains(@class,'mat-calendar-arrow')]");
                membershipPage.click("//button[@aria-label='Previous 24 years']");
                membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2001')]");
                membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'MAR')]");
                membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'17')]");

                logger.info("Clicking radio buttons");
                membershipPage.click("//label[@class='mat-radio-label'][contains(.,'Male')]");
                membershipPage.click("(//label[@class='mat-radio-label'][contains(.,'Yes')])[1]");
                membershipPage.click("(//label[@class='mat-radio-label'][contains(.,'Yes')])[2]");
                membershipPage.click("(//label[@class='mat-radio-label'][contains(.,'Yes')])[3]");
                membershipPage.click("(//label[@class='mat-radio-label'][contains(.,'Yes')])[4]");
                membershipPage.click("//label[@class='mat-checkbox-layout'][contains(.,'I consent')]");
                membershipPage.click("(//span[@class='mat-checkbox-label'][contains(.,'I confirm')])[1]");

                membershipPage.click("//button[contains(.,'Add Member')]");
                break;
            default:
        }
        membershipPage.scrollPageToTop();
    }

    @And("xgb enabled membership plan details filled in")
    public void XGBMembershipPlanDetailsAreFilledIn() {
        membershipPage.waitOneSecond();
        membershipPage.acceptCookies();

        logger.info("Check validation for 30 fields");
        membershipPage.click("//button[contains(.,'Confirm details')]");
        membershipPage.findOnPage("//mat-error[contains(.,'First name is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Last name is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Phone number is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Email is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'DOB is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Gender is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'County is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Country is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Town is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Street address is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Postcode is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Please select the option that applies to you is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Allergies is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Please list any medical conditions / injuries / allergies / special needs the club need to be aware of is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Disability/Special needs is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Emergency contact name is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Emergency contact number is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'What is your club role? is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'ID number is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Level of experience is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Academic Year is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Are you a player? is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Would you be interested in Volunteering for the club? is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Playing kit size is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'I consent to be contacted by my club is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'I consent to allow photo/videos to be taken of me/my child for official club use and/or promotional purposes? is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'I consent for member information to be retained by the Club and submitted to the relevant Governing Body for administrative and statistical purposes for such period as the membership subsists. is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'I agree to inform the club of any medical conditions I have that are relevant to my participation is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Please confirm you have read & agree to our Terms & Conditions is mandatory')]");
        membershipPage.findOnPage("//mat-error[contains(.,'Please confirm you have read & agree to our Code of Conduct is mandatory')]");

        logger.info("Filling in plan 1");



        // magnus


        Lorem lorem = LoremIpsum.getInstance();
        FIRST_NAME_PLAN1 = lorem.getFirstName();
        membershipPage.waitThreeSeconds();
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'First name')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", FIRST_NAME_PLAN1);
        logger.info("First name: " + FIRST_NAME_PLAN1);

        LAST_NAME_PLAN1 = lorem.getLastName();
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Last name')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", LAST_NAME_PLAN1);
        logger.info("Last name: " + LAST_NAME_PLAN1);

        PHONE_NUMBER_PLAN1 = RandomStringUtils.randomNumeric(24);
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Phone number')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", PHONE_NUMBER_PLAN1);
        logger.info("Phone number: " + PHONE_NUMBER_PLAN1);

        EMAIL_PLAN1 = lorem.getEmail();
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Email')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", EMAIL_PLAN1);
        logger.info("Email: " + EMAIL_PLAN1);

//        logger.info("Setting date of birth to 11th of current month and year.");    it depends on what the date range for plan is
//        membershipPage.click("//button[@aria-label='Open calendar']");
//        membershipPage.waitTwoSeconds();
//        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'11')]");
//        assertTrue(membershipPage.isElementDisplayed("//mat-error[@aria-atomic='true'][contains(.,'The DOB range for this plan is from')]"));

        logger.info("Setting date of birth to 22nd of June 1994.");
        membershipPage.click("//button[@aria-label='Open calendar']");
        membershipPage.click("//span[contains(@id,'mat-calendar-button')]");
        membershipPage.click("//button[@aria-label='Previous 24 years']");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'1994')]");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'JUN')]");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'22')]");
        membershipPage.waitOneSecond();
        //        assertTrue(membershipPage.isElementDisplayed(""));
//        logger.info("Error message is displayed for invalid DOB");

//        membershipPage.click("//button[@aria-label='Open calendar']");
//        membershipPage.click("//span[contains(@id,'mat-calendar-button')]");
//        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2014')]");
//        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'JUN')]");
//        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'22')]");
//        DOB_PLAN1 = "22-06-2014";

        COUNTRY_PLAN1 = "England";
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Country')])[1]");
        membershipPage.waitOneSecond();
        membershipPage.click("//span[@class='mat-option-text'][contains(.,'"+COUNTRY_PLAN1+"')]");
        logger.info("Country: " + COUNTRY_PLAN1);

        COUNTY_PLAN1 = "Devon";
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'County')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", COUNTY_PLAN1);
        logger.info("County: " + COUNTY_PLAN1);

        TOWN_PLAN1 = lorem.getCity()+" town";
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Town')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", TOWN_PLAN1);
        logger.info("Town: " + TOWN_PLAN1);

        STREET_ADDRESS_PLAN1 = lorem.getZipCode()+" "+lorem.getFirstNameFemale()+" street";
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Street address')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", STREET_ADDRESS_PLAN1);
        logger.info("Street address: " + STREET_ADDRESS_PLAN1 );

        POSTCODE_PLAN1 = lorem.getZipCode();
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Postcode')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", POSTCODE_PLAN1);
        logger.info("Postcode: " + POSTCODE_PLAN1);

        Allergies_PLAN1 = "Hay fever, peanuts";
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Allergies')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", Allergies_PLAN1);
        logger.info("Allergies_PLAN1: " + Allergies_PLAN1);

        Medication_PLAN1 = "Panadol, Imodium";
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Please list any medical conditions / injuries / allergies / special needs the club need to be aware of')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", Medication_PLAN1);
        logger.info("Medication_PLAN1: " + Medication_PLAN1);

        EmergencyContactName_PLAN1 = lorem.getFirstName()+" "+lorem.getLastName();
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Member's emergency contact name')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", EmergencyContactName_PLAN1);
        logger.info("EmergencyContactName_PLAN1: " + EmergencyContactName_PLAN1);

        EmergencyContactNumber_PLAN1 = RandomStringUtils.randomNumeric(10);
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'Member's emergency contact number')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", EmergencyContactNumber_PLAN1);
        logger.info("EmergencyContactNumber_PLAN1: " + EmergencyContactNumber_PLAN1);

        IDNumber_PLAN1 = RandomStringUtils.randomNumeric(9);
        membershipPage.click("(//mat-form-field[@appearance='outline'][contains(.,'ID number')])[1]");
        membershipPage.sendKeys("(//input[contains(@aria-invalid,'true')])[1]", IDNumber_PLAN1);
        logger.info("IDNumber_PLAN1: " + IDNumber_PLAN1);

        //Tick boxes
        membershipPage.waitOneSecond();
        membershipPage.click("//span[@class='mat-radio-label-content'][contains(.,'Male')]");
        membershipPage.click("//span[@class='mat-radio-label-content'][contains(.,'Student')]");
        membershipPage.clickElementBelow("//strong[contains(.,'Disability/Special needs')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.click("//span[@class='mat-checkbox-label'][contains(.,'OCM')]");
        membershipPage.click("//span[@class='mat-radio-label-content'][contains(.,'Beginner')]");
        membershipPage.click("//span[@class='mat-radio-label-content'][contains(.,'PG')]");
        membershipPage.clickElementBelow("//strong[contains(.,'Are you a player?')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.clickElementBelow("//strong[contains(.,'Would you be interested in Volunteering for the club?')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.click("//span[@class='mat-radio-label-content'][contains(.,'XL')]");
        membershipPage.clickElementBelow("//strong[contains(.,'I consent to be contacted by my club')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.clickElementBelow("//strong[contains(.,'I consent to allow photo/videos to be taken of me/my child for official club use and/or promotional purposes?')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.click("//span[@class='mat-checkbox-label'][contains(.,'I consent')]");
        membershipPage.clickElementBelow("//strong[contains(.,'I agree to inform the club of any medical conditions I have that are relevant to my participation')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.clickElementBelow("//strong[contains(.,'Please confirm you have read & agree to our Terms & Conditions')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
        membershipPage.clickElementBelow("//strong[contains(.,'Please confirm you have read & agree to our Code of Conduct')]","//span[@class='mat-radio-label-content'][contains(.,'Yes')]");

        membershipPage.click("//button[contains(.,'Confirm details')]");
    }

    @And("membership buyer {string} logs in")
    public void membershipBuyerLogIn(String plan_selection) {
        membershipPage.waitFiveSeconds();

        if (membershipPage.isElementDisplayed("//strong[@data-test='app.userName'][contains(.,'Hello')]")){
            logger.info("We are already logged in, log out first");
            membershipPage.click("//strong[@data-test='app.userName'][contains(.,'Hello')]");
            membershipPage.click("//span[contains(.,'Sign out')]");
            membershipPage.waitTwoSeconds();
        }

        switch (plan_selection) {
            case "mem0005@clubforce.com":
                membershipPage.acceptCookies();
                logger.info("mem0005@clubforce.com logs in");
                membershipPage.click(ProductsPage.MembershipCheckoutPageEnterEmailButton);
                membershipPage.sendKeys(LoginPage.LoginEmailField, "mem0005@clubforce.com");
                AccountPage.MEMRandomMailHolder = "mem0005@clubforce.com";
                membershipPage.click("//button[contains(.,'Next')]");
                membershipPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
                membershipPage.click(LoginPage.SignInButton);
                membershipPage.findOnPage(LoginPage.HelloHeaderText);
                logger.info("mem0005@clubforce.com logged in");
                break;
            case "signup user":
                membershipPage.acceptCookies();
                logger.info("Random user creates account and logs in");
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Login or Create account')]",10);
                membershipPage.click(ProductsPage.MembershipCheckoutPageEnterEmailButton);
                accountPage.useCreateAccountModal("Membership");
                logger.info("Created brand new account: "+AccountPage.MEMRandomMailHolder+" with password b3deG2FnmrEy");
                membershipPage.findOnPage(LoginPage.HelloHeaderText);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for membershipBuyerLogIn!");
        }
    }

    @And("membership plan is no longer available")
    public void lookForSoldOutPlan() {
        membershipPage.waitFiveSeconds();
        membershipPage.findElementBelow("//h3[contains(.,'"+NEW_PLAN_NAME+"')]","//div[contains(.,'sold out')]");
    }

    @And("memberships are added to cart")
    public void addMembershipsToCart() {
        logger.info("Confirm memberships are displayed in summary block");
        membershipPage.findOnPage("//h2[contains(.,'Summary')]");
        assertFalse(membershipPage.isElementDisplayed("//h3[contains(.,'Check registration form details')]"));
        assertFalse(membershipPage.isElementDisplayed("//span[contains(.,'One or multiple form fields are empty or incorrect')]"));
        logger.info("All fields are filled in and validation checks pass. Ready to now add to cart");

        logger.info("Add plans to cart");
        membershipPage.findOnPage(MembershipPage.MembershipAddToCartButton);
        membershipPage.centreElement(MembershipPage.MembershipAddToCartButton);
        membershipPage.click(MembershipPage.MembershipAddToCartButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//button[contains(.,'Checkout')]", 10);
        membershipPage.findOnPage("//button[contains(.,'Continue shopping')]");
        membershipPage.findOnPage("//mat-icon[contains(.,'delete')][1]");

        String membershipPrice = membershipPage.getElementAttribute(
                "(//div[contains(@fxlayoutalign,'end flex-end')])[3]", "textContent");
        MEMBERSHIP_TOTAL_PRICE_HOLDER = membershipPrice.substring(1, membershipPrice.length() - 1);
        logger.info("Membership total price for the one plan that is being purchased :" + MEMBERSHIP_TOTAL_PRICE_HOLDER + ".");
        logger.info("Membership is successfully added to the cart");
    }

    @And("user click Continue Shopping")
    public void continueShopping() {
        logger.info("Shopper select to continue shopping ");
        membershipPage.click("//button[contains(.,'Continue shopping')]");
        membershipPage.waitThreeSeconds();
    }

    @And("user click Checkout to go pay")
    public void goToPaymentPage() {
        logger.info("Shopper select to go too payment page ");
        membershipPage.click("//button[contains(.,'Checkout')]");
        membershipPage.waitThreeSeconds();
    }

    @And("random memberships are added to cart")
    public void addRandomMembershipsToCart() {
        membershipPage.findOnPage("//h3[contains(.,'Select how you want to pay for each plan')]");
        membershipPage.findOnPage("//p[@class='text-primary mb-0'][contains(.,'Plan')]");
        logger.info("Confirm memberships are displayed in summary block");
        membershipPage.findOnPage("//h2[contains(.,'Summary')]");
        membershipPage.findOnPage("//li[contains(.,'Membership plans  × 3')]");
        membershipPage.findOnPage("//span[contains(.,'BrandNewPlan')]");
        membershipPage.findOnPage("(//span[contains(.,'Adult')])[1]");
        membershipPage.findOnPage("//span[contains(.,'Adult (Non-Playing)')]");
        membershipPage.findOnPage("//strong[contains(.,'Total')]");
        membershipPage.findOnPage("//strong[contains(.,'€883.99')]");
        logger.info("Add plans to cart");
        membershipPage.click("(//button[contains(.,'add to cart')])[1]");
        membershipPage.waitFiveSeconds();
        membershipPage.findOnPage("(//span[contains(.,'Membership')])[2]");
        membershipPage.findOnPage("(//span[contains(.,'Membership')])[3]");
        membershipPage.findOnPage("(//span[contains(.,'Membership')])[4]");
        membershipPage.click("//*[contains(text(),'add to cart')]");
    }

    @And("member purchases {string} memberships with {string} card")
    public void purchaseMembershipPlans(String planPriceType, String card) {
        Lorem lorem = LoremIpsum.getInstance();

        switch (planPriceType) {
            case "paid":
                logger.info("Filling in card details");
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Hello,')]", 10);
                membershipPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.CheckoutPageNameOnCard, 10);
                productsPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
                membershipPage.waitTwoSeconds();
                driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
                membershipPage.waitFiveSeconds();
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardNumber, card);
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
                membershipPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
                membershipPage.waitFiveSeconds();
                driverManager.driver.switchTo().parentFrame();
                membershipPage.click(ProductsPage.CheckoutPayNowButton);
                membershipPage.waitTenSeconds();
                break;
            case "free":
                productsPage.waitForElementDisplayedByXpathWithTimeout("//p[contains(.,'You will not be charged for this transaction')]",10);
                productsPage.click("//span[contains(.,'Complete Transaction')]");
                membershipPage.waitTenSeconds();
                break;
            case "promo included":
                logger.info("Checking we have membership and also lotto in cart");
                productsPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Membership plans')]",5);
                productsPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Lotto tickets')]",5);
                logger.info("Filling in card details");

                membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Hello,')]", 10);
                membershipPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.CheckoutPageNameOnCard, 10);
                productsPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
                membershipPage.waitTwoSeconds();
                driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
                membershipPage.waitFiveSeconds();
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardNumber, card);
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
                membershipPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
                membershipPage.waitFiveSeconds();
                driverManager.driver.switchTo().parentFrame();
                membershipPage.click(ProductsPage.CheckoutPayNowButton);
                membershipPage.waitTenSeconds();
                break;
            case "instalments plans":
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Hello,')]", 10);
                membershipPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.CheckoutPageNameOnCard, 10);

                int num;
                if(isInstalmentPlanEdited){
                    logger.info("If day is odd we select 10 months instalments, if day is even we select 5 months instalments");
                    String day = SeleniumUtilities.getDateTimeFormat("dd");
                    int dayDate = Integer.parseInt(day);
                    if (dayDate % 2 == 0) {
                        logger.info("Day is: " + dayDate + " which is even, 5 monthly instalments");
                        num = 5;
                    } else {
                        logger.info("Day is: " + dayDate + " which is odd, 10 months instalments");
                        num = 10;
                    }
                }else{
                    logger.info("If day is odd we select 9 months instalments, if day is even we select 6 months instalments");
                    String day = SeleniumUtilities.getDateTimeFormat("dd");
                    int dayDate = Integer.parseInt(day);
                    if (dayDate % 2 == 0) {
                        logger.info("Day is: " + dayDate + " which is even, 6 monthly instalments");
                        num = 6;
                    } else {
                        logger.info("Day is: " + dayDate + " which is odd, 9 months instalments");
                        num = 9;
                    }
                }

                membershipPage.findOnPage("//div[@class='fw-bold'][contains(.,'"+num+" monthly instalments')]");

                logger.info("Checking we have instalments plan in cart");
                double instalmentPayNowCalculation = Double.parseDouble(INSTALMENT_PLAN_FULL_PRICE) / num;
                BigDecimal instalmentMonthlyAmount = new BigDecimal(instalmentPayNowCalculation).setScale(2, RoundingMode.HALF_UP);
                double INSTALMENT_PAY_NOW_MONTHLY_PRICE = instalmentMonthlyAmount.doubleValue();
                logger.info("Rounded up to 2 decimal value " + INSTALMENT_PAY_NOW_MONTHLY_PRICE);

                logger.info("Looking for INSTALMENT_PLAN_FULL_PRICE: "+INSTALMENT_PLAN_FULL_PRICE);
                productsPage.findOnPage("//*[contains(text(),'"+INSTALMENT_PLAN_FULL_PRICE+"')]");

                logger.info("Looking for INSTALMENT_PAY_NOW_MONTHLY_PRICE: "+INSTALMENT_PAY_NOW_MONTHLY_PRICE);
//                productsPage.findOnPage("//*[contains(text(),'"+INSTALMENT_PAY_NOW_MONTHLY_PRICE+"')]"); TODO BE does a certain calculation that 1st payment is sometimes a few cents different

                logger.info("Click monthly instalment option on checkout page");
                productsPage.click("//*[contains(text(),'Monthly payment')]");
                productsPage.waitTwoSeconds();
                productsPage.findOnPage("//small[contains(.,'Your card will be saved for recurring orders and for future purchases. You can remove it at any time. About your data.')]");

                productsPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
                membershipPage.waitTwoSeconds();
                driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
                membershipPage.waitFiveSeconds();
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardNumber, card);
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
                membershipPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
                membershipPage.waitFiveSeconds();
                driverManager.driver.switchTo().parentFrame();
                membershipPage.click(ProductsPage.CheckoutPayNowButton);
                membershipPage.waitTenSeconds();
                break;
            case "instalments bundle":
                productsPage.waitForElementDisplayedByXpathWithTimeout("//label[@class='mat-radio-label'][contains(.,'monthly instalments')]",10);
                productsPage.click("//label[@class='mat-radio-label'][contains(.,'monthly instalments')]");
                membershipPage.waitFiveSeconds();
                productsPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
                membershipPage.waitTwoSeconds();
                driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
                membershipPage.waitFiveSeconds();
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardNumber, card);
                membershipPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
                membershipPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
                membershipPage.waitFiveSeconds();
                driverManager.driver.switchTo().parentFrame();
                membershipPage.click(ProductsPage.CheckoutPayNowButton);
                membershipPage.waitTenSeconds();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for purchaseMembershipPlans!");
        }
        logger.info("Checking that confirmation page is displayed");
        membershipPage.findOnPage("//h1[contains(.,'Order confirmed')]");
        membershipPage.findOnPage("//p[contains(.,'Thank you for your order. We hope you had a great experience.')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Order summary')]", 8);

        logger.info("Extracting order date, time and ID");
        ORDER_DATE_ID_HOLDER = membershipPage.getElementAttribute("//p[contains(.,'Order placed on ')]", "textContent");
        logger.info("ORDER_DATE_ID_HOLDER = "+ ORDER_DATE_ID_HOLDER);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Membership plans')]", 30);
        ORDER_ID = membershipPage.getElementAttribute("//p[contains(.,'ID ')]", "textContent");
        ORDER_ID_HOLDER = ORDER_ID.substring(3, 9);
        logger.info("ORDER_ID_HOLDER = "+ ORDER_ID_HOLDER);

        logger.info("Check order summary on confirmation page");
        membershipPage.findOnPage("//h2[contains(.,'Order summary')]");
        membershipPage.findOnPage("//*[contains(text(),'Membership plans')]");
        productsPage.findOnPage(ProductsPage.ConfirmationPagePaymentMethodTitle);
        productsPage.findOnPage(ProductsPage.ConfirmationPagePaymentMethodTitle);

//        membershipPage.findOnPage("//small[contains(.,'"+FIRST_NAME_PLAN1+" "+LAST_NAME_PLAN1+"')]");
//        membershipPage.findOnPage("(//strong[contains(.,'"+MEMBERSHIP_TOTAL_PRICE_HOLDER+"')])[1]");
//        membershipPage.findOnPage("(//strong[contains(.,'"+MEMBERSHIP_TOTAL_PRICE_HOLDER+"')])[2]");
//        membershipPage.findOnPage("//div[@class='text-end h4 py-1'][contains(.,'"+MEMBERSHIP_TOTAL_PRICE_HOLDER+"')]");
        membershipPage.findOnPage("//p[contains(.,'A copy of this message has been sent to your email')]");
    }

    @And("Member is displayed on Search page")
    public void checkMembershipRegistrations() {
        membershipPage.findOnPage("//h1[contains(.,'User search')]");
        membershipPage.findOnPage("(//strong[contains(.,'First name')])[1]");
        membershipPage.findOnPage("(//strong[contains(.,'Last name')])[1]");
//        membershipPage.findOnPage("(//div[@class='cf-ui-list-item-content col'][contains(.,'"+ FIRST_NAME_PLAN1 +"')])[1]");
//        membershipPage.findOnPage("//div[@class='cf-ui-list-item-content col'][contains(.,'"+ LAST_NAME_PLAN1 +"')][1]");
    }

    @And("the plan is selected on CW")
    public void purchaseNewPlanOnWebsite() {
        if (!(NEW_PLAN_NAME == null)){
            membershipPage.findOnPage("//h3[contains(.,'"+NEW_PLAN_NAME+"')]");
            membershipPage.centreElement("//h3[contains(.,'"+NEW_PLAN_NAME+"')]");
            membershipPage.clickElementBelow("//h3[contains(.,'"+NEW_PLAN_NAME+"')]","//button[contains(.,'select plan')]");
        } else {
            membershipPage.clickElementBelow("(//span[@class='text-muted'][contains(.,'Valid until')])[1]",
                    "//button[contains(.,'select plan')]");
        }
    }

    @And("Member choose to continue")
    public void membershipContinue() {
        membershipPage.click(MembershipPage.ContinueButtonOnWebsiteStep1Membership);
        membershipPage.waitUntilElementInvisible(MembershipPage.ContinueButtonOnWebsiteStep1Membership,5);
    }

    public void checkMembershipFindOutMoreLink() {
        logger.info("Open Find out more link in new tab");
        membershipPage.click(MembershipPage.ClubforceFeesLink);
        membershipPage.waitTwoSeconds();
        membershipPage.switchToBrowserTab(1);
        membershipPage.waitTwoSeconds();
        assertTrue(driverManager.driver.getCurrentUrl().contains("/pricing"));
        assertThat("Did not find expected text!",  membershipPage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Pricing")));
        assertThat("Did not find expected text!",  membershipPage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Everything a committee needs to run a sports club")));
        logger.info("All is well on Find out more - go back to old tab");
        membershipPage.switchToBrowserTab(0);
    }

    @And("ClubAdmin can create an instalment membership bundle")
    public void createMembershipInstalmentBundle() {

        logger.info("Check there is at least 1 published plan");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Your published plans!')]",15);
        logger.info("There is at least 1 published plan");

        logger.info("Go to bundle page");
        membershipPage.scrollPageToTop();
        membershipPage.click(MembershipPage.NewBundleButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Create family bundle')]",5);
        membershipPage.findOnPage("//*[contains(text(),'Step 1 of 2 - Select applicable plans')]");
        membershipPage.findOnPage("//*[contains(text(),'Plans included')]");

        logger.info("Check that clicking next step with no plan selected produce error");
        membershipPage.click(MembershipPage.NextStepButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'To continue select at least one plan')]",5);
        logger.info("Clicking next step triggered errors as expected");

        logger.info("Select one plan");
        membershipPage.click("(//span[contains(@class,'mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin')])[3]");
        membershipPage.waitTwoSeconds();

        membershipPage.click(MembershipPage.NextStepButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Cap price')]",5);
        assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'Step 2 of 2 - Set up a bundle')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Bundle details')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Member limits')]"));
        assertTrue(membershipPage.isElementDisplayed("//p[contains(.,'Adult members:')]"));
        assertTrue(membershipPage.isElementDisplayed("//p[contains(.,'Juvenile members:')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Cap price')]"));

        logger.info("Error checks on Set Up Bundle page");
        membershipPage.click(MembershipPage.ConfirmAndPublishButton);
        membershipPage.findOnPage("//span[contains(.,'Please enter Bundle name')]");
        membershipPage.findOnPage("//span[contains(.,'Please enter Valid from')]");
        membershipPage.findOnPage("//span[contains(.,'Please enter Valid to')]");

        logger.info("Fill in details on Set Up Bundle page");
        BUNDLE_NAME_HOLDER = "Bundle "+RandomStringUtils.randomAlphanumeric(8);
        CapPriceHOLDER = "3"+RandomStringUtils.randomNumeric(2)+".99";
        PriceInBundleHOLDER1 = "1"+RandomStringUtils.randomNumeric(1)+".99";

        membershipPage.sendKeys("//input[@formcontrolname='bundleName']", BUNDLE_NAME_HOLDER);
        membershipPage.click(MembershipPage.DatePicker1);
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'JAN')]");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'11')]");
        membershipPage.click(MembershipPage.DatePicker2);
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'DEC')]");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'31')]");
        membershipPage.scrollPageToBottom();
        membershipPage.sendKeys("//input[contains(@formcontrolname,'capPrice')]", CapPriceHOLDER);
        membershipPage.sendKeys("//input[contains(@formcontrolname,'price_in_bundle')]", PriceInBundleHOLDER1);

//        boolean disabledCheck1 = Boolean.parseBoolean(membershipPage.getElementAttribute("(//input[contains(@formcontrolname,'min')])[1]", "disabled"));
//        logger.info("disabledCheck1 = "+disabledCheck1);
//        membershipPage.waitTwoSeconds();
//        if (!disabledCheck1){
//            membershipPage.sendKeys("(//input[contains(@formcontrolname,'min')])[1]", "2");
//        }
//
//        boolean disabledCheck2 = Boolean.parseBoolean(membershipPage.getElementAttribute("(//input[contains(@formcontrolname,'min')])[2]", "disabled"));
//        logger.info("disabledCheck2 = "+disabledCheck2);
//        membershipPage.waitTwoSeconds();
//        if (!disabledCheck2){
//            membershipPage.sendKeys("(//input[contains(@formcontrolname,'min')])[2]", "2");
//        }

        logger.info("Give bundle 2 monthly instalments (default)");
        membershipPage.scrollPageToBottom();
        membershipPage.click("//button[contains(.,'enable instalments')]");
        membershipPage.waitTwoSeconds();
        membershipPage.escape();
        membershipPage.scrollPageToTop();
        membershipPage.waitTwoSeconds();
        membershipPage.click(MembershipPage.ConfirmAndPublishButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'"+BUNDLE_NAME_HOLDER+"')]", 15);
        isBundle = true;
    }

    @And("ClubAdmin edits and publishes the membership bundle")
    public void editMembershipBundle() {
        logger.info("Look for our bundle");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'"+BUNDLE_NAME_HOLDER+"')]",15);

        logger.info("Go into bundle");
        membershipPage.clickElementRightOf("//*[contains(text(),'"+BUNDLE_NAME_HOLDER+"')]", "//button[contains(.,'Edit Details')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Plans included')]",15);
        membershipPage.click("//button[contains(.,'next step')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Bundle details')]",15);

        logger.info("Verify details");
        assertThat("BUNDLE_NAME not as expected", membershipPage.getElementAttribute("//input[contains(@formcontrolname,'bundleName')]", "value"), containsString(BUNDLE_NAME_HOLDER));
        assertThat("CapPrice not as expected", membershipPage.getElementAttribute("//input[contains(@formcontrolname,'capPrice')]", "value"), containsString(CapPriceHOLDER));
        assertThat("PriceInBundle not as expected", membershipPage.getElementAttribute("(//input[contains(@formcontrolname,'price_in_bundle')])[1]", "value"), containsString(PriceInBundleHOLDER1));

        logger.info("Make some changes, click update");
        EDITED_BUNDLE_NAME_HOLDER = BUNDLE_NAME_HOLDER+", edited: "+SeleniumUtilities.getDateTimeFormat(("yyyy-MM-dd HH:mm:ss"));
        membershipPage.sendKeys("//input[contains(@formcontrolname,'bundleName')]",EDITED_BUNDLE_NAME_HOLDER);
        membershipPage.sendKeys("//input[contains(@formcontrolname,'capPrice')]","4"+RandomStringUtils.randomNumeric(2)+".77");
        membershipPage.click(MembershipPage.DatePicker1);
        membershipPage.waitTwoSeconds();
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'MAR')]");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'17')]");
        membershipPage.click(MembershipPage.DatePicker2);
        membershipPage.waitTwoSeconds();
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'NOV')]");
        membershipPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'11')]");
        membershipPage.click("//button[contains(.,'Update')]");
        membershipPage.waitUntilElementInvisible("//button[contains(.,'Update')]", 15);
    }

    @And("ClubAdmin can cancel during bundle creation")
    public void cancelMembershipBundle() {
        logger.info("Check there is at least 1 published plan");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Your published plans!')]",5);
        assertTrue(membershipPage.isElementDisplayed("//span[@data-test='membership-plans.status'][contains(.,'Published')]"));

        logger.info("Check how many bundles are already created");

        int bundleNumber = 1;
        if(membershipPage.isElementDisplayed("(//button[contains(.,'Unpublish')])[1]")){
            while(membershipPage.isElementDisplayed("(//button[contains(.,'Unpublish')])["+bundleNumber+"]")){
                logger.info("Bundle number : "+ bundleNumber);
                bundleNumber++;
                logger.info("Bundle number after increment: "+ bundleNumber);
            }
        }else{
            bundleNumber = 0;
        }

        if(membershipPage.isElementDisplayed("(//button[contains(.,'Unpublish')])["+bundleNumber+"]")){
            PLAN_NAME = membershipPage.getElementAttribute("(//div[@class='cf-ui-list-item-content col'])["+bundleNumber+1+"]", "innerText");
            logger.info("There is at least 1 published plan, named "+PLAN_NAME);
        }

        logger.info("Check the bundle button is visible");
        assertTrue(membershipPage.isElementDisplayed(MembershipPage.NewBundleButton));
        logger.info("Bundle button is visible");

        logger.info("Go to bundle page");
        membershipPage.click(MembershipPage.NewBundleButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Create family bundle')]",5);
        membershipPage.findOnPage("//*[contains(text(),'Step 1 of 2 - Select applicable plans')]");
        membershipPage.findOnPage("//*[contains(text(),'Plans included')]");

        logger.info("Check that clicking cancel takes us back to membership plans page");
        membershipPage.click(MembershipPage.CancelButton);
        membershipPage.click(MembershipPage.ModalDiscardButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Your published plans!')]",5);
        logger.info("Clicking cancel took us back to membership plans page");

        logger.info("Now select a plan and go to last step, and then cancel");
        membershipPage.click(MembershipPage.NewBundleButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Create family bundle')]",5);
        membershipPage.click("(//span[contains(@class,'mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin')])[1]");
        membershipPage.waitTwoSeconds();

        membershipPage.click(MembershipPage.NextStepButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Cap price')]",5);
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Cap price')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Bundle details')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Member limits')]"));
        assertTrue(membershipPage.isElementDisplayed("//p[contains(.,'Adult members:')]"));
        assertTrue(membershipPage.isElementDisplayed("//p[contains(.,'Juvenile members:')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Cap price')]"));

        logger.info("Check that clicking cancel on cancel modal leaves us remaining on step 2 page");
        membershipPage.click(MembershipPage.CancelButton);
        membershipPage.click(MembershipPage.ModalCancelButton);
        assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'Step 2 of 2 - Set up a bundle')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Bundle details')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Member limits')]"));
        assertTrue(membershipPage.isElementDisplayed("//p[contains(.,'Adult members:')]"));
        assertTrue(membershipPage.isElementDisplayed("//p[contains(.,'Juvenile members:')]"));
        assertTrue(membershipPage.isElementDisplayed("//h3[contains(.,'Cap price')]"));


        logger.info("Check that clicking discard on cancel modal takes us back to plans page");
        membershipPage.click(MembershipPage.CancelButton);
        membershipPage.click(MembershipPage.ModalDiscardButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Your published plans!')]",5);
        assertTrue(membershipPage.isElementDisplayed("//span[@data-test='membership-plans.status'][contains(.,'Published')]"));
    }

    @And("bundle is {string} on the memberships purchase page")
    public void checkBundlePresentPurchasePage(String visibility) {
        getMembershipLinkOnMembershipPlansPage();
        goToMembershipPageOnWebsite();

        switch (visibility){
            case "visible":
                logger.info("Check the created bundle "+BUNDLE_NAME_HOLDER+" is present");
                membershipPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Memberships')]", 15);
                membershipPage.findOnPage("//h3[@data-test='bundle-detail.name'][contains(.,'"+BUNDLE_NAME_HOLDER+"')]");
                break;
            case "not visible":
                logger.info("Check the created bundle "+BUNDLE_NAME_HOLDER+" is not present");
                membershipPage.waitFiveSeconds();
                assertFalse(membershipPage.isElementPresent(
                        "//h3[@data-test='bundle-detail.name'][contains(.,'"+BUNDLE_NAME_HOLDER+"')]"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminCreatesProduct!");
        }

    }

    @And("ClubAdmin gets membership page link")
    public void getMembershipPageLink(){
        logger.info("Going from membership plans to purchase page");
        BuyPlanURLHOLDER = membershipPage.getElementAttribute("//h3[contains(.,'://')]", "textContent");
        logger.info("BuyPlanURLHOLDER: " + BuyPlanURLHOLDER);

        if (envName.contains("local")) {
            String BuyPlanClubName = membershipPage.getElementAttribute("//strong[contains(.,'Membership')]", "textContent");
            BuyPlanURLHOLDER = "http://"+BuyPlanClubName+".development.local:4100/products/memberships/memberships";
        }
    }

    @And("user goes to membership page on website")
    public void goToMembershipPageOnWebsite(){
        driverManager.driver.navigate().to(BuyPlanURLHOLDER);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Summary')]",60);
        membershipPage.acceptCookies();
//        assertTrue(driverManager.driver.getCurrentUrl().contains(BuyPlanURLHOLDER));  doesn't work for local, fix later
    }

    @And("ClubAdmin goes to member profile page on order details page")
    public void goToMemberPageOnOrderDetailsPage(){
        membershipPage.findOnPage("//h1[contains(.,'Order details')]");
        membershipPage.click("//a[contains(@data-test,'order-details.name')]");
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//div[@class='text-muted'][contains(.,'Member ID')]", 30);
    }

    @Then("Membership on dashboard works as expected")
    public void membershipDashboardWorks(){
        membershipPage.findOnPage(MembershipPage.DashboardViewMembershipPlansGroupButton);
        membershipPage.findOnPage("//mat-icon[@role='img'][contains(.,'insights')]");
        membershipPage.findOnPage("//canvas[contains(@width,'')]");
        membershipPage.click("//mat-icon[@role='img'][contains(.,'list')]");
        membershipPage.findOnPage("(//strong[@class='ng-star-inserted'][contains(.,'Title')])[1]");
        membershipPage.findOnPage("(//strong[@class='ng-star-inserted'][contains(.,'Signups')])[1]");
        membershipPage.click(MembershipPage.DashboardViewMembershipPlansGroupButton);
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//h2[@class='cf-ui-content-title'][contains(.,'Your published plans!')]", 30);
    }

    @And("ClubAdmin cancels {string} membership registration")
    public void cancelMembership(String membershipPaymentType){
        switch (membershipPaymentType){
            case "paid in full":
            case "instalment":
                membershipPage.findOnPage("(//mat-icon[@role='img'][contains(.,'more_vert')])[2]");
                membershipPage.click("(//mat-icon[@role='img'][contains(.,'more_vert')])[2]");
                membershipPage.click("(//span[@class='ms-2 text-danger'][contains(.,'Cancel membership')])[1]");
                membershipPage.findOnPage("//h2[contains(.,'Cancel membership')]");

                if(isBundle){
                    logger.info("Plan is part of a bundle");
                    membershipPage.findOnPage("//mat-icon[@role='img'][contains(.,'warning')]");
                    membershipPage.findOnPage("//h6[contains(.,'Bundle registrations')]");
                    membershipPage.findOnPage("//span[contains(.,'Registrations within this bundle will also be cancelled:')]");
                }else{
                    membershipPage.findOnPage("//p[contains(.,'You are canceling: "+NEW_PLAN_NAME+"')]");
                }

                membershipPage.findOnPage("//*[contains(text(),'Cancellation reason')]");
                membershipPage.click("(//div[contains(.,'Cancellation reason')])[7]");
                membershipPage.click("//span[@class='mat-option-text'][contains(.,'Other')]");
                membershipPage.findOnPage("//input[contains(@formcontrolname,'notes')]");
                String membershipCancellationReason = "QA cancellation " + RandomStringUtils.randomAlphabetic(4);
                membershipPage.sendKeys("//input[contains(@formcontrolname,'notes')]", membershipCancellationReason);
                membershipPage.click("//button[contains(.,'cancel membership')]");
                membershipPage.findOnPage("//span[contains(.,'info CANCELLED')]");
                membershipPage.findOnPage("//h5[contains(.,'check_circle Membership cancelled')]");
                membershipPage.findOnPage("//p[contains(.,'Do you also want to initiate refund process for this membership?')]");
                membershipPage.click("//button[contains(.,'cancel')]");
                membershipPage.click("//a[contains(.,'Registration Details')]");
                membershipPage.findOnPage("//span[@class='badge bg-danger d-inline-block p-2 pill'][contains(.,'Cancelled')]");
                membershipPage.findOnPage("//label[@class='text-muted mb-2'][contains(.,'Cancellation details:')]");
                membershipPage.findOnPage("//span[contains(.,'Cancelled by:')]");
                membershipPage.findOnPage("//span[contains(.,'Time: ')]");
                membershipPage.findOnPage("//span[contains(.,'Reason: Other. "+membershipCancellationReason+"')]");
                break;
            default:
        }
    }

    public void verifyDefaultStandardQuestionsAreDisplayedOnForm(){
        membershipPage.findOnPage("//strong[contains(.,'Member Details')]");
        membershipPage.findOnPage("//strong[contains(.,'First name')]");
        membershipPage.findOnPage("//strong[contains(.,'Last name')]");
        membershipPage.findOnPage("//strong[contains(.,'DOB')]");
        membershipPage.findOnPage("//strong[contains(.,'Gender')]");
        membershipPage.findOnPage("//strong[contains(.,'Email')]");
        membershipPage.findOnPage("//strong[contains(.,'Phone number')]");
        membershipPage.findOnPage("//strong[contains(.,'Street address')]");
        membershipPage.findOnPage("//strong[contains(.,'Town')]");
        membershipPage.findOnPage("//strong[contains(.,'Postcode')]");
        membershipPage.findOnPage("//strong[contains(.,'County')]");
        membershipPage.findOnPage("//strong[contains(.,'Country')]");

        membershipPage.findOnPage("//strong[contains(.,'Communications')]");
        membershipPage.findOnPage("//strong[contains(.,'I consent to be contacted by my club')]");
        membershipPage.findOnPage("//strong[contains(.,'What is your preferred method of communication?')]");

        membershipPage.findOnPage("//strong[contains(.,'Agreements')]");
        membershipPage.findOnPage("//strong[contains(.,'I consent for member information to be retained by the Club and submitted to the relevant Governing Body for administrative and statistical purposes for such period as the membership subsists')]");

        for(int i = 1; i <= 14; i++){
            logger.info("Required question : " + i);
            membershipPage.findOnPage("(//span[@class='text-muted'][contains(.,'Required question')])["+i+"]");
        }
    }

    public void verifyDefaultAdditionalStandardQuestionsAreDisplayedOnAddQuestionsForm(){
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Are you a player?')]");
//        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Nationality')]"); https://clubforce.atlassian.net/browse/CE-546
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Allergies')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Disability/Special needs')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Please list any medical conditions / injuries / allergies / special needs the club need to be aware of')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Playing kit size')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Place of Birth')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'FAI League last registered with')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'If you have played with a different club previously, please add')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Add your IRFU number, if known')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Add your BIPIN number, if known')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Add your AAI number, if known')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Add your Hockey Ireland membership number')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Are you a New member or Renewing member')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'IRFU age grade')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Hockey - Please choose your school class/year that player will enter in September')]");

        membershipPage.findOnPage("//h3[contains(.,'Communications')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'I consent to allow photo/videos to be taken of me/my child for official club use and/or promotional purposes.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Would you be interested in volunteering for the club?')]");

        membershipPage.findOnPage("//h3[contains(.,'Agreements')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Please confirm you have read & agree to our Terms & Conditions')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'It is your responsibility to inform the team coach/trainer or volunteer of any medical conditions you or your child(ren) may have')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Please confirm you have read & agree to our Code of Conduct')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Confirm you have read and agree with the Child Protection Policy.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Confirm you have read and agree to our Player Code of Conduct.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Confirm you have read and agree to our Parent/Guardian Code of Conduct.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Confirm you have read and agree with our Social Media Policy.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Confirm you have read and understand our GDPR Policy.')]");
    }

    public void verifyGuardianQuestionsAreMandatoryOnForm(){
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//strong[contains(.,'Parent/Guardian Details')]",20);
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'First name')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Last name')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Gender')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Email')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Phone number')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Street address')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Town')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Postcode')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'County')]");
        membershipPage.findElementBelow("//strong[contains(.,'Parent/Guardian Details')]", "//strong[contains(.,'Country')]");
    }

    public void verifyGuardianQuestionsAreNotMandatoryOnForm(){
        assertFalse(membershipPage.isElementDisplayed("//strong[contains(.,'Parent/Guardian Details')]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'First name')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Last name')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Phone number')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Email')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Gender')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Country')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'County')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Town')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Street address')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Postcode')])[2]"));
        assertFalse(membershipPage.isElementDisplayed("(//strong[contains(.,'Would you be interested in Volunteering for the club?')])[2]"));
    }

    public void verifyOptionalStandardQuestionsAreNotDisplayed(){
        logger.info("checking optional questions don't show yet");
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Please select the option that applies to you')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Allergies')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Please list any medical conditions / injuries / allergies / special needs the club need to be aware of')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Disability/Special needs')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Member's emergency contact name')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Member's emergency contact number')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'What is your club role?')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'ID number')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Level of experience')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Academic Year')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Playing kit size')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'I agree to inform the club of any medical conditions I have that are relevant to my participation')]"));
        assertFalse(membershipPage.isElementDisplayed("//*[contains(text(),'Please confirm you have read & agree to our Code of Conduct')]"));
    }

    public void calculateInstalments(){
        membershipPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Number of instalments')]",5);

        if(membershipPage.isElementDisplayed("//span[contains(@class,'mat-select-placeholder mat-select-min-line')]")){
            membershipPage.click("//span[contains(@class,'mat-select-placeholder mat-select-min-line')]");  // click dropdown
        }else{
            membershipPage.click("(//span[contains(.,'monthly instalments - ')])[1]");
        }


        logger.info("Calculate instalments and check page.");
        int INSTALMENT_PLAN_FULL_PRICE_Int = Integer.parseInt(INSTALMENT_PLAN_FULL_PRICE);
        int oneMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int;
        int twoMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 2;
        int threeMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 3;
        int fourMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 4;
        int fiveMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 5;
        int sixMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 6;
        int sevenMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 7;
        int eightMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 8;
        int nineMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 9;
        int tenMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 10;
        int elevenMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 11;
        int twelveMonthlyInstalmentsPrice = INSTALMENT_PLAN_FULL_PRICE_Int / 12;


        logger.info("oneMonthlyInstalmentsPrice: "+oneMonthlyInstalmentsPrice);
        logger.info("twoMonthlyInstalmentsPrice: "+twoMonthlyInstalmentsPrice);
        logger.info("threeMonthlyInstalmentsPrice: "+threeMonthlyInstalmentsPrice);
        logger.info("fourMonthlyInstalmentsPrice: "+fourMonthlyInstalmentsPrice);
        logger.info("fiveMonthlyInstalmentsPrice: "+fiveMonthlyInstalmentsPrice);
        logger.info("sixMonthlyInstalmentsPrice: "+sixMonthlyInstalmentsPrice);
        logger.info("sevenMonthlyInstalmentsPrice: "+sevenMonthlyInstalmentsPrice);
        logger.info("eightMonthlyInstalmentsPrice: "+eightMonthlyInstalmentsPrice);
        logger.info("nineMonthlyInstalmentsPrice: "+nineMonthlyInstalmentsPrice);
        logger.info("tenMonthlyInstalmentsPrice: "+tenMonthlyInstalmentsPrice);
        logger.info("elevenMonthlyInstalmentsPrice: "+elevenMonthlyInstalmentsPrice);
        logger.info("twelveMonthlyInstalmentsPrice: "+twelveMonthlyInstalmentsPrice);

        // Set currency symbol

        if (membershipPage.getElementAttribute("//span[@class='mat-option-text'][contains(.,'3 monthly instalments')]", "textContent").contains("£"))  {
            String currencySymbol = "£";
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'1 monthly instalment -  "+currencySymbol+oneMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'2 monthly instalments -  "+currencySymbol+twoMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'3 monthly instalments -  "+currencySymbol+threeMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'4 monthly instalments -  "+currencySymbol+fourMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'5 monthly instalments -  "+currencySymbol+fiveMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'6 monthly instalments -  "+currencySymbol+sixMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'7 monthly instalments -  "+currencySymbol+sevenMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'8 monthly instalments -  "+currencySymbol+eightMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'9 monthly instalments -  "+currencySymbol+nineMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'10 monthly instalments -  "+currencySymbol+tenMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'11 monthly instalments -  "+currencySymbol+elevenMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'12 monthly instalments -  "+currencySymbol+twelveMonthlyInstalmentsPrice+"')]"));
        } else {
            String currencySymbol = "€";
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'1 monthly instalment -  "+currencySymbol+oneMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'2 monthly instalments -  "+currencySymbol+twoMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'3 monthly instalments -  "+currencySymbol+threeMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'4 monthly instalments -  "+currencySymbol+fourMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'5 monthly instalments -  "+currencySymbol+fiveMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'6 monthly instalments -  "+currencySymbol+sixMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'7 monthly instalments -  "+currencySymbol+sevenMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'8 monthly instalments -  "+currencySymbol+eightMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'9 monthly instalments -  "+currencySymbol+nineMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'10 monthly instalments -  "+currencySymbol+tenMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'11 monthly instalments -  "+currencySymbol+elevenMonthlyInstalmentsPrice+"')]"));
            assertTrue(membershipPage.isElementDisplayed("//span[contains(.,'12 monthly instalments -  "+currencySymbol+twelveMonthlyInstalmentsPrice+"')]"));
        }
    }

    public void customQuestionDropDown(){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Adding a dropdown custom question");
        logger.info("'dropdown' special questions have been selected, adding a single question tom Agreements section");
        membershipPage.click("//button[contains(.,'add_circle_outline create question')]");
        membershipPage.click("//li[contains(.,'arrow_drop_down_circle Dropdown')]");
        membershipPage.findOnPage("//strong[contains(.,'Customise dropdown')]");

        DropdownQuestionTitleHOLDER1 = "Best name? "+RandomStringUtils.randomNumeric(3);
        DropdownQuestionFirstNameHOLDER1 = lorem.getFirstNameMale();
        DropdownQuestionFirstNameHOLDER2 = lorem.getFirstNameFemale();

        membershipPage.sendKeys("(//input[contains(@formcontrolname,'label')])[1]", DropdownQuestionTitleHOLDER1);
        membershipPage.click("//div[@class='d-flex flex-grow-1 align-items-center'][contains(.,'1. Option 1')]");
        membershipPage.sendKeysToElementAbove("//button[contains(.,'add_circle_outline add option')]", "//input[contains(@type,'text')]", DropdownQuestionFirstNameHOLDER1);
        membershipPage.click("//button[contains(.,'add_circle_outline add option')]");
        membershipPage.click("//div[@class='d-flex flex-grow-1 align-items-center'][contains(.,'2. Option 2')]");
        membershipPage.sendKeysToElementAbove("//button[contains(.,'add_circle_outline add option')]", "//input[contains(@type,'text')]", DropdownQuestionFirstNameHOLDER2);

        membershipPage.click("//mat-select[contains(@formcontrolname,'default_section_id')]");
        membershipPage.click("//span[@class='mat-option-text'][contains(.,'Member Details')]");
        membershipPage.click("//span[@class='mat-checkbox-label'][contains(.,'Required question')]");
//                    membershipPage.click("(//button[contains(.,'create question')])[2]");
//                    membershipPage.click("//button[contains(.,'Save changes')]");
        membershipPage.click("//mat-select[contains(@formcontrolname,'default_section_id')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Member Details')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Communications')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Parent/Guardian Details')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Agreements')]");
        membershipPage.click("//span[@class='mat-option-text'][contains(.,'Agreements')]");
        membershipPage.click("//button[contains(.,'Create question')]");
        membershipPage.waitUntilElementInvisible("//h5[contains(.,'Create question')]",5);
        membershipPage.findOnPage("//span[contains(.,'Question successfully created.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'"+DropdownQuestionTitleHOLDER1+"')]");
        logger.info("Dropdown question created");
    }

    public void customQuestionFreeText(){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Adding a free text custom question");
        logger.info("'freetext' special questions have been selected, adding a single question tom Agreements section");
        membershipPage.click("//button[contains(.,'add_circle_outline create question')]");
        membershipPage.click("//li[contains(.,'notes Free text input')]");
        membershipPage.findOnPage("//h5[contains(.,'Create question')]");
        logger.info("Check fields validation");
//        membershipPage.waitTwoSeconds();  TODO fix this field check below
//        membershipPage.click("//mat-checkbox[@class='mat-checkbox col-12 mat-accent ng-untouched ng-pristine ng-valid'][contains(.,'Restrict max number of characters')]");
//        membershipPage.waitForElementDisplayedByXpathWithTimeout("//mat-label[contains(.,'Max number of characters')]",5);
        membershipPage.click("//button[contains(.,'Create question')]");
        membershipPage.waitTwoSeconds();
        membershipPage.findOnPage("//span[@data-test='input-error'][contains(.,'Please enter Input name')]");
//        membershipPage.findOnPage("//span[@data-test='input-error'][contains(.,'Please enter Max number of characters')]");
        membershipPage.findOnPage("//span[@data-test='input-error'][contains(.,'Please enter Select section')]");
        logger.info("Fields validation ok");
        logger.info("Enter details into fields");
        FreeTextQuestionTitleHOLDER1 = "How is "+lorem.getWords(3,5)+"?";
        membershipPage.sendKeys("//input[contains(@formcontrolname,'label')]", FreeTextQuestionTitleHOLDER1);
//        membershipPage.sendKeys("//input[contains(@formcontrolname,'maxLength')]", "5");
        membershipPage.click("//mat-select[contains(@formcontrolname,'default_section_id')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Member Details')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Communications')]");
        if(isJuvenilePlan){
            membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Parent/Guardian Details')]");
        }
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Agreements')]");
        membershipPage.click("//span[@class='mat-option-text'][contains(.,'Agreements')]");
        membershipPage.click("//button[contains(.,'Create question')]");
        membershipPage.waitUntilElementInvisible("//h5[contains(.,'Create question')]",5);
        membershipPage.findOnPage("//span[contains(.,'Question successfully created.')]");
        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'"+FreeTextQuestionTitleHOLDER1+"')]");
        logger.info("Freetext question created");
    }

    public void customQuestionSingleSelect(){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Adding a single select (radio button) with a URL custom question");
        logger.info("'single select' special questions have been selected, adding a single question tom Agreements section");
        membershipPage.click("//button[contains(.,'add_circle_outline create question')]");
        membershipPage.click("//li[contains(.,'check_box Single select')]");
        membershipPage.findOnPage("//h5[contains(.,'Create question')]");
        membershipPage.click("//button[contains(.,'Create question')]");
        membershipPage.waitTwoSeconds();
//        membershipPage.findOnPage("//span[@data-test='input-error'][contains(.,'Please enter Single select')]");  https://clubforce.atlassian.net/browse/CE-834
        membershipPage.findOnPage("//span[@data-test='input-error'][contains(.,'Please enter Select section')]");
        logger.info("Fields validation ok");
        logger.info("Enter details into fields");
        SingleSelectQuestionTitleHOLDER = "Select between 2 options "+RandomStringUtils.randomNumeric(3);
        SingleSelectQuestionOption1HOLDER = lorem.getWords(3);
        SingleSelectQuestionOption2HOLDER = lorem.getWords(2);

        membershipPage.sendKeys("//input[contains(@formcontrolname,'label')]", SingleSelectQuestionTitleHOLDER);

        membershipPage.click("//div[@class='d-flex flex-grow-1 align-items-center'][contains(.,'1. Option 1')]");
        membershipPage.sendKeysToElementAbove("//button[contains(.,'add_circle_outline add option')]", "//input[contains(@type,'text')]", SingleSelectQuestionOption1HOLDER);

        membershipPage.click("//button[contains(.,'add_circle_outline add option')]");
        membershipPage.click("//div[@class='d-flex flex-grow-1 align-items-center'][contains(.,'2. Option 2')]");
        membershipPage.sendKeysToElementAbove("//button[contains(.,'add_circle_outline add option')]", "//input[contains(@type,'text')]", SingleSelectQuestionOption2HOLDER);

        logger.info("add 2 more options but leave empty");
        membershipPage.click("//button[contains(.,'add_circle_outline add option')]");
        membershipPage.click("//button[contains(.,'add_circle_outline add option')]");

        logger.info("Add a URL with some random numbers");
        membershipPage.click("//button[contains(.,'link Add URL')]");
        SingleSelectURLHOLDER = "a"+RandomStringUtils.randomNumeric(5)+".com";
        membershipPage.sendKeys("//input[contains(@formcontrolname,'external_url')]", SingleSelectURLHOLDER);
        logger.info("Remove URL");
        membershipPage.click("//mat-icon[@role='img'][contains(.,'link_off')]");
        assertFalse(membershipPage.isElementDisplayed("//input[contains(@formcontrolname,'external_url')]"));
        membershipPage.click("//button[contains(.,'link Add URL')]");
        membershipPage.sendKeys("//input[contains(@formcontrolname,'external_url')]", SingleSelectURLHOLDER);

        membershipPage.click("//mat-select[contains(@formcontrolname,'default_section_id')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Member Details')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Communications')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Parent/Guardian Details')]");
        membershipPage.findOnPage("//span[@class='mat-option-text'][contains(.,'Agreements')]");
        membershipPage.click("//span[@class='mat-option-text'][contains(.,'Member Details')]");
        membershipPage.click("//button[contains(.,'Create question')]");

        logger.info("We are prevented from saving because of the 2 more options left empty. Remove those");
        membershipPage.click("(//mat-icon[@role='img'][contains(.,'delete')])[3]");
        membershipPage.click("(//mat-icon[@role='img'][contains(.,'delete')])[3]");

        membershipPage.click("//button[contains(.,'Create question')]");
        membershipPage.waitUntilElementInvisible("//h5[contains(.,'Create question')]",5);
        membershipPage.findOnPage("//span[contains(.,'Question successfully created.')]");

        membershipPage.findOnPage("//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'"+SingleSelectQuestionTitleHOLDER+"')]");
        membershipPage.findOnPage("//span[@class='mat-radio-label-content'][contains(.,'"+SingleSelectQuestionOption1HOLDER+"')]");
        membershipPage.findOnPage("//span[@class='mat-radio-label-content'][contains(.,'"+SingleSelectQuestionOption2HOLDER+"')]");

        logger.info("Single select (radio button) question created");
    }

    public void saveAndGoBackToReviewQuestionsPage(){
        logger.info("Save changes which takes us back to Review questions page");
        membershipPage.click(MembershipPage.SaveChangesButton);
        membershipPage.waitUntilElementInvisible(MembershipPage.SaveChangesButton, 5);
        membershipPage.findOnPage(MembershipPage.ReviewQuestionsHeading);
        logger.info("We are on Review questions page");
    }

    public void setDateOfBirthDateRangesForMembershipPlan(){
        logger.info("Enable DOB range and set new plan DOB start date to 2000");
        membershipPage.clickElementLeftOf(MembershipPage.SetDateOfBirthRangeText, MembershipPage.DateOfBirthRangeCheckBox);
        membershipPage.click(MembershipPage.DateOfBirthStartDateFieldDropDownArrow);
        membershipPage.click(MembershipPage.CalenderInvertArrow);
        membershipPage.click(MembershipPage.CalenderArrow);
        membershipPage.click(MembershipPage.CalenderYear2000);
        membershipPage.click(MembershipPage.CalenderMonthDec);
        membershipPage.click(MembershipPage.CalenderDay31);
        membershipPage.escape();

        logger.info("Set new plan DOB end date to 2015");
        membershipPage.click(MembershipPage.DateOfBirthEndDateFieldDropDownArrow);
        membershipPage.click(MembershipPage.CalenderInvertArrow);
        membershipPage.click(MembershipPage.CalenderArrow);
        membershipPage.click(MembershipPage.CalenderYear2015);
        membershipPage.click(MembershipPage.CalenderMonthDec);
        membershipPage.click(MembershipPage.CalenderDay31);
        membershipPage.escape();
    }

    public void setPlanSubscriptionDates(){
        logger.info("Setting plan Subscription dates");
        String dayString = SeleniumUtilities.getDateTimeFormat("dd");
        dayNumber = Integer.parseInt(dayString);
        if (dayNumber % 2 == 0) {
            logger.info("If day is even we use a past date as the start dates");
            logger.info("Set New Plan start date to last year, June 10");
            membershipPage.scrollPageToBottom();
            membershipPage.click(MembershipPage.StartDateFieldDropDownArrow);
            membershipPage.click(MembershipPage.CalenderPreviousYearButton);
            membershipPage.click(MembershipPage.CalenderMonthJune);
            membershipPage.click(MembershipPage.CalenderDay10);
            membershipPage.escape();

            logger.info("Set New Plan end date to 2024, August 10");
            membershipPage.click(MembershipPage.EndDateFieldDropDownArrow);
            membershipPage.click(MembershipPage.CalenderNextYearButton);
            membershipPage.click(MembershipPage.CalenderNextYearButton);
        } else {
            logger.info("If day is odd we use a start date in the future");
            logger.info("Set New Plan start date to next year, June 10");
            membershipPage.scrollPageToBottom();
            membershipPage.click(MembershipPage.StartDateFieldDropDownArrow);
            membershipPage.click(MembershipPage.CalenderNextYearButton);
            membershipPage.click(MembershipPage.CalenderMonthJune);
            membershipPage.click(MembershipPage.CalenderDay10);
            membershipPage.escape();

            logger.info("Set New Plan end date to next year , August 10");
            membershipPage.click(MembershipPage.EndDateFieldDropDownArrow);
        }
        membershipPage.click(MembershipPage.CalenderMonthAugust);
        membershipPage.click(MembershipPage.CalenderDay10);
        membershipPage.escape();

        logger.info("Set new plan cutoff date to 10 July next year");
        membershipPage.click(MembershipPage.CutOffDateFieldDropDownArrow);
        membershipPage.click(MembershipPage.CalenderNextYearButton);
        membershipPage.click(MembershipPage.CalenderMonthJuly);
        membershipPage.click(MembershipPage.CalenderDay10);
        membershipPage.escape();
    }

    public void verifyPageElementsOnStep1OfMembershipPlanSetUp(){
        logger.info("Verify elements");
        membershipPage.findOnPage(MembershipPage.GeneralInformationText);
        membershipPage.waitOneSecond();
//        membershipPage.findOnPage(MembershipPage.ClubFeesWillBeChargedText);    this is hidden for now  CE-716 and CE-717
        membershipPage.scrollPageToBottom();
        membershipPage.click(MembershipPage.Step1ContinueButtonOnSetUp);

        logger.info("Verify error text for required fields");
        membershipPage.findOnPage(MembershipPage.PleaseEnterPlanNameErrorText);
        membershipPage.findOnPage(MembershipPage.PleaseEnterDescriptionErrorText);
        membershipPage.findOnPage(MembershipPage.PleaseEnterPriceErrorText);
        membershipPage.findOnPage(MembershipPage.RequiredFieldErrorText);
        membershipPage.findOnPage(MembershipPage.PleaseEnterSubscriptionStartDateErrorText);
        membershipPage.findOnPage(MembershipPage.PleaseEnterSubscriptionEndDateErrorText);
        membershipPage.findOnPage(MembershipPage.PleaseEnterSubscriptionCutOffDateErrorText);
        membershipPage.findOnPage(MembershipPage.PleaseEnterPlanType);
    }

    public void setPlanNameAndDescriptionBasedOnPlanPriceType(String planPriceType, String planType){
        logger.info("Setting plan name");
        membershipPage.scrollPageToTop();
        switch (planPriceType) {
            case "monthly instalments plan":
                logger.info("If day is odd we select 9 months instalments, if day is even we select 6 months instalments");
                String day = SeleniumUtilities.getDateTimeFormat("dd");
                int dayDate = Integer.parseInt(day);
                if (dayDate % 2 == 0) {
                    logger.info("Day is: " + dayDate + " which is even, 6 monthly instalments");
                    INSTALMENT_MONTHS_HOLDER = "6 monthly instalments";
                } else {
                    logger.info("Day is: " + dayDate + " which is odd, 9 months instalments");
                    INSTALMENT_MONTHS_HOLDER = "9 monthly instalments";
                }

                //setting plan name with instalment number
                NEW_PLAN_NAME = "New " + INSTALMENT_MONTHS_HOLDER + " plan " + RandomStringUtils.randomNumeric(5);
                membershipPage.sendKeys(MembershipPage.PlaneNameField, NEW_PLAN_NAME);
                logger.info("NEW_PLAN_NAME: " + NEW_PLAN_NAME);
                break;
            case "paid plan":
                NEW_PLAN_NAME = "New " + planType + " " + planPriceType + " " +  RandomStringUtils.randomNumeric(5);
                membershipPage.sendKeys(MembershipPage.PlaneNameField, NEW_PLAN_NAME);
                logger.info("NEW_PLAN_NAME: " + NEW_PLAN_NAME);
                break;
            case "free plan":
                NEW_PLAN_NAME = "New " + planType + " " + planPriceType + " " +  RandomStringUtils.randomNumeric(3);
                membershipPage.sendKeys(MembershipPage.PlaneNameField, NEW_PLAN_NAME);
                logger.info("NEW_PLAN_NAME: " + NEW_PLAN_NAME);
                break;
            default:
        }

        if (CommonSteps.scenarioName.contains("unPublish")){
            NEW_PLAN_NAME = NEW_PLAN_NAME+" will be unPublished";
            membershipPage.sendKeys(MembershipPage.PlaneNameField, NEW_PLAN_NAME);
            logger.info("Because this is an unPublish scenario we change name of NEW_PLAN_NAME to: " + NEW_PLAN_NAME);
        }

        logger.info("Setting plan description");
        membershipPage.sendKeys(MembershipPage.PlanDescriptionField, "Plan description for " + NEW_PLAN_NAME);
    }

    public void setPlanTypeFee(String planPriceType, String planType){
        logger.info("Add plan type " + planType);
        switch (planPriceType) {
            case "monthly instalments plan":
                //setting new plan price for instalment
                INSTALMENT_PLAN_FULL_PRICE = "4" + RandomStringUtils.randomNumeric(2);
                membershipPage.sendKeys(MembershipPage.PriceField, INSTALMENT_PLAN_FULL_PRICE);
                logger.info("Paid instalment plan price: " + INSTALMENT_PLAN_FULL_PRICE);

                logger.info("Enable instalments");
                membershipPage.scrollPageToBottom();
                membershipPage.click(MembershipPage.EnableInstalmentsButton);

                calculateInstalments();

                logger.info("Select " + INSTALMENT_MONTHS_HOLDER + " option from dropdown");
                membershipPage.click("//*[contains(text(),'"+INSTALMENT_MONTHS_HOLDER+"')]");

                INSTALMENT_PLAN_PAY_NOW_PRICE = membershipPage.getElementAttribute("(//span[contains(.,'"+INSTALMENT_MONTHS_HOLDER+" -')])[2]", "innerText");
//                INSTALMENT_PLAN_PAY_NOW_PRICE = membershipPage.getElementAttribute("//section[@class='d-flex justify-content-between py-2 ng-star-inserted'][contains(.,'" + INSTALMENT_MONTHS_HOLDER + " Instalment amount:')]", "innerText");
//                INSTALMENT_PLAN_PAY_NOW_PRICE = INSTALMENT_PLAN_PAY_NOW_PRICE.substring(41, 43);
                logger.info("INSTALMENT_PLAN_PAY_NOW_PRICE: " + INSTALMENT_PLAN_PAY_NOW_PRICE);
                break;
            case "paid plan":
                logger.info("Add price");
                PLAN_PRICE = "2" + RandomStringUtils.randomNumeric(2) + "." + RandomStringUtils.randomNumeric(2);
                membershipPage.sendKeys(MembershipPage.PriceField, PLAN_PRICE);
                logger.info("Paid plan price no instalments" + PLAN_PRICE);
                break;
            case "free plan":
                membershipPage.click(MembershipPage.PlanPriceTypeDropdownPaidSelected);
                membershipPage.click(MembershipPage.FreePlanOption);
                PLAN_PRICE = "0.00";
                break;
            default:
        }
    }

    public void waitForMembershipPageToFinishLoading(){
        logger.info("Wait for page to load properly");
        membershipPage.waitThreeSeconds();
        if (membershipPage.isElementDisplayed(MembershipPage.ProgressLoadingBar)) {
            logger.info("Membership plans page still loading, waiting");
            membershipPage.waitUntilElementInvisible(MembershipPage.ProgressLoadingBar, 60);
            membershipPage.waitTwoSeconds();
            logger.info("Membership plans page finished loading");
        }
    }

    public void signOutOfAccountOnStep1MembershipPlanFlow(){
        logger.info("Sign out of account");
        membershipPage.click(MembershipPage.AccountProfileCircleIcon);
        membershipPage.click(MembershipPage.SignOutOptionButton);
        membershipPage.waitUntilElementInvisible(MembershipPage.YouHaveBeenLoggedOutNotification, 10);
        membershipPage.findOnPage(MembershipPage.Step1SelectPlansHeading);
    }

    public void getMembershipLinkOnMembershipPlansPage(){
        membershipPage.waitForElementDisplayedByXpathWithTimeout(MembershipPage.MembershipPlansHeading,15);
        assertFalse(membershipPage.isElementDisplayed(MembershipPage.YouDontHavePublishedPlansYetText));
        logger.info("Going from membership plans to purchase page");
        BuyPlanURLHOLDER = membershipPage.getElementAttribute("//h3[contains(.,'://')]", "textContent");
        logger.info("BuyPlanURLHOLDER: " + BuyPlanURLHOLDER);

        if (envName.contains("local")) {
            String BuyPlanClubName = membershipPage.getElementAttribute("//strong[contains(.,'Membership')]", "textContent");
            BuyPlanURLHOLDER = "http://"+BuyPlanClubName+".development.local:4100/products/memberships/memberships";
        }
    }
}
