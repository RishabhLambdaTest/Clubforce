package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class SponsorPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public SponsorPage (WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static String SponsorHeading = "//h1[contains(.,'Sponsors')]";
    public static String AddSponsorButton = "//button[contains(.,'Add Sponsor add')]";
    public static String SponsorNameField = "//input[contains(@formcontrolname,'name')]";
    public static String AddSponsorHeading = "//h1[contains(.,'Add Sponsor')]";
    public static String SponsorNameInformationText = "//mat-error[contains(.,'Name is required')]";
    public static String SponsorLinkField = "//input[contains(@formcontrolname,'link')]";
    public static final String NotificationCloseIcon = "//mat-icon[contains(.,'close')]";
    public static final String SponsorSuccessNotification = "//span[contains(.,'Sponsor created!')]";
    public static final String SponsorsPageFilePath = "//input[@type='file']";
}
