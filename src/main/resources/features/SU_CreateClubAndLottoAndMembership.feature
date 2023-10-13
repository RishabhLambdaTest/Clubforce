@createClubAndLottoAndMembership
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: createClubAndLottoAndMembership

  Scenario Outline: Create new club and lotto and membership
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
    And user click "Set up membership" tile on Dashboard
    And ClubAdmin completes step 1 for a new "adult" "free plan" plan with "1" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And "Free" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And new club website can be visited
    And they follow the Lotto link for <country> club
    And they can add "losing" Lotto to cart "without autoRenewal" for "generated" club
    And cart has their "Lotto" purchase
    And AH purchased lotto and membership products
    Examples:
      | country    | GB    | sport   | product | countryAccountType    |
      | "Northern"  | "NONE" | "NONE" | "MandL" | "NorthernIreland Individual"  |



