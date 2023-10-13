package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MembershipBundles.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipBundles_failed.txt",
                "json:target/cucumber/MembershipBundles_unfiltered.txt"})
public class MembershipBundlesRunner extends AbstractTestNGCucumberTests {
}
