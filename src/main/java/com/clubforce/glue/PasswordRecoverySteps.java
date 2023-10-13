package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;

public class PasswordRecoverySteps extends WebDriverManager {
    //logger
    private final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public PasswordRecoverySteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
    }

    @Given("PwdRecoveryUser goes to Backoffice login page")
    public void pwdRecoveryUserGoToBackofficeLoginPage() {
        loginPage.goToBackofficeURL("/");
    }

    @When("PwdRecoveryAdmin requests password code on backoffice page")
    public void theyUsePasswordRecoveryFeature() {
        loginPage.click(LoginPage.ForgotPasswordLink);
        //wait for an item on next page to load
        loginPage.findOnPage(LoginPage.RecoverPasswordMailField);
        loginPage.waitTwoSeconds();
        logger.info("Verifying text on the page");
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Password reset")));
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Enter your email address to reset your password")));
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "You will receive a code by email")));
        logger.info("Sending an unrecognised mail address");
        loginPage.sendKeys(LoginPage.RecoverPasswordMailField, "wnzt4.xxxxxxxxxxx@inbox.testmail.app");
        loginPage.click(LoginPage.ResetPasswordButton);
        loginPage.waitTwoSeconds();
        //        assertTrue(loginPage.isElementPresent(LoginPage.AccountNotFoundErrorText)); Removed at the moment for security reasons
        loginPage.findOnPage(LoginPage.CodeVerificationHeadingText);
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Please check your email and enter 6-digit code to confirm your e-mail address")));
        loginPage.click("//a[contains(.,'back')]");

        loginPage.clear(LoginPage.RecoverPasswordMailField);

        logger.info("Sending an recognised mail address");
        loginPage.sendKeys(LoginPage.RecoverPasswordMailField, "wnzt4.passwordrecovery@inbox.testmail.app");
        loginPage.click(LoginPage.ResetPasswordButton);
        loginPage.findOnPage(LoginPage.CodeVerificationHeadingText);
        loginPage.waitTwoSeconds();
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Please check your email and enter 6-digit code to confirm your e-mail address")));
    }

    @Given("PwdRecoveryMember requests password code on homepage")
    public void pwdRecoveryUserGoToHomepagePWDRecoveryPage() {
        if (driverManager.agent.contains("appium")) {
            loginPage.click("//span[contains(@class,'navbar-toggler-icon')]");
            loginPage.waitTwoSeconds();
        }
        loginPage.click(LoginPage.HomepageProfileIcon);
        loginPage.sendKeys(LoginPage.RecoverPasswordMailField, "wnzt4.passwordrecovery@inbox.testmail.app");
        loginPage.click("//button[contains(.,'Next')]");
        loginPage.click(LoginPage.ForgotPasswordLink);
        loginPage.findOnPage(LoginPage.RecoverPasswordMailField);
        loginPage.waitOneSecond();
        logger.info("Verifying text on the page");
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Password reset")));
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Enter your email address to reset your password")));
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "You will receive a code by email")));
        logger.info("Sending an unrecognised mail address");
        loginPage.sendKeys(LoginPage.RecoverPasswordMailField, "wnzt4.xxxxxxxxxxx@inbox.testmail.app");
        loginPage.click(LoginPage.ResetPasswordButton);
        loginPage.waitOneSecond();
//        assertTrue(loginPage.isElementPresent(LoginPage.AccountNotFoundErrorText)); Removed at the moment for security reasons
        loginPage.findOnPage(LoginPage.CodeVerificationHeadingText);
        loginPage.refreshPage();
        loginPage.findOnPage(LoginPage.CodeVerificationHeadingText);
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Please check your email and enter 6-digit code to confirm your e-mail address")));
        loginPage.click("//a[contains(.,'back')]");

        logger.info("Sending an recognised mail address");
        loginPage.sendKeys(LoginPage.RecoverPasswordMailField, "wnzt4.passwordrecovery@inbox.testmail.app");
        loginPage.click(LoginPage.ResetPasswordButton);
        loginPage.findOnPage(LoginPage.CodeVerificationHeadingText);
        loginPage.waitOneSecond();
        assertTrue(loginPage.isElementPresent(String.format(LoginPage.LoginPageText, "Please check your email and enter 6-digit code to confirm your e-mail address")));
    }
}
