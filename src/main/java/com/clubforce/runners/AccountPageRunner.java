package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/AccountPage.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/AccountPage_failed.txt",
                "json:target/cucumber/AccountPage_unfiltered.txt"})
public class AccountPageRunner extends AbstractTestNGCucumberTests {
}
