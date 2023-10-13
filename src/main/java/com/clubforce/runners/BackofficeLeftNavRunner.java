package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/BackofficeLeftNav.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/BackofficeLeftNav_failed.txt",
                "json:target/cucumber/BackofficeLeftNav_unfiltered.txt"})
public class BackofficeLeftNavRunner extends AbstractTestNGCucumberTests {
}
