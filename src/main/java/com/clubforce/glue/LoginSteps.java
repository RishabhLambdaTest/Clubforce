package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.clubforce.util.MailServerUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LoginSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public LoginSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.myAccountPage = driverManager.myAccountPage;
        this.accountPage = driverManager.accountPage;
    }

    private String PasswordCodeHOLDER = " ";
    private String ActivateAccountCodeHOLDER = " ";
    private String MailBodyHOLDER = " ";


    @Given("user is on the backoffice login page")
    public void backofficeLoginPage() {
        loginPage.goToBackofficeLoginPage();
    }

    @Given("admin is on the SU login page")
    public void superuserLoginPage() {
        loginPage.goToSuperUserURL();
    }

    @Given("user is on the My Account login page")
    public void myAccountLoginPage() {
        loginPage.goToMyAccountLoginPage();
    }

    @Given("ClubAdmin goes back to backoffice")
    public void goBackToBackOffice(){
        logger.info("User is going back to backoffice");
        loginPage.goToBackofficeURL("/");
        logger.info("They shouldn't need to sign in because they never signed out");
    }

    @Given("{string} is logged into My Account")
    public void userLogsIn(String userLevel) {
        loginPage.goToMyAccountLoginPage();
        switch (userLevel) {
            case "manager":
                logger.info("Logging in as manager");
                loginPage.sendKeys(LoginPage.LoginEmailField,"admingalwaysports@CLUBFORCE.com");
                loginPage.sendKeys(LoginPage.LoginPasswordField,"asdasd");
                break;
            case "member":
                logger.info("Logging in as member");
                loginPage.sendKeys(LoginPage.LoginEmailField,"test1029@test.com");
                loginPage.sendKeys(LoginPage.LoginPasswordField,"asdasd");
                break;
            case "membertest4@clubforce.com":
                logger.info("Logging in as member");
                loginPage.sendKeys(LoginPage.LoginEmailField,"membertest4@clubforce.com");
                loginPage.sendKeys(LoginPage.LoginPasswordField,"b3deG2FnmrEy");
                loginPage.click("//button[contains(.,'sign in')]");
                loginPage.findOnPage("//h1[contains(.,'My Account')]");
                break;
            case "membertest25@clubforce.com":
                logger.info("Logging in as member");
                loginPage.sendKeys(LoginPage.LoginEmailField,"membertest25@clubforce.com");
                loginPage.sendKeys(LoginPage.LoginPasswordField,"b3deG2FnmrEy");
                loginPage.click("//button[contains(.,'sign in')]");
                loginPage.findOnPage("//h1[contains(.,'My Account')]");
                break;
            case "Lotto refund user":
                logger.info("Logging in as member");
                loginPage.sendKeys(LoginPage.LoginEmailField,ProductPurchaseSteps.USERMAILHOLDER);
                loginPage.sendKeys(LoginPage.LoginPasswordField,"b3deG2FnmrEy");
                loginPage.click("//button[contains(.,'sign in')]");
                loginPage.findOnPage("//h1[contains(.,'My Account')]");
                break;
            case "Membership refund user":
                logger.info("Logging in as member");
                loginPage.sendKeys(LoginPage.LoginEmailField, AccountPage.MEMRandomMailHolder);
                loginPage.sendKeys(LoginPage.LoginPasswordField,"b3deG2FnmrEy");
                loginPage.click("//button[contains(.,'sign in')]");
                loginPage.findOnPage("//h1[contains(.,'My Account')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for goToMainLoginPage!");
        }
    }

    @Given("ClubMember is logged into MyAccount")
    public void userLogsInMyAccount() {
        loginPage.goToMyAccountURL("/");
        loginPage.waitTwoSeconds();
        loginPage.sendKeys(LoginPage.LoginEmailField,myAccountUsername);
        loginPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
        loginPage.click(LoginPage.LoginButton);
        loginPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'My Account')]",15);
    }

    @Given("SuperUser is logged into SU")
    public void superUserLoggedIn() {
        logger.info("Logging in SuperUser");
        loginPage.goToSuperUserURL();
        loginPage.sendKeys(LoginPage.LoginEmailField, superuserUsername);
        loginPage.sendKeys(LoginPage.LoginPasswordField, superuserPassword);
        logger.info("SuperUser credentials entered, clicking login button");
        loginPage.click(LoginPage.SignIn);
        loginPage.waitUntilElementInvisible(LoginPage.SignIn,30);
        loginPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Account search')]", 30);
    }

    @Given("SuperUser logs in as ClubAdmin")
    public void superUserImpersonatesClubAdmin() {
        logger.info("Logging in SuperUser");
        loginPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);
        loginPage.click("(//mat-icon[@role='img'][contains(.,'edit')])[2]");
        loginPage.waitUntilElementInvisible(BackofficePage.ProgressBar, 10);
        loginPage.click("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]");
        loginPage.click("//button[@text='text-titlecase'][contains(.,'Log in as')]");
        loginPage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.DashboardHeading, 60);
    }

    @Given("{string} is logged into Backoffice")
    public void clubAdminLogsInBackOffice(String adminPerson) {
        logger.info("ClubAdmin goes to Backoffice URL");
        loginPage.goToBackofficeURL("/");
        if (!loginPage.isElementDisplayed(LoginPage.LoginEmailField)) {
            logger.info("Backoffice URL didn't load correctly, let's try again.");
            loginPage.goToBackofficeURL("/");
            loginPage.waitTwoSeconds();
        }
        loginPage.findOnPage(LoginPage.SignIn);
        loginPage.findOnPage(LoginPage.LoginEmailField);

        logger.info("ClubAdmin fills in login credentials");
        switch (adminPerson) {
            case "ClubAdmin":
                loginPage.sendKeys(LoginPage.LoginEmailField, clubAdminUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, clubAdminPassword);
                break;
            case "LottoClubAdmin":
                loginPage.sendKeys(LoginPage.LoginEmailField, lottoClubAdminUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, lottoClubAdminPassword);
                break;
            case "MembershipClubAdmin":
                loginPage.sendKeys(LoginPage.LoginEmailField, membershipClubAdminUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, membershipClubAdminPassword);
                break;
            case "AdminMember":
                loginPage.sendKeys(LoginPage.LoginEmailField, adminMemberUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, adminMemberPassword);
                break;
            case "AutoRenewalLottoClubAdmin":
                loginPage.sendKeys(LoginPage.LoginEmailField, "2425845775@clubforce.com");
                loginPage.sendKeys(LoginPage.LoginPasswordField, adminMemberPassword);
                break;
            case "newAdmin":
                loginPage.sendKeys(LoginPage.LoginEmailField, SuperUserSteps.ClubMailHOLDER);
                loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
                break;
            case "membershipPlansAdmin":
                loginPage.sendKeys(LoginPage.LoginEmailField, "membershipautotest@example.com");
                loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for clubAdminLogsInBackOffice!");
        }
        logger.info("ClubAdmin clicks login button");
        loginPage.userClicksLoginButton();
        assertFalse(loginPage.isElementDisplayed(LoginPage.LoginInvalidCredentialsMessage), "Invalid credentials error message is displayed");
        assertFalse(loginPage.isElementDisplayed(LoginPage.UnexpectedErrorMessage), "'Something unexpected happened' error message is displayed");
        loginPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Dashboard')]",60);
        logger.info("ClubAdmin has successfully logged into Backoffice.");
    }

    @And("ClubAdmin is logged out of Backoffice")
    public void userLogsOutBackOffice() {
        logger.info("ClubAdmin logs out of Backoffice");
        loginPage.click(BackofficePage.BO_MenuDots);
        loginPage.waitTwoSeconds();
        loginPage.click(MyAccountPage.BO_MenuDotsSignOut);
        loginPage.findOnPage(LoginPage.SignIn);
        loginPage.waitOneSecond();

        logger.info("Deleting all cookies");
        loginPage.deleteAllCookies();
        loginPage.waitFiveSeconds();
        loginPage.deleteAllCookies();
        loginPage.waitFiveSeconds();
    }

    @Given("SUAdmin is logged out of SU")
    public void suLogsOutBackOffice() {
        loginPage.click(BackofficePage.BO_MenuDots);
        loginPage.waitTwoSeconds();
        loginPage.click(MyAccountPage.BO_MenuDotsSignOut);
        loginPage.findOnPage(LoginPage.SignIn);
        loginPage.deleteAllCookies();
        loginPage.clearLocalStorage();
        loginPage.deleteAllCookies();
    }

    @Given("User is logged out of MA")
    public void logOutMyAccount() {
        loginPage.click(BackofficePage.BO_MenuDots);
        loginPage.waitTwoSeconds();
        loginPage.click(MyAccountPage.BO_MenuDotsSignOut);
        loginPage.deleteAllCookies();
        loginPage.clearLocalStorage();
        loginPage.deleteAllCookies();
    }
    @When("admin enter {string} login details")
    public void adminEntersLoginDetails(String app_login_details) {
        if (driverManager.agent.contains("appium")) {
            loginPage.findOnPage(LoginPage.EmailField);
            loginPage.findOnPage(LoginPage.LoginEmailField);
        } else {
            loginPage.findElementBelow(LoginPage.LoginEmailField, LoginPage.SignIn);
            loginPage.findElementBelow(LoginPage.LoginEmailField, LoginPage.LoginRememberMe);
        }

        switch (app_login_details) {
            case "invalid":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, "xxxxxx@ex");
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.LoginPasswordField);
                break;
            case "bad username":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, "xxxxxx@example.com");
                loginPage.waitOneSecond();
                loginPage.sendKeys(LoginPage.LoginPasswordField, "12345qwertyA");
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.SignIn);
                break;
            case "bad password":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, clubAdminUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, "thisIsWrongPassword");
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.SignIn);
                break;
            case "correct backoffice":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, clubAdminUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, clubAdminPassword);
                loginPage.click(LoginPage.SignIn);
                break;
            case "correct superuser":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, superuserUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, superuserPassword);
                loginPage.click(LoginPage.SignIn);
                break;
            case "correct myAccount":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, myAccountUsername);
                loginPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                loginPage.click(LoginPage.SignIn);
                break;
            case "lotto calculations club":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, "calculationtest@example.om");
                loginPage.waitOneSecond();
                loginPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.SignIn);
                break;
            case "testmonthlylottoAdmin":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, "testmonthlylottoadmin@example.com");
                loginPage.waitOneSecond();
                loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.SignIn);
                break;
            case "Irish visuals club":
                logger.info("admin enter "+app_login_details+" login details");
                loginPage.sendKeys(LoginPage.LoginEmailField, "irishvisualsadmin@example.com");
                loginPage.waitOneSecond();
                loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.SignIn);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminEntersLoginDetails!");
        }
    }

    @Then("they use expected transition path for {string}")
    public void useTransition(String userLevel) {
        switch (userLevel) {
            case "homepage to BO":
                loginPage.click(LoginPage.GoToClubAdminOption);
                loginPage.waitTwoSeconds();
                loginPage.findOnPage(BackofficePage.DashboardHeading);
                break;
            case "BO to homepage":
                loginPage.click(LoginPage.GoToMyClubOption);
                loginPage.waitTwoSeconds();
                loginPage.switchToBrowserTab(1);
                loginPage.findOnPage("//h2[contains(.,'Club news')] | //h2[contains(.,'Latest news')]");
                break;
            case "BO to MyAccount":
                loginPage.click(LoginPage.GoToMyAccountOption);
                loginPage.waitTwoSeconds();
                loginPage.switchToBrowserTab(1);
                loginPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'My Account')]",15);
                break;
            case "homepage to MyAccount":
                loginPage.click(LoginPage.MyAccountOption);
                loginPage.waitTwoSeconds();
                loginPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'My Account')]",15);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for useTransition!");
        }
    }

    @Then("user arrives on URL {string}")
    public void userArrivesOnExpectedURL(String destination) {
        loginPage.findOnPage(BackofficePage.SponsorsHeading);
        loginPage.waitTwoSeconds();
        assertTrue(driverManager.driver.getCurrentUrl().contains(destination));
    }

    @When("account holder enter a {string} mail into login modal")
    public void userLogsInHomepageModal(String mailType) {
        switch (mailType) {
            case "unrecognised":
                loginPage.sendKeys(LoginPage.LoginEmailField, "noSuchMail@example.com");
                loginPage.click(LoginPage.LoginNextButton);
                break;
            case "invalid":
                loginPage.sendKeys(LoginPage.LoginEmailField, "xxxxxx@ex");
                loginPage.waitOneSecond();
                loginPage.click(LoginPage.LoginRememberMe);
                break;
            case "recognised":
                loginPage.sendKeys(LoginPage.LoginEmailField, myAccountUsername);
                myAccountPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                myAccountPage.click("//img[contains(@alt,'ClubForce')]"); // defocus for keyboard collapse
                myAccountPage.click(LoginPage.SignIn);
                myAccountPage.waitUntilElementInvisible(LoginPage.SignIn,5);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for userLogsInHomepageModal!");
        }
    }

    @When("member enter a {string} mail into club page sign-in modal")
    public void memberEntersInfoOnClubPageLoginModal(String mailType) {
        switch (mailType) {
            case "unrecognised":
                loginPage.sendKeys(LoginPage.LoginEmailField, "noSuchMail@example.com");
                loginPage.click("//h2[contains(.,'Enter your e-mail')]"); // for mobile focus
                loginPage.click(LoginPage.HomepageLoginNextButton);
                loginPage.waitUntilElementInvisible(LoginPage.HomepageLoginNextButton, 5);
                break;
            case "recognised":
                loginPage.sendKeys(LoginPage.LoginEmailField, myAccountUsername);
                loginPage.click("//h2[contains(.,'Enter your e-mail')]"); // for mobile focus
                loginPage.click(LoginPage.HomepageLoginNextButton);
                loginPage.waitUntilElementInvisible(LoginPage.HomepageLoginNextButton, 5);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for memberEntersInfoOnClubPageLoginModal!");
        }
    }

    @Then("admin login page reacts accordingly to {string}")
    public void adminLoginModalReacts(String mailType) {
        switch (mailType) {
            case "unrecognised":
                logger.info("Log test for "+mailType);
                loginPage.findOnPage(LoginPage.CreateAccountTitle);
                //   loginPage.findOnPage(LoginPage.SignInOnlyValidForExistingClubAdminText);
                break;
            case "invalid":
                loginPage.waitTwoSeconds();
                loginPage.waitForElementDisplayedByXpathWithTimeout("//mat-error[contains(.,'Email must be valid')]",15);
                break;
            case "bad username":
            case "bad password":
                loginPage.waitTwoSeconds();
                logger.info("Log test for "+mailType);
                loginPage.waitForElementDisplayedByXpathWithTimeout("//h4[contains(.,'The user credentials were incorrect.')]", 15);
                break;
            case "correct backoffice":
            case "correct superuser":
                logger.info("Log test for "+mailType);
                loginPage.waitForElementDisplayedByXpathWithTimeout(ClubPage.MenuIcon,20);
                break;
            case "recognised":
                logger.info("Log test for "+mailType);
                loginPage.findOnPage(LoginPage.SignInHeadingText);
                loginPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                loginPage.click(LoginPage.LoginButton);
                loginPage.waitTwoSeconds();
                loginPage.click(LoginPage.ClubPageAccountCircleIcon);
                loginPage.waitTwoSeconds();
                loginPage.click(LoginPage.MyAccountOption);
                loginPage.waitForElementDisplayedByXpathWithTimeout(MyAccountPage.MyAccountHeading,20);
//                myAccountPage.verifyMyAccountPageElements();
                break;
            case "correct superuser on Backoffice login":
                loginPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'You do not have sufficient permissions to access this area. Please try again with a different email.')]",20);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminLoginModalReacts!");
        }
    }

    @Then("myaccount login modal reacts to {string}")
    public void myAccountLoginModalReacts(String mailType) {
        switch (mailType) {
            case "unrecognised":
                myAccountPage.findOnPage(LoginPage.CreateAccountTitle);
                break;
            case "invalid":
                myAccountPage.findOnPage(LoginPage.EmailMustBeValidErrorText);
                break;
            case "recognised":
                myAccountPage.waitTwoSeconds();
                assertFalse(myAccountPage.isElementDisplayed(LoginPage.LoginInvalidCredentialsMessage), "Invalid credentials error message is displayed");
                myAccountPage.verifyMyAccountPageElements();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for homepageLoginModalReacts!");
        }
    }

    @Then("club page login modal reacts accordingly to {string} mail")
    public void clubPageLoginModalReacts(String mailType) {
        switch (mailType) {
            case "unrecognised":
                myAccountPage.findOnPage(LoginPage.CreateAccountTitle);
                break;
            case "recognised":
                myAccountPage.findOnPage(LoginPage.SignInHeadingText);
                myAccountPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                myAccountPage.click(LoginPage.SignIn);
                assertFalse(myAccountPage.isElementDisplayed(LoginPage.LoginInvalidCredentialsMessage), "Invalid credentials error message is displayed");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for clubPageLoginModalReacts!");
        }
    }

    @Then("they can get {string} mail and do process to log in")
    public void mailAndLogIn(String code_type) {
        logger.info("Wait a while so mail have time to arrive");
        loginPage.waitFifteenSeconds();
        logger.info("Done waiting, let's go check");

        switch (code_type) {
            case "forget password":
                if (envName.contains("prod")) {
                    logger.info("Getting code from TestMailApp");
                    loginPage.goTo_URL("https://api.testmail.app/api/json?apikey=2a835032-6507-4e9e-8da9-153c8a33d44f&namespace=wnzt4&pretty=true");
                }
                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    PasswordCodeHOLDER = MailServerUtil.getForgotPasswordKeyFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    PasswordCodeHOLDER = MailServerUtil.getForgotPasswordKeyFromMailtrapSBX();
                }

                logger.info("Checked mail and got code " + PasswordCodeHOLDER);
                logger.info("Using code to log in");
                logger.info("Switch back to main tab so we can continue where we left off");
                loginPage.sendKeys(LoginPage.RecoverPasswordTokenField, PasswordCodeHOLDER);
                loginPage.click(LoginPage.RecoverPasswordTokenSubmitButton);
                logger.info("Land on create new password page and fill out form");
                loginPage.findOnPage(LoginPage.NewPasswordHeadingText);
                loginPage.sendKeys(MyAccountPage.newPasswordField, myAccountPassword);
                loginPage.sendKeys(LoginPage.ConfirmPasswordField, myAccountPassword);
                loginPage.click(LoginPage.SubmitButton);
                logger.info("Check that we were logged in");
                loginPage.waitFiveSeconds();
                if (driverManager.driver.getCurrentUrl().contains("backoffice")) {
                    loginPage.waitUntilElementInvisible(LoginPage.YouHaveChangedPasswordText, 5);
                    loginPage.findOnPage(BackofficePage.DashboardHeading);
                }
                if (driverManager.driver.getCurrentUrl().contains("penny")) {
                    if ((driverManager.agent.contains("appium"))) {
                        loginPage.click("//span[contains(@class,'navbar-toggler-icon')]");
                        loginPage.waitTwoSeconds();
                    }
                    loginPage.findOnPage(LoginPage.HelloHeaderText);
                }
                break;
            case "new CA":
                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    ActivateAccountCodeHOLDER = MailServerUtil.getActivateAccountCodeFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    ActivateAccountCodeHOLDER = MailServerUtil.getActivateAccountCodeFromMailtrapSBX();
                }

                logger.info("Checked mail and got code " + ActivateAccountCodeHOLDER);
                loginPage.goToBackofficeURL("/authentication/set-password/validate-token?email="+UsersPageSteps.RandomMailHolder);
                loginPage.sendKeys(LoginPage.RecoverPasswordTokenField, ActivateAccountCodeHOLDER);
                loginPage.click(LoginPage.SubmitButton);
                loginPage.findOnPage(LoginPage.NewPasswordHeadingText);
                loginPage.sendKeys(MyAccountPage.newPasswordField, myAccountPassword);
                loginPage.sendKeys(LoginPage.ConfirmPasswordField, myAccountPassword);
                loginPage.click(LoginPage.SubmitButton);
                logger.info("Check that we were logged in");
                loginPage.waitFiveSeconds();
                loginPage.findOnPage(BackofficePage.DashboardHeading);
                loginPage.waitTwoSeconds();
                break;
            case "new GM":
                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    MailBodyHOLDER = MailServerUtil.getMailTextFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    MailBodyHOLDER = MailServerUtil.getMailTextFromMailtrapSBX();
                }

                assertTrue(MailBodyHOLDER.contains("You have been made a Group Manager"));
                assertTrue(MailBodyHOLDER.contains("It’s easy to get started and create your first group"));
                assertTrue(MailBodyHOLDER.contains("App Store"));
                assertTrue(MailBodyHOLDER.contains("Play Store"));
                break;
            case "SU new CA":  // this is exact same as "new CA"
                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    ActivateAccountCodeHOLDER = MailServerUtil.getActivateAccountCodeFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    ActivateAccountCodeHOLDER = MailServerUtil.getActivateAccountCodeFromMailtrapSBX();
                }

                logger.info("Checked mail and got code " + ActivateAccountCodeHOLDER);
                loginPage.goToBackofficeURL("/authentication/set-password/validate-token?email="+UsersPageSteps.RandomMailHolder);
                loginPage.sendKeys(LoginPage.RecoverPasswordTokenField, ActivateAccountCodeHOLDER);
                loginPage.click(LoginPage.SubmitButton);
                loginPage.findOnPage(LoginPage.NewPasswordHeadingText);
                loginPage.sendKeys(MyAccountPage.newPasswordField, myAccountPassword);
                loginPage.sendKeys(LoginPage.ConfirmPasswordField, myAccountPassword);
                loginPage.click(LoginPage.SubmitButton);
                logger.info("Check that we were logged in ");
                loginPage.waitFiveSeconds();
                loginPage.findOnPage(BackofficePage.DashboardHeading);
                loginPage.waitTwoSeconds();
                break;
            case "AH to CA":
                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    MailBodyHOLDER = MailServerUtil.getMailTextFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    MailBodyHOLDER = MailServerUtil.getMailTextFromMailtrapSBX();
                }

                assertTrue(MailBodyHOLDER.contains("Your account was granted with admin access."));
                assertTrue(MailBodyHOLDER.contains("Please follow the link below to sign in to your account."));
                loginPage.deleteAllCookies();
                loginPage.goToBackofficeURL("/");
                loginPage.sendKeys(LoginPage.LoginEmailField, UsersPageSteps.RandomMailHolder);
                loginPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                loginPage.click(LoginPage.SignIn);
                logger.info("Check that we were logged in");
                loginPage.waitFiveSeconds();
                loginPage.findOnPage(BackofficePage.DashboardHeading);
                loginPage.waitTwoSeconds();
                break;
            case "AH to GM":
                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    MailBodyHOLDER = MailServerUtil.getMailTextFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    MailBodyHOLDER = MailServerUtil.getMailTextFromMailtrapSBX();
                }

                assertTrue(MailBodyHOLDER.contains("You have been made a Group Manager"));
                assertTrue(MailBodyHOLDER.contains("You will now be able to create and manage groups in Clubforce Connect."));
                assertTrue(MailBodyHOLDER.contains("App Store"));
                assertTrue(MailBodyHOLDER.contains("Play Store"));
                loginPage.deleteAllCookies();
                loginPage.goToBackofficeURL("/");
                loginPage.sendKeys(LoginPage.LoginEmailField, UsersPageSteps.RandomMailHolder);
                loginPage.sendKeys(LoginPage.LoginPasswordField, myAccountPassword);
                loginPage.click(LoginPage.SignIn);
                logger.info("Check that we are NOT logged into Backoffice. Group manager only role should not be allowed to access BAckoffice");
                loginPage.waitFiveSeconds();
                loginPage.findOnPage("//span[contains(.,'You do not have sufficient permissions to access this area. Please try again with a different email.')]");
                loginPage.waitTwoSeconds();
                logger.info("Group manager only role cannot access backoffice. User permission/role working as expected!");
                break;
            case "GM and CA":
                logger.info("In this case we get 2 mails, one for admin access and one for gm access");
                logger.info("We just look for 2 instances on the user's name and mail address in all mails ");

                if (envName.contains("test")) {
                    logger.info("Getting code from MailTrap Test");
                    MailBodyHOLDER = MailServerUtil.getAllMailsFromMailtrapTEST();
                }
                if (envName.contains("sandbox")) {
                    logger.info("Getting code from MailTrap Sandbox");
                    MailBodyHOLDER = MailServerUtil.getAllMailsFromMailtrapSBX();
                }

                logger.info("MailBodyHOLDER "+MailBodyHOLDER);
                assertTrue(MailBodyHOLDER.contains("You have been made a Group Manager at pennybridgerugby"));
                assertTrue(MailBodyHOLDER.contains(UsersPageSteps.RandomMailHolder));
                assertTrue(MailBodyHOLDER.contains("Admin access"));
                assertTrue(MailBodyHOLDER.contains(UsersPageSteps.RandomMailHolder));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for mailAndLogIn!");
        }
    }
}
