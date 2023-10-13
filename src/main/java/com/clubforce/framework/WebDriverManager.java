package com.clubforce.framework;

import com.clubforce.pages.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import static org.testng.Assert.fail;

public class WebDriverManager {

    // shared driver instance
    public RemoteWebDriver driver;

    // logger
    private static final Logger logger = LogManager.getLogger();
    public String logThread;

    // page objects
    public AccountPage accountPage;
    public ArticlePage articlePage;
    public BackofficePage backofficePage;
    public ComortaisPage comortaisPage;
    public ContactPage contactPage;
    public ConnectPage connectPage;
    public ClubPage clubPage;
    public EmailPage emailPage;
    public LoginPage loginPage;
    public LottoPage lottoPage;
    public MobileBrowserPage mobileBrowserPage;
    public MembershipPage membershipPage;
    public MyAccountPage myAccountPage;
    public PaymentProviderPage paymentProviderPage;
    public ProductsPage productsPage;
    public PromotionsPage promotionsPage;
    public BackofficeDashboardPage backofficeDashboardPage;
    public BackofficeAboutUsPage backofficeAboutUsPage;
    public RevenueReportPage revenueReportPage;
    public MembershipReportPage membershipReportPage;
    public SectionsAndPagesPage sectionsAndPagesPage;
    public xgbTemplatePage xgbTemplatePage;
    public xgbDashboardPage xgbDashboardPage;
    public UsersPage usersPage;
    public WebsiteSettingsPage websiteSettingsPage;
    public ThemePage themePage;
    public BannerPage bannerPage;
    public SponsorPage sponsorPage;
    public WebsiteNavigationPage websiteNavigationPage;
    public SuperUserPage superUserPage;
    public PayoutPage payoutPage;
    public MembershipDiscountPage membershipDiscountPage;
    public MembershipRegistrationPage membershipRegistrationPage;
    public DiscountCodePage discountCodePage;

    // environment details as set by maven profile
    public static String envURL;
    public static String lottoClubURL;
    public static String membershipURL;
    public static String backofficeURL;
    public static String superuserURL;
    public static String myAccountURL;
    public static String superuserUsername;
    public static String superuserPassword;
    public static String clubAdminUsername;
    public static String clubAdminPassword;
    public static String myAccountUsername;
    public static String myAccountPassword;
    public static String lottoClubAdminUsername;
    public static String lottoClubAdminPassword;
    public static String membershipClubAdminUsername;
    public static String membershipClubAdminPassword;
    public static String adminMemberUsername;
    public static String adminMemberPassword;
    public static String xgbURL;
    public static String xgbAdminUsername;
    public static String xgbAdminPassword;
    public static String envName;
    public String agent;
    public static String driverPath;
    public static String baseDir;
    public static String buildDir;
    public static String platform = System.getProperty("os.name");

    // browser capabilities
    private final DesiredCapabilities caps = new DesiredCapabilities();
    public boolean ignoreInvalidCerts = false;
    private boolean headlessBrowser;

    public String localBrowserDownloadDir;
    private String userName = "rishabhsinghlambdatest";
    private String accessKey = "MtmThl1OkRKinMaMQ9qg9BZ2xgf8NhK0G30HMJ7VCudgcFvNt9";

