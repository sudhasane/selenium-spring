package com.selenium.driver.element;

import com.selenium.Utils.TestConfig;
import com.selenium.driver.javascript.Js;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext

public class Act {
    private final static Logger LOGGER = LoggerFactory.getLogger(Act.class);

    @Autowired
    protected WebDriver webDriver;

    @Autowired
    protected Find find;

    @Autowired
    protected WaitFor waitFor;

    @Autowired
    protected Js js;

    /**
     * navigate to URL
     *
     * @param url
     */

    public void goTo(String url) {

        webDriver.get(url);
    }

    /**
     * Click on an Element
     *
     * @param locator By locator for a specific WebElement
     */
    public void clickElement(By locator) {
        find.element(locator).click();
    }

    public void selectRadioButtonOrCheckBox(By locator) {

        try {
            find.element(locator).click();
        }
        catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            find.element(locator).click();
        }
    }

    public void clickIfExists(By conditionalLocator, By clickableLocator) {

        int count = 0;
        try {
            waitFor.elementVisible(conditionalLocator);
            waitFor.elementVisible(clickableLocator);
            List<WebElement> conditionalElements = find.elements(conditionalLocator);
            List<WebElement> clickableElements = find.elements(clickableLocator);
            if (conditionalElements.size() == clickableElements.size()) {
                for (int i = 0; i < conditionalElements.size(); i++) {
                    if (conditionalElements.get(i).isDisplayed()) {
                        clickableElements.get(i).click();
                    }
                }
            }
        }
        catch (Exception e) {
            count++;
            LOGGER.info("Caight " + e.getMessage() + ". Trying again");
            while (count <= 1) {
                clickIfExists(conditionalLocator, clickableLocator);
            }
        }
    }

    public void navigateTo(String url) {

        webDriver.navigate().to(url);
    }

    /**
     * Click on an Element
     *
     * @param webElement By locator for a specific WebElement
     */
    void clickElement(WebElement webElement) {

        try {
            webElement.click();
        }
        catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            webElement.click();
        }
    }

    /**
     * Clicks on the visible WebElement
     * throws RuntimeException is multiple webelements are found to be visible
     *
     * @param locator
     */
    public void clickVisibleElement(By locator) {

        List<WebElement> elems = find.elements(locator);
        boolean found = false;
        WebElement target = null;
        for (WebElement elem : elems) {
            if (elem.isDisplayed()) {
                if (found) {
                    throw new RuntimeException("Multiple Visible Elements Found");
                }
                found = true;
                target = elem;
            }
        }
        clickElement(target);
    }

    /**
     * Click on an Element that is repeated in the page specified by index
     *
     * @param locator By locator for a specific WebElement
     */
    public void clickElement(By locator, int index) {

        try {
            waitFor.elementClickable(locator);
            find.elements(locator).get(index).click();
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            waitFor.elementClickable(locator);
            find.elements(locator).get(index).click();
        }
    }

    public void clickAllElements(By locator) {

        List <WebElement> elements = find.elements(locator);
        elements.forEach(WebElement::click);
    }

    /**
     * Performs a click using the actions builder
     *
     * @param element
     */
    public void clickElementWithBuilder(WebElement element) {

        Actions builder = new Actions(webDriver);
        builder.moveToElement(element).click(element).perform();
    }

    /**
     * Double click on an element
     *
     * @param locator By locator for WebElement to double click on
     */
    public void doubleClickElement(By locator) {

        Actions builder = new Actions(webDriver);
        builder.doubleClick(find.element(locator)).perform();
    }

    /**
     * Click the element that matches the text specified
     *
     * @param locator By for element to click
     * @param text    String for the text to search for
     */
    public void clickElementWithText(By locator, String text) {

        waitFor.elementVisible(locator);
        List <WebElement> elements = find.elementsWithText(locator, text);
        if (elements.size() == 0) {
            throw new RuntimeException("Locator(" + locator + ") with text '" + text + "' was not found!");
        } else if (elements.size() > 1) {
            throw new RuntimeException("Multiple locators(" + locator + ") with text '" + text + "' were found!");
        }
        clickElement(elements.get(0));
    }

    public void clickElementWithText(By locator, int index, String text) {

        waitFor.elementVisible(locator);
        List <WebElement> elements = find.elementsWithText(locator, text);
        if (elements.size() == 0) {
            throw new RuntimeException("Locator(" + locator + ") with text '" + text + "' was not found!");
        } else if (elements.size() > 1) {
            throw new RuntimeException("Multiple locators(" + locator + ") with text '" + text + "' were found!");
        }
        clickElement(elements.get(index));
    }


    /**
     * Clears an Element of it's value
     *
     * @param locator By locator for a specific WebElement
     */

    public void clearElement(By locator) {
        waitFor.elementVisible(locator);
        find.element(locator).clear();
    }

    /**
     * Types in an element (appends if not clear)
     *
     * @param locator By locator for a specific WebElement
     * @param value   Value to type into Element
     */
    public void typeInElement(By locator, String value) {
        try {
            find.element(locator).sendKeys(value);
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.elementVisible(locator);
            find.element(locator).sendKeys(value);
        }
    }

    /**
     * Updates an Element with value (clears it first)
     *
     * @param locator By locator for a specific WebElement
     * @param value   Value to type into Element
     */
    public void updateElement(By locator, String value) {
        clearElement(locator);
        typeInElement(locator, value);
    }

    public void updateElement(By locator, int elementIndex, String value) {

        waitFor.elementVisible(locator);
        find.element(locator, elementIndex).clear();
        try {
            find.element(locator, elementIndex).sendKeys(value);
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.elementVisible(locator);
            find.element(locator).sendKeys(value);
        }
    }

    /**
     * Select an option
     *
     * @param locator     By locator for a specific select WebElement
     * @param visibleText Text visible in option
     */
    public void selectOption(By locator, String visibleText) {

        waitFor.elementPresent(locator);
        find.select(locator).selectByVisibleText(visibleText);
    }

    /**
     * Select an option
     *
     * @param locator By locator for a specific select WebElement
     * @param index   select option by index
     */
    public void selectOptionByIndex(By locator, int index) {
        find.select(locator).selectByIndex(index);
    }

    /**
     * Multi select elements
     *
     * @param locator By locator for a specific select WebElement
     */
    public void multiSelect(By locator) {

        List<WebElement> allElements = find.elements(locator);
        for (WebElement element : allElements) {
            if (!element.isSelected()) {
                element.click();
                try {
                    waitFor.elementToBeSelected(locator);
                } catch (Exception e) {
                    LOGGER.info("Element not selected. Trying again.");
                    element.click();
                    waitFor.elementToBeSelected(locator);
                }
            }
        }
    }

    /**
     * Select multiple options in a multi-select list
     *
     * @param locator    The list locator
     * @param selections Options to be selected
     */
    public void multiSelectListOptions(By locator, List <String> selections) {

        for (String selection : selections) {
            find.element(locator).sendKeys(Keys.CONTROL);
            find.select(locator).selectByVisibleText(selection);
        }
    }

    /**
     * Scrolls the parameter into view area
     */
    public void scrollElementIntoView(By locator) {

        waitFor.elementPresent(locator);
        scrollElementIntoView(find.element(locator));
    }

    /**
     * Scrolls the parameter into view area
     */
    public void scrollElementIntoView(By locator, int index) {

        waitFor.elementPresent(locator);
        scrollElementIntoView(find.elements(locator).get(index));
    }

    public void scrollToBottomOfPage() {

        ((JavascriptExecutor) webDriver).executeScript("scroll(0,800);");
        waitFor.milliSeconds(500);
    }
    public  void oneScrollDownToTheBottom(){
        ((JavascriptExecutor) webDriver).executeScript("scroll(0,100);");
        waitFor.milliSeconds(500);
    }

    public  void oneScrollUpToTheBottom(){
        ((JavascriptExecutor) webDriver).executeScript("scroll(0,200);");
        waitFor.milliSeconds(500);
    }
    public void newScroll(By locator) {

        WebElement element = find.element(locator);
        Coordinates coordinate = ((Locatable) element).getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
    }

    /**
     * Scrolls the parameter into view area
     */
    public void scrollElementIntoView(WebElement webElement) {

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", webElement);
        waitFor.milliSeconds(500);
    }

    public void scrollIntoView(By locator) {

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", find.element(locator));
    }

    public List <String> getBoundedRectangleOfElement(WebElement we) {

        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        List <String> bounds = (ArrayList<String>) je.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return [ '' + parseInt(rect.left), '' + parseInt(rect.top), '' + parseInt(rect.width), '' + parseInt(rect.height) ]", we);
        System.out.println("top: " + bounds.get(1));
        return bounds;
    }

    public Long getViewPortHeight(WebDriver driver) {

        return (long) webDriver.manage().window().getSize().getHeight();
    }

    public void scrollToElementAndCenterVertically(By locator) {

        List <String> bounds = getBoundedRectangleOfElement(find.element(locator));
        Long totalInnerPageHeight = getViewPortHeight(webDriver);
        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        je.executeScript("window.scrollTo(0, " + (Integer.parseInt(bounds.get(1)) - (totalInnerPageHeight / 2)) + ");");
        je.executeScript("arguments[0].style.outline = \"thick solid #0000FF\";", find.element(locator));
    }


    public void switchToDialog() {


    }

    /**
     * Scrolls to the top of the current page
     */


    public void scrollToTopOfPage() {

        ((JavascriptExecutor) webDriver).executeScript(("scroll(0, -250);"));
        waitFor.milliSeconds(500);
    }

    public void focusOnElement(By locator) {

        focusOnElement(find.element(locator));
    }


    public void focusOnElementByIndex(By locator) {

        focusOnElement(find.element(locator));
    }


    void focusOnElement(WebElement webElement) {

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].focus();", webElement);
        waitFor.milliSeconds(500);
    }

    /**
     * Drag and drop an element from it's source to another element
     *
     * @param sourceLocator Locator of the element to be dragged
     * @param targetLocator Locator of the element to drop on
     * @param horizOffset   X axis offset of the drop position - for no offset, use 0
     * @param vertOffset    Y axis offset of the drop position - for no offset, use 0
     */
    public void dragAndDropElement(By sourceLocator, By targetLocator, int horizOffset, int vertOffset) {

        WebElement sourceElement = find.element(sourceLocator);
        WebElement targetElement = find.element(targetLocator);
        dragAndDropElement(sourceElement, targetElement, horizOffset, vertOffset);
    }

    /**
     * Drag and drop an element from it's source to another element
     *
     * @param sourceElement
     * @param targetElement
     * @param horizOffset   X axis offset of the drop position - for no offset, use 0
     * @param vertOffset    Y axis offset of the drop position - for no offset, use 0
     */
    void dragAndDropElement(WebElement sourceElement, WebElement targetElement, int horizOffset, int vertOffset) {

        Actions builder = new Actions(webDriver);
        Action drag = builder.clickAndHold(sourceElement).moveToElement(targetElement, horizOffset, vertOffset).build();
        Action drop = builder.release().build();
        drag.perform();
        drop.perform();
        LOGGER.debug("Done drag & drop");
    }

    /**
     * Drag and drop an element to a target element
     *
     * @param sourceLocator
     * @param targetLocator
     */
    public void dragAndDrop(By sourceLocator, By targetLocator) {

        WebElement source = find.element(sourceLocator);
        WebElement target = find.element(targetLocator);
        new Actions(webDriver).dragAndDrop(source, target).build().perform();
    }

    /**
     * Mouse over an element
     *
     * @param locator Locator of the element to hover over
     */
    public void moveToElement(By locator) {

        moveToElement(find.element(locator));
    }

    /**
     * Mouse over an element
     *
     * @param element The element to hover over
     */
    void moveToElement(WebElement element) {

        Actions builder = new Actions(webDriver);
        builder.moveToElement(element).perform();
    }

    public void moveToElement(By locator, int index) {

        moveToElement(find.elements(locator).get(index));
    }
    /**
     * Wrapper method for upload file - no need for locator
     *
     * @param filePath
     */
    public void uploadFile(String filePath) {

        uploadFile(By.cssSelector("input[type=file]"), filePath);
    }

    /**
     * Uploads a file using the windows dialog
     *
     * @param locator  Locator of the file input
     * @param filePath
     */
    void uploadFile(By locator, String filePath) {

        WebElement elem = find.element(locator);
        try {
            elem.sendKeys(filePath);
        } catch (Exception e) {
            String attrVal = js.getAttributeValues(locator.toString(), "class");
            js.removeAttribute(locator.toString(), "class");
            elem.sendKeys(filePath);
            js.setAttributeValue(locator.toString(), "class", attrVal);
        }
    }

    public void keyPress(CharSequence keys) {

        new Actions(webDriver).sendKeys(keys).build().perform();
    }

    /**
     * Get the text on an open Alert
     *
     * @return Alert text value
     */
    public String getAlertText() {

        waitFor.alert();
        return webDriver.switchTo().alert().getText();
    }

    /**
     * Accept an open alert (click ok)
     */
    public void alertAccept() {

        waitFor.alert();
        webDriver.switchTo().alert().accept();
    }

    /**
     * Dismiss an open alert (click cancel)
     */
    public void alertDismiss() {

        waitFor.alert();
        webDriver.switchTo().alert().dismiss();
    }

    public List <WebElement> getChildren(By locator, int elementIndex) {

        String jscript = "return arguments[0].childNodes";
        List <Object> children = (List <Object>) ((JavascriptExecutor) webDriver).executeScript(jscript, webDriver.findElements(locator).get(elementIndex));
        List <WebElement> elements = new ArrayList <WebElement>();
        for (Object child : children) {
            if (child instanceof WebElement) {
                elements.add((WebElement) child);
            }
        }
        return elements;
    }

    public void waitForPageToLoad() {

        new WebDriverWait(webDriver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void refreshThePage() {

        webDriver.navigate().refresh();
        waitForPageToLoad();
    }

    public void waitForAJAX() {

        waitForAJAX(5);
    }

    /**
     * Waits for all ajax responses to complete
     *
     * @param waitTime
     */
    public void waitForAJAX(int waitTime) {

        int counter = 0;
        Long ajaxReady = (Long) js.getEval("return window.Ext && window.Ext.Ajax && window.Ext.Ajax.__queueCounter");
        try {
            if (ajaxReady.equals(null)) {
                ajaxReady = (long) 1;
            }
        } catch (NullPointerException n) {
            ajaxReady = (long) 1;
        }

        while (!ajaxReady.toString().equals("0") && counter < waitTime / 3) {
            waitFor.milliSeconds(3000);
            ajaxReady = (Long) js.getEval("return window.Ext && window.Ext.Ajax && window.Ext.Ajax.__queueCounter");
            try {
                if (ajaxReady.equals(null)) {
                    ajaxReady = (long) 1;
                }
            } catch (NullPointerException n) {
                ajaxReady = (long) 1;
            }
            counter++;
        }
        if (!ajaxReady.toString().equals("0")) {
            throw new RuntimeException("Waiting for AJAX failed after " + waitTime + " seconds either as hadn't finished loading or no AJAX on the page");
        }
    }
}
