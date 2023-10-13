package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AccountPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public AccountPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    // logger
    private static final Logger logger = LogManager.getLogger();

    // Links
    public static final String AccountUpdateHeading = "//h1[contains(.,'Account update')]";
    public static final String AccountPageUpdateTopButton = "(//button[contains(.,'Update')])[1]";
    public static final String AccountPageUpdateBottomButton = "(//button[contains(.,'Update')])[2]";
    public static final String AccountPageMailField = "(//input[@formcontrolname='email'])[1]";
    public static final String AccountPagePhoneField1 = "(//input[@formcontrolname='phone'])[1]";
    public static final String AccountPageWebsiteField = "//input[@formcontrolname='website']";
    public static final String AccountPagePostCodeField = "//input[contains(@formcontrolname,'postal_code')]";
    public static final String AccountPageCountyField = "//input[contains(@formcontrolname,'county')]";
    public static final String AccountPageCityField = "//input[contains(@formcontrolname,'city')]";
    public static final String AccountPageAddressField = "//input[@formcontrolname='line1']";
    public static final String AccountPageFacebookField = "//input[contains(@formcontrolname,'facebook')]";
    public static final String AccountPageInstagramField = "//input[contains(@formcontrolname,'instagram')]";
    public static final String AccountPageTwitterField = "//input[contains(@formcontrolname,'twitter')]";
    public static final String SportsAssociationText = "//p[contains(.,'Sports')]";
    public static final String AccountUpdatedNotification = "//span[contains(.,'Account updated!')]";
    public static final String SocialMediaText = "//p[contains(.,'Social media')]";

    public static String RandomMailHOLDER;
    public static String LOTTORandomMailHolder;
    public static String MEMRandomMailHolder;
    public static String randomFirstName;
    public static String randomLastName;
    public static String randomMobileNumber;
    public static String randomStreetName;
    public static String randomCity;
    public static String randomZipCode;
    public static String randomCountyName;
    public static String randomCountry;

    // Methods
    public void errorHandlingCreateAccountModal() {
        logger.info("Verifying elements on club account page");
        logger.info("First entering an invalid mail");
        sendKeys(LoginPage.LoginEmailField, "''''@456.789");
        click(LoginPage.CardHeader);
        findOnPage(LoginPage.EmailMustBeValidErrorText);

        logger.info("Entering a mail I will want to change");
        sendKeys(LoginPage.LoginEmailField, "123@456.789");
        click(LoginPage.LoginNextButton);
        findOnPage(LoginPage.CreateAccountTitle);
        click(LoginPage.WrongEmailButton);
    }

    public void useCreateAccountModal(String product) {
        Lorem lorem = LoremIpsum.getInstance();

        logger.info("Entering a valid, but brand new, mail address");

        if (WebDriverManager.envName.contains("prod")) {
            RandomMailHOLDER = "wnzt4." + RandomStringUtils.randomAlphabetic(7).toLowerCase() + "@inbox.testmail.app";
            LOTTORandomMailHolder = "wnzt4." + RandomStringUtils.randomAlphabetic(7).toLowerCase()
                    + "@inbox.testmail.app";
        } else {
            RandomMailHOLDER = "qa+" + RandomStringUtils.randomNumeric(10) + "@clubforce.com";
            LOTTORandomMailHolder = "qa+" + RandomStringUtils.randomNumeric(10) + "@clubforce.com";
            MEMRandomMailHolder = "qa+" + RandomStringUtils.randomNumeric(10) + "@clubforce.com";
        }

        if (product.contains("Lotto")) {
            logger.info("Brand new mail address is: " + LOTTORandomMailHolder);
            sendKeys(LoginPage.LoginEmailField, LOTTORandomMailHolder);
        } else if (product.contains("Membership")) {
            logger.info("Brand new mail address is: " + MEMRandomMailHolder);
            sendKeys(LoginPage.LoginEmailField, MEMRandomMailHolder);
        } else {
            logger.info("Brand new mail address is: " + RandomMailHOLDER);
            sendKeys(LoginPage.LoginEmailField, RandomMailHOLDER);
        }
        click(LoginPage.LoginNextButton);
        findOnPage(LoginPage.CreateAccountTitle);

        logger.info("Entering create account passwords");
        if (WebDriverManager.envName.contains("test")) {
            sendKeys(BackofficePage.PasswordField, "B3deG2FnmrEy");
            sendKeys(BackofficePage.ConfirmPasswordField, "b3deG2FnmrEy");
            click(LoginPage.HaveAtLeastOneLowerCaseText);
            findOnPage(BackofficePage.CreateAccountModalPasswordsDoNotMatchErrorText);
            sendKeys(BackofficePage.PasswordField, "b3deG2FnmrEy");
            assertFalse(isElementPresent(BackofficePage.CreateAccountModalPasswordsDoNotMatchErrorText));
        } else {
            sendKeys(BackofficePage.PasswordField, "b3deG2FnmrEy");
            sendKeys(BackofficePage.ConfirmPasswordField, "This is wrong password");
            click(LoginPage.HaveAtLeastOneLowerCaseText);
            findOnPage(BackofficePage.CreateAccountModalPasswordsDoNotMatchErrorText);
            sendKeys(BackofficePage.ConfirmPasswordField, "b3deG2FnmrEy");
        }

        logger.info("Entering create account personal details");
        randomFirstName = lorem.getFirstName() + " " + lorem.getFirstName();
        click(BackofficePage.FirstNameField);
        click(LoginPage.HaveAtLeastOneLowerCaseText);
        assertTrue(isElementPresent(LoginPage.FirstNameIsRequiredErrorText));
        logger.info("First Name is required, error message displayed");
        sendKeys(BackofficePage.FirstNameField, randomFirstName);
        logger.info(randomFirstName + " is entered into first name field");

        randomLastName = lorem.getLastName();
        click(BackofficePage.LastNameField);
        click(LoginPage.HaveAtLeastOneLowerCaseText);
        assertTrue(isElementPresent(LoginPage.LastNameIsRequiredErrorText));
        logger.info("Last Name is required, error message displayed");
        sendKeys(BackofficePage.LastNameField, randomLastName);
        logger.info(randomLastName + " is entered into Last Name field");

        click(BackofficePage.MobileField);
        click(LoginPage.HaveAtLeastOneLowerCaseText);
        assertTrue(isElementPresent(LoginPage.MobilePhoneNumberIsRequiredErrorText));
        logger.info("Mobile phone number is required, error message displayed");
        randomMobileNumber = RandomStringUtils.randomNumeric(10);
        sendKeys(BackofficePage.MobileField, randomMobileNumber);
        logger.info("Mobile number is entered into Mobile Number field");

        String[] countries = { "Scotland", "Ireland", "Northern Ireland", "England", "Wales", "South Africa" };

        String[] irelandCounties = { "Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork",
                "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare",
                "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo",
                "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone",
                "Waterford", "Westmeath", "Wexford", "Wicklow" };

        String[] northernIrelandCounties = { "Antrim", "Armagh", "Derry", "Down", "Fermanagh", "Tyrone" };

        String[] englandCounties = { "Avon", "Bedfordshire", "Berkshire", "Buckinghamshire", "Cambridgeshire",
                "Cheshire", "Cornwall", "Cumbria", "Derbyshire", "Devon", "Dorset", "Durham", "Essex",
                "Gloucestershire",
                "Greater London", "Hampshire", "Herefordshire", "Hertfordshire", "Isle of Wight", "Kent", "Lancashire",
                "Leicestershire", "Lincolnshire", "Merseyside", "Norfolk", "Northamptonshire", "Northumberland",
                "Nottinghamshire", "Oxfordshire", "Rutland", "Schools", "Shropshire", "Somerset", "Staffordshire",
                "Suffolk", "Surrey", "Sussex", "Tyne and Wear", "Warwickshire", "West Midlands", "Wiltshire",
                "Worcestershire", "Yorkshire" };

        String[] walesCounties = { "Isle of Anglesey (Ynys Môn)", "Gwynedd", "Conwy",
                "Denbighshire (Sir Ddinbych)", "Flintshire (Sir y Fflint)", "Wrexham (Wrecsam)",
                "Ceredigion", "Powys", "Pembrokeshire (Sir Benfro)", "Carmarthenshire (Sir Gaerfyrddin)",
                "Swansea (Abertawe)", "Neath Port Talbot (Castell-nedd Port Talbot)", "Bridgend (Pen-y-bont ar Ogwr)",
                "Vale of Glamorgan (\u200B\u200BBro Morgannwg)", "Rhondda Cynon Taff", "Cardiff (Caerdydd)",
                "Merthyr Tydfil (Merthyr Tudful)", "Caerphilly (Caerffili)", "Newport (Casnewydd)",
                "Torfaen (Tor-faen)", "Blaenau Gwent", "Monmouthshire (Sir Fynwy)" };

        String[] scotlandCounties = { "Aberdeen", "Angus", "Argyll", "Ayrshire",
                "Banffshire", "Berwickshire", "Bute", "Caithness", "Clackmannan",
                "Dumfriesshire", "Dunbartonshire", "East Lothian", "Fife", "Inverness-shire",
                "Kincardineshire", "Kinross-shire", "Kirkcudbrightshire", "Lanarkshire",
                "Midlothian", "Moray", "Nairnshire", "Orkney", "Peebleshire", "Perthshire",
                "Renfrewshire", "Ross and Cromarty", "Roxburghshire", "Selkirkshire",
                "Shetland", "Stirlingshire", "Sutherland", "West Lothian", "Wigtownshire" };

        Random random = new Random();
        int i = random.nextInt(countries.length);
        randomCountry = countries[i];

        click(BackofficePage.AddressCountryField);
        click("(//span[@class='mat-option-text'][contains(.,'" + randomCountry + "')])[1]");
        logger.info(randomCountry + " has been selected from country drop down list");

        click(BackofficePage.AddressCountyField);

        switch (randomCountry) {
            case "Ireland":
                for (String irelandCounty : irelandCounties) {
                    findOnPage("//span[@class='mat-option-text'][contains(.,'" + irelandCounty + "')]");
                    logger.info(irelandCounty + " is displayed in dropdown");
                }
                int j = random.nextInt(irelandCounties.length);
                randomCountyName = irelandCounties[j];
                click("//span[@class='mat-option-text'][contains(.,'" + randomCountyName + "')]");
                logger.info("County is " + randomCountyName);
                break;
            case "Northern Ireland":
                for (String northernIrelandCounty : northernIrelandCounties) {
                    findOnPage("//span[@class='mat-option-text'][contains(.,'" + northernIrelandCounty + "')]");
                    logger.info(northernIrelandCounty + " is displayed in dropdown");
                }

                int k = random.nextInt(northernIrelandCounties.length);
                randomCountyName = northernIrelandCounties[k];
                click("//span[@class='mat-option-text'][contains(.,'" + randomCountyName + "')]");
                logger.info("County is " + randomCountyName);
                break;
            case "England":
                for (String englandCounty : englandCounties) {
                    findOnPage("//span[@class='mat-option-text'][contains(.,'" + englandCounty + "')]");
                    logger.info(englandCounty + " is displayed in dropdown");
                }

                int l = random.nextInt(englandCounties.length);
                randomCountyName = englandCounties[l];
                click("//span[@class='mat-option-text'][contains(.,'" + randomCountyName + "')]");
                logger.info("County is " + randomCountyName);
                break;
            case "Wales":
                for (String walesCounty : walesCounties) {
                    findOnPage("//span[@class='mat-option-text'][contains(.,'" + walesCounty + "')]");
                    logger.info(walesCounty + " is displayed in dropdown");
                }

                int m = random.nextInt(walesCounties.length);
                randomCountyName = walesCounties[m];
                click("//span[@class='mat-option-text'][contains(.,'" + randomCountyName + "')]");
                logger.info("County is " + randomCountyName);
                break;
            case "Scotland":
                for (String scotlandCounty : scotlandCounties) {
                    findOnPage("//span[@class='mat-option-text'][contains(.,'" + scotlandCounty + "')]");
                    logger.info(scotlandCounty + " is displayed in dropdown");
                }

                int n = random.nextInt(scotlandCounties.length);
                randomCountyName = scotlandCounties[n];
                click("//span[@class='mat-option-text'][contains(.,'" + randomCountyName + "')]");
                logger.info("County is " + randomCountyName);
                break;
            case "South Africa":
                findOnPage("//input[contains(@formcontrolname,'county')]");
                randomCountyName = "Gauteng";
                sendKeys("//input[contains(@formcontrolname,'county')]", randomCountyName);
                logger.info("County is " + randomCountyName);
                break;
            default:
        }

        randomZipCode = lorem.getZipCode();
        sendKeys(BackofficePage.AddressPostalCodeField, randomZipCode);
        logger.info(randomZipCode + " is entered into Postal Code field");
        randomCity = lorem.getCity();
        sendKeys(BackofficePage.AddressCityField, randomCity);
        logger.info(randomCity + " is entered into City field");

        click(BackofficePage.AddressLine1Field);
        click(LoginPage.HaveAtLeastOneLowerCaseText);
        assertTrue(isElementPresent(LoginPage.AddressLine1IsRequiredErrorText));
        randomStreetName = "12 " + randomFirstName + " street";
        sendKeys(BackofficePage.AddressLine1Field, randomStreetName);
        logger.info("12 " + randomFirstName + " street" + " is entered into Address line 1 field");

        String randomMaleName = lorem.getFirstNameMale();
        sendKeys(BackofficePage.AddressLine2Field, randomMaleName + " apartments");
        logger.info(randomMaleName + " apartments" + " is entered into Address line 2 field");

        logger.info("Accepting Terms and Conditions, and also marketing consent");
        click(BackofficePage.TermsAndConditionsCheckbox);

        logger.info("Click button to create account");
        click(BackofficePage.CreateAccountButton);
        waitFiveSeconds();
    }

    public void useCreateAccountModalPresetMail(String member_mail) {
        Lorem lorem = LoremIpsum.getInstance();

        sendKeys(BackofficePage.PasswordField, "b3deG2FnmrEy");
        sendKeys(BackofficePage.ConfirmPasswordField, "b3deG2FnmrEy");
        sendKeys(BackofficePage.FirstNameField, "Mark");
        sendKeys(BackofficePage.LastNameField, "Bark");
        sendKeys(BackofficePage.MobileField, "0851234567");
        click(BackofficePage.AddressCountryField);
        findOnPage(BackofficePage.OptionTextIreland);
        click(BackofficePage.OptionTextIreland);
        sendKeys(BackofficePage.AddressCountyField, "Galway");
        sendKeys(BackofficePage.AddressPostalCodeField, "123123");
        sendKeys(BackofficePage.AddressCityField, "Galway");
        ;
        sendKeys(BackofficePage.AddressLine1Field, "12 main street");
        click(BackofficePage.TermsAndConditionsCheckbox);
        logger.info("Click button to create account");
        scrollPageToBottom();
        click(BackofficePage.CreateAccountButton);
        waitForElementDisplayedByXpathWithTimeout("//strong[@data-test='app.userName'][contains(.,'Hello')]", 10);
    }
}
