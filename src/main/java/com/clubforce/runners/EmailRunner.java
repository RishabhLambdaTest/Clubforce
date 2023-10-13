package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Email.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Email_failed.txt",
                "json:target/cucumber/Email_unfiltered.txt"})
public class EmailRunner extends AbstractTestNGCucumberTests {
}