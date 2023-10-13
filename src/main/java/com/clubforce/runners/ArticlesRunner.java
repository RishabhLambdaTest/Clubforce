package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Articles.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Articles_failed.txt",
                "json:target/cucumber/Articles_unfiltered.txt"})
public class ArticlesRunner extends AbstractTestNGCucumberTests {
}
