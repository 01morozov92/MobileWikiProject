package lib;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_WEB = "web";

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
        } else if (isWeb()) {
            return PLATFORM_WEB;
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

    public static boolean isWeb() {
        return isPlatform(PLATFORM_WEB);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities(String platform, String udid, String platformVersion, String avd) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        capabilities.setCapability("udid", udid); //имя используемого эмулятора
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability("avd", avd); //для запуска эмулятора из теста
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "pixel64"); //имя устройства
        capabilities.setCapability(MobileCapabilityType.APP, new File(readProperty("app.android.path")).getAbsolutePath());
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("uiautomator2ServerInstallTimeout", "300000");
        capabilities.setCapability("deviceReadyTimeout", "300000");
        capabilities.setCapability("avdLaunchTimeout", "300000");
        capabilities.setCapability("avdReadyTimeout", "300000");
        capabilities.setCapability(MobileCapabilityType.LOCALE, "RU");
        capabilities.setCapability(MobileCapabilityType.LANGUAGE, "ru");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities(String platform, String udid, String platformVersion, String avd) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP, readProperty("app.ios.path"));
//        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, readProperty("device.ios.name"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, avd);
//        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, readProperty("platform.ios.version"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
//        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        return capabilities;
    }

    private ChromeOptions getWebChromeOptions() {
        Map<String, Object> deviseMetrics = new HashMap<String, Object>();
        deviseMetrics.put("width", 360);
        deviseMetrics.put("height", 640);
        deviseMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviseMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Mobile Safari/537.36");
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=340,640");
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        return chromeOptions;
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

    public RemoteWebDriver getDriver(String platform, String udid, String platformVersion, String avd) throws Exception {
        RemoteWebDriver driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (Boolean.parseBoolean(readProperty("run.hybrid"))) {
            capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
        }

        switch (getPlatform().toLowerCase()) {//getPlatform().toLowerCase()

            case "ios":
                String completeURL = "http://" + readProperty("run.ip.ios") + ":" + readProperty("run.port") + "/wd/hub";
                getIOSDesiredCapabilities(platform, udid, platformVersion, avd);

                if (Boolean.parseBoolean(readProperty("platform.ios.xcode8"))) {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                }

                driver = new IOSDriver<MobileElement>(new URL(completeURL), getIOSDesiredCapabilities(platform, udid, platformVersion, avd));
                break;

            case "android":
                completeURL = "http://" + readProperty("run.ip.android") + ":" + readProperty("run.port") + "/wd/hub";
                getAndroidDesiredCapabilities(platform, udid, platformVersion, avd);

                AppiumServer();
                driver = new AndroidDriver(get(), getAndroidDesiredCapabilities(platform, udid, platformVersion, avd));
                break;

            case "web":
                if (Boolean.parseBoolean(readProperty("run.hybrid"))) {
                    capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
                }
                driver = new ChromeDriver(this.getWebChromeOptions());
                break;

            default:
                throw new Exception("Platform not supported");
        }

        return driver;
    }

//    AppiumDriverLocalService service;
//
//    public void startServer() {
//        AppiumServiceBuilder builder = new AppiumServiceBuilder();
//        if (isIOS()) {
//            builder.withAppiumJS(new File("C:\\Users\\user\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"));
//        } else {
//            builder.withAppiumJS(new File("C:\\Users\\user\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"));
//        }
////        builder.withIPAddress("127.0.0.1");
//        builder.usingPort(4723);
//        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
//        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
//
//        //Start the server with the builder
//        service = AppiumDriverLocalService.buildService(builder);
//        service.start();
//    }
//
//    public void stopServer() {
//        service.stop();
//    }


    private final AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
    private static AppiumDriverLocalService server;
    private int port;
    private final Path appiumLogsLoc = Path.of("C:\\Users\\user\\IdeaProjects\\MobileWikiProject\\appium-logs");
    private final String logFileName = "logs";

    public void AppiumServer() {
        this.serviceBuilder.withAppiumJS(new File("C:\\Users\\user\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"));
//        this.serviceBuilder.usingAnyFreePort();
        this.serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        this.serviceBuilder.withIPAddress("127.0.0.1");
        this.serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        server = AppiumDriverLocalService.buildService(serviceBuilder);
        server.start();
    }

    public void stopServer() {
        server.stop();
    }

    public static AppiumDriverLocalService get() {
        return server;
    }

    public static int getAvailablePort() {
        int port = 4723;

        try {
            ServerSocket serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

    public void redirectLog() throws IOException {
        this.server.clearOutPutStreams();
        String i = String.valueOf((int) (Math.random() * 5) + 500);
        try {
            Path directoryPath = Files.createDirectory(Paths.get("C:\\Users\\user\\IdeaProjects\\MobileWikiProject\\appium-logs"));
            this.server.addOutPutStream(Files.newOutputStream(directoryPath.resolve(i + logFileName)));
        } catch (FileAlreadyExistsException fex) {
            this.server.addOutPutStream(Files.newOutputStream(appiumLogsLoc.resolve(i + logFileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}