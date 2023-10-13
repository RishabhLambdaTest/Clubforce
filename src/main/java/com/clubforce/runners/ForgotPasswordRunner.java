package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ForgotPassword.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ForgotPassword_failed.txt",
                "json:target/cucumber/ForgotPassword_unfiltered.txt"})
public class ForgotPasswordRunner extends AbstractTestNGCucumberTests {
}
