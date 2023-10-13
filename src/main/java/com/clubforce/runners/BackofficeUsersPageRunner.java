package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/BackofficeUsersPage.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/BackofficeUsersPage_failed.txt",
                "json:target/cucumber/BackofficeUsersPage_unfiltered.txt"})
public class BackofficeUsersPageRunner extends AbstractTestNGCucumberTests {
}
