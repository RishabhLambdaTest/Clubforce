package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/PageLoadTimesChecks.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/PageLoadTimesChecks_failed.txt",
                "json:target/cucumber/PageLoadTimesChecks_unfiltered.txt"})

public class PageLoadTimesChecksRunner extends AbstractTestNGCucumberTests {
}
