package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Pagination.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Pagination_failed.txt",
                "json:target/cucumber/Pagination_unfiltered.txt"})
public class PaginationRunner extends AbstractTestNGCucumberTests {
}
