package com.clubforce.pages;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.glue.ProductPurchaseSteps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Collections;

import static org.testng.Assert.assertTrue;

public class ProductsPage extends SeleniumUtilities {

    public WebDriverManager driverManager;

    public ProductsPage(WebDriverManager driverManager) {
        super(driverManager);
        this.driverManager = driverManager;
    }

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Links
    public static final String ProductsPageText = "//*[contains(text(),'%s')]";
    public static final String ProductsPageImage = "//img[contains(@src, '%s')]";
    public static final String ClubHomepageShoppingCartIcon = "//mat-icon[contains(.,'shopping_cart')]";
    public static final String ShoppingCartContinueShoppingButton = "//button[contains(.,'Continue shopping')]";
    public static final String ShoppingCartCheckoutButton = "//button[contains(.,'Checkout')]";
    public static final String ShoppingCartEmptyMessage = "//h3[contains(.,'Your shopping cart is empty')]";
    public static final String CheckoutPageTitle = "//h2[contains(.,'Checkout')]";
    public static final String CheckoutPageEnterEmailButton = "//button[contains(.,'Enter email')]";
    public static final String MembershipCheckoutPageEnterEmailButton = "//button[contains(.,'enter email')]";
    public static final String CheckoutPageUseNewCardTitle = "//h3[contains(.,'Use a New Card')]";
    public static final String CheckoutPageSaveCardTitle = "//span[contains(.,'Save card for future purchases')]";
    public static final String CheckoutPageNameOnCard = "//input[contains(@formcontrolname,'nameOnCard')]";
    public static final String CheckoutPageCardNumber = "//input[contains(@name,'cardnumber')]";
    public static final String CheckoutPageCardExpiry = "//input[contains(@name,'exp-date')]";
    public static final String CheckoutPageCVC = "//input[contains(@name,'cvc')]";
    public static final String DiscountCodeField = "//input[contains(@formcontrolname,'discountCode')]";
    public static final String DiscountCodeApplyButton = "//button[contains(.,'apply')]";
    public static final String DiscountCodeAppliedTextAndCheckIcon = "//span[contains(.,'Discount code applied check_circle')]";
    public static final String DiscountCodeRemoveButton = "//button[contains(.,'remove')]";
    public static final String DiscountsExpandIcon = "//mat-icon[contains(.,'expand_more')]";
    public static final String CheckoutPayNowButton = "//button[contains(.,'Pay Now')]";
    public static final String ConfirmationPageTitle = "//h1[contains(.,'Order confirmed')]";
    public static final String ConfirmationPageSubTitle = "//p[contains(.,'Thank you for your order. We hope you had a great experience.')]";
    public static final String ConfirmationPageOrderSummaryTitle = "//h2[contains(.,'Order summary')]";
    public static final String ConfirmationPagePaymentMethodTitle = "//h2[contains(.,'Payment method')]";
    public static final String ConfirmationPagePaymentCardDetails = "//mat-card-title[@class='mat-card-title mat-card--sidenav__header__title'][contains(.,'1111')]";
    public static final String ConfirmationPageCardExpiryDateDetails = "//mat-card-subtitle[contains(.,'Expiry Date: 11 / 2026')]";
    public static final String ConfirmationPageMailMessage = "//p[contains(.,'A copy of this message has been sent to your email ')]";
    public static final String OrderSubmittedMessage = "//span[contains(.,'Your order has been submitted.')]";
    public static final String ConfirmationPageReturnToHomepageButton = "//button[contains(.,'Return to home page')]";
    public static final String DismissButton = "//span[contains(.,'Dismiss')]";
    public static final String EnterEmailButton = "//button[contains(.,'Enter email')]";
    public static final String FirstDeleteBinIcon = "//mat-icon[contains(.,'delete')][1]";
    public static final String AddToCartMembershipButton = "//button[contains(.,'Add to Cart')]";
    public static final String MembershipPlansCartHeading = "//strong[contains(.,'Membership plans')]";
    public static final String MembershipPlansConfirmationPageHeading = "//h2[contains(.,'Membership plans')]";

