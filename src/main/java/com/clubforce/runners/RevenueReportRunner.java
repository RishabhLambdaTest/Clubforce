package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(features = "src/main/resources/features/RevenueReport.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/RevenueReport_failed.txt",
                "json:target/cucumber/RevenueReport_unfiltered.txt"})
public class RevenueReportRunner extends AbstractTestNGCucumberTests {
}
