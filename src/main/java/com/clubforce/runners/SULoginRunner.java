package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SU_Login.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SU_Login_failed.txt",
                "json:target/cucumber/SU_Login_unfiltered.txt"})
public class SULoginRunner extends AbstractTestNGCucumberTests {
}
