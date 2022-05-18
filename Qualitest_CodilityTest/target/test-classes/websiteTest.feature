Feature: Website Test
Background:
  Given I add four different products to my wishlist
|Bikini      |
|Hard top    |
|Watch Modern|
|Black trousers |

  Scenario: Modify my cart
    When I view my wishlist table
    Then I find total four selected items in my wishlist
    When I search for lowest price product
    And I am able to add the lowest price item to my cart
    Then I am able to verify the item in my cart