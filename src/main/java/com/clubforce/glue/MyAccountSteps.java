package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.LoginPage;
import com.clubforce.pages.MyAccountPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MyAccountSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public MyAccountSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.myAccountPage = driverManager.myAccountPage;
        this.backofficePage = driverManager.backofficePage;
    }

    @And("clicking member leftNav icons or text lead to correct pages")
    public void memberLeftNavClickValidations() {
        logger.info("Checking icons");
        myAccountPage.click(BackofficePage.LeftNavFolder);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Documents")));

        myAccountPage.click(BackofficePage.LeftNavUsersText);

        myAccountPage.click(BackofficePage.LeftNavCard);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Payment Methods")));

        myAccountPage.click(BackofficePage.LeftNavRugbyBall);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Memberships")));

        myAccountPage.click(BackofficePage.LeftNavShopBag);
        myAccountPage.findOnPage(BackofficePage.PaginationText);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "Orders")));

        myAccountPage.click(BackofficePage.LeftNavStar);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Clubs")));

        logger.info("Checking text, toggle leftNav open if needed");
        backofficePage.openBackOfficeLeftSideNav();

        myAccountPage.click(BackofficePage.LeftNavDocuments);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Documents")));

        myAccountPage.click(BackofficePage.LeftNavGroups);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Groups")));

        myAccountPage.click(MyAccountPage.LeftNavPaymentMethods);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Payment Methods")));

        myAccountPage.click(BackofficePage.LeftNavMemberships);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Memberships")));

        myAccountPage.click(BackofficePage.LeftNavOrders);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "Orders")));

        myAccountPage.click(BackofficePage.LeftNavClubs);
        assertTrue(myAccountPage.isElementPresent(String.format(LoginPage.LoginPageText, "My Clubs")));
    }

    @And("ClubMember goes to {string} page")
    public void userGoesToPage(String page) {
        switch (page) {
            case "Notifications":
                logger.info("Going to " + page + " page");
                myAccountPage.click(MyAccountPage.MyAccountSettingsLeftNav);
                myAccountPage.click(MyAccountPage.LeftNavNotificationSection);
                myAccountPage.findOnPage(MyAccountPage.NotificationsHeadingText);
                break;
            case "Orders":
                logger.info("Going to " + page + " page");
                myAccountPage.click("//span[@class='ms-2'][contains(.,'Orders')]");
                myAccountPage.findOnPage("//h1[contains(.,'Orders')]");
                break;
            case "Membership":
                logger.info("Going to " + page + " page");
                myAccountPage.click("//span[@class='ms-2'][contains(.,'Membership')]");
                myAccountPage.findOnPage("//h1[contains(.,'Membership')]");
                break;
            case "Lotto":
                logger.info("Going to " + page + " page");
                myAccountPage.click("(//span[contains(.,'fiber_smart_recordLotto')])[1]");
                myAccountPage.findOnPage("//h1[contains(.,'Lotto')]");
                break;
            case "Payment Methods":
                logger.info("Going to " + page + " page");
                myAccountPage.waitTwoSeconds();
                myAccountPage.click(MyAccountPage.LeftNavPaymentMethods);
                myAccountPage.findOnPage(MyAccountPage.PaymentMethodHeading);
                break;
            case "Groups":
                break;
            case "Documents":
                break;
            case "Member details":
                break;
            default:
                throw new NotFoundException("For some reason there is no case for userGoesToPage!");
        }
    }

    @Then("ClubMember performs action {string}")
    public void userPerformsAction(String action) {
        myAccountPage.waitOneSecond();
        switch (action) {
            case "subscribesToAllClubs":
                logger.info("ClubMember subscribes to all notifications");
                String xpathToCheck;
                myAccountPage.waitTwoSeconds();
                int i = 1;
                if (envName.contains("prod")) {
                    while(i <= 4) {
                        xpathToCheck = "(//span[@class='mat-checkbox-inner-container'])["+i+"]//input[contains(@aria-checked,'false')]";
                        if (myAccountPage.isElementPresent(xpathToCheck)) {
                            myAccountPage.centreElement(xpathToCheck);
                            myAccountPage.click(("(//span[@class='mat-checkbox-inner-container'])["+i+"]"));
                        }
//                        myAccountPage.waitFiveSeconds();
                        logger.info("Checkbox " + i + " of " + 4 + " is checked");
                        i++;
                    }
                } else {
                    while(i <=74) {
                        xpathToCheck = "(//span[contains(@class,'mat-checkbox-inner-container')])["+i+"]//input[contains(@aria-checked,'false')]";
                        if (myAccountPage.isElementPresent(xpathToCheck)) {
                            myAccountPage.click("(//span[@class='mat-checkbox-inner-container'])["+i+"]");
                        }
//                        myAccountPage.waitTenSeconds();
                        logger.info("Checkbox " + i + " of " + 66 + " is checked");
                        assertFalse(myAccountPage.isElementDisplayed(xpathToCheck));
                        i++;
                    }
                }
                myAccountPage.findOnPage("(//div[contains(.,'You will still receive order confirmations, password reminders and other transactional messages')])[10]");
                myAccountPage.click("//button[contains(.,'Save')]");
                myAccountPage.findOnPage("//span[contains(.,'Notifications has been updated successfully.')]");
                myAccountPage.click("//mat-icon[contains(.,'close')]");
                break;
            case "unsubscribesFromAllClubs":
                logger.info("ClubMember unsubscribes from all notifications");
                myAccountPage.click(MyAccountPage.UnsubscribeFromAllButton);
                myAccountPage.waitOneSecond();
                break;
            case "Remove all cards":
                while(myAccountPage.isElementPresent("(//mat-icon[@role='img'][contains(.,'delete')])[1]")) {
                    myAccountPage.click(MyAccountPage.DeleteCardIcon);
                    myAccountPage.click(MyAccountPage.DeleteCardButton);
                    myAccountPage.refreshPage();
                }
                assertFalse(myAccountPage.isElementPresent("(//mat-icon[@role='img'][contains(.,'delete')])[1]"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for userPerformsAction!");
        }
    }

    @Then("signs out of their Account")
    public void signOutOfMyAccount() {
        myAccountPage.waitOneSecond();
        myAccountPage.click(BackofficePage.BO_MenuDots);
        myAccountPage.click(MyAccountPage.MyAccountSignOut);
        myAccountPage.deleteAllCookies();
        myAccountPage.deleteAllCookies();
    }

    @Then("recurring card shows in Payment Methods")
    public void recurringCardCiaranShows() {
        myAccountPage.waitOneSecond();
        assertTrue(myAccountPage.isElementPresent(MyAccountPage.CiaranNameOnCard));
    }

    @And ("updated notifications changes remain for {string}")
    public void verifyPageElements(String action) {
        logger.info("Checking that updated notifications remain");
        myAccountPage.waitFiveSeconds();
        String xpathToCheck;
        switch (action) {
            case "subscribesToAllClubs":
                if (envName.contains("prod")) {
                    for(int i = 1; i <= 4; i++) {
                        xpathToCheck = "(//span[@class='mat-checkbox-inner-container'])["+i+"]//input[contains(@aria-checked,'false')]";
                        assertFalse(myAccountPage.isElementPresent(xpathToCheck));
                        logger.info("Checkbox " + i + " is checked");
                        myAccountPage.waitOneSecond();
                    }
                }

                if (envName.contains("test")) {
                    for(int i = 1; i <=66; i++) {
                        xpathToCheck = "(//span[@class='mat-checkbox-inner-container'])["+i+"]//input[contains(@aria-checked,'false')]";
                        assertFalse(myAccountPage.isElementPresent(xpathToCheck));
                        logger.info("Checkbox " + i + " is checked");
                    }
                }

                break;
            case "unsubscribesFromAllClubs":

                if (envName.contains("prod")) {
                    for(int i = 1; i <= 4; i++) {
                        xpathToCheck = "(//span[@class='mat-checkbox-inner-container'])["+i+"]//input[contains(@aria-checked,'false')]";
                        assertTrue(myAccountPage.isElementPresent(xpathToCheck));
                        logger.info("Checkbox " + i + " is unchecked");
                        myAccountPage.waitOneSecond();
                    }
                }

                if (envName.contains("test")) {
                    for(int i = 1; i <= 54; i++) {
                        xpathToCheck = "(//span[@class='mat-checkbox-inner-container'])["+i+"]//input[contains(@aria-checked,'false')]";
                        assertTrue(myAccountPage.isElementPresent(xpathToCheck));
                        logger.info("Checkbox " + i + " is unchecked");
                    }
                }

                break;
            default:
                throw new NotFoundException("For some reason there is no case for verifyPageElements!");
        }

        myAccountPage.deleteAllCookies();
        myAccountPage.deleteAllCookies();
    }

}
