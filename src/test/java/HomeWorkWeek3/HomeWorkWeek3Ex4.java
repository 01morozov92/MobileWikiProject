package HomeWorkWeek3;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HomeWorkWeek3Ex4 {

    private AppiumDriver<MobileElement> driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("udid", "emulator-5554");
        capabilities.setCapability("avd", "Pixel_3a_API_30_x86");
        capabilities.setCapability("platformVersion", "11.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("adbExecTimeout", "200000");
        capabilities.setCapability(MobileCapabilityType.LOCALE, "RU");
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "ru");
        capabilities.setCapability("app", "C:\\Users\\MI\\IdeaProjects\\MobileWikiAppiumProject\\apks\\org.wikipedia.apk");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tierDown() {
        driver.quit();
    }

    @Test
    public void checkAllSearchResults() {
        String searchingText = "Java";

        MobileElement skipBtn = waitForElementAndClick(By.xpath("//*[@text='ПРОПУСТИТЬ']"),
                "Cannot click skip button");
        MobileElement searchField = waitForElementAndClick(By.xpath("//*[contains(@text, 'Поиск')]"),
                "Cannot click search input");
        MobileElement selectedSearchField = waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), "Java",
                "Cannot find search field");
        Assert.assertTrue("Cannot find search results", waitForElementPresent(By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find search results").isDisplayed());
        List<MobileElement> searchResult = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for(MobileElement i : searchResult){
            String elementText = i.getText();
            Assert.assertTrue(String.format(
                    "Cannot find text in element %s\nExpected partial text: %s\nActual result: %s", i.toString(), searchingText, elementText),
                    elementText.contains(searchingText));
        }
    }

    private MobileElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        MobileElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }//driver.findElement(By.xpath("//*[contains(@text, 'Java')]//*div[2]//preceding-sibling::[1]"));

    private MobileElement waitForElementAndClick(By by, String errorMessage) {
        MobileElement element = waitForElementPresent(by, errorMessage, 5);
        element.click();
        return element;
    }

    private MobileElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private MobileElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private MobileElement waitForElementAndSendKeys(By by, String value, String errorMessage) {
        MobileElement element = waitForElementPresent(by, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }
}
