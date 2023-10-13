@Win10ChromeLatest @Win11ChromeLatest @WinFirefoxLatest
@ProdRegressionTest
@excludeFromSandbox
#@MacSafari https://clubforce.atlassian.net/browse/QTA-167

Feature: Backoffice Users page

  Background:
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Users" page

  Scenario: Users page validation
    Then their users page details are as expected

  Scenario: Search users
    Then they can search for users

  Scenario Outline: Member look up
    Then they can look up a member using the <type> field
    Examples:
      | type |
      | "firstname" |
      | "lastname" |
      | "email" |



