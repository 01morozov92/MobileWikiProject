package HomeWorks.HomeWorkWeek4;

import io.appium.java_client.MobileElement;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class Ex6 extends CoreTestCase {

    @Test
    public void testDeleteArticleBySwipeTest() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResultAndClick("язык программирования");
        ;
        assertElementPresent("Java", By.xpath("//*[@text='Java']"));
    }


    //Этот Метод оставил, так как он не будет использоваться в других местах
    private void assertElementPresent(String expectedResult, By by) {
        String actualResult = null;
        try {
            MobileElement element = (MobileElement) driver.findElement(by);
            actualResult = element.getText();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            fail(String.format("Element %s not found", by));
        }
        Assert.assertEquals(String.format("Article title: %s does not match with expected title: %s"), expectedResult, actualResult);
    }
}
