package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MembershipCancellation.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipCancellation_failed.txt",
                "json:target/cucumber/MembershipCancellation_unfiltered.txt"})
public class MembershipCancellationRunner extends AbstractTestNGCucumberTests {
}
