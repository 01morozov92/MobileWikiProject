package HomeWorks.HomeWorkWeek9;

import lib.CoreTestCase;
import lib.uiMobile.ArticlePageObject;
import lib.uiMobile.MyListsPageObject;
import lib.uiMobile.NavigationUI;
import lib.uiMobile.SearchPageObject;
import org.junit.Assert;
import org.testng.annotations.Test;

public class Ex17 extends CoreTestCase {

    @Test
    public void testDeleteArticle() {
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
        myListsPageObject.deleteArticle("Java");
        myListsPageObject.chooseList(nameOfArticle);
        String expectedResult = "AppImage";
        String actualResult = articlePageObject.getTitleOfArticle(textOfTitle);
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
