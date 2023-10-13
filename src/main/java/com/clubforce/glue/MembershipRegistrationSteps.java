package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.MembershipRegistrationPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

public class MembershipRegistrationSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();
    WebDriverManager driverManager;

    public MembershipRegistrationSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.membershipRegistrationPage = driverManager.membershipRegistrationPage;
        this.membershipPage = driverManager.membershipPage;
    }

    @And("ClubAdmin searches for {string} on Search page")
    public void searchRegistrationsPage(String name) {
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationUserSearchField, name);
        membershipRegistrationPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'" + name + "')])[1]");
    }

    @And("Membership is displayed on membership page in MyAccount")
    public void checkActiveMembershipOnMembershipPage() {
        membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout(MembershipRegistrationPage.MembershipHeading, 60);
        membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout(MembershipRegistrationPage.ActivePlansHeading, 60);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationDetailsButton);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.PlanTpeText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormSubmittedOnText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.PriceText);
        membershipRegistrationPage.findOnPage("//span[@data-test='membership-tile.price'][contains(.,'"+MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER+"')]");
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.PlanStatusText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormACTIVEText);
    }

    @And("registration details are displayed for {string}")
    public void checkDetailsForRegistration(String name) {
        membershipRegistrationPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'" + name + "')])[1]");
        membershipRegistrationPage.click("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'" + name + "')])[1]");
        membershipRegistrationPage.findOnPage("//h2[contains(.,'" + name + "')]");
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationGenderText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationMemberIDText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationEmailText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationAddressText);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationPhoneText);
        membershipRegistrationPage.click(MembershipRegistrationPage.RegistrationDetailsButtonBackoffice);
        membershipRegistrationPage.waitOneSecond();
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationDetailsHeading);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.PlanDetailsHeading);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.MemberDetailsHeading);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.CommunicationsHeading);
    }

    @And("ClubAdmin can edit registration details")
    public void caEditsRegistrationDetails() {
        Lorem lorem = LoremIpsum.getInstance();

        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationDetailsHeading);
        membershipRegistrationPage.click(MembershipRegistrationPage.EditRegistrationButton);
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.EditRegistrationText);

        String PhoneNumberHolder = membershipRegistrationPage.getElementAttribute(MembershipRegistrationPage.RegistrationFormPhoneField, "value");
        String TownHolder = membershipRegistrationPage.getElementAttribute(MembershipRegistrationPage.RegistrationFormTownField, "value");
        logger.info("PhoneNumberHolder "+PhoneNumberHolder);
        logger.info("TownHolder "+TownHolder);

        logger.info("Testing validation on phone field");
        String phoneDigits = RandomStringUtils.randomNumeric(26);
        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormPhoneField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormPhoneField, phoneDigits);
//        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.InvalidPhoneNumberErrorText); TODO uncomment when https://clubforce.atlassian.net/browse/CE-1192 is fixed

        logger.info("Adding new details and press cancel");
        MembershipSteps.REG_DET_PHONE = RandomStringUtils.randomNumeric(10);
        MembershipSteps.REG_DET_TOWN = lorem.getCity();
        MembershipSteps.REG_DET_CONTACT_NAME = lorem.getFirstName()+" "+lorem.getLastName();
        MembershipSteps.REG_DET_CONTACT_NUMBER = RandomStringUtils.randomNumeric(10);

        logger.info("REG_DET_PHONE " +  MembershipSteps.REG_DET_PHONE);
        logger.info("REG_DET_TOWN" +  MembershipSteps.REG_DET_TOWN);
        logger.info("REG_DET_CONTACT_NAME " +  MembershipSteps.REG_DET_CONTACT_NAME);
        logger.info("REG_DET_CONTACT_NUMBER " +  MembershipSteps.REG_DET_CONTACT_NUMBER);

        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormPhoneField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormPhoneField,  MembershipSteps.REG_DET_PHONE);
        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormTownField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormTownField,  MembershipSteps.REG_DET_TOWN);
        membershipRegistrationPage.click(MembershipRegistrationPage.CancelButton);

        membershipRegistrationPage.click(MembershipRegistrationPage.EditRegistrationButton);
        membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'"+PhoneNumberHolder+"')]",5);
        membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'"+TownHolder+"')]",5);

        logger.info("Adding new details and press Update");
        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormPhoneField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormPhoneField,  MembershipSteps.REG_DET_PHONE);
        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormTownField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormTownField,  MembershipSteps.REG_DET_TOWN);
        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormContactNameField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormContactNameField,  MembershipSteps.REG_DET_CONTACT_NAME);
        membershipRegistrationPage.clearInputFieldUsingBackSpaceKey(MembershipRegistrationPage.RegistrationFormContactNumberField);
        membershipRegistrationPage.sendKeys(MembershipRegistrationPage.RegistrationFormContactNumberField,  MembershipSteps.REG_DET_CONTACT_NUMBER);
        membershipRegistrationPage.click(MembershipRegistrationPage.UpdateRegistrationButton);

        logger.info("Checking new details on webpage");
        membershipRegistrationPage.findOnPage(MembershipRegistrationPage.CommunicationsHeading);
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_PHONE+"')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_TOWN+"')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_CONTACT_NAME+"')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_CONTACT_NUMBER+"')]");

        logger.info("Checking new details on edit page");
        membershipRegistrationPage.click("//button[contains(.,'edit registration details')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_PHONE+"')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_TOWN+"')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_CONTACT_NAME+"')]");
        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.REG_DET_CONTACT_NUMBER+"')]");
    }

    @And("Registration details for {string} are displayed in MyAccount")
    public void checkRegistrationsDetails(String membershipType) {
        switch (membershipType) {
            case "juvenile plan":
                membershipRegistrationPage.centreElement("//*[contains(text(),'"+MembershipSteps.NEW_PLAN_NAME+"')]");
                membershipRegistrationPage.clickElementBelow("//*[contains(text(),'"+MembershipSteps.NEW_PLAN_NAME+"')]",MembershipRegistrationPage.RegistrationDetailsButton); membershipRegistrationPage.findOnPage("//button[contains(.,'arrow_back back')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationDetailsHeading);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.PlanDetailsHeadingMyAccount);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormClubNameText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormPlanNameText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormSubmittedOnText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormStatusText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormACTIVEText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormRegistrationIDText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.MemberDetailsHeading);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormFirstNameTextForJuvenile);
                membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.FIRST_NAME_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormLastNameTextForJuvenile);
                membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.LAST_NAME_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormDOBText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormGenderTextForJuvenile);
                logger.info("Juveniles details are displayed");

                logger.info("Checking guardians details");
                membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Parent/Guardian Details')]",5);
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'First name')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Last name')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Gender')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Email')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Phone number')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Street address')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Town')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Postcode')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'County')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.RegistrationFormParentGuardianHeading, "//*[contains(text(),'Country')]");

                logger.info("Checking guardians details, communications");
                membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout(MembershipRegistrationPage.CommunicationsHeading,5);
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.CommunicationsHeading, "//*[contains(text(),'I consent to be contacted by my club')]");
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.CommunicationsHeading, "//*[contains(text(),'What is your preferred method of communication?')]");

                logger.info("Checking guardians details, agreements");
                membershipRegistrationPage.waitForElementDisplayedByXpathWithTimeout(MembershipRegistrationPage.AgreementsHeading,5);
                membershipRegistrationPage.findElementBelow(MembershipRegistrationPage.AgreementsHeading, "//*[contains(text(),'I consent for member information to be retained by the Club and submitted to the relevant Governing Body for administrative and statistical purposes for such period as the membership subsists')]");
                break;
            case "standard plan":
                membershipRegistrationPage.centreElement("//*[contains(text(),'"+MembershipSteps.NEW_PLAN_NAME+"')]");
                membershipRegistrationPage.clickElementBelow("//*[contains(text(),'"+MembershipSteps.NEW_PLAN_NAME+"')]",MembershipRegistrationPage.RegistrationDetailsButton);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationDetailsHeading);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.PlanDetailsHeadingMyAccount);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormClubNameText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormPlanNameText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormSubmittedOnText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormStatusText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormACTIVEText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormRegistrationIDText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.MemberDetailsHeading);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormFirstNameText);
