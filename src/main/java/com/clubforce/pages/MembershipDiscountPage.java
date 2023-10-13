package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class MembershipDiscountPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public MembershipDiscountPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String DiscountPageText = "//*[contains(text(),'%s')]";
    public static String DiscountsHeading = "//h1[contains(.,'Multi-plan discounts')]";
    public static String DiscountsInformationText = "//p[contains(.,'Automatically apply a discount to each customer’s shopping cart if they select a certain number of plans')]";
    public static String DiscountsNameColumnHeader = "(//strong[contains(.,'Name')])[1]";
    public static String DiscountsStatusColumnHeader = "(//strong[contains(.,'Status')])[1]";
    public static String DiscountsActiveFromColumnHeader = "(//strong[contains(.,'Active from')])[1]";
    public static String DiscountsActiveToColumnHeader = "(//strong[contains(.,'Active to')])[1]";
    public static String DiscountsActionsColumnHeader = "(//strong[contains(.,'Action')])[1]";
    public static String NoDiscountsText = "//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'No discounts')]";
    public static String NewDiscountButton = "//button[@routerlink='create'][contains(.,'New Discount')]";
    public static String NextStepButton = "//button[contains(.,'next step')]";
    public static String BackButton = "//mat-icon[contains(.,'arrow_back')]";
    public static String CreateNewDiscountHeadingStep1 = "//h1[contains(.,'Create new discount')]";
    public static String CreateNewDiscountHeadingStep2 = "//h1[@class='mb-0'][contains(.,'Create new discount')]";
    public static String Step1CreateDiscountInformationText = "//p[contains(.,'Step 1 of 2 - Select plans to be included in the discount')]";
    public static String PlanColumnHeaderStep1= "(//strong[contains(.,'Plan')])[1]";
    public static String StartDateColumnHeaderStep1= "(//strong[contains(.,'Start date')])[1]";
    public static String EndDateColumnHeaderStep1= "(//strong[contains(.,'End date')])[1]";
    public static String StatusColumnHeaderStep1= "(//strong[contains(.,'Status')])[1]";
    public static String PriceColumnHeaderStep1= "(//strong[contains(.,'Price')])[1]";
    public static String Step2CreateDiscountInformationText = "//p[contains(.,'Step 2 of 2 - Set up discount')]";
    public static String DiscountDetailsHeading = "//h3[contains(.,'Discount details')]";
    public static String DiscountValueHeading = "//h3[contains(.,'Discount value')]";
    public static String DiscountNameField = "//input[contains(@formcontrolname,'name')]";
    public static String TypeOfDiscountDropDownField = "(//div[contains(.,'Type of discount')])[13]";
    public static String TypeOfDiscountAmountOption = "//span[@class='mat-option-text'][contains(.,'Amount')]";
    public static String TypeOfDiscountPercentageOption = "//span[@class='mat-option-text'][contains(.,'Percentage')]";
    public static String PercentageSymbol = "//span[contains(.,'%')]";
    public static String NumberOfPlansFirstField = "(//input[contains(@formcontrolname,'number_of_products')])[1]";
    public static String NumberOfPlansSecondField = "(//input[contains(@formcontrolname,'number_of_products')])[2]";
    public static String AddDiscountButton = "//button[contains(.,'add Add Discount')]";
    public static String DiscountValueFirstField = "(//input[contains(@formcontrolname,'discount_amount')])[1]";
    public static String DiscountValueSecondField = "(//input[contains(@formcontrolname,'discount_amount')])[2]";
    public static String PleaseEnterDiscountErrorText = "//span[@data-test='input-error'][contains(.,'Please enter Discount value')]";
    public static String PleaseEnterDiscountNameText = "//span[@data-test='input-error'][contains(.,'Please enter Discount name')]";
    public static String PlansSelectedHeading = "//h3[contains(.,'Plans selected')]";
    public static String CreateButton = "//button[contains(.,'Create')]";
    public static String FirstEditButton = "(//button[contains(.,'edit Edit')])[1]";
    public static String UpdateButton = "//button[contains(.,'Update')]";
    public static String TypeOfDiscountAmount = "(//span[contains(.,'Amount')])[2]";
    public static String TypeOfDiscountPercentage = "(//span[contains(.,'Percentage')])[2]";
    public static String AmountDropDownOption = "//span[@class='mat-option-text'][contains(.,'Amount')]";
    public static String PercentageDropDownOption = "//span[@class='mat-option-text'][contains(.,'Percentage')]";
    public static String DeactivateButtonInPopUp = "//span[contains(.,'DEACTIVATE')]";
    public static String DeactivationSuccessNotification = "//span[contains(.,'Discount successfully deactivated.')]";

}
