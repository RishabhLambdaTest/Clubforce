package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubLogo.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubLogo_failed.txt",
                "json:target/cucumber/ClubLogo_unfiltered.txt"})
public class ClubLogoRunner extends AbstractTestNGCucumberTests {
}