//            membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.FIRST_NAME_PLAN1+"')]"); TODO fix these Magnus
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormLastNameText);
//              membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.LAST_NAME_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormPhoneNumberText);
//            membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.PHONE_NUMBER_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormEmailText);
//            membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.EMAIL_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormDOBText );
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormStreetAddressText);
//            membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.STREET_ADDRESS_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormTownText);
//                membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.TOWN_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormPostcodeText);
//            membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.POSTCODE_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormCountyText);
//               membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.COUNTY_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormCountryText);
//          membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.COUNTRY_PLAN1+"')]");
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormGenderText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.CommunicationsHeading);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormClubContactConsentText);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormPreferredCommunicationMethodText);

                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.AgreementsHeading);
                membershipRegistrationPage.findOnPage(MembershipRegistrationPage.RegistrationFormConsentForGoverningBodyText);
//              membershipRegistrationPage.findOnPage("//p[contains(.,'Please confirm you have read & agree to our Terms & Conditions')]");

                if (MembershipSteps.specialQuestionsFlagHOLDER.contains("true")) {
                    logger.info("There is a custom question that was answered, checking");
                    if (MembershipSteps.specialQuestionsFlagHOLDER.contains("freetext")) {
                        logger.info("There is a freetext question to check");
                        membershipRegistrationPage.findOnPage("//*[contains(text(),'" + MembershipSteps.FreeTextQuestionTitleHOLDER1 + "')]");
                        membershipRegistrationPage.findOnPage("//*[contains(text(),'" + MembershipSteps.FreeTextQuestionAnswerHOLDER1 + "')]");
                        logger.info("Found freetext question: "+MembershipSteps.FreeTextQuestionTitleHOLDER1);
                        logger.info("Found freetext answer: "+MembershipSteps.FreeTextQuestionAnswerHOLDER1);
                    }

                    if (MembershipSteps.specialQuestionsFlagHOLDER.contains("dropdown")) {
                        logger.info("There is a dropdown question to check");
                        membershipRegistrationPage.findOnPage("//*[contains(text(),'" + MembershipSteps.DropdownQuestionTitleHOLDER1 + "')]");
                        membershipRegistrationPage.findOnPage("//*[contains(text(),'" + MembershipSteps.DropdownQuestionFirstNameHOLDER2 + "')]");
                        logger.info("Found dropdown title: "+MembershipSteps.DropdownQuestionTitleHOLDER1);
                        logger.info("Found dropdown answer: "+MembershipSteps.DropdownQuestionFirstNameHOLDER2);
                    }

                    if (MembershipSteps.specialQuestionsFlagHOLDER.contains("singleSelect")) {
                        logger.info("There is a singleSelect question to check");
                        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.SingleSelectQuestionTitleHOLDER+"')]");
                        membershipRegistrationPage.findOnPage("//*[contains(text(),'"+MembershipSteps.SingleSelectQuestionOption2HOLDER+"')]");
                    }
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkRegistrationsDetails!");
        }
    }
}
