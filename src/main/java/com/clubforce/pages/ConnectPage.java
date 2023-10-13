package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectPage  extends SeleniumUtilities  {

    public WebDriverManager driverManager;

    public ConnectPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    public static String ConnectGroupHeading = "//h1[contains(.,'Groups')]";
    public static String NameColumnHeader = "(//strong[contains(.,'Name')])[1]";
    public static String MembersColumnHeader = "(//strong[contains(.,'Members')])[1]";
    public static String NoGroupsFound = "//h2[@data-test='app-articles-list__no-data'][contains(.,'No groups found')]";
    public static String GroupManagerColumnHeader = "(//strong[contains(.,'Group manager')])[1]";
    public static String ActionsColumnHeader = "(//strong[contains(.,'Actions')])[1]";
    public static String UpdateGroupManagerButton1 = "(//button[contains(.,'update group manager')])[1]";
    public static String UpdateGroupManagerHeading = "//h2[contains(.,'Update group manager')]";
    public static String UpdateButton = "//button[contains(.,'Update')]";
    public static String CancelButton = "//button[contains(.,'Cancel')]";

    public void verifyGroupPageElements() {
        findOnPage(ConnectGroupHeading);
        findOnPage(NameColumnHeader);
        findOnPage(MembersColumnHeader);
        findOnPage(GroupManagerColumnHeader );
        findOnPage(ActionsColumnHeader );
        logger.info("Connect page elements verified");
    }
}

