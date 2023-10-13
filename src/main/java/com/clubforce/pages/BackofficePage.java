package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;

public class BackofficePage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public BackofficePage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //Links
    public static final String BackofficeLeftNavContactMessages = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Contact messages')]";
    public static final String BackofficeLeftNavSponsors = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Sponsors')]";
    public static final String BackofficeLeftNavDashboard = "//span[@class='ms-2'][contains(.,'Dashboard')]";
    public static final String BackofficeSearchPage = "//span[@class='ms-2'][contains(.,'Search')]";
    public static final String BackofficeLeftNavWebsite = "//span[@class='ms-2'][contains(.,'Website')]";
    public static final String BackofficeLeftNavSettings = "//span[@class='ms-2'][contains(.,'Settings')]";
    public static final String BackofficeLeftNavEmail = "//span[@class='ms-2'][contains(.,'Email')]";
    public static final String BackofficeLeftNavAccount = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Account')]";
    public static final String AccountUpdateHeading = "//h1[contains(.,'Account update')]";
    public static final String EmailHeading = "//h1[contains(.,'Email')]";
    public static final String FooterPublishButton = "//button[@data-test='article-page-about-us.submit']";
    public static final String HeaderPublishButton = "(//button[contains(.,'Publish')])[1]";
    public static final String AddressCityField = "//input[contains(@formcontrolname,'city')]";
    public static final String AddressCountryField = "//*[contains(@formcontrolname,'country')]";
    public static final String AddressCountyField = "//*[contains(@formcontrolname,'county')]";
    public static final String AddressLine1Field = "//input[contains(@formcontrolname,'line1')]";
    public static final String AddressLine2Field = "//input[contains(@formcontrolname,'line2')]";
    public static final String AddressPostalCodeField = "//input[contains(@formcontrolname,'postal_code')]";
    public static final String BO_MenuDots = "//mat-icon[contains(.,'more_vert')]";
    public static final String BO_MenuDotsGoToMyClub = "(//span[contains(.,'Go to my club')])[2]";
    public static final String BackOfficeHamburgerOpen = "//mat-icon[contains(.,'menu_open')]";
    public static final String BackofficeHamburger = "//mat-icon[@role='img'][contains(.,'menu')]";
    public static final String BackofficeHamburgerMobile = "(//mat-icon[contains(.,'menu')])[2]";
    public static final String BackofficePageText = "//*[contains(text(),'%s')]";
    public static final String ConfirmPasswordField = "//input[contains(@formcontrolname,'confirmPassword')]";
    public static final String CreateAccountButton = "//button[contains(.,'reate')]";
    public static final String DashboardHeading = "//h1[contains(.,'Dashboard')]";
    public static final String DashboardTitleLeftNav = "//span[@class='ms-2'][contains(.,'Dashboard')]";
    public static final String CheckoutFeatureToggle = "(//div[@class='mat-slide-toggle-bar'])[3]";
    public static final String LottoFeatureToggle = "//div[contains(@class,'mat-slide-toggle-bar')]";
    public static final String ComortaisFeatureToggle = "(//div[@class='mat-slide-toggle-thumb'])[5]";
    public static final String SUComortaisFeatureToggleOFF = "//label[@class='mat-slide-toggle-label'][contains(.,'Comortais')]//input[contains(@aria-checked,'false')]";
    public static final String SUComortaisFeatureToggleON = "//label[@class='mat-slide-toggle-label'][contains(.,'Comortais')]//input[contains(@aria-checked,'true')]";
    public static final String FirstNameField = "//input[contains(@formcontrolname,'first_name')]";
    public static final String FormControlNameSummary = "//textarea[contains(@formcontrolname,'summary')]";
    public static final String FormControlNameTitle = "//input[@formcontrolname='title']";
    public static final String FormControlNameTitle2 = "(//input[contains(@formcontrolname,'title')])[2]";
    public static final String Form_Name = "//input[contains(@formcontrolname,'name')]";
    public static final String LastNameField = "//input[contains(@formcontrolname,'last_name')]";
    public static final String LeftNavAboutUs = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'About us')]";
    public static final String LeftNavCard = "//mat-icon[@role='img'][contains(.,'credit_card')]";
    public static final String LeftNavClubs = "//div[@class='mat-line'][contains(.,'Clubs')]";
    public static final String LeftNavDocuments = "//div[@class='mat-line'][contains(.,'Documents')]";
    public static final String LeftNavFolder = "//mat-icon[@role='img'][contains(.,'folder')]";
    public static final String LeftNavGroups = "//div[@class='mat-line'][contains(.,'Group')]";
    public static final String LeftNavMemberships = "//div[@class='mat-line'][contains(.,'Memberships')]";
    public static final String LeftNavNewsArticles = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'News articles')]";
    public static final String LeftNavOrders = "//div[@class='mat-line'][contains(.,'Orders')]";
    public static final String LeftNavSectionsAndPages = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Sections & Pages')]";
    public static final String LeftNavProducts = "//div[@class='mat-list-item-content'][contains(.,'widgetsProducts')]";
    public static final String LeftNavRugbyBall = "//mat-icon[@role='img'][contains(.,'sports_rugby')]";
    public static final String LeftNavShopBag = "//mat-icon[@role='img'][contains(.,'shopping_bag')]";
    public static final String LeftNavStar = "//mat-icon[@role='img'][contains(.,'star_border')]";
    public static final String LeftNavTheme = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Theme')]";
    public static final String LeftNavComortais = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Comortais')]";
    public static final String LeftNavUsersText = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Users')]";
    public static final String LottoLeftNav = "//span[@class='ms-2'][contains(.,'Lotto')]";
    public static final String LottoDetailsLeftNav = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Settings')]";
    public static final String LottoDrawsTile = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Draws')]";
    public static final String MobileField = "//input[contains(@formcontrolname,'mobile')]";
    public static final String OptionTextIreland = "(//span[@class='mat-option-text'][contains(.,'Ireland')])[1]";
    public static final String PaginationNextButton = "//button[contains(@aria-label,'Next page')]";
    public static final String PaginationNumbering = "//div[@class='mat-paginator-range-label'][contains(.,'%s')]";
    public static final String PaginationPreviousButton = "//button[contains(@aria-label,'Previous page')]";
    public static final String PaginationText = "//div[contains(@class,'mat-paginator-page-size-label')]";
    public static final String PasswordField = "//input[contains(@formcontrolname,'password')]";
    public static final String CreateAccountModalPasswordsDoNotMatchErrorText = "//strong[contains(.,'match')]";
    public static final String ProgressBar = "//div[contains(@class,'mat-progress-bar-element')]";
    public static final String SuccessfullyUpdated = "//simple-snack-bar[contains(.,'Successful updated.')]";
    public static final String TermsAndConditionsCheckbox = "//span[@class='mat-checkbox-label'][contains(.,'I agree to the')]";
    public static final String ThemeHeading = "//h1[contains(.,'Theme')]";
    public static final String ComortaisHeading = "//h1[contains(.,'Comortais')]";
    public static final String SetUpLottoLeftNav = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Lotto setup')]";
    public static final String LottoDrawsHeading = "//h1[@data-test='lottery-draws-list.pageTitle'][contains(.,'Lotto draws')]";
    public static final String ViewPayslipsButton = "//button[@data-test='lottery-draws-list.playslipButton']";
    public static final String ArticlesHeading = "//h1[contains(.,'Articles')]";
    public static final String MessagesHeading = "//h1[contains(.,'messages')]";
    public static final String SULottoFeatureToggleOFF = "//label[@class='mat-slide-toggle-label'][contains(.,'Lotto')]//input[contains(@aria-checked,'false')]";
    public static final String SULottoFeatureToggleON = "//label[@class='mat-slide-toggle-label'][contains(.,'Lotto')]//input[contains(@aria-checked,'true')]";
    public static final String ImageHelpText = "//small[contains(.,'One image only. Minimum size must be 1600 pixels x 900 pixels, this is to allow your users’ social sharing')]";
    public static final String LastLoggedInDate = "//span[contains(.,'Last logged in')]";
    public static final String CreatedColumnHeader = "//span[contains(.,'Created')]";
    public static final String EmailColumnHeader = "//span[contains(.,'E-Mail')]";
    public static final String NameColumnHeader = "//span[contains(.,'Name')]";
    public static final String HeadlineHeading = "//h1[contains(@itemprop,'headline')]";
    public static final String AutomationBallanImage =  "//img[contains(@alt,'Automation_Ballán')]";
    public static final String PublishButton = "//button[@data-test='article-page-about-us.submit'][contains(.,'Publish')]";
    public static final String LeftNavFinance = "//span[@class='ms-2'][contains(.,'Finance')]";
    public static final String LeftNavAutoRenewals = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Auto renewals')]";
    public static final String LeftNavPayouts = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Payouts')]";
    public static final String LottoButton = "//span[contains(.,'Lotto')]";
    public static final String LeftNavClubLogoImage = "(//*[contains(@src, 'cloudfront.net')])[1]";
    public static final String ClubLogoImage = "(//*[contains(@src, 'cloudfront.net')])[2]";
    public static final String PaginationNumberingText1to10 = "//*[contains(text(),'1 – 10')]";
    public static final String PaginationNumberingText11to20 = "//*[contains(text(),'11 – 20')]";
    public static final String SUUpdateButton = "//button[contains(.,'Update')]";
    public static final String NoMembershipPlanSetUpText = "//h2[contains(.,'No membership plans set up')]";
    public static final String CreateYourMembershipPlanText = "//*[contains(text(),'Create your membership plans and start signing up your members')]";
    public static final String SetUpMembershipButton = "//button[contains(.,'edit Set up membership')]";
    public static final String MembershipLeftNav = "//span[@class='ms-2'][contains(.,'Membership')]";
    public static final String UpdatedSuccessfully = "//span[contains(.,'Updated successfully!')]";
    public static final String SponsorsHeading = "//h1[contains(.,'Sponsors')]";
    public static final String BackofficeLeftNavMembership = "//span[@class='ms-2'][contains(.,'Membership')]";
    public static final String BackofficeLeftNavMembershipSelection = "//li[@data-test='button.Membership plans']";
    public static final String BackofficeLeftNavMembershipReportSelection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Membership reports')]";
    public static final String BackofficeLeftNavMembershipDiscountsSelection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Multi-plan discounts')]";
    public static final String BackofficeLeftNavDiscountCodesSelection = "//li[@routerlinkactive='fw-bold link-primary'][contains(.,'Discount codes')]";
    public static final String BackofficeLeftNavSetUpMembershipSelection = "//li[contains(@data-test,'button.Membership')]";
    public static final String BackofficeConnectGroupsLeftnav = "//span[@class='ms-2'][contains(.,'Connect')]";
    public static final String DisabledRemoveImageButton = "(//div[contains(.,'delete Remove Image')])[17]";
    public static final String EnabledRedRemoveImageButton = "//button[contains(.,'delete Remove Image')]";
    public static final String RemoveImageButton = "//span[contains(.,'Remove image')]";
    public static final String UploadButton = "//button[contains(.,'Upload Image')]";
    public static final String UploadButtonOnCropper = "//button[contains(.,'Upload image')]";
    public static final String ItemsPerPageText = "//*[contains(text(),'Items per page:')]";
    public static final String MembershipViewPlansDashboardButton = "//button[contains(.,'View membership plans group')]";
    public static final String MembershipGraphInsightsIcon = "//mat-icon[@role='img'][contains(.,'insights')]";
    public static final String MembershipPlansGraphListIcon = "//mat-icon[@role='img'][contains(.,'list')]";
    public static final String MembershipDashboardSignUpsHeading = "//h4[@data-test='dashboard-tile-title'][contains(.,'Member Signups')]";
    public static final String LeftNavSetUpMembershipOption = "//li[@data-test='button.Membership setup']";

    //Methods
    public void openBackOfficeLeftSideNav() {
        waitTwoSeconds();
        if (driverManager.agent.contains("appium")) {
            click(BackofficePage.BackofficeHamburger);
            findOnPage("//span[@class='ms-2'][contains(.,'Dashboard')]");
            waitTwoSeconds();
        }
    }

    public void setBackOfficeOrderPageToTodayDate() {
        click("//*[contains(@class,'mat-datepicker-toggle-default-icon')]");
        click("//div[contains(@class,'mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today')]");
        click("//div[contains(@class,'mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today')]");
    }

    public void setPaginationTo25() {
        clickElementRightOf("//div[@class='mat-paginator-page-size-label'][contains(.,'Items per page:')]","//span[contains(.,'10')]");
        click("//span[@class='mat-option-text'][contains(.,'25')]");
        waitOneSecond();
    }
 }
