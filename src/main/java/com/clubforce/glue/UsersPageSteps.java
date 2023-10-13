package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class UsersPageSteps extends WebDriverManager {
    // logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public UsersPageSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.usersPage = driverManager.usersPage;
        this.loginPage = driverManager.loginPage;
        this.accountPage = driverManager.accountPage;
    }

    public static String RandomMailHolder;
    public static String RandomFirstName;
    public static String newUserNameHOLDER;
    public static String NewCANameHolder;
    public static String NewCALastNameHolder;

    @Then("their users page details are as expected")
    public void userPageUserDetails() {
        usersPage.verifyUsersPageElements();
        logger.info("Verifying " + clubAdminUsername + "'s users' details");
        usersPage.findOnPage(UsersPage.AddButton);
        usersPage.findOnPage(UsersPage.UsersPageTitle);
    }

    @Then("they can search for users")
    public void userPageUserSearch() {
        usersPage.verifyUsersPageElements();

        logger.info("Checking no users appears when search for 'iAMNotHere'");
        usersPage.click(UsersPage.UsersPageSearchField);
        usersPage.clearInputFieldUsingBackSpaceKey(UsersPage.UsersPageSearchField);
        usersPage.sendKeys(UsersPage.UsersPageSearchField, "NoSuchUser");
        usersPage.findOnPage(UsersPage.UserNotFoundEmptyStateHeading);

        logger.info("Checking " + clubAdminUsername + " appears when searched for");
        usersPage.clear(UsersPage.UsersPageSearchField);

        if (envName.contains("test")) {
            usersPage.sendKeys(UsersPage.UsersPageSearchField, "Admin");
        }
        if (envName.contains("sandbox")) {
            usersPage.sendKeys(UsersPage.UsersPageSearchField, "pennybridge");
        }
        if (envName.contains("prod")) {
            usersPage.sendKeys(UsersPage.UsersPageSearchField, "magnus");
        }

        usersPage.waitForElementDisplayedByXpathWithTimeout(UsersPage.UsersPageNameColumnHeader, 10);
        assertTrue(usersPage.isElementPresent(String.format(UsersPage.UsersPageText, clubAdminUsername)));
    }

    @Then("they can look up a member using the {string} field")
    public void lookUpMemberOnUsersPage(String field) {

        String user1Name = usersPage.getElementAttribute(UsersPage.UsersTableRow1Column1, "textContent").trim();
        String user1FirstName = user1Name.substring(0, 3);
        String user1LastName = user1Name.substring(user1Name.length() - 4, user1Name.length() - 1);
        String user1Email = usersPage.getElementAttribute(UsersPage.UsersTableRow1Column2, "textContent").trim();
        String email = user1Email.substring(1, user1Email.length() - 1);
        String emailFirst3Characters = email.substring(0, 4);

        logger.info("Click add new user button");
        usersPage.click(UsersPage.AddNewUserButton);

        switch (field) {
            case "firstname":
                logger.info("Checking member look up using first name field");
                usersPage.sendKeys(UsersPage.UserPageFirstNameField, user1FirstName);
                usersPage.findOnPage("(//span[contains(.,'" + email + "')])[2]");
                break;
            case "lastname":
                logger.info("Checking member look up using last name field");
                usersPage.sendKeys(UsersPage.UsersPageLastNameField, user1LastName);
                usersPage.findOnPage("(//span[contains(.,'" + email + "')])[2]");
                break;
            case "email":
                logger.info("Checking member look up using email field");
                usersPage.sendKeys(UsersPage.UsersPageEmailField, emailFirst3Characters);
                usersPage.findOnPage("(//span[contains(.,'" + email.substring(5, email.length() - 1) + "')])[2]");
                break;
            default:
        }
    }

    @Then("they can create a new user")
    public void createNewUserBackoffice() {
        Lorem lorem = LoremIpsum.getInstance();
        usersPage.verifyUsersPageElements();

        logger.info(clubAdminUsername + "is creating a new user for club");
        usersPage.waitOneSecond();
        usersPage.click(UsersPage.AddButton);

        logger.info("Saving without password to check error handling");
        usersPage.click("//input[contains(@formcontrolname,'name')]");
        usersPage.click("//h1[contains(.,'Create User Account')]");
        assertTrue(usersPage.isElementPresent(UsersPage.EnterUserNameErrorText));
        usersPage.waitOneSecond();

        usersPage.click("//input[contains(@formcontrolname,'email')]");
        usersPage.click("//h1[contains(.,'Create User Account')]");
        assertTrue(usersPage.isElementPresent(UsersPage.EnterUserEmailErrorText));
        assertTrue(usersPage.isElementPresent(UsersPage.InvalidEmailErrorText));
        usersPage.waitOneSecond();

        logger.info("Error handling ok, adding user details");
        // Create random username and mail
        newUserNameHOLDER = lorem.getFirstName() + " " + lorem.getFirstName(); // don't risk get O'Brien as last name,
                                                                               // breaks test code
        usersPage.sendKeys(UsersPage.UsersPageAddUserNameField, newUserNameHOLDER);
        RandomMailHolder = "qa+" + RandomStringUtils.randomAlphabetic(7) + "CASE@clubforce.com";
        usersPage.sendKeys(UsersPage.UsersPageAddUserMailField, RandomMailHolder);
        // checking mail has been converted to lowercase
        assertThat(usersPage.getElementAttribute(UsersPage.UsersPageAddUserMailField, "value"), containsString("case"));

        logger.info("User details added, saving");
        usersPage.waitOneSecond();
        usersPage.click("(//button[contains(.,'create user')])[2]");
        assertFalse(usersPage.isElementPresent(UsersPage.AlertError));
        usersPage.waitUntilElementInvisible("//span[contains(.,'User successfully created!')]", 10);

        logger.info("Verifying user exit by searching them on user page");
        usersPage.sendKeys("//input[contains(@placeholder,'Search by name or e-mail')]", newUserNameHOLDER);
        usersPage.waitFiveSeconds();
        assertTrue(usersPage.isElementPresent(String.format(UsersPage.UsersPageText, newUserNameHOLDER)));
    }

    @And("the new user can activate account")
    public void activateUserAccount() {
        loginPage.accessMailTrapInbox();
        logger.info("Looking for  new ClubAdmin mail");
        usersPage.click("(//span[contains(.,'" + RandomMailHolder.toLowerCase() + "')])[2]");
        usersPage.waitThreeSeconds();
        driverManager.driver.switchTo()
                .frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
        usersPage.waitFiveSeconds();
        assertTrue(usersPage.isElementPresent("//a[contains(.,'Admin Access')]"));
        assertTrue(usersPage.isElementPresent(String.format(UsersPage.UsersPageText, newUserNameHOLDER)));
        usersPage.click("//a[contains(.,'Activate account')]");

        logger.info("Setting password for CA");
        usersPage.switchToBrowserTab(1);
        usersPage.findOnPage("//h2[contains(.,'Set password')]");
        usersPage.sendKeys("//input[contains(@formcontrolname,'password')]", "b3deG2FnmrEy");
        usersPage.sendKeys("//input[contains(@formcontrolname,'confirmPassword')]", "b3deG2FnmrEy");
        usersPage.click("//button[contains(.,'Submit')]");

        logger.info("Checking that we logged into BO");
        usersPage.waitTwoSeconds();
        usersPage.findOnPage("//h1[contains(.,'Dashboard')]");

        logger.info("Going back to mailtrap");
        usersPage.goTo_URL("https://mailtrap.io/signin");
        usersPage.waitTwoSeconds();
        if (WebDriverManager.envName.contains("test")) {
            logger.info("Going to Test Inbox");
            usersPage.click("//span[@class='inbox_name'][contains(.,'*test')]");
        }
        if (WebDriverManager.envName.contains("sandbox")) {
            logger.info("Going to Sandbox Inbox");
            usersPage.click("//span[@class='inbox_name'][contains(.,'*sandbox')]");
        }

        usersPage.findOnPage("//h2[contains(.,'SMTP / POP3')]");
        logger.info("Standing in MailTrap Inbox");
        logger.info("Looking for  new ClubAdmin mail");
        usersPage.click("(//span[contains(.,'" + RandomMailHolder.toLowerCase() + "')])[1]");
        usersPage.waitThreeSeconds();
        driverManager.driver.switchTo()
                .frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
        usersPage.waitFiveSeconds();
        usersPage.findOnPage("//p[contains(.,'You’ve successfully updated your password on')]");
    }

    @Then("they can sign up to become a member")
    public void signUpNewMemberOnHomepage() {
        if (driverManager.agent.contains("appium")) { // collapse header if phone
            accountPage.click(ClubPage.ClubNavBarIconMobile);
        }
        usersPage.click(LoginPage.HomepageProfileIcon);
        accountPage.errorHandlingCreateAccountModal();
        accountPage.useCreateAccountModal("New homepage member");
    }

    @Then("they can sign up to become a member using {string}")
    public void signUpNewMemberOnHomepage(String member_mail) {
        usersPage.click(LoginPage.HomepageProfileIcon);
        accountPage.sendKeys(LoginPage.LoginEmailField, member_mail);
        accountPage.click(LoginPage.LoginNextButton);
        accountPage.findOnPage(LoginPage.CreateAccountTitle);
        accountPage.useCreateAccountModalPresetMail(member_mail);
        accountPage.click("//strong[@data-test='app.userName'][contains(.,'Hello')]");
        accountPage.click("//span[contains(.,'My account')]");
        accountPage.click("//input[contains(@data-mat-calendar,'mat-datepicker-0')]");
        accountPage.click("//span[@id='mat-calendar-button-0']");
        accountPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2001')]");
        accountPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'MAR')]");
        accountPage.click("//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'17')]");
        accountPage.scrollPageToBottom();
        accountPage.click("(//button[contains(.,'Update')])[2]");
        accountPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Account successfully updated.')]", 5);
    }

    @And("they can verify their mail address")
    public void userVerifiesSignUpMail() {
        logger.info("Going to MailTrap");

        if (envName.contains("prod")) {
            logger.info("Going to Testmail");
            loginPage.goTo_URL(
                    "https://api.testmail.app/api/json?apikey=2a835032-6507-4e9e-8da9-153c8a33d44f&namespace=wnzt4&pretty=true");
        } else {
            if ((!driverManager.agent.contains("appium"))) { // TODO make this step work for mobile.
                logger.info("Going to Mailtrap");
                loginPage.accessMailTrapInbox();

                logger.info("Verifying SignUp mail");
                usersPage.waitTwoSeconds();

                loginPage.waitFiveSeconds();
                loginPage.sendKeys("//input[contains(@name,'quick_filter')]", AccountPage.RandomMailHOLDER);
                loginPage.waitFiveSeconds();
                usersPage.click("//span[@class='to_email'][contains(.,'" + AccountPage.RandomMailHOLDER + "')]");
                loginPage.waitFiveSeconds();
                driverManager.driver.switchTo()
                        .frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                usersPage.waitFiveSeconds();
                assertTrue(usersPage
                        .isElementPresent(String.format(LoginPage.LoginPageText, AccountPage.randomFirstName)));
                assertTrue(usersPage.isElementPresent(
                        "//p[contains(.,'Please click the below button to verify your email address.')]"));
                usersPage.click("//a[@target='_blank'][contains(.,'Verify email')]");

                logger.info("Verify mail clicked, we should now be on verification success page");
                usersPage.waitTwoSeconds();
                usersPage.switchToBrowserTab(1);
                usersPage.waitTwoSeconds();
                usersPage.findOnPage(LoginPage.SuccessHeading);
                usersPage.findOnPage(LoginPage.YourEmailIsVerifiedText);

                logger.info("To error handle, simply refresh the page and check you now get already verified message");
                usersPage.refreshPage();
                usersPage.findOnPage(LoginPage.OopsHeading);
                usersPage.findOnPage(LoginPage.YourEmailHasAlreadyBeenVerifiedText);
                logger.info("Error handling check done");
            }
        }
    }

    @And("they can see their myaccount page")
    public void newUserGoesToTheirAccountPage() {
        logger.info("User goes to My Account Page");
        usersPage.goToMyAccountURL("/");
        usersPage.findOnPage(BackofficePage.FirstNameField);
        String accountHolderName = usersPage.getElementAttribute(BackofficePage.FirstNameField, "value");
        logger.info("Account holder name entered in on website: " + accountHolderName);
        logger.info("Account holder name in MyAccount on account page: " + accountHolderName);
        assertEquals(accountHolderName, AccountPage.randomFirstName);
        logger.info(RandomFirstName + " is logged into their account");
    }

    @Then("SU add a new CA account")
    public void suAddNewCA() {
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Adding a new CA");
        usersPage.click("//button[contains(.,'add_circle_outline Add new user')]");
        usersPage.sendKeys("//input[contains(@formcontrolname,'name')]",
                lorem.getFirstNameMale() + " " + lorem.getFirstNameMale());

        if (envName.contains("prod")) {
            RandomMailHolder = "wnzt4." + RandomStringUtils.randomAlphabetic(7).toLowerCase() + "@inbox.testmail.app";
        } else {
            RandomMailHolder = "qa+" + RandomStringUtils.randomAlphabetic(12).toLowerCase() + "@clubforce.com";
        }

        usersPage.sendKeys("//input[contains(@formcontrolname,'email')]", RandomMailHolder);
        usersPage.sendKeys("//input[contains(@formcontrolname,'password')]", "b3deG2FnmrEy");
        usersPage.click("//button[@form='form'][contains(.,'Create userarrow_forward')]");
        usersPage.findOnPage("//span[contains(.,'User successfully created!')]");
    }

    @Then("CA add {string} additional {string} account")
    public void caAddNewCA(String type, String userRole) {
        Lorem lorem = LoremIpsum.getInstance();

        logger.info("Verify column headers");
        usersPage.findOnPage("//span[@data-test='table.header-item'][contains(.,'Name')]");
        usersPage.findOnPage("//span[@data-test='table.header-item'][contains(.,'E-Mail')]");
        usersPage.findOnPage("//span[@data-test='table.header-item'][contains(.,'Created')]");
        usersPage.findOnPage("//span[@data-test='table.header-item'][contains(.,'Last logged in')]");
        usersPage.findOnPage("(//strong[contains(.,'Role')])[1]");

        logger.info("Adding a new user");
        usersPage.click("//button[contains(.,'add Add new user')]");
        usersPage.findOnPage("(//div[contains(.,'Add new user')])[10]");
        usersPage.findOnPage("//h3[contains(.,'General information')]");
        usersPage.findOnPage("//span[contains(.,'back')]");
        usersPage.findOnPage("//h3[contains(.,'Roles')]");
        usersPage.findOnPage("(//span[contains(.,'Club admin')])[2]");
        usersPage.findOnPage("//small[contains(.,'Access to Backoffice')]");
        usersPage.findOnPage("//span[@class='text-break'][contains(.,'Group manager')]");
        usersPage.findOnPage("//small[contains(.,'Access to Connect App')]");

        logger.info("Error handling, add CA and cancel out");

        switch (type) {
            case "brand new":
                NewCANameHolder = lorem.getFirstNameMale();
                NewCALastNameHolder = lorem.getLastName();
                break;
            case "already AH":
                NewCANameHolder = AccountPage.randomFirstName;
                NewCALastNameHolder = AccountPage.randomLastName;
                usersPage.waitTwoSeconds();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for caAddNewCA!");
        }

        usersPage.sendKeys("//input[contains(@formcontrolname,'first_name')]", NewCANameHolder);
        usersPage.sendKeys("//input[contains(@formcontrolname,'last_name')]", "xxxxxxx@clubforce.com");
        usersPage.click("//span[contains(.,'back')]");
        logger.info("Click discard changes button");
        usersPage.findOnPage("//h2[contains(.,'Exit without saving?')]");
        usersPage.click("//span[contains(@id,'dialog--confirm')]");

        loginPage.waitTwoSeconds();
        assertTrue(usersPage.isElementDisplayed("//h1[contains(.,'Users')]"));
        assertFalse(usersPage.isElementDisplayed("//h1[contains(.,'Create User Account')]"));
        logger.info("Go back in to create page");
        usersPage.click("//button[contains(.,'add Add new user')]");
        usersPage.findOnPage("(//div[contains(.,'Add new user')])[10]");

        logger.info("Error handling, add user with existing mail. Check for error");

        switch (type) {
            case "brand new":
                NewCANameHolder = lorem.getFirstNameMale() + " " + lorem.getFirstNameFemale();
                break;
            case "already AH":
                NewCANameHolder = AccountPage.randomFirstName;
                NewCALastNameHolder = AccountPage.randomLastName;
                usersPage.waitTwoSeconds();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for caAddNewCA!");
        }
        usersPage.sendKeys("//input[contains(@formcontrolname,'name')]", NewCANameHolder);
        usersPage.sendKeys("//input[contains(@formcontrolname,'last_name')]", NewCALastNameHolder);
        usersPage.sendKeys("//input[contains(@formcontrolname,'email')]", "pennybridgeadmin@example.com");
        usersPage.click("(//div[contains(.,'add user')])[11]");
        assertTrue(usersPage.isElementDisplayed("//mat-error[contains(.,'Email is already used')]"));
        usersPage.clearInputFieldUsingBackSpaceKey("//input[contains(@formcontrolname,'email')]");
        // usersPage.sendKeys("//input[contains(@formcontrolname,'email')]", "rishabhsinghlambdatest@clubforce.com");
        usersPage.click("//button[contains(.,'add user')]");

        logger.info("No toggle is enabled");
        usersPage.waitTwoSeconds();
        assertTrue(usersPage.isElementDisplayed("//span[contains(.,'The given data was invalid.')]"));
        logger.info("Error message is displayed");

        logger.info("Enable Club admin toggle");
        usersPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[1]");
        usersPage.click("//button[contains(.,'add user')]");
        usersPage.waitTwoSeconds();
        assertTrue(usersPage.isElementDisplayed(
                "//span[contains(.,'This email is already used by an admin or group manager of another Clubforce club.')]"));
        logger.info("Error handling ok");

        logger.info("Refresh page and fill in all required details");
        usersPage.refreshPage();
        usersPage.sendKeys("//input[contains(@formcontrolname,'name')]", NewCANameHolder);
        usersPage.sendKeys("//input[contains(@formcontrolname,'last_name')]", NewCALastNameHolder);

        switch (type) {
            case "brand new":
                if (envName.contains("prod")) {
                    RandomMailHolder = "wnzt4." + RandomStringUtils.randomAlphabetic(7).toLowerCase()
                            + "@inbox.testmail.app";
                } else {
                    String userName = NewCANameHolder.replaceAll(" ", "");
                    RandomMailHolder = userName.toLowerCase() + RandomStringUtils.randomNumeric(4) + "@clubforce.com";
                }
                break;
            case "already AH":
                RandomMailHolder = AccountPage.RandomMailHOLDER;
                usersPage.waitTwoSeconds();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for caAddNewCA!");
        }

        logger.info("RandomMailHolder: " + RandomMailHolder);
        usersPage.sendKeys("//input[contains(@formcontrolname,'email')]", RandomMailHolder);
        usersPage.waitTwoSeconds();

        switch (userRole) {
            case "Club admin":
                usersPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[1]");
                usersPage.waitOneSecond();
                usersPage.click("(//div[contains(.,'add user')])[11]");
                // happens too fast TODO make ticket
                // usersPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'User
                // successfully added.')]",5);
                usersPage.waitFiveSeconds();
                usersPage.findOnPage("//h1[contains(.,'Users')]");
                // usersPage.waitTwoSeconds();
                // assertFalse(usersPage.isElementDisplayed("//span[contains(.,'User
                // successfully added.')]"));
                logger.info("user has been added successfully");
                usersPage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"
                        + RandomMailHolder + "')]");
                usersPage.findOnPage("(//li[contains(.,'Admin')])[1]");
                break;
            case "Group manager":
                usersPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[2]");
                usersPage.waitOneSecond();
                usersPage.click("(//div[contains(.,'add user')])[11]");
                // happens too fast TODO make ticket
                // usersPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'User
                // successfully added.')]",5);
                usersPage.waitFiveSeconds();
                usersPage.findOnPage("//h1[contains(.,'Users')]");
                // usersPage.waitTwoSeconds();
                // assertFalse(usersPage.isElementDisplayed("//span[contains(.,'User
                // successfully added.')]"));
                logger.info("user has been added successfully");
                usersPage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"
                        + RandomMailHolder + "')]");
                usersPage.findOnPage("(//li[contains(.,'Group manager')])[1]");
                break;
            case "GM and CA":
                usersPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[1]");
                usersPage.waitOneSecond();
                usersPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[2]");
                usersPage.waitOneSecond();
                usersPage.click("(//div[contains(.,'add user')])[11]");
                // happens too fast TODO make ticket
                // usersPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'User
                // successfully added.')]",5);
                usersPage.waitFiveSeconds();
                usersPage.findOnPage("//h1[contains(.,'Users')]");
                // usersPage.waitTwoSeconds();
                // assertFalse(usersPage.isElementDisplayed("//span[contains(.,'User
                // successfully added.')]"));
                logger.info("user has been added successfully");
                usersPage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"
                        + RandomMailHolder + "')]");
                usersPage.findOnPage("(//li[contains(.,'Group manager')])[1]");
                usersPage.findOnPage("(//li[contains(.,'Admin')])[1]");
                break;
            default:
        }
        // usersPage.sendKeys( "//input[contains(@filtername,'name')]",
        // NewCANameHolder); https://clubforce.atlassian.net/browse/NG-664
        // usersPage.waitFiveSeconds();
        // assertTrue(usersPage.isElementDisplayed(String.format(UsersPage.UsersPageText,
        // RandomMailHolder)));

    }

    @Then("they can sign up to become a member using a CA account mail")
    public void caUserCreatesAHAccount() {
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Enter mail for the newly created CA ");
        loginPage.sendKeys(AccountPage.AccountPageMailField, RandomMailHolder);
        loginPage.click(LoginPage.LoginNextButton);
        logger.info("Adding a new CA");
        loginPage.findOnPage(UsersPage.CreateAccountHeading);
        assertTrue(loginPage.isElementDisplayed("//span[contains(.,'" + RandomMailHolder + "')]"),
                "Expected mail address not showing!");

        loginPage.sendKeys(UsersPage.CreateAccountFirstNameField, lorem.getFirstNameMale());
        loginPage.sendKeys(UsersPage.CreateAccountLastNameField, lorem.getLastName());
        loginPage.sendKeys(UsersPage.CreateAccountMobileField, RandomStringUtils.randomNumeric(15));
        loginPage.click(UsersPage.CreateAccountCountryField);
        loginPage.click("(//span[@class='mat-option-text'][contains(.,'Ireland')])[1]");
        loginPage.sendKeys(UsersPage.CreateAccountCountyField, lorem.getLastName());
        loginPage.sendKeys(UsersPage.CreateAccountPostCodeField, lorem.getZipCode());
        loginPage.sendKeys(UsersPage.CreateAccountCityField, lorem.getCity());
        loginPage.sendKeys(UsersPage.CreateAccountAddress1Field, lorem.getWords(3));
        loginPage.sendKeys(UsersPage.CreateAccountAddress2Field, lorem.getWords(3));
        loginPage.click(UsersPage.CreateAccountAgreeToTermsField);

        loginPage.click("//button[contains(.,'create account arrow_forward')]");
        loginPage.waitFiveSeconds();
        assertFalse(loginPage.isElementDisplayed("//button[contains(.,'create account arrow_forward')]"),
                "Still on Create modal!");
        logger.info("Create account button clicked");
    }

    @Then("CA-AH can log into myaccount")
    public void caAhCanLogInMyAccount() {
        logger.info("CA-AH can log into myaccount");
        loginPage.deleteAllCookies();
        loginPage.goToMyAccountURL("/");
        loginPage.waitTwoSeconds();
        loginPage.sendKeys(LoginPage.LoginEmailField, RandomMailHolder);
        loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
        loginPage.click(LoginPage.LoginButton);
        loginPage.waitFiveSeconds();
        assertTrue(loginPage.isElementPresent(MyAccountPage.MyAccountHeading));
        logger.info("MyAccountHeading found!");
    }

    @Then("CA-AH can log into backoffice")
    public void caAhCanLogInBackoffice() {
        logger.info("CA-AH can log into backoffice");
        loginPage.deleteAllCookies();
        loginPage.goToBackofficeLoginPage();
        loginPage.waitTwoSeconds();
        loginPage.sendKeys(LoginPage.LoginEmailField, RandomMailHolder);
        loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
        loginPage.click(LoginPage.LoginButton);
        loginPage.waitFiveSeconds();
        assertTrue(loginPage.isElementPresent(BackofficePage.DashboardHeading));
        logger.info("DashboardHeading found!");
    }

    @Then("CA-AH can log out of backoffice")
    public void caAhCanLogOutBackoffice() {
        logger.info("CA-AH can log out of backoffice");
        loginPage.click("//mat-icon[@role='img'][contains(.,'more_vert')]");
        loginPage.click("(//span[contains(.,'Sign out')])[2]");
    }

    @Then("new CA can activate their account")
    public void newCAGetActivationMail() {
        logger.info("new CA can activate their account");
        loginPage.accessMailTrapInbox();
        usersPage.click("(//span[contains(.,'Admin access')])[1]");
        usersPage.waitThreeSeconds();
        driverManager.driver.switchTo()
                .frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
        usersPage.waitFiveSeconds();
        assertTrue(usersPage.isElementPresent("//td[contains(.,'" + NewCANameHolder + "')]"));
        assertTrue(usersPage.isElementPresent("//td[contains(.,'" + RandomMailHolder + "')]"));

        logger.info("new CA click Activate account in mail");
        usersPage.click("//a[@target='_blank'][contains(.,'Activate account')]");
        usersPage.waitTwoSeconds();
        usersPage.switchToBrowserTab(1);
        usersPage.waitTwoSeconds();

        logger.info("Check we are on login page");
        assertTrue(usersPage.isElementPresent("//h2[contains(.,'Set password')]"));
        assertTrue(usersPage.isElementPresent(LoginPage.SubmitButton));

        // logger.info("Add new passwords but aren't the same (error check");
        // loginPage.sendKeys(LoginPage.LoginPasswordField,"b3deG2FnmrEy");
        // usersPage.sendKeys(LoginPage.ConfirmPasswordField,"XXXb3deG2FnmrEyXXXX");
        // loginPage.click(LoginPage.SubmitButton);
        // loginPage.waitTwoSeconds();

        logger.info("Add new password for user and log in");
        loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
        usersPage.sendKeys(LoginPage.ConfirmPasswordField, "b3deG2FnmrEy");
        loginPage.click(LoginPage.SubmitButton);
        loginPage.waitTwoSeconds();
        loginPage.findOnPage("//h1[contains(.,'Dashboard')]");

        logger.info("Log out and then in again");
        myAccountPage.click(BackofficePage.BO_MenuDots);
        myAccountPage.click(MyAccountPage.MyAccountSignOut);
        myAccountPage.deleteAllCookies();
        myAccountPage.deleteAllCookies();

        logger.info("Check we are on login page");
        assertTrue(usersPage.isElementPresent(LoginPage.SignIn));

        logger.info("Log in again with new CA credentials");
        loginPage.sendKeys(LoginPage.LoginEmailField, RandomMailHolder);
        loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
        loginPage.click(LoginPage.LoginButton);
        loginPage.findOnPage("//h1[contains(.,'Dashboard')]");
    }

    @Then("ClubAdmin see today as last login for themselves")
    public void caLastLoginIsToday() {
        logger.info("ClubAdmin see today as last login for themselves");
        usersPage.waitFiveSeconds();
        usersPage.sendKeys("//input[contains(@aria-required,'false')]", NewCANameHolder); // email doesn't work
                                                                                          // https://clubforce.atlassian.net/browse/NG-664
        usersPage.waitThreeSeconds();
        usersPage.findOnPage(
                "//div[@class='cf-collection-table-tbody ng-star-inserted'][contains(.,'" + NewCANameHolder + "')]");

        String LastLoginDate = SeleniumUtilities.getDateTimeFormat("MMM d, yyyy");
        assertTrue(usersPage.isElementDisplayed("//*[contains(text(),'" + LastLoginDate + "')]"));
    }

    @And("they resend verification email")
    public void resendVerificationEmail() {
        usersPage.findOnPage("//h4[@class='ng-star-inserted'][contains(.,'Verify your email')]");
        usersPage.findOnPage("//a[contains(.,'please click here to resend')]");
        usersPage.waitTwoSeconds();
        usersPage.click("//a[contains(.,'please click here to resend')]");
        usersPage.findOnPage("//h4[@class='ng-star-inserted'][contains(.,'Check your inbox')]");
        usersPage.findOnPage("//span[@class='ng-star-inserted'][contains(.,'Email sent, please check your inbox.')]");
    }
}
