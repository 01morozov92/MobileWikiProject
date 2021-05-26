package lib.uiMobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    @AndroidFindBy(xpath = "//*[@text='ПРОПУСТИТЬ']")
    @iOSXCUITFindBy(id = "Пропустить")
    MobileElement skipBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Узнать подробнее о Википедии")
    MobileElement learnMoreBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Новые способы изучения")
    MobileElement newWaysToLearn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Искать на почти 300 языках")
    MobileElement findOn300Language;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Помогите сделать это приложение лучше")
    MobileElement helpToImproveApp;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Далее")
    MobileElement nextBtn;

    @AndroidFindBy(xpath = "Empty")
    @iOSXCUITFindBy(id = "Начать")
    MobileElement getStartedBtn;


    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(learnMoreBtn, "Cannot find 'Узнать подробнее о Википедии' text", 10);
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(newWaysToLearn, "Cannot find 'Новые способы изучения' text", 10);
    }

    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(findOn300Language, "Cannot find 'Искать на почти 300 языках' text", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(helpToImproveApp, "Cannot find 'Помогите сделать это приложение лучше' text", 10);
    }


    public void clickNextButton() {
        this.waitForElementAndClick(nextBtn, "Cannot find and click 'Next' text", 10);
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(getStartedBtn, "Cannot find and click 'Get started' text", 10);
    }

    public void clickSkip() {
        waitForElementAndClick(skipBtn, "Cannot find and click skip button", 5);
    }

    public void skipWelcomePage() {
        this.waitForElementAndClick(skipBtn, "Cannot find skip button", 5);
    }
}

