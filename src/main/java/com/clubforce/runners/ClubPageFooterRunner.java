package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubPageFooter.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubPageFooter_failed.txt",
                "json:target/cucumber/ClubPageFooter_unfiltered.txt"})
public class ClubPageFooterRunner extends AbstractTestNGCucumberTests {
}
