@createClubAndMembership
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: SUCreateClubAndMembership

  Scenario Outline: Create new club and create membership
    Given SuperUser is logged into SU
    When SuperUser creates a new <country> club with variables Governing Body <GB> and Sport <sport> and Product <product>
    And SuperUser logs in as ClubAdmin
    And Stripe account is set up for <country> club
    And bank account is linked for <countryAccountType>
    And user click "Set up membership" tile on Dashboard
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "999" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And "paid" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "standard plan" are displayed in MyAccount
    Examples:
      | country   | GB     | sport  | product       | countryAccountType   |
      | "Wales"   | "NONE" | "NONE" | "Membership"  | "Wales Individual"   |
