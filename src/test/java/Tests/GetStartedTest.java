package Tests;

import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Deprecated
public class GetStartedTest extends CoreTestCase {


    //Для запуска данного теста надо убрать строчку this.skipWelcomePage(); в CoreTestCase.java
    @Test
    public void testPassThroughWelcomeTest() {
        if (lib.Platform.getInstance().isAndroid()) {
            return;
        }
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWayToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLangText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedText();
        welcomePageObject.clickGetStartedButton();
    }
}
