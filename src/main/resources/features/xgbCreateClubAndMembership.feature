@createXGBClubAndMembership
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: XGBCreateClubAndMembership

  Scenario Outline: Create new XGB full flow
    Given SuperUser is logged into SU
    When SuperUser creates a new <country> XGB account
    And SUAdmin is logged out of SU
    And new XGBAdmin is logged into XGB
    And Admin go to XGB "Templates" page
    And Admin can see "Player" template
    And Admin can see "Non-player" template
    And Admin can add questions and activate template <template>
    Given SuperUser is logged into SU
    When SuperUser creates a new <country> club with variables Governing Body <GB> and Sport <sport> and Product <product>
    And SUAdmin is logged out of SU
    Then they can get "SU new CA" mail and do process to log in
    And Stripe account is set up for <country> club
    And bank account is linked for <countryAccountType>
#    And user click "Set up membership" tile on Dashboard
#    And they set up a new "adult" "paid plan" with "999" max subscriptions and "No" special questions
#    And they publish "New Plan"
#    And "paid" plan is visible on CW using URL from Published Plans page
#    Then the plan is selected on CW
#    And Member choose to continue
#    And membership buyer "mem0005@clubforce.com" logs in
#    And "single adult Standard" plan details filled in for "mem0005@clubforce.com" buyer
#    And memberships are added to cart
#    And user click Checkout to go pay
#    And member purchases "paid" memberships with "4000000000000077" card
#    And "Standard membership" order is displayed in my order in MyAccount
#    And ClubMember goes to "Membership" page
#    And Membership is displayed on membership page in MyAccount
#    And Registration details for "standard plan" are displayed in MyAccount

    Examples:
      | country   | GB                | sport         |  template   | product       | countryAccountType   |
      | "Ireland" | "Tennis Ireland"  | "Hurling"     |  "Player"   | "Membership"  | "Ireland Individual" |




