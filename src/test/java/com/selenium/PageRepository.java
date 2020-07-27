package com.selenium;

import com.selenium.Utils.TestConfig;
import com.selenium.driver.element.*;
import com.selenium.driver.javascript.Js;
import com.selenium.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class PageRepository {
    protected @Autowired
    WebDriver driver;

    @Autowired
    WaitFor waitFor;

    @Autowired
    protected Act action;

    @Autowired
    protected Find find;

    @Autowired
    protected Get get;

    @Autowired
    protected Is is;

    @Autowired
    protected Js js;

    @Autowired
    protected LoginPage loginPage;


}
