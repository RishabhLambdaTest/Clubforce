package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/VisualTests.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/VisualTests_failed.txt",
                "json:target/cucumber/VisualTests_unfiltered.txt"})
public class VisualTestsRunner extends AbstractTestNGCucumberTests {
}
