package cn.jtduan.snippets.webdriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by jintaoduan on 18/10/26.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new RemoteWebDriver(
                new URL("http://127.0.0.1:8910"),
                DesiredCapabilities.phantomjs());
        driver.manage().window().maximize();
        driver.get("https://ssl.captcha.qq.com/template/wireless_mqq_captcha.html?style=simple&aid=16&uin=3272919138&cap_cd=etNipnwbK-eCdePvW7jES_rNNUaRecUbCMcJnKi6Q14EEaF29ql46A**&clientype=1&apptype=2");

        Thread.sleep(2000);
        driver.switchTo().frame(driver.findElement(By.id("tcaptcha_iframe")));
        System.out.println(driver.getPageSource());
        WebElement icon = driver.findElement(By.id("tcaptcha_drag_thumb"));
        WebElement templateUrlEle = driver.findElement(By.id("slideBlock"));
        String url = templateUrlEle.getAttribute("src");
        System.out.println(url);
//        Actions action = new Actions(driver);
//        action.clickAndHold(icon).perform();
//
//        //确保每次拖动的像素不同，故而使用随机数
//        action.clickAndHold(icon).moveByOffset((int)(Math.random()*200)+80, 0);
//        action.clickAndHold(icon).moveByOffset((int)(Math.random()*200)+80, 0);
//        //拖动完释放鼠标
//        action.moveToElement(icon).release();
//        //组织完这些一系列的步骤，然后开始真实执行操作
//        action.perform();
//        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        org.apache.commons.io.FileUtils.copyFile(screen, new File("screenshot.png"));
    }
}
