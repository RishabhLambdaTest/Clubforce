package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Promotions.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Promotions_failed.txt",
                "json:target/cucumber/Promotions_unfiltered.txt"})
public class PromotionsRunner extends AbstractTestNGCucumberTests {
}
