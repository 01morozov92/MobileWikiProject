package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.uiMobile.WelcomePageObject;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.time.Duration;

import static java.lang.Thread.sleep;
import static lib.Platform.isWeb;

@Log4j2
public class CoreTestCase extends TestCase {

    protected RemoteWebDriver driver;
    protected Platform Platform;

    @BeforeTest
    @Parameters({"platform", "udid", "platformVersion", "avd"})
    protected void setUp(String platform, String udid, String platformVersion, String avd) throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver(platform, udid, platformVersion, avd);
        this.rotateScreenPortrait();
        this.openWikiPageForMobileWeb();
        if (!isWeb()) {
            this.skipWelcomePage();
        }
    }

    @AfterSuite
    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        if (!isWeb()) {
            if (driver == null) {
                Platform.stopServer();
            }
        }
    }

//    @AfterSuite()
//    protected void shutDownServer(){
//        if (!isWeb()) {
//            if (driver == null) {
//                Platform.stopServer();
//            }
//        }
//    }

    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }
    }

    protected void backgroundApp(int seconds) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        }
    }

    protected void openWikiPageForMobileWeb() throws Exception {
        if (isWeb()) {
            driver.get("https://ru.m.wikipedia.org");
        } else {
            log.info("Method openWikiPageForMobileWeb do nothing for platform: " + Platform.getInstance().getPlatform().toLowerCase());
        }
    }

    private void skipWelcomePage() {
        if (lib.Platform.getInstance().isIOS()) {
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        } else {
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }
}
