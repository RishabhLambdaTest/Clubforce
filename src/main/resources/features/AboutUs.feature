@Win10ChromeLatest @Win11ChromeLatest @WinEdgeLatest @WinFirefoxLatest @MacSafari @rish
@ProdRegressionTest

Feature: About Us

  Scenario: Read more button present
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website About Us" page
    When ClubAdmin "updates" an About Us page
    Then the About Us on the website is updated

