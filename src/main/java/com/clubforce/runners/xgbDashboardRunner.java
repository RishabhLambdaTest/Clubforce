package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/xgbDashboard.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/xgbDashboard_failed.txt",
                "json:target/cucumber/xgbDashboard_unfiltered.txt"})
public class xgbDashboardRunner extends AbstractTestNGCucumberTests {
}
