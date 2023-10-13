@Win10ChromeLatest @Win11ChromeLatest @WinEdgeLatest
@ProdRegressionTest @excludeFromSandbox
#@MacSafari https://clubforce.atlassian.net/browse/QTA-167

Feature: SessionTransition

  @excludeFromProd
  Scenario: Logged in adminMember in BO can transition to homepage and MyAccount
    Given "AdminMember" is logged into Backoffice
    When they click BO account icon
    And they use expected transition path for "BO to homepage"
    When they click homepage account icon
    And they use expected transition path for "homepage to BO"
    And user go to club homepage
    When they click homepage account icon
    Then they use expected transition path for "homepage to MyAccount"

  Scenario: Logged in admin in BO can go to MyAccount
    Given "ClubAdmin" is logged into Backoffice
    When they click BO account icon
    Then they use expected transition path for "BO to MyAccount"
  
  @tester
  Scenario: Logged in admin in BO can go to homepage
    Given "ClubAdmin" is logged into Backoffice
    When they click BO account icon
    Then they use expected transition path for "BO to homepage"

  Scenario: Logged in admin on homepage can go to BO
    Given "ClubAdmin" is logged into Backoffice
    And user go to club homepage
    When they click homepage account icon
    Then they use expected transition path for "homepage to BO"

  Scenario: Logged in member on homepage can go to MyAccount
    Given ClubMember is logged into MyAccount
    And user go to club homepage
    When they click homepage account icon
    Then they use expected transition path for "homepage to MyAccount"
