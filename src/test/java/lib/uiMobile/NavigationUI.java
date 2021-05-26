package lib.uiMobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static lib.Platform.*;

@Log4j2
public class NavigationUI extends MainPageObject {

    @AndroidFindBy(xpath = "//*[@text='Сохранено']")
    @iOSXCUITFindBy(id = "Empty")
    WebElement savedList;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Назад")
    WebElement back;


    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    SearchPageObject searchPageObject = new SearchPageObject(driver);

    public void backToSerachLineFromArticle() {
        if (isIOS()) {
            clickBack();
        } else if(isAndroid()){
            clickBack();
            clickBack();
        } else if(isWeb()){
           log.info("We are already here =)");
        }
    }

    public void clickBack() {
        if (isIOS()) {
            this.waitForElementAndClick(back, "Cannot find back button", 10);
        } else if(isAndroid()){
            driver.navigate().back();
        } else if(isWeb()){
            log.info("This method do nothing for web application");
        }
    }

    //this.waitForElementAndClick(savedBtn, "Cannot find saved button", 10);

    public void clickSavedList() {
        this.waitForElementAndClick(savedList, "Cannot find", 5);
    }
}
