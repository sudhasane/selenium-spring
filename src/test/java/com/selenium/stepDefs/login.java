package com.selenium.stepDefs;

import com.selenium.PageRepository;
import com.selenium.pages.LoginPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;


public class login extends PageRepository{

    @Given("^I am on home page$")
    public void I_am_on_home_page(){
//        String title = "facebook";
//        CacheService.getInstance().setDataMap("pageTitle", new AnyData(title));
//        CacheService.getInstance().getDataMap().get("pageTitle").getDataObject().toString();
//        CacheService.getInstance().destroyValues("pageTitle");
    }

    @Given("^I am on face book home page$")
    public void iAmOnFaceBookHomePage() throws Throwable {
        Thread.sleep(5000);

    }

    @And("^I enter username and password$")
    public void iEnterUsernameAndPassword() throws Throwable {
       loginPage.enterUnAndPw();
    }

    @And("^I select Login$")
    public void iSelectLogin() throws Throwable {
        loginPage.clickLogin();
    }
}
