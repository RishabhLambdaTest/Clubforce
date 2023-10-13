package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/DiscountCodes.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/DiscountCodes_failed.txt",
                "json:target/cucumber/DiscountCodes_unfiltered.txt"})

public class DiscountCodesRunner extends AbstractTestNGCucumberTests{
}