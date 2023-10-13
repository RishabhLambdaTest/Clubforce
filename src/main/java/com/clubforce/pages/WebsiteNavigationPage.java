package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class WebsiteNavigationPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public WebsiteNavigationPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String WebsiteNavigationPageMenuItemCheckbox = "(//mat-pseudo-checkbox[@class='mat-pseudo-checkbox ng-star-inserted'])[1]";
    public static final String WebsiteNavigationPageRemoveButton = "//button[contains(.,'Remove')]";
    public static final String WebsiteNavigationHeading = "//h1[contains(.,'Website navigation')]";
    public static final String WebsiteNavigationInformationText = "//mat-hint[contains(.,'Add external links to your menu, e.g. your existing lotto page. Drag to change order.')]";
    public static final String AddWebsiteNavigationItemButton = "//button[contains(.,'Add Navigation Item add')]";
    public static final String WebsiteNavigationMenuTitleField = BackofficePage.FormControlNameTitle;
    public static final String WebsiteNavigationPopUpCreateButton = "//button[contains(.,'Create')]";
    public static final String WebsiteNavigationSuccessfullyCreatedNotification = "//span[contains(.,'Successfully created!')]";
    public static final String WebsiteNavigationPageCloseIcon = "//mat-icon[contains(.,'close')]";
    public static final String WebsiteNavigationMenuURLItemSelector = "//span[contains(.,'URL')]";
    public static final String WebsiteNavigationLottoMenuItemSelector = "//span[@class='mat-option-text'][contains(.,'Lotto')]";
    public static final String WebsiteNavigationProductItemSelector = "//span[@class='mat-option-text'][contains(.,'Product')]";
    public static final String WebsiteNavigationMenuItemSelector = "//div[contains(@class,'mat-select-arrow')]";
    public static final String ProductLinkDropdown = "(//div[contains(.,'Product Link')])[10]";
    public static final String WebsiteSettingMenuPageItemSelector = "//span[@class='mat-option-text'][contains(.,'Page')]";
    public static final String WebsiteSettingsContentPageLink = "(//div[contains(.,'Content Page Link')])[10]";
    public static final String WebsiteSettingContentLinkPagePageOption = "//span[@class='mat-option-text'][contains(.,'PagePage')]";
}
