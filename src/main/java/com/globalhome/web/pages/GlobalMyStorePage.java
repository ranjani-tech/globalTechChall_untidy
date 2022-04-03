package com.globalhome.web.pages;

import com.globalhome.web.base.BasePage;
import com.globalhome.web.utilities.PropertiesProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.*;

public class GlobalMyStorePage extends BasePage {

    @FindBy(how = How.XPATH, using = "//a[text()='Women']")
    WebElement womenProductBlockElement;

    @FindBy(how = How.XPATH, using = "//p[@class='subcategory-heading']")
    WebElement parSubCategoryElement;

    @FindBy(how = How.XPATH, using = "//a[@class='subcategory-name']")
    List<WebElement> subCategoriesElements;
    WebElement subCategoryElement;

    //Generic productCategoryXpath creator for productCategory selection
    String productCategoryXpathPrefix = "//a[@class='subcategory-name' and text()='";
    String productCategoryXpathSuffix = "']";
    String productCategoryXpath = null;
    WebElement productCategoryElement;


    //Generic xPath for checkbox
    //a[text()='M'] or a[text()='Blue']
    String checkboxXpathPrefix = "//a[text()='";
    String checkboxXpathSuffix = "']";
    String checkboxXpath = null;
    WebElement checkboxSizeElement;
    WebElement checkboxColorElement;

    //Generic xPath for product
    //eg "//a[@title='Faded Short Sleeve T-shirts' and @class='product-name']"

    String productNameXpathPrefix = "//a[@title='";
    String productNameXpathSuffix = "' and @class='product-name']";
    String productNameXpath = null;
    WebElement productNameElement;


    @FindBy(how = How.XPATH, using = "//h1[@itemprop='name']")
    WebElement itemHeadingElement;


    @FindBy(how = How.ID, using = "quantity_wanted")
    WebElement inputQuantityWantedElement;

    //size
    @FindBy(how = How.NAME, using = "group_1")
    WebElement sizeWantedElement;


    // colorWanted
    String colorWantedXpathPrefix = "//ul[@id='color_to_pick_list']//li/a[@title='";
    String colorWantedXpathSuffix = "']";
    String colorWantedXpath = null;
    WebElement colorWantedElement;


    @FindBy(how = How.NAME, using = "Submit")
    WebElement submitButtonElement;

    @FindBy(how = How.XPATH, using = "//i[@class='icon-ok']//..")
    WebElement itemCartStatusElement;

    String itemCartStatusSuccess = "Product successfully added to your shopping cart";

    @FindBy(how = How.CSS, css = "span[title='Continue shopping']")
    WebElement continueShoppingElement;

    //regex = //a[@title='xyz']
    String aTitleXpathPrefix = "//a[@title='";
    String aTitleXpathSuffix = "']";
    String aTitleXpath = null;
    WebElement aTitleElement;

    @FindBy(how = How.ID, using = "cart_title")
    WebElement cartTitleElement;

    //actionInCartElement
    String actionInCartXpathPrefix = "//p[@class='product-name']//a[text()='";
    String actionInCartXpathMiddleFirst = "']//../..//..//a[@title='";
    String actionInCartXpathMiddleSecond = "']//..//i[@class='";
    String actionInCartXpathSuffix = "']";
    String actionInCartXpath;
    WebElement actionInCartElement;

    String summaryProductsQuantityXpath = "//*[@id='summary_products_quantity']";

    @FindBy(how = How.ID, using = "summary_products_quantity")
    WebElement summaryProductQuantityElement;


    @FindBy(how = How.ID, using = "total_price")
    WebElement totalPriceElement;


    //constructor
    public GlobalMyStorePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Navigate to HomePage:
    public void openStartPage() {
        goToWeb(PropertiesProvider.getProperties().getProperty("starturl"));
        waitUntilUrlContains(PropertiesProvider.getProperties().getProperty("starturl"));
    }

    public void selectPageTitle(String getPageTitle) {
        waitUntilUrlContains("index.php");
        checkIfPageTitleContains(getPageTitle(), "My Store");
    }


