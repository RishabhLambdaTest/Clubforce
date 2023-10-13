package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class SectionsAndPagesPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public SectionsAndPagesPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    public static final String SectionsAndPagesPageText = "//*[contains(text(),'%s')]";
    public static final String SectionsHeading = "//h1[contains(.,'Sections')]";
    public static final String PagesHeading = "//h1[contains(.,'Pages')]";
    public static final String CreatePageHeading = "//h1[contains(.,'Create page')]";
    public static final String CreateSectionButton = "//button[contains(.,'create section add')]";
    public static final String CreatePageButton = "//a[contains(.,'create page add')]";
    public static final String SectionsSearchBar = "(//input[contains(@filtername,'title')])[1]";
    public static final String PagesSearchBar = "(//input[contains(@filtername,'title')])[2]";
    public static final String SectionTitleColumnHeader = "(//span[contains(.,'Title')])[1]";
    public static final String SectionCreatedDateColumnHeader = "(//span[contains(.,'Created date')])[1]";
    public static final String SectionLastUpdatedColumnHeader = "(//span[contains(.,'Last updated')])[1]";
    public static final String SectionActionColumnHeader = "(//strong[contains(.,'Actions')])[1]";
    public static final String PagesTitleColumnHeader = "(//span[contains(.,'Title')])[2]";
    public static final String PagesCreatedDateColumnHeader = "(//span[contains(.,'Created date')])[2]";
    public static final String PagesLastUpdatedColumnHeader = "(//span[contains(.,'Last updated')])[2]";
    public static final String PagesActionColumnHeader = "(//div[@data-test='widscreen.table.header-item'][contains(.,'Actions')])[2]";
    public static final String CreateSectionPopUpHeading = "//h2[contains(.,'Create section')]";
    public static final String TitleField = "//input[contains(@formcontrolname,'title')]";
    public static final String CreateSectionPopUpCreateButton = "//button[@class='btn btn-primary rounded-0 d-flex align-items-center text-uppercase'][contains(.,'Create')]";
    public static final String PopUpCancelButton = "//button[contains(.,'Cancel')]";
    public static final String MinimumThreeCharactersErrorText = "//div[@class='ng-star-inserted'][contains(.,'Minimum 3 characters')]";
    public static final String MaximumSixteenCharactersErrorText = "//div[@class='ng-star-inserted'][contains(.,'Maximum 16 characters')]";
    public static final String FirstAddPagesButton = "(//button[contains(.,'add add pages')])[1]";
    public static final String AddPagesInformationText = "//span[contains(.,'Please select one or more pages to add to section')]";
    public static final String AddButton = "//button[contains(.,'Add')]";
    public static final String PageAddedSuccessfullyNotification = "//span[contains(.,'Page/s successfully added to section.')]";
    public static final String PagesAddedFailureNotification = "//h4[contains(.,'Page/s could not be successfully added to section. Please try again.')]";
    public static final String FirstDeleteBinIcon = "(//mat-icon[contains(.,'delete_outline')])[1]";
    public static final String PleaseConfirmHeadingText = "//h2[@data-test='confirm-dialog.page'][contains(.,'Please confirm')]";
    public static final String DeletePopUpInformationText = "//mat-dialog-content[contains(.,'Are you sure you want to delete this section?Your pages will not be deleted but they will be removed from the section.')]";
    public static final String DeletePopUpDeleteButton = "//button[contains(.,'Delete')]";
    public static final String DeleteSectionSuccessNotification = "//span[contains(.,'Section successfully deleted.')]";
    public static final String CreateSectionSuccessNotification = "//span[contains(.,'Section successfully created.')]";
    public static final String FirstEditIconButton = "(//mat-icon[@role='img'][contains(.,'edit')])[1]";
    public static final String EditSectionTitleHeading = "//h2[contains(.,'Edit section title')]";
    public static final String PopUpUpdateButton = "//button[contains(.,'Update')]";
    public static final String SectionTitleMustBeUniqueErrorText = "//div[@class='ng-star-inserted'][contains(.,'Section title must be unique.')]";
    public static final String SectionUpdatedSuccessNotification = "//span[contains(.,'Section updated.')]";
    public static final String FirstSectionDropdownArrow = "(//mat-icon[contains(@class,'mat-icon notranslate cursor-pointer material-icons mat-icon-no-color')])[1]";
    public static final String RemovePageSecondButton = "(//button[contains(.,'remove page')])[2]";
    public static final String RemovePagePopUpInformationText = "//mat-dialog-content[contains(.,'Are you sure you want to remove the page from this section?The page will not be deleted it will just be removed from the section')]";
    public static final String RemoveButton = "//button[contains(.,'Remove')]";
    public static final String PageSuccessfullyRemovedNotification = "//span[contains(.,'Page successfully removed from section.')]";
    public static final String SectionWebsiteNavigationOption = "//span[@class='mat-option-text'][contains(.,'Section')]";
    public static final String SectionLinkDropDownWebsiteNavigation = "(//div[contains(.,'Section Link')])[10]";
    public static final String CreateButton = "//button[contains(.,'Create')]";
    public static final String SectionMenuItemSuccessfullyCreatedNotification ="//span[contains(.,'Successfully created!')]";
    public static final String MenuItemDropDownOnWebsite= "//a[contains(@id,'dropdownMenuMore')]";
    public static final String SectionsAndPagesUploadImageFilePath = "(//input[@type='file'])[2]";
    public static final String SectionsAndPagesQLEditorBlank = "//div[contains(@class,'ql-editor ql-blank')]";
    public static final String SectionsAndPagesEmbedVideoButtonInEditorField = "//button[contains(@class,'ql-video')]";
    public static final String SectionsAndPagesEmbedVideoLinkField = "//input[@data-video='Embed URL']";
    public static final String SectionsAndPagesEmbedVideoSaveButton = "//a[contains(@class,'ql-action')]";
    public static final String SectionsAndPagesUploadImageButton = "//button[contains(.,'Upload image')]";
}
