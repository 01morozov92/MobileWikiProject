package HomeWorks.HomeWorkWeek9;

import io.qameta.allure.Epic;
import lib.CoreTestCase;
import lib.uiMobile.SearchPageObject;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;

@Epic("Тесты для статей")
public class Ex18 extends CoreTestCase {

        //Тест полностью кросс платформенный, работает одинаково как на iOS так и на Android так и для Web
        @Test
        @Severity(value = SeverityLevel.BLOCKER)
        @Features("Поиск статьи по заголовку и описанию")
        @Step("Начало теста testCheckArticlesByTitleAndDescriptionCrossPlatform")
        @Title("Проверка статьи по заголовку и описанию в результате поисковой выдачи")
        @Description("Вводим в поиск Android > проверяем выдачу на наличие 3ех результатов > переходим в 3-ю статью")
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