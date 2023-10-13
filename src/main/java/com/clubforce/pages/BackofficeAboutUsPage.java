package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class BackofficeAboutUsPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public  BackofficeAboutUsPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static String AboutUsHeadingWebsite = "//h4[contains(.,'About Us')]";
    public static final String AboutUsPageText = "//*[contains(text(),'%s')]";
    public static final String AboutUsDeleteImageButton = "//button[@type='button'][contains(.,'delete Remove Image')]";
    public static final String AboutUsRemoveImageButton = "//span[contains(.,'Remove image')]";
    public static final String AboutUsUploadImageButton = "//button[contains(.,'Upload image')]";
    public static final String AboutUsSavedSuccessNotification = "//span[contains(.,'About us saved.')]";
    public static final String AboutUsCreatedSuccessNotification =  "//span[contains(.,'About us created.')]";
    public static final String AboutUsReadMoreButton = "//button[contains(.,'Read More')]";
    public static final String AboutUsAddVideoButton = "//button[contains(@class,'ql-video')]";
    public static final String AboutUsEmbedVideo = "//input[@data-video='Embed URL']";
    public static final String AboutUsQLEditorFilled = "//div[contains(@class,'ql-editor')]";
    public static final String AboutUsQLEditorBlank = "//div[contains(@class,'ql-editor ql-blank')]";
    public static final String AboutUsPageFilePath = "(//input[@type='file'])[2]";

}
