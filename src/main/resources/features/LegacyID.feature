@Win10ChromeLatest
@LegacyID
@excludeFromProd

# noinspection SpellCheckingInspection

Feature: LegacyID

  Scenario: Add and remove legacy IDs
    Given SuperUser is logged into SU
    When SuperUser selects the latest complete "Membership" account
    Then they can add 3 legacy IDs for the club
    And the IDs can be removed

  Scenario: Add already used legacy IDs
    Given SuperUser is logged into SU
    When SuperUser selects the latest complete "Membership" account
    Then they cannot use the same three legacy IDs as pennybridge
