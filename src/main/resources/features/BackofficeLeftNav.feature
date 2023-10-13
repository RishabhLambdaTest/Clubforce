@Win10ChromeLatest @Win11ChromeLatest
#@MacSafari
@ProdRegressionTest @excludeFromSandbox

Feature: AdminLeftNav

  Scenario: Click hamburger to toggle leftNav
    Given "ClubAdmin" is logged into Backoffice
    Then click hamburger to toggle leftNav
