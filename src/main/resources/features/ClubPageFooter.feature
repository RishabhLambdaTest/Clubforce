@Win10ChromeLatest @Win11ChromeLatest @WinFirefoxLatest
@ProdRegressionTest
#@MacSafari https://clubforce.atlassian.net/browse/QTA-167
# noinspection SpellCheckingInspection

Feature: ClubPageFooter

  Scenario Outline: Checking ClubPageFooter links
    Given user go to club homepage
    Then the footer link <link> works as expected
    Examples:
    | link |
    | "About Clubforce" |
    | "Terms & Conditions" |
    | "Privacy Policy" |

