@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Discount codes

  Scenario: View and check discount codes default empty state
    Given SuperUser is logged into SU
    And they search "genIrelandMembership388564" club
    And "genIrelandMembership388564" club is displayed
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Discount codes" page
    Then the default empty state is displayed for discount codes pages

  Scenario: Create a new single use discount code and try use twice
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "5" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin go to Backoffice "Discount codes" page
    Then ClubAdmin can create a new "single" use "percentage" discount code
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin gets membership page link
    And ClubAdmin is logged out of Backoffice
    And user goes to membership page on website
    And the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    Then purchase membership plan with "single use" discount code using "4000000000000077" card
    And "Membership" order shows in my mail client
    And "Membership with discount code" order is displayed in my order in MyAccount
    And user goes to membership page on website
    And the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    Then user attempts to use code and gets "Discount code expired" error message

  Scenario: Create a new multi use discount code and use twice
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "5" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin go to Backoffice "Discount codes" page
    Then ClubAdmin can create a new "multi" use "amount" discount code
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin gets membership page link
    And ClubAdmin is logged out of Backoffice
    And user goes to membership page on website
    And the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    Then purchase membership plan with "multi use" discount code using "4000000000000077" card
    And "Membership with discount code" order is displayed in my order in MyAccount
    And user goes to membership page on website
    And the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    Then purchase membership plan with "multi use" discount code using "4000000000000077" card
    And "Membership with discount code" order is displayed in my order in MyAccount
    And "Membership" order shows in my mail client

  Scenario: Deactivate an active discount code
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Discount codes" page
    Then ClubAdmin can deactivate an active discount code

  Scenario: Edit an active discount code
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Discount codes" page
    Then ClubAdmin can edit an "active" discount code

  Scenario: Edit an expired discount code
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Discount codes" page
    Then ClubAdmin can edit an "expired" discount code

  Scenario: Export discount code CSV file
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Discount codes" page
    And ClubAdmin goes to discount code report page
    Then ClubAdmin can download discount code csv file

#    TODO add automation to reactivate inactive discount code
