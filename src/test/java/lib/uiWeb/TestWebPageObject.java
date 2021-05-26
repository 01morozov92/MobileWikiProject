package lib.uiWeb;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.fail;


@Log4j2
public class TestWebPageObject extends MainPageObjectWeb {

    @FindBy(id = "searchIcon")
    WebElement searchResultByDescription;

    @FindBy(xpath = "//input[@class='search mw-ui-background-icon-search']")
    WebElement searchInput;

    @FindBy(xpath = "//h3[text()]")
    List<WebElement> articlesInWatchList;

    @FindBy(className = "page-summary")
    List<WebElement> allSearchResults;

    @FindBy(id = "ca-watch")
    WebElement watchLaterBtn;

    @FindBy(xpath = "//a[text()='Log in'] | //button[text()='Log in']")
    WebElement logInBtn;

    @FindBy(xpath = "//input[@id='wpName1']")
    WebElement loginField;

    @FindBy(xpath = "//input[@id='wpPassword1']")
    WebElement passwordField;

    @FindBy(xpath = "//label[@title='Open main menu']")
    WebElement burgerMenu;

    @FindBy(xpath = "//*[text()='Watchlist']")
    WebElement watchListBtn;

    @FindBy(css = ".watch-this-article")
    List<WebElement> allStarsBtns;

    @FindBy(xpath = "//*[text()='AppImage']")
    WebElement AppImageArticle;



    public TestWebPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void inputText(String text) {
        this.waitForElementAndSendKeys(searchInput, text, "Cannot find search input field");
    }

    public void clickSearch() {
        this.waitForElementAndClick(searchResultByDescription, "", 10);
    }

    public void chooseArticleByText(String title) {
        getElementByPartialText(title, allSearchResults).click();
    }

    public void saveArticle() {
//        sleep(2000);
        waitForElementAndClick(watchLaterBtn, "Cannot find save article button", 20);
    }

    public void singInFromArticle() {
        waitForElementAndClick(logInBtn, "cannot find log in button");
//        sleep(2000);
//        waitForElementAndClick(loginField, "cannot find log in field");
        waitForElementAndSendKeys(loginField, "ilya.moroz", "Cannot find log in field");
        waitForElementAndSendKeys(passwordField, "123ion123", "Cannot find password field");
        waitForElementAndClick(logInBtn, "cannot find log in button");
    }

    public void goToWatchListThoughtBurgerMenu() {
        waitForElementAndClick(burgerMenu, "Cannot find burger menu");
        waitForElementAndClick(watchListBtn, "Cannot find watch list button");
    }

    public void deleteArticleByText(String text) {
        List<WebElement> listOfStars = waitForListOfElementsPresent(allStarsBtns, "Cannot find list", 10);

        List<WebElement> successfulList = listOfStars.stream().filter(element -> element.getAttribute("href").toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
        if(!(successfulList.size() >0)){
            fail("Cannot find article to delete");
        } else {
//            sleep(2000);
            successfulList.forEach(e -> waitForElementAndClick(e, "Cannot find star button"));
        }
        driver.navigate().refresh();
    }

    public boolean checkArticles(){
        return waitForElementNotPresent(getElementByPartialText("AppImage", allElements), "Element is still present", 10);
    }

    public void chooseArticleByTextFromWatchList(String title) {
        getElementByPartialText(title, articlesInWatchList).click();
        System.out.println();
    }
}
