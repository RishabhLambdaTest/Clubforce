package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/xgbTemplates.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/xgbTemplates_failed.txt",
                "json:target/cucumber/xgbTemplates_unfiltered.txt"})
public class xgbTemplatesRunner extends AbstractTestNGCucumberTests {
}
