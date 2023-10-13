package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/ConnectBackoffice.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/ConnectBackoffice_failed.txt",
                "json:target/cucumber/ConnectBackoffice_unfiltered.txt"})

public class ConnectBackofficeRunner extends AbstractTestNGCucumberTests {
}

