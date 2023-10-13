package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.ClubPage;
import com.clubforce.pages.LoginPage;
import io.cucumber.java.en.Given;
import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageLoadSteps extends WebDriverManager {
    //logger
    private final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public PageLoadSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.contactPage = driverManager.contactPage;
    }

    @Given("TimeChecker goes to home page")
    public void checkPageLoadTimeForHomePage() {
        StopWatch pageLoad = new StopWatch();
        pageLoad.start();
        //Open your web page
        loginPage.goToClubURL("/");
        // Wait for the required any element
        loginPage.findOnPage(ClubPage.ContactText);
        pageLoad.stop();
        //Get the time
        long pageLoadTime_ms = pageLoad.getTime();
        long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
        logger.info("Total homepage load time: " + pageLoadTime_Seconds + " seconds");

        if (pageLoadTime_Seconds >= 3) {
            logger.error("ERROR: HOMEPAGE TOOK 3 SECONDS OR MORE TO LOAD");
        }
    }

    @Given("TimeChecker goes to BackOffice login page")
    public void checkPageLoadTimeForBackOfficePage() {
        loginPage.goToBackofficeURL("/");
        loginPage.waitOneSecond();
        loginPage.sendKeys(LoginPage.LoginEmailField, clubAdminUsername);
        loginPage.sendKeys(LoginPage.LoginPasswordField, clubAdminPassword);
        logger.info("ClubAdmin credentials entered");
        loginPage.click(LoginPage.LoginButton);
        logger.info("Button Clicked");

        StopWatch pageLoad = new StopWatch();
        pageLoad.start();
        // Wait for the required any element
        loginPage.findOnPage(BackofficePage.DashboardHeading);
        pageLoad.stop();
        //Get the time
        long pageLoadTime_ms = pageLoad.getTime();
        long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
        logger.info("Total Back Office Page Load Time: " + pageLoadTime_Seconds + " seconds");

        if (pageLoadTime_Seconds >= 3) {
            logger.info("ERROR : BACKOFFICE LOGIN PAGE TOOK 3 SECONDS OR MORE TO LOAD");
        }
    }
}