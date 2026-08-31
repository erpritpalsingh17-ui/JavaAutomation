@erail
Feature: eRail station search
  As a traveller
  I want to search source stations and select a journey date
  So that I can validate the eRail station search experience

  Scenario: Validate From station dropdown and date
    Given I open the eRail application
    When I click on the From field
    And I clear the From field
    And I enter "DEL" in the From field
    Then I should see the station dropdown
    And I select the 4th station from the dropdown
     And I validate the station list with Excel data
     And I select a date 30 days from today
