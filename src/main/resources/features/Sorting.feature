@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Sorting

  Scenario Outline: Sorting checks for <pageArea> in Backoffice
    Given SuperUser is logged into SU
    And they search <clubName> club
    And <clubName> club is displayed
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice <page> page
    Then sorting works for <pageArea>
    Examples:
      | clubName           | page                        | pageArea             |
      | "Donnamore"        | "Membership reports"        | "memberships reports"|
      | "Donnamore"        | "Promotions"                | "promotions"         |
      | "pennybridgerugby" | "Search"                    | "members"            |
      | "pennybridgerugby" | "Revenue report"            | "revenues"           |
      | "pennybridgerugby" | "Finance Orders"            | "ordersBO"           |
      | "pennybridgerugby" | "Website Articles"          | "articles"           |
      | "pennybridgerugby" | "Users"                     | "users"              |
      | "Ulster Rugby"     | "Auto Renewals"             | "auto renewals"      |
      | "Ulster Rugby"     | "Website Sections and Pages"| "sections"           |
      | "Ulster Rugby"     | "Website Sections and Pages"| "pages"              |

  Scenario: Sorting checks for orders page in MyAccount
    Given ClubMember is logged into MyAccount
    And ClubMember goes to "Orders" page
    Then sorting works for "ordersMA"
