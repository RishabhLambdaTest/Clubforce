@Win10ChromeLatest @WinEdgeLatest
@excludeFromProd
@excludeFromSandbox
# doesn't work on SBX because there is a server error from BE https://clubforce.atlassian.net/browse/CE-532
# noinspection SpellCheckingInspection
Feature: Feature toggle variations

  Scenario: Checkout is OFF for club
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When Checkout is "disabled" for club
    And user go to club homepage
    Then Checkout is "not visible" on page

  Scenario: Checkout is ON for club
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When Checkout is "enabled" for club
    And user go to club homepage
    Then Checkout is "visible" on page

  Scenario: Lotto OFF PaymentProvider ON
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When PaymentProvider is "enabled" for club
    And Lotto is "disabled" for club
    And SuperUser logs in as ClubAdmin
    And Lotto option is "not visible"

  Scenario: Lotto ON PaymentProvider OFF
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When PaymentProvider is "disabled" for club
    And Lotto is "enabled" for club
    And SuperUser logs in as ClubAdmin
    Then PaymentProvider option is "not visible"
    And Lotto option is "visible"

  Scenario: Lotto OFF PaymentProvider OFF
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When PaymentProvider is "disabled" for club
    And Lotto is "disabled" for club
    And SuperUser logs in as ClubAdmin
    Then PaymentProvider option is "not visible"
    And Lotto option is "not visible"

  Scenario: Lotto ON PaymentProvider ON
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When PaymentProvider is "enabled" for club
    And Lotto is "enabled" for club
    And SuperUser logs in as ClubAdmin
    Then PaymentProvider option is "visible"
    And Lotto option is "visible"

  Scenario: Enable Lotto TestMonthlyLotto (Lotto not set up)
    Given SuperUser is logged into SU
    When they search "TestMonthlyLotto" club
    And "TestMonthlyLotto" club is displayed
    Then Lotto is "enabled" for club
    And Checkout is "enabled" for club
    And SuperUser logs in as ClubAdmin
    Then Lotto option is "visible" for "TestMonthlyLotto"

  @excludeFromSandbox
  Scenario: Disable Lotto TestMonthlyLotto (Lotto not set up)
    Given SuperUser is logged into SU
    When they search "TestMonthlyLotto" club
    And "TestMonthlyLotto" club is displayed
    Then Lotto is "disabled" for club
    And SuperUser logs in as ClubAdmin
    Then Lotto option is "not visible" for "TestMonthlyLotto"

  Scenario: Membership is OFF for club (Membership set up)
    Given SuperUser is logged into SU
    And they search "penny" club
    And "pennybridge" club is displayed
    When Membership is "disabled" for club
    And SuperUser logs in as ClubAdmin
    Then Membership set up is "not visible" for "pennybridge"

  @excludeFromSandbox
  Scenario: Membership is ON for club
    Given SuperUser is logged into SU
    And they search "TestMonthlyLotto" club
    And "TestMonthlyLotto" club is displayed
    When Membership is "enabled" for club
    And SuperUser logs in as ClubAdmin
    Then Membership set up is "visible" for "TestMonthlyLotto"

  @excludeFromSandbox
  Scenario: Membership is OFF for club (Membership not set up)
    Given SuperUser is logged into SU
    And they search "TestMonthlyLotto" club
    And "TestMonthlyLotto" club is displayed
    When Membership is "disabled" for club
    And SuperUser logs in as ClubAdmin
    Then Membership set up is "not visible" for "TestMonthlyLotto"

  @excludeFromSandbox
  Scenario: Enable Comortais in SU
    Given SuperUser is logged into SU
    And they search "TestMonthlyLotto" club
    And "TestMonthlyLotto" club is displayed
    When Comortais is "enabled" for club
    And SuperUser logs in as ClubAdmin
    Then Comortais set up is "visible" for "TestMonthlyLotto"

  @excludeFromSandbox
  Scenario: Disable Comortais in SU
    Given SuperUser is logged into SU
    And they search "TestMonthlyLotto" club
    And "TestMonthlyLotto" club is displayed
    When Comortais is "disabled" for club
    And SuperUser logs in as ClubAdmin
    Then Comortais set up is "not visible" for "TestMonthlyLotto"
