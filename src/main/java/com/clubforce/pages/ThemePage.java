package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ThemePage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public ThemePage (WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    private static final Logger logger = LogManager.getLogger();
    public static final String ThemeHeading = "//h1[contains(.,'Theme')]";
    public static final String PrimaryColourFieldDropDownField = "//div[contains(@id,'mat-select-value-1')]";
    public static final String PrimaryColourFieldDropDownFieldDefault = "(//div[contains(.,'Primary colour')])[12]";
    public static final String SecondaryColourFieldDropDownFieldDefault = "(//span[contains(.,'Teal')])[2]";
    public static final String ThemeColourPurple = "//span[@class='d-block py-3 text-center'][contains(.,'Purple')]";
    public static final String ThemeColourAmber = "//span[@class='d-block py-3 text-center'][contains(.,'Amber')]";
    public static final String ThemeColourBlue = "//span[@class='d-block py-3 text-center'][contains(.,'Blue')]";
    public static final String ThemeColourRed = "//span[@class='d-block py-3 text-center'][contains(.,'Red')]";
    public static final String ThemeColourTeal = "//span[@class='d-block py-3 text-center'][contains(.,'Teal')]";
    public static final String ThemeColourGreen = "//span[@class='d-block py-3 text-center'][contains(.,'Green')]";
    public static final String ColourSchemeHeading = "//h3[contains(.,'Colour scheme')]";
    public static final String TealDefaultSecondaryColourDropdownField = "//div[contains(@id,'mat-select-value-3')]";
    public static final String ThemePageTopUpdateButton = "//button[contains(.,'Update')]";
    public static final String SUThemePageUpdateButton = "//button[contains(.,'update arrow_forward')]";
    public static final String ThemeUpdatedSuccessMessage = "//span[contains(.,'Theme updated')]";
    public static final String NotificationCloseIcon = "//mat-icon[contains(.,'close')]";
    public static final String ThemePageDescription = "//mat-hint[contains(.,'Control your logo, colour scheme and club name display.')]";
    public static final String ThemePageClubNameInformation = "//mat-hint[contains(.,'Displays as page title, header on all pages and google search.')]";
    public static final String ThemePageColourSchemeInformation = "//mat-hint[contains(.,'Customise website to match your club colours.')]";
    public static final String ThemePagePrimaryColourInformation = "//mat-hint[contains(.,'Main colour used for buttons and links.')]";
    public static final String ThemePageSecondaryColourInformation = "//mat-hint[contains(.,'Main colour used for backdrops.')]";
    public static final String AddressSettingsHeadingThemePage = "//h3[contains(.,'Address settings')]";
    public static final String AddressSettingsInformation = "//mat-hint[contains(.,'Customise website to show or hide address on contact page.')]";
    public static final String ThemePageLogoHeading = "//h3[contains(.,'Logo')]";
    public static final String LogoInformation = "//mat-hint[contains(.,'Size must be 200 pixels x 200 pixels. Displayed on each page.')]";
    public static final String SUThemeStepPrimaryColourDropDownField = "(//div[contains(.,'Primary colour')])[15]";
    public static final String SUThemeStepSecondaryColourDropDownField = "(//div[contains(.,'Secondary colour')])[15]";
    public static final String ShowAddressToggleOFF = "//mat-slide-toggle[contains(@formcontrolname,'display_address')]//input[contains(@aria-checked,'false')]";
    public static final String ThemeAddressToggle = "//div[contains(@class,'mat-slide-toggle-bar')]";
    public static final String ThemePageUpdateTopButton = "//button[@form='form'][contains(.,'Update')]";
    public static final String SearchForLocationField = "//button[contains(.,'search')]";
    public static final String ThemePageMap = "//*[@id=\"map\"]";
    public static final String ShowAddressToggleON = "//mat-slide-toggle[contains(@formcontrolname,'display_address')]//input[contains(@aria-checked,'true')]";
    public static final String LogoImageOnThemePage = "//img[contains(@alt,'cropped image')]";

    public void dateUploadLogoImage() {
        logger.info("If day is even we use a cross logo");
        logger.info("If day is odd we use a circle logo");
        String dayString = SeleniumUtilities.getDateTimeFormat("dd");
        int dayNumber = Integer.parseInt(dayString);

        if (dayNumber % 2 == 0) {
            logger.info("Day is: " + dayNumber + " which is even, uploading logoCircle");
            uploadFileUsingJSExec("logoCircle.jpg", "//input[@type='file']");
        } else {
            logger.info("Day is: " + dayNumber + " which is odd, uploading cross");
            uploadFileUsingJSExec("logoCross.jpg", "//input[@type='file']");
        }
    }

    public void dateCompareLogoImages() throws Exception {
        String dayString = SeleniumUtilities.getDateTimeFormat("dd");
        int dayNumber = Integer.parseInt(dayString);
        logger.info("Taking screenshot of the open tab and comparing to an expected image");
        FileUtils.forceDelete(new File("src/main/resources/screenshots/visCompare/todayLogoScreenshot.png"));

        takePageScreenshot("todayLogoScreenshot");

        if (dayNumber % 2 == 0) {
            logger.info("Day is: " + dayNumber + " which is an even number, comparing to circle");
            compareImages("src/main/resources/screenshots/visCompare/ExpectedCircleScreenshotLogo.png",
                    "src/main/resources/screenshots/visCompare/todayLogoScreenshot.png");
        } else {
            logger.info("Day is: " + dayNumber + " which is en odd number, comparing to cross");
            compareImages("src/main/resources/screenshots/visCompare/ExpectedCrossScreenshotLogo.png",
                    "src/main/resources/screenshots/visCompare/todayLogoScreenshot.png");
        }
    }
}
