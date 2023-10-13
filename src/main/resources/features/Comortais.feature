@Win10ChromeLatest

# noinspection SpellCheckingInspection
Feature: Comortais

  Scenario Outline: Pick a club and link to a Comortais club
    Given SuperUser is logged into SU
    And they search "pennybridge" club
    And "pennybridge" club is displayed
    When Comortais is "enabled" for club
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Comortais" page
    And CA links <club> club to Comortais
    And CA goes to club homepage via dashboard
    Then CA can see content on Fixture and Results page for <club>
    And clicking View All shows the expected Comortais page for <club>

    Examples:
      | club |
      | "Dublin Bus FC" |
      | "St James Athletic"  |
      | "East Galway United" |
      | "Naas AFC" |
      | "Murroe"   |
      | "Benbulben FC" |
      | "Cappoquin Railway F.C." |
