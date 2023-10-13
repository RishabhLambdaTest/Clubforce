package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/AboutUs.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/AboutUs_failed.txt",
                "json:target/cucumber/AboutUs_unfiltered.txt"})
public class AboutUsRunner extends AbstractTestNGCucumberTests {
}
