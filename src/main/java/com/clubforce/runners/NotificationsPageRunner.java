package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/NotificationsPage.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/NotificationsPage_failed.txt",
                "json:target/cucumber/NotificationsPage_unfiltered.txt"})
public class NotificationsPageRunner extends AbstractTestNGCucumberTests {
}
