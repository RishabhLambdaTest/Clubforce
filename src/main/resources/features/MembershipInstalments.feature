@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Membership instalments

  Scenario: Create purchase and then edit and purchase instalment plan
   Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Promo" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "monthly instalments plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And "instalment" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "instalments plans" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "standard plan" are displayed in MyAccount
    And SuperUser is logged into SU
    And SuperUser selects the previous club
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Standard membership" order details are displayed in Reports Orders
    Given ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin edits the previous instalment plan
    And "instalment" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "instalments plans" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "standard plan" are displayed in MyAccount
