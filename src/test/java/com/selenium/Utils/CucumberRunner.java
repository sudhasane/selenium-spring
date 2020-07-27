package com.selenium.Utils;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions( plugin = {"html:target/cucumber-html-report" },
        features = { "./src/test/resources/features" },
        glue = {"com.selenium"})
public class CucumberRunner {


}
