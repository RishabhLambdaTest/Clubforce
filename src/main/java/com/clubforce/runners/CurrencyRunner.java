package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Currency.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Currency_failed.txt",
                "json:target/cucumber/Currency_unfiltered.txt"})
public class CurrencyRunner extends AbstractTestNGCucumberTests {
}