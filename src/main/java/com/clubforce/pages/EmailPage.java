package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class EmailPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public EmailPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static String EmailPageHeaderDate = "//strong[@class='ng-star-inserted'][contains(.,'Date')]";
    public static String EmailPageHeaderSender = "//strong[@class='ng-star-inserted'][contains(.,'Sender')]";
    public static String EmailPageHeaderSubject = "//strong[@class='ng-star-inserted'][contains(.,'Subject')]";
    public static String EmailPageHeaderRecipients = "//strong[@class='ng-star-inserted'][contains(.,'Recipients')]";

    public static String EmailComposeButton = "//*[contains(text(),'Compose')]";
    public static String EmailNewMessageHeading = "//*[contains(text(),'New message')]";

    public static String EmailErrorChooseARecipient = "//*[contains(text(),'Choose a Recipient')]";
    public static String EmailErrorEnterSubject = "//*[contains(text(),'Please enter Subject')]";
    public static String EmailErrorTypeYourMessage = "//*[contains(text(),'Type your message')]";

    public static String EmailDropdownChoiceMembers = "//span[@class='mat-option-text'][contains(.,'Members')]";
    public static String EmailDropdownChoiceLottoPlayers = "//span[@class='mat-option-text'][contains(.,'Lotto Players')]";


    public static String EmailSelectMailingListField = "//input[contains(@formcontrolname,'recipientInput')]";
    public static String EmailSubjectField = "//input[contains(@formcontrolname,'subject')]";
    public static String EmailContentField = "//div[contains(@class,'ql-editor ql-blank')]";

    public static String EmailChooseRecipientsHeading = "//*[contains(text(),'Choose recipients')]";
    public static String EmailWriteYourMessageHeading = "//*[contains(text(),'Write your message')]";
    public static String EmailPreviewAndSendButton = "//*[contains(text(),'preview and send')]";
    public static String EmailPreviewAndSendBackButton = "(//button[contains(.,'back')])[2]";
    public static String EmailPreviewAndSendSendButton = "(//button[contains(.,'send')])[2]";

}
