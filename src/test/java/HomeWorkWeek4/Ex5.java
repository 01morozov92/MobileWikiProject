package HomeWorkWeek4;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static org.junit.Assert.fail;

public class Ex5 {

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
    public void firstTest() {
        String nameOfArticle = "Java";
        String textOfTitle = "Java";

        waitForElementAndClick(By.xpath("//*[@text='ПРОПУСТИТЬ']"), "Cannot click skip button", 5);
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Поиск')]"), "Cannot click search input", 5);
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), "Java", "Cannot find search field");
        waitForElementAndClick(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]"), "Cannot find element", 15);

        waitForElementAndClick(By.id("org.wikipedia:id/article_menu_bookmark"), "Cannot find bookmark element", 10);
        waitForElementAndClick(By.xpath("//*[@text='ДОБАВИТЬ В СПИСОК']"), "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
        waitForElementAndClick(By.id("org.wikipedia:id/text_input"), "Cannot find text field for list name", 10);
        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"), "Мой список", "Cannot find text field for list name", 10);
        waitForElementAndClick(By.id("android:id/button1"), "Cannot find 'OK'", 10);
        waitForElementAndClick(By.xpath("//*[@text='ПРОСМОТР СПИСКА']"), "Cannot find 'ПРОСМОТР СПИСКА'", 10);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Перейти вверх']"), "Cannot find back button", 10);
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Перейти вверх']"), "Cannot find back button", 10);

        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), "Appium", "Cannot find search field");
        waitForElementAndClick(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]"), "Cannot find element", 10);
        waitForElementAndClick(By.id("org.wikipedia:id/article_menu_bookmark"), "Cannot find bookmark element", 5);
        waitForElementAndClick(By.xpath("//*[@text='ДОБАВИТЬ В СПИСОК']"), "Cannot find 'ДОБАВИТЬ В СПИСОК'", 10);
        waitForElementAndClick(By.xpath("//*[@text='Мой список']"), "Cannot find 'Мой список'", 10);
        waitForElementAndClick(By.xpath("//*[@text='ПРОСМОТР СПИСКА']"), "Cannot find 'ПРОСМОТР СПИСКА'", 10);

        swipe(waitOptions(Duration.ofMillis(500)), Direction.вправо, By.xpath("//*[@text='AppImage']"));;
        waitForElementAndClick(By.xpath("//*[@text='" + nameOfArticle + "']"), "Cannot find element with text 'Java'");
        String expectedResult = "Java";
        String actualResult = waitForElementPresent(By.xpath("//*[@class='android.webkit.WebView'][@text='" + textOfTitle + "']"), "Cannot find element with text 'Java'", 10).getText();
        Assert.assertEquals(expectedResult, actualResult);
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

    protected void swipe(WaitOptions timeOfSwipe, Direction direction) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int startY;
        int startX;
        int endX;
        int endY;
        int x = size.width / 2;
        int y = size.height / 2;
        switch (direction) {
            case вверх:
                startY = (int) (size.height * 0.8);
                endY = (int) (size.height * 0.2);
                action.press(PointOption.point(x, startY)).waitAction(timeOfSwipe).moveTo(ElementOption.point(x, endY)).release().perform();
                break;
            case вниз:
                startY = (int) (size.height * 0.2);
                endY = (int) (size.height * 0.8);
                action.press(PointOption.point(x, startY)).waitAction(timeOfSwipe).moveTo(ElementOption.point(x, endY)).release().perform();
                break;
            case влево:
                startX = (int) (size.width * 0.8);
                endX = (int) (size.width * 0.2);
                action.press(PointOption.point(startX, y)).waitAction(timeOfSwipe).moveTo(ElementOption.point(endX, y)).release().perform();
                break;
            case вправо:
                startX = (int) (size.width * 0.2);
                endX = (int) (size.width * 0.8);
                action.press(PointOption.point(startX, y)).waitAction(timeOfSwipe).moveTo(ElementOption.point(endX, y)).release().perform();
                break;

        }
    }

    protected void swipe(WaitOptions timeOfSwipe, Direction direction, By element) {
        TouchAction action = new TouchAction(driver);
        int numberOfTry = 0;
        boolean successfulSwipe = false;
        do {
            try {
                MobileElement elementForSwipe = waitForElementPresent(element, "Cannot find element for swipe", 20);
                Dimension sizeOfElement = elementForSwipe.getSize();

                int startY;
                int startX;
                int endX;
                int endY;
                int x = elementForSwipe.getLocation().x;
                int y = elementForSwipe.getLocation().y;
                switch (direction) {
                    case вверх:
                        startY = (int) (sizeOfElement.height * 0.1);
                        endY = (int) (sizeOfElement.height * 0.9);
                        action.press(PointOption.point(x, startY)).waitAction(timeOfSwipe).moveTo(ElementOption.point(x, endY)).release().perform();
                        successfulSwipe = true;
                        break;
                    case вниз:
                        startY = (int) (sizeOfElement.height * 0.9);
                        endY = (int) (sizeOfElement.height * 0.1);
                        action.press(PointOption.point(x, startY)).waitAction(timeOfSwipe).moveTo(ElementOption.point(x, endY)).release().perform();
                        successfulSwipe = true;
                        break;
                    case влево:
                        startX = (int) (sizeOfElement.width * 0.9);
                        endX = (int) (sizeOfElement.width * 0.1);
                        action.press(PointOption.point(startX, y)).waitAction(timeOfSwipe).moveTo(ElementOption.point(endX, y)).release().perform();
                        successfulSwipe = true;
                        break;
                    case вправо:
                        startX = (int) (sizeOfElement.width * 0.1);
                        endX = (int) (sizeOfElement.width * 0.9);
                        action.press(PointOption.point(startX, y)).waitAction(timeOfSwipe).moveTo(ElementOption.point(endX, y)).release().perform();
                        successfulSwipe = true;
                        break;
                }
            } catch (StaleElementReferenceException se) {
                numberOfTry++;
                System.out.println("Failed to swipe due to StaleElementReferenceException");
                if (numberOfTry == 3) {
                    se.printStackTrace();
                    fail("Cannot swipe due to StaleElementReferenceException");
                }
            }
        } while (!successfulSwipe);
    }
}
