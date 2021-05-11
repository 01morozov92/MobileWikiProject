package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class ArticlePageObject extends MainPageObject{

    private static final String
    BOOKMARK_BNT = "id:org.wikipedia:id/article_menu_bookmark",
    ADD_TO_LIST_BTN = "xpath://*[@text='ДОБАВИТЬ В СПИСОК']",
    INPUT_FIELD = "id:org.wikipedia:id/text_input",
    OK_BTN = "id:android:id/button1",
    CHOOSE_LIST_BY_NAME_TPL = "xpath://*[@text='{NAME}']",
    VIEW_MY_LSIT = "xpath://*[@text='ПРОСМОТР СПИСКА']",
    TITLE_OF_ARTICLE_BY_TPL = "xpath://*[@class='android.webkit.WebView'][@text='{TITLE}']";

    public ArticlePageObject(AppiumDriver<MobileElement> driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getNameOfList(String substring){
        return CHOOSE_LIST_BY_NAME_TPL.replace("{NAME}", substring);
    }

    private static String getTitleOfArticleByTpl(String title){
        return TITLE_OF_ARTICLE_BY_TPL.replace("{TITLE}", title);
    }
    /* TEMPLATES METHODS */

    public void addArticleToNewMyList(String nameOfFolder){
        this.waitForElementAndClick(BOOKMARK_BNT, "Cannot find bookmark element", 10);
        this.waitForElementAndClick(ADD_TO_LIST_BTN, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
        this.waitForElementAndClick(INPUT_FIELD, "Cannot find text field for list name", 10);
        this.waitForElementAndSendKeys(INPUT_FIELD, nameOfFolder, "Cannot find text field for list name", 10);
        this.waitForElementAndClick(OK_BTN, "Cannot find 'OK'", 10);
        this.waitForElementAndClick(VIEW_MY_LSIT, "Cannot find 'ПРОСМОТР СПИСКА'", 10);
    }

    public String getTitleOfArticle(String titleOfArticle){
        return this.waitForElementPresent((getTitleOfArticleByTpl(titleOfArticle)), String.format("Cannot find element with text %s", titleOfArticle), 10).getText();
    }

    public void chooseList(String nameOfList){
        this.waitForElementAndClick((getNameOfList(nameOfList)), "Cannot find and click article by name");
    }

    public void addArticleToMyList(String nameOfFolder){
        this.waitForElementAndClick(BOOKMARK_BNT, "Cannot find bookmark element", 10);
        this.waitForElementAndClick(ADD_TO_LIST_BTN, "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
        this.waitForElementAndClick(getNameOfList(nameOfFolder), "Cannot find text field for list name", 10);
        this.waitForElementAndClick(VIEW_MY_LSIT, "Cannot find 'ПРОСМОТР СПИСКА'", 10);
    }



}
