@smoke
Feature: Google search
  As a user
  I want to search on Google
  So that I can validate the search page works

  Scenario: Search for a keyword
    Given user is on the Google homepage
    When user searches for "Selenium"
    Then the search query should be submitted for "Selenium"