    public void setProperties() {
        logger.info("setProperties found");
        // load properties file
        Properties props = new Properties();
        try {
            props.load(this.getClass().getResourceAsStream("/config.properties"));
            logger.info("config.properties file found");
        } catch (IOException e) {
            logger.error("config.properties file not found!", e);
        }

        // output log level
        logger.info("Log level=" + props.getProperty("logLevel"));

        // environment details as set by maven profile
        logger.info("Getting properties from config file");
        envName = props.getProperty("envName");
        logger.info("props EnvName " + envName);
        agent = props.getProperty("agent");
        headlessBrowser = "true".equals(props.getProperty("headlessBrowser"));
        driverPath = props.getProperty("driverPath");
        logger.info("driverPath: " + driverPath);
        baseDir = props.getProperty("baseDir");
        buildDir = props.getProperty("buildDir");
        envURL = props.getProperty("envURL");
        lottoClubURL = props.getProperty("lottoClubURL");
        membershipURL = props.getProperty("membershipURL");
        backofficeURL = props.getProperty("backofficeURL");
        superuserURL = props.getProperty("superuserURL");
        myAccountURL = props.getProperty("myAccountURL");
        clubAdminUsername = props.getProperty("clubAdminUsername");
        clubAdminPassword = props.getProperty("clubAdminPassword");
        superuserUsername = props.getProperty("superuserUsername");
        superuserPassword = props.getProperty("superuserPassword");
        myAccountUsername = props.getProperty("myAccountUsername");
        myAccountPassword = props.getProperty("myAccountPassword");
        lottoClubAdminUsername = props.getProperty("lottoClubAdminUsername");
        lottoClubAdminPassword = props.getProperty("lottoClubAdminPassword");
        membershipClubAdminUsername = props.getProperty("membershipClubAdminUsername");
        membershipClubAdminPassword = props.getProperty("membershipClubAdminPassword");
        adminMemberUsername = props.getProperty("adminMemberUsername");
        adminMemberPassword = props.getProperty("adminMemberPassword");
        xgbURL = props.getProperty("xgbURL");
        xgbAdminUsername = props.getProperty("xgbAdminUsername");
        xgbAdminPassword = props.getProperty("xgbAdminPassword");

        if (envURL.equals("${env.url}")) {
            fail("Test environment profile was not set!");
        }
    }

