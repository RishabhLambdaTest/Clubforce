package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Dashboard.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Dashboard_failed.txt",
                "json:target/cucumber/Dashboard_unfiltered.txt"})

public class DashboardRunner extends AbstractTestNGCucumberTests{
}
