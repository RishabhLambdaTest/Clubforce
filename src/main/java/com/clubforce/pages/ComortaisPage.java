package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class ComortaisPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public ComortaisPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }
 }
