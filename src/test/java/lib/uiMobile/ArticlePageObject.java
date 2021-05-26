package lib.uiMobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.remote.RemoteWebDriver;

import static lib.Platform.isIOS;

public class ArticlePageObject extends MainPageObject {

    @AndroidFindBy(id = "org.wikipedia:id/article_menu_bookmark")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Сохранить на потом']")
    MobileElement bookmarkBtn;

    @AndroidFindBy(xpath = "//*[@text='ДОБАВИТЬ В СПИСОК']")
    @iOSXCUITFindBy(id = "Empty")
    MobileElement addToListBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='places auth close']")
    MobileElement closeNotify;

    @AndroidFindBy(id = "org.wikipedia:id/text_input")
    @iOSXCUITFindBy(id = "Empty")
    MobileElement inputField;

    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(id = "Empty")
    MobileElement okBtn;

    @AndroidFindBy(xpath = "//*[@text='ПРОСМОТР СПИСКА']")
    @iOSXCUITFindBy(id = "Empty")
    MobileElement viewMyList;

    @AndroidFindBy(xpath = "//*[@text='Сохранено']")
    @iOSXCUITFindBy(id = "Сохранено")
    MobileElement savedBtn;


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    NavigationUI navigationUI = new NavigationUI((AppiumDriver<MobileElement>) driver);

    public void addArticleToNewMyList(String nameOfFolder) {
        if (isIOS()) {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark element", 10);
            this.waitForElementAndClick(closeNotify, "Cannot find close notification element", 10);
        } else {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark element", 10);
            this.waitForElementAndClick(addToListBtn, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
            this.waitForElementAndClick(inputField, "Cannot find text field for list name", 10);
            this.waitForElementAndSendKeys(inputField, nameOfFolder, "Cannot find text field for list name", 10);
            this.waitForElementAndClick(okBtn, "Cannot find 'OK'", 10);
            this.waitForElementAndClick(viewMyList, "Cannot find 'ПРОСМОТР СПИСКА'", 10);
        }
    }

    public String getTitleOfArticle(String titleOfArticle) {
        return this.waitForElementPresent((getElementByText(titleOfArticle, allElements)), String.format("Cannot find element with text %s", titleOfArticle), 10).getText();
    }

    public void addArticleToMyList(String nameOfFolder) {
        if (isIOS()) {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark button", 10);
        } else {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark button", 10);
            this.waitForElementAndClick(addToListBtn, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
            this.waitForElementAndClick(getElementByText(nameOfFolder, allElements), "Cannot find text field for list name", 10);
        }
    }

    public void getIntoMySavedLists(String nameOfFolder) {
        if (isIOS()) {
            this.waitForElementAndClick(savedBtn, "Cannot find saved button", 10);
        } else {
            navigationUI.clickBack();
            sleep(1000);
            navigationUI.clickBack();
            this.waitForElementAndClick(savedBtn, "Cannot find saved button", 10);
            this.waitForElementAndClick(getElementByText(nameOfFolder, allElements), "Cannot find text field for list name", 10);
        }
    }
}