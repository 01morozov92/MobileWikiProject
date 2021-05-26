package HomeWorks.HomeWorkWeek9;

import lib.CoreTestCase;
import lib.uiMobile.SearchPageObject;
import org.testng.annotations.Test;

public class Ex18 extends CoreTestCase {

        //Тест полностью кросс платформенный, работает одинакого как на iOS так и на Android так и для Web
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