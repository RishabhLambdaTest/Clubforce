package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class xgbTemplatePage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public xgbTemplatePage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String XBGLoginTitle = "//h2[contains(.,'XGB Sign in')]";
    public static final String SignIn = "//button[contains(.,'Sign in')]";
    public static final String LeftNavNewsTemplates = "//span[@class='ms-2'][contains(.,'Templates')]";
    public static final String LoginEmailField = "//input[contains(@formcontrolname,'email')]";
    public static final String LoginPasswordField = "//input[contains(@formcontrolname,'password')]";

    //Methods
    public void goToXGBTemplatePage() {
        goToXGB("/");
        waitForElementDisplayedByXpathWithTimeout(SignIn,5);
    }
}


