@Win10ChromeLatest @WinEdgeLatest
@ProdRegressionTest @excludeFromSandbox

Feature: NotificationsPage

  Scenario Outline: Check notifications page works as expected in My Account
    Given ClubMember is logged into MyAccount
    And ClubMember goes to "Notifications" page
    When ClubMember performs action <action>
    And signs out of their Account
    Then ClubMember is logged into MyAccount
    And ClubMember goes to "Notifications" page
    And updated notifications changes remain for <action>
    Examples:
    | action |
    | "unsubscribesFromAllClubs" |
    | "subscribesToAllClubs" |
