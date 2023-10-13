@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Membership multi plan discounts

#  Using a club that has no discounts created. If discounts do get added, change club in scenario below
  Scenario: View and check membership multi plan discounts default empty state
    Given SuperUser is logged into SU
    And they search "genIrelandMembership388564" club
    And "genIrelandMembership388564" club is displayed
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Multi-plan discounts" page
    Then the default empty state is displayed for discounts page

  Scenario: Club Admin creates multi plan discount - percentage discount
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "5" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    When ClubAdmin go to Backoffice "Multi-plan discounts" page
    And they proceed to "Step 1" of multi plan creation
    And they select plans for discount
    And they proceed to "Step 2" of multi plan creation
    And they set "percentage" multiplan discounts for the plans
    Then they can publish the multi plan discounts
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin gets membership page link
    And ClubAdmin is logged out of Backoffice
    And user goes to membership page on website
    And the plan is selected on CW
    And user selects "2" plans for discount
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "2" adult plans with discount are filled in
    And add "2" plans with "percentage" discount to cart
    And "2" plans with "percentage" discount are purchased with "4000000000000077" card
#    And "Membership with multi plan discount" order is displayed in my order in MyAccount TODO annie

  Scenario: Club Admin creates multi plan discount - amount discount
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "5" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    When ClubAdmin go to Backoffice "Multi-plan discounts" page
    And they proceed to "Step 1" of multi plan creation
    And they select plans for discount
    And they proceed to "Step 2" of multi plan creation
    And they set "amount" multiplan discounts for the plans
    Then they can publish the multi plan discounts
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin gets membership page link
    And ClubAdmin is logged out of Backoffice
    And user goes to membership page on website
    And the plan is selected on CW
    And user selects "3" plans for discount
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "3" adult plans with discount are filled in
    And add "3" plans with "amount" discount to cart
    And "3" plans with "amount" discount are purchased with "4000000000000077" card
#    And "Membership with multi plan discount" order is displayed in my order in MyAccount TODO annie
    And "Membership" order shows in my mail client

    Scenario: Club Admin edits a club discount
      Given SuperUser is logged into SU
      And SuperUser selects the latest complete "MandL" account
      And SuperUser logs in as ClubAdmin
      When ClubAdmin go to Backoffice "Membership plans" page
      And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "5" max subscriptions
      And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
      And they publish "New Plan"
      When ClubAdmin go to Backoffice "Multi-plan discounts" page
      And they proceed to "Step 1" of multi plan creation
      And they select plans for discount
      And they proceed to "Step 2" of multi plan creation
      And they set "amount" multiplan discounts for the plans
      Then they can publish the multi plan discounts
      And ClubAdmin edits a discount
      Then they can re publish the multi plan discounts

  Scenario: Club Admin deactivates a club discount
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "MandL" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "5" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    When ClubAdmin go to Backoffice "Multi-plan discounts" page
    And they proceed to "Step 1" of multi plan creation
    And they select plans for discount
    And they proceed to "Step 2" of multi plan creation
    And they set "amount" multiplan discounts for the plans
    Then they can publish the multi plan discounts
    And ClubAdmin deactivates a discount on discount page