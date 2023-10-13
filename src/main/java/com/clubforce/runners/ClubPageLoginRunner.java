package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ClubPageLogin.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ClubPageLogin_failed.txt",
                "json:target/cucumber/ClubPageLogin_unfiltered.txt"})
public class ClubPageLoginRunner extends AbstractTestNGCucumberTests {
}
