@Win10ChromeLatest @MacSafari @WinFirefoxLatest
@ProdRegressionTest

Feature: ClubSearch for SU support staff

  # noinspection SpellCheckingInspection
  Scenario Outline: Search club
    Given SuperUser is logged into SU
    When they search <clubName> club
    Then <clubName> club is displayed
    Examples:
      |  clubName |
      |  "pennybridge" |
      |  "NoSuchClub" |


