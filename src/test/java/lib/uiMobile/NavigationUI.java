package lib.uiMobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.remote.RemoteWebDriver;

import static lib.Platform.isIOS;

public class NavigationUI extends MainPageObject {

    @AndroidFindBy(xpath = "//*[@text='Сохранено']")
    @iOSXCUITFindBy(id = "Empty")
    MobileElement savedList;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Назад")
    MobileElement back;


    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void backToSerachLineFromArticle() {
        if (isIOS()) {
            clickBack();
        } else {
            clickBack();
            clickBack();
        }
    }

    public void clickBack() {
        if (isIOS()) {
            this.waitForElementAndClick(back, "Cannot find back button", 10);
        } else {
            driver.navigate().back();
        }
    }

    //this.waitForElementAndClick(savedBtn, "Cannot find saved button", 10);

    public void clickSavedList() {
        this.waitForElementAndClick(savedList, "Cannot find", 5);
    }
}
