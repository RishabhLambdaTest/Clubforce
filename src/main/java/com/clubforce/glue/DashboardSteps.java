package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficeDashboardPage;
import com.clubforce.pages.BackofficePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertTrue;

public class DashboardSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public DashboardSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficeDashboardPage = driverManager.backofficeDashboardPage;
        this.backofficePage = driverManager.backofficePage;
    }

    public static String totalRevenueCurrency;
    public static String currentDrawCurrency;

    @Then("the {string} works as expected on the {string} tile")
    public void interactWithDashboardTiles(String buttonName, String tile) {
        backofficeDashboardPage.waitForElementDisplayedByXpathWithTimeout(BackofficeDashboardPage.ViewWebsiteDashboardTileButton,15);
        logger.info("Clicking button " + buttonName);
        switch(tile) {
            case "Manage Lotto":
                backofficeDashboardPage.findOnPage(BackofficeDashboardPage.ManageLottoDashboardText);
                backofficeDashboardPage.findOnPage(BackofficeDashboardPage.CurrentDrawText);
                checkButtonOption(buttonName);
                break;
            case "Your club's website":
                backofficeDashboardPage.findOnPage(String.format(BackofficeDashboardPage.BackofficeDashboardPageText, "View website"));
                backofficeDashboardPage.findOnPage(BackofficeDashboardPage.LookGoodForFansText);
                checkButtonOption(buttonName);
                break;
            case "View all articles":
                backofficeDashboardPage.findOnPage(BackofficeDashboardPage.NewsArticlesDashboardText);
                backofficeDashboardPage.findOnPage(BackofficeDashboardPage.NewsArticlesFansDashboardText);
                checkButtonOption(buttonName);
                break;
            case "Contact messages":
                checkButtonOption(buttonName);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for interactWithDashboardTiles!");
        }
    }

    public void checkButtonOption(String button) {
        switch (button) {
            case "Manage draws button":
                backofficeDashboardPage.click(BackofficeDashboardPage.ManageDrawsDashboardButton);
                backofficeDashboardPage.findOnPage(BackofficePage.LottoDrawsHeading);
                backofficeDashboardPage.findOnPage(BackofficePage.ViewPayslipsButton);
                break;
            case "Customise button":
                backofficeDashboardPage.centreElement(BackofficeDashboardPage.CustomiseThemeButton);
                backofficeDashboardPage.click(BackofficeDashboardPage.CustomiseThemeButton);
                backofficeDashboardPage.findOnPage(BackofficePage.ThemeHeading);
                break;
            case "View website button":
                backofficeDashboardPage.centreElement(BackofficeDashboardPage.ViewWebsiteButton);
                backofficeDashboardPage.click(BackofficeDashboardPage.ViewWebsiteButton);
                backofficeDashboardPage.waitFiveSeconds();
                backofficeDashboardPage.switchToBrowserTab(1);
                backofficeDashboardPage.findOnPage("//span[contains(.,'Contact')]");
                break;
            case "View all articles button":
                backofficeDashboardPage.centreElement(BackofficeDashboardPage.ViewAllArticlesButton);
                backofficeDashboardPage.click(BackofficeDashboardPage.ViewAllArticlesButton);
                backofficeDashboardPage.findOnPage(BackofficePage.ArticlesHeading);
                break;
            case "View all messages button":
                backofficeDashboardPage.centreElement(BackofficeDashboardPage.ViewAllMessagesButton);
                backofficeDashboardPage.click(BackofficeDashboardPage.ViewAllMessagesButton);
                backofficeDashboardPage.findOnPage(BackofficePage.MessagesHeading);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkButtonOption!");
        }
    }

    @Then("ZenDesk chat pops up on demand")
    public void zendeskCornerBubble() {
        backofficeDashboardPage.waitTwoSeconds();
        backofficeDashboardPage.click("//iframe[@id='launcher']");
        backofficeDashboardPage.waitTwoSeconds();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[@id='webWidget']")));
        backofficeDashboardPage.findOnPage("//span[contains(text(),'Hi! Welcome to Clubforce.')]");
        logger.info("Verified chat popup support greeting");
    }

    @And("{string} is displayed on dashboard page")
    public void checkCurrencySymbolIsDisplayedOnDashboardPage(String currencySymbol) {
        switch (currencySymbol) {
            case "Euro":
                totalRevenueCurrency = backofficeDashboardPage.getElementAttribute(BackofficeDashboardPage.TotalRevenueCurrencyAndAmount, "textContent");
                assertTrue(totalRevenueCurrency.contains("€"));
                currentDrawCurrency = backofficeDashboardPage.getElementAttribute(BackofficeDashboardPage.CurrentDrawRevenueCurrencyAndAmount, "textContent");
                assertTrue(currentDrawCurrency.contains("€"));
                break;
            case "Pound":
                totalRevenueCurrency = backofficeDashboardPage.getElementAttribute(BackofficeDashboardPage.TotalRevenueCurrencyAndAmount, "textContent");
                assertTrue(totalRevenueCurrency.contains("£"));
                currentDrawCurrency = backofficeDashboardPage.getElementAttribute(BackofficeDashboardPage.CurrentDrawRevenueCurrencyAndAmount, "textContent");
                assertTrue(currentDrawCurrency.contains("£"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for checkCurrencySymbolIsDisplayedOnDashboardPage!");
        }

    }

    @And("user click {string} tile on Dashboard")
    public void dashboardClickTile(String tile) {
        if (!backofficeDashboardPage.isElementDisplayed(BackofficeDashboardPage.DashboardPageHeading)) {
            backofficeDashboardPage.click(BackofficePage.DashboardTitleLeftNav);
            backofficeDashboardPage.findOnPage(BackofficeDashboardPage.DashboardPageHeading);
        }
        logger.info("Clicking "+tile+" tile");
        backofficeDashboardPage.click("//*[contains(text(),'"+tile+"')]");
    }
}
