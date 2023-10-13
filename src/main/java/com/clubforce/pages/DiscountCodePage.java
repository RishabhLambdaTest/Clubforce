package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class DiscountCodePage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public DiscountCodePage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static String DiscountCodesHeading = "//h1[contains(.,'Discount codes')]";
    public static String NewDiscountButton = "//button[contains(.,'new discount')]";
    public static String ActiveDiscountsTab = "(//span[contains(.,'Active discounts')])[2]";
    public static String InactiveDiscountsTab = "(//span[contains(.,'Inactive discounts')])[2]";
    public static String BackButton = "//button[contains(.,'arrow_back back')]";
    public static String CreateDiscountCodeHeading = "//h1[contains(.,'Create discount code')]";
    public static String DiscountDetailsSubHeading = "//h2[contains(.,'Discount details')]";
    public static String SingleUseCodeRadioButton = "(//span[contains(@class,'mat-radio-outer-circle')])[1]";
    public static String SingleUseCodeRadioButtonText = "//span[@class='mat-radio-label-content'][contains(.,'Single use code')]";
    public static String MultiUseCodeRadioButton = "(//span[contains(@class,'mat-radio-outer-circle')])[2]";
    public static String MultiUseCodeRadioButtonText = "//span[@class='mat-radio-label-content'][contains(.,'Multi use code')]";
    public static String SingleUseCodeExplanationText = "//p[contains(.,'One or more auto generated codes that may only be used once by a single club member.')]";
    public static String MultiUseCodeExplanationText = "//p[contains(.,'One code generated to be used multiple times by club members/customers.')]";
    public static String DiscountNameField = "//input[contains(@formcontrolname,'name')]";
    public static String TypeOfDiscountDropDownField = "(//div[contains(.,'Type of discount')])[11]";
    public static String AmountDiscountTypeDropDownOption = "//span[@class='mat-option-text'][contains(.,'Amount')]";
    public static String PercentageDiscountTypeDropDownOption = "//span[@class='mat-option-text'][contains(.,'Percentage')]";
    public static String LimitField = "//input[contains(@formcontrolname,'number_of_uses')]";
    public static String NumberOfCouponsField = "//input[contains(@formcontrolname,'number_of_coupons')]";
    public static String ValidFromDropDown = "(//mat-icon[@role='img'][contains(.,'keyboard_arrow_down')])[1]";
    public static String ValidToDropDown = "(//mat-icon[@role='img'][contains(.,'keyboard_arrow_down')])[2]";
    public static String DiscountCodeField = "//input[contains(@formcontrolname,'code')]";
    public static String DiscountCodeRefreshButton = "//mat-icon[@role='img'][contains(.,'cached')]";
    public static String CreateButton = "//button[contains(.,'Create')]";
    public static String CancelButton = "//button[contains(.,'Cancel')]";
    public static String DiscountAmountField = "//input[contains(@formcontrolname,'discount_amount')]";
    public static String InvalidValueErrorText = "//span[@data-test='input-error'][contains(.,'Invalid value')]";
    public static String PleaseEnterNameErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Name')]";
    public static String PleaseEnterTypeOfDiscountErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Type of discount')]";
    public static String PleaseEnterNumberOfCouponsErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Number of coupons')]";
    public static String PleaseEnterValidFromErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Valid from')]";
    public static String PleaseEnterValidToErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Valid to')]";
    public static String PleaseEnterLimitText = "//span[@data-test='input-error'][contains(.,'Please enter Limit')]";
    public static String PleaseEnterDiscountCodeErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Discount code')]";
    public static String PercentageSymbol = "//span[contains(.,'%')]";
    public static String CurrencySymbol = "//cf-currency-symbol[contains(.,'€')] | //cf-currency-symbol[contains(.,'£')]";
    public static String NextYearButton = "//button[@aria-label='Next year']";
    public static String DiscountNameColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Discount name')])[1]";
    public static String DiscountCodeColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Discount code')])[1]";
    public static String ValidFromColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Valid from')])[1]";
    public static String ValidToColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Valid to')])[1]";
    public static String AmountColumnHeader = "(//strong[@class='ng-star-inserted'][contains(.,'Amount')])[1]";
    public static String ActionsColumnHeader = "//strong[contains(.,'Actions')]";
    public static String NoDiscountsEmptyStateText = "//h2[contains(.,'No discounts')]";
    public static String DeactivateDiscountCodeHeading = "//h2[contains(.,'Deactivate discount?')]";
    public static String DeactivateButtonPopUp = "//span[@id='dialog--confirm']";
    public static String FirstCopyIcon = "(//mat-icon[@role='img'][contains(.,'copy')])[1]";
    public static String FirsDeactivatedDeactivateButton = "(//li[@class='dropdown-item border-bottom border-light cursor-pointer'][normalize-space()='Deactivate'])[1]";
    public static String UpdateButton = "//button[contains(.,'Update')]";
    public static String DiscountCodeRow1 = "(//button[@class='btn rounded-2 w-100 justify-content-center rounded-0 d-flex align-items-center text-uppercase btn-success ng-star-inserted'])[1]";
    public static String InactiveDiscountDropDownRow1 = "(//mat-icon[contains(.,'keyboard_arrow_down')])[1]";
    public static String FirstEditButton = "(//li[contains(.,'Edit')])[1]";
    public static String FirstActivateButton  = "(//li[contains(.,'Activate')])[1]";
    public static String SeeReportButton = "//button[contains(.,'see report')]";
    public static String DiscountCodeReportHeading = "//h1[contains(.,'Discount code report')]";
    public static String TotalDiscountsCodesUsedText = "//span[@class='cf-ui-content-title fw-bold'][contains(.,'Total discount codes used')]";
    public static String ExportCSVButton = "//button[contains(.,'download export csv')]";
}