package lib.ui;

import HomeWorkWeek4.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.fail;

public class MainPageObject {

    protected AppiumDriver<?> driver;

    public MainPageObject(AppiumDriver<?> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "empty")
    @iOSXCUITFindBy(id = "empty")
    static MobileElement emptyElemForReturn;

    @AndroidFindBy(xpath = "//*")
    @iOSXCUITFindBy(xpath = "//*")
    static List<MobileElement> allElements;

    public boolean waitForElementNotPresent(MobileElement locator, String errorMessage, long timeoutInSeconds) {
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

    public void waitForElementAndClick(MobileElement locator, String errorMessage, long timeoutInSeconds) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                MobileElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                System.out.println(String.format("Failed to click on element %s due to the StaleElementReferenceException", locator));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", locator));
                }
            }
        } while (!successfulClick);
    }

    public void waitForElementAndClick(MobileElement locator, String errorMessage) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                MobileElement element = waitForElementPresent(locator, errorMessage, 5);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                System.out.println(String.format("Failed to click on element %s due to the StaleElementReferenceException", locator));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", locator));
                }
            }
        } while (!successfulClick);
    }

    public MobileElement waitForElementPresent(MobileElement locator, String errorMessage, long timeoutInSeconds) {
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

    MobileElement waitForElementPresent(MobileElement locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MobileElement getElementByText(String text, List<MobileElement> searchResultsByTitle) {
        if (searchResultsByTitle.size() == 0) {
            System.out.println((String.format("Search results with text: %s is not present ", text)));
            return emptyElemForReturn;
        }
        for (MobileElement mobileElement : searchResultsByTitle) {
            if (mobileElement.getText().equals(text)) {
                return mobileElement;
            }
        }
        throw new Error("Cannot find article with title: " + text);
    }

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
                System.out.println(String.format("Элемент: %s не найден", element));
                return false;
            } catch (StaleElementReferenceException ex) {
                System.out.println("Ошибка при поиске элемента " + element);
                j += 1;
                if (j == 5) {
                    ex.printStackTrace();
                    System.out.println(String.format("Элемент: %s принял другое состояние", element));
                    Assert.fail("Элемент принял другое состояние");
                    return false;
                }
            }
        }
        while (!webElementPassed);
        return true;
    }

    public MobileElement waitForElementAndSendKeys(MobileElement locator, String value, String errorMessage, long timeoutInSeconds) {
        MobileElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public MobileElement waitForElementAndSendKeys(MobileElement locator, String value, String errorMessage) {
        MobileElement element = waitForElementPresent(locator, errorMessage, 5);
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

    public void swipe(WaitOptions timeOfSwipe, Direction direction, MobileElement locator) {
        TouchAction action = new TouchAction(driver);
        int numberOfTry = 0;
        boolean successfulSwipe = false;
        do {
            try {
                MobileElement elementForSwipe = waitForElementPresent(locator, "Cannot find element for swipe", 20);
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
