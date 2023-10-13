package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/LottoCalculations.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/LottoCalculations_failed.txt",
                "json:target/cucumber/LottoCalculations_unfiltered.txt"})

public class LottoCalculationsRunner extends AbstractTestNGCucumberTests {
}
