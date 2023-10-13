package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(features = "src/main/resources/features/SectionsAndPages.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SectionsAndPages_failed.txt",
                "json:target/cucumber/SectionsAndPages_unfiltered.txt"})
public class SectionsAndPagesRunner extends AbstractTestNGCucumberTests {
}
