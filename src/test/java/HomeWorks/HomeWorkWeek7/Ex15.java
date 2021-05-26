package HomeWorks.HomeWorkWeek7;

import lib.CoreTestCase;
import lib.uiWeb.TestWebPageObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class Ex15 extends CoreTestCase {

    @Test
    public void testWeb() throws InterruptedException {
        String titleOfArticlePage;
        TestWebPageObject testWebPageObject = new TestWebPageObject(driver);
        testWebPageObject.clickSearch();
        testWebPageObject.inputText("Java");
        testWebPageObject.chooseArticleByText("Object-oriented programming language");
        testWebPageObject.saveArticle();
        testWebPageObject.singInFromArticle();
        testWebPageObject.clickSearch();
        testWebPageObject.inputText("Appium");
        testWebPageObject.chooseArticleByText("AppImage");
        testWebPageObject.saveArticle();
        testWebPageObject.goToWatchListThoughtBurgerMenu();
        testWebPageObject.deleteArticleByText("AppImage");
        Assert.assertTrue(testWebPageObject.checkArticles());
        testWebPageObject.chooseArticleByTextFromWatchList("Java (programming language)");
        titleOfArticlePage = driver.getTitle();
        Assert.assertEquals(titleOfArticlePage, "Java (programming language) - Wikipedia", "This is a wrong page");
    }
}
