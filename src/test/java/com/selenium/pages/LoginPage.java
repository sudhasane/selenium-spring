package com.selenium.pages;
import ch.lambdaj.function.aggregate.PairAggregator;
import com.selenium.PageRepository;
import com.selenium.driver.element.Act;
import com.selenium.driver.element.WaitFor;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;


public class LoginPage extends PageRepository {

   private static final By EMAIL = By.cssSelector("#email");
   private static final By PW = By.cssSelector("pass");
   private static final By BTN = By.cssSelector("#loginbutton");


   public void enterUnAndPw(){
      //waitFor.milliSeconds(3000);
      action.updateElement(EMAIL, "vanisekhar75@gmail.com");
      action.updateElement(PW, "password");
   }

   public void clickLogin(){
      action.clearElement(BTN);
   }

}
