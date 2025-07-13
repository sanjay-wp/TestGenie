Feature: Google Search Functionality
  As a user of Google search
  I want to search for information
  So that I can find what I'm looking for

  Background:
    Given I am on the KPN Fresh home page

  @search @regression @sanity
  Scenario: Search for a topic
    When I search for "selenium automation"
    Then I should see search results
    And the search results should contain "selenium"

  @search @regression
  Scenario Outline: Search for different topics
    When I search for "<topic>"
    Then I should see search results
    And the search results should contain "<keyword>"

    Examples:
      | topic           | keyword    |
      | java testing    | test       |
      | web automation  | automation |
      | cucumber bdd    | cucumber   |