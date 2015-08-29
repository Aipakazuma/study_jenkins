package test;

import java.io.File;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImageSearchTest extends TestCase {
    public interface WebDriverFactory {

        public WebDriver create();

    }
    
    public static Iterable<WebDriverFactory> getDriverFactories() {
        ArrayList<WebDriverFactory> factories = new ArrayList<WebDriverFactory>();
        factories.add(new WebDriverFactory() {
//            @Override
            public WebDriver create() {
                return new FirefoxDriver();
            }
        });
//        final String chromeDriverPath = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"; // ChromeDriverのパス
//        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//        factories.add(new WebDriverFactory() {
////            @Override
//            public WebDriver create() {
//                return new ChromeDriver();
//            }
//        });
        return factories;
    }

    @Test
    public void testImage() throws Exception {
        for (WebDriverFactory factory : getDriverFactories()) {
            WebDriver driver = factory.create();
            try {
                // 1.Googleを検索
                driver.get("http://www.google.co.jp");
                Wait<WebDriver> wait = new WebDriverWait(driver, 30);
                // 2.フィールドを指定して文字を検索
                WebElement inputField = wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//input[@type='text']")));
                assertEquals(driver.getTitle(), "Google");
                inputField.sendKeys("ウェルシュ・コーギー・ペンブローク");
                inputField.sendKeys(Keys.ENTER);
                wait.until(ExpectedConditions.titleContains("ウェルシュ・コーギー・ペンブローク"));

                WebElement imageButton = wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//a[contains(@href,'tbm=isch')]")));
                imageButton.sendKeys(Keys.ENTER);
                Thread.sleep(10000);
                // 3.画面キャプチャ
                // 4.ファイル保存
                FileUtils.copyFile(((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE), new File(driver
                        .getClass().getName() + "-dogs-search.png"));
            } finally {
                driver.quit();
            }
        }
    }
}
