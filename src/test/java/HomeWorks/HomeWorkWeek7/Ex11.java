package HomeWorks.HomeWorkWeek7;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.testng.annotations.Test;

public class Ex11 extends CoreTestCase {


    //Тест полностью кросс платформенный, работает одинакого как на iOS так и на Android
    @Test
    public void testDeleteArticleBySwipeCrossPlatform() {
        //Инициализация необходимых параметров
        String nameOfArticle = "AppImage";
        String textOfTitle = "AppImage";
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        //Поиск статьи и переход к ней
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResultAndClick("Java");
        //Создание списка статей и добавление в него статьи
        articlePageObject.addArticleToNewMyList("Мой список");
        //Возвращение к строке поиска
        navigationUI.backToSerachLineFromArticle();
        //Поиск второй статьи и добавление ее в уже созданный список
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.waitForSearchResultAndClick("AppImage");
        articlePageObject.addArticleToMyList("Мой список");
        navigationUI.clickBack();
        articlePageObject.getIntoMySavedLists("Мой список");
        //Удаление второй статьи и проверка, что осталась только первая статья
        myListsPageObject.swipeArticleToDelete("Язык программирования");
        myListsPageObject.chooseList(nameOfArticle);
        String expectedResult = "AppImage";
        String actualResult = articlePageObject.getTitleOfArticle(textOfTitle);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
