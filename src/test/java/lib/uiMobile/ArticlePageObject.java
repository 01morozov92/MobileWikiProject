package lib.uiMobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static lib.Platform.*;

public class ArticlePageObject extends MainPageObject {

    @FindBy(id = "ca-watch")
    @AndroidFindBy(id = "org.wikipedia:id/article_menu_bookmark")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Сохранить на потом']")
    WebElement bookmarkBtn;

    @AndroidFindBy(xpath = "//*[@text='ДОБАВИТЬ В СПИСОК']")
    @iOSXCUITFindBy(id = "Empty")
    WebElement addToListBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='places auth close']")
    WebElement closeNotify;

    @AndroidFindBy(id = "org.wikipedia:id/text_input")
    @iOSXCUITFindBy(id = "Empty")
    WebElement inputField;

    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(id = "Empty")
    WebElement okBtn;

    @AndroidFindBy(xpath = "//*[@text='ПРОСМОТР СПИСКА']")
    @iOSXCUITFindBy(id = "Empty")
    WebElement viewMyList;

    @AndroidFindBy(xpath = "//*[@text='Сохранено']")
    @iOSXCUITFindBy(id = "Сохранено")
    WebElement savedBtn;

    @FindBy(id = "ca-watch")
    WebElement watchLaterBtn;

    @FindBy(xpath = "//a[text()='Войти'] | //button[text()='Войти']")
    WebElement logInBtn;

    @FindBy(xpath = "//input[@id='wpName1']")
    WebElement loginField;

    @FindBy(xpath = "//input[@id='wpPassword1']")
    WebElement passwordField;


    @FindBy(xpath = "//label[@title='Открыть главное меню']")
    WebElement burgerMenu;

    @FindBy(xpath = "//*[text()='Список наблюдения']")
    WebElement watchListBtn;

    @AndroidFindBy(id = "org.wikipedia:id/item_title")
    @iOSXCUITFindBy(id = "Empty")
    List<WebElement> listOfMyArticles;


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    NavigationUI navigationUI = new NavigationUI(driver);

    public void addArticleToNewMyList(String nameOfFolder) {
        if (isIOS()) {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark element", 10);
            this.waitForElementAndClick(closeNotify, "Cannot find close notification element", 10);
        } else if (isAndroid()) {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark element", 10);
            this.waitForElementAndClick(addToListBtn, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
            this.waitForElementAndClick(inputField, "Cannot find text field for list name", 10);
            this.waitForElementAndSendKeys(inputField, nameOfFolder, "Cannot find text field for list name", 10);
            this.waitForElementAndClick(okBtn, "Cannot find 'OK'", 10);
            this.waitForElementAndClick(viewMyList, "Cannot find 'ПРОСМОТР СПИСКА'", 10);
        } else if (isWeb()) {
            waitForElementAndClick(bookmarkBtn, "Cannot find save article button", 20);
            singInFromArticle();
        }
    }

    public void saveArticle() {
//        sleep(2000);
        waitForElementAndClick(bookmarkBtn, "Cannot find save article button", 20);
    }

    public void singInFromArticle() {
        waitForElementAndClick(logInBtn, "cannot find log in button");
        waitForElementAndSendKeys(loginField, "ilya.moroz", "Cannot find log in field");
        waitForElementAndSendKeys(passwordField, "123ion123", "Cannot find password field");
        waitForElementAndClick(logInBtn, "cannot find log in button");
    }

    public String getTitleOfArticle(String titleOfArticle) {
        if (isWeb()) {
            return driver.getTitle();
        } else {
            return this.waitForElementPresent((getElementByText(titleOfArticle, allElements)), String.format("Cannot find element with text %s", titleOfArticle), 10).getText();
        }
    }

    public void addArticleToMyList(String nameOfFolder) {
        if (isIOS()) {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark button", 10);
        } else if (isAndroid()) {
            this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark button", 10);
            this.waitForElementAndClick(addToListBtn, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
            this.waitForElementAndClick(getElementByText(nameOfFolder, listOfMyArticles), "Cannot find text field for list: " + nameOfFolder, 10);
        } else if (isWeb()) {
            waitForElementAndClick(watchLaterBtn, "Cannot find save article button", 20);
        }
    }

    public void getIntoMySavedLists(String nameOfFolder) {
        if (isIOS()) {
            this.waitForElementAndClick(savedBtn, "Cannot find saved button", 10);
        } else if (isAndroid()) {
            navigationUI.clickBack();
            navigationUI.clickBack();
            this.waitForElementAndClick(savedBtn, "Cannot find saved button", 10);
            this.waitForElementAndClick(getElementByText(nameOfFolder, listOfMyArticles), "Cannot find text field for list name", 10);
        } else if (isWeb()) {
            goToWatchListThoughtBurgerMenu();
        }
    }

    public void goToWatchListThoughtBurgerMenu() {
        sleep(1000);
        waitForElementAndClick(burgerMenu, "Cannot find burger menu");
        waitForElementAndClick(watchListBtn, "Cannot find watch list button");
    }
}