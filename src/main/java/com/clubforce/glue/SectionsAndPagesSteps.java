package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SectionsAndPagesSteps extends WebDriverManager {

    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public SectionsAndPagesSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.backofficePage = driverManager.backofficePage;
        this.sectionsAndPagesPage = driverManager.sectionsAndPagesPage;
    }

    private String uniqueSectionName;
    private String newsTitle;
    private String newsTitle2;
    private String newsTitle3;
    private String editedSectionNameTitle;

    @Then("The default empty state is displayed for sections")
    public void checkEmptyStateIsDisplayedForSections(){
        verifyPageElementsOnSectionsAndPagesPage();
        logger.info("Checking empty state is displayed for sections area");
        sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "No sections found"));
        sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "Host multiple pages within a section"));
    }

    @Then("The default empty state is displayed for pages")
    public void checkEmptyStateIsDisplayedForPages(){
        verifyPageElementsOnSectionsAndPagesPage();
        logger.info("Checking empty state is displayed for pages area");
        //Check create section button is disabled if club has no pages created
        assertThat("Create section button is NOT disabled!", sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.CreateSectionButton, "disabled"), containsString("true"));
    }

    @Then("Club admin creates a section")
    public void createASection(){
        verifyPageElementsOnSectionsAndPagesPage();
        logger.info("Check to see if create section button is disabled");
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(SectionsAndPagesPage.CreateSectionButton)).isEnabled();
        logger.info("Is create section button disabled :- " + isButtonEnabled);

        if(!isButtonEnabled) {
            logger.info("Create section button is disabled. Creating a new page to enable create section button");
            createNewPage();
        }
        logger.info("Create section button is enabled. Creating new section");
        sectionsAndPagesPage.waitFifteenSeconds();
        createNewSection();
        verifySectionsColumnHeaders();
        verifyPagesColumnHeaders();
    }

    @Then("Club admin adds a page to a section")
    public void addPagesToSection(){
        verifyPageElementsOnSectionsAndPagesPage();
        createNewPage();

        logger.info("If a section already exists then we will try add page to the first section");
        if(!sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.FirstAddPagesButton)){
            logger.info("A section does NOT exists. Creating a new section");
            createNewSection();
        }else{
            logger.info("A section does exists. Getting the name of the first section");
            String sectionName = sectionsAndPagesPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
            uniqueSectionName = sectionName.replaceAll("expand_more", "");
            logger.info("First section name " + uniqueSectionName);
        }

        verifySectionsColumnHeaders();
        verifyPagesColumnHeaders();
        logger.info("Add page to first section in sections table");
        sectionsAndPagesPage.click(SectionsAndPagesPage.FirstAddPagesButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.AddPagesInformationText);
        sectionsAndPagesPage.click("//span[@class='mat-checkbox-label'][contains(.,'"+newsTitle+"')]");
        sectionsAndPagesPage.click(SectionsAndPagesPage.AddButton);
        sectionsAndPagesPage.waitTwoSeconds();
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PageAddedSuccessfullyNotification));
        assertFalse(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesAddedFailureNotification));
        sectionsAndPagesPage.click("//p[contains(.,'"+uniqueSectionName+"')]");
        sectionsAndPagesPage.findOnPage("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+ newsTitle+"')]");
        logger.info("Page has been successfully added to the section");
    }

    @Then("Club admin removes pages from a section")
    public void removePagesFromASection(){
        verifyPageElementsOnSectionsAndPagesPage();

        logger.info("Add page to the first section");
        addPagesToSection();

        logger.info("Add a second page to the first section");
        addPagesToSection();

        logger.info("Add a third page to the first section");
        addPagesToSection();

        logger.info("Open the first section drop down");
        sectionsAndPagesPage.click(SectionsAndPagesPage.FirstSectionDropdownArrow);

        logger.info("Get the names of the pages under the section");
        String pageTitleForRow1 = sectionsAndPagesPage.getElementAttribute("(//div[contains(@class,'col-lg-3 mx-auto py-3 ps-lg-4 text-break')])[1]","textContent");
        logger.info("First page title under the section dropdown : " + pageTitleForRow1);
        String pageTitleForRow2 = sectionsAndPagesPage.getElementAttribute("(//div[contains(@class,'col-lg-3 mx-auto py-3 ps-lg-4 text-break')])[2]","textContent");
        logger.info("Second page title under the section dropdown : " + pageTitleForRow2);
        String pageTitleForRow3 = sectionsAndPagesPage.getElementAttribute("(//div[contains(@class,'col-lg-3 mx-auto py-3 ps-lg-4 text-break')])[3]","textContent");
        logger.info("Third page title under the section dropdown : " + pageTitleForRow3);

        logger.info("Make sure each page has a remove page button");
        for(int i = 1; i <=3; i++){
            assertTrue(sectionsAndPagesPage.isElementDisplayed("(//button[contains(.,'remove page')])["+i+"]"));
        }

        logger.info("Remove 1 of the pages that were added - 2nd remove page button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.RemovePageSecondButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PleaseConfirmHeadingText);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.RemovePagePopUpInformationText);
        sectionsAndPagesPage.click(SectionsAndPagesPage.RemoveButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PageSuccessfullyRemovedNotification);
        sectionsAndPagesPage.waitThreeSeconds();
        assertFalse(sectionsAndPagesPage.isElementDisplayed("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+pageTitleForRow2+"')]"));
    }

    @Then("Club admin deletes a section")
    public void deleteSection(){
        verifyPageElementsOnSectionsAndPagesPage();
        logger.info("Check to see if create section button is disabled");
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(SectionsAndPagesPage.CreateSectionButton)).isEnabled();
        logger.info("Is create section button disabled :- " + isButtonEnabled);

        if(!isButtonEnabled) {
            logger.info("Create section button is disabled. Creating a new page to enable create section button");
            createNewPage();
        }
        logger.info("Create section button is enabled. Creating new section");
        sectionsAndPagesPage.waitThreeSeconds();
        createNewSection();
        verifySectionsColumnHeaders();
        verifyPagesColumnHeaders();

        logger.info("A section does exists. Getting the name of the first section");
        String sectionName = sectionsAndPagesPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
        logger.info("First section name : " + sectionName);
        sectionName = sectionName.replaceAll("expand_more", "");
        logger.info("First section name : " + sectionName);
        uniqueSectionName = sectionName;
        logger.info("Unique section name : " + uniqueSectionName);
        assertTrue(uniqueSectionName.equalsIgnoreCase(sectionName));

        logger.info("Attempting to delete the first section");
        sectionsAndPagesPage.click(SectionsAndPagesPage.FirstDeleteBinIcon);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PleaseConfirmHeadingText);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.DeletePopUpInformationText);

        logger.info("Clicking cancel button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);

        logger.info("Attempting to delete the first section again");
        sectionsAndPagesPage.click(SectionsAndPagesPage.FirstDeleteBinIcon);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PleaseConfirmHeadingText);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.DeletePopUpInformationText);
        sectionsAndPagesPage.click(SectionsAndPagesPage.DeletePopUpDeleteButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.DeleteSectionSuccessNotification);
        sectionsAndPagesPage.waitThreeSeconds();
        assertFalse(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+uniqueSectionName+"')]"));
    }

    @Then("Club admin edits a section")
    public void editSection(){
        verifyPageElementsOnSectionsAndPagesPage();
        logger.info("Check to see if create section button is disabled");
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(SectionsAndPagesPage.CreateSectionButton)).isEnabled();
        logger.info("Is create section button disabled :- " + isButtonEnabled);

        if(!isButtonEnabled) {
            logger.info("Create section button is disabled. Creating a new page to enable create section button");
            createNewPage();
        }
        logger.info("Create section button is enabled. Creating new section");
        createNewSection();
        verifySectionsColumnHeaders();
        verifyPagesColumnHeaders();

        logger.info("A section does exists. Getting the name of the first section");
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+uniqueSectionName+"')]"));
        String sectionName = sectionsAndPagesPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[1]", "textContent");
        logger.info("First section name : " + sectionName);
        sectionName = sectionName.replaceAll("expand_more", "");
        logger.info("First section name : " + sectionName);
        uniqueSectionName = sectionName;
        logger.info("Unique section name : " + uniqueSectionName);
        assertTrue(uniqueSectionName.equalsIgnoreCase(sectionName));

        logger.info("Attempting to edit the first section title");
        sectionsAndPagesPage.click(SectionsAndPagesPage.FirstEditIconButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.EditSectionTitleHeading);
        assertThat("Update button is NOT disabled!", sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.PopUpUpdateButton, "disabled"), containsString("true"));

        sectionsAndPagesPage.click(SectionsAndPagesPage.EditSectionTitleHeading);
        sectionsAndPagesPage.waitOneSecond();
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionTitleMustBeUniqueErrorText));

        logger.info("Clear section name field");
        sectionsAndPagesPage.clearInputFieldUsingBackSpaceKey(SectionsAndPagesPage.TitleField);

        logger.info("Enter in 17 characters");
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.TitleField,"11111111111111111");
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.MaximumSixteenCharactersErrorText));
        assertThat("Update button is NOT set to Inactive!!", sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.PopUpUpdateButton, "disabled"), containsString("true"));

        logger.info("Clear section name field");
        sectionsAndPagesPage.clearInputFieldUsingBackSpaceKey(SectionsAndPagesPage.TitleField);
        editedSectionNameTitle = "Edited name " + RandomStringUtils.randomNumeric(4);

        logger.info("Enter in new edited section name : " + editedSectionNameTitle);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.TitleField, editedSectionNameTitle);
        sectionsAndPagesPage.click(SectionsAndPagesPage.EditSectionTitleHeading);
        sectionsAndPagesPage.waitOneSecond();
        assertFalse(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionTitleMustBeUniqueErrorText));
        sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpUpdateButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.SectionUpdatedSuccessNotification);
        sectionsAndPagesPage.waitTwoSeconds();
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+editedSectionNameTitle+"')]"));
        assertFalse(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+uniqueSectionName+"')]"));
    }

    @Then("Club admin add a section to website using website navigation")
    public void addSectionToWebsiteUsingWebsiteNavigationPopUp(){
        logger.info("Clear all website navigation items");
        sectionsAndPagesPage.waitTwoSeconds();

        while (sectionsAndPagesPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsItemCheckbox)) {
            logger.info("Menu item found, removing");
            sectionsAndPagesPage.click(WebsiteSettingsPage.WebsiteSettingsItemCheckbox);
            sectionsAndPagesPage.click(WebsiteSettingsPage.WebsiteSettingsItemRemoveButton);
            logger.info("Removed existing menu item");
            sectionsAndPagesPage.waitHalfSecond();
        }

        logger.info("Click add navigation item button");
        sectionsAndPagesPage.click(WebsiteSettingsPage.AddNavigationItemButton);
        sectionsAndPagesPage.waitHalfSecond();

        logger.info("Select section from menu item content type dropdown");
        sectionsAndPagesPage.click(WebsiteSettingsPage.WebsiteSettingsMenuItemSelector);
        sectionsAndPagesPage.waitHalfSecond();

        logger.info("Select the section from content section link dropdown");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionWebsiteNavigationOption);

        logger.info("Make sure that no menu title field is displayed");
        assertFalse(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.TitleField));

        logger.info("Open the section link dropdown");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionLinkDropDownWebsiteNavigation);

        logger.info("Choose the section name '" +uniqueSectionName+"'");
        sectionsAndPagesPage.click("//span[contains(.,'"+uniqueSectionName+"')]");
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreateButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.SectionMenuItemSuccessfullyCreatedNotification);
        sectionsAndPagesPage.findOnPage("//div[@class='mat-line'][contains(.,'"+uniqueSectionName+"')]");
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle+"')]"));
    }

    @Then("the section and its pages are {string} on the website")
    public void checkSectionIsDisplayedOnWebsite(String displayedOrNotDisplayed){
        logger.info("Going to website again");
        sectionsAndPagesPage.click(BackofficePage.DashboardTitleLeftNav);
        sectionsAndPagesPage.click(BackofficeDashboardPage.ViewWebsiteDashboardTileButton);
        sectionsAndPagesPage.waitTwoSeconds();
        sectionsAndPagesPage.switchToBrowserTab(1);

        switch (displayedOrNotDisplayed){
            case "displayed":
                logger.info("Verify that the section and its pages are displayed");
                sectionsAndPagesPage.findOnPage("//a[@data-bs-toggle='dropdown'][contains(.,'"+uniqueSectionName+"')]");
                sectionsAndPagesPage.click(SectionsAndPagesPage.MenuItemDropDownOnWebsite);
                sectionsAndPagesPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'"+newsTitle+"')]");
                driverManager.driver.close();
                break;
            case "not displayed":
                logger.info("Verify that the section and its pages are NOT displayed");
                assertFalse(sectionsAndPagesPage.isElementDisplayed("//a[@data-bs-toggle='dropdown'][contains(.,'"+uniqueSectionName+"')]"));
                driverManager.driver.close();
                break;
            default:
        }
    }

    @Then("the section and its pages are not displayed on the website navigation page")
    public void checkSectionTitleIsNotOnWebsiteNavigationPage(){
        logger.info("Check to see if section title " +uniqueSectionName+ " is displayed on website navigation page");
        sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "Add external links to your menu, e.g. your existing lotto page. Drag to change order."));
        assertFalse(sectionsAndPagesPage.isElementDisplayed("//div[@class='mat-line'][contains(.,'"+uniqueSectionName+"')]"));
        assertFalse(sectionsAndPagesPage.isElementDisplayed("(//div[@class='mat-line'][contains(.,'Subpages "+newsTitle+"')])[1]"));
        logger.info("Section title " +uniqueSectionName+ " is NOT displayed on website navigation page anymore");
    }

    @Then("Club admin goes back to backoffice")
    public void goFromWebsiteTabToBackOfficeTab(){
        sectionsAndPagesPage.switchToBrowserTab(0);
        sectionsAndPagesPage.findOnPage(BackofficePage.DashboardHeading);
        assertTrue(driverManager.driver.getCurrentUrl().contains("https://backoffice.test.clubforce.io"));
    }

    @Then("Club admin {string} the section that is displayed on the website")
    public void updatesASectionThatIsAlreadyOnAWebsite(String action){
        int i = 1;
        sectionsAndPagesPage.click("(//mat-icon[@role='img'][contains(.,'edit')])["+i+"]");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.EditSectionTitleHeading);
        String sectionTitleName = sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.TitleField, "value");
        logger.info("Edit section title field contains : " + sectionTitleName);
        logger.info("Section name we looking for is : " + uniqueSectionName);
        while(!sectionTitleName.equalsIgnoreCase(uniqueSectionName)){
            logger.info("Clicking the cancel button in the edit section pop up");
            sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);
            i++;
            logger.info("Clicking edit icon " + i);
            sectionsAndPagesPage.click("(//mat-icon[@role='img'][contains(.,'edit')])["+i+"]");
            sectionTitleName = sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.TitleField, "value");
            logger.info("Edit section title field contains : " + sectionTitleName);
        }
        switch (action){
            case "edits":
                editedSectionNameTitle = "Edited " + RandomStringUtils.randomNumeric(8);
                sectionsAndPagesPage.clearInputFieldUsingBackSpaceKey(SectionsAndPagesPage.TitleField);
                sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.TitleField,editedSectionNameTitle);
                sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpUpdateButton);
                sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.SectionUpdatedSuccessNotification);
                sectionsAndPagesPage.waitTwoSeconds();
                assertTrue(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+editedSectionNameTitle+"')]"));
                assertFalse(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+uniqueSectionName+"')]"));
                break;
            case "removes":
                logger.info("Close edit section title pop up");
                sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);
                logger.info("Need to click the " +i+ "delete bin icon in order to delete the correct section");
                sectionsAndPagesPage.click("(//mat-icon[contains(.,'delete_outline')])["+i+"]");
                sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PleaseConfirmHeadingText);
                sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.DeletePopUpInformationText);
                sectionsAndPagesPage.click(SectionsAndPagesPage.DeletePopUpDeleteButton);
                sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.DeleteSectionSuccessNotification);
                sectionsAndPagesPage.waitThreeSeconds();
                assertFalse(sectionsAndPagesPage.isElementDisplayed("//p[contains(.,'"+uniqueSectionName+"')]"));
                break;
            default:
        }
    }

    @Then("the sections {string} should be updated on {string}")
    public void checkSectionHasBeenUpdatedOnNavigationAndWebsite(String items, String area){
        switch (items){
            case "menu item title":
                if(area.equalsIgnoreCase("website navigation page")){
                    logger.info("Going to website navigation page again");
                    if (!sectionsAndPagesPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsMenuSection)) {
                        sectionsAndPagesPage.click(BackofficePage.BackofficeLeftNavWebsite);
                    }
                    sectionsAndPagesPage.click(WebsiteSettingsPage.WebsiteSettingsMenuSection);
                    sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "Add external links to your menu, e.g. your existing lotto page. Drag to change order."));

                    logger.info("Checking edited section title on website navigation page");
                    sectionsAndPagesPage.findOnPage("//div[@class='mat-line'][contains(.,'"+editedSectionNameTitle+"')]");
                    logger.info(editedSectionNameTitle + " is displayed on website navigation page");
                    assertFalse(sectionsAndPagesPage.isElementDisplayed("//div[@class='mat-line'][contains(.,'"+uniqueSectionName+"')]"));
                    logger.info(uniqueSectionName + " is NOT displayed on website navigation page");
                }

                if(area.equalsIgnoreCase("website")){
                    logger.info("Going to website again");
                    sectionsAndPagesPage.click(BackofficePage.BO_MenuDots);
                    sectionsAndPagesPage.click(LoginPage.GoToMyClubOption);
                    sectionsAndPagesPage.waitTwoSeconds();
                    sectionsAndPagesPage.switchToBrowserTab(1);
                    sectionsAndPagesPage.waitForElementDisplayedByXpathWithTimeout(ContactPage.ClubPageContactLink,10);

                    logger.info("Checking edited section title on website");
                    sectionsAndPagesPage.findOnPage("//span[@class='mat-button-wrapper'][contains(.,'"+editedSectionNameTitle+"')]");
                    logger.info(editedSectionNameTitle + " is displayed on website");
                    assertFalse(sectionsAndPagesPage.isElementDisplayed("span[@class='mat-button-wrapper'][contains(.,'"+uniqueSectionName+"')]"));
                    logger.info(uniqueSectionName + " is NOT displayed on website");
                }
                break;
            case "add page to list dropdown":
                if(area.equalsIgnoreCase("website navigation page")){
                    logger.info("Going to website navigation page again");
                    if (!sectionsAndPagesPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsMenuSection)) {
                        sectionsAndPagesPage.click(BackofficePage.BackofficeLeftNavWebsite);
                    }
                    sectionsAndPagesPage.click(WebsiteSettingsPage.WebsiteSettingsMenuSection);
                    sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "Add external links to your menu, e.g. your existing lotto page. Drag to change order."));

                    logger.info("Checking section title on website navigation page");
                    sectionsAndPagesPage.findOnPage("//div[@class='mat-line'][contains(.,'"+uniqueSectionName+"')]");
                    logger.info(uniqueSectionName + " is displayed on website navigation page");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle+"')]"));
                    logger.info(newsTitle + " sub page is displayed on website navigation page");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle2+"')]"));
                    logger.info(newsTitle2 + " sub page is displayed on website navigation page");
                }

                if(area.equalsIgnoreCase("website")){
                    logger.info("Going to website again");
                    sectionsAndPagesPage.click(BackofficePage.BO_MenuDots);
                    sectionsAndPagesPage.click(LoginPage.GoToMyClubOption);
                    sectionsAndPagesPage.waitTwoSeconds();
                    sectionsAndPagesPage.switchToBrowserTab(1);
                    sectionsAndPagesPage.findOnPage(ContactPage.ClubPageContactLink);

                    logger.info("Checking correct pages are displayed under section on website");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//span[@class='mat-button-wrapper'][contains(.,'"+uniqueSectionName+"')]"));
                    logger.info(uniqueSectionName + " is displayed on website");

                    logger.info("Click on the section title menu item and check if its pages are displayed in the dropdown");
                    sectionsAndPagesPage.click("//span[@class='mat-button-wrapper'][contains(.,'"+uniqueSectionName+"')]");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@data-bs-toggle='collapse'][contains(.,'"+newsTitle+"')]"));
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@data-bs-toggle='collapse'][contains(.,'"+newsTitle2+"')]"));
                    logger.info("Correct pages are displayed in dropdown for the correct section menu item");
                }
                break;
            case "remove page from list dropdown":
                if(area.equalsIgnoreCase("website navigation page")){
                    logger.info("Going to website navigation page again");
                    if (!sectionsAndPagesPage.isElementDisplayed(WebsiteSettingsPage.WebsiteSettingsMenuSection)) {
                        sectionsAndPagesPage.click(BackofficePage.BackofficeLeftNavWebsite);
                    }
                    sectionsAndPagesPage.click(WebsiteSettingsPage.WebsiteSettingsMenuSection);
                    sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "Add external links to your menu, e.g. your existing lotto page. Drag to change order."));

                    logger.info("Checking section title on website navigation page");
                    sectionsAndPagesPage.findOnPage("//div[@class='mat-line'][contains(.,'"+uniqueSectionName+"')]");
                    logger.info(uniqueSectionName + " is displayed on website navigation page");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle+"')]"));
                    logger.info(newsTitle + " sub page is displayed on website navigation page");
                    assertFalse(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle2+"')]"));
                    logger.info(newsTitle2 + " sub page is NOT displayed on website navigation page");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle3+"')]"));
                    logger.info(newsTitle3 + " sub page is displayed on website navigation page");
                }

                if(area.equalsIgnoreCase("website")){
                    logger.info("Going to website again");
                    sectionsAndPagesPage.click(BackofficePage.BO_MenuDots);
                    sectionsAndPagesPage.click(LoginPage.GoToMyClubOption);
                    sectionsAndPagesPage.waitTwoSeconds();
                    sectionsAndPagesPage.switchToBrowserTab(1);
                    sectionsAndPagesPage.findOnPage(ContactPage.ClubPageContactLink);

                    logger.info("Checking correct pages are displayed under section on website");
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//span[@class='mat-button-wrapper'][contains(.,'"+uniqueSectionName+"')]"));
                    logger.info(uniqueSectionName + " is displayed on website");

                    logger.info("Click on the section title menu item and check if its pages are displayed in the dropdown");
                    sectionsAndPagesPage.click("//span[@class='mat-button-wrapper'][contains(.,'"+uniqueSectionName+"')]");
                    sectionsAndPagesPage.findOnPage("//a[@data-bs-toggle='collapse'][contains(.,'"+newsTitle+"')]");
                    assertFalse(sectionsAndPagesPage.isElementDisplayed("//a[@data-bs-toggle='collapse'][contains(.,'"+newsTitle2+"')]"));
                    assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@data-bs-toggle='collapse'][contains(.,'"+newsTitle3+"')]"));
                    logger.info("Correct pages are displayed in dropdown for the correct section menu item");
                }
                break;
            default:
        }
    }

    @Then("Club admin creates a new page")
    public void createNewPage(){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Create a new page");
        sectionsAndPagesPage.scrollPageDown();
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreatePageButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.CreatePageHeading);

        newsTitle = RandomStringUtils.randomAlphabetic(4) + " Page " + SeleniumUtilities.getDateTimeFormat("dd MMM yyyy HH:mm:ss");
        sectionsAndPagesPage.sendKeys(BackofficePage.FormControlNameTitle,  newsTitle);

        String NewsSummary = lorem.getTitle(6);
        sectionsAndPagesPage.sendKeys(BackofficePage.FormControlNameSummary, NewsSummary);

        String NewsArticleContent = lorem.getWords(100, 300);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.SectionsAndPagesQLEditorBlank, NewsArticleContent);
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesEmbedVideoButtonInEditorField);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.SectionsAndPagesEmbedVideoLinkField, "https://www.youtube.com/watch?v=WQOmyoQ7ISE");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesEmbedVideoSaveButton);

        sectionsAndPagesPage.uploadFileUsingJSExec("newsArticleImage.jpg", SectionsAndPagesPage.SectionsAndPagesUploadImageFilePath);
        sectionsAndPagesPage.waitTwoSeconds();
        logger.info("Click upload button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesUploadImageButton);

        sectionsAndPagesPage.waitTwoSeconds();
        sectionsAndPagesPage.scrollPageDown();
        sectionsAndPagesPage.click(BackofficePage.PublishButton);
//        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PageCreatedSuccessNotification);
        if (!backofficePage.isElementDisplayed(BackofficePage.LeftNavSectionsAndPages)) {
            backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
        }
        backofficePage.click(BackofficePage.LeftNavSectionsAndPages);
        backofficePage.waitTwoSeconds();
        backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Pages"));
        backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Sections"));
        backofficePage.findOnPage(SectionsAndPagesPage.CreateSectionButton);
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(SectionsAndPagesPage.CreateSectionButton)).isEnabled();
        logger.info("Is create section button disabled :- " + isButtonEnabled);
    }

    @Then("Club admin adds another page to the section that is displayed on the website")
    public void addAnotherPageToSectionThatIsAlreadyOnTheWebsite(){
        logger.info("Create another page to add to section");
        createASecondPage();

        int k = 1;
        sectionsAndPagesPage.click("(//mat-icon[@role='img'][contains(.,'edit')])["+k+"]");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.EditSectionTitleHeading);
        String sectionTitleName2 = sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.TitleField, "value");
        logger.info("Edit section title field contains : " + sectionTitleName2);
        logger.info("Section name we looking for is : " + uniqueSectionName);

        while(!sectionTitleName2.equalsIgnoreCase(uniqueSectionName)){
            logger.info("Clicking the cancel button in the edit section pop up");
            sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);
            k++;
            logger.info("Clicking edit icon " + k);
            sectionsAndPagesPage.click("(//mat-icon[@role='img'][contains(.,'edit')])["+k+"]");
            sectionTitleName2 = sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.TitleField, "value");
            logger.info("Edit section title field contains : " + sectionTitleName2);
        }
        sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);
        logger.info("Need to click the add page button in row " + k);
        k += 1;
        sectionsAndPagesPage.click("(//mat-icon[contains(.,'add')])["+k+"]");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.AddPagesInformationText);
        sectionsAndPagesPage.click("//span[@class='mat-checkbox-label'][contains(.,'"+newsTitle2+"')]");
        sectionsAndPagesPage.click(SectionsAndPagesPage.AddButton);
        sectionsAndPagesPage.waitTwoSeconds();
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PageAddedSuccessfullyNotification));
        assertFalse(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesAddedFailureNotification));
        sectionsAndPagesPage.click("//p[contains(.,'"+uniqueSectionName+"')]");
        sectionsAndPagesPage.findOnPage("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+ newsTitle+"')]");
        logger.info("First page is still added in the section");
        sectionsAndPagesPage.findOnPage("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+ newsTitle2+"')]");
        logger.info("Page has been successfully added to the section");
    }

    @Then("Club admin adds 3 pages to a section")
    public void addThreePagesToASection(){
        logger.info("Create 3 pages");
        createNewPage();
        createASecondPage();
        createAThirdPage();

        logger.info("Create a new section");
        createNewSection();

        int j = 1;
        sectionsAndPagesPage.click("(//mat-icon[@role='img'][contains(.,'edit')])["+j+"]");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.EditSectionTitleHeading);
        String sectionTitleName3 = sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.TitleField, "value");
        logger.info("Edit section title field contains : " + sectionTitleName3);
        logger.info("Section name we looking for is : " + uniqueSectionName);

        while(!sectionTitleName3.equalsIgnoreCase(uniqueSectionName)){
            logger.info("Clicking the cancel button in the edit section pop up");
            sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);
            j++;
            logger.info("Clicking edit icon " + j);
            sectionsAndPagesPage.click("(//mat-icon[@role='img'][contains(.,'edit')])["+j+"]");
            sectionTitleName3 = sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.TitleField, "value");
            logger.info("Edit section title field contains : " + sectionTitleName3);
        }
        sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);

        logger.info("Need to click the add page button in row " + j);
        j += 1;
        sectionsAndPagesPage.click("(//mat-icon[contains(.,'add')])["+j+"]");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.AddPagesInformationText);
        sectionsAndPagesPage.click("//span[@class='mat-checkbox-label'][contains(.,'"+newsTitle+"')]");
        sectionsAndPagesPage.click("//span[@class='mat-checkbox-label'][contains(.,'"+newsTitle2+"')]");
        sectionsAndPagesPage.click("//span[@class='mat-checkbox-label'][contains(.,'"+newsTitle3+"')]");
        sectionsAndPagesPage.click(SectionsAndPagesPage.AddButton);
        sectionsAndPagesPage.waitTwoSeconds();
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PageAddedSuccessfullyNotification));
        assertFalse(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesAddedFailureNotification));
        sectionsAndPagesPage.click("//p[contains(.,'"+uniqueSectionName+"')]");
        sectionsAndPagesPage.findOnPage("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+ newsTitle+"')]");
        sectionsAndPagesPage.findOnPage("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+ newsTitle2+"')]");
        sectionsAndPagesPage.findOnPage("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+ newsTitle3+"')]");
        logger.info("Pages have been successfully added to the section");
    }

    @Then("the section and the 3 pages are displayed on the website navigation page")
    public void checkTheThreePagesAreDisplayedOnTheWebsiteNavigationPage(){
        sectionsAndPagesPage.findOnPage(String.format(SectionsAndPagesPage.SectionsAndPagesPageText, "Add external links to your menu, e.g. your existing lotto page. Drag to change order."));
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle+"')]"));
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle2+"')]"));
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//a[@target='_blank'][contains(.,'"+newsTitle3+"')]"));
        logger.info("Page titles - "+newsTitle+ ", " +newsTitle2+ ", " +newsTitle3 +" - are displayed on website navigation page");
    }

    @Then("the section and the 3 pages are displayed on the website")
    public void checkTheThreePagesAreDisplayedOnTheWebsite(){
        logger.info("Going to website again");
        sectionsAndPagesPage.click(BackofficePage.DashboardTitleLeftNav);
        sectionsAndPagesPage.click(BackofficeDashboardPage.ViewWebsiteDashboardTileButton);
        sectionsAndPagesPage.waitTwoSeconds();
        sectionsAndPagesPage.switchToBrowserTab(1);

        logger.info("Verify that the section and its pages are displayed");
        sectionsAndPagesPage.findOnPage("//a[@data-bs-toggle='dropdown'][contains(.,'"+uniqueSectionName+"')]");
        sectionsAndPagesPage.click(SectionsAndPagesPage.MenuItemDropDownOnWebsite);
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//span[@class='mat-button-wrapper'][contains(.,'"+newsTitle+"')]"));
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//span[@class='mat-button-wrapper'][contains(.,'"+newsTitle2+"')]"));
        assertTrue(sectionsAndPagesPage.isElementDisplayed("//span[@class='mat-button-wrapper'][contains(.,'"+newsTitle3+"')]"));
        driverManager.driver.close();
    }

    @Then("Club admin removes a page from a section that is displayed on the website")
    public void clubAdminRemovesOneOfTheThreePagesFromSection(){
        logger.info("Open the section title " +uniqueSectionName+" dropdown");
        sectionsAndPagesPage.click("//p[contains(.,'"+uniqueSectionName+"')]");

        int num = 1;
        String pageTitleName = sectionsAndPagesPage.getElementAttribute("(//div[contains(@class,'col-lg-3 mx-auto py-3 ps-lg-4 text-break')])["+num+"]", "textContent");
        pageTitleName = pageTitleName.substring(1,pageTitleName.length()-1);
        logger.info("Actual page title : " + pageTitleName);
        logger.info("Expected page title: " + newsTitle2);
        while(!pageTitleName.equalsIgnoreCase(newsTitle2)){
            logger.info("Index number : " + num);
            pageTitleName = sectionsAndPagesPage.getElementAttribute("(//div[contains(@class,'col-lg-3 mx-auto py-3 ps-lg-4 text-break')])["+num+"]", "textContent");
            pageTitleName = pageTitleName.substring(1,pageTitleName.length()-1);
            logger.info("Page title : " + pageTitleName);
            num++;
            logger.info("Index number : " + num);
        }
        logger.info("Page title has index number : " + num + " so we need to click the remove page button with the same index number");
        sectionsAndPagesPage.click("(//button[contains(.,'remove page')])["+(num-1)+"]");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PleaseConfirmHeadingText);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.RemovePagePopUpInformationText);
        sectionsAndPagesPage.click(SectionsAndPagesPage.RemoveButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PageSuccessfullyRemovedNotification);
        sectionsAndPagesPage.waitTwoSeconds();
        assertFalse(sectionsAndPagesPage.isElementDisplayed("//div[@class='col-lg-3 mx-auto py-3 ps-lg-4 text-break'][contains(.,'"+newsTitle2+"')]"));
    }

    public void createAThirdPage(){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Create a new page");
        sectionsAndPagesPage.scrollPageDown();
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreatePageButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.CreatePageHeading);

        newsTitle3 = RandomStringUtils.randomAlphabetic(4) + " Page " + SeleniumUtilities.getDateTimeFormat("dd MMM yyyy HH:mm:ss");
        sectionsAndPagesPage.sendKeys(BackofficePage.FormControlNameTitle,  newsTitle3);

        String NewsSummary3 = lorem.getTitle(6);
        sectionsAndPagesPage.sendKeys(BackofficePage.FormControlNameSummary, NewsSummary3);

        String NewsArticleContent3 = lorem.getWords(100, 300);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.SectionsAndPagesQLEditorBlank, NewsArticleContent3);
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesEmbedVideoButtonInEditorField);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.SectionsAndPagesEmbedVideoLinkField, "https://www.youtube.com/watch?v=WQOmyoQ7ISE");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesEmbedVideoSaveButton);

        sectionsAndPagesPage.uploadFileUsingJSExec("newsArticleImage.jpg", SectionsAndPagesPage.SectionsAndPagesUploadImageFilePath);
        sectionsAndPagesPage.waitTwoSeconds();
        logger.info("Click upload button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesUploadImageButton);

        sectionsAndPagesPage.waitTwoSeconds();
        sectionsAndPagesPage.click(BackofficePage.PublishButton);
        if (!backofficePage.isElementDisplayed(BackofficePage.LeftNavSectionsAndPages)) {
            backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
        }
        backofficePage.click(BackofficePage.LeftNavSectionsAndPages);
        backofficePage.waitTwoSeconds();
        backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Pages"));
        backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Sections"));
        backofficePage.centreElement(SectionsAndPagesPage.CreateSectionButton);
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(SectionsAndPagesPage.CreateSectionButton)).isEnabled();
        logger.info("Is create section button disabled :- " + isButtonEnabled);
    }

    public void createASecondPage(){
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Create a new page");
        sectionsAndPagesPage.scrollPageDown();
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreatePageButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.CreatePageHeading);

        newsTitle2 = RandomStringUtils.randomAlphabetic(4) + " Page " + SeleniumUtilities.getDateTimeFormat("dd MMM yyyy HH:mm:ss");
        sectionsAndPagesPage.sendKeys(BackofficePage.FormControlNameTitle,  newsTitle2);

        String NewsSummary2 = lorem.getTitle(6);
        sectionsAndPagesPage.sendKeys(BackofficePage.FormControlNameSummary, NewsSummary2);

        String NewsArticleContent2 = lorem.getWords(100, 300);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.SectionsAndPagesQLEditorBlank, NewsArticleContent2);
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesEmbedVideoButtonInEditorField);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.SectionsAndPagesEmbedVideoLinkField, "https://www.youtube.com/watch?v=WQOmyoQ7ISE");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesEmbedVideoSaveButton);

        sectionsAndPagesPage.uploadFileUsingJSExec("newsArticleImage.jpg", SectionsAndPagesPage.SectionsAndPagesUploadImageFilePath);
        sectionsAndPagesPage.waitTwoSeconds();
        logger.info("Click upload button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.SectionsAndPagesUploadImageButton);

        sectionsAndPagesPage.waitTwoSeconds();
        sectionsAndPagesPage.click(BackofficePage.PublishButton);
        if (!backofficePage.isElementDisplayed(BackofficePage.LeftNavSectionsAndPages)) {
            backofficePage.click(BackofficePage.BackofficeLeftNavWebsite);
        }
        backofficePage.click(BackofficePage.LeftNavSectionsAndPages);
        backofficePage.waitTwoSeconds();
        backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Pages"));
        backofficePage.findOnPage(String.format(LoginPage.LoginPageText, "Sections"));
        boolean isButtonEnabled = driverManager.driver.findElement(By.xpath(SectionsAndPagesPage.CreateSectionButton)).isEnabled();
        logger.info("Is create section button disabled :- " + isButtonEnabled);
    }

    public void createNewSection(){
        sectionsAndPagesPage.waitThreeSeconds();
        logger.info("Click create section button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreateSectionButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.CreateSectionPopUpHeading);

        logger.info("Click that the create button is disabled");
        assertThat("Create button is NOT set to Inactive!!", sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.CreateSectionPopUpCreateButton, "disabled"), containsString("true"));

        logger.info("Click the cancel button");
        sectionsAndPagesPage.click(SectionsAndPagesPage.PopUpCancelButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.SectionsHeading);

        logger.info("Click create section button again");
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreateSectionButton);

        logger.info("Enter in 2 characters");
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.TitleField,"12");
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreateSectionPopUpHeading);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.MinimumThreeCharactersErrorText);
        assertThat("Create button is NOT set to Inactive!!", sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.CreateSectionPopUpCreateButton, "disabled"), containsString("true"));

        logger.info("Clear section name field");
        sectionsAndPagesPage.clearInputFieldUsingBackSpaceKey(SectionsAndPagesPage.TitleField);

        logger.info("Enter in 17 characters");
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.TitleField,"11111111111111111");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.MaximumSixteenCharactersErrorText);
        assertThat("Create button is NOT set to Inactive!!", sectionsAndPagesPage.getElementAttribute(SectionsAndPagesPage.CreateSectionPopUpCreateButton, "disabled"), containsString("true"));

        logger.info("Clear section name field");
        sectionsAndPagesPage.clearInputFieldUsingBackSpaceKey(SectionsAndPagesPage.TitleField);

        //once we can create sections then we should check unique name as well
        logger.info("Enter in unique section name");
        uniqueSectionName = "Section name " + RandomStringUtils.randomAlphabetic(3);
        sectionsAndPagesPage.sendKeys(SectionsAndPagesPage.TitleField, uniqueSectionName);
        sectionsAndPagesPage.click(SectionsAndPagesPage.CreateSectionPopUpCreateButton);
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.CreateSectionSuccessNotification);
        sectionsAndPagesPage.waitThreeSeconds();
    }

    public void verifyPageElementsOnSectionsAndPagesPage() {
        logger.info("Verify elements on Sections and Pages page");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PagesHeading);
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.CreatePageButton));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionsHeading));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.CreateSectionButton));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionsSearchBar));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesSearchBar));
    }

    public void verifyPagesColumnHeaders(){
        logger.info("Check pages column headers are displayed");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.PagesTitleColumnHeader);
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesCreatedDateColumnHeader));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesLastUpdatedColumnHeader));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.PagesActionColumnHeader));
    }

    public void verifySectionsColumnHeaders(){
        logger.info("Check sections column headers are displayed");
        sectionsAndPagesPage.findOnPage(SectionsAndPagesPage.SectionTitleColumnHeader);
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionCreatedDateColumnHeader));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionLastUpdatedColumnHeader));
        assertTrue(sectionsAndPagesPage.isElementDisplayed(SectionsAndPagesPage.SectionActionColumnHeader));
    }
}
