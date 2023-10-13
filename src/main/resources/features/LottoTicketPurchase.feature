@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: LottoTicketPurchase

  Scenario: Add Lotto menu item
    Given "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Navigation" page
    And they add a Lotto Menu item
    When user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club

  Scenario: Add 3 Lotto items, remove 1
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add 3 Lotto to cart
    When they remove 1 Lotto
    Then only 2 Lotto remain

  @pixel7viewport @iPhone14viewport
  Scenario Outline: Card error handling checks for lotto
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "without autoRenewal" for "Durban" club
    Then cart has their "Lotto" purchase
    And user attempts purchase with <card> and gets <error message> for "lotto"
    Examples:
      | card | error message |
      | "4000000000000069" | "Your card has expired" |
      | "4000000000000119" | "An error occurred while processing your card. Try again" |
      | "4000000000000002" | "Card declined - n.b. only debit cards (not credit cards) may be used for lottery transactions in Great Britain"|
      | "4000000000009995" | "Your card has insufficient funds" |

  @WinFirefoxLatest
  Scenario: Buy losing ticket - card is rejected
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "without autoRenewal" for "Durban" club
    Then cart has their "Lotto" purchase
    And "existing loser" purchase "Lotto without good card" with "4000000000000002" card
    And order is rejected

  Scenario: Buy winning ticket Lotto default test club - existing user
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    When they can add "winning" Lotto to cart "without autoRenewal" for "Durban" club
    Then cart has their "Lotto" purchase
    And "existing winner" purchase "Lotto without autoRenewal" with "4111111111111111" card
    And "Lotto" order is displayed in my order in MyAccount
    And "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is displayed on Playslip page
    And "Lotto" order shows in my mail client

  Scenario: Buy auto renewing ticket Lotto default test club
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "with autoRenewal" for "Durban" club
    Then cart has their "auto renew lotto" purchase
    And "existing loser" purchase "Lotto with autoRenewal" with "4111111111111111" card
    And "Lotto" order is displayed in my order in MyAccount
    And "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is displayed on Playslip page
    And ClubAdmin go to Backoffice "Auto Renewals" page
    And Lotto "active auto renewal" ticket is displayed on auto renewals page
    And "Lotto" order shows in my mail client
#    Then ClubMember goes to "Payment Methods" page
#    And recurring card shows in Payment Methods
#    And ClubMember performs action "Remove all cards" https://clubforce.atlassian.net/browse/CP-1866

  #   On phone I get null pointer for trying to extract Order date and ID #TODO make work on iPhone
  @MacSafari @WinFirefoxLatest
  Scenario: Buy losing ticket Lotto default test club - existing user
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "without autoRenewal" for "Durban" club
    Then cart has their "Lotto" purchase
    And "existing loser" purchase "Lotto without autoRenewal" with "4111111111111111" card
    And "Lotto" order is displayed in my order in MyAccount
    And "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is displayed on Playslip page
    And "Lotto" order shows in my mail client

 #   On phone I get null pointer for trying to extract Order date and ID #TODO make work on iPhone
  Scenario: Buy losing ticket Lotto default test club - sign up user
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "without autoRenewal" for "Durban" club
    Then cart has their "Lotto" purchase
    And "signup loser" purchase "Lotto without autoRenewal" with "4111111111111111" card
    And "Lotto" order is displayed in my order in MyAccount
    And ClubMember goes to "Lotto" page
    And Lotto playslip is displayed on lotto page in MyAccount
    Then Clubmember can view order details for "Active" playslip
    And "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is displayed on Playslip page
    And "Lotto" order shows in my mail client

  @WinFirefoxLatest
  Scenario: Buy auto renewing ticket and cancel auto renewal
    Given user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "with autoRenewal" for "Durban" club
    Then cart has their "auto renew lotto" purchase
    And "signup loser" purchase "Lotto with autoRenewal" with "4111111111111111" card
    And "Lotto" order is displayed in my order in MyAccount
    And ClubMember goes to "Lotto" page
    And Lotto playslip is displayed on lotto page in MyAccount
    And Club member can cancel auto renewal ticket
    And Clubmember can view order details for "Active" playslip
    And "LottoClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Finance Orders" page
    And "Lotto" order details are displayed in Reports Orders
    And ClubAdmin go to Backoffice "Lotto Playslip" page
    And Playslip is displayed on Playslip page
    And ClubAdmin go to Backoffice "Auto Renewals" page
    And Lotto "cancelled auto renewal" ticket is displayed on auto renewals page
    And "Lotto" order shows in my mail client