    //Methods
    public void verifyCheckoutPageElements() {
        logger.info("Verifying elements on club account page");
        waitOneSecond();
        findOnPage("//h3[contains(.,'Sign in or create account')]");
        findOnPage("//span[contains(.,'You will need an account before proceeding')]");
    }

    public void enterGoodCardDetails(String cardNumber) {
        logger.info("Enter card details");
        findOnPage(CheckoutPageUseNewCardTitle);
        logger.info("Card holder name: " + ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER + " " + ProductPurchaseSteps.MEMBERLASTNAMEHOLDER);
        sendKeys(CheckoutPageNameOnCard, ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER+" "+ProductPurchaseSteps.MEMBERLASTNAMEHOLDER);

        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        ProductPurchaseSteps.CARDNUMBERHOLDER = cardNumber;
        logger.info("Card number: " + ProductPurchaseSteps.CARDNUMBERHOLDER);
        sendKeys(CheckoutPageCardNumber, ProductPurchaseSteps.CARDNUMBERHOLDER);

        logger.info("Check expiry date");
        sendKeys(CheckoutPageCardExpiry, "1120");
        driverManager.driver.switchTo().parentFrame();
        waitTwoSeconds();
        assertTrue(isElementPresent("//div[contains(@class,'mat-error cardError')]"));
//   breaks on phones     assertThat(getElementAttribute("//div[contains(@class,'mat-error cardError')]", "textContent"),containsString("Your card's expiration year is in the past"));

        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        clear(CheckoutPageCardExpiry);
        sendKeys(CheckoutPageCardExpiry, "1126");

        logger.info("Set CVC code");
        sendKeys(CheckoutPageCVC, "123");
        driverManager.driver.switchTo().parentFrame();
    }

    public void enterBadCardDetails() {
        logger.info("Enter card details");
        waitTwoSeconds();
        findOnPage(CheckoutPageUseNewCardTitle);
        sendKeys(CheckoutPageNameOnCard, ProductPurchaseSteps.MEMBERFIRSTNAMEHOLDER+" "+ProductPurchaseSteps.MEMBERLASTNAMEHOLDER);

        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        ProductPurchaseSteps.CARDNUMBERHOLDER = "4000000000000002";
        sendKeys(CheckoutPageCardNumber, ProductPurchaseSteps.CARDNUMBERHOLDER);

        logger.info("Check expiry date");
        sendKeys(CheckoutPageCardExpiry, "1120");
        driverManager.driver.switchTo().parentFrame();
        waitTwoSeconds();
        assertTrue(isElementPresent("//div[contains(@class,'mat-error cardError')]"));
//   breaks on phones     assertThat(getElementAttribute("//div[contains(@class,'mat-error cardError')]", "textContent"),containsString("Your card's expiration year is in the past"));

        driverManager.driver.switchTo().frame(driverManager.driver.findElement(By.xpath("(//iframe[contains(@src,'js.stripe.com')])[1]")));
        sendKeys(CheckoutPageCardExpiry, "1126");

        logger.info("Set CVC code");
        sendKeys(CheckoutPageCVC, "123");
        driverManager.driver.switchTo().parentFrame();
    }

    public void downloadPDFifChrome() {
        if (driverManager.agent.contains("chrome")) {
            logger.info("If we are on Chrome we also download PDF");
            waitTenSeconds();
            click("//button[contains(.,'picture_as_pdf Download copy')]");
            click("//button[contains(.,'picture_as_pdf Download copy')]"); // sometimes the first click doesn't work
            waitTenSeconds();
            logger.info("Checking file name downloaded is 'confirmation-*****' ");
            isDownloadedInChrome(Collections.singletonList("confirmation-"), "confirmation");
        }
    }
}
