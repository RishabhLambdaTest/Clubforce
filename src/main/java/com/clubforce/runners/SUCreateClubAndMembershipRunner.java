package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SU_CreateClubAndMembership.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SU_CreateClubAndMembership_failed.txt",
                "json:target/cucumber/SU_CreateClubAndMembership_unfiltered.txt"})
public class SUCreateClubAndMembershipRunner extends AbstractTestNGCucumberTests {
}
