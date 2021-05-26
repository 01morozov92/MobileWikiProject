package HomeWorks.HomeWorkWeek3;

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

import static org.junit.Assert.fail;

public class HomeWorkWeek3Ex2 {

    private AppiumDriver<MobileElement> driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
//        capabilities.setCapability("udid", "emulator-5554");
//        capabilities.setCapability("avd", "Pixel_3a_API_30_x86");
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
    public void checkSearchFieldText(){
        MobileElement skipBtn = waitForElementAndClick(By.xpath("//*[@text='ПРОПУСТИТЬ']"),
                "Cannot click skip button", 5);
        assertElementHasText(By.xpath("//*[contains(@text, 'Поиск')]"), "Поиск по Википедии", "The element does not contain the expected text");
    }

    @Test
    public void checkSearchFieldPartialText(){
        MobileElement skipBtn = waitForElementAndClick(By.xpath("//*[@text='ПРОПУСТИТЬ']"),
                "Cannot click skip button", 5);
        assertElementHasPartialText(By.xpath("//*[contains(@text, 'Поиск')]"), "Поиск", "The element does not contain the expected text");
    }

    private MobileElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds){
        MobileElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private MobileElement waitForElementAndClick(By by, String errorMessage){
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

    private void assertElementHasText(By by, String text, String errorMessage){
        String textOfElement = waitForElementPresent(by, errorMessage, 5).getText();
        Assert.assertEquals(errorMessage, text, textOfElement);
    }

    private void assertElementHasPartialText(By by, String text, String errorMessage){
        String textOfElement = waitForElementPresent(by, errorMessage, 5).getText();
        if(!textOfElement.contains(text)){
            fail(String.format("%s\nExpected partial text: %s\nActual result: %s", errorMessage, text, textOfElement));
        }
    }
}
