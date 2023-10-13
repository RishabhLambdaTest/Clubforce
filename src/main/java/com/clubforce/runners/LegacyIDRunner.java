package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/LegacyID.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/LegacyID_failed.txt",
                "json:target/cucumber/LegacyID_unfiltered.txt"})
public class LegacyIDRunner extends AbstractTestNGCucumberTests {
}
