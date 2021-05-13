package HomeWorks.HomeWorkWeek5;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class Ex9 extends CoreTestCase {

    @Test
    public void testCheckArticlesByTitleAndDescription() {
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
