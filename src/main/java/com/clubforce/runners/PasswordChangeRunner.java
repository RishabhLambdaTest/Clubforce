package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/PasswordChange.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/PasswordChange_failed.txt",
                "json:target/cucumber/PasswordChange_unfiltered.txt"})
public class PasswordChangeRunner extends AbstractTestNGCucumberTests {
}
