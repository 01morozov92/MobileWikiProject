package HomeWorkWeek4;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.fail;

public class Ex6 extends Ex5{

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
        capabilities.setCapability("uiautomator2ServerInstallTimeout", "200000");
        capabilities.setCapability(MobileCapabilityType.LOCALE, "RU");
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "ru");
        capabilities.setCapability("app", "C:\\Users\\MI\\IdeaProjects\\MobileWikiAppiumProject\\apks\\org.wikipedia.apk");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void tierDown() {
        driver.quit();
    }

    @Test
    public void deleteArticleBySwipeTest() {
        waitForElementAndClick(By.xpath("//*[@text='ПРОПУСТИТЬ']"), "Cannot click skip button", 5);
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Поиск')]"), "Cannot click search input", 5);
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), "Java", "Cannot find search field");
        waitForElementAndClick(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]"), "Cannot find first element in result list ", 15);
        assertElementPresent("Java", By.xpath("//*[@text='Java']"));
    }

    public void assertElementPresent(String expectedResult, By by){
        String expectedTitle = "Java";
        String actualTitle = driver.findElement(by).getText();
        Assert.assertEquals(String.format("Article title: %s does not match with expected title: %s"), expectedTitle, actualTitle);
    }

    private void waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                MobileElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                System.out.println(String.format("Failed to click on element %s due to the StaleElementReferenceException", by));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", by));
                }
            }
        } while (!successfulClick);
    }

    private void waitForElementAndClick(By by, String errorMessage) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                MobileElement element = waitForElementPresent(by, errorMessage, 5);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                System.out.println(String.format("Failed to click on element %s due to the StaleElementReferenceException", by));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", by));
                }
            }
        } while (!successfulClick);
    }

    private MobileElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private MobileElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private MobileElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        MobileElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private MobileElement waitForElementAndSendKeys(By by, String value, String errorMessage) {
        MobileElement element = waitForElementPresent(by, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }
}
