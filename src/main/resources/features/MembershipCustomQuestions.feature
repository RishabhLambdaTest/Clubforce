@Win10ChromeLatest @MembershipCustomQuestions
# noinspection SpellCheckingInspection

Feature: MembershipCustomQuestions

  @excludeFromProd
  Scenario: Test freetext question
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "freetext" special questions
    And they publish "New Plan"
    And "paid" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "standard plan" are displayed in MyAccount

  @excludeFromProd
  Scenario: Test dropdown question
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "dropdown" special questions
    And they publish "New Plan"
    And "paid" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "standard plan" are displayed in MyAccount

  @excludeFromProd
  Scenario: Test singleSelect question
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "singleSelect" special questions
    And they publish "New Plan"
    And "paid" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "standard plan" are displayed in MyAccount