    public void addProductInCart(String productBlock, String subCategory, String productCategoryName, String size, String color, String productName, String quantity, String actionNow, String actionNext) {

        waitUntilElementClickable(womenProductBlockElement);
        checkIfTextIsExpected(womenProductBlockElement, productBlock);
        clickOn(womenProductBlockElement);
        waitUntilElementVisible(parSubCategoryElement);
        subCategoryElement = getSubCategoryElement(subCategory);
        waitUntilElementClickable(subCategoryElement);
        clickOn(subCategoryElement);


        //T-shirts element erukka nu paarthu click panna pooran
        productCategoryXpath = productCategoryXpathPrefix + productCategoryName + productCategoryXpathSuffix;
        productCategoryElement = find_element_by_xpath(productCategoryXpath);
        waitUntilElementClickable(productCategoryElement);
        clickOn(productCategoryElement);
        waitUntilUrlContains("category");
        //Size Element eruukkanu parthu select panna pooran
        //a[text()='M']
        checkboxSizeElement = getCheckboxElement(size);
        waitUntilElementClickable(checkboxSizeElement);
        clickOn(checkboxSizeElement);
        waitUntilUrlContains("size-" + size.toLowerCase());
        // color select pannu
        checkboxColorElement = getCheckboxElement((color));
        waitUntilElementClickable(checkboxColorElement);
        clickOn(checkboxColorElement);
        waitUntilUrlContains("color-" + color.toLowerCase());
        // check the product name visible
        productNameElement = getProductNameElement((productName));
        checkIfTextContainsExpected(productNameElement, productName);
        clickOn(productNameElement);
        checkIfTextIsExpected(itemHeadingElement, productName);

        inputTextBox(inputQuantityWantedElement, quantity);
        selectByVisibleTextInDropDown(sizeWantedElement, size);

        colorWantedElement = getListWantedElement(color);
        clickOn(colorWantedElement);

        clickOn(submitButtonElement);
        waitUntilUrlContains("color-" + color.toLowerCase());

        waitUntilUrlContains("size-" + size.toLowerCase());

        waitUntilElementVisible(itemCartStatusElement);
        checkIfTextContainsExpected(itemCartStatusElement, itemCartStatusSuccess);

        waitUntilElementClickable(continueShoppingElement);
        clickOn(continueShoppingElement);

    }

    WebElement getSubCategoryElement(String subCategory) {

        WebElement subCategoryElement = null;
        for (WebElement eachElement : subCategoriesElements) {
            System.out.println(eachElement.getText() + " vs " + subCategory);
            if (eachElement.getText().contains(subCategory)) {
                subCategoryElement = eachElement;
                System.out.println(subCategoryElement.getLocation() + "Found");
                return subCategoryElement;
            }
        }
        return subCategoryElement;

    }


    WebElement getCheckboxElement(String selectCheckbox) {
        checkboxXpath = checkboxXpathPrefix + selectCheckbox + checkboxXpathSuffix;
        WebElement CheckboxElement = find_element_by_xpath(checkboxXpath);
        return CheckboxElement;
    }

    WebElement getListWantedElement(String listText) {
        colorWantedXpath = colorWantedXpathPrefix + listText + colorWantedXpathSuffix;
        WebElement listSelectedElement = find_element_by_xpath(colorWantedXpath);
        return listSelectedElement;
    }

    WebElement getProductNameElement(String selectProductName) {
        productNameXpath = productNameXpathPrefix + selectProductName + productNameXpathSuffix;
        WebElement ProductNameElement = find_element_by_xpath(productNameXpath);
        return ProductNameElement;
    }

    public void userClicksLinkTitle(String linkToClickFromTitle) {
        aTitleElement = getaTitleElement(linkToClickFromTitle);
        clickOn(aTitleElement);
        waitUntilUrlContains("order");

    }

    WebElement getaTitleElement(String linkToClickFromTitle) {
        aTitleXpath = aTitleXpathPrefix + linkToClickFromTitle + aTitleXpathSuffix;
        WebElement aTitleElementFound = find_element_by_xpath(aTitleXpath);
        return aTitleElementFound;
    }

    public void viewCart(String cartTitle) {
        waitUntilElementVisible(cartTitleElement);
        checkIfTextContainsExpected(cartTitleElement, cartTitle);


    }

