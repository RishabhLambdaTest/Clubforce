package com.clubforce.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Sorting.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Sorting_failed.txt",
                "json:target/cucumber/Sorting_unfiltered.txt"})

public class SortingRunner extends AbstractTestNGCucumberTests {
}
