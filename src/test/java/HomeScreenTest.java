import com.relevantcodes.extentreports.ExtentReports;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomeScreenTest {

    private WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driver = Utils.runTestsLocally();
        Utils.reports = new ExtentReports("reports/testCaseScreenshotOnEveryFailedStep.html", true);
    }

    @Test
    public void testCaseScreenshotOnEveryFailedStep() {
        boolean condition1 = false, condition2= false;

        CustomSoftAssert customSoftAssert = new CustomSoftAssert();
        Utils.logger = Utils.reports.startTest("Take screenshot on every failed step");

        driver.get("https://www.browserstack.com/");
        Utils.waitForSomeTime(3);

        Utils.stepLog(condition1,"condition1 is passed","condition1 is failed");
        customSoftAssert.assertTrue(condition1);

        Utils.scrollSpecificDirection(driver,"bottom",0);

        Utils.waitForSomeTime(2);
        Utils.stepLog(condition2,"condition2 is passed","condition2 is failed");
        customSoftAssert.assertTrue(condition2);

    }
    @AfterTest
    public void flushReport() {
        Utils.reports.flush();
    }
}
