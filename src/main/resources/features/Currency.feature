@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Currency

  Scenario Outline: Currency check in BO
    Given SuperUser is logged into SU
    And they search <clubName> club
    And <clubName> club is displayed
    And SuperUser logs in as ClubAdmin
    And <currencySymbol> is displayed on dashboard page
    And ClubAdmin go to Backoffice "Finance Orders" page
    And <currencySymbol> is displayed on orders page
    And ClubAdmin go to Backoffice "Auto Renewals" page
    And <currencySymbol> is displayed on auto renewals page
    And ClubAdmin go to Backoffice "Payouts" page
    And <currencySymbol> is displayed on payouts page
    Examples:
      | clubName | currencySymbol |
      | "genIrelandLotto401883" | "Euro" |
      | "genScotlandLotto294128" | "Pound" |
      | "genWalesLotto996725" | "Pound" |
      | "genEnglandLotto743077" | "Pound" |


  Scenario Outline: Currency check in SU
    Given SuperUser is logged into SU
    And they search <clubName> club
    And <currencySymbol> is displayed on su accounts page
    Examples:
      | clubName | currencySymbol |
      | "Durban Daily (Do not touch)" | "Euro" |
      #    1 Ireland club = stripe setup - currency set
      | "Ulster Rugby" | "Pound" |
      #    1 Northern Ireland club = stripe setup - currency set
      | "Cardiff Rugby Club" | "Pound" |
      #    1 Wales club = stripe setup - currency set
      | "genEnglandMandL785960" | "Pound" |
      #    1 England club = stripe setup - currency set
      | "genScotlandLotto374031" | "n/a" |
      #    1 Scotland club = stripe NOT setup - currency NOT set








