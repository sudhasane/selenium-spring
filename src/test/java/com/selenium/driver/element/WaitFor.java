package com.selenium.driver.element;

import com.selenium.Utils.TestConfig;
import com.selenium.driver.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by sudha on 06/10/2018.
 */

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
@Component
public class WaitFor {
    @Autowired
    public WebDriver webDriver;


    public void milliSeconds(long milliseconds) {
        Times.waitForMilliSeconds(milliseconds);
    }


    public void elementPresent(By locator) {

        elementPresent(locator, Times.TWENTY_SECOND_WAIT);
    }


    public void elementPresent(By locator, int waitTime) {

        wait(ConditionFactory.elementPresent(locator), waitTime);
    }

    public void elementNotPresent(By locator) {

        elementNotPresent(locator, Times.TWENTY_SECOND_WAIT);
    }


    public void elementNotPresent(By locator, int waitTime) {

        wait(ConditionFactory.elementNotPresent(locator), waitTime);
    }


    public boolean elementVisible(By locator) {

        return elementVisible(locator, Times.TWENTY_SECOND_WAIT);
    }

    public boolean elementVisible(By locator, int waitTime) {
        try {
            wait(ConditionFactory.elementVisible(locator), waitTime);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void elementVisible(By locator, long waitTime) {

        wait(ConditionFactory.elementVisible(locator), waitTime);
    }


    public void elementInvisible(By locator) {

        elementInvisible(locator, Times.TWENTY_SECOND_WAIT);
    }


    public void elementInvisible(By locator, int waitTime) {
        wait(ConditionFactory.elementInvisible(locator), waitTime);
    }


    public void elementClickable(By locator) {

        elementClickable(locator, Times.TWENTY_SECOND_WAIT);
    }


    public void elementClickable(By locator, int waitTime) {

        wait(ConditionFactory.elementClickable(locator), waitTime);
    }


    public void elementToBeSelected(By locator) {

        elementToBeSelected(locator, Times.TWENTY_SECOND_WAIT);
    }


    public void elementToBeSelected(By locator, int waitTime) {

        elementSelectionStateToBe(locator, true, waitTime);
    }


    public void elementToBeUnselected(By locator, int waitTime) {

        elementSelectionStateToBe(locator, false, waitTime);
    }


    private void elementSelectionStateToBe(By locator, boolean selected, int waitTime) {

        wait(ConditionFactory.elementSelectionState(locator, selected), waitTime);
    }


    public void elementToHaveText(By locator, String text) {

        elementToHaveText(locator, text, Times.TWENTY_SECOND_WAIT);
    }
    public void elementToHaveTextLength(By locator, int length) {

        wait(ConditionFactory.elementToHaveTextLength(locator, length), 10);
    }
    public void elementTextIsNotNull(By locator) {

        wait(ConditionFactory.elementVisibleTextLengthIsNotNull(locator), 10);
    }


    void elementToHaveText(By locator, String text, int waitTime) {

        wait(ConditionFactory.elementVisibleText(locator, text), waitTime);
    }


    public void elementWithText(By locator, String text) {

        elementWithText(locator, text, Times.TWENTY_SECOND_WAIT);
    }


    void elementWithText(By locator, String text, int waitTime) {

        wait(ConditionFactory.elementWithText(locator, text), waitTime);
    }


    public void elementToHaveValue(By locator, String text) {

        elementToHaveValue(locator, text, Times.TWENTY_SECOND_WAIT);
    }


    void elementToHaveValue(By locator, String text, int waitTime) {

        wait(ConditionFactory.elementVisibleValue(locator, text), waitTime);
    }


    public void elementActive(By locator) {

        elementActive(locator, Times.TWENTY_SECOND_WAIT);
    }


    void elementActive(By locator, int waitTime) {

        wait(ConditionFactory.elementAttributeContains(locator, "class", "active"), waitTime);
    }


    public void alert(int waitTime) {

        wait(ConditionFactory.alertPresent(), waitTime);
    }

    public void alert() {

        alert(Times.TWENTY_SECOND_WAIT);
    }


    private void wait(ExpectedCondition<?> expCondition, int waitTime) {

        for (int i = 1; i < 4; i++) {
            try {
                new WebDriverWait(webDriver, waitTime).until(ConditionFactory.refreshed(expCondition));
                return;
            }
            catch (TimeoutException | UnreachableBrowserException te) {
                throw te;
            }
            catch (WebDriverException wde) {
                //                logWarning("Caught WebDriverException " + i + " times");
                Times.waitForMilliSeconds(Times.TWENTY_SECOND_WAIT);
            }
        }
        throw new RuntimeException("Webdriver exception failed more than 3 times");
    }


    private void wait(ExpectedCondition <?> expCondition, long waitTime) {

        for (int i = 1; i < 4; i++) {
            try {
                new WebDriverWait(webDriver, waitTime).until(ConditionFactory.refreshed(expCondition));
                return;
            } catch (TimeoutException | UnreachableBrowserException te) {
                throw te;
            } catch (WebDriverException wde) {
                //                logWarning("Caught WebDriverException " + i + " times");
                Times.waitForMilliSeconds(Times.FIVE_SECOND_PAUSE);
            }
        }
        throw new RuntimeException("Webdriver exception failed more than 3 times");
    }


}
