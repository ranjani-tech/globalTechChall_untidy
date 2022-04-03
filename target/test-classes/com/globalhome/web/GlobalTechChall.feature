@Regression
@endtoend

Feature: Find product checkout page and total cost of the product

  @AddAndRemoveProduct
  Scenario: end to end journey till product checkout page.
    Given User is on my store product home page
    When user verify the page title "My Store"
    #And User selects productBlock as "WOMEN" and sub-Category as "DRESSES" and add the productCategory as "Evening Dresses", size as "S", colour as "Beige", productName as "Printed Dress", quantity as "1", actionNow as "Add to Cart", actionNext as "Continue Shopping"
    #And User selects productBlock as "WOMEN" and sub-Category as "TOPS" and add the productCategory as "T-shirts", size as "M", colour as "Blue", productName as "Faded Short Sleeve T-shirts", quantity as "1", actionNow as "Add to Cart", actionNext as "Continue Shopping"
    #And User selects productBlock as "WOMEN" and sub-Category as "DRESSES" and add the productCategory as "Summer Dresses", size as "M", colour as "Orange", productName as "Printed Summer Dress", quantity as "1", actionNow as "Add to Cart", actionNext as "Continue Shopping"
    And User selects productBlock as "WOMEN" and
      | subcategory | ProductCategory | Size | Colour | ProductName                 | quantity  |  actionNow  | actionNext        |
      | TOPS        | T-shirts        | M    | Blue   | Faded Short Sleeve T-shirts | 1         | Add to Cart | Continue Shopping |
      | DRESSES     | Evening Dresses | S    | Beige  | Printed Dress               | 1         | Add to Cart | Continue Shopping |
      | DRESSES     | Summer Dresses  | M    | Orange | Printed Summer Dress        | 1         | Add to Cart | Continue Shopping |
    And user clicks Link Title "View my shopping cart"
    Then user should be able to view the item in cart "SHOPPING-CART SUMMARY" page
    And user verify the products
      | productname                 | amount | quantity |
      | Printed Dress               | $50.99 | 1 |
      | Faded Short Sleeve T-shirts | $16.51 | 1 |
      | Printed Summer Dress        | $28.98 | 1 |
    And user verify summary product is "3 Products"
    And user verify the total amount is "$98.48"
    And user "Add" the "Faded Short Sleeve T-shirts" in the cart
    And user verify the products
      | productname                 | amount | quantity |
      | Printed Dress               | $50.99 | 1 |
      | Faded Short Sleeve T-shirts | $33.02 | 2 |
      | Printed Summer Dress        | $28.98 | 1 |
    And user verify summary product is "4 Products"
    And user verify the total amount is "$114.99"
    And user "Remove" the "Printed Dress" in the cart
    And user verify the products
      | productname                 | amount | quantity |
      | Faded Short Sleeve T-shirts | $33.02 | 2 |
      | Printed Summer Dress        | $28.98 | 1 |
    And user verify summary product is "3 Products"
    And user verify the total amount is "$64.00"




