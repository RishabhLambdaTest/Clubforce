package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/FeatureToggle.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/FeatureToggle_failed.txt",
                "json:target/cucumber/FeatureToggle_unfiltered.txt"})
public class FeatureToggleRunner extends AbstractTestNGCucumberTests {
}
