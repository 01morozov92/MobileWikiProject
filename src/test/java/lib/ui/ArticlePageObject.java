package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ArticlePageObject extends MainPageObject{

    @AndroidFindBy(id = "org.wikipedia:id/article_menu_bookmark")
    @iOSXCUITFindBy(id = "//*")
    MobileElement bookmarkBtn;

    @AndroidFindBy(xpath = "//*[@text='ДОБАВИТЬ В СПИСОК']")
    @iOSXCUITFindBy(id = "//*")
    MobileElement addToListBtn;

    @AndroidFindBy(id = "org.wikipedia:id/text_input")
    @iOSXCUITFindBy(id = "//*")
    MobileElement inputField;

    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(id = "//*")
    MobileElement okBtn;

    @AndroidFindBy(xpath = "//*[@text='ПРОСМОТР СПИСКА']")
    @iOSXCUITFindBy(id = "//*")
    MobileElement viewMyList;


    public ArticlePageObject(AppiumDriver<MobileElement> driver){
        super(driver);
    }

    public void addArticleToNewMyList(String nameOfFolder){
        this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark element", 10);
        this.waitForElementAndClick(addToListBtn, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
        this.waitForElementAndClick(inputField, "Cannot find text field for list name", 10);
        this.waitForElementAndSendKeys(inputField, nameOfFolder, "Cannot find text field for list name", 10);
        this.waitForElementAndClick(okBtn, "Cannot find 'OK'", 10);
        this.waitForElementAndClick(viewMyList, "Cannot find 'ПРОСМОТР СПИСКА'", 10);
    }

    public String getTitleOfArticle(String titleOfArticle){
        return this.waitForElementPresent((getElementByText(titleOfArticle, allElements)), String.format("Cannot find element with text %s", titleOfArticle), 10).getText();
    }

    public void chooseList(String nameOfList){
        this.waitForElementAndClick((getElementByText(nameOfList, allElements)), "Cannot find and click article by name");
    }

    public void addArticleToMyList(String nameOfFolder){
        this.waitForElementAndClick(bookmarkBtn, "Cannot find bookmark element", 10);
        this.waitForElementAndClick(addToListBtn, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
        this.waitForElementAndClick(getElementByText(nameOfFolder, allElements), "Cannot find text field for list name", 10);
        this.waitForElementAndClick(viewMyList, "Cannot find 'ПРОСМОТР СПИСКА'", 10);
    }
}