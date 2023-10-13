package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class MembershipRegistrationPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public MembershipRegistrationPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String RegistrationUserSearchField = "(//input[contains(@aria-invalid,'false')])[1]";
    public static final String RegistrationGenderText = "//div[@class='text-muted'][contains(.,'Gender')]";
    public static final String RegistrationMemberIDText = "//div[@class='text-muted'][contains(.,'Member ID')]";
    public static final String RegistrationEmailText = "//div[@class='text-muted'][contains(.,'Email')]";
    public static final String RegistrationAddressText = "//div[@class='text-muted'][contains(.,'Address')]";
    public static final String RegistrationPhoneText = "//div[@class='text-muted'][contains(.,'Phone')]";
    public static final String RegistrationDetailsButton = "//a[contains(.,'Registration details')]";
    public static final String RegistrationDetailsButtonBackoffice = "//a[@data-test='registration-card.registration-details'][contains(.,'Registration Details')]";
    public static final String RegistrationDetailsHeading = "//h1[contains(.,'Registration details')]";
    public static final String PlanDetailsHeading = "//h2[contains(.,'Plan Details')]";
    public static final String PlanDetailsHeadingMyAccount = "//h2[contains(.,'Plan details')]";
    public static final String MemberDetailsHeading = "//h2[contains(.,'Member Details')]";
    public static final String CommunicationsHeading = "//h2[contains(.,'Communications')]";
    public static final String EditRegistrationButton = "//button[contains(.,'edit registration details')]";
    public static final String EditRegistrationText = "//*[contains(text(),'Edit registration details')]";
    public static final String RegistrationFormPhoneField = "//input[@data-test='dynamic-form-text-field.phoneNumber']";
    public static final String RegistrationFormTownField = "//input[@data-test='dynamic-form-text-field.town']";
    public static final String InvalidPhoneNumberErrorText = "//mat-error[@aria-atomic='true'][contains(.,'Maximum length exceeded for Phone number')]";
    public static final String CancelButton = "//button[contains(.,'Cancel')]";
    public static final String RegistrationFormContactNameField = "//input[@data-test='dynamic-form-text-field.membersEmergencyContactName']";
    public static final String RegistrationFormContactNumberField = "//input[@data-test='dynamic-form-text-field.membersEmergencyContactNumber']";
    public static final String UpdateRegistrationButton = "//button[contains(.,'update registration')]";
    public static final String RegistrationFormClubNameText = "//p[contains(.,'Club name')]";
    public static final String RegistrationFormPlanNameText = "//p[contains(.,'Plan name')]";
    public static final String RegistrationFormSubmittedOnText = "//p[contains(.,'Submitted on')]";
    public static final String RegistrationFormStatusText = "//p[contains(.,'Status')]";
    public static final String RegistrationFormDOBText = "//p[contains(.,'DOB')]";
    public static final String RegistrationFormACTIVEText = "//div[@data-test='membership-tile.status'][contains(.,'Active')]";
    public static final String RegistrationFormRegistrationIDText = "//p[contains(.,'Registration ID')]";
    public static final String RegistrationFormFirstNameTextForJuvenile = "(//p[contains(.,'First name')])[1]";
    public static final String RegistrationFormFirstNameText = "//p[contains(.,'First name')]";
    public static final String RegistrationFormLastNameText = "//p[contains(.,'Last name')]";
    public static final String RegistrationFormPhoneNumberText = "//p[contains(.,'Phone number')]";
    public static final String RegistrationFormEmailText = "//p[contains(.,'Email')]";
    public static final String RegistrationFormStreetAddressText = "//p[contains(.,'Street address')]";
    public static final String RegistrationFormTownText = "//p[contains(.,'Town')]";
    public static final String RegistrationFormPostcodeText = "//p[contains(.,'Postcode')]";
    public static final String RegistrationFormCountyText = "//p[contains(.,'County')]";
    public static final String RegistrationFormCountryText = "//p[contains(.,'Country')]";
    public static final String RegistrationFormLastNameTextForJuvenile = "(//p[contains(.,'Last name')])[1]";
    public static final String RegistrationFormGenderTextForJuvenile = "(//p[contains(.,'Gender')])[1]";
    public static final String RegistrationFormGenderText = "//p[contains(.,'Gender')]";
    public static final String RegistrationFormClubContactConsentText = "//p[contains(.,'I consent to be contacted by my club')]";
    public static final String RegistrationFormPreferredCommunicationMethodText = "//p[contains(.,'What is your preferred method of communication?')]";
    public static final String RegistrationFormConsentForGoverningBodyText = "//p[contains(.,'I consent for member information to be retained by the Club and submitted to the relevant Governing Body for administrative and statistical purposes for such period as the membership subsists')]";
    public static final String RegistrationFormParentGuardianHeading = "//h2[contains(.,'Parent/Guardian Details')]";
    public static final String AgreementsHeading = "//h2[contains(.,'Agreements')]";
    public static final String MembershipHeading = "//h1[contains(.,'Membership')]";
    public static final String ActivePlansHeading = "//h2[contains(.,'Active plans')]";
    public static final String PlanTpeText = "//p[contains(.,'Plan type')]";
    public static final String PriceText = "//p[contains(.,'Price')]";
    public static final String PlanStatusText = "//p[contains(.,'Plan status')]";

}
