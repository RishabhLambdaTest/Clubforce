@createClubAndLotto
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: SUCreateClubAndLotto

  Scenario Outline: Create new club and lotto
    Given SuperUser is logged into SU
    When SuperUser creates a new <country> club with variables Governing Body <GB> and Sport <sport> and Product <product>
    And SUAdmin is logged out of SU
    Then they can get "SU new CA" mail and do process to log in
    And Stripe account is set up for <country> club
    And bank account is linked for <countryAccountType>
    And ClubAdmin go to Backoffice "Website Theme" page
    And ClubAdmin sets up theme page for club
    And ClubAdmin go to Backoffice "Website Banner" page
    And ClubAdmin sets up banner for club
    And ClubAdmin go to Backoffice "Website About Us" page
    And ClubAdmin "creates" an About Us page
    And ClubAdmin go to Backoffice "Website Articles" page
    And ClubAdmin "creates" an article
    And ClubAdmin go to Backoffice "Website Sponsor" page
    And ClubAdmin creates a sponsor
    And ClubAdmin go to Backoffice "Website Navigation" page
    And ClubAdmin creates multiple menu items
    And they create a "Lotto" product page
    And they fill out the Lotto Information pages
    And they fill out the <country> Lotto Settings pages
    And ClubAdmin go to Backoffice "Website Navigation" page
    And they add a Lotto Menu item
    Then CA-AH can log out of backoffice
    And new club website can be visited
    And they follow the Lotto link for <country> club
    And they can add "losing" Lotto to cart "without autoRenewal" for "generated" club
    And cart has their "Lotto" purchase
    And "signup loser" purchase "Lotto without autoRenewal" with "4111111111111111" card
    And "Lotto" order is displayed in my order in MyAccount
    And ClubMember goes to "Lotto" page
    And "newAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    And ClubAdmin cannot issue refund for <country> "lotto ticket" due to insufficient funds
    Examples:
      | country    | GB    | sport   | product | countryAccountType    |
      | "Scotland" | "NONE" | "NONE" | "Lotto" | "Scotland Individual" |
