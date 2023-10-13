package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NotFoundException;

public class MobileBrowserSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public MobileBrowserSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.mobileBrowserPage = driverManager.mobileBrowserPage;
        this.contactPage = driverManager.contactPage;
    }

    @Given("browser window is resized to {string} size")
    public void resizeBrowserWindow(String device) {
        switch (device) {
            case "portraitPhone":
                logger.info("Setting viewport to portraitPhone");
                Dimension portraitPhone = new Dimension(375, 667);
                driverManager.driver.manage().window().setSize(portraitPhone);
                break;
            case "portraitTablet":
                logger.info("Setting viewport to portraitTablet");
                Dimension portraitTablet = new Dimension(768, 1024);
                driverManager.driver.manage().window().setSize(portraitTablet);
                break;
            case "landscapePhone":
                logger.info("Setting viewport to portraitPhone");
                Dimension landscapePhone = new Dimension(667, 375);
                driverManager.driver.manage().window().setSize(landscapePhone);
                break;
            case "landscapeTablet":
                logger.info("Setting viewport to portraitTablet");
                Dimension landscapeTablet = new Dimension(1024, 768);
                driverManager.driver.manage().window().setSize(landscapeTablet);
                break;
            case "desktop":
                logger.info("Setting viewport to desktop");
                driverManager.driver.manage().window().maximize();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for resizeBrowserWindow!");
        }
        mobileBrowserPage.refreshPage();
    }

    @Then("{string} mobile web club page elements displays")
    public void mobileWebPageElementsValidation(String device) {
        mobileBrowserPage.waitOneSecond();
        switch (device) {
            case "portraitPhone":
            case "portraitTablet":
            case "landscapePhone":
                logger.info("Verifying page details");
                mobileBrowserPage.verifyMobileBrowserWebPageElements();
                mobileBrowserPage.verifyMobileHamburgerSideNavElements();
                logger.info("Verified page details");
                break;
            case "landscapeTablet":
                contactPage.verifyClubPageElements();
                break;
            default:
                throw new NotFoundException("For some reason there is no case for mobilePageElementsValidation!");
        }
    }
}
