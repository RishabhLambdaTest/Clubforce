package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.glue.MembershipSteps;
import com.clubforce.glue.ProductPurchaseSteps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class LoginPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public LoginPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    // logger
    private static final Logger logger = LogManager.getLogger();

    // Links
    public static final String SignIn = "//button[contains(.,'sign in')]";
    public static final String LoginPageText = "//*[contains(text(),'%s')]";
    public static final String LoginButton = "//button[contains(.,'sign in')]";
    public static final String LoginRememberMe = "//span[contains(.,'Remember me')]";
    public static final String EnterYourMailText = "//h2[contains(.,'Enter your')]";
    public static final String CookieUnderstand = "//a[contains(.,'I understand')]";
    public static final String LoginEmailField = "//input[contains(@formcontrolname,'email')]";
    public static final String LoginPasswordField = "//input[contains(@formcontrolname,'password')]";
    public static final String LoginInvalidCredentialsMessage = "//mat-error[contains(.,'Invalid credentials')]";
    public static final String HomepageProfileIcon = "//mat-icon[contains(@data-test,'app.accountButton')]";
    public static final String LoginNextButton = "//button[contains(.,'Next')]";
    public static final String HomepageLoginNextButton = "//button[@data-test='check-existing-email.nextButton'][contains(.,'Next')]";
    public static final String CreateAccountTitle = "//*[contains(.,'Create account')]";
    public static final String HaveAtLeastOneLowerCaseText = "//li[contains(.,'At least one lower case')]";
    public static final String ForgotPasswordLink = "//a[contains(.,'Forgot Password?')]";
    public static final String RecoverPasswordMailField = "//input[contains(@autocomplete,'off')]";
    public static final String RecoverPasswordTokenField = "//input[contains(@formcontrolname,'token')]";
    public static final String RecoverPasswordTokenSubmitButton = "//button[contains(.,'Submit')]";
    public static final String ResetPasswordButton = "//button[contains(.,'Reset password')]";
    public static final String UnexpectedErrorMessage = "//mat-error[contains(.,'Something unexpected happened')]";
    public static final String SuccessHeading = "//h1[contains(.,'Success!')]";
    public static final String YourEmailIsVerifiedText = "//span[contains(.,'Your email is verified. You can now log in to your account.')]";
    public static final String OopsHeading = "//h1[contains(.,'Oops!')]";
    public static final String YourEmailHasAlreadyBeenVerifiedText = "//span[contains(.,'Your email has already been verified, or you have arrived here in error.')]";
    public static final String WrongEmailButton = "//a[contains(.,'Wrong email?')]";
    public static final String EmailMustBeValidErrorText = "//mat-error[contains(.,'Email must be valid')]";
    public static final String CardHeader = "//div[contains(@class,'card__header')]";
    public static final String SignInHeadingText = "//h2[contains(.,'Sign in')]";
    public static final String ClubPageAccountCircleIcon = "//mat-icon[@role='img'][contains(.,'account_circle')]";
    public static final String MyAccountOption = "//span[contains(.,'My account')]";
    public static final String GoToMyAccountOption = "//span[contains(.,'Go to my account')]";
    public static final String GoToMyClubOption = "//span[contains(.,'Go to my club')]";
    public static final String GoToClubAdminOption = "//span[contains(.,'Go to club admin')]";
    public static final String EmailField = "//*[@id='mat-input-1']";
    public static final String CodeVerificationHeadingText = "//h2[contains(.,'Verification')]";
    public static final String NewPasswordHeadingText = "//h2[contains(.,'New password')]";
    public static final String ConfirmPasswordField = "//input[@formcontrolname='confirmPassword']";
    public static final String SubmitButton = "//button[contains(.,'Submit')]";
    public static final String HelloHeaderText = "//strong[@data-test='app.userName'][contains(.,'Hello')]";
    public static final String YouHaveChangedPasswordText = "//*[contains(text(),'You have successfully changed password.')]";
    public static final String FirstNameIsRequiredErrorText = "//mat-error[contains(.,'First name is required')]";
    public static final String LastNameIsRequiredErrorText = "//mat-error[contains(.,'Last name is required')]";
    public static final String MobilePhoneNumberIsRequiredErrorText = "//mat-error[contains(.,'Mobile phone number is required')]";
    public static final String CountyIsRequiredErrorText = "//mat-error[contains(.,'County is required')]";
    public static final String AddressLine1IsRequiredErrorText = "//mat-error[contains(.,'Address line 1 is required')]";
    public static final String SignInButton = "//button[contains(@data-test,'sign-in.signInButton')]";
    public static final String XBGLoginTitle = "//h2[contains(.,'XGB Sign in')]";

    // Methods
    public void goToBackofficeLoginPage() {
        goToBackofficeURL("/");
        waitOneSecond();
        if (!isElementPresent(SignIn)) {
            logger.info("LoginPageSignInTitle not displayed, refreshing page");
            refreshPage();
            waitOneSecond();
            assertTrue(isElementPresent(SignIn));
        }
    }

    public void goToSuperUserURL() {
        goToSuperUserURL("/");
    }

    public void goToMyAccountLoginPage() {
        beforeHandleChromeMobile();
        goToMyAccountURL("/");
        waitOneSecond();
    }

    public void accessMailTrapInbox() {
        logger.info("Going to MailTrap Inbox");
        goTo_URL("http://mailtrap.io/signin");
        waitTwoSeconds();
        waitFiveSeconds();
        // sendKeys("//input[contains(@id,'user_email')]", "rishabhsinghlambdatest@clubforce.com");
        click("//a[contains(.,'Next')]");
        click("//button[contains(.,'Accept')]");
        sendKeys("//input[contains(@id,'user_password')]", "meljfallantydligenellerhur!");
        click("//input[contains(@type,'submit')]");
        waitForElementDisplayedByXpathWithTimeout("//a[contains(.,'*test')]", 10);

        if (WebDriverManager.envName.contains("test")) {
            logger.info("Going to Test Inbox");
            click("//a[contains(.,'*test')]");
        }
        if (WebDriverManager.envName.contains("sandbox")) {
            logger.info("Going to Sandbox Inbox");
            click("//a[contains(.,'*sandbox')]");
        }

        findOnPage("//h2[contains(.,'SMTP / POP3')]");
        logger.info("Standing in MailTrap Inbox");
    }

    public String getPasswordTokenFromMail() {
        logger.info("Getting password recovery code from mail");
        waitThreeSeconds();
        assertTrue(isElementPresent(String.format(LoginPageText, "Your verification code is")));
        String PreTrimmedCodeString = driver.findElement(By.xpath("//*[contains(text(),'Your verification code is')]"))
                .getText();
        System.out.println(PreTrimmedCodeString);
        String tokenCode = "";
        Pattern regexPattern = Pattern.compile("Your verification code is \\w\\w\\w\\w\\w\\w");
        Matcher match = regexPattern.matcher(PreTrimmedCodeString);
        if (match.find()) {
            tokenCode = match.group();
        }
        // trim and return
        return tokenCode.substring(26, 32);
    }

    public void lookForLottoPurchaseMailOnMailTrap() {
        logger.info("Looking for lotto purchase mail");
        waitForElementDisplayedByXpathWithTimeout("//input[contains(@name,'quick_filter')]", 10);
        sendKeys("//input[contains(@name,'quick_filter')]", ProductPurchaseSteps.USERMAILHOLDER);
        waitForElementDisplayedByXpathWithTimeout(
                "(//span[@class='to_email'][contains(.,'" + ProductPurchaseSteps.USERMAILHOLDER + "')])[1]", 10);
        click("(//span[@class='to_email'][contains(.,'" + ProductPurchaseSteps.USERMAILHOLDER + "')])[1]");
        waitThreeSeconds();
        driverManager.driver.switchTo()
                .frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
        waitForElementDisplayedByXpathWithTimeout("//a[contains(.,'Thanks for your order!')]", 10);
        driver.switchTo().parentFrame();
        assertTrue(isElementPresent("//small[contains(.,'<" + ProductPurchaseSteps.USERMAILHOLDER + ">')]"));
    }

    public void lookForMembershipPurchaseMailOnMailTrap() {
        logger.info("Looking for membership purchase mail");
        waitFiveSeconds();
        sendKeys("//input[contains(@name,'quick_filter')]", AccountPage.MEMRandomMailHolder);
        waitTwoSeconds();
        click("(//span[@class='to_email'][contains(.,'" + AccountPage.MEMRandomMailHolder + "')])[1]");
        waitThreeSeconds();
        driverManager.driver.switchTo()
                .frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
        waitFiveSeconds();
        assertTrue(isElementPresent("//a[contains(.,'Thanks for your order!')]"));
        driver.switchTo().parentFrame();
        assertTrue(isElementPresent("//small[contains(.,'<" + AccountPage.MEMRandomMailHolder + ">')]"));
    }

    public void test13userLogInOnCheckoutPage() {
        waitTwoSeconds();
        click(ProductsPage.CheckoutPageEnterEmailButton);
        waitTwoSeconds();
        findOnPage(EnterYourMailText);
        sendKeys(LoginEmailField, ProductPurchaseSteps.USERMAILHOLDER);
        click(LoginNextButton);
        findOnPage(LoginPasswordField);
        sendKeys(LoginPasswordField, "b3deG2FnmrEy");
        escape();
        click(SignInButton);
    }

    public void test4userLogInOnCheckoutPage() {
        waitOneSecond();
        click(ProductsPage.CheckoutPageEnterEmailButton);
        waitTwoSeconds();
        findOnPage(EnterYourMailText);
        sendKeys(LoginEmailField, ProductPurchaseSteps.USERMAILHOLDER);
        click(LoginNextButton);
        findOnPage(LoginPasswordField);
        sendKeys(LoginPasswordField, "b3deG2FnmrEy");
        click(SignInButton);
    }

    public void beforeHandleChromeMobile() {
        if (driverManager.agent.contains("emulator.local")) {
            logger.info("Local Android, handle Chrome blurbs");
            if (isElementDisplayed("//*[@text='Accept & continue')]")) {
                click("//*[@text='Accept & continue')]");
                waitTwoSeconds();
            }

            if (isElementDisplayed("//*[@text='No thanks')]")) {
                click("//*[@text='No thanks')]");
                waitTwoSeconds();
            }

            if (isElementDisplayed("//*[@text='No thanks')]")) {
                click("//*[@text='No thanks')]");
                waitTwoSeconds();
            }

            if (isElementDisplayed("//*[@text='Use without an account')]")) {
                click("//*[@text='Use without an account')]");
                waitTwoSeconds();
            }
        }
    }
}
