@LottoPayoutReport
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: LottoPayoutReport

  Scenario Outline: LottoPayoutReport
    Given SuperUser is logged into SU
    And they search <clubName> club
    And <clubName> club is displayed
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Payouts" page
    Then Lotto payout reports are available
    And Lotto payout report can be downloaded

    Examples:
      | clubName |
      | "Stormers Rugby Club" |
      | "Springboks Rugby" |
      | "Sharks Rugby Club" |
      | "Durban Daily" |
#      | "Donnamore Daily" | https://clubforce.atlassian.net/browse/NG-488