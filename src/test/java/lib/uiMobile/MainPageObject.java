package lib.uiMobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static junit.framework.TestCase.fail;

@Log4j2
public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "empty")
    @iOSXCUITFindBy(id = "empty")
    static public WebElement emptyElemForReturn;

    @AndroidFindBy(xpath = "//*")
    @iOSXCUITFindBy(xpath = "//*")
    static public List<WebElement> allElements;

    @Step("Ожидаем исчезновения элемента по локатору: {0}")
    public boolean waitForElementNotPresent(WebElement locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        try {
            wait.until(ExpectedConditions.visibilityOf((locator)));
            return false;
        } catch (Exception tex) {
            tex.printStackTrace();
            return true;
        }
    }

    @Step("Получаем элемент по части текста: {0}")
    public WebElement getElementByPartialText(String text, List<WebElement> searchResultsByTitle) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        if (searchResultsByTitle.size() == 0) {
            log.info((String.format("Search results with text: %s is not present ", text)));
            return emptyElemForReturn;
        }
        do {
            try {
                for (WebElement mobileElement : searchResultsByTitle) {
                    if (mobileElement.getText().toLowerCase().contains(text.toLowerCase())) {
                        return mobileElement;
                    }
                }
                successfulClick = true;
            } catch (StaleElementReferenceException sre) {
                numberOfRetry++;
                log.info(String.format("Failed to find element due to the StaleElementReferenceException"));
                if (numberOfRetry == 3) {
                    sre.printStackTrace();
                    successfulClick = true;
                    fail(String.format("Cannot find element due to the StaleElementReferenceException"));
                }
            }
        } while (!successfulClick);
        return emptyElemForReturn;
//        throw new Error("Cannot find article with title: " + text);
    }

    @Step("Ожидаем и получаем список элементов по локатору: {0}")
    public List<WebElement> waitForListOfElementsPresent(List<WebElement> locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        try {
            List<WebElement> listOfElements = locator.stream().filter(e -> isClickable(wait.until(ExpectedConditions.visibilityOf((e))))).collect(Collectors.toList());
            return listOfElements;
        } catch (Exception tex) {
            tex.printStackTrace();
            fail(errorMessage);
        }
        throw new Error("Cannot find list of elements");
    }

    @Step("Проверяем элемент на кликабельность")
    public boolean isClickable(WebElement element) {
        if (element.isDisplayed() && element.isEnabled()) {
            return true;
        } else {
            log.info("Element is not clickable");
            return false;
        }
    }

    @Step("Кликаем на элемент по локатору {0} с ожиданием в {2} секунд")
    public void waitForElementAndClick(WebElement locator, String errorMessage, long timeoutInSeconds) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException | ElementClickInterceptedException se) {
                numberOfRetry++;
                log.info(String.format("Failed to click on element %s due to the StaleElementReferenceException", locator));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", locator));
                }
            }
        } while (!successfulClick);
    }

    @Step("Кликаем на элемент по локатору {0}")
    public void waitForElementAndClick(WebElement locator, String errorMessage) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                WebElement element = waitForElementPresent(locator, errorMessage, 5);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                log.info(String.format("Failed to click on element %s due to the StaleElementReferenceException", locator));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", locator));
                }
            }
        } while (!successfulClick);
    }

    @Step("Получаем элемент по точному тексту: {0}")
    public WebElement getElementByText(String text, List<WebElement> searchResultsByTitle) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        if (searchResultsByTitle.size() == 0) {
            log.info((String.format("Search results with text: %s is not present ", text)));
            return emptyElemForReturn;
        }
        do {
            try {
                for (WebElement WebElement : searchResultsByTitle) {
                    if (WebElement.getText().equalsIgnoreCase(text)) {
                        return WebElement;
                    }
                }
                successfulClick = true;
            } catch (StaleElementReferenceException sre) {
                numberOfRetry++;
                log.info(String.format("Failed to find element due to the StaleElementReferenceException"));
                if (numberOfRetry == 3) {
                    sre.printStackTrace();
                    successfulClick = true;
                    fail(String.format("Cannot find element due to the StaleElementReferenceException"));
                }
            }
        } while (!successfulClick);
        throw new Error("Cannot find article with title: " + text);
    }

    public WebElement waitForElementPresent(WebElement locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        try {
            wait.until(ExpectedConditions.visibilityOf((locator)));
        } catch (Exception tex) {
            tex.printStackTrace();
            fail(errorMessage);
        }
        return locator;
    }

    WebElement waitForElementPresent(WebElement locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    @Step("Ждем {0} секунд")
    public void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Проверяем присутствие элемента на странице")
    public boolean exist(WebElement element) {
        int j = 0;
        boolean webElementPassed = false;
        do {
            sleep(1000);
            try {
                element.isDisplayed();
                webElementPassed = true;
            } catch (TimeoutException | NoSuchElementException e) {
                e.printStackTrace();
                log.info(String.format("Элемент: %s не найден", element));
                return false;
            } catch (StaleElementReferenceException ex) {
                log.info("Ошибка при поиске элемента " + element);
                j += 1;
                if (j == 5) {
                    ex.printStackTrace();
                    log.info(String.format("Элемент: %s принял другое состояние", element));
                    Assert.fail("Элемент принял другое состояние");
                    return false;
                }
            }
        }
        while (!webElementPassed);
        return true;
    }

    @Step("Вводим текст в элемент по локатору: {0} с ожиданием {2} секунд")
    public void waitForElementAndSendKeys(WebElement locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
    }

    public void waitForElementAndSendKeys(WebElement locator, String value, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, 5);
        element.sendKeys(value);
    }

    @Step("Свайпаем по экрану в направлении {1}")
    protected void swipe(WaitOptions timeOfSwipe, Direction direction) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver<WebElement> driver = (AppiumDriver<WebElement>) this.driver;
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
    }

    @Step("Свайпаем по элементу с локатором: {2} в направлении {1}")
    public void swipe(WaitOptions timeOfSwipe, Direction direction, WebElement locator) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver<WebElement> driver = (AppiumDriver<WebElement>) this.driver;
            TouchAction action = new TouchAction(driver);
            int numberOfTry = 0;
            boolean successfulSwipe = false;
            do {
                try {
                    WebElement elementForSwipe = waitForElementPresent(locator, "Cannot find element for swipe", 20);
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
                    log.info("Failed to swipe due to StaleElementReferenceException");
                    if (numberOfTry == 3) {
                        se.printStackTrace();
                        fail("Cannot swipe due to StaleElementReferenceException");
                    }
                }
            } while (!successfulSwipe);
        }
    }

    @Attachment(value = "Скриншот страницы", type = "image/png")
    public byte[] saveAllureScreenshot() {
        return ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
    }

    private By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }
}