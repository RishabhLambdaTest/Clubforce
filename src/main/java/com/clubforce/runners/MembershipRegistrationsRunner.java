package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MembershipRegistrations.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipRegistrations_failed.txt",
                "json:target/cucumber/MembershipRegistrations_unfiltered.txt"})
public class MembershipRegistrationsRunner extends AbstractTestNGCucumberTests {
}