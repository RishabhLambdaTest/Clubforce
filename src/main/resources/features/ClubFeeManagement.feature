@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Club Fee Management

  Scenario Outline: View clubs membership transaction fee and and charges
    Given SuperUser is logged into SU
    When they search <clubName> club
    Then they can view <clubName> membership transaction fee and card charges
    Examples:
    |clubName              |
     # UK club
    | "Ulster Rugby"       |
    # Ireland club
    | "Leopards Rugby"     |

  Scenario Outline: Edit clubs membership transaction fee and card charges
    Given SuperUser is logged into SU
    When they search <clubName> club
    And they can edit <clubName> membership transaction fee to <feePercentage>
    And they can edit <clubName> membership card charges to <feeAmount>
    Examples:
      | clubName          | feePercentage | feeAmount |
      # UK club
      | "Ulster Rugby"    | "10.5"        | "0.25"    |
      | "Ulster Rugby"    | "33.3"        | "0.82"    |
      # Ireland club
      | "Leopards Rugby"  | "10.5"        | "0.25"    |
      | "Leopards Rugby"  | "33.3"        | "0.82"    |