package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SocialMedia.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SocialMedia_failed.txt",
                "json:target/cucumber/SocialMedia_unfiltered.txt"})
public class SocialMediaRunner extends AbstractTestNGCucumberTests {
}
