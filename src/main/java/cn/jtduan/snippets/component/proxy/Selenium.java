package cn.jtduan.snippets.component.proxy;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 */
public class Selenium {
    public String getHtml(String url) {
        try {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//            String PROXY = "127.0.0.1:1080";
//            Proxy proxy = new Proxy();
//            proxy.setHttpProxy(PROXY)
//                    .setFtpProxy(PROXY)
//                    .setSocksProxy(PROXY)
//                    .setSslProxy(PROXY);
//            proxy.setProxyType(Proxy.ProxyType.MANUAL);
//            capabilities.setCapability(CapabilityType.PROXY, proxy);

//            WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), DesiredCapabilities.phantomjs());
//            WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:8910"), DesiredCapabilities.phantomjs());
            WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:8910"), capabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(url);
            Thread.sleep(2000);
            String str = driver.getPageSource();
            driver.close();
            return str;
        } catch (Exception e) {
            return "";
        }
    }
}
