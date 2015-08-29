package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;

@Ignore
public class TestLogin {
    FirefoxDriver wd;
    
    @Before
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
    
    @Test
    public void Test() {
        wd.get("https://www.google.co.jp/search?q=gmail&ie=utf-8&oe=utf-8&hl=ja");
        wd.findElement(By.linkText("Gmail - Google の無料ストレージとメール")).click();
        wd.findElement(By.id("gmail-sign-in")).click();
        wd.findElement(By.id("Email")).click();
        wd.findElement(By.id("Email")).clear();
        wd.findElement(By.id("Email")).sendKeys("xxxxx@gmail.com");
        wd.findElement(By.id("next")).click();
        wd.findElement(By.id("Passwd")).click();
        wd.findElement(By.id("Passwd")).clear();
        wd.findElement(By.id("Passwd")).sendKeys("password");
        if (wd.findElement(By.id("PersistentCookie")).isSelected()) {
            wd.findElement(By.id("PersistentCookie")).click();
        }
        wd.findElement(By.id("signIn")).click();
    }
    
    @After
    public void tearDown() {
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
