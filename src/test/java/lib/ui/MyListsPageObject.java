package lib.ui;

import HomeWorkWeek4.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.time.Duration;

import static io.appium.java_client.touch.WaitOptions.waitOptions;

public class MyListsPageObject extends MainPageObject{

    public MyListsPageObject(AppiumDriver<MobileElement> driver){
        super(driver);
    }

    public void swipeArticleToDelete(String articleTitle){
        this.swipe( waitOptions(Duration.ofMillis(500)), Direction.вправо, getElementByText(articleTitle, allElements));
    }
}
