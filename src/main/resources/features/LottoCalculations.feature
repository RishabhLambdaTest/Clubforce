@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection
Feature: LottoCalculations

  Scenario Outline: Check Lotto calculations
    Given user is on the backoffice login page
    And admin enter "lotto calculations club" login details
    When ClubAdmin go to Backoffice "Lotto Details" page
    Then user fills out the lotto information and settings pages
    And they fill in lotto ticket options with price per line set to <priceperline> <draws> <lines> <priceperticket>
    Examples:
      | priceperline | draws | lines | priceperticket |
      | "1" | "1" | "1" | "5" |
      | "2" | "2" | "2" | "6" |
      | "3" | "3" | "3" | "7" |
      | "4" | "4" | "4" | "8" |
      | "5" | "5" | "5" | "9" |


