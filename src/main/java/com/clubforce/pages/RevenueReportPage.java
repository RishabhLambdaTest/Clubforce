package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class RevenueReportPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public RevenueReportPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String RevenueReportPageText = "//*[contains(text(),'%s')]";
    public static final String RevenueReportHeading = "//h1[contains(.,'Revenue report')]";
    public static final String RevenueReportInformationText = "//span[contains(.,'Overall report of all purchases')]";
    public static final String RevenueReportAllSourceFilter = "(//div[contains(.,'AllSource')])[11]";
    public static final String RevenueReportTerminalSourceFilter = "(//div[contains(.,'TerminalSource')])[11]";
    public static final String RevenueReportWebsiteSourceFilter = "(//div[contains(.,'WebsiteSource')])[11]";
    public static final String RevenueReportAllTypeFilter = "(//div[contains(.,'AllType')])[11]";
    public static final String RevenueReportAllStatusFilter = "(//div[contains(.,'AllStatus')])[11]";
    public static final String RevenueReportCancelledStatusFilter = "(//span[contains(.,'Cancelled')])[2]";
    public static final String RevenueReportDueDateFilter = "(//button[contains(@type,'button')])[1]";
    public static final String RevenueReportTakenDateFilter = "(//button[contains(@type,'button')])[2]";
    public static final String RevenueReportOrderIDColumnHeader = "//span[contains(.,'Order ID')]";
    public static final String RevenueReportCustomerColumnHeader = "//span[contains(.,'Customer')]";
    public static final String RevenueReportAmountColumnHeader = "//span[contains(.,'Amount')]";
    public static final String RevenueReportDueDateColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Due date')]";
    public static final String RevenueReportTakenDateColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Taken date')]";
    public static final String RevenueReportTypeColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Type')]";
    public static final String RevenueReportStatusColumnHeader = "//span[@data-test='table.header-item'][contains(.,'Status')]";
    public static final String RevenueReportSearchBar = "//input[contains(@filtername,'customer')]";
    public static final String TotalRevenueTile = "//h2[contains(.,'Total Revenue')]";
    public static final String TotalRevenueTooltip = "//mat-icon[@mattooltip='The total net value of all club revenue received']";
    public static final String PendingRevenueTile = "//h2[contains(.,'Pending')]";
    public static final String PendingRevenueTooltip = "//mat-icon[@mattooltip='The total net value of all pending transactions']";
    public static final String RevenueReportSourceAllOption = "//span[@class='mat-option-text'][contains(.,'All')]";
    public static final String RevenueReportSourceTerminalOption = "//span[@class='mat-option-text'][contains(.,'Terminal')]";
    public static final String RevenueReportSourceWebsiteOption = "//span[@class='mat-option-text'][contains(.,'Website')]";
    public static final String RevenueReportEmptyStateIcon = "//mat-icon[@role='img'][contains(.,'article')]";
    public static final String RevenueReportPaymentTypeFilter = "(//div[contains(.,'PaymentType')])[11]";
    public static final String RevenueReportRefundTypeFilter = "(//div[contains(.,'RefundType')])[11]";
    public static final String RevenueReportTypeAllOption = "//span[@class='mat-option-text'][contains(.,'All')]";
    public static final String RevenueReportPaymentTypeOption = "//span[@class='mat-option-text'][contains(.,'Payment')]";
    public static final String RevenueReportRefundTypeOption = "//span[@class='mat-option-text'][contains(.,'Refund')]";
    public static final String RevenueReportCancelledStatusOption = "//span[@class='mat-option-text'][contains(.,'Cancelled')]";
    public static final String RevenueReportCompleteStatusOption = "//span[@class='mat-option-text'][contains(.,'Complete')]";
    public static final String CompleteBadge= "(//p[contains(.,'Complete')])[1]";
    public static final String CancelledBadge= "(//p[contains(.,'Cancelled')])[1]";
    public static final String DueDateFromNextMonthArrow = "//button[@aria-label='Next month']";
    public static final String PreviousMonthArrow = "//button[@aria-label='Previous month']";
    public static final String NextMonthArrow = "//button[@aria-label='Next month']";
    public static final String Date1stOfMonth = "(//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'1')])[1]";
    public static final String Date1stOfNextMonth = "(//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'1')])[1]";
    public static final String DateFilterClearButton = "//mat-icon[@role='img'][contains(.,'clear')]";
    public static final String ProductAllFilter = "(//div[contains(.,'AllProduct')])[11]";
    public static final String ProductLottoFilter = "(//span[contains(.,'Lotto')])[5]";
    public static final String ProductAllOption = "//span[@class='mat-option-text'][contains(.,'All')]";
    public static final String ProductMembershipFilter = "(//span[contains(.,'Membership')])[2]";
    public static final String ProductMembershipOption = "//span[@class='mat-option-text'][contains(.,'Membership')]";
    public static final String ProductLottoOption = "//span[@class='mat-option-text'][contains(.,'Lotto')]";
    public static final String CalenderYearButton = "//div[contains(@class,'mat-calendar-arrow')]";
    public static final String CalenderYear2023 = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today'][contains(.,'2023')]";
    public static final String CalenderMonthApril = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'APR')]";
    public static final String Calender18thMonth = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'18')]";
    public static final String Calender19thMonth = "//div[@class='mat-calendar-body-cell-content mat-focus-indicator'][contains(.,'19')]";
    public static final String OrderItemAndOrderAmount = "//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'€45.00 (€390.65)')]";
    public static final String TypeColumnPaymentText = "//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Payment')]";
    public static final String OrderIDRow1 = "(//p[contains(@class,'text-primary cursor-pointer ng-star-inserted')])[1]";
    public static final String OrderDetailsHeadingBackoffice = "//h1[contains(.,'Order details')]";
    public static final String OrderDetailsPageBackButton = "//button[contains(.,'arrow_back back')]";
}
