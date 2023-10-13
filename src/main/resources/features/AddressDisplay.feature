@Win10ChromeLatest @MacSafari @WinFirefoxLatest
@ProdRegressionTest @excludeFromSandbox

Feature: AddressDisplay

  Scenario Outline: Hide and show address
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Theme" page
    When admin set address toggle to <action>
    And user go to club contact page
    Then contact page reacts accordingly to <action>
    Examples:
    | action |
    | 'hide' |
    | 'show' |


