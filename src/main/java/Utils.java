import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.time.Duration;
import java.util.Date;

public class Utils {
    public static WebDriver driver;
    public static String extentReportImageName;
    public static ExtentTest logger;
    public static ExtentReports reports;

    public static void takeScreenshot() {
        try {
            waitForSomeTime(3);
            Date d = new Date();
            System.out.println("Taking screenshot");
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File src = screenshot.getScreenshotAs(OutputType.FILE);
            System.out.println("File is created successfully with the content. ");
            String TimeStamp = d.toString().replace(":", "_").replace(" ", "_");
            extentReportImageName = "testFailed" + TimeStamp + ".png";
            FileUtils.copyFile(src, new File("./Screenshots/" + extentReportImageName));
            System.out.println(extentReportImageName);
            System.out.println("Successfully captured a screenshot");
            String path = System.getProperty("user.dir");
            logger.log(LogStatus.FAIL, logger.addScreenCapture(path + "\\Screenshots\\" + extentReportImageName));

        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }

    }
    public static WebDriver runTestsLocally() {
        if (driver == null) {
            driver = launchDriver();
        }
        return driver;
    }

    private static WebDriver launchDriver() {
        String browserName = "chrome";
        System.out.println("Executing tests on "+browserName);

        try {
            if (browserName.equalsIgnoreCase("chrome")) {

                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions(); options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
            } else if (browserName.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("ie")) {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            }

            driver.manage().window().maximize();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: ");
        }
        return driver;
    }

    public static void stepLog(boolean bStatus, String strPassDescription, String strFailDescription) {
        if (bStatus) {
            logger.log(LogStatus.PASS, strPassDescription);
        } else {
            logger.log(LogStatus.FAIL, strFailDescription);
        }
    }
    public static void scrollSpecificDirection(WebDriver driver, String topBottomLeftRight, int scrollValue) {
       waitForSomeTime(1);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            if ((topBottomLeftRight.equalsIgnoreCase("top") && scrollValue == 0) || (topBottomLeftRight.equalsIgnoreCase("bottom") && scrollValue == 0)) {
                if (topBottomLeftRight.equalsIgnoreCase("top")) {
                    System.out.println("scroll very top ");
                    js.executeScript("window.scrollBy(0,-document.body.scrollHeight)");
                } else if (topBottomLeftRight.equalsIgnoreCase("bottom")) {
                    System.out.println("scroll  very bottom");
                    js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
                }
            } else {
                if (topBottomLeftRight.equalsIgnoreCase("bottom")) {
                    System.out.println("scroll bottom by pixel");
                    js.executeScript("window.scrollBy(0," + scrollValue + ")");
                } else if (topBottomLeftRight.equalsIgnoreCase("top")) {
                    System.out.println("scroll top by pixel");
                    js.executeScript("window.scrollBy(0," + (-scrollValue) + ")");
                } else if (topBottomLeftRight.equalsIgnoreCase("right")) {
                    System.out.println("scroll right by pixel");
                    js.executeScript("window.scrollBy(" + scrollValue + ",0)");
                } else if (topBottomLeftRight.equalsIgnoreCase("left")) {
                    System.out.println("scroll left by pixel");
                    js.executeScript("window.scrollBy(" + (-scrollValue) + ",0)");
                }
            }

            waitForSomeTime(1);

        } catch (Exception exh) {
            System.out.println("Error while scrolling");
            //logger.log(LogStatus.INFO, exh.getMessage());
        }
    }

    public static void waitForSomeTime(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (Exception e) {

        }
    }
}
