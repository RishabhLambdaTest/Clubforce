package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ContactPage.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ContactPage_failed.txt",
                "json:target/cucumber/ContactPage_unfiltered.txt"})
public class ContactPageRunner extends AbstractTestNGCucumberTests {
}
