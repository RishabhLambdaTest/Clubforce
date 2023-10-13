@refunds
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: Refunds

  Scenario Outline: ClubAdmin attempts to issue a refund for lotto ticket
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "with autoRenewal" for "Durban" club
    And cart has their "auto renew lotto" purchase
    And "signup loser" purchase "Lotto with autoRenewal" with <cardNumber> card
    And "Lotto" order is displayed in my order in MyAccount
    And "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is displayed on Playslip page
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    When ClubAdmin issues refund for "lotto ticket"
    Then refund <status> is reflected on order details page
    And "Lotto ticket" refund is reflected on orders page
    And ClubAdmin go to Backoffice "Auto Renewals" page
    And Lotto "cancelled auto renewal" ticket is displayed on auto renewals page
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is removed from current draw on Playslip page
    And CA-AH can log out of backoffice
    And user is on the My Account login page
    And "Lotto refund user" is logged into My Account
    And refunded order is displayed in MyAccount on orders page
    And refunded order is displayed in MyAccount on orders details page for <status>
    And User is logged out of MA
    And "Lotto" refund email is sent to "account holder" for <status>
    And "Lotto" refund email is sent to "ClubAdmin" for <status>
    And "Lotto" failed refund email is sent to "account holder" for <status>
    And "Lotto" failed refund email is sent to "ClubAdmin" for <status>
    Examples:
    | cardNumber        | status              |
    | "4111111111111111" | "pendingToSuccess" |
    #The charge succeeds. If you initiate a refund, its status begins as pending.Some time later, its status transitions to succeeded and sends a charge.refund.updated webhook event.
    | "4000000000005126" | "pendingToFailed"  |
    #The charge succeeds. If you initiate a refund, its status begins as succeeded. Some time later, its status transitions to failed and sends a charge.refund.updated webhook event.

  Scenario Outline: ClubAdmin attempts to issue refund for PAID membership plan
    Given user go to membership club purchase page
    And they select membership plan "Priced plan for automation"
    And user continue to membership forms
    And membership buyer "signup user" logs in
    And "adult member details" are filled in for "adult" plan for "signup user" buyer
    And memberships are added to cart
    And user click Checkout to go pay
    And member purchases "paid" memberships with <cardNumber> card
    And "Standard membership" order is displayed in my order in MyAccount
    And "MembershipClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Standard membership" order details are displayed in Reports Orders
    When ClubAdmin issues refund for "membership plan"
    Then refund <status> is reflected on order details page
    And "Membership plan" refund is reflected on orders page
    Then CA-AH can log out of backoffice
    And user is on the My Account login page
    And "Membership refund user" is logged into My Account
    And refunded order is displayed in MyAccount on orders page
    And refunded order is displayed in MyAccount on orders details page for <status>
    And ClubMember goes to "Membership" page
    And Membership plan is displayed as refunded on Membership page in MyAccount
    And User is logged out of MA
    And "Membership" refund email is sent to "account holder" for <status>
    And "Membership" refund email is sent to "ClubAdmin" for <status>
    And "Membership" failed refund email is sent to "account holder" for <status>
    And "Membership" failed refund email is sent to "ClubAdmin" for <status>
    Examples:
    |cardNumber        |status            |
    | "4111111111111111"| "pendingToSuccess"|
    #The charge succeeds. If you initiate a refund, its status begins as pending.Some time later, its status transitions to succeeded and sends a charge.refund.updated webhook event.
    | "4000000000005126"| "pendingToFailed" |
    #The charge succeeds. If you initiate a refund, its status begins as succeeded. Some time later, its status transitions to failed and sends a charge.refund.updated webhook event.
