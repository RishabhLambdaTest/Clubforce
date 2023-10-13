@Win10ChromeLatest @Memberships
# noinspection SpellCheckingInspection
@excludeFromProd

Feature: Memberships

  @excludeFromProd @excludeFromSandbox
  Scenario: Discard changes using cancel button when editing a plan
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin edits a plan
    And ClubAdmin discards changes using cancel button
    Then all of the edited changes are discarded

  @excludeFromProd @excludeFromSandbox
  Scenario: Discard changes when click out of edit plan flow
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin edits a plan
    And ClubAdmin discards changes by leaving section
    Then all of the edited changes are discarded

  @excludeFromProd @excludeFromSandbox
  Scenario: CA edits plan
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin edits a plan
    And they publish "Edited Plan"
    And "Edited" plan is visible on CW using URL from Published Plans page

  @excludeFromProd @excludeFromSandbox
  Scenario: CA duplicates plan
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "genIrelandMembership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin deletes any duplicated plans
    And ClubAdmin duplicates a plan
    And duplicate plan contains contain the correct information
    And ClubAdmin deletes any duplicated plans

  @excludeFromProd @excludeFromSandbox
  Scenario: CA quickPublish plan on step 1
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "1" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin edits and quickPublish plan on "step 1"
    Then "paid" plan is visible on CW using URL from Published Plans page

  @excludeFromProd @excludeFromSandbox
  Scenario: CA quickPublish plan on step 2
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "1" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin edits and quickPublish plan on "step 2"
    Then "paid" plan is visible on CW using URL from Published Plans page

  @excludeFromProd @excludeFromSandbox
  Scenario: CA unPublishes plan
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "1" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And ClubAdmin unPublishes a plan
    Then the plan will no longer be displayed on a website

#  @excludeFromProd @excludeFromSandbox
#  Scenario: Create a pending plan TODO uncomment when https://clubforce.atlassian.net/browse/CE-1112 is fixed
#    Given SuperUser is logged into SU
#    And SuperUser selects the latest complete "Membership" account
#    And SuperUser logs in as ClubAdmin
#    When ClubAdmin go to Backoffice "Membership plans" page
#    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "1" max subscriptions
#    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
#    And they publish plan with future date
#    And pending plan is not visible on club website

    #  @ProdRegressionTest
  Scenario Outline: Card error handling checks for memberships
    Given user go to membership club purchase page
    And they select membership plan "Priced plan for automation"
    And user click Continue on purchase plans page
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And user attempts purchase with <card> and gets <error message> for "membership"
    Examples:
      |       card         |      error message                   |
      | "4000000000000069" | "Your card has expired"              |
      | "4000000000000119" | "An error occurred while processing your card. Try again" |
      | "4000000000000002" | "Card declined - n.b. only debit cards (not credit cards) may be used for lottery transactions in Great Britain" |
      | "4000000000009995" | "Your card has insufficient funds"   |

  @excludeFromProd @excludeFromSandbox
  Scenario: Buy plans on membership club - signup user
    Given user go to membership club purchase page
    And they select membership plan "Priced plan for automation"
    And user continue to membership forms
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And "Membership" order shows in my mail client


  @excludeFromProd @excludeFromSandbox
  Scenario: Buy kids plan and then adult plan on membership club - existing user
    Given user go to membership club purchase page
    And they select membership plan "Priced juvenile plan"
    And user click Continue on purchase plans page
    And membership buyer "mem0005@clubforce.com" logs in
    And "guardian details" are filled in for "juvenile" plan for "mem0005@clubforce.com" buyer
    And "juvenile member details" are filled in for "juvenile" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Continue Shopping
    And user go to membership club purchase page
    And they select membership plan "Priced plan for automation"
    And user click Continue on purchase plans page
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Standard membership" order is displayed in my order in MyAccount
    And "MembershipClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Standard membership" order details are displayed in Reports Orders

  @excludeFromProd @excludeFromSandbox
  Scenario: Create and purchase new juvenile plan - signup user
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "juvenile" "paid plan" plan with "10" max subscriptions
    And ClubAdmin completes step 2 for a new "juvenile" plan with "No" special questions
    And they publish "New Plan"
    And "paid" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "signup user" logs in
    And "guardian details" are filled in for "juvenile" plan for "mem0005@clubforce.com" buyer
    And "juvenile member details" are filled in for "juvenile" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And "Juvenile membership" order is displayed in my order in MyAccount
    And ClubMember goes to "Membership" page
    And Membership is displayed on membership page in MyAccount
    And Registration details for "juvenile plan" are displayed in MyAccount
    And SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Juvenile membership" order details are displayed in Reports Orders

  @excludeFromProd @excludeFromSandbox
  Scenario: Test max subscriptions limit
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    When ClubAdmin go to Backoffice "Membership plans" page
    And ClubAdmin completes step 1 for a new "adult" "paid plan" plan with "1" max subscriptions
    And ClubAdmin completes step 2 for a new "adult" plan with "No" special questions
    And they publish "New Plan"
    And "paid" plan is visible on CW using URL from Published Plans page
    Then the plan is selected on CW
    And Member choose to continue
    And membership buyer "mem0005@clubforce.com" logs in
    And "adult member details" are filled in for "adult" plan for "mem0005@clubforce.com" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with "4000000000000077" card
    And member go to the current club homepage membership purchase page
    And membership plan is no longer available

