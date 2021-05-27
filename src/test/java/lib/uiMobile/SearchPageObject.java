package lib.uiMobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.stream.Collectors;

import static lib.Platform.*;


@Log4j2
public class SearchPageObject extends MainPageObject {

    @FindBy(className = "page-summary")
    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_title")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText")
    List<WebElement> searchResultsByTitle;

    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_title")
    @iOSXCUITFindBy(id = "Empty")
    WebElement searchResultByTitle;

    @FindBy(id = "searchIcon")
    @AndroidFindBy(xpath = "//*[contains(@text, 'Поиск')]")
    @iOSXCUITFindBy(id = "Поиск по Википедии")
    WebElement searchInitElement;

    @FindBy(xpath = "//input[@class='search mw-ui-background-icon-search']")
    @AndroidFindBy(xpath = "//*[@resource-id='org.wikipedia:id/search_src_text']")
    @iOSXCUITFindBy(xpath = "//*[@name='Википедия']")
    WebElement searchInput;

    @FindBy(xpath = "//*[contains(@class, 'page-summary')]")
    @AndroidFindBy(xpath = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@class='android.view.ViewGroup']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCollectionView/XCUIElementTypeCell")
    List<WebElement> searchResults;

    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_description")
    @iOSXCUITFindBy(id = "Empty")
    WebElement searchResultByDescription;

    @AndroidFindBy(id = "org.wikipedia:id/search_close_btn")
    @iOSXCUITFindBy(id = "clear mini")
    WebElement closeSearchBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Сохранено")
    WebElement savedBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "clear mini")
    WebElement clearSearchInput;

    @FindBy(xpath = "//h3[text()]")
    List<WebElement> titleWeb;

    @FindBy(xpath = "wikidata-description")
    List<WebElement> descriptionWeb;


//    $$$$$$$

    @FindBy(className = "page-summary")
    List<WebElement> allSearchResults;

    @FindBy(xpath = "//*[text()='AppImage']")
    WebElement AppImageArticle;
//    $$$$$$$

    private static final String
            ARTICLE_TITLE = "org.wikipedia:id/page_list_item_title",
            ARTICLE_DESCRIPTION = "org.wikipedia:id/page_list_item_description",
            ARTICLE_TITLE_IOS = "//XCUIElementTypeStaticText[1]",
            ARTICLE_DESCRIPTION_IOS = "//XCUIElementTypeStaticText[2]",
            ARTICLE_TITLE_WEB = "a/h3",
            ARTICLE_DESCRIPTION_WEB = "a/div[@class='wikidata-description']";

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Ищем статьи по заданному заголовку: {0} и описанию: {1}")
    public WebElement waitForElementByTitleAndDescription(String title, String description) {
        if (isIOS()) {
            for (int i = 0; i < searchResults.size() - 1; i++) {
                String articleTitle = waitForElementPresent(searchResults.get(i).findElement(By.xpath(ARTICLE_TITLE_IOS)), "Cannot find element title", 10).getText();
                log.info(articleTitle);
                String articleDescription = waitForElementPresent(searchResults.get(i).findElement((By.xpath(ARTICLE_DESCRIPTION_IOS))), "Cannot find element description", 10).getText();
                log.info(articleDescription);
                if (articleTitle.equalsIgnoreCase(title) && articleDescription.equalsIgnoreCase(description)) {
                    return this.waitForElementPresent(searchResults.get(i), String.format("Cannot find element with title: %s and with description: %s", title, description), 10);
                }
            }
        } else if(isAndroid()) {
            for (int i = 0; i < searchResults.size() - 1; i++) {
                String articleTitle = waitForElementPresent(searchResults.get(i).findElement(By.id(ARTICLE_TITLE)), "Cannot find element title", 10).getText();
                String articleDescription = waitForElementPresent(searchResults.get(i).findElement((By.id(ARTICLE_DESCRIPTION))), "Cannot find element description", 10).getText();
                if (articleTitle.equals(title) && articleDescription.equals(description)) {
                    return this.waitForElementPresent(searchResults.get(i), String.format("Cannot find element with title: %s and with description: %s", title, description), 10);
                }
            }
        } else if(isWeb()){
            for (int i = 0; i < searchResults.size() - 1; i++) {
                String articleTitle = waitForElementPresent(searchResults.get(i).findElement(By.xpath(ARTICLE_TITLE_WEB)), "Cannot find element title", 10).getText();
                log.info(articleTitle);
                String articleDescription = waitForElementPresent(searchResults.get(i).findElement((By.xpath(ARTICLE_DESCRIPTION_WEB))), "Cannot find element description", 10).getText();
                log.info(articleDescription);
                if (articleTitle.equals(title) && articleDescription.equals(description)) {
                    return this.waitForElementPresent(searchResults.get(i), String.format("Cannot find element with title: %s and with description: %s", title, description), 10);
                }
            }
        }
        throw new Error(String.format("Cannot find element with title: %s and with description: %s", title, description));
    }

    public boolean checkVisionOfALlSearchResults() {
        return this.waitForElementPresent(searchResultByDescription,
                "Cannot find search results").isDisplayed();
    }

    public List<WebElement> getListOfArticles(String searchPhrase) {
        List<WebElement> list;
        try {
            list = searchResultsByTitle.stream().filter(element -> element.getText().equals(searchPhrase)).collect(Collectors.toList());
        } catch (NullPointerException nex) {
            nex.printStackTrace();
            log.info("List is empty");
            return searchResultsByTitle;
        }
        return list;
    }

    public void closeSearchField() {
        this.waitForElementAndClick(closeSearchBtn, "cannot find close button");
    }

    public void initSearchInput() {
        this.waitForElementAndClick(searchInitElement,
                "Cannot find and click init search element", 5);
    }

    public void typeSearchLine(String searchLine) {
        if (isIOS()) {
            initSearchInput();
            if (exist(closeSearchBtn)) {
                this.waitForElementAndClick(closeSearchBtn, "Cannot find close search button", 5);
            }
            this.waitForElementAndSendKeys(searchInput, searchLine, "Cannot find search input field", 5);
        } else if (isAndroid()) {
            if (exist(closeSearchBtn)) {
                this.waitForElementAndSendKeys(searchInput, searchLine, "Cannot find search input field", 5);
            } else {
                initSearchInput();
                this.waitForElementAndSendKeys(searchInput, searchLine, "Cannot find search input field", 5);
            }
        } else if (isWeb()) {
            initSearchInput();
            this.waitForElementAndSendKeys(searchInput, searchLine, "Cannot find search input field", 5);
        }
    }

    public void waitForSearchResultAndClick(String substring) {
        if (isWeb()) {
            this.waitForElementAndClick(getElementByPartialText(substring, searchResultsByTitle), "Cannot find and click search result with substring " + substring, 5);
        } else {
            this.waitForElementAndClick(getElementByText(substring, searchResultsByTitle), "Cannot find and click search result with substring " + substring, 5);
        }
    }

    public boolean waitForSearchResult(String substring) {
        return this.waitForElementNotPresent(searchResultByTitle, String.format("Cannot find search result by title: %s", substring), 10);
    }

    //    $$$$$$$
    public void inputText(String text) {
        this.waitForElementAndSendKeys(searchInput, text, "Cannot find search input field");
    }


    public void clickSearch() {
        this.waitForElementAndClick(searchInitElement, "", 10);
    }

    public void chooseArticleByText(String title) {
        getElementByPartialText(title, allSearchResults).click();
    }

    public boolean checkArticles() {
        return waitForElementNotPresent(getElementByPartialText("AppImage", allElements), "Element is still present", 10);
    }

    //    $$$$$$$
}

