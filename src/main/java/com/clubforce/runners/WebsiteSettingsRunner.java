package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/WebsiteSettings.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/WebsiteSettings_failed.txt",
                "json:target/cucumber/WebsiteSettings_unfiltered.txt"})
public class WebsiteSettingsRunner extends AbstractTestNGCucumberTests {
}
