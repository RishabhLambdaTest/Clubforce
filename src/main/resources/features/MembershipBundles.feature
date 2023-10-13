@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Membership bundles

  @excludeFromProd
  Scenario: Buy bundle on membership club - new user
    Given user go to membership club purchase page
    And they select membership bundle "QA automation bundle"
    And user continue to membership forms
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card

  Scenario: CA cancel plan bundle creation
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin can cancel during bundle creation

  Scenario: CA creates and then edits a bundle
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin can create an instalment membership bundle
    And ClubAdmin edits and publishes the membership bundle
    And "edited bundle" plan is visible on CW using URL from Published Plans page

  Scenario: CA publishes and unPublishes a plan bundle
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin can create an instalment membership bundle
    Then bundle is "visible" on the memberships purchase page
    Given ClubAdmin goes back to backoffice
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin unPublishes a bundle
    Then bundle is "not visible" on the memberships purchase page

  Scenario: CA creates and buys an instalment bundle
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin unPublishes all bundles
    And ClubAdmin can create an instalment membership bundle
    And "bundled" plan is visible on CW using URL from Published Plans page
    And they select the first bundle
    And user continue to membership forms
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "instalments bundle" memberships with "4000000000000077" card

  Scenario: CA creates a bundle with a min/max global numbers
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "monthly instalments plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin completes step 1 for a new "juvenile" "paid plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "juvenile" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin can create an instalment membership bundle
    And "bundled" plan is visible on CW using URL from Published Plans page
