@Win10ChromeLatest
@ProdRegressionTest  @excludeFromSandbox

Feature: Social Media

  Scenario Outline: Delete and add social media links in backoffice, verify in frontend
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Account" page
    When social media links are <action> on Account page
    Then social media links are <action> on the club front page
    Examples:
      | action |
      | 'removed' |
      | 'added' |

  Scenario Outline: Check sharing works
  Given user go to club homepage
  When they open an article
  Then they can share article to <media>
    Examples:
      | media |
      | 'Facebook' |
      | 'Twitter' |
