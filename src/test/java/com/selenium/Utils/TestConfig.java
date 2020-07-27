package com.selenium.Utils;

import com.selenium.driver.element.*;
import com.selenium.driver.javascript.Js;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.selenium.pages.LoginPage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.selenium")
public class TestConfig {
    @Value("${driver}")
    private String driver;

    @Bean
    public WebDriver getWebDriver() {
        switch (driver) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "ie":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "safari":
                return new SafariDriver();
            default:
                throw new IllegalArgumentException("Unknown driver " + driver);
        }
    }

    @Bean
    public Act getAct() {

        return new Act();
    }

    @Bean
    public Find getFind() {

        return new Find();
    }

    @Bean
    public Get getInstance() {

        return new Get();
    }

    @Bean
    public Is getTheIstanceOfIs() {

        return new Is();
    }

    @Bean
    public WaitFor getWaitFor() {

        return new WaitFor();
    }

    @Bean
    public Js getJs() {

        return new Js();
    }

    @Bean
    public LoginPage getLoginPage(){
        return new LoginPage();
    }

}
