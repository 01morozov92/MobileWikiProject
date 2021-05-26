package lib.uiMobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static lib.Platform.isIOS;

public class MyListsPageObject extends MainPageObject {

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='swipe action delete']")
    public MobileElement delete;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void swipeArticleToDelete(String articleTitle) {
        this.swipe(waitOptions(Duration.ofMillis(2000)), Direction.влево, getElementByText(articleTitle, allElements));
        if (isIOS()) {
            this.waitForElementAndClick(delete, "Cannot find delete element");
        }
    }

    public void chooseList(String nameOfList) {
        this.waitForElementAndClick((getElementByText(nameOfList, allElements)), "Cannot find and click article by name");
    }
}
