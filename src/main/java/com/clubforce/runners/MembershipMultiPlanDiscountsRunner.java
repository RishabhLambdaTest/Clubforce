package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/MembershipMultiPlanDiscounts.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/MembershipMultiPlanDiscounts_failed.txt",
                "json:target/cucumber/MembershipMultiPlanDiscounts_unfiltered.txt"})
public class MembershipMultiPlanDiscountsRunner extends AbstractTestNGCucumberTests {
}
