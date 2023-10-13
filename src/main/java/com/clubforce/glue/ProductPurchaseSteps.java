package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.*;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.Color;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class ProductPurchaseSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    public static Color paymentStatusColour;
    private final Color Payment_Status_Green = Color.fromString("rgba(25, 135, 84, 1)");
    public static String CARDNUMBERHOLDER;
    public static String MEMBERFIRSTNAMEHOLDER;
    public static String MEMBERLASTNAMEHOLDER;
    public static String ORDER_DATE_ID_HOLDER;
    public static String ORDER_ID_HOLDER;
    public static String USERMAILHOLDER;
    public static String PLAYSLIP_ID;
    public static String LINEOFPICKS;
    public static String LOTTOPICKS;
    public static boolean ISAUTORENEWAL;
    public static String LINE1;
    public static String LINE2;
    public static String LINE3;
    public static String ADDED_LOTTO_TICKET_OPTION;
    public static String ACTIVE_TICKET_OPTION_NAME;
    public static String ACTIVE_PLAYSLIP_ID;
    public static String ORDER_ID;
    public static String ACCOUNT_HOLDER_NAME;

    WebDriverManager driverManager;

    public ProductPurchaseSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.accountPage = driverManager.accountPage;
        this.backofficePage = driverManager.backofficePage;
        this.lottoPage = driverManager.lottoPage;
        this.loginPage = driverManager.loginPage;
        this.clubPage = driverManager.clubPage;
        this.productsPage = driverManager.productsPage;
        this.myAccountPage = driverManager.myAccountPage;
    }

    @Then("Checkout is {string} on page")
    public void homepageDisplayCheckout(String display_option) {
        switch (display_option) {
            case "not visible":
                productsPage.waitTwoSeconds();
                assertFalse(productsPage.isElementDisplayed(ProductsPage.ClubHomepageShoppingCartIcon));
                break;
            case "visible":
                productsPage.waitTwoSeconds();
                assertTrue(productsPage.isElementDisplayed("(//mat-icon[contains(.,'shopping_cart')])[2]"));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for homepageDisplayCheckout!");
        }
    }

    @Then("shopping cart is empty")
    public void shoppingCartIsEmpty() {
        lottoPage.findOnPage(ProductsPage.ShoppingCartContinueShoppingButton);
        assertFalse(lottoPage.isElementPresent(ProductsPage.ShoppingCartCheckoutButton));
        assertTrue(lottoPage.isElementDisplayed(ProductsPage.ShoppingCartEmptyMessage));
    }

    @Then("checkout page is empty")
    public void checkoutPageIsEmpty() {
        assertTrue(lottoPage.isElementPresent(String.format(ProductsPage.ProductsPageText, "Your cart is empty")));
        assertTrue(lottoPage.isElementPresent(String.format(ProductsPage.ProductsPageText, "Help text.")));
    }

    @Then("user opens shopping cart")
    public void userOpensShoppingCart() {
        if (driverManager.agent.contains("appium")) {   //if phone
            productsPage.click("//span[contains(@class,'navbar-toggler-icon')]");
            productsPage.waitTwoSeconds();
            lottoPage.click("//mat-icon[contains(.,'shopping_cart')]");
        } else {    //if desktop
            //lottoPage.click(ClubPage.ClubPageShoppingCart);
            lottoPage.click("(//mat-icon[contains(.,'shopping_cart')])[2]");
        }
    }

    @Then("cart has their {string} purchase")
    public void userHasProductInCart(String product) {
        if (driverManager.agent.contains("appium")) {   //if phone
            logger.info("On phone we ignore this step");
        } else {
            switch (product) {
                case "Lotto":
                    logger.info("Checking cart for Lotto");
                    lottoPage.findOnPage("//strong[contains(.,'Lotto tickets')]");
                    lottoPage.findOnPage("//mat-icon[contains(.,'delete')][1]");
                    lottoPage.findOnPage("//strong[contains(.,'"+LottoSteps.LottoTotalPriceHOLDER+" delete')]");
                    assertFalse(lottoPage.isElementDisplayed("//span[contains(.,'Auto renew selected')]"));
                    break;
                case "auto renew lotto":
                    logger.info("Checking cart for Lotto");
                    lottoPage.findOnPage("//strong[contains(.,'Lotto tickets')]");
                    lottoPage.findOnPage("//mat-icon[contains(.,'delete')][1]");
                    lottoPage.findOnPage("//strong[contains(.,'"+LottoSteps.LottoTotalPriceHOLDER+" delete')]");
                    lottoPage.findOnPage("//span[contains(.,'Auto renew selected')]");
                    break;
                case "Some other product":
                    logger.info("Checking cart for XXX");
                    break;
                default:
                    throw new NotFoundException("For some reason there is no case for userHasProductInCart!");
            }
        }
    }

    @Then("AH purchased lotto and membership products")
    public void purchaseProductsOnWebsite() {
        Lorem lorem = LoremIpsum.getInstance();
        logger.info("Filling in card details");
        productsPage.waitFiveSeconds();
        productsPage.click("//button[contains(.,'Checkout')]");
        productsPage.waitThirtySeconds();
        productsPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstName());
        productsPage.waitTwoSeconds();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        productsPage.waitFiveSeconds();
        productsPage.sendKeys(ProductsPage.CheckoutPageCardNumber, "4000000000000077");
        productsPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
        productsPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
        productsPage.waitFiveSeconds();
        driverManager.driver.switchTo().parentFrame();
        productsPage.click(ProductsPage.CheckoutPayNowButton);
        productsPage.waitTenSeconds();

        logger.info("Checking that confirmation page is displayed");
        productsPage.findOnPage("//h1[contains(.,'Order confirmed')]");
        productsPage.findOnPage("//p[contains(.,'Thank you for your order. We hope you had a great experience.')]");
        productsPage.waitTwoSeconds();

        lottoPage.waitTenSeconds();
        logger.info("Extracting order date, time and ID");
        ORDER_DATE_ID_HOLDER = lottoPage.getElementAttribute("//p[contains(.,'Order placed on ')]", "textContent");
        logger.info("ORDER_DATE_ID_HOLDER = "+ ORDER_DATE_ID_HOLDER);
        ORDER_ID = lottoPage.getElementAttribute("//p[contains(.,'ID ')]", "textContent");
        ORDER_ID_HOLDER = ORDER_ID.substring(3,9);
        logger.info("ORDER_ID_HOLDER = "+ ORDER_ID_HOLDER);

        logger.info("Check order summary on confirmation page");
        productsPage.findOnPage("//h2[contains(.,'Order summary')]");
        productsPage.findOnPage("//p[contains(.,'A copy of this message has been sent to your email')]");
    }

    @Then("{string} purchase {string} with {} card")
    public void userPurchasesProduct(String winner, String product, String card) {
        logger.info("Checking cart contents");

        logger.info("Cart content ok, moving to checkout");
        productsPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.ShoppingCartCheckoutButton, 10);
        productsPage.click(ProductsPage.ShoppingCartCheckoutButton);
        productsPage.refreshPage();
        productsPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.EnterEmailButton, 60);
        productsPage.findOnPage("//*[contains(text(),'Checkout')]");
        productsPage.findOnPage(ProductsPage.CheckoutPageTitle);
        productsPage.findOnPage("//h3[contains(.,'Lotto tickets')]");

        logger.info("Check picks are displayed on checkout page");
        lottoPage.findOnPage("//mat-icon[contains(.,'delete')]");
        LINEOFPICKS = lottoPage.getElementAttribute("//div[@class='text-muted'][contains(.,'3 lines')]", "textContent");
        logger.info(LINEOFPICKS);
        String lottoPicksRemoveLines = LINEOFPICKS.substring(9);
        if (lottoPicksRemoveLines.contains("Auto renew selected")) {
            String lottoPicksRemoveAutoRenew = lottoPicksRemoveLines.replaceAll("Auto renew selected","");
            String lottoPicksRemoveDraws = lottoPicksRemoveAutoRenew.replaceAll(" 1 draw","");
            LOTTOPICKS = lottoPicksRemoveDraws.substring(0,lottoPicksRemoveDraws.length()-1);
            logger.info(LOTTOPICKS);
        } else {
            String lottoPicksRemoveDraws = lottoPicksRemoveLines.replaceAll(" 1 draw","");
            LOTTOPICKS = lottoPicksRemoveDraws.substring(0,lottoPicksRemoveDraws.length()-1);
            logger.info(LOTTOPICKS);
        }

        logger.info("Verifying checkout page elements");
        productsPage.verifyCheckoutPageElements();

        logger.info("Signing in");
        if (winner.equals("existing winner")) {
            MEMBERFIRSTNAMEHOLDER = "Desmond";
            MEMBERLASTNAMEHOLDER = "Logan";
            USERMAILHOLDER = "membertest15@clubforce.com";
            loginPage.test4userLogInOnCheckoutPage();
            logger.info("Signed in Test15");
        }
        if (winner.equals("existing loser")) {
            MEMBERFIRSTNAMEHOLDER = "Iris";
            MEMBERLASTNAMEHOLDER = "Summer";
            USERMAILHOLDER = "membertest25@clubforce.com";
            loginPage.test13userLogInOnCheckoutPage();
            logger.info("Signed in Block me");
        }
        if (winner.equals("signup loser")) {
            productsPage.click(ProductsPage.CheckoutPageEnterEmailButton);
            accountPage.useCreateAccountModal("Lotto");
            logger.info("Created brand new account: "+AccountPage.RandomMailHOLDER+" with password b3deG2FnmrEy");
            MEMBERFIRSTNAMEHOLDER = AccountPage.randomFirstName;
            MEMBERLASTNAMEHOLDER = AccountPage.randomLastName;
            USERMAILHOLDER = AccountPage.LOTTORandomMailHolder;
        }

        productsPage.waitFiveSeconds(); // for login or create snackbar to disappear.

        logger.info("Assert Pay Now is OFF");
        assertThat("Add to Cart button is NOT set to Inactive!",
                productsPage.getElementAttribute("//button[@data-test='checkout.payNowButton'][contains(.,'Pay Now')]",
                        "disabled"), containsString("true"));

        logger.info("This lotto purchase is a " + product);
        switch (product) {
            case "Lotto without autoRenewal":

                logger.info("Assert auto renewal is OFF");
                assertTrue(productsPage.isElementPresent("//label[@class='mat-slide-toggle-label']//input[contains(@aria-checked,'false')]"));
                ISAUTORENEWAL = false;

                logger.info("About to click Pay now");
                if (envName.contains("prod")) {
                    logger.info("Environment: "+envName);
                    logger.info("This is Prod, don't purchase here");
                } else {
                    logger.info("Enter card details");
                    productsPage.enterGoodCardDetails(card);
                    productsPage.centreElement(ProductsPage.CheckoutPayNowButton);
                    productsPage.click(ProductsPage.CheckoutPayNowButton);
                    productsPage.waitUntilElementInvisible(ProductsPage.OrderSubmittedMessage, 15);

                    logger.info("Wait for confirmation page");
                    productsPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.ConfirmationPageTitle, 15);
                    productsPage.findOnPage(ProductsPage.ConfirmationPageOrderSummaryTitle);
                    productsPage.findOnPage(ProductsPage.ConfirmationPagePaymentMethodTitle);
                    assertTrue(driverManager.driver.getCurrentUrl().contains("checkout/confirmation"));

                    logger.info("Extracting order date, time and ID");
                    ORDER_DATE_ID_HOLDER = lottoPage.getElementAttribute("//p[contains(.,'Order placed on ')]", "textContent");
                    logger.info("ORDER_DATE_ID_HOLDER = "+ ORDER_DATE_ID_HOLDER);
                    ORDER_ID = lottoPage.getElementAttribute("//p[contains(.,'ID ')]", "textContent");
                    ORDER_ID_HOLDER = ORDER_ID.substring(3, 9).trim();

                    logger.info("ORDER_ID_HOLDER = "+ ORDER_ID_HOLDER);
                    productsPage.findOnPage(ProductsPage.ConfirmationPagePaymentMethodTitle);
                    productsPage.findOnPage(ProductsPage.ConfirmationPageCardExpiryDateDetails);
                    assertTrue(productsPage.isElementPresent(String.format(ProductsPage.ProductsPageImage, "card-brands/visa.png")));
                    assertTrue(productsPage.isElementPresent(ProductsPage.ConfirmationPageMailMessage));

                    logger.info("Download PDF if chrome browser");
                    productsPage.scrollPageToBottom();
                    productsPage.downloadPDFifChrome();
                }
                break;
            case "Lotto with autoRenewal":
                logger.info("Assert auto renewal is not optional on this page");
                assertFalse(productsPage.isElementPresent("//label[@class='mat-slide-toggle-label']//input[contains(@aria-checked,'false')]"));
                assertTrue(productsPage.isElementPresent("//small[contains(.,'Your card will be saved for recurring orders and for future purchases. You can remove it at any time. About your data.')]"));
                ISAUTORENEWAL = true;

                logger.info("About to click Pay now");
                if (envName.contains("prod")) {
                    logger.info("Environment: "+envName);
                    logger.info("This is Prod, don't purchase here");
                } else {
                    productsPage.enterGoodCardDetails(card);
                    productsPage.waitOneSecond();
                    productsPage.click(ProductsPage.CheckoutPayNowButton);
                    productsPage.waitUntilElementInvisible(ProductsPage.OrderSubmittedMessage, 15);

                    logger.info("Wait for confirmation page");
                    productsPage.waitForElementDisplayedByXpathWithTimeout(ProductsPage.ConfirmationPageTitle, 15);
                    productsPage.findOnPage(ProductsPage.ConfirmationPageSubTitle);
                    productsPage.findOnPage(ProductsPage.ConfirmationPageOrderSummaryTitle);
                    assertTrue(driverManager.driver.getCurrentUrl().contains("checkout/confirmation"));

                    logger.info("Checking purchase details");
                    productsPage.findOnPage("//h2[contains(.,'Lotto')]");
                    productsPage.findOnPage("//strong[contains(.,'" + LottoSteps.LottoTotalPriceHOLDER + "')]");

                    logger.info("Extracting order date, time and ID");
                    ORDER_DATE_ID_HOLDER = lottoPage.getElementAttribute("//p[contains(.,'Order placed on ')]", "textContent");

                    logger.info("ORDER_DATE_ID_HOLDER = "+ ORDER_DATE_ID_HOLDER);
                    ORDER_ID = lottoPage.getElementAttribute("//p[contains(.,'ID ')]", "textContent");
                    ORDER_ID_HOLDER = ORDER_ID.substring(3,9);

                    logger.info("ORDER_ID_HOLDER = "+ ORDER_ID_HOLDER);
                    productsPage.findOnPage(ProductsPage.ConfirmationPagePaymentMethodTitle);
                    productsPage.findOnPage(ProductsPage.ConfirmationPageCardExpiryDateDetails);
                    assertTrue(productsPage.isElementPresent(String.format(ProductsPage.ProductsPageImage, "card-brands/visa.png")));
                    assertTrue(productsPage.isElementPresent(ProductsPage.ConfirmationPageMailMessage));

                    logger.info("Download PDF if Chrome browser");
                    productsPage.scrollPageToBottom();
                    productsPage.downloadPDFifChrome();
                }
                break;
            case "Lotto without good card":
                logger.info("Enter card details");
                productsPage.enterBadCardDetails();

                logger.info("Assert auto renewal is OFF");
                assertTrue(productsPage.isElementPresent("//label[@class='mat-slide-toggle-label']//input[contains(@aria-checked,'false')]"));

                logger.info("About to click Pay now");
                logger.info("Environment: "+envName);
                if (envName.contains("prod")) {
                    logger.info("This is Prod, don't purchase here");
                } else {
                    productsPage.waitOneSecond();
                    productsPage.click(ProductsPage.CheckoutPayNowButton);
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for userPurchasesProduct!");
        }
    }

    @And("order is rejected")
    public void orderIsRejected() {
        logger.info("Wait for confirmation page");
        productsPage.waitUntilElementInvisible("//circle[contains(@cx,'50%')]", 10);
        productsPage.waitUntilElementInvisible("//span[contains(.,'Your card was declined.')]",10);
    }

    @And("{string} order is displayed in my order in MyAccount")
    public void findOrderInMyAccount(String product)throws NotFoundException {
        logger.info("We should already be logged in, going to my account page");
        loginPage.goToMyAccountURL("/");

        logger.info("Check if leftnav is open");
        if (driverManager.agent.contains("appium")) {
            lottoPage.click(BackofficePage.BackofficeHamburger);
            lottoPage.waitTwoSeconds();
        }

        logger.info("Check that AH is logged into MA");
        lottoPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'My Account')]", 15);
        lottoPage.findOnPage("//span[@class='ms-2'][contains(.,'Settings')]");
        lottoPage.findOnPage("//span[@class='ms-2'][contains(.,'Payment methods')]");
        lottoPage.findOnPage("//span[@class='ms-2'][contains(.,'Orders')]");

        if (product.equals("Juvenile membership")) {
            lottoPage.waitForElementDisplayedByXpathWithTimeout("//h3[contains(.,'Linked accounts')]", 15);
            lottoPage.findOnPage("//strong[contains(.,'" + MembershipSteps.FIRST_NAME_PLAN1 + " " + MembershipSteps.LAST_NAME_PLAN1 + "')]");
        }

        logger.info("Click on Order option in leftnav");
        lottoPage.click("//span[@class='ms-2'][contains(.,'Orders')]");
        lottoPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Orders')]", 10);
        lottoPage.findOnPage("//h1[contains(.,'Orders')]");
        lottoPage.findOnPage("(//strong[contains(.,'ID')])[1]");
        lottoPage.findOnPage("(//strong[contains(.,'Amount')])[1]");
        lottoPage.findOnPage("(//strong[contains(.,'Date')])[1]");
        lottoPage.findOnPage("(//strong[contains(.,'Payment status')])[1]");
        lottoPage.findOnPage("(//strong[contains(.,'Summary')])[1]");

        switch (product) {
            case "Lotto":
                logger.info("Open order with ID : " + ORDER_ID_HOLDER);
                lottoPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+LottoSteps.LottoTotalPriceHOLDER+"')])[1]");
                loginPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])[1]");
                lottoPage.click("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'" + ORDER_ID_HOLDER + "')]");
                lottoPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Order details')]", 10);

                logger.info("Verify that order ID is displayed inside order");
                lottoPage.findOnPage("//button[contains(.,'arrow_back back')]");
                lottoPage.findOnPage("//h1[contains(.,'Order details')]");
                lottoPage.findOnPage("//h2[contains(.,'General info')]");
                lottoPage.findOnPage("//span[contains(.,'Club:')]");
                lottoPage.findOnPage("//span[contains(.,'Order ID:')]");
                lottoPage.findOnPage("//*[contains(text(),'" + ORDER_ID_HOLDER + "')]");
                lottoPage.findOnPage("//span[contains(.,'Order date:')]");
                lottoPage.findOnPage("//span[contains(.,'Amount:')]");
                lottoPage.findOnPage("//span[contains(.,'Payment status:')]");
                lottoPage.findOnPage("//span[contains(.,'Complete')]");
                lottoPage.findOnPage("//h2[contains(.,'Summary')]");
                lottoPage.findOnPage("//strong[contains(.,'Playslip ID:')]");
                lottoPage.findOnPage("//strong[contains(.,'Ticket cost:')]");

                logger.info("Verify that playslip ID is displayed inside order");
                String playslipID = lottoPage.getElementAttribute("//div[@class='py-2'][contains(.,'Playslip ID:')]", "textContent");
                PLAYSLIP_ID = playslipID.substring(13, playslipID.length() - 1);
                logger.info("Playslip ID : " + PLAYSLIP_ID);
                break;
            case "Juvenile membership":
            case "Standard membership":
                logger.info("Open order with ID : " + MembershipSteps.ORDER_ID_HOLDER);
                myAccountPage.findOnPage("//*[contains(text(),'" + MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER + "')]");
