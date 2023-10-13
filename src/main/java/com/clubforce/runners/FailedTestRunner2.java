package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "@target/cucumber/scenariosToRetry2.txt",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "json:target/cucumber/retries2.txt"})
public class FailedTestRunner2 extends AbstractTestNGCucumberTests {
}
