package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class BannerPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public BannerPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static String BannerPageHeading = "//h1[contains(.,'Banner')]";
    public static String BannerInformationText = "//mat-hint[contains(.,'Image and text at top of each page.')]";
    public static String BannerTitleInformationText = "//mat-hint[contains(.,'Text placed on image on homepage only.')]";
    public static String BannerContentFieldInformationText = "//mat-hint[contains(.,'Longer text on image on homepage only.')]";
    public static String BannerButtonInformationText = "//mat-hint[contains(.,'Link to news article or any link')]";
    public static String BannerImageHeading = "//h3[contains(.,'Image')]";
    public static String BannerImageInformationText = "//small[contains(.,'One image only. Minimum size must be 1600 pixels x 900 pixels. This is your main image displayed at the top of each page.')]";
    public static String BannerCreatedSuccessMessage = "//span[contains(.,'Banner created!')]";
    public static String BannerPageFilePath = "//input[@type='file']";
}
