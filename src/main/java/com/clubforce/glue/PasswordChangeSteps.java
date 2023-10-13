package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.LoginPage;
import com.clubforce.pages.MyAccountPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;

public class PasswordChangeSteps extends WebDriverManager {
    //logger
    private final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public PasswordChangeSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.myAccountPage = driverManager.myAccountPage;
    }

    private String RandomPasswordHOLDER = " ";

    @When("user updates password")
    public void userUpdatePassword() {
        goToMyAccountPage();
        goToPasswordUpdatePage();
        validatePasswordPageFields();
        enterInCurrentAndNewPasswordAndUpdate();
    }

    @Then("user can login into myAccount with new password")
    public void signBackIntoMyAccountWithNewPassword() {
        signOutOfMyAccount();
        signBackIntoMyAccount();
    }

    @And("change password back to original")
    public void changePasswordBackToOriginal() {
        goToMyAccountPage();
        goToPasswordUpdatePage();
        validatePasswordPageFields();
        enterInCurrentPasswordAndUpdateToOriginalPassword();
        signOutOfMyAccount();
    }

    public void validatePasswordPageFields() {
        logger.info("Check if 'Current password is required' text displays");
        myAccountPage.click(MyAccountPage.currentPasswordField);
        myAccountPage.click(MyAccountPage.passwordPageSubtitle);
        myAccountPage.waitTwoSeconds();
        assertThat(" Current password required text is not shown", myAccountPage.isElementDisplayed(MyAccountPage.currentPasswordRequiredText));

        logger.info("Check if 'New password is required' text displays");
        myAccountPage.click(MyAccountPage.newPasswordField);
        myAccountPage.click(MyAccountPage.passwordPageSubtitle);
        myAccountPage.waitTwoSeconds();
        assertThat("New password required text is not shown", myAccountPage.isElementDisplayed(MyAccountPage.newPasswordRequiredText));

        logger.info("Check is 'Confirm password is required' text displays");
        myAccountPage.click(MyAccountPage.confirmPasswordField);
        myAccountPage.click(MyAccountPage.passwordPageSubtitle);
        myAccountPage.waitTwoSeconds();
        assertThat("Confirm password required text is not shown", myAccountPage.isElementDisplayed(MyAccountPage.confirmPasswordRequiredText));
        assertThat("Do not match required text is not shown", myAccountPage.isElementDisplayed(MyAccountPage.confirmPasswordDoNotMatchText));
    }

    public void enterInCurrentAndNewPasswordAndUpdate() {
        logger.info("Enter in current password: " + myAccountPassword);
        myAccountPage.sendKeys(MyAccountPage.currentPasswordField, myAccountPassword);

        RandomPasswordHOLDER = "Magnus1234" + RandomStringUtils.randomAlphabetic(3);

        logger.info("Enter in new password: " + RandomPasswordHOLDER);
        myAccountPage.sendKeys(MyAccountPage.newPasswordField, RandomPasswordHOLDER);

        logger.info("Confirm new password: " + RandomPasswordHOLDER);
        myAccountPage.sendKeys(MyAccountPage.confirmPasswordField, RandomPasswordHOLDER);

        myAccountPage.click(MyAccountPage.updatePasswordButton);
        myAccountPage.findOnPage(MyAccountPage.successMessageSnackBar);
        logger.info("Password Update Successful");
    }

    public void goToMyAccountPage() {
        logger.info("Go to My Account Page");
//        myAccountPage.click(MyAccountPage.MyAccountHamburger);
//        myAccountPage.waitTwoSeconds();
        myAccountPage.click(MyAccountPage.MyAccountMySettingsTitle);
        myAccountPage.click(MyAccountPage.MyAccountMyDetailsTitle);
//        myAccountPage.click("//div[@class='mat-drawer-backdrop ng-star-inserted mat-drawer-shown']");
        myAccountPage.findOnPage(MyAccountPage.MyAccountHeading);
    }

    public void goToPasswordUpdatePage() {
        logger.info("Go to Update Password Page");
        myAccountPage.waitOneSecond();
        myAccountPage.click(MyAccountPage.updatePasswordButton);
        myAccountPage.findOnPage(MyAccountPage.PasswordPageHeading);
    }

    public void signOutOfMyAccount() {
        logger.info("Sign out of My Account");
        myAccountPage.click(MyAccountPage.HeaderThreeDots);
        myAccountPage.click(MyAccountPage.BO_MenuDotsSignOut);
    }

    public void signBackIntoMyAccount() {
        logger.info("Sign back into My Account with password: " + RandomPasswordHOLDER);
        loginPage.goToMyAccountURL("/");
        loginPage.waitTwoSeconds();
        loginPage.sendKeys(LoginPage.LoginEmailField, myAccountUsername);
        loginPage.click("//span[contains(.,'Next')]");
        loginPage.sendKeys(LoginPage.LoginPasswordField, RandomPasswordHOLDER);
        loginPage.click(LoginPage.LoginButton);
        loginPage.waitTwoSeconds();
        assertFalse(loginPage.isElementPresent("//mat-error[@role='alert']"), "Error message when trying to log in");
    }

    public void enterInCurrentPasswordAndUpdateToOriginalPassword() {
        logger.info("Enter in current password: " + RandomPasswordHOLDER);
        myAccountPage.sendKeys(MyAccountPage.currentPasswordField, RandomPasswordHOLDER);

        logger.info("Enter in new password: " + myAccountPassword);
        myAccountPage.sendKeys(MyAccountPage.newPasswordField, myAccountPassword);

        logger.info("Confirm new password: " + myAccountPassword);
        myAccountPage.sendKeys(MyAccountPage.confirmPasswordField, myAccountPassword);

        myAccountPage.click(MyAccountPage.updatePasswordButton);
        myAccountPage.findOnPage(MyAccountPage.successMessageSnackBar);
        logger.info("Password Update Successful");
    }
}
