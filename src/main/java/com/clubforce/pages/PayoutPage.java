package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class PayoutPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public PayoutPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final  String PayoutsPageEuroSymbol = "//span[@class='col-6 col-md'][contains(.,'€')]";
    public static final String PayoutsPagePoundSymbol = "//span[@class='col-6 col-md'][contains(.,'£')]";
    public static final String PayoutPagePaidStatusBadge = "//span[@class='badge bg-success text-capitalize'][contains(.,'paid')]";
    public static final String PayoutPageHeading = "//h1[contains(.,'Payout reports')]";
    public static final String PayoutPageAccountColumnHeader = "(//strong[contains(.,'Account')])[1]";
    public static final String PayoutPageAmountColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Amount')])[1]";
    public static final String PayoutPageDateColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Date')])[1]";
    public static final String PayoutPageStatusColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Status')])[1]";
    public static final String PayoutPageDetailsColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Details')])[1]";
    public static final String PayoutPageInformationText = "//p[contains(.,'Payouts include all available funds')]";
}
