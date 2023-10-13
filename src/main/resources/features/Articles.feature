@Win10ChromeLatest @MacSafari @WinFirefoxLatest
@ProdRegressionTest
# noinspection SpellCheckingInspection

Feature: Article Load More

  @ipadAiros13 @ipadAiros15 @iPhone7os12 @iPhone8os15 @iPhone11os14 @iPhoneXSos15 @pixel6os12
  Scenario: Load More
    Given user go to club homepage
    Then they can expand the articles section using the Load More button

  Scenario: Update Article
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Articles" page
    When ClubAdmin "updates" an article
    Then the article on the website is updated
