package com.clubforce.framework;

import com.clubforce.pages.ClubPage;
import com.clubforce.pages.LoginPage;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static com.clubforce.framework.WebDriverManager.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertFalse;


public class SeleniumUtilities {

    private final WebDriverManager driverManager;
    protected RemoteWebDriver driver;

    //logger
    private static final Logger logger = LogManager.getLogger();

    public SeleniumUtilities(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driverManager.driver;
    }

    public void findElementBelow(String reference, String target) {
        waitHalfSecond();
        try {
            driver.findElement(RelativeLocator.with(By.xpath(target)).below(By.xpath(reference)));
        } catch (Exception e) {
            logger.error("The element we looked for is not in expected location, or not displayed at all");
            throw new NotFoundException();
        }
    }

    public void findElementRightOf(String reference, String target) {
        waitHalfSecond();
        try {
            driver.findElement(RelativeLocator.with(By.xpath(target)).toRightOf(By.xpath(reference)));
        } catch (Exception e) {
            logger.error("The element we looked for is not in expected location, or not displayed at all");
            throw new NotFoundException();
        }
    }

    public void clickElementBelow(String reference, String target) {
        findOnPage(reference);
        centreElement(reference);
        waitTwoSeconds();
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).below(By.xpath(reference)));
        element.click();
    }

    public void clickElementRightOf(String reference, String target) {
        findOnPage(reference);
        centreElement(reference);
        waitHalfSecond();
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).toRightOf(By.xpath(reference)));
        element.click();
    }

    public void clickElementLeftOf(String reference, String target) {
        findOnPage(reference);
        centreElement(reference);
        waitHalfSecond();
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).toLeftOf(By.xpath(reference)));
        element.click();
    }

    public String getElementAttributeLeftOf(String reference, String target, String attribute) {
        findOnPage(reference);
        centreElement(reference);
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).toLeftOf(By.xpath(reference)));
        return element.getAttribute(attribute);
    }

    public String getElementAttributeRightOf(String reference, String target, String attribute) {
        findOnPage(reference);
        centreElement(reference);
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).toRightOf(By.xpath(reference)));
        return element.getAttribute(attribute);
    }

    public void sendKeysToElementToRightOf(String reference, String target, String text) {
        findOnPage(reference);
        centreElement(reference);
        waitOneSecond();
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).toRightOf(By.xpath(reference)));
        element.click();
        element.clear();
        waitHalfSecond();
        element.sendKeys(text);
        waitHalfSecond();
    }

    public void sendKeysToElementBelow(String reference, String target, String text) {
        findOnPage(reference);
        centreElement(reference);
        waitOneSecond();
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).below(By.xpath(reference)));
        element.click();
        element.clear();
        waitHalfSecond();
        element.sendKeys(text);
        waitHalfSecond();
    }

    public void sendKeysToElementAbove(String reference, String target, String text) {
        findOnPage(reference);
        centreElement(reference);
        waitOneSecond();
        WebElement element = driver.findElement(RelativeLocator.with(By.xpath(target)).above(By.xpath(reference)));
        element.click();
        element.clear();
        waitHalfSecond();
        element.sendKeys(text);
        waitHalfSecond();
    }

    public void goTo_URL(String url) {
        driver.navigate().to(url);
        setViewport();
        waitOneSecond();
    }

    public void goToClubURL(String url) {
        driver.navigate().to(envURL + url);
        setViewport();
        waitHalfSecond();
    }

    public void goToLottoClubURL() {
        driver.navigate().to(lottoClubURL);
        setViewport();
        waitHalfSecond();
    }

    public void goToMembershipClubURL(String url) {
        driver.navigate().to(membershipURL + url);
        setViewport();
        waitHalfSecond();
    }

    public void goToBackofficeURL(String url) {
        driver.navigate().to(backofficeURL + url);
        setViewport();
        waitOneSecond();
    }

    public void goToSuperUserURL(String url) {
        driver.navigate().to(superuserURL + url);
        setViewport();
        waitHalfSecond();
    }

    public void goToMyAccountURL(String url) {
        driver.navigate().to(myAccountURL + url);
        setViewport();
        waitHalfSecond();
    }

    public void goToXGB(String url) {
        logger.info("url is "+ xgbURL);
        driver.navigate().to(xgbURL + url);
        setViewport();
        waitHalfSecond();
    }

    public void goTo_Google() {
        driver.navigate().to("http://google.com");
        driver.manage().window().maximize();
        waitHalfSecond();
    }

    public void waitSplitSecond() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitHalfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitTwoSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitThreeSeconds() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitFiveSeconds() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitTenSeconds() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitFifteenSeconds() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void waitThirtySeconds() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public WebElement findOnPage(String elementLocator) {
        waitSplitSecond();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        waitSplitSecond();
        return wait.until(visibilityOfElementLocated(By.xpath(elementLocator)));
    }

    public void click(String elementLocator) {
        findOnPage(elementLocator);
        centreElement(elementLocator);
        WebElement webElement = findOnPage(elementLocator);
        webElement.click();
        waitOneSecond();
    }

    public void sendKeys(String elementLocator, String text) {
        findOnPage(elementLocator);
        centreElement(elementLocator);
        WebElement webElement = findOnPage(elementLocator);
        webElement.click();
        webElement.clear();
        webElement.sendKeys(text);
        waitHalfSecond();
    }

    public void sendKeysWithoutClear(String elementLocator, String text) {
        waitForElementDisplayedByXpathWithTimeout(elementLocator,10);
        centreElement(elementLocator);
        waitHalfSecond();
        WebElement webElement = findOnPage(elementLocator);
        webElement.click();
        waitHalfSecond();
        webElement.sendKeys(text);
        waitHalfSecond();
    }

    public void hoverOverElement(String path) {
        new Actions(driver).moveToElement(findOnPage(path)).perform();
    }

    public void clear(String elementLocator) {
        centreElement(elementLocator);
        waitHalfSecond();
        WebElement webElement = findOnPage(elementLocator);
        webElement.click();
        webElement.clear();
    }

    public WebElement waitForElementDisplayedByXpathWithTimeout(String elementLocator, long timeoutInSeconds) {
        waitSplitSecond();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        waitOneSecond();
        return wait.until(visibilityOfElementLocated(By.xpath(elementLocator)));
    }

    public WebElement waitForElementPresenceByClassName(String elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(11));
        return wait.until(presenceOfElementLocated(By.className(elementLocator)));
    }

    public void waitUntilElementInvisible(String xpathExpression, long timeOutInSeconds) {
        waitOneSecond();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathExpression)));
        waitOneSecond();
    }

    public void waitForSkeletonLoader() {
        if (isElementPresent("//span[@role='progressbar']")) {
            logger.info("Waiting for skeleton loader to finish");
            waitUntilElementInvisible("//span[@role='progressbar']", 60);
            logger.info("Skeleton loader finished");
            waitOneSecond();
        }
    }

    public boolean isElementPresent(String elementLocator) {
        try {
            driver.findElement(By.xpath(elementLocator));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementDisplayed(String elementLocator) {
        try {
            return driver.findElement(By.xpath(elementLocator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void deleteAllCookies() {
        driverManager.driver.manage().deleteAllCookies();
    }

    public String getElementAttribute(String elementLocator, String attribute) {
        WebElement element = findOnPage(elementLocator);
        return element.getAttribute(attribute);
    }

    public void clearInputFieldUsingBackSpaceKey(String elementLocator) {
        WebElement webElement = findOnPage(elementLocator);
        String currentText = webElement.getAttribute("value");
        click(elementLocator);
        for (int i = 0; i < currentText.length(); i++) {
            webElement.sendKeys(Keys.BACK_SPACE);
        }

        //do it again to make sure everything was cleared
        currentText = webElement.getAttribute("value");
        for (int i = 0; i < currentText.length(); i++) {
            webElement.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void twoBackSpacesTriggerDropdown(String elementLocator) {
        WebElement webElement = findOnPage(elementLocator);
        String currentText = webElement.getAttribute("value");
        webElement.click();
        webElement.sendKeys(Keys.BACK_SPACE);
        webElement.sendKeys(Keys.BACK_SPACE);
    }

    public void escape() {
        WebElement pageBody = findOnPage("//body");
        pageBody.sendKeys(Keys.ESCAPE);
        waitOneSecond();
    }

    public void pressEnter(String elementLocator) {
        WebElement webElement = findOnPage(elementLocator);
        webElement.sendKeys(Keys.RETURN);
        waitOneSecond();
    }

    public void centreElement(String elementLocator) {
        WebElement element = findOnPage(elementLocator);
//         OLD -----------------------------------------
        driver.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
//         OLD -----------------------------------------

        // NEW -----------------------------------------
//        int viewportHeight = driver.manage().window().getSize().height;  // Get the viewport's height.
//        int top = (int) (viewportHeight * 0.4); // Disregard the top of the viewport.
//        int bottom = (int) (viewportHeight * 0.6);   // Disregard the bottom of the viewport.
//        boolean isVisible = (boolean) driver.executeScript(
//                "return arguments[0].getBoundingClientRect().top <= " + bottom + " && arguments[0].getBoundingClientRect().bottom >= " + top, element);
//        if (!isVisible) {
//            driver.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
//            waitOneSecond();
//        }
        // NEW -----------------------------------------
        waitSplitSecond();
    }

    public void zoomOut() {
        driver.executeScript("document.body.style.zoom='60%'");
        waitTwoSeconds();
    }

    public void resetZoom() {
        driver.executeScript("document.body.style.zoom='100%'");
        waitTwoSeconds();
    }

    public static String getDateTimeFormat(String format) {
        SimpleDateFormat formDate = new SimpleDateFormat(format);
        formDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formDate.format(new Date());
    }

    public void scrollPageUp() {
        WebElement pageBody = findOnPage("//body");
        pageBody.sendKeys(Keys.PAGE_UP);
        waitTwoSeconds();
    }

    public void scrollPageDownSoft() {
        WebElement pageBody = findOnPage("//body");
        driver.executeScript("window.scrollBy(0,300)");
        waitTwoSeconds();
    }

    public void scrollPageDown() {
        WebElement pageBody = findOnPage("//body");
        driver.executeScript("window.scrollBy(0,630)");
        waitTwoSeconds();
    }

    public void scrollPageToTop() {
        driver.executeScript("window.scrollBy(0,-8000)");
        waitTwoSeconds();
    }

    public void scrollPageToBottom() {
        driver.executeScript("window.scrollBy(0,8000)");
        waitTwoSeconds();
    }

    public void uploadFileUsingJSExec(String fileName, String pageXPath) {
        if (driverManager.agent.contains("lambdatest")) {
            driverManager.driver.setFileDetector(new LocalFileDetector());
        }
        WebElement imageUpload= driverManager.driver.findElement(By.xpath(pageXPath));
        driver.executeScript("arguments[0].style.display='block';", imageUpload);

        String systemFilePath = WebDriverManager.baseDir + "/src/main/resources/files/".replace("/", File.separator);
        File uploadThisFile = new File(systemFilePath+fileName);
        imageUpload.sendKeys(uploadThisFile.getAbsolutePath());
        waitTwoSeconds();

        if (isElementPresent("//mat-icon[contains(.,'crop')]")) {
            click("//mat-icon[contains(.,'crop')]");
        }
    }

    public void refreshPage() {
        waitHalfSecond();
        driver.navigate().refresh();
        waitTwoSeconds();
    }

    public void setViewport() {
        if (!(driverManager.agent.contains("appium"))) {
            driverManager.driver.manage().window().maximize();
        }

        if (driverManager.agent.contains("viewport")) {
            logger.info("Setting viewport to phone portrait");
            Dimension screenPhone = new Dimension(400, 900);
            driverManager.driver.manage().window().setSize(screenPhone);
        }
    }

    public void isDownloadedInChrome(java.util.List<String> files, String text) {
        assertIsDownloadedInChrome(files, text);
    }

    private void assertIsDownloadedInChrome(java.util.List<String> files, String text) {
        driver.navigate().to("chrome://downloads/");
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertThat("Downloaded file not found!", bodyText.contains(text));
    }

    public void acceptCookies() {
        if (isElementDisplayed(LoginPage.CookieUnderstand)) {
            click(LoginPage.CookieUnderstand);
            waitUntilElementInvisible(LoginPage.CookieUnderstand,5);
            assertFalse(isElementDisplayed(LoginPage.CookieUnderstand));
        }
    }

    public void userClicksLoginButton() {
        click(LoginPage.SignInHeadingText);    /// defocus for mobile
        click(LoginPage.LoginButton);
        waitUntilElementInvisible(LoginPage.LoginButton, 10);
        waitForSkeletonLoader();
    }

    public void userClosesMobileMenuHeader() {
        String menuOpen = getElementAttribute("//div[contains(@id,'navbarNavAltMarkup')]", "offsetHeight");
        if (!menuOpen.contains("0")) {
            click(ClubPage.ClubNavBarIconMobile);
            waitOneSecond();
        }
    }

    public void clearLocalStorage() {
        driverManager.driver.executeScript("window.localStorage.clear();");
    }

    public void switchToBrowserTab(int tabIndex) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabIndex));
        waitHalfSecond();
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    public void takePageScreenshot(String name) throws IOException {
        File screenshotFile = ((TakesScreenshot)(driver)).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File("src/main/resources/screenshots/visCompare/"+name+".png"));
        if (!new File("src/main/resources/screenshots/visCompare/"+name+".png").exists()) throw new java.io.FileNotFoundException(name+".png not created!");
    }

    public void compareImages(String image1, String image2) throws Exception {
        BufferedImage img1 = ImageIO.read(new File(image1));
        BufferedImage img2 = ImageIO.read(new File(image2));
        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            throw new Exception("The images are not the same dimensions, failing the comparison. Are you running this on other than Chrome 2048x1536?");
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = img1.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = img2.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = (double) diff / (w1 * h1 * 3);
            double percentage = (avg / 255) * 100;
            logger.info("Image difference: " + percentage);
            if (percentage > 2) throw new Exception("Expected images to be exactly same but there is a deviance of: " + percentage);
        }
    }
}
