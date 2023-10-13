package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubColours.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubColours_failed.txt",
                "json:target/cucumber/ClubColours_unfiltered.txt"})
public class ClubColoursRunner extends AbstractTestNGCucumberTests {
}
