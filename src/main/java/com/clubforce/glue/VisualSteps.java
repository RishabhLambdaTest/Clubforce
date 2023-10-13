package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import io.cucumber.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class VisualSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public VisualSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.loginPage = driverManager.loginPage;
        this.mobileBrowserPage = driverManager.mobileBrowserPage;
    }

    @Then("{string} is visually as expected")
    public void visualCheck(String photoItem) throws Exception {
        mobileBrowserPage.waitTwoSeconds();
        if (!driverManager.agent.contains("chromeLatest.lambdatest")) {
            logger.error("Profile for ChromeLatest lambdatest not set - aborting test!");
        } else {
            logger.info("Case is " + photoItem);
            logger.info("Deleting old screenshot of 'Latest" + photoItem + "'");

            if (new File("src/main/resources/screenshots/visCompare/Latest" + photoItem + ".png").exists())
                FileUtils.forceDelete(new File("src/main/resources/screenshots/visCompare/Latest" + photoItem + ".png"));

            if (new File("src/main/resources/screenshots/visCompare/Latest" + photoItem + ".png").exists())
                throw new java.io.FileNotFoundException("Latest" + photoItem + ".png found after deletion attempt");

            logger.info("Taking fresh screenshot Latest" + photoItem);
            mobileBrowserPage.waitTwoSeconds();
            mobileBrowserPage.takePageScreenshot("Latest" + photoItem);

            logger.info("Comparing new and old screenshots of " + photoItem);
            // make sure files exist
            if (!new File("src/main/resources/screenshots/visCompare/Expected" + photoItem + ".png").exists())
                throw new java.io.FileNotFoundException("Expected" + photoItem + ".png not found, cannot compare");
            if (!new File("src/main/resources/screenshots/visCompare/Latest" + photoItem + ".png").exists())
                throw new java.io.FileNotFoundException("Latest" + photoItem + ".png not found, cannot compare");

            // compare the images
            mobileBrowserPage.compareImages("src/main/resources/screenshots/visCompare/Expected" + photoItem + ".png",
                    "src/main/resources/screenshots/visCompare/Latest" + photoItem + ".png");
        }
    }
}
