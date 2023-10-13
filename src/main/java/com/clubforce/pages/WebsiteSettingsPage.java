package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class WebsiteSettingsPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public WebsiteSettingsPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String WebsiteSettingsPageText = "//*[contains(text(),'%s')]";
    public static final String UpdateTopButton = "//button[contains(.,'Update')]";
    public static final String UpdateBottomButton = "(//button[contains(.,'Update')])[2]";
    public static final String WebsiteSettingsSaveButton = "//button[contains(.,'Save')]";
    public static final String WebsiteSettingsBannerSection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Banner')]";
    public static final String WebsiteSettingsBannerTitleField = "//input[contains(@formcontrolname,'title')]";
    public static final String WebsiteSettingsContentTitleField = "//textarea[contains(@formcontrolname,'short_content')]";
    public static final String WebsiteSettingsViewMoreButtonDropdown = "//div[contains(@id,'mat-select-value-1')]";
    public static final String WebsiteSettingsViewMoreButtonNoButtonOption = "//span[@class='mat-option-text'][contains(.,'No button')]";
    public static final String WebsiteSettingViewMoreButtonURLOption = "//span[@class='mat-option-text'][contains(.,'URL')]";
    public static final String WebsiteSettingsBannerRemoveImageButton = "//span[contains(.,'delete Remove Image')]";
    public static final String WebsiteSettingsViewMoreButtonClubPage = "//button[contains(.,'View More')]";
    public static final String WebsiteSettingsURLField = "//input[contains(@formcontrolname,'url')]";
    public static final String WebsiteSettingsViewMoreURLField = "//input[contains(@formcontrolname,'url')]";
    public static final String WebsiteSettingsMenuSection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Website navigation')]";
    public static final String WebsiteSettingsMenuTitleField = "//input[contains(@formcontrolname,'title')]";
    public static final String WebsiteSettingsMenuURLField = "//input[contains(@formcontrolname,'url')]";
    public static final String WebsiteSettingsMenuItemSelector = "//div[contains(@class,'mat-select-arrow')]";
    public static final String WebsiteSettingMenuURLItemSelector = "//span[contains(.,'URL')]";
    public static final String WebsiteSettingMenuPageItemSelector = "//span[@class='mat-option-text'][contains(.,'Page')]";
    public static final String WebsiteSettingsItemCheckbox = "(//mat-pseudo-checkbox[@class='mat-pseudo-checkbox ng-star-inserted'])[1]";
    public static final String WebsiteSettingsAddButton = "//button[contains(.,'Add Sponsor add')]";
    public static final String WebsiteSettingsItemRemoveButton = "//button[contains(.,'Remove')]";
    public static final String WebsiteSettingsSponsorNameField = "//input[contains(@formcontrolname,'name')]";
    public static final String WebsiteSettingsSponsorLinkField = "//input[contains(@formcontrolname,'link')]";
    public static final String FooterSuccessMessage = "//span[contains(.,'Successfully created!')]";
    public static final String WebsiteSettingsSponsorSuccessMessage = "//span[contains(.,'Sponsor created!')]";
    public static final String WebsiteSettingsClubNameField = "//input[contains(@formcontrolname,'name')]";
    public static final String WebsiteSettingsRemoveButton = "(//span[contains(.,'Remove')])[2]";
    public static final String WebsiteSettingsContentPageLink = "(//div[contains(.,'Content Page Link')])[10]";
    public static final String WebsiteSettingContentLinkPagePageOption = "//span[@class='mat-option-text'][contains(.,'PagePage')]";
    public static final String WebsiteSettingsMenuMaxInstance1 = "//span[@class='mat-button-wrapper'][contains(.,'Menu item 88')]";
    public static final String WebsiteSettingsMenuMaxInstance2 = "//span[@class='mat-button-wrapper'][contains(.,'Menu item 87')]";
    public static final String WebsiteSettingsMenuMaxInstance3 = "//span[@class='mat-button-wrapper'][contains(.,'Menu item 86')]";
    public static final String WebsiteSettingsMenuMaxInstance4 = "//span[@class='mat-button-wrapper'][contains(.,'Menu item 85')]";
    public static final String WebsiteSettingsMoreDropdownMenuMaxInstance1 = "//a[@data-bs-toggle='collapse'][contains(.,'Menu item 85')]";
    public static final String WebsiteSettingsMoreDropdownMenuMaxInstance2 = "//a[@data-bs-toggle='collapse'][contains(.,'Menu item 84')]";
    public static final String WebsiteSettingsMoreDropdownMenuMaxInstance3 = "//a[@data-bs-toggle='collapse'][contains(.,'Menu item 83')]";
    public static final String WebsiteSettingsMoreDropdownMenuMaxInstance4 = "//a[@data-bs-toggle='collapse'][contains(.,'Menu item 82')]";
    public static final String WebsiteSettingsMoreDropdownMenuMaxInstance5 = "//a[@data-bs-toggle='collapse'][contains(.,'Menu item 81')]";
    public static final String WebsiteSettingsMoreDropDown = "//span[contains(.,'More...')]";
    public static final String WebsiteSettingUploadImage = "//button[contains(.,'backup Upload Image')]";
    public static final String ThemeUpdatedSnackbar = "//span[contains(.,'Theme updated')]";
    public static final String LogoFrameColour = "//a[contains(@style,'background-color: rgb(')]";
    public static final String AddNavigationItemButton = "//button[contains(.,'Add Navigation Item add')]";
    public static final String DeleteIcon = "(//mat-icon[contains(.,'delete')])[1]";
    public static final String PublishButton = "//button[contains(.,'Publish')]";
 }
