package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Lotto.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Lotto_failed.txt",
                "json:target/cucumber/Lotto_unfiltered.txt"})
public class LottoRunner extends AbstractTestNGCucumberTests {
}
