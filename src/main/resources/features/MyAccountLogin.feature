@Win10ChromeLatest @Win11ChromeLatest @WinEdgeLatest
@MacSafari
@pixel4os11 @pixel6os12 @huaweiP30os10 @galaxyS10os9 @galaxyS20os10 @galaxyS21o11 @galaxyS21os11 @iPhoneXSos15
@pixel7viewport @iPhone14viewport
@ProdRegressionTest
# noinspection SpellCheckingInspection
Feature: MyAccountLogin

  Background:
    Given user is on the My Account login page

  Scenario: Invalid mail trigger error
    When account holder enter a "invalid" mail into login modal
    Then myaccount login modal reacts to "invalid"

  Scenario: Recognised mail allows user to sign in
    When account holder enter a "recognised" mail into login modal
    Then myaccount login modal reacts to "recognised"
