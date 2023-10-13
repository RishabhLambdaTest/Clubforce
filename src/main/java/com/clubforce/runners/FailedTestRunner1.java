package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "@target/cucumber/scenariosToRetry1.txt",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "json:target/cucumber/retries1.txt"})
public class FailedTestRunner1 extends AbstractTestNGCucumberTests {
}
