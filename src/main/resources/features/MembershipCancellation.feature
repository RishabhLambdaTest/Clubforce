@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection
#  TODO annie needs to finish automation for this feature

Feature: Membership cancellation

  Scenario Outline: Cancel an individual member’s membership for <paymentType> plan
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Promo" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" <planPriceType> plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And <price> plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And SuperUser is logged into SU
    And SuperUser selects the previous club
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Standard membership" order details are displayed in Reports Orders
    And ClubAdmin goes to member profile page on order details page
    And ClubAdmin cancels <paymentType> membership registration
    Examples:
    |planPriceType             |price        |paymentType  |
    |"paid plan"               |"paid"      |"paid in full"|
    |"monthly instalments plan"|"instalment"|"instalment"  |

  Scenario:  Cancel an individual member’s instalment bundle
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Promo" account
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
    And SuperUser is logged into SU
    And SuperUser selects the previous club
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "bundle" order details are displayed in Reports Orders
    And ClubAdmin goes to member profile page on order details page
    And ClubAdmin cancels "instalment" membership registration

