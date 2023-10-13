package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/BackofficeLogin.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/BackofficeLogin_failed.txt",
                "json:target/cucumber/BackofficeLogin_unfiltered.txt"})
public class BackofficeLoginRunner extends AbstractTestNGCucumberTests {
}
