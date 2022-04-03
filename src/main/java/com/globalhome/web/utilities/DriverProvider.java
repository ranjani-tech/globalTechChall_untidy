package com.globalhome.web.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;

import java.time.Duration;
import java.util.Collections;

import static com.globalhome.web.constants.BrowserConstants.*;


public class DriverProvider {

    private static WebDriver driver;

    public void initDriver(){
        driver = createDriver(PropertiesProvider.getProperties().getProperty("browser"));

    }

    public static WebDriver getDriver() {
        return driver;
    }

    private WebDriver createDriver(String browserName) {
        switch (browserName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chOption = new ChromeOptions();
                chOption.addArguments("start-maximized");
                chOption.addArguments("--disable-blink-features");
                chOption.addArguments("--disable-blink-features=AutomationControlled");

                chOption.addArguments("window-size=1280,800");
                chOption.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

                //chOption.addArguments("proxy-server=106.122.8.54:3128");
                chOption.setExperimentalOption("useAutomationExtension", Boolean.FALSE);
                chOption.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                driver = new ChromeDriver(chOption);

                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fxOption = new FirefoxOptions();
                fxOption.addArguments("--disable-blink-features=AutomationControlled");
                driver = new FirefoxDriver(fxOption);

                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();

                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();

                break;
            default:
                throw new IllegalStateException("UNEXPECTED_VALUE" + browserName);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15L,1L));
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        return driver;
    }

    public void closeDriver(){
        driver.quit();
    }
}
