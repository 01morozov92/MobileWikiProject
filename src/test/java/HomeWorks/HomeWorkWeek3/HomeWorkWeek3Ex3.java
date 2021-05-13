package HomeWorks.HomeWorkWeek3;

import io.appium.java_client.MobileElement;
import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class HomeWorkWeek3Ex3 extends CoreTestCase{

    @Test
    public void testSearchCancel(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        Assert.assertTrue("Cannot find search results", searchPageObject.checkVisionOfALlSearchResults());
        List<MobileElement> searchResultBefore = searchPageObject.getListOfArticles("Java");
        Assert.assertTrue("Search result is empty", searchResultBefore.size() > 0);
        searchPageObject.closeSearchField();
        Assert.assertTrue("Search result is still present", searchPageObject.waitForSearchResult("Java"));
        List<MobileElement> searchResultAfter = searchPageObject.getListOfArticles("Java");
        Assert.assertEquals("Search result is not empty", 0, searchResultAfter.size());
    }
}
