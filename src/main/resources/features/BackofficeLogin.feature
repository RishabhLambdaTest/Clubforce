@Win10ChromeLatest @Win11ChromeLatest @MacSafari  @WinFirefoxLatest
@ProdRegressionTest

Feature: BackofficeLogin

  Background:
    Given user is on the backoffice login page

  Scenario: Invalid mail trigger error
    When admin enter "invalid" login details
    Then admin login page reacts accordingly to "invalid"

  Scenario: Incorrect username login details are rejected
    When admin enter "bad username" login details
    Then admin login page reacts accordingly to "bad username"

  Scenario: Incorrect password login details are rejected
    When admin enter "bad password" login details
    Then admin login page reacts accordingly to "bad password"

  Scenario: Correct SuperUser native login on Backoffice login page
    When admin enter "correct superuser" login details
    Then admin login page reacts accordingly to "correct superuser on Backoffice login"

  @ProdCriticalCheck
  Scenario: Correct Backoffice native login details are accepted
    When admin enter "correct backoffice" login details
    Then admin login page reacts accordingly to "correct backoffice"

  @excludeFromProd @excludeFromSandbox
  Scenario: Log in as lotto calculations admin
    When admin enter "lotto calculations club" login details
    Then admin login page reacts accordingly to "correct backoffice"