package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/LottoTicketPurchase.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/LottoTicketPurchase_failed.txt",
                "json:target/cucumber/LottoTicketPurchase_unfiltered.txt"})
public class LottoTicketPurchaseRunner extends AbstractTestNGCucumberTests {
}
