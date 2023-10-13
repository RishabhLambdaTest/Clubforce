package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/LottoRandomnessTest.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/LottoRandomnessTest_failed.txt",
                "json:target/cucumber/LottoRandomnessTest_unfiltered.txt"})
public class LottoRandomnessTestRunner extends AbstractTestNGCucumberTests {
}
