package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class BackofficeDashboardPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public BackofficeDashboardPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String BackofficeDashboardPageText = "//*[contains(text(),'%s')]";
    public static final String ManageLottoDashboardText = "//button[@data-test='cf-dashboard-tile.button'][contains(.,'edit Manage draws')]";
    public static final String CurrentDrawText = "//*[contains(text(),'Current draw')]";
    public static final String ViewWebsiteDashboardTileButton = "//button[contains(.,'open_in_new View website')]";
    public static final String LookGoodForFansText = "//p[@class='mat-body-1'][contains(.,'You have to look good for the fans!')]";
    public static final String NewsArticlesDashboardText = "//h2[contains(.,'News articles')]";
    public static final String NewsArticlesFansDashboardText = "//p[@class='mat-body-1'][contains(.,'Keep the fans up to date')]";
    public static final String ManageDrawsDashboardButton = "//button[contains(.,'edit Manage draws')]";
    public static final String CustomiseThemeButton = "//button[contains(.,'format_paint Customise')]";
    public static final String ViewWebsiteButton = "//button[contains(.,'View website')]";
    public static final String ViewAllArticlesButton = "//button[contains(.,'View all articles')]";
    public static final String ViewAllMessagesButton = "//button[contains(.,'View all messages')]";
    public static final String ThemeDashboardSecondCircle = "(//li[contains(@class,'circle-element m-1 ng-star-inserted')])[2]";
    public static final String ThemeDashboardFirstCircle = "(//li[contains(@class,'circle-element m-1 ng-star-inserted')])[1]";
    public static final String TotalRevenueCurrencyAndAmount = "(//p[contains(@data-test,'dashboard-tile-content')])[2]";
    public static final String CurrentDrawRevenueCurrencyAndAmount = "(//p[contains(@data-test,'dashboard-tile-content')])[3]";
    public static final String DashboardPageHeading = "//h1[contains(.,'Dashboard')]";
}
