package com.clubforce.glue;

import com.clubforce.framework.WebDriverManager;
import com.clubforce.pages.ArticlePage;
import com.clubforce.pages.BackofficePage;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ArticleSteps extends WebDriverManager {
    //logger
    private static final Logger logger = LogManager.getLogger();

    WebDriverManager driverManager;

    public ArticleSteps(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.articlePage = driverManager.articlePage;
        this.clubPage = driverManager.clubPage;
    }

    private static String ArticlesRandomTitleHOLDER;
    private static String ArticlesRandomSummaryHOLDER;
    private static String ArticlesRandomContentHOLDER;

    @When("they open an article")
    public void theFirstArticleIsOpened() {
        articlePage.centreElement("//h2[contains(.,'Club news')] | //h2[contains(.,'Latest news')]");
        articlePage.click(ArticlePage.ArticleAboutBikesCardOnHomepage);
    }

    @Then("they can share article to {string}")
    public void shareTheArticle(String media) {
        articlePage.waitTwoSeconds();
        switch (media) {
            case "Facebook":
                articlePage.click(ArticlePage.ArticlesFacebookIcon);
                articlePage.waitTwoSeconds();
                articlePage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains("facebook.com"));
                articlePage.switchToBrowserTab(0);
                break;
            case "Twitter":
                articlePage.click(ArticlePage.ArticlesTwitterIcon);
                articlePage.waitTwoSeconds();
                articlePage.switchToBrowserTab(1);
                assertTrue(driverManager.driver.getCurrentUrl().contains("twitter.com"));
                articlePage.switchToBrowserTab(0);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for shareTheArticle!");
        }
    }

    @Then("they can expand the articles section using the Load More button")
    public void userExpandsArticlesWithLoadMore() {
        if (envName.contains("test")) {
            checkFirst6Articles();
            checkSecond6Articles();
            checkThirdArticles();
            refreshPageAndCheckFirst6Articles();
        }
        if (envName.contains("sandbox")) {
            checkFirst6Articles();
            checkSecond6Articles();
            checkThirdArticles();
            refreshPageAndCheckFirst6Articles();
        }
        if (envName.contains("prod")) {
            checkFirst6Articles();
            checkSecond6Articles();
            checkThirdArticles();
            refreshPageAndCheckFirst6Articles();
        }
    }

    public void checkFirst6Articles() {
        logger.info("Wait and check the first 6 articles");
        articlePage.centreElement(ArticlePage.ArticlePageLoadMoreButton);
        articlePage.waitTwoSeconds();
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "This article is about bikes")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Automation_Ballán")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Carrickwilliamodonnell")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Athenry")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Charlie")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Bamba")));
        assertThat("Found unexpected article text!", !articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "999999999999999")));
        assertThat("Found unexpected article text!", !articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "888888888888888")));
        articlePage.waitOneSecond();
    }

    public void checkSecond6Articles() {
        logger.info("Wait and check the second 6 articles");
        articlePage.waitOneSecond();
        articlePage.click(ArticlePage.ArticlePageLoadMoreButton);
        articlePage.scrollPageDown();
        articlePage.waitTwoSeconds();
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Amaretto")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "992")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "8888")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "77 seventyseven")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "666")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "55555555555555555")));
        articlePage.waitOneSecond();
    }

    public void checkThirdArticles() {
        logger.info("Wait and check last articles");
        articlePage.waitOneSecond();
        articlePage.click(ArticlePage.ArticlePageLoadMoreButton);
        articlePage.scrollPageDown();
        articlePage.waitTwoSeconds();
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "444444444")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "333333333")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "222222222")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Lough Clougherbowbart")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Templeancheathraira..")));
    }

    public void refreshPageAndCheckFirst6Articles() {
        logger.info("Refresh page and check only first 6 articles are showing and check the last 4 articles");
        articlePage.refreshPage();
        articlePage.waitTwoSeconds();
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "This article is about bikes")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Automation_Ballán")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Carrickwilliamodonnell")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Athenry")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Charlie")));
        assertThat("Did not find expected article text!", articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, "Bamba")));
    }

    @And("ClubAdmin {string} an article")
    public void adminUpdatesOrCreatesAnArticle(String action) {
        Lorem lorem = LoremIpsum.getInstance();

        articlePage.sendKeys(ArticlePage.ArticlesPageSearchBar, "automation");
        articlePage.waitForElementDisplayedByXpathWithTimeout(ArticlePage.ArticlePageHeading, 10);

        switch (action) {
            case "updates":
                articlePage.waitForElementDisplayedByXpathWithTimeout("//*[contains(text(),'Automation_Ballán')]", 10);
                articlePage.click(ArticlePage.ViewDetailsFirstButton);
                articlePage.waitForElementDisplayedByXpathWithTimeout(ArticlePage.UpdateArticleHeading,10);
                logger.info("Updating "+clubAdminUsername+"'s article");
                break;
            case "creates":
                articlePage.click(ArticlePage.AddArticleButtonOnArticlePage);
                articlePage.waitForElementDisplayedByXpathWithTimeout(ArticlePage.CreateArticleHeading,10);
                break;
            default:
                throw new NotFoundException("For some reason there is no case for adminUpdatesOrCreatesAnArticle!");
        }

        ArticlesRandomTitleHOLDER = "Automation_Ballán Álainn 韓國語 "+ RandomStringUtils.randomAlphabetic(3);
        articlePage.sendKeys(BackofficePage.FormControlNameTitle, ArticlesRandomTitleHOLDER);

        ArticlesRandomSummaryHOLDER = lorem.getTitle(6);
        articlePage.sendKeys(BackofficePage.FormControlNameSummary, ArticlesRandomSummaryHOLDER);

        ArticlesRandomContentHOLDER = lorem.getWords(100, 100);
        articlePage.sendKeys(ArticlePage.ArticlesQLEditorFilled, ArticlesRandomContentHOLDER);
        articlePage.click(ArticlePage.ArticlesAddVideoButton);
        articlePage.sendKeys(ArticlePage.ArticlesEmbedVideo, "https://www.youtube.com/watch?v=WQOmyoQ7ISE");
        articlePage.click(ArticlePage.ArticlesEmbedVideoSaveButton);

        if(envName.contains("test")){
            if(action.equals("updates")){
                logger.info("Delete current newsArticleImage");
                articlePage.click(ArticlePage.ArticlesDeleteImageButton);
                articlePage.click(ArticlePage.ArticleRemoveImageButton);
            }
            articlePage.uploadFileUsingJSExec("newsArticleImage.jpg", ArticlePage.ArticleUploadImageFilePath);
            articlePage.waitForElementDisplayedByXpathWithTimeout(ArticlePage.ArticleUploadImageButton,10);

            logger.info("Click upload button");
            articlePage.click(ArticlePage.ArticleUploadImageButton);
        }

        logger.info("Uploaded newsArticleImage");
        articlePage.click(BackofficePage.HeaderPublishButton);
        articlePage.waitUntilElementInvisible("//span[contains(@role,'progressbar')]", 20);

        articlePage.waitForElementDisplayedByXpathWithTimeout(ArticlePage.UpdateArticleHeading, 20);
        articlePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.FormControlNameTitle, 20);
        articlePage.waitForElementDisplayedByXpathWithTimeout(BackofficePage.FormControlNameSummary, 20);
        articlePage.waitForElementDisplayedByXpathWithTimeout(ArticlePage.ArticlesQLEditorFilled, 20);
        assertThat(articlePage.getElementAttribute(BackofficePage.FormControlNameTitle, "value"),containsString(ArticlesRandomTitleHOLDER));
        assertThat(articlePage.getElementAttribute(BackofficePage.FormControlNameSummary, "value"),containsString(ArticlesRandomSummaryHOLDER));
        assertThat(articlePage.getElementAttribute(ArticlePage.ArticlesQLEditorFilled, "textContent"),containsString(ArticlesRandomContentHOLDER));
    }

    @Then("the article on the website is updated")
    public void websiteArticleIsUpdate() {
        articlePage.goToClubURL("");
        articlePage.deleteAllCookies();
        articlePage.waitFiveSeconds();
        articlePage.refreshPage();
        articlePage.acceptCookies();
        articlePage.waitTwoSeconds();

        logger.info("Verifying " + clubAdminUsername + "'s article on webpage");
        articlePage.click(BackofficePage.AutomationBallanImage);

        logger.info("Verifying article");
        articlePage.waitTwoSeconds();
//        articlePage.click(BackofficePage.HeadlineHeading);
//        articlePage.waitTwoSeconds();
        assertTrue( articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, ArticlesRandomTitleHOLDER)));
        assertTrue( articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, ArticlesRandomSummaryHOLDER)));
        assertTrue( articlePage.isElementPresent(String.format(ArticlePage.ArticlePageText, ArticlesRandomContentHOLDER)));
    }
}
