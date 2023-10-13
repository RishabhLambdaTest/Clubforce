package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;

public class PaymentProviderPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public PaymentProviderPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String PaymentProviderPageText = "//*[contains(text(),'%s')]";
    public static final String PaymentProviderFeatureToggle = "//span[@class='mat-slide-toggle-content'][contains(.,'Payment Provider')]";
    public static final String PaymentProviderLeftNav = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Payment provider')]";
    public static final String PaymentProviderHeading = "//h1[contains(.,'Payments')]";
    public static final String StripeSetUpButton = "//button[contains(.,'open_in_new Set up Stripe')]";
    public static final String StripeTestModeText = "//*[contains(text(),'Test mode')]";
    public static final String TestModeText = "//*[contains(text(),'Test mode')]";
    public static final String StripeIsWorkingProperlyText = "//p[contains(.,'Stripe is connected and working properly')]";


    //Methods
    public void verifyPaymentProviderPageElements() {
        logger.info("verifyPaymentProviderBasicPageElements");
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "Create PaymentProvider")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "Basic Settings")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "PaymentProvider Settings")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "Short description")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "Full description")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "Image")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "One image only. Minimum size must be 1600 pixels x 900 pixels, this is to allow your users’ social sharing")));
        assertTrue(isElementPresent(String.format(PaymentProviderPageText, "Participating Club")));
    }
}


