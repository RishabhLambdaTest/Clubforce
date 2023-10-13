@Win10ChromeLatest @WinEdgeLatest
@pixel7viewport
#@MacSafari tab switch issue
Feature: Forgot password - get code

  Scenario: Password code for backoffice login
    Given user is on the backoffice login page
    When PwdRecoveryAdmin requests password code on backoffice page
    Then they can get "forget password" mail and do process to log in

  Scenario: Password code for homepage login
    Given user go to club homepage
    When PwdRecoveryMember requests password code on homepage
    Then they can get "forget password" mail and do process to log in

  Scenario: Password code for my account login
    Given user is on the My Account login page
    When PwdRecoveryAdmin requests password code on backoffice page
    Then they can get "forget password" mail and do process to log in
