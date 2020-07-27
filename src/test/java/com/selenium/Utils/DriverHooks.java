package com.selenium.Utils;


import com.selenium.PageRepository;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * Created by sudha on 04/10/2018.
 */
public class DriverHooks  extends PageRepository {

    @Autowired
    WebDriver driver;

    @Before
    public void Setup() throws Throwable {

        System.out.println("Setting up the driver and opening the page ");
        driver.get("https://www.facebook.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

    }

    @After
    public void afterEveryScenario() throws Throwable {
        driver.manage().deleteAllCookies();
        CacheService.clearCacheInstance();
        driver.quit();
    }
}
