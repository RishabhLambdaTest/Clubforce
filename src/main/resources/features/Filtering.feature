@Win11ChromeLatest
@excludeFromSandbox @excludeFromProd

Feature: Filtering

  Scenario Outline: Filtering in SU
    Given SuperUser is logged into SU
    Then they can use filter to view <status> results
    Examples:
    | status |
    | "Pending SU" |
    | "Complete SU" |

    Scenario Outline: Filtering on orders page BO
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    Then they can use filter to view <status> results
    Examples:
      | status        |
      | "Pending BO"  |
      | "Complete BO" |
      | "Cancelled BO"|
      | "New BO"      |

  Scenario Outline: Filtering on orders page MA
    Given ClubMember is logged into MyAccount
    And ClubMember goes to "Orders" page
    Then they can use filter to view <status> results
    Examples:
      | status           |
      | "Pending MA"     |
      | "Complete MA"    |
      | "Cancelled MA"   |
      | "New MA"         |
      | "Refunded MA"    |
      | "Auto renewal MA"|

#    Scenario Outline: Filtering on auto renewals page BO
#    Given "LottoClubAdmin" is logged into Backoffice
#    And ClubAdmin go to Backoffice "Auto Renewals" page
#    Then they can use filter to view <status> results
#    Examples:
#      | status |
#      | "Inactive" |
#      | "Active" |
