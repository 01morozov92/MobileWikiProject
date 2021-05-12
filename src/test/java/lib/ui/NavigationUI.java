package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class NavigationUI extends MainPageObject{

    @AndroidFindBy(xpath = "//*[@text='Сохранено']")
    @iOSXCUITFindBy(id = "")
    MobileElement savedList;

    public NavigationUI(AppiumDriver<MobileElement> driver){
        super(driver);
    }

    public void clickBack(){
        driver.navigate().back();
    }

    public void clickSavedList(){
        this.waitForElementAndClick(savedList, "Cannot find", 5);
    }
}