    public List<Map<String, String>> verifyProductList() {

        waitUntilUrlContains("order");
        List<Map<String, String>> actualProductCarts = new ArrayList<Map<String, String>>();

        String everyTableRows = "//div[@id='order-detail-content']//table[@id='cart_summary']//tbody//tr";
        String before_XPath_1 = "//*[@id='cart_summary']//*//tbody//tr[1]//th['";
        String before_XPath_2 = "//*[@id='cart_summary']//*//tbody//tr[2]//td['";
        String after_XPath = "']";
        List<WebElement> tableRows = find_elements_by_xpath(everyTableRows);
        waitUntilElementsVisible(tableRows);
        for (WebElement eachRow : tableRows) {
            //System.out.println(eachRow.getText());
            List<WebElement> productColumns = find_elements_by_xpath(eachRow, "./td[2]");
            //List<WebElement> quantityColumns = find_elements_by_xpath(eachRow, "./td[@class='cart_quantity text-center']//input[@type='hidden']");
            List<WebElement> quantityColumns = find_elements_by_xpath(eachRow, "./td[@class='cart_quantity text-center']//input[@type='hidden']");
            List<WebElement> totalColumns = find_elements_by_xpath(eachRow, "./td[6]");
            //System.out.println("productColumns" + productColumns.size());
            //System.out.println("quantityColumns" + quantityColumns.size());
            //System.out.println("totalColumns" + totalColumns.size());
            //String List elements text into String array

            //System.out.println("|++++++++++++++|");
            //System.out.println(productColumns.get(0).getText());
            String[] listCarts = productColumns.get(0).getText().split("\\n");
            String productName = listCarts[0];
            String quantity = quantityColumns.get(0).getAttribute("value");
            String totalCount = totalColumns.get(0).getText();

            System.out.println("|--------------|productName|-------|quantity|--------|countTotal|-------|");
            System.out.println("|--------------|" + productName + "|-------|" + quantity + "|--------|" + totalCount + "|-------|");

            Map<String, String> productInCart = new HashMap<String, String>();
            productInCart.put("productname", productName);
            productInCart.put("amount", totalCount);
            productInCart.put("quantity", quantity);
            System.out.println(productInCart);

            actualProductCarts.add(productInCart);

        }


        return actualProductCarts;

    }

    public void verifySummaryProduct(String summaryProduct) {
        checkIfTextIsExpected(summaryProductQuantityElement, summaryProduct);
        Assert.assertEquals(summaryProductQuantityElement.getText(), summaryProduct);
    }

    public void actionTheProductInCart(String productName, String actionName, String iconName) {
        actionInCartElement = getActionElementInTheCart(productName, actionName, iconName);
        waitUntilElementClickable(actionInCartElement);
        moveElement(actionInCartElement);
        summaryProductQuantityElement = find_element_by_xpath(summaryProductsQuantityXpath);
        String summaryProductQuantityValue = summaryProductQuantityElement.getText();

        clickOn(actionInCartElement);
        waitUntilTextInElementInvisible(summaryProductsQuantityXpath, summaryProductQuantityValue);

        //waitUntilTextChange(summaryProductsQuantity,"1");
        /*
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        waitUntilUrlContains("order");
    }


    String getActionElementXpath(String productName, String actionName, String iconName) {

        actionInCartXpath = actionInCartXpathPrefix + productName + actionInCartXpathMiddleFirst + actionName + actionInCartXpathMiddleSecond + iconName + actionInCartXpathSuffix;
        return actionInCartXpath;
    }

    WebElement getActionElementInTheCart(String productName, String actionName, String iconName) {

        actionInCartXpath = getActionElementXpath(productName, actionName, iconName);
        WebElement actionInCartElementFound = find_element_by_xpath(actionInCartXpath);
        return actionInCartElementFound;
    }

    public void verifyAmount(String amountValue) {
        waitUntilElementVisible(totalPriceElement);
        checkIfTextIsExpected(totalPriceElement, amountValue);
    }

    public void verifyTheProductInCartDeleted(String productName, String actionName, String iconName) {
        actionInCartXpath = getActionElementXpath(productName, actionName, iconName);
        waitUntilElementInvisible(actionInCartXpath);

    }

}

