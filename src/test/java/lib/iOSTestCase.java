package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class iOSTestCase extends TestCase {

    protected AppiumDriver<MobileElement> driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("app", "C:\\Users\\MI\\IdeaProjects\\MobileWikiAppiumProject\\apks\\Wikipedia.app");
        capabilities.setCapability("app", "/Users/Ilya/Downloads/Wikipedia.app");

        String appiumUrl = "http://192.30.132.150:4723/wd/hub";
        driver = new IOSDriver<>(new URL(appiumUrl), capabilities);
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }
}
