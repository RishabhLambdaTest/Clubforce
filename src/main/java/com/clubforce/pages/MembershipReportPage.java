package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class MembershipReportPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public MembershipReportPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String MembershipReportPageText = "//*[contains(text(),'%s')]";
    public static final String MembershipReportHeading = "//h1[contains(.,'Membership reports')]";
    public static final String MembershipReportsSubHeading = "//span[contains(.,'Overall membership reports')]";
    public static final String TotalRegisteredMembersTileHeading = "//h2[contains(.,'Total registered members')]";
    public static final String ArrearsTileHeading = "//h2[contains(.,'Arrears')]";
    public static final String PlayerNonPlayerTileHeading = "//h2[contains(.,'Player / non-player')]";
    public static final String TotalRegisteredMembersLastUpdatedText = "(//span[contains(.,'last updated:')])[1]";
    public static final String PlayerNonPlayerLastUpdatedText = "(//span[contains(.,'last updated:')])[2]";
    public static final String MembershipReportStatusAllFilter = "(//div[contains(.,'All')])[23]";
    public static final String MembershipReportStatusActiveFilter = "(//span[contains(.,'Active')])[2]";
    public static final String MembershipReportStatusExpiredFilter = "(//span[contains(.,'Expired')])[2]";
    public static final String MembershipReportStatusArrearsFilter = "(//span[contains(.,'Arrears')])[2]";
    public static final String MembershipStatusAllOption = "//span[@class='mat-option-text'][contains(.,'All')]";
    public static final String MembershipStatusActiveOption = "//span[@class='mat-option-text'][contains(.,'Active')]";
    public static final String MembershipStatusExpiredOption = "//span[@class='mat-option-text'][contains(.,'Expired')]";
    public static final String MembershipStatusArrearsOption = "//span[@class='mat-option-text'][contains(.,'Arrears')]";
    public static final String MemberNameColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Member name')]";
    public static final String GenderColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Gender')]";
    public static final String DOBColumnHeader = "//span[@data-test='table.header-item'][contains(.,'DOB')]";
    public static final String PlanNameColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Plan name')]";
    public static final String MembershipStatusColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Membership status')]";
    public static final String GuardianNameColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Guardian name')]";
    public static final String MembershipReportsPageDefaultEmptyStateIcon = "//mat-icon[@role='img'][contains(.,'article')]";
    public static final String MembershipReportDOBFilter = "//button[@aria-label='Open calendar']";
    public static final String DateButtonArrow = "//div[contains(@class,'mat-calendar-arrow')]";
    public static final String DatePreviousYearsArrowButton = "//button[@aria-label='Previous 24 years']";
    public static final String Year1993Button = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'1993')]";
    public static final String JanuaryMonthButton = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'JAN')]";
    public static final String MembershipReportDate1stOfMonth = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'1')]";
    public static final String MembershipReportDate2ndOfMonth = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'2')]";
    public static final String MembershipReportDOBClearCrossIcon = "//mat-icon[contains(.,'clear')]";
    public static final String MembershipGenderAllFilter = "(//div[contains(.,'All')])[13]";
    public static final String MembershipGenderPreferNotToSayFilter = "(//div[contains(.,'Prefer Not To SayGender')])[11]";
    public static final String MembershipReportFemaleFilter = "(//div[contains(.,'FemaleGender')])[11]";
    public static final String GenderMaleOption = "//span[@class='mat-option-text'][contains(.,'Male')]";
    public static final String GenderFemaleOption = "//span[@class='mat-option-text'][contains(.,'Female')]";
    public static final String GenderPreferNotToSayOption = "//span[@class='mat-option-text'][contains(.,'Prefer Not To Say')]";
    public static final String PlanNameAllFilter = "(//div[contains(.,'AllPlan name')])[11]";
    public static final String PricePlanForAutomationPlanNameOption = "//span[@class='mat-option-text'][contains(.,'Priced plan for automation')]";
    public static final String AdultPlayingPlanNameOption = "//span[@class='mat-option-text'][contains(.,'Adult Playing')]";
    public static final String PricedPlanPlanNameFilter = "(//span[contains(.,'Priced plan for automation')])[2]";
    public static final String MembershipQuickReportExportToCSVButton = "//button[contains(.,'download Quick Report')]";
    public static final String MembershipFullReportExportCSVButton = "//button[contains(.,'download Full Report')]";
    public static final String MembershipPopUpExportButton = "//button[contains(.,'Export')]";
    public static final String ExportPopUpHeading = "//h2[contains(.,'You are exporting personal data')]";
}

