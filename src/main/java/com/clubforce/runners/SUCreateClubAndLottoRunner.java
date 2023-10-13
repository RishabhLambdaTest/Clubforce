package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/SU_CreateClubAndLotto.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/SU_CreateClubAndLotto_failed.txt",
                "json:target/cucumber/SU_CreateClubAndLotto_unfiltered.txt"})
public class SUCreateClubAndLottoRunner extends AbstractTestNGCucumberTests {
}
