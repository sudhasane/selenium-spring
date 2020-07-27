package com.selenium.driver.element;

import com.selenium.Utils.TestConfig;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sudha on 06/10/2018.
 */

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class Get {
    private final static Logger LOGGER = LoggerFactory.getLogger(Act.class);
    private static final Get INSTANCE = new Get();

    @Autowired
    private Find find;

    @Autowired
    private WaitFor waitFor;

    @Autowired
    private WebDriver webDriver;

    public List<String> elementsAttribute(By locator, String attribute) {

        List<String> listAttribute = new ArrayList<>();
        List<WebElement> webElementList = find.elements(locator);
        for (WebElement webElement : webElementList) {
            listAttribute.add(webElement.getAttribute(attribute));
        }
        return listAttribute;
    }


    public String elementAttribute(By locator, String attribute) {
        return find.element(locator).getAttribute(attribute);
    }

    public String elementAttributeByIndex(By locator, String attribute, int elementIndex) {    //TODO: Remove this method after extracting the exact elements. Passing the webelement index would become very flakey in some times.
        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementPresent(locator, Times.TWENTY_SECOND_WAIT);
                return find.element(locator).getAttribute(attribute);
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }


    public String elementCssValue(By locator, String property) {

        waitFor.elementPresent(locator);
        return find.element(locator).getCssValue(property);
    }


    public int elementCount(By locator) {

        try {
            return find.elements(locator).size();
        }
        catch (NoSuchElementException e) {
            return 0;
        }
    }


    public String elementText(By locator) {

        return elementText(locator, Times.TWENTY_SECOND_WAIT);
    }

    public String elementTag(By locator) {

        return elementTag(locator, Times.TWENTY_SECOND_WAIT);
    }

    public String elementTag(By locator, int waitTime) {

        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementPresent(locator, waitTime);
                return find.element(locator).getTagName();
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }


    public String elementText(By locator, int waitTime) {

        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementPresent(locator, waitTime);
                return find.element(locator).getText();
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }

    public String elementText(By locator, String expectedMessage, int waitTime) {

        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementWithText(locator, expectedMessage);
                return find.element(locator).getText();
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }

    public String elementHtml(By locator) {

        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementPresent(locator);
                return find.element(locator).getAttribute("innerHTML");
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }


    public String elementValue(By locator) {

        waitFor.elementVisible(locator);
        return elementAttribute(locator, "value");
    }

    public String elementValueByindex(By locator, int elementIndex) {
        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementVisible(locator);
                return elementAttribute(locator, "value");
            } catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }


    public String selectedValue(By locator) {

        waitFor.elementVisible(locator);
        return find.select(locator).getFirstSelectedOption().getText();
    }


    public List<String> optionsText(By locator) {

        for (int i = 0; i < 10; i++) {
            try {
                List<String> optionsText = new ArrayList<>();
                List<WebElement> optionElementList = optionsElements(locator);
                for (WebElement webElement : optionElementList) {
                    if (!webElement.getText().equals("--Please select--") && !webElement.getText()
                            .equals("-- Please select --")) {
                        optionsText.add(webElement.getText());
                    }
                }
                return optionsText;
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }


    private List<WebElement> optionsElements(By locator) {

        waitFor.elementPresent(locator);
        return find.select(locator).getOptions();
    }


    public List<String> elementsText(By locator) {

        List<String> listText = new ArrayList<>();
        List<WebElement> webElementList;
        try {
            webElementList = find.elements(locator);
            for (WebElement webElement : webElementList) {
                listText.add(webElement.getText().trim());
            }
        }
        catch (StaleElementReferenceException e) {
            waitFor.milliSeconds(Times.TEN_SECOND_PAUSE);
            webElementList = find.elements(locator);
            for (WebElement webElement : webElementList) {
                listText.add(webElement.getText().trim());
            }
        }
        return listText;
    }


    public List<String> elementsUniqueText(By locator) {

        List<String> listText = new ArrayList<>();
        List<WebElement> webElementList = find.elements(locator);
        for (WebElement webElement : webElementList) {
            if (!listText.contains(webElement.getText())) {
                listText.add(webElement.getText());
            }
        }
        return listText;
    }


    public String focusedElementId() {

        return find.focusedElement().getAttribute("id");
    }


    public boolean areAllElementsSelected(By locator) {

        boolean allChecked = true;
        List<WebElement> elements = find.elements(locator);
        for (WebElement element : elements) {
            if (element.isEnabled()) {
                if (!element.isSelected()) {
                    allChecked = false;
                    break;
                }
            }
        }
        return allChecked;
    }

    public boolean areListValuesDisabled(By locator, List<String> expDisabledValues) {

        List<WebElement> options = optionsElements(locator);
        boolean allDisabled = true;

        for (WebElement element : options) {
            String optionText = element.getText();
            for (String value : expDisabledValues) {
                if (optionText.equals(value)) {
                    if (element.isEnabled()) {
                        allDisabled = false;
                        break;
                    }
                }
            }
        }

        return allDisabled;
    }

    public String focusedElementAttribute(String attribute) {

        return find.focusedElement().getAttribute(attribute);
    }

    public String attributeOfFocusedParentElement(String attribute) {

        return find.parentElement(find.focusedElement()).getAttribute(attribute);
    }

    /**
     * @param locator
     * @param elementIndex
     * @return
     */
    public String elementTextByIndex(By locator, int elementIndex) {    //TODO: Remove this method after extracting the exact elements. Passing the webelement index would become very flakey in some times.
        for (int i = 0; i < 10; i++) {
            try {
                waitFor.elementPresent(locator, Times.TWENTY_SECOND_WAIT);
                return find.elements(locator).get(elementIndex).getText();
            }
            catch (StaleElementReferenceException e) {
                LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
                waitFor.milliSeconds(500);
            }
        }
        throw new RuntimeException("Could not find: " + locator.toString());
    }

    /**
     * @param days positive number returns the future-date by '@param days' to the todays date
     *             negative number returns the past date by '@param days' to the todays date
     *             '0' returns todays date
     * @return Date object
     */

    public String getFutureDateBy(int days) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return dateFormat.format(cal.getTime());
    }

    public String dateByDays(int days) {

        return getFutureDateBy(days);
    }

    public String pageTitle() {

        return webDriver.getTitle();
    }

    public String currentUrl() {

        return webDriver.getCurrentUrl();
    }

    public String getDateFor(String valueFor) {
        String dateStrToReturn = null;
        switch (valueFor) {
            case "TOMORROW":
                dateStrToReturn = dateByDays(1);
                break;
            case "YESTERDAY":
                dateStrToReturn = dateByDays(-1);
                break;
            case "TODAY":
                dateStrToReturn = dateByDays(0);
                break;
            case "INVALID":
                dateStrToReturn = "22/22/2222";
                break;
            case "BLANK":
                dateStrToReturn = "";
        }
        if (valueFor.contains("TOMORROW+")) {
            dateStrToReturn = dateByDays(Integer.parseInt(valueFor.substring("TOMORROW+".length())) + 1);
        } else if (valueFor.contains("YESTERDAY-")) {
            dateStrToReturn = dateByDays(Integer.parseInt(valueFor.substring("YESTERDAY-".length())) - 1);
        }
        return dateStrToReturn;
    }
}
