@Win10ChromeLatest @ProdRegressionTest
# noinspection SpellCheckingInspection

Feature: Page checks feature

  Scenario: Backoffice page checks
    Given "MembershipClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Account" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Contact Messages" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Comortais" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Connect groups" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Dashboard" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Finance Orders" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Lotto Draws" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Lotto Settings" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Membership plans" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Payouts" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Revenue report" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Search" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Users" page
    And user refreshes page
    And ClubAdmin go to Backoffice "Website About Us" page

  Scenario: My account page checks
    Then ClubMember is logged into MyAccount

  Scenario: Superuser page checks
    Given SuperUser is logged into SU

