package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SKIP_BTN = "//*[@text='ПРОПУСТИТЬ']",
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Поиск')]",
            SEARCH_INPUT = "//*[@resource-id='org.wikipedia:id/search_src_text']",
            FIRST_ELEMENT_IN_SEARCH_RESULT = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]",
            LIST_OF_SEARCH_RESULTS = "//*[@class='android.view.ViewGroup']//*[contains(@text, '{SEARCH_PHRASE}')]",
            SEARCH_CLOSE_BTN = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='{SUBSTRING}']";//язык программирования

    public SearchPageObject(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getAllArticlesBySearchPhrase(String searchPhrase){
        return LIST_OF_SEARCH_RESULTS.replace("{SEARCH_PHRASE}", searchPhrase);
    }

    private static String getAllSearchResults(){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("//*[@text='{SUBSTRING}']", "");
    }
    /* TEMPLATES METHODS */

    public boolean checkVisionOfALlSearchResults(){
        return this.waitForElementPresent(By.xpath(getAllSearchResults()),
                "Cannot find search results").isDisplayed();
    }

    public List<MobileElement> getListOfArticles(String searchPhrase){
        return driver.findElements(By.xpath(getAllArticlesBySearchPhrase(searchPhrase)));
    }

    public void closeSearchField(){
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BTN), "cannot find close button");
    }

    public void skipWelcomePage() {
        this.waitForElementAndClick(By.xpath(SKIP_BTN), "Cannot find skip button", 5);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click init search element", 5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Cannot find search input field", 5);
    }

    public void waitForSearchResultAndClick(String substring){
        this.waitForElementAndClick(By.xpath(getResultSearchElement(substring)), "Cannot find and click search result with substring " + substring, 5);
    }

    public boolean waitForSearchResult(String substring){
       return this.waitForElementNotPresent(By.xpath(getResultSearchElement(substring)), "Cannot find and click search result with substring " + substring, 5);
    }
}