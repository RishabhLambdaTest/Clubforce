@ProdRegressionTest @excludeFromSandbox @rish

Feature: Account

  @Win10ChromeLatest @WinFirefoxLatest
  Scenario: Account page change contact details
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Account" page
    And they can change contact details
    And ClubAdmin is logged out of Backoffice
    And "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Account" page
    Then updated contact details remain


