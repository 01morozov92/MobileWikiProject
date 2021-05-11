package lib.ui;

import HomeWorkWeek4.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static io.appium.java_client.touch.WaitOptions.waitOptions;

public class MyListsPageObject extends MainPageObject{

    private static final String
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver<MobileElement> driver){
        super(driver);
    }


    /* TEMPLATES METHODS */
    private static String getNameOfList(String substring){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", substring);
    }
    /* TEMPLATES METHODS */

    public void swipeArticleToDelete(String articleTitle){
        this.swipe( waitOptions(Duration.ofMillis(500)), Direction.вправо, getNameOfList(articleTitle));
    }
}
