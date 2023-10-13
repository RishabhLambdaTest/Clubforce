@Win10ChromeLatest @Memberships
@excludeFromProd
@excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Membership dashboard

  Scenario: Membership dashboard
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    Then Membership on dashboard works as expected

