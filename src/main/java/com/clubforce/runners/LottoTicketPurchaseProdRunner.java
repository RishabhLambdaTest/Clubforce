package com.clubforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features/LottoTicketPurchaseProd.feature",
        glue = "com/clubforce/glue",
        plugin = {"pretty",
                "rerun:target/cucumber/failed/LottoTicketPurchaseProd_failed.txt",
                "json:target/cucumber/LottoTicketPurchaseProd_unfiltered.txt"})
public class LottoTicketPurchaseProdRunner extends AbstractTestNGCucumberTests {
}
