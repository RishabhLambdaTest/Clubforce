@Win10ChromeLatest
@excludeFromSandbox
Feature: ClubLogo

  Scenario: Change logo
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Theme" page
    When admin changes logo
    And user go to club homepage
    Then logo is changed

