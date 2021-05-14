package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    private static Platform instance;

    public Platform() {
    }

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public String getPlatform() throws Exception {
        if (isAndroid()) {
            return PLATFORM_ANDROID;
        } else if (isIOS()) {
            return PLATFORM_IOS;
        } else {
            throw new Exception("Cannot detect type of the Driver. Platform value " + this.getPlatformVar());
        }
    }

    public static boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public static boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP, new File(readProperty("app.android.path")).getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, readProperty("device.android.name"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, readProperty("platform.android.version"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("udid", "emulator-5556");
        capabilities.setCapability("avd", "pixel64");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("uiautomator2ServerInstallTimeout", "200000");
        capabilities.setCapability(MobileCapabilityType.LOCALE, "RU");
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "ru");
        ;
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP, readProperty("app.ios.path"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, readProperty("device.ios.name"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, readProperty("platform.ios.version"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        return capabilities;
    }

    private static boolean isPlatform(String my_platform) {
        String platform = getPlatformVar();
        return my_platform.equals(platform);
    }

    private static String getPlatformVar() {
        return System.getenv("PLATFORM");
    }

    public static String readProperty(String property) {
        Properties prop;
        String value = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream(new File("src/test/resources/config.properties")));

            value = prop.getProperty(property);

            if (value == null || value.isEmpty()) {
                throw new Exception("Value not set or empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public AppiumDriver<?> getDriver() throws Exception {
        AppiumDriver<?> driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (Boolean.parseBoolean(readProperty("run.hybrid"))) {
            capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
        }

        switch ("android") {//getPlatform().toLowerCase()

            case "ios":
                String completeURL = "http://" + readProperty("run.ip.ios") + ":" + readProperty("run.port") + "/wd/hub";
                getIOSDesiredCapabilities();

                if (Boolean.parseBoolean(readProperty("platform.ios.xcode8"))) {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                }

                driver = new IOSDriver<RemoteWebElement>(new URL(completeURL), getIOSDesiredCapabilities());
                break;

            case "android":
                completeURL = "http://" + readProperty("run.ip.android") + ":" + readProperty("run.port") + "/wd/hub";
                getAndroidDesiredCapabilities();

                driver = new AndroidDriver(new URL(completeURL), getAndroidDesiredCapabilities());
                break;

            default:
                throw new Exception("Platform not supported");
        }

        return driver;
    }
}