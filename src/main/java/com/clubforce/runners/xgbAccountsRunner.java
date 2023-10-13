package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/xgbAccounts.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/xgbAccounts_failed.txt",
                "json:target/cucumber/xgbAccounts_unfiltered.txt"})
public class xgbAccountsRunner extends AbstractTestNGCucumberTests {
}
