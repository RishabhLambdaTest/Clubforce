@Win10ChromeLatest

Feature: SULogin

  Scenario: Incorrect username login details are rejected
    Given admin is on the SU login page
    When admin enter "bad username" login details
    Then admin login page reacts accordingly to "bad username"

  Scenario: Correct SuperUser native login details are accepted
    Given admin is on the SU login page
    When admin enter "correct superuser" login details
    Then admin login page reacts accordingly to "correct superuser"
