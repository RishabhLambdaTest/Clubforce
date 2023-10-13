package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PromotionsPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public PromotionsPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String PromotionsLeftNav = "//span[@class='ms-2'][contains(.,'Promotions')]";
    public static final String PromotionsPageTitle = "//h1[contains(.,'Promotions')]";
    public static final String PromotionImage = "//img[contains(@src, '%s')]";
    public static final String CreatePromoButton = "//button[contains(.,'add Create')]";
    public static final String EditPromoButton = "//button[contains(.,'edit Edit')]";
    public static final String DeletePromoButton = "//button[contains(.,'delete Delete')]";

    public static final String PromoTitleField = "//input[contains(@formcontrolname,'title')]";
    public static final String PromoDescriptionField = "//input[contains(@formcontrolname,'description')]";
    public static final String PromoPublish = "//button[contains(.,'Publish')]";
    public static final String PromoSaveChanges = "//button[contains(.,'save changes')]";


    //Methods

    public void ssssssssssssssss() {
        logger.info("sssssssss");

    }

}


