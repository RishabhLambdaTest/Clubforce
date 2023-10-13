@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Membership report

#  Membership report page pagination scenario in pagination feature
#  Membership report page sorting in sorting feature

  Scenario: Search by status on membership report page
    Given "MembershipClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Membership reports" page
    Then ClubAdmin can search by membership status

  Scenario: Search by DOB on membership report page
    Given "MembershipClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Membership reports" page
    Then ClubAdmin can search for membership record by DOB range

  Scenario: Search by gender on membership report page
    Given "MembershipClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Membership reports" page
    Then ClubAdmin can search for membership records by gender

#  Scenario: Search by plan name on membership report page TODO uncomment when https://clubforce.atlassian.net/browse/CE-557 is fixed
#    Given "MembershipClubAdmin" is logged into Backoffice
#    When ClubAdmin go to Backoffice "Membership reports" page
#    Then ClubAdmin can search for membership record by plan name

  Scenario: Membership report download quick report CSV
    Given "MembershipClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Membership reports" page
    Then ClubAdmin can download membership "quick" report CSV

  Scenario: Membership report download full report CSV
    Given "MembershipClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Membership reports" page
    Then ClubAdmin can download membership "full" report CSV