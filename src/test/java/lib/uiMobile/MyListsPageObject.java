package lib.uiMobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static lib.Platform.isIOS;
import static lib.Platform.isWeb;
import static org.testng.Assert.fail;

public class MyListsPageObject extends MainPageObject {

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='swipe action delete']")
    public WebElement delete;

    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_title")
    @iOSXCUITFindBy(xpath = "Empty")
    public List<WebElement> listsOfMyArticlesInWatchList;

    @FindBy(css = ".watch-this-article")
    List<WebElement> allStarsBtns;


    @FindBy(xpath = "//h3[text()]")
    List<WebElement> articlesInWatchList;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void swipeArticleToDelete(String articleTitle) {
        this.swipe(waitOptions(Duration.ofMillis(2000)), Direction.влево, getElementByPartialText(articleTitle, allElements));
        if (isIOS()) {
            this.waitForElementAndClick(delete, "Cannot find delete element");
        }
    }

    public void deleteArticle(String articleTitle) {
        if (isWeb()) {
            deleteArticleByText(articleTitle);
        } else {
            swipeArticleToDelete(articleTitle);
        }
    }

    public void chooseList(String nameOfList) {
        if (isWeb()) {
            this.waitForElementAndClick((getElementByPartialText(nameOfList, articlesInWatchList)), "Cannot find and click article by: " + nameOfList);
        } else {
            this.waitForElementAndClick((getElementByText(nameOfList, listsOfMyArticlesInWatchList)), "Cannot find and click article by: " + nameOfList);
        }
    }

    public void deleteArticleByText(String text) {
        List<WebElement> listOfStars = waitForListOfElementsPresent(allStarsBtns, "Cannot find list", 10);

        List<WebElement> successfulList = listOfStars.stream().filter(element -> element.getAttribute("href").toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
        if (!(successfulList.size() > 0)) {
            fail("Cannot find article to delete");
        } else {
//            sleep(2000);
            successfulList.forEach(e -> waitForElementAndClick(e, "Cannot find star button"));
        }
        driver.navigate().refresh();
    }

    public void chooseArticleByTextFromWatchList(String title) {
        getElementByPartialText(title, articlesInWatchList).click();
    }
}
