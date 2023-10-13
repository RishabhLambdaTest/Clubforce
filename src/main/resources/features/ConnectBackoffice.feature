@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox

Feature: Connect Backoffice

  Scenario: Connect groups empty state BO page
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Connect groups" page
    Then the page shows no groups

  Scenario: Update group managers
    Given "MembershipClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Connect groups" page
    And ClubAdmin updates group managers for a group
#    Then updated group manager access email shows in my mail client https://clubforce.atlassian.net/browse/CE-893

#  Scenario: Remove GM role from user  https://clubforce.atlassian.net/browse/CAP-1185
#    Given "MembershipClubAdmin" is logged into Backoffice
#    And ClubAdmin go to Backoffice "Connect groups" page
#    And ClubAdmin removes role for the GM of one of the groups
#    And ClubAdmin go to Backoffice "Connect groups" page
#    Then user should not be displayed as a group manager on the Connect page for any group
#    And ClubAmin assigns GM role back to user
#    And ClubAdmin go to Backoffice "Connect groups" page
#    Then user should be displayed in GM list on connect page
#    And user can be added again to the group
