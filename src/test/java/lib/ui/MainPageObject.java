package lib.ui;

import HomeWorkWeek4.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;

import static junit.framework.TestCase.fail;

public class MainPageObject {

    protected AppiumDriver<MobileElement> driver;

    public MainPageObject(AppiumDriver<MobileElement> driver){
        this.driver = driver;
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(getLocatorByString(locator)));
    }

    public void waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
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

    public void waitForElementAndClick(String locator, String errorMessage) {
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

    public MobileElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(getLocatorByString(locator)));
    }

    MobileElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public MobileElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        MobileElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public MobileElement waitForElementAndSendKeys(String locator, String value, String errorMessage) {
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

    public void swipe(WaitOptions timeOfSwipe, Direction direction, String locator) {
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

    private By getLocatorByString(String locator_with_type)
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get typ of locator. Locator: " + locator_with_type);
        }
    }
}
