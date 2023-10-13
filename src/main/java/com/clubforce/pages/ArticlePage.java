package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class ArticlePage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public ArticlePage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //Links
    public static final String ArticlePageText = "//*[contains(text(),'%s')]";
    public static final String ArticlePageLoadMoreButton = "//button[contains(.,'Load More')]";
    public static final String ArticlesTwitterIcon = "(//mat-icon[@role='img'][contains(.,'Twitter icon')])[2]";
    public static final String ArticlesFacebookIcon = "(//mat-icon[@role='img'][contains(.,'Facebook icon')])[2]";
    public static final String ArticlesPageSearchBar = "//input[contains(@filtername,'title')]";
    public static final String ArticlesAddVideoButton = "//button[contains(@class,'ql-video')]";
    public static final String ArticlesEmbedVideo = "//input[@data-video='Embed URL']";
    public static final String ArticlesQLEditorFilled = "//div[contains(@class,'ql-editor')]";
    public static final String ArticlePageHeading = "//h1[contains(.,'Articles')]";
    public static final String ViewDetailsFirstButton = "(//a[contains(.,'view details')])[1]";
    public static final String CreateArticleHeading = "//h1[contains(.,'Create article')]";
    public static final String UpdateArticleHeading = "//h1[contains(.,'Update article')]";
    public static final String ArticlesEmbedVideoSaveButton = "//a[contains(@class,'ql-action')]";
    public static final String ArticlesDeleteImageButton = "//button[@type='button'][contains(.,'delete Remove Image')]";
    public static final String ArticleRemoveImageButton = "//span[contains(.,'Remove image')]";
    public static final String ArticleUploadImageButton = "//button[contains(.,'Upload image')]";
    public static final String ArticleUploadImageFilePath = "(//input[@type='file'])[2]";
    public static final String ArticleAboutBikesCardOnHomepage = "//mat-card-title[contains(.,'This article is about bikes')]";
    public static final String AddArticleButtonOnArticlePage = "//a[contains(.,'add article add')]";
 }
