package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubSearch.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubSearch_failed.txt",
                "json:target/cucumber/ClubSearch_unfiltered.txt"})
public class ClubSearchRunner extends AbstractTestNGCucumberTests {
}
