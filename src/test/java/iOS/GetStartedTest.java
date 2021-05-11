package iOS;

import lib.iOSTestCase;
import org.junit.Test;
import org.openqa.selenium.By;

public class GetStartedTest extends iOSTestCase {

    @Test
    public void testPassWelcome() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.id("Skip")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("Search Wikipedia")).click();
        System.out.println("");
    }
}
