package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class SuperUserPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public SuperUserPage (WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String SUClubSearchField = "//input[contains(@id,'mat-input-2')]";
    public static final String SUAccountPageLoadingState = "//div[@class='loader ng-star-inserted']";
    public static final String SUEmptyStateText = "//h2[contains(.,'No accounts data yet')]";
    public static final String SUAccountUpdateHeading = "//h2[contains(.,'Update account')]";
    public static final String SUClubNameField = "//input[@formcontrolname='name']";
    public static final String SUNewAccountButton = "//*[contains(text(),'New Account')]";
    public static final String SUCreateAccountHeading = "//h2[contains(.,'Create account')]";
    public static final String SUCreateButton = "//button[contains(.,'Create account arrow_forward')]";
    public static final String FixIssuesAndSubmitAgainErrorText = "//*[contains(.,'Error! Please fix the issues and submit again.')]";
    public static final String EnterInNameErrorText = "//mat-error[contains(.,'Please enter Name')]";
    public static final String EnterEmailErrorText1 = "(//mat-error[contains(.,'Please enter Email')])[1]";
    public static final String SelectCountryErrorText = "//mat-error[contains(.,'Please select Country')]";
    public static final String EnterInCountyErrorText = "//mat-error[contains(.,'Please enter County')]";
    public static final String EnterInCityErrorText = "//mat-error[contains(.,'Please enter City')]";
    public static final String EnterInFirstNameErrorText = "//mat-error[contains(.,'Please enter First name')]";
    public static final String EnterInLastNameErrorText = "//mat-error[contains(.,'Please enter Last name')]";
    public static final String EnterEmailErrorText2 = "(//mat-error[contains(.,'Please enter Email')])[2]";
    public static final String ClubNameField = "//input[@formcontrolname='name']";
    public static final String SUGoverningBodiesDropDownField = "//mat-form-field[@appearance='outline'][contains(.,'Governing bodies')]";
    public static final String SUClubSportsAssocDropdownSports = "(//div[contains(.,'Sports')])[15]";
    public static final String SUClubContactDetailsMail = "(//input[contains(@formcontrolname,'email')])[1]";
    public static final String SUClubContactDetailsPhone = "(//input[contains(@formcontrolname,'phone')])[1]";
    public static final String SUClubContactDetailsWebsite = "//input[contains(@formcontrolname,'website')]";
    public static final String SUClubAddressStreet = "//input[contains(@formcontrolname,'line1')]";
    public static final String SUClubAddressCity = "//input[contains(@formcontrolname,'city')]";
    public static final String SUClubAddressCounty = "//input[contains(@formcontrolname,'county')]";
    public static final String SUClubAddressPostCode = "//input[contains(@formcontrolname,'code')]";
    public static final String SUClubAddressCountry = "//*[contains(@formcontrolname,'country')]";
    public static final String SUClubSocialMediaFB = "//input[contains(@formcontrolname,'facebook')]";
    public static final String SUClubSocialMediaInsta = "//input[contains(@formcontrolname,'instagram')]";
    public static final String SUClubSocialMediaTwitter = "//input[contains(@formcontrolname,'twitter')]";
    public static final String SUClubRepresentativeFirstName = "//input[contains(@formcontrolname,'first_name')]";
    public static final String SUClubRepresentativeLastName = "//input[contains(@formcontrolname,'last_name')]";
    public static final String SUClubRepresentativeMail = "(//input[contains(@formcontrolname,'email')])[2]";
    public static final String SUClubRepresentativePhone = "(//input[contains(@formcontrolname,'phone')])[2]";
    public static final String SUClubRepresentativeDropdown = "(//div[contains(.,'Clubforce account manager')])[14]";
    public static final String NoneAssignedOptionText = "//span[contains(.,'None assigned')]";
    public static final String SUAddNewUserTitle = "//h2[contains(.,'Add new user')]";
    public static final String SUCreateUserButton = "//button[contains(.,'Create user')]";
    public static final String SU_UsersStepper = "//strong[@class='text-uppercase text-muted'][contains(.,'users')]";
    public static final String SUWebsiteStepper = "//strong[@class='text-uppercase text-muted'][contains(.,'website')]";
    public static final String SUValidateSubdomainButton = "//button[contains(.,'validate subdomain')]";
    public static final String SUCreateSubdomainButton = "//button[contains(.,'create')]";
    public static final String SU_ClubWebsiteCreationSubDomainField = "//input[contains(@formcontrolname,'slug')]";
    public static final String SubdomainNotAvailableErrorText ="//mat-error[contains(.,'Subdomain not available!')]";
    public static final String WebsiteSuccessfullyCreatedNotification = "//span[contains(.,'Website successfully created')]";
    public static final String SUFeaturesStepper = "//strong[@class='text-uppercase text-primary'][contains(.,'features')]";
    public static final String LottoFeatureToggle = "//span[@class='mat-slide-toggle-content'][contains(.,'Lotto')]";
    public static final String PaymentProviderFeatureToggle = "//span[@class='mat-slide-toggle-content'][contains(.,'Payment Provider')]";
    public static final String CheckoutFeatureToggle = "//span[@class='mat-slide-toggle-content'][contains(.,'Checkout')]";
    public static final String MembershipFeatureToggle = "//span[@class='mat-slide-toggle-content'][contains(.,'Membership')]";
    public static final String ComortaisFeatureToggle = "//span[@class='mat-slide-toggle-content'][contains(.,'Comortais')]";
    public static final String SUFeaturesUpdateButton = "//button[contains(.,'Update')]";
    public static final String FeaturesUpdatedSuccessfullyNotification = "//span[contains(.,'Features updated successfully.')]";
    public static final String AddNewUserButtonOnStep2InSU = "//button[contains(.,'add_circle_outline Add new user')]";
    public static final String MembershipTransactionFeeFieldTitle = "//span[contains(.,'Membership transaction fee')]";
    public static final String MembershipChargeAmountFieldTitle = "//span[@class='small'][contains(.,'Card charge amount')]";
    public static final String MembershipFeatureToggleOFF = "//label[@class='mat-slide-toggle-label'][contains(.,'Membership')]//input[contains(@aria-checked,'false')]";
    public static final String MembershipFeatureToggleON = "//label[@class='mat-slide-toggle-label'][contains(.,'Membership')]//input[contains(@aria-checked,'true')]";
    public static final String CheckoutFeatureToggleOFF = "//label[@class='mat-slide-toggle-label'][contains(.,'Checkout')]//input[contains(@aria-checked,'false')]";
    public static final String CheckoutFeatureToggleON = "//label[@class='mat-slide-toggle-label'][contains(.,'Checkout')]//input[contains(@aria-checked,'true')]";
    public static final String PaymentProviderFeatureToggleOFF = "//label[@class='mat-slide-toggle-label'][contains(.,'Payment Provider')]//input[contains(@aria-checked,'false')]";
    public static final String PaymentProviderFeatureToggleON = "//label[@class='mat-slide-toggle-label'][contains(.,'Payment Provider')]//input[contains(@aria-checked,'true')]";
    public static final String MembershipToggleOFF = "//label[@class='mat-slide-toggle-label'][contains(.,'Membership')]//input[contains(@aria-checked,'false')]";
    public static final String MembershipToggleON = "//label[@class='mat-slide-toggle-label'][contains(.,'Membership')]//input[contains(@aria-checked,'true')]";
    public static final String EditFeesButton = "//button[contains(.,'Edit fees')]";
    public static final String EditFeesPopUpHeading = "//h2[contains(.,'Edit membership fees')]";
    public static final String SUSearchField = "//input[contains(@filtername,'name')]";
    public static final String AllStatusFilter = "(//span[contains(.,'All')])[2]";
    public static final String CompleteStatusInDropDown = "//span[@class='mat-option-text'][contains(.,'Complete')]";
    public static final String CompleteStatusInRow1OfTable = "(//span[@data-toggle='tooltip'][contains(.,'Complete')])[10]";
    public static final String SUAccountSearchHeading = "//h1[contains(.,'Account search')]";
    public static final String Step2TileTextSU = "(//mat-icon[@role='img'][contains(.,'edit')])[2]";
    public static final String Step3TileTextSU = "(//mat-icon[@role='img'][contains(.,'edit')])[3]";
    public static final String Step4TileTextSU = "(//mat-icon[@role='img'][contains(.,'edit')])[4]";
    public static final String TransactionFeePercentageFieldPopUp = "//input[contains(@formcontrolname,'transactionFee')]";
    public static final String ChargeAmountFieldPopUp = "//input[contains(@formcontrolname,'cardCharge')]";
    public static final String UpdateFeesButton = "//button[contains(.,'update fees')]";
    public static final String MembershipFeesUpdatedSuccessfullyNotification = "//span[contains(.,'Membership fees updated successfully.')]";
    public static final String EuroSymbolSUAccounts = "//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'€')]";
    public static final String PoundSymbolSUAccounts = "//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'£')]";
    public static final String NACurrencySUAccounts = "//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'n/a')]";
    public static final String AccountFeaturesHeading = "//h1[contains(.,'Account features')] | //h2[contains(.,'Account features')]";
}
