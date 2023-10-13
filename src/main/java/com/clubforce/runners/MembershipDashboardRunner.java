package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MembershipDashboard.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipDashboard_failed.txt",
                "json:target/cucumber/MembershipDashboard_unfiltered.txt"})
public class MembershipDashboardRunner extends AbstractTestNGCucumberTests {
}
