package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/AdministrativeTasks.feature",
        //strict = true,
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/AdministrativeTasks_failed.txt",
                "json:target/cucumber/AdministrativeTasks_unfiltered.txt"})
public class AdministrativeTasksRunner extends AbstractTestNGCucumberTests {
}
