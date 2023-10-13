package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SU_Reports.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SU_Reports_failed.txt",
                "json:target/cucumber/SU_Reports_unfiltered.txt"})
public class SUReportsRunner extends AbstractTestNGCucumberTests {
}
