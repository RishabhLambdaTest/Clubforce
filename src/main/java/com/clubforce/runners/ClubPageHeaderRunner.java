package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubPageHeader.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubPageHeader_failed.txt",
                "json:target/cucumber/ClubPageHeader_unfiltered.txt"})
public class ClubPageHeaderRunner extends AbstractTestNGCucumberTests {
}
