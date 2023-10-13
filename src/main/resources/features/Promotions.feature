@Win10ChromeLatest
# noinspection SpellCheckingInspection
Feature: Promotions

  @excludeFromProd @excludeFromSandbox
  Scenario Outline: Create and buy promotion for Lotto when buying Membership - create new club
    Given SuperUser is logged into SU
    When SuperUser creates a new <country> club with variables Governing Body <GB> and Sport <sport> and Product <product>
    And SuperUser logs in as ClubAdmin
    Then promotions tab is "not displayed"
    And Stripe account is set up for <country> club
    And bank account is linked for <countryAccountType>
    And they create a "Lotto" product page
    And they fill out the Lotto Information pages
    And they fill out the <country> Lotto Settings pages
    Then promotions tab is "displayed"
    And ClubAdmin go to Backoffice "Promotions" page
    And a new promotion is created
    And ClubAdmin go to Backoffice "Website Navigation" page
    And they add a Lotto Menu item
    And user click "Set up membership" tile on Dashboard
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "999" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    Then "paid" plan is visible on CW using URL from Published Plans page
    And the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And buyer selects cart promotion which takes them to Lotto page
    And they can add "losing" Lotto to cart "without autoRenewal" for "generated" club
    And cart has their "Lotto" purchase
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card

    Examples:
      | country   | GB     | sport  | product | countryAccountType   |
      | "Ireland" | "NONE" | "NONE" | "Promo" | "Ireland Individual" |

  @excludeFromProd @excludeFromSandbox
  Scenario: Create and buy promotion for Lotto when buying Membership - existing club
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Promo" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Promotions" page
    And a new promotion is created
    And a promotion is edited
    And ClubAdmin go to Backoffice "Membership plans" page
    And "Promo" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And buyer selects cart promotion which takes them to Lotto page
    And they can add "losing" Lotto to cart "without autoRenewal" for "generated" club
    And cart has their "Lotto" purchase
    And user click Checkout to go pay
    And member purchases "promo included" memberships with "4000000000000077" card

  @excludeFromProd @excludeFromSandbox
  Scenario: Create and delete promotion for Lotto when buying Membership - existing club
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Promo" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Promotions" page
    And a new promotion is created
    And a promotion is deleted

