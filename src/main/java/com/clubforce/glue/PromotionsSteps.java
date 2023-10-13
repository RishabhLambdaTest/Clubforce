package com.clubforce.glue;

import com.clubforce.framework.SeleniumUtilities;
import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.PromotionsPage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class PromotionsSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    public static String PROMO_TITLE_HOLDER;
    public static String PROMO_DESCRIPTION_HOLDER;

    WebDriverManager driverManager;

    public PromotionsSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.promotionsPage = driverManager.promotionsPage;
    }

    @Then("promotions tab is {string}")
    public void promoLeftNavPresent(String display_option) {
        switch (display_option) {
            case "displayed":
                assertTrue(promotionsPage.isElementDisplayed(PromotionsPage.PromotionsLeftNav));
                break;
            case "not displayed":
                assertFalse(promotionsPage.isElementDisplayed(PromotionsPage.PromotionsLeftNav));
                break;
            default:
                throw new NotFoundException("For some reason there is no case for promoLeftNavPresent!");
        }
    }

    @And("a new promotion is created")
    public void createPromotion() {
        Lorem lorem = LoremIpsum.getInstance();
        assertTrue(promotionsPage.isElementDisplayed(PromotionsPage.PromotionsLeftNav));
        logger.info("Adding random details in promo fields");
        PROMO_TITLE_HOLDER = "Lotto promotion "+lorem.getWords(2)+" "+ SeleniumUtilities.getDateTimeFormat("yyyyMMDD_HH:mm");
        PROMO_DESCRIPTION_HOLDER = lorem.getCity()+" "+lorem.getWords(10,13);

        promotionsPage.click(PromotionsPage.CreatePromoButton);

        promotionsPage.sendKeys(PromotionsPage.PromoTitleField, PROMO_TITLE_HOLDER);
        promotionsPage.sendKeys(PromotionsPage.PromoDescriptionField, PROMO_DESCRIPTION_HOLDER);
        logger.info("Promo details, title: "+PROMO_TITLE_HOLDER);
        logger.info("Promo details, description: "+PROMO_DESCRIPTION_HOLDER);
        promotionsPage.click(PromotionsPage.PromoPublish);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout(PromotionsPage.PromotionsPageTitle, 5);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", 5);
    }

    @And("a promotion is edited")
    public void editPromotion() {
        Lorem lorem = LoremIpsum.getInstance();
        promotionsPage.waitForElementDisplayedByXpathWithTimeout(PromotionsPage.PromotionsPageTitle, 5);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", 5);

        promotionsPage.clickElementRightOf("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", PromotionsPage.EditPromoButton);

        logger.info("Promo old details, title: "+PROMO_TITLE_HOLDER);
        logger.info("Promo old details, description: "+PROMO_DESCRIPTION_HOLDER);
        PROMO_TITLE_HOLDER = "Lotto promotion "+lorem.getWords(2,3)+" "+ SeleniumUtilities.getDateTimeFormat("yyyyMMDD-HH:mm");
        PROMO_DESCRIPTION_HOLDER = lorem.getCity()+" "+lorem.getWords(10,13);
        logger.info("Promo new details, title: "+PROMO_TITLE_HOLDER);
        logger.info("Promo new details, description: "+PROMO_DESCRIPTION_HOLDER);

        promotionsPage.clearInputFieldUsingBackSpaceKey(PromotionsPage.PromoTitleField); // https://clubforce.atlassian.net/browse/NG-1835
        promotionsPage.sendKeys(PromotionsPage.PromoTitleField, PROMO_TITLE_HOLDER);
        promotionsPage.sendKeys(PromotionsPage.PromoDescriptionField, PROMO_DESCRIPTION_HOLDER);
        promotionsPage.click(PromotionsPage.PromoSaveChanges);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout(PromotionsPage.PromotionsPageTitle, 5);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", 5);
    }

    @And("a promotion is deleted")
    public void deletePromotion() {
        logger.info("Process to deleting promotion: "+PROMO_TITLE_HOLDER);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout(PromotionsPage.PromotionsPageTitle, 5);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", 5);
        promotionsPage.clickElementRightOf("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", PromotionsPage.DeletePromoButton);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//h2[@data-test='confirm-dialog.page'][contains(.,'Delete promotion?')]",5);
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//mat-dialog-content[contains(.,'You are about to delete the promotion')]",5);

        logger.info("Cancel deleting promotion");
        promotionsPage.click("//span[contains(@id,'dialog--cancel')]");
        promotionsPage.waitTwoSeconds();
        promotionsPage.refreshPage();
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]",5);

        logger.info("Deleting promotion");
        promotionsPage.clickElementRightOf("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]", PromotionsPage.DeletePromoButton);
        promotionsPage.click("//span[contains(@id,'dialog--confirm')]");
        promotionsPage.waitTwoSeconds();
        promotionsPage.refreshPage();
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("(//strong[contains(.,'Actions')])[1]",5);
        promotionsPage.waitTwoSeconds();
        assertFalse(promotionsPage.isElementDisplayed("//span[contains(@title,'"+PROMO_TITLE_HOLDER+"')]"));
    }

    @And("buyer selects cart promotion which takes them to Lotto page")
    public void buyerBuysLottoPromotion() {
        assertTrue(promotionsPage.isElementPresent(String.format(PromotionsPage.PromotionImage, "lotto-promotion.png")));
        promotionsPage.click("//button[contains(.,'purchase')]");
        promotionsPage.waitForElementDisplayedByXpathWithTimeout("//span[contains(@id,'dialog--confirm')]",5);

        logger.info("Confirming we are over 18");
        promotionsPage.click("//button[contains(.,'Confirm')]");
    }
}