//                productsPage.findOnPage("//div[@data-test='table.expander'][contains(.,'Amount "+MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER+"')]");
                loginPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])[1]");
                myAccountPage.click("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'" + MembershipSteps.ORDER_ID_HOLDER + "')]");
                myAccountPage.waitFiveSeconds();
                myAccountPage.refreshPage();
                myAccountPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'General info')]",60);
                //Added a refresh because sometimes it takes a little longer for registration details to display in myaccount

                logger.info("Verify that order ID is displayed inside order");
                myAccountPage.findOnPage("//button[contains(.,'arrow_back back')]");
                myAccountPage.findOnPage("//h1[contains(.,'Order details')]");
                myAccountPage.findOnPage("//h2[contains(.,'General info')]");
                myAccountPage.findOnPage("//span[contains(.,'Club:')]");
                myAccountPage.findOnPage("//span[contains(.,'Order ID:')]");
                myAccountPage.findOnPage("//*[contains(text(),'" + MembershipSteps.ORDER_ID_HOLDER + "')]");
                myAccountPage.findOnPage("//span[contains(.,'Order date:')]");
                myAccountPage.findOnPage("//span[contains(.,'Amount:')]");
                myAccountPage.findOnPage("//span[contains(.,'Payment status:')]");
                myAccountPage.findOnPage("//span[contains(.,'Complete')]");
                myAccountPage.findOnPage("//*[contains(text(),'" + MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER + "')]");

                if (backofficePage.isElementDisplayed("//strong[contains(.,'Bundle for')]")) {
                    logger.info("This is a bundle");
                    myAccountPage.findOnPage("//*[contains(text(),'Registration ID:')]");
                    myAccountPage.findOnPage("//*[contains(text(),'Submitted on:')]");
                } else {
                    logger.info("This is a plan");
                    myAccountPage.findOnPage("//div[@class='fw-bold'][contains(.,'Registration ID:')]");
                    myAccountPage.findOnPage("//strong[contains(.,'Submitted on:')]");
                    myAccountPage.findOnPage("//strong[contains(.,'Valid to:')]");
                    myAccountPage.findOnPage("//strong[contains(.,'Plan status:')]");
                    myAccountPage.findOnPage("//*[contains(text(),'Active')]");
                }
                break;
            case "Membership with discount code":
                logger.info("Open order with ID : " + MembershipSteps.ORDER_ID_HOLDER);
                myAccountPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Complete')])[1]");
                myAccountPage.click("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'" + MembershipSteps.ORDER_ID_HOLDER + "')]");
                myAccountPage.waitForElementDisplayedByXpathWithTimeout("//h2[contains(.,'General info')]",60);
                myAccountPage.waitFiveSeconds();
                myAccountPage.refreshPage();
                //Added a refresh because sometimes it takes a little longer for registration details to display in myaccount

                logger.info("Verify that order ID is displayed inside order");
                myAccountPage.findOnPage("//button[contains(.,'arrow_back back')]");
                myAccountPage.findOnPage("//h1[contains(.,'Order details')]");
                myAccountPage.findOnPage("//h2[contains(.,'General info')]");
                myAccountPage.findOnPage("//span[contains(.,'Club:')]");
                myAccountPage.findOnPage("//span[contains(.,'Order ID:')]");
                myAccountPage.findOnPage("//*[contains(text(),'" + MembershipSteps.ORDER_ID_HOLDER + "')]");
                myAccountPage.findOnPage("//span[contains(.,'Order date:')]");
                myAccountPage.findOnPage("//span[contains(.,'Amount:')]");
                myAccountPage.findOnPage("//span[contains(.,'Discount amount:')]");
                myAccountPage.findOnPage("//span[contains(.,'Payment status:')]");
                myAccountPage.findOnPage("//span[contains(.,'Complete')]");

                if (backofficePage.isElementDisplayed("//strong[contains(.,'Bundle for')]")) {
                    logger.info("This is a bundle");
                    myAccountPage.findOnPage("//*[contains(text(),'Registration ID:')]");
                    myAccountPage.findOnPage("//*[contains(text(),'Submitted on:')]");
                } else {
                    logger.info("This is a plan");
                    myAccountPage.findOnPage("//div[@class='fw-bold'][contains(.,'Registration ID:')]");
                    myAccountPage.findOnPage("//strong[contains(.,'Submitted on:')]");
                    myAccountPage.findOnPage("//strong[contains(.,'Valid to:')]");
                    myAccountPage.findOnPage("//strong[contains(.,'Plan status:')]");
                    myAccountPage.findOnPage("//*[contains(text(),'Active')]");
                }
                myAccountPage.findOnPage("//*[contains(text(),'Discount code:')]");
                if (DiscountCodeSteps.isMultiDiscount){
                    myAccountPage.findOnPage("//*[contains(text(),'"+DiscountCodeSteps.multiDiscountCodeRow1+"')]");
                }else{
                    myAccountPage.findOnPage("//*[contains(text(),'"+DiscountCodeSteps.singleDiscountCodeRow1+"')]");
                }
                break;
            case "Membership with multi plan discount":
                //TODO annie
                break;
            default:
                throw new NotFoundException("For some reason there is no case for findOrderInMyAccount!");
        }

        if (!myAccountPage.isElementDisplayed("(//span[contains(.,'€0.00')])[1]")){
            myAccountPage.findOnPage("//h2[contains(.,'Payment method')]");
            myAccountPage.findOnPage("//span[contains(.,'Expiry Date:')]");
            myAccountPage.findOnPage("//p[contains(.,'This payment method cannot be deleted')]");
        }
    }

    @And("Lotto playslip is displayed on lotto page in MyAccount")
    public void checkForPlayslipOnLottoPageInMyAccount() {
        productsPage.waitFifteenSeconds();
        productsPage.findOnPage("//h2[contains(.,'Active')]");
        String activeLottoPagination = backofficePage.getElementAttribute("(//div[contains(@class,'mat-paginator-range-label')])[1]", "textContent");
        String numberOfActivePlayslips = activeLottoPagination.substring(0, activeLottoPagination.length()-1);
        String playslipNumber = numberOfActivePlayslips.substring(numberOfActivePlayslips.lastIndexOf(" "));
        String number = playslipNumber.substring(1);
        logger.info("Number of active playslips: " + number + ".");
        if (number.equals("1")){
            productsPage.findOnPage("//p[contains(.,'You have "+number+" active lotto ticket')]");
        }else{
            productsPage.findOnPage("//p[contains(.,'You have "+number+" active lotto tickets')]");
        }

        logger.info("Checking lotto playslip elements are displayed");
        productsPage.findOnPage("(//header[contains(.,'Playslip ID:')])[1]");
        productsPage.findOnPage("(//header[contains(.,'Purchased:')])[1]");
        productsPage.findOnPage("(//header[contains(.,'Ticket cost:')])[1]");
        productsPage.findOnPage("//span[@class='mat-body-2'][contains(.,'"+LottoSteps.LottoTotalPriceHOLDER+"')]");
        productsPage.findOnPage("(//p[contains(.,'Draws to enter:')])[1]");
        productsPage.findOnPage("(//p[contains(.,'Draws entered:')])[1]");

        logger.info("Check playslip ID is displayed and is correct");
        productsPage.findOnPage("//*[contains(.,'"+PLAYSLIP_ID+"')]");

        logger.info("Verify lotto picks");
        LINE1 = productsPage.getElementAttribute("(//div[contains(@class,'border rounded m-1 p-1 ng-star-inserted')])[1]", "textContent");
        logger.info("Line 1 picks : " + LINE1);
        LINE2 = productsPage.getElementAttribute("(//div[contains(@class,'border rounded m-1 p-1 ng-star-inserted')])[2]", "textContent");
        logger.info("Line 2 picks : " + LINE2);
        LINE3 = productsPage.getElementAttribute("(//div[contains(@class,'border rounded m-1 p-1 ng-star-inserted')])[3]", "textContent");
        logger.info("Line 3 picks : " + LINE3);
        String line3RemoveSpaces = LINE3.replaceAll(" ", "");
        String line2RemoveSpaces = LINE2.replaceAll(" ", "");
        String line1RemoveSpaces = LINE1.replaceAll(" ", "");
        String lottopicksMA = line1RemoveSpaces+line2RemoveSpaces+line3RemoveSpaces;
        logger.info("Lotto picks from website : " + LOTTOPICKS);
        logger.info("Lotto picks in MyAccount Lotto area : " + lottopicksMA);
        assertEquals(lottopicksMA, LOTTOPICKS);

        lottoPage.findOnPage("//h1[contains(.,'Lotto')]");
        ACTIVE_PLAYSLIP_ID = lottoPage.getElementAttribute("(//span[contains(@class,'mat-body-2 ng-star-inserted')])[1]", "textContent");
        ACTIVE_PLAYSLIP_ID = ACTIVE_PLAYSLIP_ID.replaceAll(" ", "");
        logger.info("Active playslip ID : " + ACTIVE_PLAYSLIP_ID);
        ACTIVE_TICKET_OPTION_NAME = lottoPage.getElementAttribute("(//span[contains(@class,'mat-body-2 text-muted')])[1]", "textContent");
        ACTIVE_TICKET_OPTION_NAME = ACTIVE_TICKET_OPTION_NAME.substring(1, ACTIVE_TICKET_OPTION_NAME.length()-1);
        logger.info("Active ticket option name : " + ACTIVE_TICKET_OPTION_NAME);
    }

    @And("Club member can cancel auto renewal ticket")
    public void cancelAutoRenewalTicket() {
        logger.info("Click cancel auto-renew button");
        lottoPage.click("(//button[contains(.,'Cancel auto-renew')])[1]");
        lottoPage.findOnPage("//h2[@data-test='confirm-dialog.page'][contains(.,'Cancel auto-renew?')]");
        lottoPage.findOnPage("//p[contains(.,'This action will permanently stop auto-renewing of the "+ACTIVE_TICKET_OPTION_NAME+" ticket.')]");
        lottoPage.findOnPage("//p[contains(.,'Please enter your password to confirm')]");
        lottoPage.findOnPage("//input[contains(@formcontrolname,'password')]");

        logger.info("Clicking back button");
        lottoPage.click("//span[contains(.,'Back')]");

        logger.info("Click cancel auto-renew button again");
        lottoPage.click("(//button[contains(.,'Cancel auto-renew')])[1]");
        lottoPage.findOnPage("//h2[@data-test='confirm-dialog.page'][contains(.,'Cancel auto-renew?')]");
        lottoPage.findOnPage("//p[contains(.,'This action will permanently stop auto-renewing of the "+ACTIVE_TICKET_OPTION_NAME+" ticket.')]");
        lottoPage.findOnPage("//p[contains(.,'Please enter your password to confirm')]");
        lottoPage.findOnPage("//span[contains(.,'Back')]");
        lottoPage.sendKeys("//input[contains(@formcontrolname,'password')]", "xxuueor");
        lottoPage.click("//span[contains(.,'Cancel auto-renew')]");
        lottoPage.waitTwoSeconds();
        assertTrue(lottoPage.isElementDisplayed("//span[contains(.,'Wrong Password')]"));
        lottoPage.clearInputFieldUsingBackSpaceKey("//input[contains(@formcontrolname,'password')]");
        lottoPage.sendKeys("//input[contains(@formcontrolname,'password')]", "b3deG2FnmrEy");
        lottoPage.click("//span[contains(.,'Cancel auto-renew')]");

        logger.info("Check that auto-renew playslip is cancelled");
        lottoPage.waitTenSeconds();
        lottoPage.findOnPage("//span[contains(.,'Cancelled')]");
    }

    @And("Playslip is displayed on Playslip page")
    public void findPlayslipOnPlayslipPage() {
        logger.info("Find playslip");
        lottoPage.waitForElementDisplayedByXpathWithTimeout("(//strong[contains(.,'Playslip ID')])[1]", 10);
        logger.info("The playslip ID is : " + PLAYSLIP_ID);
        assertTrue(lottoPage.isElementDisplayed("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+PLAYSLIP_ID+"')]"),PLAYSLIP_ID + " is displayed on the Playslip page");
    }

    @And("Playslip is removed from current draw on Playslip page")
    public void checkPlayslipHasBeenRemovedFromPlayslipPage() {
        logger.info("Find playslip");
        lottoPage.waitTenSeconds();
        logger.info("The playslip ID is : " + PLAYSLIP_ID);
        assertFalse(lottoPage.isElementDisplayed("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+PLAYSLIP_ID+"')]"),PLAYSLIP_ID + " is displayed on the Playslip page");
    }

    @And("{string} order details are displayed in Reports Orders")
    public void findOrderOnReportsOrdersPage(String product) {
        switch (product) {
            case "Lotto":
                logger.info("Open order with ID : " + ORDER_ID_HOLDER);
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+LottoSteps.LottoTotalPriceHOLDER+"')])[1]");
                productsPage.findOnPage("(//p[contains(.,'Complete')])[1]");

                logger.info("Checking status colour is displayed");
                paymentStatusColour = Color.fromString(productsPage.findOnPage("(//p[@class='badge bg-success my-auto p-2 pill same-width ng-star-inserted'])[1]").getCssValue("background-color"));
                logger.info(paymentStatusColour);
                assertEquals(paymentStatusColour,Payment_Status_Green);

                ACCOUNT_HOLDER_NAME = productsPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                productsPage.click("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+ORDER_ID_HOLDER+"')]");
                productsPage.waitForElementDisplayedByXpathWithTimeout("//h1[contains(.,'Order details')]", 10);

                logger.info("Verify that order ID is displayed inside order");
                productsPage.findOnPage("//button[contains(.,'arrow_back back')]");
                productsPage.findOnPage("//h1[contains(.,'Order details')]");
                productsPage.findOnPage("//h2[contains(.,'General info')]");
                productsPage.findOnPage("//span[contains(.,'Order ID:')]");
                productsPage.findOnPage("//*[contains(text(),'"+ORDER_ID_HOLDER+"')]");
                productsPage.findOnPage("//span[contains(.,'Name:')]");
                productsPage.findOnPage("//*[contains(text(),'"+ACCOUNT_HOLDER_NAME.substring(ACCOUNT_HOLDER_NAME.length()-2)+"')]");
                productsPage.findOnPage("//span[contains(.,'Order date:')]");
                productsPage.findOnPage("//span[contains(.,'Amount:')]");
                productsPage.findOnPage("//span[contains(.,'Payment status:')]");
                productsPage.findOnPage("//p[contains(.,'Complete')]");
                productsPage.findOnPage("//h2[contains(.,'Summary')]");
                productsPage.findOnPage("//h4[contains(.,'Playslip ID: "+PLAYSLIP_ID+"')]");
                productsPage.findOnPage("//h4[contains(.,'Ticket cost:')]");

                logger.info("Checking if auto renew is on for ticket that was bought as an auto renewal.");
                if (ISAUTORENEWAL){
                    productsPage.findOnPage("//h4[contains(.,'Auto renew selected')]");
                }

                break;
            case "Juvenile membership":
            case "Standard membership":
                logger.info("Open order with ID : " + MembershipSteps.ORDER_ID_HOLDER);
                productsPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'"+MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER+"')]",30);
                productsPage.findOnPage("(//p[contains(.,'Complete')])[1]");

                logger.info("Checking status colour is displayed");
                paymentStatusColour = Color.fromString(productsPage.findOnPage("(//p[@class='badge bg-success my-auto p-2 pill same-width ng-star-inserted'])[1]").getCssValue("background-color"));
                logger.info(paymentStatusColour);
                assertEquals(paymentStatusColour,Payment_Status_Green);

                ACCOUNT_HOLDER_NAME = productsPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                productsPage.click("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+MembershipSteps.ORDER_ID_HOLDER+"')]");
                productsPage.waitFiveSeconds();

                logger.info("Verify that order ID is displayed inside order");
                productsPage.findOnPage("//button[contains(.,'arrow_back back')]");
                productsPage.findOnPage("//h1[contains(.,'Order details')]");
                productsPage.findOnPage("//h2[contains(.,'General info')]");
                productsPage.findOnPage("//*[contains(text(),'Order ID:')]");
                productsPage.findOnPage("//*[contains(text(),'"+MembershipSteps.ORDER_ID_HOLDER+"')]");
                productsPage.findOnPage("//span[contains(.,'Name:')]");
                productsPage.findOnPage("//*[contains(text(),'"+ACCOUNT_HOLDER_NAME.substring(ACCOUNT_HOLDER_NAME.length()-2)+"')]");
                productsPage.findOnPage("//span[contains(.,'Order date:')]");
                productsPage.findOnPage("//span[contains(.,'Amount:')]");
                productsPage.findOnPage("//span[contains(.,'Payment status:')]");
                productsPage.findOnPage("//p[contains(.,'Complete')]");
                productsPage.findOnPage("//strong[contains(.,'Registration ID:')]");
                productsPage.findOnPage("//strong[contains(.,'Submitted on:')]");
                productsPage.findOnPage("//strong[contains(.,'Valid to:')]");
                myAccountPage.findOnPage("//*[contains(text(),'"+MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER+"')]");
                productsPage.findOnPage("//*[contains(text(),'Active')]");
                productsPage.findOnPage("//mat-icon[@role='img'][contains(.,'supervisor_account')]");
//                productsPage.findOnPage("//span[contains(.,'"+MembershipSteps.FIRST_NAME_PLAN1 +"')]");
                break;
            case "bundle": logger.info("Open order with ID : " + MembershipSteps.ORDER_ID_HOLDER);
                productsPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'"+MembershipSteps.MEMBERSHIP_TOTAL_PRICE_HOLDER+"')]",30);
                productsPage.findOnPage("(//p[contains(.,'Complete')])[1]");

                logger.info("Checking status colour is displayed");
                paymentStatusColour = Color.fromString(productsPage.findOnPage("(//p[@class='badge bg-success my-auto p-2 pill same-width ng-star-inserted'])[1]").getCssValue("background-color"));
                logger.info(paymentStatusColour);
                assertEquals(paymentStatusColour,Payment_Status_Green);

                ACCOUNT_HOLDER_NAME = productsPage.getElementAttribute("(//div[@class='col-md p-3 text-break text-truncate col-6'])[2]", "textContent");
                productsPage.click("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+MembershipSteps.ORDER_ID_HOLDER+"')]");
                productsPage.waitFiveSeconds();

                logger.info("Verify that order ID is displayed inside order");
                productsPage.findOnPage("//button[contains(.,'arrow_back back')]");
                productsPage.findOnPage("//h1[contains(.,'Order details')]");
                productsPage.findOnPage("//h2[contains(.,'General info')]");
                productsPage.findOnPage("//*[contains(text(),'Order ID:')]");
                productsPage.findOnPage("//*[contains(text(),'"+MembershipSteps.ORDER_ID_HOLDER+"')]");
                productsPage.findOnPage("//span[contains(.,'Name:')]");
                productsPage.findOnPage("//*[contains(text(),'"+ACCOUNT_HOLDER_NAME.substring(ACCOUNT_HOLDER_NAME.length()-2)+"')]");
                productsPage.findOnPage("//span[contains(.,'Order date:')]");
                productsPage.findOnPage("//span[contains(.,'Amount:')]");
                productsPage.findOnPage("//span[contains(.,'Payment status:')]");
                productsPage.findOnPage("//p[contains(.,'Complete')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for findOrderInMyAccount!");
        }

        if (!productsPage.isElementDisplayed("//span[@data-test='order-details.amount'][contains(.,'0.00')]")){
            productsPage.findOnPage("//h2[contains(.,'Payment method')]");
            productsPage.findOnPage("//img[contains(@alt,'payment card icon')]");
            productsPage.findOnPage("(//strong[contains(.,'* * * *')])[1]");
            productsPage.findOnPage("//span[contains(.,'Expiry Date:')]");
            productsPage.findOnPage("//mat-icon[contains(.,'lock')]");
            productsPage.findOnPage("//p[contains(.,'This payment method cannot be deleted')]");
        }
    }

    @And("Lotto {string} ticket is displayed on auto renewals page")
    public void findRecurringOrder(String type) {
        productsPage.waitTwoSeconds();
        //TODO change date format below to dd/MM/yyyy when date format has been fixed for LOT-258
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
        Date date = new Date();
        String currentDate = formatter.format(date);
        switch (type) {
            case "cancelled auto renewal":
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+MEMBERFIRSTNAMEHOLDER+" "+MEMBERLASTNAMEHOLDER+"')])[1]");
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Inactive')])[1]");
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'1')])[1]");
                productsPage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+currentDate+"')]");
                break;
            case "active auto renewal":
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER+"')])[1]");
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'€5.00')])[1]");
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'1')])[1]");
                productsPage.findOnPage("(//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'Active')])[1]");
                productsPage.findOnPage("//div[@class='col-md p-3 text-break text-truncate col-6'][contains(.,'"+currentDate+"')]");
                break;
            default:
                throw new NotFoundException("For some reason there is no case for findRecurringOrder!");
        }
    }

    @And("{string} order shows in my mail client")
    public void orderDisplaysInMailTrap(String product) {
        logger.info("Go to MailTrap and look for mail");
        loginPage.accessMailTrapInbox();
        switch (product) {
            case "Lotto":
                loginPage.lookForLottoPurchaseMailOnMailTrap();
                break;
            case "Membership":
                loginPage.lookForMembershipPurchaseMailOnMailTrap();
                break;
            default:
        }
    }

    @Then("ClubAdmin can download {string} for Draw PDF")
    public void downloadPDF(String buttonName) throws IOException {
        switch (buttonName) {
            case "Playslips":
                if (driverManager.agent.contains("chrome")) {
                    logger.info("As we are on Chrome we also download PDF");
//                    logger.info("First read into memory all the rows on the pages");
//                    readInPlayerDetailsFromPage();  //magnus
                    productsPage.findOnPage("//button[contains(.,'arrow_downward download playslips (pdf)')]");
                    productsPage.click("//button[contains(.,'arrow_downward download playslips (pdf)')]");
                    productsPage.click("//button[contains(.,'arrow_downward download playslips (pdf)')]"); // sometimes the first click doesn't work
                    productsPage.waitThirtySeconds();
                    logger.info("Checking file name downloaded is '2023-*****' ");
                    productsPage.isDownloadedInChrome(Collections.singletonList("2023"), "2023");

//                    PDFUtil pdfUtil = new PDFUtil();
//                    String PDFText = pdfUtil.getText(buildDir+"/./.2023*.pdf");
//                    logger.info("PDFText: "+PDFText);
                }
                break;
            case "Lucky dips":
                if (driverManager.agent.contains("chrome")) {
                    logger.info("As we are on Chrome we also download PDF");
                    productsPage.findOnPage("//button[contains(.,'arrow_downward download names for lucky dip')]");
                    productsPage.click("//button[contains(.,'arrow_downward download names for lucky dip')]");
                    productsPage.click("//button[contains(.,'arrow_downward download names for lucky dip')]"); // sometimes the first click doesn't work
                    productsPage.waitThirtySeconds();
                    logger.info("Checking file name downloaded is '2022-*****' ");
                    productsPage.isDownloadedInChrome(Collections.singletonList("2022"), "2023");
                }
                break;
            default:
                throw new NotFoundException("For some reason there is no case for downloadPDF!");
        }
    }

    @And("order is displayed in Reports Orders")
    public void orderDisplaysInReportsOrders() {
        //   flaky, wait for more BE
        logger.info("Checking order appear in BO Reports/Orders page with details");
        productsPage.findOnPage("//h1[contains(.,'Reports - Orders')]");

        productsPage.waitTwoSeconds();
        backofficePage.setBackOfficeOrderPageToTodayDate();
        productsPage.waitTwoSeconds();
        backofficePage.setPaginationTo25();
        productsPage.click("//*[contains(.,'"+ORDER_ID_HOLDER+"')]");

        productsPage.waitFiveSeconds();
        productsPage.findOnPage("//*[contains(.,'"+MEMBERFIRSTNAMEHOLDER+"')]");
        productsPage.findOnPage("//*[contains(.,'"+MEMBERLASTNAMEHOLDER+"')]");

//        productsPage.findOnPage("//*[contains(.,'"+ORDER_DATE_HOLDER.substring()+"')]");  date is displayed diff, fix later
        productsPage.findOnPage("//*[contains(.,'"+LottoSteps.LottoNameSuffixHOLDER+"')]");
//        productsPage.findOnPage(Lotto numbers line 1)
//        productsPage.findOnPage(Lotto numbers line 2)
//        productsPage.findOnPage(Lotto numbers line 3)
//        productsPage.findOnPage(Amount)
        productsPage.findOnPage("//*[contains(.,'"+LottoSteps.LottoTotalPriceHOLDER+"')]");
        productsPage.findOnPage("//*[contains(.,'Mastercard')]");
        productsPage.findOnPage("//*[contains(.,'"+CARDNUMBERHOLDER.substring(13, 16)+"')]");
    }

    @And ("user attempts purchase with {string} and gets {string} for {string}")
    public void userAttemptsPurchase(String cardNumber, String cardErrorMessage, String product) {
        Lorem lorem = LoremIpsum.getInstance();
        if (product.equals("lotto")) {
            productsPage.waitThreeSeconds(); // page is loading
            productsPage.click(ProductsPage.ShoppingCartCheckoutButton);
            productsPage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Lotto tickets')]", 30);
            logger.info("Going to click sign in");
            productsPage.click(ProductsPage.CheckoutPageEnterEmailButton);
            productsPage.sendKeys(LoginPage.LoginEmailField, "membertest3@clubforce.com");
            productsPage.click(LoginPage.LoginNextButton);
            productsPage.sendKeys(LoginPage.LoginPasswordField, "b3deG2FnmrEy");
            productsPage.click("//form[contains(@id,'form')]");
            productsPage.click("//button[@data-test='sign-in.signInButton']");
        }

        productsPage.findOnPage(ProductsPage.CheckoutPageTitle);
        productsPage.refreshPage(); //TODO remove once Pay Now button works first time

        productsPage.scrollPageDown();
        productsPage.sendKeys(ProductsPage.CheckoutPageNameOnCard, lorem.getFirstNameFemale());
        ProductPurchaseSteps.CARDNUMBERHOLDER = cardNumber;
        productsPage.scrollPageDown();
        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        productsPage.waitTwoSeconds();
        productsPage.sendKeys(ProductsPage.CheckoutPageCardNumber, ProductPurchaseSteps.CARDNUMBERHOLDER);
        productsPage.scrollPageDown();
        productsPage.sendKeys(ProductsPage.CheckoutPageCardExpiry, "1126");
        productsPage.sendKeys(ProductsPage.CheckoutPageCVC, "123");
        driverManager.driver.switchTo().parentFrame();
        productsPage.findOnPage(ProductsPage.CheckoutPayNowButton);

        productsPage.click(ProductsPage.CheckoutPayNowButton);
        if (envName.contains("prod")) {
            productsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'Your card was declined. Your request was in live mode, but used a known test card.')]",15);
        } else {
            productsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(.,'" + cardErrorMessage + "')]",15);
        }
        productsPage.waitOneSecond();
    }

    @Then("they can share link to Lotto on {string}")
    public void shareLottoLinkToSocialMedia(String socialMedia) {
        lottoPage.waitFiveSeconds();
        switch (socialMedia) {
            case "Facebook":
                lottoPage.click("//*[@data-icon='facebook-f']");
                lottoPage.waitFiveSeconds();
                lottoPage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains("facebook.com"));
                lottoPage.switchToBrowserTab(0);
                break;
            case "LinkedIn":
                lottoPage.click("//*[@data-icon='linkedin-in']");
                lottoPage.waitFiveSeconds();
                lottoPage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains("linkedin.com"));
                lottoPage.switchToBrowserTab(0);
                break;
            case "Twitter":
                lottoPage.click("//*[@data-icon='twitter']");
                lottoPage.waitFiveSeconds();
                lottoPage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains("twitter.com"));
                lottoPage.switchToBrowserTab(0);
                break;
            default:
        }
    }
}
