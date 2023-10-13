@Win10ChromeLatest @WinEdgeLatest
@ProdRegressionTest

Feature: WebsiteSettings

  Background:
    Given "ClubAdmin" is logged into Backoffice

  Scenario: Club Name change
    And ClubAdmin go to Backoffice "Website Theme" page
    When they change "club name" of their club
    Then their website "club name" updates as expected

  Scenario: Sponsor
    And ClubAdmin go to Backoffice "Website Sponsor" page
    When they change "sponsor" of their club
    Then their website "sponsor" updates as expected

  Scenario: Banner details change
    And ClubAdmin go to Backoffice "Website Banner" page
    When they change "banner details" of their club
    Then their website "banner details" updates as expected

  Scenario: Banner View More button disable
    And ClubAdmin go to Backoffice "Website Banner" page
    When they change "disable View More button" of their club
    Then their website "disable View More button" updates as expected

  Scenario: Banner View More button enable
    And ClubAdmin go to Backoffice "Website Banner" page
    When they change "enable View More button" of their club
    Then their website "enable View More button" updates as expected

  @excludeFromProd
  Scenario: Menu item max instances
    And ClubAdmin go to Backoffice "Website Navigation" page
    When they change "menu item instances" of their club
    Then their website "menu item instances" updates as expected

  Scenario: Menu URL change
    And ClubAdmin go to Backoffice "Website Navigation" page
    When they change "menu URL item" of their club
    Then their website "menu URL item" updates as expected

  Scenario: Menu Page change
    And ClubAdmin go to Backoffice "Website Navigation" page
    When they change "menu Page item" of their club
    Then their website "menu Page item" updates as expected
