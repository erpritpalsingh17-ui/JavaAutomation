@orangehrm
Feature: OrangeHRM login validation
  As a user of OrangeHRM
  I want to validate valid and invalid login credentials from an Excel sheet
  So that login behaviour is checked with data-driven testing

  Scenario: Validate login credentials from Excel
    Given I open the OrangeHRM login page
    When I validate each login from the OrangeHRM Excel sheet
    Then every OrangeHRM login result should match the expected outcome
