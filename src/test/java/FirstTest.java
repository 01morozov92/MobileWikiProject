import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FirstTest {

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
        capabilities.setCapability("adbExecTimeout", "100000");
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
        MobileElement skipBtn = waitForElementPresentByXpath(".//*[@text='ПРОПУСТИТЬ']",
                "Cannot find skip button", 5);
        skipBtn.click();
        MobileElement searchField = waitForElementPresentByXpath("//*[contains(@text, 'Поиск')]",
                "Cannot find search input", 5);
        searchField.click();
    }

    private MobileElement waitForElementPresentByXpath(String xpath, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        By by = By.xpath(xpath);
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
