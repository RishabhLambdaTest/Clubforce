package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MyAccountLogin.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MyAccountLogin_failed.txt",
                "json:target/cucumber/MyAccountLogin_unfiltered.txt"})
public class MyAccountLoginRunner extends AbstractTestNGCucumberTests {
}