    public void launchDevice(String featureScenario) throws MalformedURLException {
        logThread = UUID.randomUUID().toString();
        ThreadContext.put("threadID", logThread);
        logger.info("Test device is " + agent);
        switch (agent) {
            // case "chrome.local.win":
            // case "chrome.local.mac":
            // case "chrome.local.linux":
            // case "pixel7viewport.appium.local":
            //     System.setProperty("webdriver.chrome.driver", driverPath);
            //     driver = new ChromeDriver(setChromeOptions());
            //     break;
            // case "edge.local.win":
            //     System.setProperty("webdriver.edge.driver", driverPath);
            //     driver = new EdgeDriver();
            //     break;
            // case "firefox.local.win":
            // case "firefox.local.mac":
            // case "firefox.local.mac.aarch64":
            //     System.setProperty("webdriver.gecko.driver", driverPath);
            //     driver = new FirefoxDriver();
            //     break;
            // case "chrome.selenium_grid":
            //     driver = new RemoteWebDriver(new URL("http://104.199.31.222:4444/wd/hub"), setChromeOptions());
            //     break;
            // case "safari.local.mac":
            //     driver = new SafariDriver(); // remember to set local safari to allow remote control
            //     break;
            // case "emulator.local.linux":
            //     DesiredCapabilities capLocalEmulatorAndroidCollection = new DesiredCapabilities();
            //     capLocalEmulatorAndroidCollection.setCapability(CapabilityType.PLATFORM_NAME, "Android");
            //     capLocalEmulatorAndroidCollection.setCapability(MobileCapabilityType.DEVICE_NAME, "Local_emu");
            //     capLocalEmulatorAndroidCollection.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            //     capLocalEmulatorAndroidCollection.setCapability("autoGrantPermissions", "true");
            //     capLocalEmulatorAndroidCollection.setCapability("resetKeyboard", true);
            //     logger.info("Launching android_local_emulator");
            //     driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capLocalEmulatorAndroidCollection);
            //     break;
            // case "chromeLatest.aws":
            //     String myProjectARN = "arn:aws:devicefarm:us-west-2:090759491118:testgrid-project:b206dd52-eee1-4080-a24e-19e6f086ed78";
            //     DeviceFarmClient client = DeviceFarmClient.builder().region(Region.AWS_GLOBAL).build();
            //     CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
            //             .expiresInSeconds(300)
            //             .projectArn(myProjectARN)
            //             .build();
            //     CreateTestGridUrlResponse response = client.createTestGridUrl(request);
            //     URL testGridUrl = new URL(response.url());

            //     DesiredCapabilities capAWSChromeLatestCollection = new DesiredCapabilities();
            //     capAWSChromeLatestCollection.setCapability("browserName", "chrome");
            //     capAWSChromeLatestCollection.setCapability("browserVersion", "latest");
            //     capAWSChromeLatestCollection.setCapability("platform", "windows");

            //     driver = new RemoteWebDriver(testGridUrl, capAWSChromeLatestCollection);
            //     break;
            case "chromeLatest.lambdatest":
            case "pixel7viewport.appium.lambdatest":
                ChromeOptions chromeBrowserOptions = new ChromeOptions();
                chromeBrowserOptions.setPlatformName("Windows 10");
                chromeBrowserOptions.setBrowserVersion("latest");
                HashMap<String, Object> ltChromeOptions = new HashMap<>();
                ltChromeOptions.put("project", agent + ".lambdatest");
                ltChromeOptions.put("resolution", "2048x1536");
                // ltChromeOptions.put("timezone", "Dublin");
                ltChromeOptions.put("name", featureScenario);                
                ltChromeOptions.put("name", "rishabh");
                // ltChromeOptions.put("build", featureScenario);
                ltChromeOptions.put("w3c", true);
                ltChromeOptions.put("network", true);
                ltChromeOptions.put("visual", true);
                ltChromeOptions.put("video", true);
                ltChromeOptions.put("console", true);
                chromeBrowserOptions.setCapability("LT:Options", ltChromeOptions);
                try {
                    driver = new RemoteWebDriver(
                            new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
                            chromeBrowserOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace(System.out);
                }
                break;
            // case "firefoxLatest.lambdatest":
            //     FirefoxOptions firefoxBrowserOptions = new FirefoxOptions();
            //     firefoxBrowserOptions.setPlatformName("Windows 10");
            //     firefoxBrowserOptions.setBrowserVersion("103.0");
            //     HashMap<String, Object> ltFireFoxOptions = new HashMap<>();
            //     ltFireFoxOptions.put("build", featureScenario);
            //     ltFireFoxOptions.put("project", agent + ".lambdatest");
            //     ltFireFoxOptions.put("name", featureScenario);
            //     ltFireFoxOptions.put("w3c", true);
            //     ltFireFoxOptions.put("timezone", "Dublin");
            //     ltFireFoxOptions.put("network", true);
            //     ltFireFoxOptions.put("visual", true);
            //     ltFireFoxOptions.put("video", true);
            //     ltFireFoxOptions.put("console", true);
            //     firefoxBrowserOptions.setCapability("LT:Options", ltFireFoxOptions);
            //     try {
            //         driver = new RemoteWebDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
            //                 firefoxBrowserOptions);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "edgeLatest.lambdatest":
            //     EdgeOptions edgeBrowserOptions = new EdgeOptions();
            //     edgeBrowserOptions.setPlatformName("Windows 10");
            //     edgeBrowserOptions.setBrowserVersion("latest");
            //     HashMap<String, Object> ltEdgeOptions = new HashMap<>();
            //     ltEdgeOptions.put("build", featureScenario);
            //     ltEdgeOptions.put("project", agent + ".lambdatest");
            //     ltEdgeOptions.put("name", featureScenario);
            //     ltEdgeOptions.put("timezone", "Dublin");
            //     ltEdgeOptions.put("w3c", true);
            //     ltEdgeOptions.put("network", true);
            //     ltEdgeOptions.put("visual", true);
            //     ltEdgeOptions.put("video", true);
            //     ltEdgeOptions.put("console", true);
            //     edgeBrowserOptions.setCapability("LT:Options", ltEdgeOptions);
            //     try {
            //         driver = new RemoteWebDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
            //                 edgeBrowserOptions);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "chromeWindows11.lambdatest":
            //     ChromeOptions chromeWindows11Options = new ChromeOptions();
            //     chromeWindows11Options.setPlatformName("Windows 11");
            //     chromeWindows11Options.setBrowserVersion("107.0");
            //     HashMap<String, Object> ltWin11Options = new HashMap<>();
            //     ltWin11Options.put("project", agent + ".lambdatest");
            //     ltWin11Options.put("resolution", "2048x1536");
            //     ltWin11Options.put("timezone", "Dublin");
            //     ltWin11Options.put("name", featureScenario);
            //     ltWin11Options.put("build", featureScenario);
            //     ltWin11Options.put("w3c", true);
            //     ltWin11Options.put("network", true);
            //     ltWin11Options.put("visual", true);
            //     ltWin11Options.put("video", true);
            //     ltWin11Options.put("console", true);
            //     chromeWindows11Options.setCapability("LT:Options", ltWin11Options);
            //     try {
            //         driver = new RemoteWebDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
            //                 chromeWindows11Options);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "macSafariVentura.lambdatest":
            // case "iPhone14viewport.appium.lambdatest":
            //     SafariOptions browserSafariVenturaOptions = new SafariOptions();
            //     browserSafariVenturaOptions.setPlatformName("MacOS Ventura");
            //     browserSafariVenturaOptions.setBrowserVersion("16.0");
            //     HashMap<String, Object> ltSafariVenturaOptions = new HashMap<>();
            //     ltSafariVenturaOptions.put("project", agent + ".lambdatest");
            //     ltSafariVenturaOptions.put("visual", true);
            //     ltSafariVenturaOptions.put("video", true);
            //     ltSafariVenturaOptions.put("resolution", "1920x1080");
            //     ltSafariVenturaOptions.put("timezone", "Dublin");
            //     ltSafariVenturaOptions.put("build", featureScenario);
            //     ltSafariVenturaOptions.put("name", featureScenario);
            //     ltSafariVenturaOptions.put("w3c", true);
            //     ltSafariVenturaOptions.put("selenium_version", "4.1.2");
            //     browserSafariVenturaOptions.setCapability("LT:Options", ltSafariVenturaOptions);
            //     try {
            //         driver = new RemoteWebDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
            //                 browserSafariVenturaOptions);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "macSafariSonoma.lambdatest":
            //     SafariOptions browserSafariSonomaOptions = new SafariOptions();
            //     browserSafariSonomaOptions.setPlatformName("MacOS Sonoma");
            //     browserSafariSonomaOptions.setBrowserVersion("17.0");
            //     HashMap<String, Object> ltSafariSonomaOptions = new HashMap<>();
            //     ltSafariSonomaOptions.put("project", agent + ".lambdatest");
            //     ltSafariSonomaOptions.put("visual", true);
            //     ltSafariSonomaOptions.put("video", true);
            //     ltSafariSonomaOptions.put("resolution", "1920x1080");
            //     ltSafariSonomaOptions.put("timezone", "Dublin");
            //     ltSafariSonomaOptions.put("build", featureScenario);
            //     ltSafariSonomaOptions.put("name", featureScenario);
            //     ltSafariSonomaOptions.put("w3c", true);
            //     ltSafariSonomaOptions.put("selenium_version", "4.1.2");
            //     browserSafariSonomaOptions.setCapability("LT:Options", ltSafariSonomaOptions);
            //     try {
            //         driver = new RemoteWebDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
            //                 browserSafariSonomaOptions);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "galaxyS10os9.appium.lambdatest":
            //     DesiredCapabilities galaxyS10os9capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltGalaxyS10os9Options = new HashMap<>();
            //     ltGalaxyS10os9Options.put("w3c", true);
            //     ltGalaxyS10os9Options.put("name", featureScenario);
            //     ltGalaxyS10os9Options.put("build", featureScenario);
            //     ltGalaxyS10os9Options.put("project", agent + ".lambdatest");
            //     ltGalaxyS10os9Options.put("platformName", "android");
            //     ltGalaxyS10os9Options.put("deviceName", "Galaxy S10");
            //     ltGalaxyS10os9Options.put("platformVersion", "9");
            //     ltGalaxyS10os9Options.put("isRealMobile", true);
            //     galaxyS10os9capabilities.setCapability("lt:options", ltGalaxyS10os9Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 galaxyS10os9capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "galaxyS20os10.appium.lambdatest":
            //     DesiredCapabilities galaxyS20os10capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltGalaxyS20os10Options = new HashMap<>();
            //     ltGalaxyS20os10Options.put("w3c", true);
            //     ltGalaxyS20os10Options.put("name", featureScenario);
            //     ltGalaxyS20os10Options.put("build", featureScenario);
            //     ltGalaxyS20os10Options.put("project", agent + ".lambdatest");
            //     ltGalaxyS20os10Options.put("platformName", "android");
            //     ltGalaxyS20os10Options.put("deviceName", "Galaxy S20");
            //     ltGalaxyS20os10Options.put("platformVersion", "10");
            //     ltGalaxyS20os10Options.put("isRealMobile", true);
            //     galaxyS20os10capabilities.setCapability("lt:options", ltGalaxyS20os10Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 galaxyS20os10capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "galaxyS21os11.appium.lambdatest":
            //     DesiredCapabilities galaxyS21os11capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltGalaxyS21os11Options = new HashMap<>();
            //     ltGalaxyS21os11Options.put("w3c", true);
            //     ltGalaxyS21os11Options.put("name", featureScenario);
            //     ltGalaxyS21os11Options.put("build", featureScenario);
            //     ltGalaxyS21os11Options.put("project", agent + ".lambdatest");
            //     ltGalaxyS21os11Options.put("platformName", "android");
            //     ltGalaxyS21os11Options.put("deviceName", "Galaxy S21");
            //     ltGalaxyS21os11Options.put("platformVersion", "11");
            //     ltGalaxyS21os11Options.put("isRealMobile", true);
            //     galaxyS21os11capabilities.setCapability("lt:options", ltGalaxyS21os11Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 galaxyS21os11capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "pixel4os11.appium.lambdatest":
            //     DesiredCapabilities pixel4os11capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltPixel4os11Options = new HashMap<>();
            //     ltPixel4os11Options.put("w3c", true);
            //     ltPixel4os11Options.put("name", featureScenario);
            //     ltPixel4os11Options.put("build", featureScenario);
            //     ltPixel4os11Options.put("project", agent + ".lambdatest");
            //     ltPixel4os11Options.put("platformName", "android");
            //     ltPixel4os11Options.put("deviceName", "Pixel 4");
            //     ltPixel4os11Options.put("platformVersion", "11");
            //     ltPixel4os11Options.put("isRealMobile", true);
            //     ltPixel4os11Options.put("resetKeyboard", true);
            //     pixel4os11capabilities.setCapability("lt:options", ltPixel4os11Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 pixel4os11capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "pixel6os12.appium.lambdatest":
            //     DesiredCapabilities pixel6os12capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltpixel6os12Options = new HashMap<>();
            //     ltpixel6os12Options.put("w3c", true);
            //     ltpixel6os12Options.put("name", featureScenario);
            //     ltpixel6os12Options.put("build", featureScenario);
            //     ltpixel6os12Options.put("project", agent + ".lambdatest");
            //     ltpixel6os12Options.put("platformName", "android");
            //     ltpixel6os12Options.put("deviceName", "Pixel 6");
            //     ltpixel6os12Options.put("platformVersion", "12");
            //     ltpixel6os12Options.put("isRealMobile", true);
            //     ltpixel6os12Options.put("resetKeyboard", true);
            //     pixel6os12capabilities.setCapability("lt:options", ltpixel6os12Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 pixel6os12capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "huaweiP30os10.appium.lambdatest":
            //     DesiredCapabilities huaweiP30os10capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltHuaweiP30os10Options = new HashMap<>();
            //     ltHuaweiP30os10Options.put("w3c", true);
            //     ltHuaweiP30os10Options.put("name", featureScenario);
            //     ltHuaweiP30os10Options.put("build", featureScenario);
            //     ltHuaweiP30os10Options.put("project", agent + ".lambdatest");
            //     ltHuaweiP30os10Options.put("platformName", "android");
            //     ltHuaweiP30os10Options.put("deviceName", "Huawei P30");
            //     ltHuaweiP30os10Options.put("platformVersion", "10");
            //     ltHuaweiP30os10Options.put("isRealMobile", true);
            //     huaweiP30os10capabilities.setCapability("lt:options", ltHuaweiP30os10Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 huaweiP30os10capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "galaxyTabS8os12.appium.lambdatest":
            //     DesiredCapabilities galaxyTabS8os12capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltGalaxyTabS8os12Options = new HashMap<>();
            //     ltGalaxyTabS8os12Options.put("w3c", true);
            //     ltGalaxyTabS8os12Options.put("name", featureScenario);
            //     ltGalaxyTabS8os12Options.put("build", featureScenario);
            //     ltGalaxyTabS8os12Options.put("project", agent + ".lambdatest");
            //     ltGalaxyTabS8os12Options.put("platformName", "android");
            //     ltGalaxyTabS8os12Options.put("deviceName", "Galaxy Tab S8");
            //     ltGalaxyTabS8os12Options.put("platformVersion", "12");
            //     ltGalaxyTabS8os12Options.put("isRealMobile", true);
            //     galaxyTabS8os12capabilities.setCapability("lt:options", ltGalaxyTabS8os12Options);
            //     try {
            //         driver = new AndroidDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 galaxyTabS8os12capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "iphoneXRos12.appium.lambdatest":
            //     DesiredCapabilities iphoneXRos12capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltIphoneXRos12Options = new HashMap<>();
            //     ltIphoneXRos12Options.put("w3c", true);
            //     ltIphoneXRos12Options.put("name", featureScenario);
            //     ltIphoneXRos12Options.put("build", featureScenario);
            //     ltIphoneXRos12Options.put("project", agent + ".lambdatest");
            //     ltIphoneXRos12Options.put("platformName", "ios");
            //     ltIphoneXRos12Options.put("deviceName", "iPhone XR");
            //     ltIphoneXRos12Options.put("platformVersion", "15");
            //     ltIphoneXRos12Options.put("isRealMobile", true);
            //     iphoneXRos12capabilities.setCapability("lt:options", ltIphoneXRos12Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 iphoneXRos12capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "iphoneXSos15.appium.lambdatest":
            //     DesiredCapabilities iphoneXSos15capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltIphoneXSos15Options = new HashMap<>();
            //     ltIphoneXSos15Options.put("w3c", true);
            //     ltIphoneXSos15Options.put("name", featureScenario);
            //     ltIphoneXSos15Options.put("build", featureScenario);
            //     ltIphoneXSos15Options.put("project", agent + ".lambdatest");
            //     ltIphoneXSos15Options.put("platformName", "ios");
            //     ltIphoneXSos15Options.put("deviceName", "iPhone XS");
            //     ltIphoneXSos15Options.put("platformVersion", "15");
            //     ltIphoneXSos15Options.put("isRealMobile", true);
            //     iphoneXSos15capabilities.setCapability("lt:options", ltIphoneXSos15Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 iphoneXSos15capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "iphone7os12.appium.lambdatest":
            //     DesiredCapabilities iphone7os12capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltIphone7os12Options = new HashMap<>();
            //     ltIphone7os12Options.put("w3c", true);
            //     ltIphone7os12Options.put("name", featureScenario);
            //     ltIphone7os12Options.put("build", featureScenario);
            //     ltIphone7os12Options.put("project", agent + ".lambdatest");
            //     ltIphone7os12Options.put("platformName", "ios");
            //     ltIphone7os12Options.put("deviceName", "iPhone 7");
            //     ltIphone7os12Options.put("platformVersion", "12");
            //     ltIphone7os12Options.put("isRealMobile", true);
            //     iphone7os12capabilities.setCapability("lt:options", ltIphone7os12Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 iphone7os12capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "iphone8os13.appium.lambdatest":
            //     DesiredCapabilities iphone8os13capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltIphone8os13Options = new HashMap<>();
            //     ltIphone8os13Options.put("w3c", true);
            //     ltIphone8os13Options.put("name", featureScenario);
            //     ltIphone8os13Options.put("build", featureScenario);
            //     ltIphone8os13Options.put("project", agent + ".lambdatest");
            //     ltIphone8os13Options.put("platformName", "ios");
            //     ltIphone8os13Options.put("deviceName", "iPhone 8");
            //     ltIphone8os13Options.put("platformVersion", "13");
            //     ltIphone8os13Options.put("isRealMobile", true);
            //     iphone8os13capabilities.setCapability("lt:options", ltIphone8os13Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 iphone8os13capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "iphone8os15.appium.lambdatest":
            //     DesiredCapabilities iphone8os15capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltIphone8os15Options = new HashMap<>();
            //     ltIphone8os15Options.put("w3c", true);
            //     ltIphone8os15Options.put("name", featureScenario);
            //     ltIphone8os15Options.put("build", featureScenario);
            //     ltIphone8os15Options.put("project", agent + ".lambdatest");
            //     ltIphone8os15Options.put("platformName", "ios");
            //     ltIphone8os15Options.put("deviceName", "iPhone 8");
            //     ltIphone8os15Options.put("platformVersion", "15");
            //     ltIphone8os15Options.put("isRealMobile", true);
            //     iphone8os15capabilities.setCapability("lt:options", ltIphone8os15Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 iphone8os15capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "iphone11os14.appium.lambdatest":
            //     DesiredCapabilities iphone11os14capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltIphone11os14Options = new HashMap<>();
            //     ltIphone11os14Options.put("w3c", true);
            //     ltIphone11os14Options.put("name", featureScenario);
            //     ltIphone11os14Options.put("build", featureScenario);
            //     ltIphone11os14Options.put("project", agent + ".lambdatest");
            //     ltIphone11os14Options.put("platformName", "ios");
            //     ltIphone11os14Options.put("deviceName", "iPhone 11");
            //     ltIphone11os14Options.put("platformVersion", "14");
            //     ltIphone11os14Options.put("isRealMobile", true);
            //     iphone11os14capabilities.setCapability("lt:options", ltIphone11os14Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 iphone11os14capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "ipadAiros13.appium.lambdatest":
            //     DesiredCapabilities ipadAiros13capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltipadAiros13Options = new HashMap<>();
            //     ltipadAiros13Options.put("w3c", true);
            //     ltipadAiros13Options.put("name", featureScenario);
            //     ltipadAiros13Options.put("build", featureScenario);
            //     ltipadAiros13Options.put("platformName", "ios");
            //     ltipadAiros13Options.put("deviceName", "iPad Air (2019)");
            //     ltipadAiros13Options.put("platformVersion", "13");
            //     ltipadAiros13Options.put("isRealMobile", true);
            //     ipadAiros13capabilities.setCapability("lt:options", ltipadAiros13Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 ipadAiros13capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "ipadAiros15.appium.lambdatest":
            //     DesiredCapabilities ipadAiros15capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> ltipadAiros15Options = new HashMap<>();
            //     ltipadAiros15Options.put("w3c", true);
            //     ltipadAiros15Options.put("name", featureScenario);
            //     ltipadAiros15Options.put("build", featureScenario);
            //     ltipadAiros15Options.put("platformName", "ios");
            //     ltipadAiros15Options.put("deviceName", "iPad Air (2022)");
            //     ltipadAiros15Options.put("platformVersion", "15");
            //     ltipadAiros15Options.put("isRealMobile", true);
            //     ipadAiros15capabilities.setCapability("lt:options", ltipadAiros15Options);
            //     try {
            //         driver = new IOSDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 ipadAiros15capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            // case "samsungGalaxyTab8os12.appium.lambdatest":
            //     DesiredCapabilities samsungTabS8capabilities = new DesiredCapabilities();
            //     HashMap<String, Object> samsungTabS8Options = new HashMap<String, Object>();
            //     samsungTabS8Options.put("w3c", true);
            //     samsungTabS8Options.put("platformName", "android");
            //     samsungTabS8Options.put("deviceName", "Galaxy Tab S8");
            //     samsungTabS8Options.put("platformVersion", "12");
            //     samsungTabS8Options.put("visual", true);
            //     samsungTabS8Options.put("network", true);
            //     samsungTabS8Options.put("video", true);
            //     samsungTabS8Options.put("build", featureScenario);
            //     samsungTabS8Options.put("name", featureScenario);
            //     samsungTabS8Options.put("project", "samsungGalaxyTab8os12.appium.lambdatest");
            //     samsungTabS8Options.put("deviceOrientation", "portrait");
            //     samsungTabS8Options.put("isRealMobile", true);
            //     samsungTabS8Options.put("console", true);
            //     samsungTabS8capabilities.setCapability("lt:options", samsungTabS8Options);
            //     try {
            //         driver = new AppiumDriver(
            //                 new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"),
            //                 samsungTabS8capabilities);
            //     } catch (MalformedURLException e) {
            //         e.printStackTrace(System.out);
            //     }
            //     break;
            default:
                throw new NotFoundException("For some reason there is no case for launchDevice!");
        }
    }

    public void tearDownTest(Scenario scenario) {
        // get test result
        String resultLogOutput = (getScenarioName(scenario) + " - " + scenario.getStatus().toString().toUpperCase()
                + "!");
        // only run this if webDriver session was successfully initiated
        if (driver != null) {
            try {
                if (scenario.getStatus() == Status.PASSED) {
                    // output test result as info as this is PASSED status.
                    logger.info(resultLogOutput);
                    if (agent.contains("lambda")) {
                        logger.info("Sending PASS to Lambda");
                        driver.executeScript("lambda-status=" + "passed");
                    }
                } else {
                    // output result as warning (more appropriate than ERROR as test could be
                    // pending, pass on retry...)
                    logger.warn(resultLogOutput);
                    // take screenshot of failure and embed in scenario
                    logger.info("Test failed, taking screenshot");
                    takeScreenshot(scenario);
                    if (agent.contains("lambda")) {
                        logger.info("Sending FAIL to Lambda");
                        driver.executeScript("lambda-status=" + "failed");
                    }
                }
                // close browser and quit webdriver
                logger.info("Quitting browser");
                driver.quit();
                logger.info("Test end");
            } catch (Exception e) {
                logger.error("Something went wrong tearing down test [{}] in [{}]", scenario.getName(),
                        scenario.getId().split("/")[scenario.getId().split("/").length - 1], e);
            }
        }
    }

    public String getScenarioName(Scenario scenario) {
        // get the full scenario name from getUri() as this includes the feature name -
        // want to include this so cannot user getName()
        String[] uriComponents = scenario.getUri().toString().split("/");
        String fullScenarioName = uriComponents[uriComponents.length - 1].replace(".feature", "") + "-"
                + scenario.getName();

        // need to strip out any uuids that may have been added from CucumberStudio
        // integration
        String[] scenarioComponents = fullScenarioName.split("\\(");
        return scenarioComponents[0].trim();
    }

    private ChromeOptions setChromeOptions() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("profile.default_content_setting_values.notifications", 1);
        String folder = SeleniumUtilities.getDateTimeFormat("yyyyMMDD-HHmmssSSS-")
                + RandomStringUtils.randomAlphanumeric(10);
        if (agent.equals("lambdatest")) {
            String downloadFilepath = "C:\\Users\\you\\Downloads\\" + folder;
            chromePrefs.put("download.default_directory", downloadFilepath);
        } else {
            String downloadFilepath = buildDir + "/" + folder;
            String systemFilePath = downloadFilepath.replace("/", File.separator);
            chromePrefs.put("download.default_directory", systemFilePath);
            logger.info("Files will be downloaded to " + systemFilePath);
            localBrowserDownloadDir = systemFilePath;
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // fixes problem with Chrome 111
        if (headlessBrowser) {
            options.setHeadless(true);

            // Need to specify the window size in headless mode
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            options.addArguments("--window-size=" + width + "," + height);
        }

        if (ignoreInvalidCerts) {
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        }

        // Standardise this behaviour (chromedriver accepts by default but lambdatest
        // doesn't?)
        options.addArguments("--disable-backgrounding-occluded-windows"); // fix for Chrome 87 becoming unfocused
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-renderer-backgrounding");

        options.setExperimentalOption("prefs", chromePrefs);
        return options;
    }

    private void takeScreenshot(Scenario scenario) throws Exception {
        // set screenshot filepath
        String screenshot = WebDriverManager.buildDir + "/screenshots/failShots/" + getScenarioName(scenario) + ".png";

        // determine if alert is present
        SeleniumUtilities seleniumUtilities = new SeleniumUtilities(this);

        if (seleniumUtilities.isAlertPresent()) {
            BufferedImage bufferedImage = new Robot()
                    .createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();

            // embed screenshot in cucumber report
            scenario.attach(imageInByte, "image/png", "Screenshot");

            // add screenshot to build directory
            File screenShot = new File(screenshot);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(screenShot);
                fileOutputStream.write(imageInByte);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        } else {
            // embed screenshot in cucumber report
            scenario.attach(driver.getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");

            // add screenshot to build directory
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(screenshot));
        }
    }
}
