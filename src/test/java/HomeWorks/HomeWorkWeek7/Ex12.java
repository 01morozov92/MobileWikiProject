package HomeWorks.HomeWorkWeek7;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class Ex12 extends CoreTestCase {

    //Тест полностью кросс платформенный, работает одинакого как на iOS так и на Android
    @Test
    public void testCheckArticlesByTitleAndDescriptionCrossPlatform() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.typeSearchLine("Android");
        searchPageObject.waitForElementByTitleAndDescription
                ("Android Studio", "среда разработки для Android");
        searchPageObject.waitForElementByTitleAndDescription
                ("Android 10", "десятая версия ОС Android");
        searchPageObject.waitForElementByTitleAndDescription
                ("Android Lollipop", "Версия мобильной ОС Android 5.0").click();
    }
}
