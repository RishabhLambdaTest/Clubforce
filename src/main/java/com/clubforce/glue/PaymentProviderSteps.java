package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.BackofficePage;
import com.clubforce.pages.PaymentProviderPage;
import com.clubforce.pages.SectionsAndPagesPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class PaymentProviderSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public PaymentProviderSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.paymentProviderPage = driverManager.paymentProviderPage;
        this.backofficePage = driverManager.backofficePage;

    }


    @Then("PaymentProvider option is {string}")
    public void ppIsVisible(String display_option) {
        backofficePage.refreshPage();
        backofficePage.waitFiveSeconds();
        backofficePage.openBackOfficeLeftSideNav();
        // paymentProviderPage.findOnPage(BackofficePage.LeftNavContactMessagesText);
        paymentProviderPage.waitTwoSeconds();
        paymentProviderPage.click(BackofficePage.BackofficeLeftNavSettings);
        switch (display_option) {
            case "not visible":
                assertFalse(paymentProviderPage.isElementPresent(PaymentProviderPage.PaymentProviderLeftNav));
                break;
            case "visible":
                paymentProviderPage.findOnPage(PaymentProviderPage.PaymentProviderLeftNav);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for ppIsVisible!");
        }
    }

    @And("Stripe account is set up for {string} club")
    public void stripeSetup(String country) {
        Lorem lorem = LoremIpsum.getInstance();

        logger.info("Starting Stripe setup");
        backofficePage.openBackOfficeLeftSideNav();
        paymentProviderPage.click(BackofficePage.BackofficeLeftNavSettings);
        paymentProviderPage.waitTwoSeconds();
        paymentProviderPage.click(PaymentProviderPage.PaymentProviderLeftNav);
        paymentProviderPage.findOnPage(PaymentProviderPage.PaymentProviderHeading);

        logger.info("Clicking Stripe setup with time check");
        StopWatch loginTime = new StopWatch();
        loginTime.start();
        paymentProviderPage.click(PaymentProviderPage.StripeSetUpButton);
        paymentProviderPage.waitFifteenSeconds();
        paymentProviderPage.waitForElementDisplayedByXpathWithTimeout(PaymentProviderPage.StripeTestModeText, 120);
        paymentProviderPage.findOnPage(PaymentProviderPage.TestModeText);
        loginTime.stop();
        long pageLoadTime_ms = loginTime.getTime();
        logger.info("Time to Stipe page loaded: " + pageLoadTime_ms + " milliseconds");

        logger.info("We are on Stripe domain");
        paymentProviderPage.click("(//span[contains(.,'Continue')])[4]");
        paymentProviderPage.waitFiveSeconds();
        paymentProviderPage.findOnPage("(//span[contains(.,'Verification summary')])[2]");
        paymentProviderPage.findOnPage("(//span[contains(.,'Professional details')])[3]");
        paymentProviderPage.findOnPage("(//span[contains(.,'Missing required business information')])[3]");
        paymentProviderPage.click("//*[contains(text(),'Edit')]");

        logger.info("Add our test site");
        if (SuperUserSteps.ClubNameHOLDER != null) {
            paymentProviderPage.sendKeys("//input[contains(@type,'url')]", "https://" + SuperUserSteps.ClubNameHOLDER + "." + envName + ".clubforce.io");
        } else {
            paymentProviderPage.sendKeys("//input[contains(@type,'url')]", "https://" + lorem.getFirstNameMale() + ".com.test");
        }
        paymentProviderPage.click("//*[contains(text(),'Save')]");
        logger.info("Add our test site, done");

        paymentProviderPage.waitThirtySeconds();

        switch (country) {
            case "Ireland":
                logger.info("Add personal and address details for "+country);
                paymentProviderPage.findOnPage("(//span[contains(.,'Professional details')])[3]");
                paymentProviderPage.click("//*[contains(text(),'Update')]");
//        paymentProviderPage.findOnPage("(//span[contains(.,'Account representative')])[4]");
                paymentProviderPage.sendKeys("//input[contains(@id,'first_name')]", lorem.getFirstName());
                paymentProviderPage.sendKeys("//input[contains(@id,'last_name')]", lorem.getLastName());
                paymentProviderPage.sendKeys("//input[contains(@name,'email')]", "qa+" + RandomStringUtils.randomAlphabetic(7).toLowerCase() + "@clubforce.com");

                if (paymentProviderPage.isElementDisplayed("//input[contains(@placeholder,'DD')]")) {
                    paymentProviderPage.sendKeys("//input[contains(@placeholder,'DD')]", "22");
                    paymentProviderPage.sendKeys("//input[contains(@placeholder,'MM')]", "11");
                    paymentProviderPage.sendKeys("//input[contains(@placeholder,'YYYY')]", "1988");
                } else {
                    paymentProviderPage.click("//select[contains(@id,'dob-day')]");
                    paymentProviderPage.click("//*[contains(text(),'22')]");
                    paymentProviderPage.click("//select[contains(@id,'dob-month')]");
                    paymentProviderPage.click("//*[contains(text(),'March')]");
                    paymentProviderPage.click("//select[contains(@id,'dob-year')]");
                    paymentProviderPage.click("//*[contains(text(),'1990')]");
                }

                paymentProviderPage.sendKeys("//input[contains(@autocomplete,'address-line1')]", lorem.getWords(2, 3));
                paymentProviderPage.sendKeys("//input[contains(@autocomplete,'address-line2')]", lorem.getWords(2, 3));
                paymentProviderPage.sendKeys("//input[contains(@placeholder,'City')]", lorem.getCity());
                paymentProviderPage.click("//select[contains(@aria-label,'County')]");
                paymentProviderPage.click("//*[contains(text(),'Co. Galway')]");
                paymentProviderPage.sendKeys("//input[@autocomplete='postal-code']", lorem.getZipCode());
                paymentProviderPage.sendKeys("//input[contains(@id,'phone')]", "87" + RandomStringUtils.randomNumeric(7));
                paymentProviderPage.click("//*[contains(text(),'Submit')]");
                logger.info("Add personal and address details for "+country+", done");

                break;
            case "Northern":
            case "Scotland":
            case "Wales":
            case "England":
                logger.info("Add personal and address details for "+country);
                paymentProviderPage.findOnPage("(//span[contains(.,'Professional details')])[3]");
                paymentProviderPage.click("//*[contains(text(),'Update')]");
//        paymentProviderPage.findOnPage("(//span[contains(.,'Account representative')])[4]");
                paymentProviderPage.sendKeys("//input[contains(@id,'first_name')]", lorem.getFirstName());
                paymentProviderPage.sendKeys("//input[contains(@id,'last_name')]", lorem.getLastName());
                paymentProviderPage.sendKeys("//input[contains(@name,'email')]", lorem.getFirstNameMale() + "@clubforce.com");

                if (paymentProviderPage.isElementDisplayed("//input[contains(@placeholder,'DD')]")) {
                    paymentProviderPage.sendKeys("//input[contains(@placeholder,'DD')]", "22");
                    paymentProviderPage.sendKeys("//input[contains(@placeholder,'MM')]", "11");
                    paymentProviderPage.sendKeys("//input[contains(@placeholder,'YYYY')]", "1988");
                } else {
                    paymentProviderPage.click("//select[contains(@id,'dob-day')]");
                    paymentProviderPage.click("//*[contains(text(),'22')]");
                    paymentProviderPage.click("//select[contains(@id,'dob-month')]");
                    paymentProviderPage.click("//*[contains(text(),'March')]");
                    paymentProviderPage.click("//select[contains(@id,'dob-year')]");
                    paymentProviderPage.click("//*[contains(text(),'1990')]");
                }
                paymentProviderPage.sendKeys("//input[contains(@autocomplete,'address-line1')]", lorem.getWords(2, 3));
                paymentProviderPage.sendKeys("//input[contains(@autocomplete,'address-line2')]", lorem.getWords(2, 3));
                paymentProviderPage.sendKeys("//input[contains(@placeholder,'City')]", lorem.getCity());
                paymentProviderPage.sendKeys("//input[@autocomplete='postal-code']", lorem.getZipCode());
                paymentProviderPage.sendKeys("//input[contains(@id,'phone')]", "87" + RandomStringUtils.randomNumeric(7));
                paymentProviderPage.click("//*[contains(text(),'Submit')]");
                logger.info("Add personal and address details for "+country+", done");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for stripeSetup!");
        }

        paymentProviderPage.findOnPage("//*[contains(text(),'Verification summary')]");
        paymentProviderPage.waitThirtySeconds();

        if (paymentProviderPage.isElementPresent("//button[contains(.,'Update')]")) {
            logger.info("Stripe is already ready for ID");
            paymentProviderPage.click("//button[contains(.,'Update')]");
            paymentProviderPage.findOnPage("(//span[contains(.,'Use test document')])[4]");
            paymentProviderPage.waitTwoSeconds();
            paymentProviderPage.click("(//span[contains(.,'Use test document')])[4]");
        } else {
            logger.info("Stripe is not yet ready for ID, wait a while");
            paymentProviderPage.waitThirtySeconds();
            paymentProviderPage.scrollPageDown();
            paymentProviderPage.waitThirtySeconds();
            paymentProviderPage.scrollPageUp();
            paymentProviderPage.waitThirtySeconds();
            paymentProviderPage.scrollPageDown();
            paymentProviderPage.waitThirtySeconds();
            paymentProviderPage.click("(//div[contains(@aria-label,'Edit')])[2]");
            paymentProviderPage.waitTwoSeconds();
            paymentProviderPage.findOnPage("//input[contains(@id,'first_name')]");
            paymentProviderPage.click("//button[contains(.,'Submit')]");
            paymentProviderPage.waitTwoSeconds();
            paymentProviderPage.click("//button[contains(.,'Update')]");
            paymentProviderPage.waitTwoSeconds();
            paymentProviderPage.click("(//span[contains(.,'Use test document')])[4]");
        }
        paymentProviderPage.waitFiveSeconds();
        paymentProviderPage.findOnPage("(//span[contains(.,'Clubforce partners with Stripe for secure financial services.')])[2]");
        assertFalse(paymentProviderPage.isElementPresent("(//span[contains(.,'Use test document')])[4]"));
        assertFalse(paymentProviderPage.isElementPresent("//span[contains(.,'Select how to verify your ID')]"));
        logger.info("Personal, business, and ID details are given to Stripe");

        logger.info("We will leave Stripe after having left all needed info, and return to CF payment provider page");
        paymentProviderPage.click("//button[contains(.,'submit')]");
        paymentProviderPage.waitThirtySeconds();
        paymentProviderPage.findOnPage(PaymentProviderPage.PaymentProviderHeading);

        logger.info("Looking for 'Stripe is working' message");

        if (!paymentProviderPage.isElementPresent(PaymentProviderPage.StripeIsWorkingProperlyText)) {
            paymentProviderPage.waitThirtySeconds();
            paymentProviderPage.refreshPage();
            paymentProviderPage.findOnPage(SectionsAndPagesPage.AddButton);
        }

        if (paymentProviderPage.isElementPresent("//button[contains(.,'Return to Stripe')]")) {
            paymentProviderPage.click("//button[contains(.,'Return to Stripe')]");
            assertFalse(paymentProviderPage.isElementDisplayed("//*[contains(.,'Missing required information')]"));
            paymentProviderPage.refreshPage();
            assertTrue(paymentProviderPage.isElementDisplayed(PaymentProviderPage.StripeIsWorkingProperlyText));
        }
        backofficePage.findOnPage(PaymentProviderPage.StripeIsWorkingProperlyText);
    }

    @And("bank account is linked for {string}")
    public void bankAccountLinks(String entity) {
        logger.info("Link bank account");
        Lorem lorem = LoremIpsum.getInstance();
        paymentProviderPage.findOnPage(PaymentProviderPage.StripeIsWorkingProperlyText);
        paymentProviderPage.click(SectionsAndPagesPage.AddButton);
        paymentProviderPage.sendKeys("//input[contains(@formcontrolname,'accountHolderName')]", lorem.getNameMale());
        paymentProviderPage.click("(//div[contains(.,'Account holder type')])[17]");
        paymentProviderPage.waitTwoSeconds();
        paymentProviderPage.click("//span[contains(.,'Individual')]");
        paymentProviderPage.waitFiveSeconds();

        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@name,'__privateStripeFrame')]")));
        paymentProviderPage.click("//input[contains(@name,'iban')]");
        paymentProviderPage.clearInputFieldUsingBackSpaceKey("//input[contains(@name,'iban')]");

        switch (entity) {
            case "Ireland Individual":
                paymentProviderPage.sendKeys("//input[contains(@name,'iban')]", "IE29AIBK93115212345678");
                paymentProviderPage.clearInputFieldUsingBackSpaceKey("//input[contains(@name,'iban')]");
                paymentProviderPage.sendKeys("//input[contains(@name,'iban')]", "IE29AIBK93115212345678");

                driverManager.driver.switchTo().parentFrame();
                paymentProviderPage.waitFiveSeconds();
                paymentProviderPage.click(PaymentProviderPage.StripeIsWorkingProperlyText);
                paymentProviderPage.click("//button[contains(.,'Link bank account')]");
                paymentProviderPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Your services')]",20);

                logger.info("Switch from default bank account to named bank account for Lotto");
                paymentProviderPage.click("(//span[contains(.,'Select bank account')])[1]");
                paymentProviderPage.click("//span[@class='mat-option-text'][contains(.,'STRIPE TEST BANK - 5678')]");
                paymentProviderPage.waitTwoSeconds();
                paymentProviderPage.click("//h2[contains(.,'Your services')]");
                paymentProviderPage.waitTwoSeconds();

                if(paymentProviderPage.isElementDisplayed("//span[contains(.,'Select bank account')]")){
                    logger.info("Checking if membership services is also available");
                    paymentProviderPage.click("//span[contains(.,'Select bank account')]");
                    paymentProviderPage.click("//span[@class='mat-option-text'][contains(.,'STRIPE TEST BANK - 5678')]");
                }

                paymentProviderPage.click("//button[@type='submit']");
                paymentProviderPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Product Bank Account updated.')]",5);
                paymentProviderPage.refreshPage();
                paymentProviderPage.waitFiveSeconds();
                assertTrue(paymentProviderPage.isElementDisplayed("//span[contains(.,'STRIPE TEST BANK - 5678')]"), "The new setting for default bank account isn't showing");
                break;
            case "NorthernIreland Individual" :
            case "Scotland Individual" :
            case "Wales Individual" :
            case "England Individual":
                paymentProviderPage.sendKeys("//input[contains(@name,'iban')]", "GB82WEST12345698765432");
                paymentProviderPage.clearInputFieldUsingBackSpaceKey("//input[contains(@name,'iban')]");
                paymentProviderPage.sendKeys("//input[contains(@name,'iban')]", "GB82WEST12345698765432");

                driverManager.driver.switchTo().parentFrame();
                paymentProviderPage.waitFiveSeconds();
                paymentProviderPage.click(PaymentProviderPage.StripeIsWorkingProperlyText);
                paymentProviderPage.click("//button[contains(.,'Link bank account')]");
                paymentProviderPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'Your services')]",20);

                logger.info("Switch from default bank account to named bank account for Lotto");
                paymentProviderPage.click("(//span[contains(.,'Select bank account')])[1]");
                paymentProviderPage.click("//span[@class='mat-option-text'][contains(.,'STRIPE TEST BANK - 5432')]");
                paymentProviderPage.waitTwoSeconds();
                paymentProviderPage.click("//h2[contains(.,'Your services')]");

                if(paymentProviderPage.isElementDisplayed("//span[contains(.,'Select bank account')]")){
                    logger.info("Checking if membership services is also available");
                    paymentProviderPage.click("//span[contains(.,'Select bank account')]");
                    paymentProviderPage.click("//span[@class='mat-option-text'][contains(.,'STRIPE TEST BANK - 5432')]");
                }

                paymentProviderPage.click("//button[@type='submit']");
                paymentProviderPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Product Bank Account updated.')]",5);
                paymentProviderPage.refreshPage();
                paymentProviderPage.waitFiveSeconds();
                assertTrue(paymentProviderPage.isElementDisplayed("//span[contains(.,'STRIPE TEST BANK - 5432')]"), "The new setting for default bank account isn't showing");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for bankAccountLinks!");
        }
    }

    @And("{string} is linked to the bank account")
    public void bankAccountLinksProduct(String entity) {
        backofficePage.openBackOfficeLeftSideNav();
        paymentProviderPage.click(PaymentProviderPage.PaymentProviderLeftNav);
        paymentProviderPage.waitFiveSeconds();
        paymentProviderPage.click("//h2[contains(.,'STRIPE TEST BANK')]");
        paymentProviderPage.scrollPageToBottom();
        switch (entity) {
            case "Lotto":
                paymentProviderPage.waitTwoSeconds();
                paymentProviderPage.click("//span[contains(.,'Default Bank Account')]");
                paymentProviderPage.waitTwoSeconds();
                paymentProviderPage.click("//span[@class='mat-option-text'][contains(.,'STRIPE TEST BANK')]");
                paymentProviderPage.waitTwoSeconds();
                paymentProviderPage.click("//span[contains(.,'Save')]");
                paymentProviderPage.waitTwoSeconds();
                break;
            case "Company":
                paymentProviderPage.findOnPage("xxxxxxxxxxxxxx");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for bankAccountLinks!");
        }
    }
}
