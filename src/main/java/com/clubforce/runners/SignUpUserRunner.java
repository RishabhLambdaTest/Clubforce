package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SignUpUser.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SignUpUser_failed.txt",
                "json:target/cucumber/SignUpUser_unfiltered.txt"})
public class SignUpUserRunner extends AbstractTestNGCucumberTests {
}
