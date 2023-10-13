package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MembershipInstalments.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipInstalments_failed.txt",
                "json:target/cucumber/MembershipInstalments_unfiltered.txt"})
public class MembershipInstalmentsRunner extends AbstractTestNGCucumberTests {
}