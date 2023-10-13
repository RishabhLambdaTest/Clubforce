package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.AccountPage;
import com.clubforce.pages.LoginPage;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class RefundSteps extends WebDriverManager{
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public RefundSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.accountPage = driverManager.accountPage;
        this.backofficePage = driverManager.backofficePage;
        this.lottoPage = driverManager.lottoPage;
        this.loginPage = driverManager.loginPage;
        this.clubPage = driverManager.clubPage;
        this.productsPage = driverManager.productsPage;
        this.myAccountPage = driverManager.myAccountPage;
    }

    public static String currencySymbol;

    @And("ClubAdmin issues refund for {string}")
    public void issueRefund(String product){
        logger.info("Check for issue refund button");
        backofficePage.findOnPage("//button[contains(.,'reply issue refund')]");

        switch (product){
            case "lotto ticket":
                logger.info("Click on the issue refund button");
                backofficePage.click("//button[contains(.,'reply issue refund')]");

                logger.info("Verify that the refund request pop up is displayed");
                backofficePage.findOnPage("//h2[contains(.,'Refund request')]");
                backofficePage.findOnPage("//p[contains(.,'You are requesting a refund for: ')]");
                backofficePage.findOnPage("//strong[contains(.,'| Playslip ID: "+ProductPurchaseSteps.PLAYSLIP_ID+".')]");
                backofficePage.findOnPage("//p[contains(.,'Refund process may take up to 10 working days.')]");
                backofficePage.findOnPage("//strong[contains(.,'Refund reason')]");
                backofficePage.findOnPage("(//span[contains(.,'Requested by member')])[2]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//button[contains(.,'cancel')]");
                backofficePage.findOnPage("//button[contains(.,'send refund request')]");
                logger.info("Click the send request button");
                backofficePage.click("//button[contains(.,'send refund request')]");
                backofficePage.waitFiveSeconds();
                break;
            case "membership plan":
                String registrationID = backofficePage.getElementAttribute("//span[@data-test='order-details-membership-card.itemMembershipRegistrationId'][contains(.,'')]","textContent");
                logger.info("Registration ID: " + registrationID + ".");
//                String nameOfPlan = backofficePage.getElementAttribute("//strong[@data-test='order-details-membership-card.itemHeading'][contains(.,'')]", "textContent");
//                String planName = nameOfPlan.substring(1, nameOfPlan.length()-1);
//                logger.info("Plan name: " + planName + ".");

                logger.info("Click on the issue refund button");
                backofficePage.click("//button[contains(.,'reply issue refund')]");

                logger.info("Verify that the refund request pop up is displayed");
                backofficePage.findOnPage("//h2[contains(.,'Refund request')]");
                backofficePage.findOnPage("//p[contains(.,'You are requesting a refund for:')]");
                backofficePage.findOnPage("//p[contains(.,'| Registration ID: "+registrationID+".')]");
                backofficePage.findOnPage("//p[contains(.,'Refund process may take up to 10 working days.')]");
                backofficePage.click("(//div[contains(.,'Refund option')])[6]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Full')]");
                backofficePage.findOnPage("//strong[contains(.,'Refund reason')]");
                backofficePage.findOnPage("(//span[contains(.,'Requested by member')])[2]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//button[contains(.,'cancel')]");
                backofficePage.findOnPage("//button[contains(.,'send refund request')]");
                logger.info("Click the send request button");
                backofficePage.click("//button[contains(.,'send refund request')]");
                backofficePage.waitForElementDisplayedByXpathWithTimeout("//h5[contains(.,'check_circle Refund requested')]",30);
                backofficePage.findOnPage("//p[contains(.,'Do you also want to cancel this membership?')]");
                backofficePage.findOnPage("//button[contains(.,'cancel membership')]");
                backofficePage.click("(//button[contains(.,'cancel')])[1]");
                backofficePage.waitForSkeletonLoader();
                break;
            default:
                throw new NotFoundException("For some reason there is no case issueRefund");
        }

    }

    @And("ClubAdmin cannot issue refund for {string} {string} due to insufficient funds")
    public void attemptRefundWithInsufficientFunds(String country, String product){
        logger.info("Check for issue refund button");
        backofficePage.findOnPage("//button[contains(.,'reply issue refund')]");

        switch (product){
            case "lotto ticket":
                logger.info("Click on the issue refund button");
                backofficePage.click("//button[contains(.,'reply issue refund')]");

                logger.info("Verify that the refund request pop up is displayed");
                backofficePage.findOnPage("//h2[contains(.,'Refund request')]");
                backofficePage.findOnPage("//p[contains(.,'You are requesting a refund for: ')]");
                backofficePage.findOnPage("//strong[contains(.,'| Playslip ID: "+ProductPurchaseSteps.PLAYSLIP_ID+".')]");
                backofficePage.findOnPage("//p[contains(.,'Refund process may take up to 10 working days.')]");
                backofficePage.findOnPage("//strong[contains(.,'Refund reason')]");
//                backofficePage.findOnPage("(//span[contains(.,'Requested by member')])[2]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//button[contains(.,'cancel')]");

                if (country.equals("Ireland")){
                    assertTrue(backofficePage.isElementDisplayed("(//div[contains(.,'€Refund amount')])[6]"));
                }

                if (country.equals("Scotland")){
                    assertTrue(backofficePage.isElementDisplayed("(//div[contains(.,'£Refund amount')])[6]"));
                }

                logger.info("Verify that warning message is displayed");
                backofficePage.findOnPage("//h5[contains(.,'Insufficient funds')]");
                backofficePage.findOnPage("//p[contains(.,'Account does not have a sufficient balance to issue the refund.')]");

                logger.info("Verify that send refund request button is disabled");
                assertThat("Send refund request button is NOT set to Inactive!", backofficePage.getElementAttribute("//button[contains(.,'send refund request')]", "disabled"), containsString("true"));

                break;
            case "membership plan":
                String registrationID = backofficePage.getElementAttribute("//span[@data-test='order-details-membership-card.itemMembershipRegistrationId-0'][contains(.,'')]","textContent");
                logger.info("Registration ID: " + registrationID + ".");
                String nameOfPlan = backofficePage.getElementAttribute("//strong[@data-test='order-details-membership-card.itemHeading-0'][contains(.,'')]", "textContent");
                String planName = nameOfPlan.substring(1, nameOfPlan.length()-1);
                logger.info("Plan name: " + planName + ".");

                logger.info("Click on the issue refund button");
                backofficePage.click("//button[contains(.,'reply issue refund')]");

                logger.info("Verify that the refund request pop up is displayed");
                backofficePage.findOnPage("//h2[contains(.,'Refund request')]");
                backofficePage.findOnPage("//p[contains(.,'You are requesting a refund for: "+planName+" | Registration ID: "+registrationID+".')]");
                backofficePage.findOnPage("//p[contains(.,'Refund process may take up to 10 working days.')]");
                backofficePage.findOnPage("//strong[contains(.,'Refund reason')]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//button[contains(.,'cancel')]");

                if (country.equals("Ireland")){
                    assertTrue(backofficePage.isElementDisplayed("(//div[contains(.,'€Refund amount')])[6]"));
                }

                if (country.equals("Wales")){
                    assertTrue(backofficePage.isElementDisplayed("(//div[contains(.,'£Refund amount')])[6]"));
                }

                logger.info("Verify that warning message is displayed");
                backofficePage.findOnPage("//h5[contains(.,'Insufficient funds')]");
                backofficePage.findOnPage("//p[contains(.,'Account does not have a sufficient balance to issue the refund.')]");

                logger.info("Verify that send refund request button is disabled");
                assertThat("Send refund request button is NOT set to Inactive!!", backofficePage.getElementAttribute("//button[contains(.,'send refund request')]", "disabled"), containsString("true"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case attemptRefundWithInsufficientFunds");
        }
    }

    @And("refund {string} is reflected on order details page")
    public void checkRefundIsReflected(String status){
        switch (status){
            case "pendingToSuccess":
                backofficePage.findOnPage("//span[contains(.,'Your request was successfully sent. A notification email has been sent to the customer and the club admin.')]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//span[contains(.,'Refund status:')]");
                backofficePage.findOnPage("//p[contains(.,'Pending')]");
                backofficePage.findOnPage("//p[@class='badge bg-warning my-auto p-2 pill same-width ng-star-inserted'][contains(.,'Pending')]");

                logger.info("Check that the issue refund button is disabled");
                backofficePage.waitTwoSeconds();
                assertThat("Issue refund button is NOT set to Inactive!", backofficePage.getElementAttribute("//button[contains(.,'reply issue refund')]",
                                "disabled"), containsString("true"));

                logger.info("Verify that page elements remain");
                assertTrue(backofficePage.isElementDisplayed("//h1[contains(.,'Order details')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Summary')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Payment method')]"));
//                assertTrue(backofficePage.isElementDisplayed("//div[@class='text-black-50'][contains(.,'* * * *  * * * *  * * * *  7726')]"));
                assertTrue(backofficePage.isElementDisplayed("//span[contains(.,'Expiry Date:')]"));
                assertTrue(backofficePage.isElementDisplayed("//p[contains(.,'This payment method cannot be deleted')]"));

                logger.info("Checking for complete status");
                backofficePage.refreshPage();
                backofficePage.waitFiveSeconds();

                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//span[contains(.,'Refund status:')]");
                backofficePage.findOnPage("(//p[contains(.,'Complete')])[2]");

                logger.info("Check that the issue refund button is disabled");
                backofficePage.waitTwoSeconds();
                assertThat("Issue refund button is NOT set to Inactive!", backofficePage.getElementAttribute("//button[contains(.,'reply issue refund')]",
                        "disabled"), containsString("true"));

                logger.info("Verify that page elements remain");
                assertTrue(backofficePage.isElementDisplayed("//h1[contains(.,'Order details')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Summary')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Payment method')]"));
//                assertTrue(backofficePage.isElementDisplayed("//div[@class='text-black-50'][contains(.,'* * * *  * * * *  * * * *  7726')]"));
                assertTrue(backofficePage.isElementDisplayed("//span[contains(.,'Expiry Date:')]"));
                assertTrue(backofficePage.isElementDisplayed("//p[contains(.,'This payment method cannot be deleted')]"));
                break;
            case "pendingToFailed":
                backofficePage.findOnPage("//span[contains(.,'Your request was successfully sent. A notification email has been sent to the customer and the club admin.')]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//p[contains(.,'Pending')]");
                backofficePage.findOnPage("//p[@class='badge bg-warning my-auto p-2 pill same-width ng-star-inserted'][contains(.,'Pending')]");

                logger.info("Check that the issue refund button is disabled");
                backofficePage.waitTwoSeconds();
                assertThat("Issue refund button is NOT set to Inactive!", backofficePage.getElementAttribute("//button[contains(.,'reply issue refund')]",
                        "disabled"), containsString("true"));

                logger.info("Verify that page elements remain");
                assertTrue(backofficePage.isElementDisplayed("//h1[contains(.,'Order details')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Summary')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Payment method')]"));
                assertTrue(backofficePage.isElementDisplayed("//div[@class='text-black-50'][contains(.,'* * * *  * * * *  * * * *  5126')]"));
                assertTrue(backofficePage.isElementDisplayed("//span[contains(.,'Expiry Date:')]"));
                assertTrue(backofficePage.isElementDisplayed("//p[contains(.,'This payment method cannot be deleted')]"));
                //Need to check how long a refund takes to go from pending to failed
                //https://clubforce.atlassian.net/browse/LOT-411
                break;
            case "immediateSuccess":
                backofficePage.findOnPage("//span[contains(.,'Your request was successfully sent. A notification email has been sent to the customer and the club admin.')]");
                backofficePage.findOnPage("//mat-icon[contains(.,'info')]");
                backofficePage.findOnPage("//p[@class='ps-2'][contains(.,'Refund status:  Complete')]");
                backofficePage.findOnPage("//p[@class='badge bg-warning my-auto p-2 pill same-width ng-star-inserted'][contains(.,'Complete')]");

                logger.info("Check that the issue refund button is disabled");
                backofficePage.waitTwoSeconds();
                assertThat("Issue refund button is NOT set to Inactive!", backofficePage.getElementAttribute("//button[contains(.,'reply issue refund')]",
                        "disabled"), containsString("true"));

                logger.info("Verify that page elements remain");
                assertTrue(backofficePage.isElementDisplayed("//h1[contains(.,'Order details')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Summary')]"));
                assertTrue(backofficePage.isElementDisplayed("//h2[contains(.,'Payment method')]"));
                assertTrue(backofficePage.isElementDisplayed("//div[@class='text-black-50'][contains(.,'* * * *  * * * *  * * * *  1111')]"));
                assertTrue(backofficePage.isElementDisplayed("//span[contains(.,'Expiry Date:')]"));
                assertTrue(backofficePage.isElementDisplayed("//p[contains(.,'This payment method cannot be deleted')]"));
                //Need to check how long a refund takes to go from pending to failed
                //https://clubforce.atlassian.net/browse/LOT-411
                break;
            default:
                throw new NotFoundException("For some reason there is no case checkRefundIsReflected");
        }
    }

    @And("{string} refund is reflected on orders page")
    public void checkRefundedBadgeOnOrdersPage(String product){
        backofficePage.waitFiveSeconds();
        backofficePage.click("//button[contains(.,'arrow_back back')]");
        backofficePage.findOnPage("//h1[contains(.,'Orders')]");
        switch (product){
            case "Lotto ticket":
                backofficePage.sendKeys("//input[contains(@filtername,'member')]", ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER.substring(0, 2));
                backofficePage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER+"')])[1]");
                backofficePage.click("//h1[contains(.,'Orders')]");
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Refunded')]");
                backofficePage.waitThreeSeconds();
//                assertTrue(backofficePage.isElementDisplayed("(//span[contains(.,'Lotto Tickets × 1')])[1]"));
                assertTrue(backofficePage.isElementDisplayed("(//span[contains(.,'Refunded')])[1]"));
                break;
            case "Membership plan":
                backofficePage.waitFiveSeconds();
                backofficePage.click("(//span[contains(.,'All')])[2]");
                backofficePage.click("//span[@class='mat-option-text'][contains(.,'Refunded')]");
                backofficePage.waitThreeSeconds();
                assertTrue(backofficePage.isElementDisplayed("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Membership plans × 1')])[1]"));
                assertTrue(backofficePage.isElementDisplayed("(//span[contains(.,'Refunded')])[2]"));
                break;
            default:
        }


    }

    @And("{string} refund email is sent to {string} for {string}")
    public void checkRefundRequestEmailIsDisplayedForUser(String product, String user, String status){
        switch (user){
            case "account holder":

                logger.info("Accessing mail-trap");
                loginPage.accessMailTrapInbox();

                if (product.equals("Lotto")){
                    logger.info("Looking for lotto refund request mail for account holder");
                    backofficePage.waitFiveSeconds();
                    backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", ProductPurchaseSteps.USERMAILHOLDER);
                    backofficePage.waitTwoSeconds();
                    if(status.equals("pendingToSuccess")){
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+ProductPurchaseSteps.USERMAILHOLDER+"')])[1]");
                    }else{
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+ProductPurchaseSteps.USERMAILHOLDER+"')])[2]");
                    }
                    backofficePage.waitThreeSeconds();
                    driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                    backofficePage.waitFiveSeconds();
                    assertTrue(backofficePage.isElementPresent("//a[contains(.,'Refund request')]"));
                    assertTrue(backofficePage.isElementPresent(String.format(LoginPage.LoginPageText, ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER)));
                    assertTrue(backofficePage.isElementPresent(String.format(LoginPage.LoginPageText, "Refund details")));
                    assertTrue(backofficePage.isElementPresent("(//div[@class='lotto-circle small square'])[1]"));
                    assertTrue(backofficePage.isElementPresent("(//div[@class='lotto-circle small square'])[2]"));
                    assertTrue(backofficePage.isElementPresent("(//div[@class='lotto-circle small square'])[3]"));
                    assertTrue(backofficePage.isElementDisplayed("//a[@target='_blank'][contains(.,'View my orders')]"));
                    assertTrue(backofficePage.isElementPresent("//p[contains(.,'If you have any questions or feedback, please contact the club')]"));
                    backofficePage.scrollPageDown();
                    backofficePage.click("//a[@target='_blank'][contains(.,'View my orders')]");
                    backofficePage.waitFiveSeconds();
                    loginPage.switchToBrowserTab(1);
                    assertTrue(driverManager.driver.getCurrentUrl().contains("https://myaccount.test.clubforce.io"));
                    backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                    driverManager.driver.close();
                    loginPage.switchToBrowserTab(0);
                }

                if (product.equals("Membership")){
                    logger.info("Looking for membership refund request mail for account holder");
                    backofficePage.waitFiveSeconds();
                    backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", AccountPage.MEMRandomMailHolder);
                    backofficePage.waitTwoSeconds();

                    if(status.equals("pendingToSuccess")){
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+AccountPage.MEMRandomMailHolder+"')])[1]");
                    }

                    if (status.equals("pendingToFailed")){
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+AccountPage.MEMRandomMailHolder+"')])[2]");
                    }

                    backofficePage. waitThreeSeconds();
                    driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                    backofficePage.waitFiveSeconds();
                    assertTrue(backofficePage.isElementPresent("//a[contains(.,'Refund request')]"));
                    assertTrue(backofficePage.isElementPresent(String.format(LoginPage.LoginPageText, "Refund details")));
                    assertTrue(backofficePage.isElementPresent("//a[@target='_blank'][contains(.,'View my orders')]"));
                    assertTrue(backofficePage.isElementPresent("//p[contains(.,'If you have any questions or feedback, please contact the club')]"));
                    backofficePage.click("//a[@target='_blank'][contains(.,'View my orders')]");
                    backofficePage.waitFiveSeconds();
                    loginPage.switchToBrowserTab(1);
                    assertTrue(driverManager.driver.getCurrentUrl().contains("https://myaccount.test.clubforce.io"));
                    backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                    driverManager.driver.close();
                    loginPage.switchToBrowserTab(0);
                }
                break;
            case "ClubAdmin":
                logger.info("Going back to mail-trap");
                backofficePage.findOnPage("//input[contains(@name,'quick_filter')]");

                if (product.equals("Lotto")){
                    logger.info("Looking for lotto refund request mail for club admin");
                    backofficePage.waitFiveSeconds();
                    backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", lottoClubAdminUsername);
                    backofficePage.waitTwoSeconds();

                    if(status.equals("pendingToSuccess")){
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+lottoClubAdminUsername+"')])[1]");
                    }else{
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+lottoClubAdminUsername+"')])[2]");
                    }

                    backofficePage. waitThreeSeconds();
                    driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                    backofficePage.waitFiveSeconds();
                    assertTrue(backofficePage.isElementPresent("//a[contains(.,'Refund request')]"));
                    assertTrue(backofficePage.isElementPresent(String.format(LoginPage.LoginPageText, "Refund details")));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Amount refunded')])[4]"));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Order ID')])[4]"));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Date & Time')])[4]"));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Name')])[4]"));
                    assertFalse(backofficePage.isElementPresent("(//div[@class='lotto-circle small square'])[1]"));
                    assertFalse(backofficePage.isElementPresent("(//div[@class='lotto-circle small square'])[2]"));
                    assertFalse(backofficePage.isElementPresent("(//div[@class='lotto-circle small square'])[3]"));
                    assertTrue(backofficePage.isElementPresent("//a[contains(.,'View order details')]"));
                    assertTrue(backofficePage.isElementPresent("//h3[contains(.,'There is no action required on your behalf, this email is purely to inform you of your refund.')]"));
                    backofficePage.click("//a[contains(.,'View order details')]");
                    backofficePage.waitFiveSeconds();
                    loginPage.switchToBrowserTab(1);
                    assertTrue(driverManager.driver.getCurrentUrl().contains("https://backoffice.test.clubforce.io"));
                    backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                    driverManager.driver.close();
                    loginPage.switchToBrowserTab(0);
                }

                if (product.equals("Membership")){
                    logger.info("Looking for lotto refund request mail for club admin");
                    backofficePage.waitFiveSeconds();
                    backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", membershipClubAdminUsername);
                    backofficePage.waitTwoSeconds();

                    if(status.equals("pendingToSuccess")){
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+membershipClubAdminUsername+"')])[1]");
                    }else{
                        backofficePage.click("(//span[@class='to_email'][contains(.,'"+membershipClubAdminUsername+"')])[2]");
                    }
                    backofficePage. waitThreeSeconds();

                    driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                    backofficePage.waitFiveSeconds();
                    assertTrue(backofficePage.isElementPresent("//a[contains(.,'Refund request')]"));
                    assertTrue(backofficePage.isElementPresent(String.format(LoginPage.LoginPageText, "Refund details")));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Amount refunded')])[4]"));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Order ID')])[4]"));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Date & Time')])[4]"));
                    assertTrue(backofficePage.isElementPresent("(//td[contains(.,'Name')])[4]"));
                    assertTrue(backofficePage.isElementPresent("//a[contains(.,'View order details')]"));
                    assertTrue(backofficePage.isElementPresent("//h3[contains(.,'There is no action required on your behalf, this email is purely to inform you of your refund.')]"));
                    backofficePage.click("//a[contains(.,'View order details')]");
                    backofficePage.waitFiveSeconds();
                    loginPage.switchToBrowserTab(1);
                    assertTrue(driverManager.driver.getCurrentUrl().contains("https://backoffice.test.clubforce.io"));
                    backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                    driverManager.driver.close();
                    loginPage.switchToBrowserTab(0);
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case checkRefundRequestEmailIsDisplayedForUser!");
        }
    }

    @And("{string} failed refund email is sent to {string} for {string}")
    public void checkFailedRefundRequestEmailIsDisplayedForUser(String product, String user, String status){
       if(status.equals("pendingToFailed")){
           switch(user){
               case "account holder":
                   logger.info("Go back to mailtrap to check for Account holder failed refund email");
                   if(product.equals("Lotto")){
                       backofficePage.waitFiveSeconds();
                       backofficePage.clear("//input[contains(@name,'quick_filter')]");
                       backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", ProductPurchaseSteps.USERMAILHOLDER);
                       backofficePage.waitTwoSeconds();
                       backofficePage.click("(//span[@class='to_email'][contains(.,'"+ProductPurchaseSteps.USERMAILHOLDER+"')])[1]");
                       backofficePage. waitThreeSeconds();
                       driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                       backofficePage.waitFiveSeconds();
                       backofficePage.findOnPage("//*[contains(text(),'Refund request failed')]");
                       backofficePage.click("//*[contains(text(),'View details')]");
                       backofficePage. waitThreeSeconds();
                       loginPage.switchToBrowserTab(1);
                       assertTrue(driverManager.driver.getCurrentUrl().contains("https://myaccount.test.clubforce.io"));
                       backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                       driverManager.driver.close();
                       loginPage.switchToBrowserTab(0);
                   }

                   if(product.equals("Membership")){
                       backofficePage.waitFiveSeconds();
                       backofficePage.clear("//input[contains(@name,'quick_filter')]");
                       backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", AccountPage.MEMRandomMailHolder);
                       backofficePage.waitTwoSeconds();
                       backofficePage.click("(//span[@class='to_email'][contains(.,'"+AccountPage.MEMRandomMailHolder+"')])[1]");
                       backofficePage. waitThreeSeconds();
                       driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                       backofficePage.waitFiveSeconds();
                       backofficePage.findOnPage("//*[contains(text(),'Refund request failed')]");
                       backofficePage.click("//*[contains(text(),'View details')]");
                       backofficePage. waitThreeSeconds();
                       loginPage.switchToBrowserTab(1);
                       assertTrue(driverManager.driver.getCurrentUrl().contains("https://myaccount.test.clubforce.io"));
                       backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                       driverManager.driver.close();
                       loginPage.switchToBrowserTab(0);
                   }
                   break;
               case "ClubAdmin":
                   logger.info("Go back to mailtrap to check for Club Admin failed refund email");

                   if(product.equals("Lotto")){
                       backofficePage.waitFiveSeconds();
                       backofficePage.clear("//input[contains(@name,'quick_filter')]");
                       backofficePage.sendKeys("//input[contains(@name,'quick_filter')]", lottoClubAdminUsername);
                       backofficePage.waitTwoSeconds();
                       backofficePage.click("(//span[@class='to_email'][contains(.,'"+lottoClubAdminUsername+"')])[1]");
                       backofficePage. waitThreeSeconds();
                       driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                       backofficePage.waitFiveSeconds();
                       backofficePage.findOnPage("//*[contains(text(),'Refund request failed')]");
                       backofficePage.click("//*[contains(text(),'View details')]");
                       backofficePage. waitThreeSeconds();
                       loginPage.switchToBrowserTab(1);
                       assertTrue(driverManager.driver.getCurrentUrl().contains("https://backoffice.test.clubforce.io"));
                       backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                       driverManager.driver.close();
                       loginPage.switchToBrowserTab(0);
                   }

                   if(product.equals("Membership")){

                       backofficePage.waitFiveSeconds();
                       backofficePage.clear("//input[contains(@name,'quick_filter')]");
                       backofficePage.sendKeys("//input[contains(@name,'quick_filter')]",membershipClubAdminUsername);
                       backofficePage.waitTwoSeconds();
                       backofficePage.click("(//span[@class='to_email'][contains(.,'"+membershipClubAdminUsername+"')])[1]");
                       backofficePage. waitThreeSeconds();
                       driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("//iframe[contains(@title,'Message view')]")));
                       backofficePage.waitFiveSeconds();
                       backofficePage.findOnPage("//*[contains(text(),'Refund request failed')]");
                       backofficePage.click("//*[contains(text(),'View details')]");
                       backofficePage. waitThreeSeconds();
                       loginPage.switchToBrowserTab(1);
                       assertTrue(driverManager.driver.getCurrentUrl().contains("https://backoffice.test.clubforce.io"));
                       backofficePage.findOnPage("//h2[contains(.,'Sign in')]");
                       driverManager.driver.close();
                       loginPage.switchToBrowserTab(0);
                   }
                   break;
               case "Mary and Carole":
                   logger.info("Go back to mailtrap to check that email is sent to Mary and Carole at ClubForce");
                   backofficePage.click("(//span[@class='to_email'][contains(.,'mary@clubforce.com, Carole@clubforce.com')])[1]");
                   break;
               default:
           }
       }else{
           logger.info("Check that no failed refund email is sent");
       }

    }

    @And("refunded order is displayed in MyAccount on orders page")
    public void checkRefundedOnOrderPageInMyAccount(){
        backofficePage.waitOneSecond();
        logger.info("Going to Orders page");
        backofficePage.click("//span[@class='ms-2'][contains(.,'Orders')]");
        backofficePage.waitThreeSeconds();
        backofficePage.findOnPage("(//strong[contains(.,'Summary')])[1]");
        logger.info("Orders table is now displayed");
        assertTrue(backofficePage.isElementDisplayed("(//span[contains(.,'Refunded')])[2]"));
        logger.info("Refunded badge is displayed for order in the table on the order page");
    }

    @And("refunded order is displayed in MyAccount on orders details page for {string}")
    public void checkRefundedOnOrderDetailsPageInMyAccount(String status){
        backofficePage.click("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])[1]");
        backofficePage.findOnPage("//h2[contains(.,'Summary')]");
        switch (status){
            case "pendingToSuccess":
                logger.info("Summary section is displayed");
                assertTrue(backofficePage.isElementDisplayed("//p[@class='badge bg-success my-auto p-2 pill same-width ng-star-inserted'][contains(.,'Complete')]"));
                assertTrue(backofficePage.isElementDisplayed("//mat-icon[@role='img'][contains(.,'info')]"));
                break;
            case "pendingToFailed":
                assertTrue(backofficePage.isElementDisplayed("//p[@class='badge bg-danger my-auto p-2 pill same-width'][contains(.,'Failed')]"));
                assertTrue(backofficePage.isElementDisplayed("//mat-icon[@role='img'][contains(.,'info')]"));
                break;
            default:
        }
    }

    @And("Membership plan is displayed as refunded on Membership page in MyAccount")
    public void checkRefundedIsDisplayedOnMembershipPlanCard(){
        backofficePage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Membership')]", 10);
        backofficePage.waitForElementDisplayedByXpathWithTimeout("//div[@data-test='membership-tile.status'][contains(.,'Active')]", 10);
        //note I haven't cancelled plan only refunded it
        assertTrue(backofficePage.isElementDisplayed("//p[contains(.,'Additional')]"));
        assertTrue(backofficePage.isElementDisplayed("//span[contains(.,'Refunded')]"));
        logger.info("Membership plan is displayed as refunded on membership page");
    }

    @And("Lotto ticket is displayed as refunded in history section on lotto page in MyAccount")
    public void checkPlayslipIsRefundedInHistorySection(){
        backofficePage.waitFiveSeconds();
        logger.info("Checking that the refunded ticket is displayed in the history section");
        backofficePage.findOnPage("//h2[contains(.,'History')]");
        backofficePage.findOnPage("(//header[@class='mat-body-2 text-muted'][contains(.,'Playslip ID:')])[1]");
        backofficePage.findOnPage("(//header[@class='mat-body-2 text-muted'][contains(.,'Purchased:')])[1]");
        backofficePage.findOnPage("(//header[@class='mat-body-2 text-muted'][contains(.,'Status:')])[1]");
        backofficePage.findOnPage("(//header[contains(.,'Additional:')])[1]");
        backofficePage.findOnPage("(//button[contains(.,'See Details')])[1]");
        assertTrue(backofficePage.isElementDisplayed("(//span[@class='mat-body-2 ng-star-inserted'][contains(.,'Refunded')])[1]"));

        logger.info("Clicking see details to see more information for the refunded history ticket");
        backofficePage.click("(//button[contains(.,'See Details')])[1]");
        backofficePage.findOnPage("//button[contains(.,'See order details')]");
        assertTrue(backofficePage.isElementDisplayed("//span[@class='badge bg-primary bg-opacity-75 p-2 mb-1 ng-star-inserted'][contains(.,'Refunded')]"));
    }

}
