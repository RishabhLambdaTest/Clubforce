package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SU_CreateClubAndLottoAndMembership.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SU_CreateClubAndLottoAndMembership_failed.txt",
                "json:target/cucumber/SU_CreateClubAndLottoAndMembership_unfiltered.txt"})
public class SUCreateClubAndLottoAndMembershipRunner extends AbstractTestNGCucumberTests {
}
