package com.globalhome.web;

import com.globalhome.web.pages.GlobalMyStorePage;
import com.globalhome.web.utilities.DriverProvider;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions.*;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    private final GlobalMyStorePage MyStoreProductPage;

//Constructor
    public StepDefinitions(){
        MyStoreProductPage = new GlobalMyStorePage(DriverProvider.getDriver());
        }

    @Given("User is on my store product home page")
        public void user_Is_On_My_Store_Product_Home_Page() {
            MyStoreProductPage.openStartPage();
        }

    @When("user verify the page title {string}")
    public void userVerifyThePageTitle(String PageTitle) {
            MyStoreProductPage.selectPageTitle(PageTitle);
    }



    @And("User selects productBlock as {string} and sub-Category as {string} and add the productCategory as {string}, size as {string}, colour as {string}, productName as {string}, quantity as {string}, actionNow as {string}, actionNext as {string}")
    public void user_selects_product_block_as_and_sub_category_as_and_add_the_product_category_as_size_as_colour_as_product_name_as_quantity_as_action_now_as_action_next_as(String productBlock, String subCategory, String productCategory, String size, String colour, String productName, String quantity, String actionNow, String actionNext){
        MyStoreProductPage.addProductInCart(productBlock, subCategory, productCategory,  size,  colour,  productName,  quantity, actionNow,  actionNext);

    }

    @And("user adds")
    public void user_adds(DataTable addItemToCartTable) {
        List<Map<String,String>> productlist = addItemToCartTable.asMaps(String.class, String.class);

        //[,,,,]
        /*
        [
            {k:v,k:v},
            {k:v,k:v},
            {}
        ]

         */

        for(Map<String,String> eachColumn :productlist){
           // eachColumn.get("Product"),("")
        }

    }
    @When("User selects productBlock as {string} and")
    public void user_selects_product_block_as_and(String productBlock, io.cucumber.datatable.DataTable addItemToCartTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        String subCategory = null;
        String productCategory = null;
        String size=null;
        String colour=null;
        String productName=null;
        String quantity=null;
        String actionNow=null;
        String actionNext=null;
        List<Map<String,String>> productlist = addItemToCartTable.asMaps(String.class, String.class);
        for(Map<String,String> eachColumn :productlist){
            // eachColumn.get("Product"),("")
            //      | subcategory | ProductCategory | Size | Colour | ProductName                 | quantity  |  actionNow  | actionNext          |
            subCategory=eachColumn.get("subcategory");
            productCategory=eachColumn.get("ProductCategory");
            size=eachColumn.get("Size");
            colour=eachColumn.get("Colour");
            productName=eachColumn.get("ProductName");
            quantity=eachColumn.get("quantity");
            actionNow=eachColumn.get("actionNow");
            actionNext=eachColumn.get("actionNext");

            MyStoreProductPage.addProductInCart(productBlock, subCategory, productCategory,  size,  colour,  productName,  quantity, actionNow,  actionNext);

        }



    }
    @When("user clicks Link Title {string}")
    public void user_clicks_link_title(String linkToClickFromTitle) {
        // Write code here that turns the phrase above into concrete actions
        MyStoreProductPage.userClicksLinkTitle(linkToClickFromTitle);
    }

    @Then("user should be able to view the item in cart {string} page")
    public void user_should_be_able_to_view_the_item_in_cart_page(String cartTitle) {
        // Write code here that turns the phrase above into concrete actions
        MyStoreProductPage.viewCart(cartTitle);


    }

    @Then("user verify the products")
    public void user_verify_the_products(io.cucumber.datatable.DataTable productInCarts) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        List<Map<String,String>> expectedProductlist = productInCarts.asMaps(String.class,String.class);
        List<Map<String,String>> actualProductList = MyStoreProductPage.verifyProductList();


        System.out.println("expectedProductlist "+ expectedProductlist);
        System.out.println("actualProductList "+ actualProductList);

        //assertTrue(expectedProductlist.containsAll(actualProductList));
        assertTrue(new HashSet<Map<String, String>>(actualProductList).equals(new HashSet<Map<String, String>>(expectedProductlist)));
  /*
            actualProductList as List<Map{k:v}> --> iterator every list and match for every key to every  <-- expectedProductList as List<Map{k:v}>
         */
        List<Map<String,String>> unMatchedRecords = actualProductList.parallelStream().filter(actualProductSearchData ->
                expectedProductlist.parallelStream().noneMatch( expectedProductMap ->
                        actualProductSearchData.entrySet().stream().noneMatch(actualProductSearchValue ->
                                expectedProductMap.entrySet().stream().noneMatch(expectedProductValue ->
                                        (expectedProductValue.getKey().equals(actualProductSearchValue.getKey()) &&
                                                expectedProductValue.getValue().equals(actualProductSearchValue.getValue()))))
                )).collect(Collectors.toList());
        System.out.println(unMatchedRecords.size());
        for(Map<String,String> unMatchedRecord: unMatchedRecords){
            System.out.println(unMatchedRecord);
        }
        assertEquals(unMatchedRecords.size(),0);


    }

    @And("user {string} the {string} in the cart")
    public void user_action_item_in_the_cart(String actionName, String productName) {
        switch(actionName) {
            case "Add":
                MyStoreProductPage.actionTheProductInCart(productName,"Add","icon-plus");
                break;
            case "Delete":
            case "Remove":
            case "Subtract":
                MyStoreProductPage.actionTheProductInCart(productName,"Delete","icon-trash");
                MyStoreProductPage.verifyTheProductInCartDeleted(productName,"Delete","icon-trash");
                break;
            default:
                //fail for any other case
                assertTrue(Boolean.FALSE);
                return;
        }
    }

    ////p[@class='product-name']//a[text()='Printed Summer Dress']//../..//..//a[@title='Add']

    //p[@class='product-name']//a[text()='Printed Summer Dress']//../..//..//a[@title='Delete']

    @And("user verify summary product is {string}")
    public void user_verify_summary_product_is(String summaryProduct) {
        MyStoreProductPage.verifySummaryProduct(summaryProduct);

    }

    @And("user verify the total amount is {string}")
    public void user_verify_the_amount(String amountValue) {
        MyStoreProductPage.verifyAmount(amountValue);
    }

    @And("user again add the Faded Short Sleeve T-shirts item in cart {string}")
    public void user_again_add_the_faded_short_sleeve_t_shirts_item_in_cart(String string) {
        // Write code here that turns the phrase above into concrete actions

    }
    @And("user remove the evening dresses item from cart {string}")
    public void user_remove_the_evening_dresses_item_from_cart(String string) {
        // Write code here that turns the phrase above into concrete actions

    }
    @And("user validate the summary page")
    public void user_validate_the_summary_page(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.

    }
    @And("user verify the {string} button on cart page")
    public void user_verify_the_button_on_cart_page(String string) {
        // Write code here that turns the phrase above into concrete actions
    }


}
