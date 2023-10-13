@Win10ChromeLatest
@excludeFromProd
@Lotto
# noinspection SpellCheckingInspection

Feature: Lotto

  @excludeFromSandbox @WinEdgeLatest
  Scenario: Verify empty shopping cart
    When user go to lotto club homepage
    And user opens shopping cart
    Then shopping cart is empty

  Scenario: Publish lotto for current draw
    Given "LottoClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Lotto Draws" page
    Then they can publish lotto for the current draw

  @excludeFromSandbox
  Scenario: Open Lotto past draw
    Given "LottoClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Lotto Draws" page
    Then they can open a past draw

#  @excludeFromSandbox @WinEdgeLatest TODO uncomment when https://clubforce.atlassian.net/browse/CE-1179 is fixed
#  Scenario: Playslip pdf is downloadable
#    Given "LottoClubAdmin" is logged into Backoffice
#    When ClubAdmin go to Backoffice "Lotto Playslip" page
#    Then ClubAdmin can download "Playslips" for Draw PDF

  @excludeFromSandbox @WinEdgeLatest
  Scenario: Lucky dip pdf is downloadable
    Given "LottoClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Lotto Playslip" page
    Then ClubAdmin can download "Lucky dips" for Draw PDF

#  @excludeFromSandbox @WinEdgeLatest TODO uncomment when https://clubforce.atlassian.net/browse/CE-1180 is fixed
#  Scenario: playslips csv is downloadable
#    Given "LottoClubAdmin" is logged into Backoffice
#    When ClubAdmin go to Backoffice "Lotto Playslip" page
#    Then Playslips CSV file can be downloaded

  @excludeFromSandbox
  Scenario: Set Lotto with a winner, pick two lucky dip winners
    Given "LottoClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Lotto Draws" page
    And they pick "a lotto winner" for the daily Lotto
    And they pick "a lucky dip winner" for the daily Lotto
    Then they finish the daily Lotto, preview and notify players

  @excludeFromSandbox @WinEdgeLatest
  Scenario: Check auto renewal orders in BO
    Given "AutoRenewalLottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Lotto Draws" page
    Then ClubAdmin checks how many draws have occurred
    And ClubAdmin go to Backoffice "Finance Orders" page
    Then Orders are displayed correctly for each user
    And ClubAdmin go to Backoffice "Auto Renewals" page
    Then renewal count is correct for each user

  @WinEdgeLatest
  #@MacSafari https://clubforce.atlassian.net/browse/QTA-167
  Scenario Outline: Share Lotto URL to social media
    Given "LottoClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Lotto Draws" page
    Then they can share link to Lotto on <socialMedia>
    Examples:
      | socialMedia |
      | "Facebook"  |
      | "LinkedIn"  |
      | "Twitter"   |

  @excludeFromSandbox @WinEdgeLatest
  Scenario Outline: Changing lotto date and Time
    Given SuperUser is logged into SU
    And they search "pennybridgerugby" club
    And "pennybridgerugby" club is displayed
    And Lotto is "enabled" for club
    When "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Lotto Draws" page
    And ClubAdmin changes <CutOffPeriod> for club
    And <CutOffPeriod> is updated on Dashboard
    And ClubAdmin go to Backoffice "Website Navigation" page
    And they add a Lotto Menu item
    And ClubAdmin is logged out of Backoffice
    And <CutOffPeriod> is updated on Website
    When "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Lotto Draws" page
    And ClubAdmin changes <CutOffPeriod> back
    Examples:
      | CutOffPeriod  |
      | "Time"        |
      | "Date"        |
      | "DateAndTime" |

  @MacSafari @WinEdgeLatest @excludeFromSandbox
  Scenario: Add and finish a ticket option
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Lotto Ticket Options" page
    Then ClubAdmin can "add new" ticket option
    And ClubAdmin go to Backoffice "Lotto Ticket Options" page
    Then ClubAdmin can "finish" ticket option