@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Revenue report

#  Revenue page pagination scenario in pagination feature
#  Revenue page sorting in sorting feature

  Scenario: Search by source on revenue report
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can search by source

  Scenario: Search by type on revenue report
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can search by type

  Scenario: Search by status on revenue report
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can search by order status

  Scenario: Search by date ranges on revenue report
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can search using date ranges

  Scenario: Search by product on revenue report
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can search by product

  Scenario: Search using all filters on revenue report
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can search using all filters

  Scenario: Download revenue report CSV
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then ClubAdmin can download revenue report CSV

  Scenario: Order ID link brings club admin to the order details
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Revenue report" page
    Then they go to order details page by clicking order ID link
    When they click the back button on order details page
    Then they are brought back to the Revenue port page with the same filters as the previously had