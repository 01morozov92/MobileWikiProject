package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    private  static final String
    SAVED_LISTS = "//*[@text='Сохранено']";

    public NavigationUI(AppiumDriver<MobileElement> driver){
        super(driver);
    }

    public void clickBack(){
        driver.navigate().back();
    }

    public void clickSavedList(){
        this.waitForElementAndClick(By.xpath(SAVED_LISTS), "Cannot find", 5);
    }
}
