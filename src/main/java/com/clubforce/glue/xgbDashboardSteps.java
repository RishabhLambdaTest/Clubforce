package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;

public class xgbDashboardSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();
    WebDriverManager driverManager;

    public xgbDashboardSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.xgbTemplatePage = driverManager.xgbTemplatePage;
        this.xgbDashboardPage = driverManager.xgbDashboardPage;
    }

    @Then("Admin can see Dashboard items")
    public void checkDashboardItems() {
        xgbDashboardPage.waitTenSeconds();
        xgbDashboardPage.findOnPage("//h1[@data-test='dashboard.title'][contains(.,'Dashboard')]");
        xgbDashboardPage.waitForElementDisplayedByXpathWithTimeout("//span[@class='submitMidHighlightText'][contains(.,'Apply')]", 30);
        xgbDashboardPage.findOnPage("//div[@class='filterList'][contains(.,'Club')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Gender Balance')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Participant Registrations')]");
        xgbDashboardPage.findOnPage("//img[contains(@class,'dateSliderPreview')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Members by County')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Members by Club')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Members by Age Group & Gender')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Nationalities')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Cross Club Membership')]");
        xgbDashboardPage.findOnPage("//*[contains(text(),'Template Detail')]");

        assertFalse(xgbDashboardPage.isElementDisplayed("//*[contains(text(),'Error running report')]"));
        assertFalse(xgbDashboardPage.isElementDisplayed("//*[contains(text(),'A connection could not be established to the source database')]"));

    }

    @When("Admin go to XGB {string} page")
    public void adminGoesToBackofficePage(String page) {
        switch (page) {
            case "Templates":
                logger.info("Going to "+page+" page");
                xgbDashboardPage.click(xgbTemplatePage.LeftNavNewsTemplates);
                xgbDashboardPage.findOnPage(String.format(LoginPage.LoginPageText, "Templates"));
                xgbDashboardPage.waitTwoSeconds();
                assertFalse(xgbDashboardPage.isElementDisplayed("//h4[contains(.,'Error fetching templates')]"));
                break;
            case "xxxxxxxxxxxxxxxxx":
                logger.info("Going to "+page+" page");
                xgbDashboardPage.click(BackofficePage.LeftNavSectionsAndPages);
                xgbDashboardPage.findOnPage(String.format(LoginPage.LoginPageText, "Published Date"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminGoesToBackofficePage!");
        }
    }
}
