@Win10ChromeLatest @Memberships
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Membership registrations

  Background:
    Given "membershipPlansAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Search" page

  Scenario Outline: CA checks old registrations
    And ClubAdmin searches for <name> on Search page
    Then registration details are displayed for <name>
    Examples:
      | name |
      | "Deloris" |
      | "Selma"   |
      | "Rusty"   |
      | "Joni"    |
      | "Adan"    |
      | "Darin"   |
      | "Dante"   |

  Scenario Outline: CA edits old registration
    And ClubAdmin searches for <name> on Search page
    Then registration details are displayed for <name>
    And ClubAdmin can edit registration details
    Examples:
      | name |
      | "Schwartz"  |
      | "Noble"     |