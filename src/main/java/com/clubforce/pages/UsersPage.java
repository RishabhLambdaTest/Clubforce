package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;

public class UsersPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public UsersPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String SUXGBSectionButton = "//span[@class='ms-2'][contains(.,'xGB accounts')]";
    public static final String SUAddButton = "//*[contains(text(),'New Account')]";
    public static final String AddButton = "//mat-icon[@role='img'][contains(.,'add')]";
    public static final String UsersPageTitle = "//h1[contains(.,'Users')]";
    public static final String UsersPageText = "//*[contains(text(),'%s')]";
    public static final String UsersPageSearchField = "//input[contains(@formcontrolname,'')]";
    public static final String UsersPageAddUserNameField = "//input[contains(@formcontrolname,'name')]";
    public static final String UsersPageAddUserMailField = "//input[contains(@formcontrolname,'email')]";
    public static final String InvalidEmailErrorText = "//mat-error[contains(.,'Invalid Email')]";
    public static final String EnterUserEmailErrorText  ="//mat-error[contains(.,'Please enter user email')]";
    public static final String EnterUserNameErrorText = "//mat-error[contains(.,'Please enter user name')]";
    public static final String AlertError = "//mat-error[contains(@role,'alert')]";
    public static final String CreateAccountHeading = "//h2[contains(.,'Create Account')]";
    public static final String CreateAccountFirstNameField = "//input[contains(@formcontrolname,'first_name')]";
    public static final String CreateAccountLastNameField = "//input[contains(@formcontrolname,'last_name')]";
    public static final String CreateAccountMobileField = "//input[contains(@formcontrolname,'mobile')]";
    public static final String CreateAccountCountryField = "(//div[contains(.,'Country *')])[12]";
    public static final String CreateAccountCountyField = "//input[contains(@formcontrolname,'county')]";
    public static final String CreateAccountPostCodeField = "//input[contains(@formcontrolname,'postal_code')]";
    public static final String CreateAccountCityField = "//input[contains(@formcontrolname,'city')]";
    public static final String CreateAccountAddress1Field = "//input[contains(@formcontrolname,'line1')]";
    public static final String CreateAccountAddress2Field = "//input[contains(@formcontrolname,'line2')]";
    public static final String CreateAccountAgreeToTermsField = "(//span[contains(@class,'mat-checkbox-inner-container')])[1]";
    public static final String UserNotFoundEmptyStateHeading = "//h2[contains(.,'Users not found')]";
    public static final String UsersPageNameColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Name')]";
    public static final String UsersTableRow1Column1 = "(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]";
    public static final String UsersTableRow1Column2 = "(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]";
    public static final String AddNewUserButton = "//button[contains(.,'add Add new user')]";
    public static final String UserPageFirstNameField = "//input[contains(@formcontrolname,'first_name')]";
    public static final String UsersPageLastNameField = "//input[contains(@formcontrolname,'last_name')]";
    public static final String UsersPageEmailField = "//input[contains(@formcontrolname,'email')]";

    //Methods
    public void verifyUsersPageElements() {
        logger.info("Verifying text and images on the front page");
        findOnPage("//*[contains(text(),'Items per page:')]");
        assertTrue(isElementPresent("(//div[contains(.,'Start typing to filter by name, email')])[10]"));
        assertTrue(isElementPresent(String.format(UsersPageText, "Name")));
        assertTrue(isElementPresent(String.format(UsersPageText, "E-Mail")));
        assertTrue(isElementPresent(String.format(UsersPageText, "Created")));
        assertTrue(isElementPresent(String.format(UsersPageText, "Last")));
        assertTrue(isElementPresent(String.format(UsersPageText, "Last logged in")));
    }
 }
