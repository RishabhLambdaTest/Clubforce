package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/Comortais.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/Comortais_failed.txt",
                "json:target/cucumber/Comortais_unfiltered.txt"})
public class ComortaisRunner extends AbstractTestNGCucumberTests {
}
