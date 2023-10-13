package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubFeeManagement.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubFeeManagement_failed.txt",
                "json:target/cucumber/ClubFeeManagement_unfiltered.txt"})

public class ClubFeeManagementRunner extends AbstractTestNGCucumberTests {
}

