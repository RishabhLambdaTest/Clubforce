package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class xgbDashboardPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public xgbDashboardPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String XBGLoginTitle = "//h2[contains(.,'XGB Sign in')]";
    public static final String SignIn = "//button[contains(.,'Sign in')]";
    public static final String TemplatePageText = "//*[contains(text(),'%s')]";
    public static final String TemplatePageImage = "//img[contains(@src, '%s')]";
    public static final String LoginButton = "//button[contains(.,'Sign in')]";
    public static final String LoggedInSuccessMessage = "//*[contains(.,'You have successfully logged in')]";
    public static final String LoginRememberMe = "//span[contains(.,'Remember me')]";
    public static final String EnterYourMailText = "//h2[contains(.,'Enter your')]";
    public static final String CookieAcceptAll = "//a[contains(.,'Accept all')]";
    public static final String LoginEmailField = "//input[contains(@formcontrolname,'email')]";
    public static final String LoginPasswordField = "//input[contains(@formcontrolname,'password')]";
    public static final String LoginInvalidCredentialsMessage = "//mat-error[contains(.,'Invalid credentials')]";
    public static final String MyClubsTitle = "//h1[contains(.,'My Clubs')]";
    public static final String HomepageProfileIcon = "//mat-icon[contains(.,'account_circle')]";
    public static final String LoginNextButton = "//button[contains(.,'Next')]";
    public static final String LoginNextButtonInactive = "(//div[contains(.,'Next')])[3]";
    public static final String HomepageCreateAccountTitle = "//h2[contains(.,'Create Account')]";
    public static final String ForgotPasswordLink = "//a[contains(.,'Forgot Password?')]";
    public static final String RecoverPasswordMailField = "//input[contains(@autocomplete,'off')]";
    public static final String RecoverPasswordTokenField = "//input[contains(@formcontrolname,'token')]";
    public static final String RecoverPasswordTokenSubmitButton = "//button[contains(.,'Submit')]";
    public static final String RecoverPasswordButton = "//button[contains(.,'Recover password')]";
    public static final String UnexpectedErrorMessage = "//mat-error[contains(.,'Something unexpected happened')]";
    public static final String SuccessHeading = "//h1[contains(.,'Success!')]";
    public static final String YourEmailIsVerifiedText = "//span[contains(.,'Your email is verified. You can now log in to your account.')]";
    public static final String GoToSignInButton = "//span[@class='mat-button-wrapper'][contains(.,'Go to sign in')]";
    public static final String OopsHeading = "//h1[contains(.,'Oops!')]";
    public static final String YourEmailHasAlreadyBeenVerifiedText = "//span[contains(.,'Your email has already been verified, or you have arrived here in error.')]";
    public static final String WrongEmailButton = "//a[contains(.,'Wrong email?')]";
    public static final String EmailMustBeValidErrorText = "//mat-error[contains(.,'Email must be valid')]";
    public static final String CardHeader = "//div[contains(@class,'card__header')]";
    public static final String ErrorMessageText = "//mat-error[@role='alert']";
    public static final String SUAccountSearchHeading = "//h1[contains(.,'Account search')]";
    public static final String SignInHeadingText = "//h2[contains(.,'Sign in')]";
    public static final String SignInOnlyValidForExistingAdminText = "//h1[contains(.,'Sign-In is only available for existing Club Admin at this time.')]";
    public static final String ClubPageAccountCircleIcon = "//mat-icon[@role='img'][contains(.,'account_circle')]";
    public static final String MyAccountOption = "//*[contains(text(),'My Account')]";
    public static final String YourSponsorsHeading = "//h1[contains(.,'Your sponsors')]";
    public static final String GoToMyClubOption = "//span[contains(.,'Go to my club')]";
    public static final String GoToAdminOption = "//span[contains(.,'Go to club admin')]";
    public static final String DoNotHaveSufficientPermissionsErrorText = "//*[contains(.,'You do not have sufficient permissions to access this area. Please try again with a different email.')]";
    public static final String SignInOuterArea = "//h1[@class='mat-card-title']";
    public static final String EmailField = "//*[@id='mat-input-1']";
    public static final String TokenValidationHeadingText = "//h2[contains(.,'Token Validation')]";
    public static final String NextButton = "//button[contains(.,'Next arrow_forward')]";
    public static final String AccountNotFoundErrorText = "//mat-error[contains(.,'Account not found!')]";
    public static final String FewSecondsTextDisplayedOnEmail = "(//span[contains(.,'few seconds')])[2]";
    public static final String NewPasswordHeadingText = "//h2[contains(.,'New password')]";
    public static final String ConfirmPasswordField = "//input[@formcontrolname='confirmPassword']";
    public static final String SubmitButton = "//button[contains(.,'Submit')]";
    public static final String HelloPasswordRecoveryHeaderText = "//*[contains(text(),'Hello PasswordRecovery')]";
    public static final String YouHaveChangedPasswordText = "//*[contains(text(),'You have successfully changed password.')]";
    public static final String FirstNameIsRequiredErrorText = "//mat-error[contains(.,'First Name is required')]";
    public static final String LastNameIsRequiredErrorText = "//mat-error[contains(.,'Last Name is required')]";
    public static final String MobilePhoneNumberIsRequiredErrorText = "//mat-error[contains(.,'Mobile phone number is required')]";
    public static final String CountryIsRequiredErrorText = "//mat-error[contains(.,'Country is required')]";
    public static final String CountyIsRequiredErrorText = "//mat-error[contains(.,'County is required')]";
    public static final String AddressLine1IsRequiredErrorText = "//mat-error[contains(.,'Address Line 1 is required')]";
    public static final String SignInButton = "//button[contains(.,'Sign in')]";
    public static final String EditProfile = "//span[contains(.,'Edit profile')]";

    //Methods
    public void goToBackofficeTemplatePage() {
        goToXGB("/");
        waitForElementDisplayedByXpathWithTimeout(SignIn,5);
    }
}


