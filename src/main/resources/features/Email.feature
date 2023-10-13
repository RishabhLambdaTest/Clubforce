@Win10ChromeLatest
@RunOnSandbox
Feature: Email from club admin

  Scenario Outline: Send emails from Club admin to groups and individuals
    Given SuperUser is logged into SU
    And they search "qaMailTestClub" club
    And "qaMailTestClub" club is displayed
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Email" page
    When ClubAdmin selects to compose a new email
    And they compose and send an email to recipient <recipient>
    Then recipients gets mail from ClubAdmin

Examples:
  | recipient |
  | "MembershipGroup" |
  | "LottoGroup" |
  | "NotInAnyGroupUser" |
  | "BothGroups" |
