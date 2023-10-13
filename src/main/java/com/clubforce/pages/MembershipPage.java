package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.glue.MembershipSteps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.*;

public class MembershipPage extends SeleniumUtilities {

    //logger
    private static final Logger logger = LogManager.getLogger();

    public WebDriverManager driverManager;

    public MembershipPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //Links
    public static final String NewBundleButton = "//button[@routerlink='/memberships/create-bundle'][contains(.,'new bundle')]";
    public static final String NextStepButton = "//button[contains(.,'next step')]";
    public static final String CancelButton = "//button[contains(.,'cancel')]";
    public static final String ModalCancelButton = "//span[contains(@id,'dialog--cancel')]";
    public static final String ModalDiscardButton = "//span[contains(@id,'dialog--confirm')]";
    public static final String DatePicker1 = "(//mat-icon[contains(.,'calendar_today')])[1]";
    public static final String DatePicker2 = "(//mat-icon[contains(.,'calendar_today')])[2]";
    public static final String ConfirmAndPublishButton = "//button[contains(.,'Confirm & Publish')]";
    public static final String MembershipPlansHeading = "//h1[contains(.,'Membership plans')]";
    public static final String ProductMembershipHeading = "//h3[contains(.,'products/membership')]";
    public static final String MemberDetailsText = "//div[@class='fw-bold'][contains(.,'Member Details')]";
    public static final String GenderQuestionOnForm = "//span[@class='pb-2'][contains(.,'Gender')]";
    public static final String MaleGenderCheckboxOption = "//span[@class='mat-radio-label-content'][contains(.,'Male')]";
    public static final String FirstNameFormField = "//input[@data-test='dynamic-form-text-field.firstName']";
    public static final String NextButton = "//button[contains(.,'Next arrow_forward')]";
    public static final String AddCircleIconInSummaryBox = "//button[@class='btn p-0 text-primary'][contains(.,'add_circle')]";
    public static final String AddMemberButtonOnPopUpForm = "//*[contains(text(),'Add Member')]";
    public static final String YouDontHavePublishedPlansYetText = "//*[contains(text(),'You don't have any published plans yet')]";
    public static final String AccountProfileCircleIcon = "(//mat-icon[contains(.,'account_circle')])[1]";
    public static final String SignOutOptionButton = "//span[contains(.,'Sign out')]";
    public static final String YouHaveBeenLoggedOutNotification = "//span[contains(.,'You have been logged out.')]";
    public static final String Step1SelectPlansHeading = "//div[@class='border-bottom bg-white p-3'][contains(.,'Step 1: Select plans')]";
    public static final String PoweredByClubforceFooterText = "//p[contains(.,'Powered by Clubforce')]";
    public static final String FreePriceText = "//*[contains(text(),'Free')]";
    public static final String ValidFrom170323To111123Dates = "//div[@data-test='bundle-detail.date'][contains(.,'Valid from:  17/03/23 to: 11/11/23')]";
    public static final String ProgressLoadingBar = "//span[contains(@role,'progressbar')]";
    public static final String NewPlanButton = "//button[contains(.,'new plan')]";
    public static final String ClubFeesWillBeChargedText = "//span[contains(.,'Clubforce fees will be charged')]";
    public static final String Step1ContinueButtonOnSetUp = "//button[contains(.,'continue')]";
    public static final String PleaseEnterPlanNameErrorText = "//div[@class='ng-star-inserted'][contains(.,'Please enter Plan name')]";
    public static final String PleaseEnterDescriptionErrorText = "//div[@class='ng-star-inserted'][contains(.,'Please enter Description')]";
    public static final String PleaseEnterPriceErrorText  = "//div[@class='ng-star-inserted'][contains(.,'Please enter Price')]";
    public static final String RequiredFieldErrorText = "//mat-error[contains(.,'Required field')]";
    public static final String PleaseEnterSubscriptionStartDateErrorText = "//div[@class='ng-star-inserted'][contains(.,'Please enter Subscription Start Date')]";
    public static final String PleaseEnterSubscriptionEndDateErrorText = "//div[@class='ng-star-inserted'][contains(.,'Please enter Subscription End Date')]";
    public static final String PleaseEnterSubscriptionCutOffDateErrorText = "//div[@class='ng-star-inserted'][contains(.,'Please enter Subscription Cutoff Date')]";
    public static final String PleaseEnterPlanType = "//span[@data-test='input-error'][contains(.,'Please enter Plan type')]";
    public static final String PlaneNameField = "//input[contains(@formcontrolname,'name')]";
    public static final String PlanDescriptionField = "//textarea[contains(@type,'text')]";
    public static final String CalenderDay10 = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'10')]";
    public static final String CalenderDay31 = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'31')]";
    public static final String CalenderMonthJune = "//*[contains(text(),'JUN')]";
    public static final String CalenderMonthJuly = "//*[contains(text(),'JUL')]";
    public static final String CalenderMonthAugust = "//*[contains(text(),'AUG')]";
    public static final String CalenderMonthDec = "//*[contains(text(),'DEC')]";
    public static final String CalenderNextYearButton = "//button[@aria-label='Next year']";
    public static final String CalenderPreviousYearButton = "//button[contains(@aria-label,'Previous year')]";
    public static final String CutOffDateFieldDropDownArrow = "(//mat-icon[@role='img'][contains(.,'keyboard_arrow_down')])[3]";
    public static final String EndDateFieldDropDownArrow = "(//mat-icon[@role='img'][contains(.,'keyboard_arrow_down')])[2]";
    public static final String StartDateFieldDropDownArrow = "(//mat-icon[contains(.,'keyboard_arrow_down')])[1]";
    public static final String DateOfBirthStartDateFieldDropDownArrow = "(//mat-icon[@role='img'][contains(.,'keyboard_arrow_down')])[4]";
    public static final String DateOfBirthEndDateFieldDropDownArrow = "(//mat-icon[@role='img'][contains(.,'keyboard_arrow_down')])[5]";
    public static final String SubscriptionField = "//input[contains(@formcontrolname,'max')]";
    public static final String FormTemplateField = "(//div[contains(.,'Form template')])[21]";
    public static final String StandardFormTemplate = "//span[@class='mat-option-text'][contains(.,'Standard')]";
    public static final String SetDateOfBirthRangeText = "//span[@class='mat-checkbox-label'][contains(.,'Set date of birth range?')]";
    public static final String DateOfBirthRangeCheckBox = "//span[contains(@class,'mat-checkbox-inner-container')]";
    public static final String CalenderInvertArrow = "//div[contains(@class,'mat-calendar-arrow mat-calendar-invert')]";
    public static final String CalenderArrow = "//div[contains(@class,'mat-calendar-arrow')]";
    public static final String CalenderYear2000 = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2000')]";
    public static final String CalenderYear2015 = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2015')]";
    public static final String PriceField = "//input[contains(@formcontrolname,'price')]";
    public static final String EnableInstalmentsButton = "//button[contains(.,'enable instalments')]";
    public static final String PlanPriceTypeDropdownPaidSelected = "(//span[contains(.,'Paid plan')])[2]";
    public static final String FreePlanOption = "//span[@class='mat-option-text'][contains(.,'Free plan')]";
    public static final String JuvenilePlanRadioButton = "//span[@class='mat-radio-label-content'][contains(.,'Juvenile')]";
    public static final String AdultPlanRadioButton = "//span[@class='mat-radio-label-content'][contains(.,'Adult')]";
    public static final String PleaseEnterErrorText = "//span[@data-test='stepEditForms.name-error'][contains(.,'Please enter')]";
    public static final String GeneralInformationText = "//*[contains(text(),'General information')]";
    public static final String ReviewQuestionsHeading = "//h2[contains(.,'Review questions')]";
    public static final String SaveChangesButton = "//button[contains(.,'Save changes')]";
    public static final String ClubforceFeesLink = "//a[contains(.,'Clubforce fees')]";
    public static final String ContinueButtonOnWebsiteStep1Membership = "//button[@data-test='product-summary.continue'][contains(.,'continue')]";
    public static final String AddQuestionButton = "//button[contains(.,'add_circle_outlineAdd Question')]";
    public static final String AddQuestionHeading = "//h1[contains(.,'Add question')]";
    public static final String QuestionSearchField = "//input[contains(@data-test,'step-form-add-question.questionsSearch')]";
    public static final String EmergencyContactNameFormQuestion = "//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'emergency contact name')]";
    public static final String EmergencyContactNumberFormQuestion = "//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'emergency contact number')]";
    public static final String PlayingKitSizeFormQuestion = "//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'Playing kit size')]";
    public static final String FirstNameFormQuestion = "//div[@class='pt-3 text-break ng-star-inserted'][contains(.,'First name')]";
    public static final String IncludeButton = "//button[contains(.,'include')]";
    public static final String PlayingKitSizeTextOnForm = "//strong[contains(.,'Playing kit size')]";
    public static final String DeleteIcon = "//mat-icon[contains(.,'delete')]";
    public static final String ContinueButtonOnStep2OfSetup = "//button[@data-test='stepEditForms.step2-continueButton']";
    public static final String YesDeleteButton = "//span[contains(@id,'dialog--confirm')]";
    public static final String PlayingKitSizeQuestionInDeletePopUp = "(//strong[contains(.,'Playing kit size')])[2]";
    public static final String MembershipAddToCartButton = "//button[contains(.,'Add to Cart')]";

    public static final String DashboardViewMembershipPlansGroupButton = "//button[@data-test='backoffice-dashboard.view-membership-btn'][contains(.,'View membership plans group')]";







    //Method
    public void checkAndFillAdultDOB() {
        if (isElementDisplayed("//*[@data-test='dynamic-form-date-field.dob'][contains(.,'')]")) {
            String PageDOB = getElementAttribute("//*[@data-test='dynamic-form-date-field.dob'][contains(.,'')]", "value");

            logger.info("DOB is " + PageDOB);

            if (!PageDOB.contains("/")) {
                logger.info("Since DOB is not a date - we fill in a date");
                click("//button[@aria-label='Open calendar']");
                click("//span[contains(@id,'mat-calendar-button')]");
                click("//span[contains(@id,'mat-calendar-button')]");

                waitThreeSeconds();
                if (!isElementDisplayed("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2002')]")) {
                    click("//button[@aria-label='Previous 24 years']");
                    waitTwoSeconds();
                }

                assertTrue(isElementDisplayed("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2002')]"));
                click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2002')]");
                click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'JUN')]");
                click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'22')]");
            }
        }
    }

    public void checkAndFillAgreements() {
        if (isElementDisplayed("//div[@class='fw-bold'][contains(.,'Agreements')]")) {
            logger.info("Agreements section found, filling");
            clickElementBelow("//span[contains(.,'I consent for member information to be retained by the Club and submitted to the relevant Governing Body for administrative and statistical purposes for such period as the membership subsists')]", "//span[@class='mat-checkbox-label'][contains(.,'I consent')]");
        }
    }

    public void checkAndFillCommunications() {
        if (isElementDisplayed("//div[@class='fw-bold'][contains(.,'Communications')]")) {
            logger.info("Communications section found, filling");
            clickElementBelow("//span[contains(.,'I consent to be contacted by my club')]", "//span[@class='mat-radio-label-content'][contains(.,'Yes')]");
          clickElementBelow("//span[contains(.,'What is your preferred method of communication?')]", "(//span[contains(.,'SMS')])[1]");
//            membershipPage.clickElementBelow("//span[contains(.,'I consent to allow photo/videos to be taken of me/my child for official club use and/or promotional purposes.')]", "//span[@class='mat-radio-label-content'][contains(.,'Yes')]");

            if (isElementDisplayed("//mat-label[contains(.,'Would you be interested in volunteering for the club?')]")) {
                logger.info("'Would you be interested in volunteering for the club?' section found, filling");
                click("//span[contains(@class,'mat-select-placeholder')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Referee')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Side line assistant')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'IT / Website')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Social activities')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Event organisation')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Fundraising')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Match / Event Travel')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Refreshment for teams')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Washing jerseys')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Office admin')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'General help')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'I am already a volunteer')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Pitch or facility maintenance')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'I cannot volunteer at this time')]");
                findOnPage("//span[@class='mat-option-text'][contains(.,'Committee / Sub-committee')]");

                click("//span[@class='mat-option-text'][contains(.,'I cannot volunteer at this time')]");
            }
        }
    }

    public void checkAndFillSpecialQuestions() {
        if (!MembershipSteps.IsBundleHOLDER.contains("true")){
            if (MembershipSteps.specialQuestionsFlagHOLDER.contains("true")) {
                logger.info("There are dropdown or freetext questions to answer");

                if (MembershipSteps.specialQuestionsFlagHOLDER.contains("dropdown")) {
                    logger.info("There is a dropdown question to answer, select second option");
                    click("//div[contains(@id,'mat-select-value-1')]"); // open best name dropdown
                    waitOneSecond();

                    logger.info("DropdownQuestionFirstNameHOLDER1 = "+MembershipSteps.DropdownQuestionFirstNameHOLDER1);
                    logger.info("DropdownQuestionFirstNameHOLDER2 = "+MembershipSteps.DropdownQuestionFirstNameHOLDER2);

                    findOnPage("//span[@class='mat-option-text'][contains(.,'"+MembershipSteps.DropdownQuestionFirstNameHOLDER1+"')]");
                    findOnPage("//span[@class='mat-option-text'][contains(.,'"+MembershipSteps.DropdownQuestionFirstNameHOLDER2+"')]");
                    waitOneSecond();
                    click("//span[@class='mat-option-text'][contains(.,'"+MembershipSteps.DropdownQuestionFirstNameHOLDER2+"')]");
                    waitOneSecond();
                }

                if (MembershipSteps.specialQuestionsFlagHOLDER.contains("freetext")) {
                    logger.info("There is a freetext question to answer");
                    findOnPage("//mat-label[contains(.,'"+MembershipSteps.FreeTextQuestionTitleHOLDER1+"')]");
                    logger.info("Questions on form is "+ getElementAttribute("//mat-label[contains(.,'How is')]", "textContent"));
                    logger.info("Expected question is "+ MembershipSteps.FreeTextQuestionTitleHOLDER1);
                    assertEquals(MembershipSteps.FreeTextQuestionTitleHOLDER1,getElementAttribute("//mat-label[contains(.,'How is')]", "textContent"));

                    logger.info("Give the free text question an answer");
                    MembershipSteps.FreeTextQuestionAnswerHOLDER1 = MembershipSteps.NEW_PLAN_NAME +" freetext answer";
                    sendKeys("//input[contains(@data-test,'dynamic-form-text-field.howIs')]", MembershipSteps.FreeTextQuestionAnswerHOLDER1);
                    logger.info("Free text answer is "+MembershipSteps.FreeTextQuestionAnswerHOLDER1);
                }

                if (MembershipSteps.specialQuestionsFlagHOLDER.contains("singleSelect")) {
                    logger.info("There is a singleSelect question to answer");
                    findOnPage("//*[contains(text(),'"+MembershipSteps.SingleSelectQuestionTitleHOLDER+"')]");
                    findOnPage("//*[contains(text(),'"+MembershipSteps.SingleSelectQuestionOption1HOLDER+"')]");
                    findOnPage("//*[contains(text(),'"+MembershipSteps.SingleSelectQuestionOption2HOLDER+"')]");

                    logger.info("Click Read More URL and verify");
                    click("//a[@target='_blank'][contains(.,'Read more')]");
                    waitTwoSeconds();
                    switchToBrowserTab(1);
                    assertTrue(driverManager.driver.getCurrentUrl().contains(MembershipSteps.SingleSelectURLHOLDER));
                    switchToBrowserTab(0);
                    waitTwoSeconds();
                    logger.info("Select second radio option for value "+MembershipSteps.SingleSelectQuestionOption2HOLDER);
                    clickElementBelow("//*[contains(text(),'"+MembershipSteps.SingleSelectQuestionTitleHOLDER+"')]",
                            "//span[contains(@class,'mat-radio-outer-circle')]");
                }
            }
        }
    }
 }
