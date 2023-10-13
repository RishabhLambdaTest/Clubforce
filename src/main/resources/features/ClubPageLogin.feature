@Win10ChromeLatest @Win11ChromeLatest @WinEdgeLatest @WinFirefoxLatest @pixel6os12
#@MacSafari
@ProdRegressionTest

Feature: ClubPageLogin

  Background:
    Given user go to club homepage
    And they click homepage account icon

  Scenario: Unrecognised mail trigger Create Account modal
    When member enter a "unrecognised" mail into club page sign-in modal
    Then club page login modal reacts accordingly to "unrecognised" mail

  Scenario: Recognised mail allows user to sign in
    When member enter a "recognised" mail into club page sign-in modal
    Then club page login modal reacts accordingly to "recognised" mail

