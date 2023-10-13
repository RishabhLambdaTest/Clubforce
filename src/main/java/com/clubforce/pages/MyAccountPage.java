package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class MyAccountPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public MyAccountPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String HeaderThreeDots = "//mat-icon[@role='img'][contains(.,'more_vert')]";
    public static final String MyAccountPageText = "//*[contains(text(),'%s')]";
    public static final String MyAccountHeading = "//h1[contains(.,'My Account')]";
    public static final String MyAccountMySettingsTitle = "(//div[contains(.,'Settings')])[2]";
    public static final String MyAccountMyDetailsTitle = "//div[@class='mat-line'][contains(.,'Member Details')]";
    public static final String updatePasswordButton = "//span[@class='mat-button-wrapper'][contains(.,'Update Password')]";
    public static final String PasswordPageHeading = "//h1[@class='mat-card-title'][contains(.,'Update Password')]";
    public static final String currentPasswordField = "//input[contains(@formcontrolname,'current_password')]";
    public static final String confirmPasswordField = "//input[contains(@formcontrolname,'confirm_password')]";
    public static final String passwordPageSubtitle = "//p[contains(.,'Confirm your current password and then create your new password.')]";
    public static final String newPasswordField = "//input[@formcontrolname='password']";
    public static final String currentPasswordRequiredText = "//mat-error[contains(.,'Current password is required')]";
    public static final String newPasswordRequiredText = "//mat-error[contains(.,'New password is required')]";
    public static final String confirmPasswordRequiredText = "//mat-error[contains(.,'Confirm password is required')]";
    public static final String confirmPasswordDoNotMatchText = "//mat-error[contains(.,'The password and confirmation password do not match.')]";
    public static final String successMessageSnackBar = "//simple-snack-bar[contains(.,'Password successfully updated!')]";
    public static final String BO_MenuDotsSignOut = "//*[contains(text(),'Sign out')]";
    public static final String LeftNavSettingsSection = "//mat-panel-title[contains(.,'settingsSettings')]";
    public static final String LeftNavNotificationSection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Notifications')]";
    public static final String NotificationsHeadingText = "//h1[contains(.,'Notifications')]";
    public static final String NotificationsSuccessfullyUpdatedSnackbar = "//span[contains(.,'Notifications has been updated successfully.')]";
    public static final String UnsubscribeFromAllButton = "//button[contains(.,'Unsubscribe from all')]";
    public static final String MyAccountSignOut = "//mat-icon[contains(.,'exit_to_app')]";
    public static final String DeleteCardIcon = "//mat-icon[contains(.,'delete')]";
    public static final String DeleteCardButton = "//span[contains(.,'Delete card')]";
    public static final String ExpiryDate11of2026 = "//mat-card-subtitle[contains(.,'Expiry Date: 11 / 2026')]";
    public static final String CiaranNameOnCard = "//h4[contains(.,'Ciaran')]";
    public static final String PaymentMethodHeading = "//h1[contains(.,'Payment methods')]";
    public static final String LeftNavPaymentMethods = "//span[@class='ms-2'][contains(.,'Payment methods')]";
    public static final String MyAccountSettingsLeftNav = "//span[contains(.,'settingsSettingsexpand_more')]";

    //Methods
    public void verifyMyAccountPageElements() {
        logger.info("Verifying text and images on the front page");
        findOnPage("//*[contains(text(),'My Account')]");
        assertFalse(isElementPresent(String.format(MyAccountPageText, "Error fetching account")));
        assertTrue(isElementPresent(String.format(MyAccountPageText, "Orders")));
        assertTrue(isElementPresent(String.format(MyAccountPageText, "Payment methods")));
        assertTrue(isElementPresent(String.format(MyAccountPageText, "Settings")));
    }
 }
