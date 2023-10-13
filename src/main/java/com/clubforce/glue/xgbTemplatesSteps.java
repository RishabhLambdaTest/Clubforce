package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.LoginPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class xgbTemplatesSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();
    Lorem lorem = LoremIpsum.getInstance();

    WebDriverManager driverManager;

    public xgbTemplatesSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driverManager.driver;
        this.loginPage = driverManager.loginPage;
        this.xgbTemplatePage = driverManager.xgbTemplatePage;
    }

    @Given("XGBAdmin is logged into XGB")
    public void XGBAdminLogsInXGB() {
        logger.info("Admin goes to XGB admin URL");
        loginPage.goToXGB("/");
        loginPage.findOnPage(LoginPage.XBGLoginTitle);

        logger.info("Admin fills in login credentials");
        loginPage.sendKeys(LoginPage.LoginEmailField, xgbAdminUsername);
        loginPage.sendKeys(LoginPage.LoginPasswordField, xgbAdminPassword);
        logger.info("Admin clicks login button");
        loginPage.userClicksLoginButton();
        assertFalse(loginPage.isElementDisplayed(LoginPage.LoginInvalidCredentialsMessage), "Invalid credentials error message is displayed");
        assertFalse(loginPage.isElementDisplayed(LoginPage.UnexpectedErrorMessage), "'Something unexpected happened' error message is displayed");

        loginPage.findOnPage(backofficePage.DashboardHeading);
        logger.info("Admin has successfully logged into XGB admin.");
    }

    @Given("new XGBAdmin is logged into XGB")
    public void NewXGBAdminLogsInXGB() {
        logger.info("Admin goes to XGB admin URL");
        loginPage.goToXGB("/");
        loginPage.findOnPage(LoginPage.XBGLoginTitle);

        logger.info("Admin fills in login credentials");
        if (SuperUserSteps.XGBAccountUserMailHOLDER.contains("@clubforce.com")) {
            loginPage.sendKeys(LoginPage.LoginEmailField, SuperUserSteps.XGBAccountUserMailHOLDER);
            loginPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
        } else {
            loginPage.sendKeys(LoginPage.LoginEmailField, xgbAdminUsername);
            loginPage.sendKeys(LoginPage.LoginPasswordField, xgbAdminPassword);
        }


        logger.info("Admin clicks login button");
        loginPage.userClicksLoginButton();
        assertFalse(loginPage.isElementDisplayed(LoginPage.LoginInvalidCredentialsMessage), "Invalid credentials error message is displayed");
        assertFalse(loginPage.isElementDisplayed(LoginPage.UnexpectedErrorMessage), "'Something unexpected happened' error message is displayed");

        loginPage.findOnPage(backofficePage.DashboardHeading);
        logger.info("Admin has successfully logged into XGB admin.");
    }

    @And("Admin can see {string} template")
    public void templateIsPresent(String template_name) {
        loginPage.waitOneSecond();
        logger.info("Admin looks for "+template_name+" template");
        assertTrue(loginPage.isElementDisplayed("//strong[@data-test='template-tile.categoryName'][contains(.,'"+template_name+"')]"), template_name+" isn't present!");
    }

    @And("Admin clicks {string} template")
    public void userLogsOutBackOffice(String template_name) {
        loginPage.waitOneSecond();
        logger.info("Admin clicks "+template_name+" template");
        loginPage.clickElementBelow("//strong[@data-test='template-tile.categoryName'][contains(.,'"+template_name+"')]", "//button[contains(.,'activate')]");
    }

    @Then("they can step through activation flow")
    public void adminFillInActivatePlanSteps() {
        logger.info("admin fill in activation details");
        xgbTemplatePage.findOnPage("//h3[contains(.,'Review mandatory and optional questions')]");

        xgbTemplatePage.click("//strong[contains(.,'Add Question')]");
        xgbTemplatePage.waitTwoSeconds();
        xgbTemplatePage.centreElement("//*[contains(text(),'Disability/Special needs')]");
        xgbTemplatePage.clickElementRightOf("//*[contains(text(),'Disability/Special needs')]", "//button[contains(.,'add this question')]");
        xgbTemplatePage.waitTwoSeconds();

        logger.info("admin check question was added");
        xgbTemplatePage.click("//strong[contains(.,'back')]");
        xgbTemplatePage.findOnPage("//strong[contains(.,'Add Question')]");
        xgbTemplatePage.findOnPage("//*[contains(text(),'Disability/Special needs')]");

        logger.info("admin go to step 2");
        xgbTemplatePage.click("//button[contains(.,'continue')]");
        xgbTemplatePage.findOnPage("//p[contains(.,'Please confirm you agree for this template to be made active to clubs for membership registration')]");
        xgbTemplatePage.findOnPage("//span[@class='mat-checkbox-label'][contains(.,'Agree')]");
    }

    @And("Admin can add questions and activate template {string}")
    public void adminCanActivateTemplate(String template_name) {
        assertTrue(loginPage.isElementDisplayed("//strong[@data-test='template-tile.categoryName'][contains(.,'"+template_name+"')]"), template_name+" isn't present!");
        xgbTemplatePage.clickElementBelow("//strong[@data-test='template-tile.categoryName'][contains(.,'"+template_name+"')]", "//button[@data-test='template-tile.activateButton']");
        xgbTemplatePage.findOnPage("//h1[@data-test='template-detail.title'][contains(.,'"+template_name+"')]");
        xgbTemplatePage.findOnPage("//h3[contains(.,'Review mandatory and optional questions')]");

        logger.info("admin fill in activation details");
        assertTrue(xgbTemplatePage.isElementDisplayed("//h3[contains(.,'Review mandatory and optional questions')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("(//section[contains(.,'Review mandatory and optional questions Mandatory questions cannot be edited or removed by clubs and will require an answer from members during registration. Add Questionadd_circle_outline')])[4]"));

        logger.info("checking optional questions don't show yet");
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please select the option that applies to you')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Allergies')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please list any medical conditions / injuries / allergies / special needs the club need to be aware of')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Disability/Special needs')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Member's emergency contact name')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Member's emergency contact number')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'What is your club role?')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'ID number')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Level of experience')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Academic Year')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Playing kit size')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'I agree to inform the club of any medical conditions I have that are relevant to my participation')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please confirm you have read & agree to our Terms & Conditions')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please confirm you have read & agree to our Code of Conduct')]"));

        logger.info("Add 11 optional questions, except I agree to inform the club of any medical conditions, T&C and Code fo conduct");
        xgbTemplatePage.click("//strong[contains(.,'Add Question')]");
        xgbTemplatePage.findOnPage("//input[@data-placeholder='Start typing to filter available questions']");

        int i = 0;
        while(i < 12) {
            xgbTemplatePage.centreElement("//button[contains(.,'add this question')]");
            xgbTemplatePage.click("//button[contains(.,'add this question')]");
            i++;
        }

        xgbTemplatePage.click("//strong[contains(.,'back')]");
        xgbTemplatePage.findOnPage("//strong[contains(.,'Add Question')]");

        logger.info("checking optional questions show now");
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please select the option that applies to you')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Allergies')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please list any medical conditions / injuries / allergies / special needs the club need to be aware of')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Disability/Special needs')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Member's emergency contact name')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Member's emergency contact number')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'What is your club role?')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'ID number')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Level of experience')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Academic Year')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Playing kit size')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'I agree to inform the club of any medical conditions I have that are relevant to my participation')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please confirm you have read & agree to our Terms & Conditions')]"));
        assertFalse(xgbTemplatePage.isElementDisplayed("//*[contains(text(),'Please confirm you have read & agree to our Code of Conduct')]"));

        logger.info("admin go to step 2");
        xgbTemplatePage.click("//button[contains(.,'continue')]");
        xgbTemplatePage.waitFiveSeconds();
        assertTrue(xgbTemplatePage.isElementDisplayed("//h2[contains(.,'Activating "+template_name+" template')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//p[contains(.,'Please confirm you agree for this template to be made active to clubs for membership registration')]"));
        assertTrue(xgbTemplatePage.isElementDisplayed("//span[@class='mat-checkbox-label'][contains(.,'Agree')]"));

        logger.info("admin activates template");
        xgbTemplatePage.click("//span[@class='mat-checkbox-label'][contains(.,'Agree')]");
        xgbTemplatePage.waitOneSecond();
        xgbTemplatePage.click("//button[@data-test='submit-changes.activateButton'][contains(.,'activate')]");
    }

    @Then("they can search specific questions")
    public void theyCanSearchSpecificQuestions() {
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'Member's emergency contact number')]"));
        xgbTemplatePage.click("//strong[contains(.,'Add Question')]");
        xgbTemplatePage.findOnPage("//input[@data-placeholder='Start typing to filter available questions']");

        xgbTemplatePage.waitFiveSeconds();
        assertTrue(xgbTemplatePage.isElementPresent("//*[contains(text(),'Member's emergency contact number')]"));
        assertTrue(xgbTemplatePage.isElementPresent("//*[contains(text(),'ID number')]"));
        assertTrue(xgbTemplatePage.isElementPresent("//*[contains(text(),'Academic Year')]"));
        assertTrue(xgbTemplatePage.isElementPresent("//*[contains(text(),'I agree to inform the club of any medical conditions I have that are relevant to my participation')]"));

        xgbTemplatePage.sendKeys("//input[@data-placeholder='Start typing to filter available questions']", "Emergency contact number");
        xgbTemplatePage.findOnPage("//*[contains(text(),'Member's emergency contact number')]");
        xgbTemplatePage.waitThreeSeconds();
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'ID number')]"));
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'Academic Year')]"));
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'I agree to inform the club of any medical conditions I have that are relevant to my participation')]"));

        xgbTemplatePage.click("//button[contains(.,'add this question')]");
        xgbTemplatePage.click("//strong[contains(.,'back')]");
        xgbTemplatePage.findOnPage("//strong[contains(.,'Add Question')]");
        xgbTemplatePage.findOnPage("//*[contains(text(),'Member's emergency contact number')]");
        xgbTemplatePage.waitThreeSeconds();
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'ID number')]"));
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'Academic Year')]"));
        assertFalse(xgbTemplatePage.isElementPresent("//*[contains(text(),'I agree to inform the club of any medical conditions I have that are relevant to my participation')]"));
    }
}
