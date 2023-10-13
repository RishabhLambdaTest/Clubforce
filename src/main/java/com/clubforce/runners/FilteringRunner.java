package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Filtering.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Filtering_failed.txt",
                "json:target/cucumber/Filtering_unfiltered.txt"})
public class FilteringRunner extends AbstractTestNGCucumberTests {
}
