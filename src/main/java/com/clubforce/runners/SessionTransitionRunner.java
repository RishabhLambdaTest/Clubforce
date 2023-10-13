package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SessionTransition.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SessionTransition_failed.txt",
                "json:target/cucumber/SessionTransition_unfiltered.txt"})
public class SessionTransitionRunner extends AbstractTestNGCucumberTests {
}
