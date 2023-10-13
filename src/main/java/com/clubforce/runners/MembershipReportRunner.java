package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(features = "src/main/resources/features/MembershipReport.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipReport_failed.txt",
                "json:target/cucumber/MembershipReport_unfiltered.txt"})
public class MembershipReportRunner extends AbstractTestNGCucumberTests {
}
