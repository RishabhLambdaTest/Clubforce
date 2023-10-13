@Win10ChromeLatest
@excludeFromSandbox @excludeFromProd
# noinspection SpellCheckingInspection

Feature: Pagination

  Scenario: Club Search pagination in SU
    Given SuperUser is logged into SU
    Then they can use the pagination

  Scenario: Check contact page pagination in BO
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Contact Messages" page
    Then they can use the pagination

  Scenario: Users Pagination in BO
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Users" page
    Then they can use the pagination

  Scenario: Check articles pagination
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Articles" page
    Then they can use the pagination

  Scenario: Check registrations pagination in BO
    Given "membershipPlansAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Search" page
    Then they can use the pagination

  Scenario: Check membership reports pagination in BO
    Given "membershipPlansAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Membership reports" page
    Then they can use the pagination

  Scenario: Check Connect group pagination in BO
    Given "membershipPlansAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Connect groups" page
    Then they can use the pagination

  Scenario: Check revenue report page pagination in BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Revenue report" page
    Then they can use the pagination

  Scenario: Check orders page pagination in BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    Then they can use the pagination

  Scenario: Check payouts page pagination in BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Payouts" page
    Then they can use the pagination

  Scenario: Check auto renewals pagination in BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Auto Renewals" page
    Then they can use the pagination

  Scenario: Check playslips pagination in BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    Then they can use the pagination

  Scenario: Check past draws pagination in BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Lotto Draws" page
    Then they can use the pagination

  Scenario: Check orders pagination in MA
    Given ClubMember is logged into MyAccount
    And ClubMember goes to "Orders" page
    Then they can use the pagination

  Scenario: Check payment methods pagination in MA
    Given "membertest25@clubforce.com" is logged into My Account
    And ClubMember goes to "Payment Methods" page
    Then they can use the pagination

  Scenario: Check active lotto tickets pagination in MA
    Given "membertest4@clubforce.com" is logged into My Account
    And ClubMember goes to "Lotto" page
    Then they can use the pagination for "active" tickets section

  Scenario: Check history lotto tickets pagination in MA
    Given "membertest4@clubforce.com" is logged into My Account
    And ClubMember goes to "Lotto" page
    Then they can use the pagination for "history" tickets section
