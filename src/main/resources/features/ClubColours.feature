@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: ClubColours

  Scenario Outline: Change club colours in BO
    Given SuperUser is logged into SU
    And they search "Durban Daily" club
    And "Durban Daily" club is displayed
    And Lotto is "enabled" for club
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Theme" page
    And "ClubAdmin" changes primary club colour to <primaryColour>
    And "ClubAdmin" changes secondary club colour to <secondaryColour>
    Then ClubAdmin go to Backoffice "Dashboard" page
    And dashboard displays <primaryColour> in first circle
    And dashboard displays <secondaryColour> in second circle
    When user go to lotto club homepage
    Then <primaryColour> and <secondaryColour> are displayed on website
    Examples:
      | primaryColour | secondaryColour |
      | "Blue"  | "Green"   |
      | "Red"   | "Teal"    |
      | "Amber" | "Purple"  |

  Scenario Outline: Change club colours in SU
    Given SuperUser is logged into SU
    When they search "Durban Daily" club
    Then they go to "Step 3" in SU for "Durban Daily"
    And "SuperUser" changes primary club colour to <primaryColour>
    And "SuperUser" changes secondary club colour to <secondaryColour>
    And Lotto is "enabled" for club
    When "LottoClubAdmin" is logged into Backoffice
    And dashboard displays <primaryColour> in first circle
    And dashboard displays <secondaryColour> in second circle
    When user go to lotto club homepage
    Then <primaryColour> and <secondaryColour> are displayed on website
    Examples:
      | primaryColour | secondaryColour |
      | "Blue"  | "Purple" |
      | "Red"   | "Green"  |
      | "Amber" | "Teal"   |