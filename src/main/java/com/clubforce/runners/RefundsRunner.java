package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(features = "src/main/resources/features/Refunds.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Refunds_failed.txt",
                "json:target/cucumber/Refunds_unfiltered.txt"})
public class RefundsRunner extends AbstractTestNGCucumberTests {
}

