@Win10ChromeLatest @WinFirefoxLatest
@ProdRegressionTest @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Dashboard

  Scenario Outline: Checking Dashboard buttons
    Given SuperUser is logged into SU
    And they search "pennybridge" club
    And "pennybridge" club is displayed
    And Lotto is "enabled" for club
    When "ClubAdmin" is logged into Backoffice
    Then the <button> works as expected on the <page> tile
    Examples:
    | button | page |
    | "Customise button" | "Your club's website" |
    | "View website button" | "Your club's website" |
    | "Manage draws button" | "Manage Lotto" |
    | "View all articles button" | "View all articles" |
    | "View all messages button" | "Contact messages" |

  Scenario: Verify zendesk chat popup
    Given "ClubAdmin" is logged into Backoffice
    Then ZenDesk chat pops up on demand
