Feature: KPN Fresh Home Page Functionality
  As a user of KPN Fresh website
  I want to use the basic features of the home page
  So that I can shop for products effectively

  Background:
    Given I am on the KPN Fresh home page

  @search @regression @sanity
  Scenario: Search for a product
    When I search for "vegetables"
    Then I should see search results
    And the search results should contain "vegetables"
#
#  @navigation @regression
#  Scenario: Navigate through categories
#    When I click on category "Groceries"
#    Then I should be on the "Groceries" category page
#    And I should see product listings
#
#  @newsletter @smoke
#  Scenario: Subscribe to newsletter
#    When I enter email "test@example.com" in newsletter field
#    And I click subscribe button
#    Then I should see newsletter subscription success message
#
#  @cart @smoke
#  Scenario: Check shopping cart
#    When I click on the shopping cart icon
#    Then I should see the mini cart
#    And the cart should be empty
#
#  @search @regression
#  Scenario Outline: Search for different products
#    When I search for "<product>"
#    Then I should see search results
#    And the search results should contain "<product>"
#
#    Examples:
#      | product    |
#      | fruits     |
#      | bread      |
#      | milk       |
#      | vegetables |