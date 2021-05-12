package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPageObject extends MainPageObject {

    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_title")
    @iOSXCUITFindBy(id = "//*")
    List<MobileElement> searchResultsByTitle;

    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_title")
    @iOSXCUITFindBy(id = "//*")
    MobileElement searchResultByTitle;

    @AndroidFindBy(xpath = "//*[contains(@text, 'Поиск')]")
    @iOSXCUITFindBy(id = "//*")
    MobileElement searchInitElement;

    @AndroidFindBy(xpath = "//*[@resource-id='org.wikipedia:id/search_src_text']")
    @iOSXCUITFindBy(id = "//*")
    MobileElement searchInput;

    @AndroidFindBy(xpath = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@class='android.view.ViewGroup']")
    @iOSXCUITFindBy(id = "//*")
    List<MobileElement> searchResults;

    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_description")
    @iOSXCUITFindBy(id = "//*")
    MobileElement searchResultByDescription;

    @AndroidFindBy(id = "org.wikipedia:id/search_close_btn")
    @iOSXCUITFindBy(id = "//*")
    MobileElement closeSearchBtn;

    private static final String
            ARTICLE_TITLE = "org.wikipedia:id/page_list_item_title",
            ARTICLE_DESCRIPTION = "org.wikipedia:id/page_list_item_description";

    public SearchPageObject(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public MobileElement waitForElementByTitleAndDescription(String title, String description) {
        MobileElement elementByArticleAndDescription = null;
        for (int i = 0; i<searchResults.size()-1; i++) {
            String articleTitle = waitForElementPresent(searchResults.get(i).findElement(By.id(ARTICLE_TITLE)),"Cannot find element title", 10).getText();
            String articleDescription = waitForElementPresent(searchResults.get(i).findElement((By.id(ARTICLE_DESCRIPTION))), "Cannot find element description, 10").getText();
            if (articleTitle.equals(title) && articleDescription.equals(description)) {
                elementByArticleAndDescription = this.waitForElementPresent(searchResults.get(i), String.format("Cannot find element with title: %s and with description: %s", title, description), 10);
            }
        }
        return elementByArticleAndDescription;
    }

    public boolean checkVisionOfALlSearchResults() {
        return this.waitForElementPresent(searchResultByDescription,
                "Cannot find search results").isDisplayed();
    }

    public List<MobileElement> getListOfArticles(String searchPhrase) {
        List<MobileElement> list;
        try {
            list = searchResultsByTitle.stream().filter(element -> element.getText().equals(searchPhrase)).collect(Collectors.toList());
        } catch (NullPointerException nex) {
            nex.printStackTrace();
            System.out.println("List is empty");
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
        this.waitForElementAndSendKeys(searchInput, searchLine, "Cannot find search input field", 5);
    }

    public void waitForSearchResultAndClick(String substring) {
        this.waitForElementAndClick(getElementByText(substring, allElements), "Cannot find and click search result with substring " + substring, 5);
    }

    public boolean waitForSearchResult(String substring) {
        return this.waitForElementNotPresent(searchResultByTitle, String.format("Cannot find search result by title: %s", substring), 10);
    }
}

