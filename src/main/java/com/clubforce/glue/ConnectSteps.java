package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.ConnectPage;
import com.clubforce.pages.LoginPage;
import com.clubforce.pages.UsersPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ConnectSteps extends WebDriverManager {

    //logger
    private static final Logger logger = LogManager.getLogger();
    WebDriverManager driverManager;

    public ConnectSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficePage = driverManager.backofficePage;
        this.connectPage = driverManager.connectPage;
        this.loginPage = driverManager.loginPage;
    }

    public static String GroupManagerName;

    @And("ClubAdmin updates group managers for a group")
    public void updateGroupManagerOnConnectPage() {
        logger.info("Verify page elements");
        connectPage.findOnPage("//button[contains(.,'update group manager')]");
        connectPage.verifyGroupPageElements();

        logger.info("Click the first update group manager button");
        GroupManagerName = connectPage.getElementAttribute("//div[@class='ng-star-inserted']", "textContent");
        connectPage.click(ConnectPage.UpdateGroupManagerButton1);
        connectPage.findOnPage(ConnectPage.UpdateGroupManagerHeading);
        connectPage.findOnPage(ConnectPage.UpdateButton);
        connectPage.findOnPage(ConnectPage.CancelButton);

        logger.info("Remove any current group managers");
        while (backofficePage.isElementDisplayed("//mat-icon[@role='img'][contains(.,'close')]")) {
            backofficePage.click("//mat-icon[@role='img'][contains(.,'close')]");
        }

        logger.info("Check error message when no group managers");
        backofficePage.click("//h2[contains(.,'Update group manager')]");
        backofficePage.findOnPage("//mat-error[contains(.,'At least one group manager is required')]");
        backofficePage.clickElementBelow("//h2[contains(.,'Update group manager')]", "//mat-form-field[@appearance='outline'][contains(.,'Group managers')]");

        logger.info("Add all managers");
        while (backofficePage.isElementDisplayed("//span[@class='mat-option-text']")) {
            backofficePage.click("//span[@class='mat-option-text']");
        }

        connectPage.click(ConnectPage.UpdateButton);
        connectPage.findOnPage("//div[@class='ng-star-inserted'][contains(.,'"+GroupManagerName+"')]");
    }

    @Then("updated group manager access email shows in my mail client")
    public void getUpdatedGroupManagerEmail() {
        logger.info("Open new tab so we can keep the CF code window open");
        ((JavascriptExecutor) driverManager.driver).executeScript("window.open()");
        connectPage.switchToBrowserTab(1);
        if (envName.contains("prod")) {
            logger.info("Getting code from TestMailApp");
            connectPage.goTo_URL("https://api.testmail.app/api/json?apikey=2a835032-6507-4e9e-8da9-153c8a33d44f&namespace=wnzt4&pretty=true");
        } else {
            logger.info("Getting to MailTrap");
            loginPage.accessMailTrapInbox();
            logger.info("Clicking mail");
        }

        connectPage.waitFiveSeconds();
        connectPage.click("//*[contains(text(),'You have been made the Group Manager of')]");
        connectPage.waitTwoSeconds();
        connectPage.click("//div[contains(@id,'tablist')]//*[contains(text(),'Text')]");
        connectPage.waitFiveSeconds();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message text')]")));
        connectPage.waitFiveSeconds();
        connectPage.findOnPage("//*[contains(text(),' You have been made the Group Manager of')]");
        connectPage.findOnPage("//*[contains(text(),'If you haven’t already downloaded the Clubforce Connect app, you can download it via the links below and log in using your Clubforce account credentials.')]");
        connectPage.findOnPage("//*[contains(text(),'App Store')]");
        connectPage.findOnPage("//*[contains(text(),'Play Store')]");
        connectPage.switchToBrowserTab(0);
    }

    @And("ClubAdmin removes role for the GM of one of the groups")
    public void getGroupManagerName() {
        logger.info("Verify page elements");
        connectPage.waitFiveSeconds();
        connectPage.verifyGroupPageElements();

        logger.info("Get name of group manager for first group");
        GroupManagerName = connectPage.getElementAttribute("//div[@class='ng-star-inserted']", "textContent");

        if (GroupManagerName.contentEquals("")) {
            logger.info("Add a group manager for the first group if their is no group manager");
            connectPage.click("(//button[contains(.,'update group manager')])[1]");
            connectPage.click("(//span[contains(@class,'mat-checkbox-inner-container')])[1]");
            connectPage.click(ConnectPage.UpdateButton);
            connectPage.waitThreeSeconds();
            GroupManagerName = connectPage.getElementAttribute("//div[@class='ng-star-inserted']", "textContent");
        }

        logger.info("ClubAdmin goes to users page to remove the role for the GM of the group");
        if (!connectPage.isElementDisplayed(BackofficePage.LeftNavUsersText)) {
            connectPage.click(BackofficePage.BackofficeLeftNavSettings);
        }
        connectPage.click(BackofficePage.LeftNavUsersText);
        connectPage.findOnPage(String.format(LoginPage.LoginPageText, "Created"));

        logger.info("Search for group manager : " + GroupManagerName);
        connectPage.click(UsersPage.UsersPageSearchField);
        connectPage.clearInputFieldUsingBackSpaceKey(UsersPage.UsersPageSearchField);
        connectPage.sendKeys(UsersPage.UsersPageSearchField, GroupManagerName.substring(0,5));
        connectPage.waitFiveSeconds();

        logger.info("Click on the user : " + GroupManagerName);
        connectPage.findOnPage("//div[@class='ng-star-inserted'][contains(.,'"+GroupManagerName+"')]");

        logger.info("Remove the users role");
        connectPage.findOnPage("(//div[contains(.,'User details')])[10]");
        connectPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[2]");
        connectPage.click("(//div[contains(.,'update details')])[11]");
    }

    @Then("user should not be displayed as a group manager on the Connect page for any group")
    public void checkGroupManagerNotDisplayedOnConnectPage() {
        logger.info("Check that group manager is not displayed in the Group manager column");
        connectPage.waitFiveSeconds();
        assertFalse(connectPage.isElementDisplayed("//*[contains(text(),'"+GroupManagerName+"')]"));

        logger.info("Check that group manager is not displayed in the Group manager list");
        connectPage.click("(//button[contains(.,'update group manager')])[1]");
        connectPage.waitFiveSeconds();
        connectPage.findOnPage(ConnectPage.UpdateGroupManagerHeading);
        assertFalse(connectPage.isElementDisplayed("//*[contains(text(),'"+GroupManagerName+"')]"));
        connectPage.click(ConnectPage.CancelButton);
        connectPage.findOnPage(ConnectPage.ConnectGroupHeading);
    }

    @Then("the page shows no groups")
    public void connectGroupsPageEmptyState() {
        logger.info("Check empty state");
        connectPage.findOnPage(ConnectPage.NoGroupsFound);
    }

    @And("ClubAmin assigns GM role back to user")
    public void assignRoleBackToGroupManager() {
        logger.info("ClubAdmin goes to users page to remove the role for the GM of the group");
        if (!connectPage.isElementDisplayed(BackofficePage.LeftNavUsersText)) {
            connectPage.click(BackofficePage.BackofficeLeftNavSettings);
        }
        connectPage.click(BackofficePage.LeftNavUsersText);
        connectPage.findOnPage(String.format(LoginPage.LoginPageText, "Created"));

        logger.info("Search for group manager");
        connectPage.click(UsersPage.UsersPageSearchField);
        connectPage.clearInputFieldUsingBackSpaceKey(UsersPage.UsersPageSearchField);
        connectPage.sendKeys(UsersPage.UsersPageSearchField, GroupManagerName.substring(0,2));

        logger.info("Click on the user : " + GroupManagerName);
        connectPage.click("//div[@class='col-md p-3 col-6'][contains(.,'"+GroupManagerName+"')]");

        logger.info("Remove the users role");
        connectPage.findOnPage("(//div[contains(.,'User details')])[10]");
        connectPage.click("(//div[contains(@class,'mat-slide-toggle-bar')])[2]");
        connectPage.click("(//div[contains(.,'update details')])[11]");
    }

    @And("user should be displayed in GM list on connect page")
    public void checkGroupManagerIsDisplayedInGroupManagerList() {
        logger.info("Check that group manager is not displayed in the Group manager list");
        connectPage.click("(//button[contains(.,'update group manager')])[1]");
        connectPage.waitFiveSeconds();
        connectPage.findOnPage(ConnectPage.UpdateGroupManagerHeading);
        assertTrue(connectPage.isElementDisplayed("//*[contains(text(),'"+GroupManagerName+"')]"));
    }

    @And("user can be added again to the group")
    public void addGroupManagerUserBackToGroup() {
        connectPage.click("//span[@class='mat-checkbox-label'][contains(.,'"+GroupManagerName+"')]");
        connectPage.click(ConnectPage.UpdateButton);
        connectPage.waitFiveSeconds();
        assertTrue(connectPage.isElementDisplayed("//div[@class='col-md p-3 col-6'][contains(.,'"+GroupManagerName+"')]"));
    }
}







