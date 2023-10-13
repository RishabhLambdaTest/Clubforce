package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Membership.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Membership_failed.txt",
                "json:target/cucumber/Membership_unfiltered.txt"})
public class MembershipRunner extends AbstractTestNGCucumberTests {
}
