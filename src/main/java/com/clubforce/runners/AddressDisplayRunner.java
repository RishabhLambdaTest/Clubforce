package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/AddressDisplay.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/AddressDisplay_failed.txt",
                "json:target/cucumber/AddressDisplay_unfiltered.txt"})
public class AddressDisplayRunner extends AbstractTestNGCucumberTests {
}
