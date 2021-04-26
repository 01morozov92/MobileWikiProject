package HomeWorkWeek4;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class Ex5 extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testDeleteArticleBySwipe() {
        //Инициализация необходимых параметров
        String nameOfArticle = "Java";
        String textOfTitle = "Java";
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        //Поиск статьи и переход к ней
        searchPageObject.skipWelcomePage();
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResultAndClick("язык программирования");
        //Создание списка статей и добавление в него статьи
        articlePageObject.addArticleToNewMyList("Мой список");
        //Возвращение к строке поиска
        navigationUI.clickBack();
        navigationUI.clickBack();
        //Поиск второй статьи и добавление ее в уже созданный список
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.waitForSearchResultAndClick("AppImage");
        articlePageObject.addArticleToMyList("Мой список");
        //Удаление второй статьи и проверка, что осталась только первая статья
        myListsPageObject.swipeArticleToDelete("AppImage");
        articlePageObject.chooseList(nameOfArticle);
        String expectedResult = "Java";
        String actualResult = articlePageObject.getTitleOfArticle(textOfTitle);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
