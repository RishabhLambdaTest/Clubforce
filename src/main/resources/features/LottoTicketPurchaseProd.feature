@LottoTicketPurchaseProd

Feature: ProdLottoTicketPurchase

  Scenario: Card error handling checks Prod
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Website Navigation" page
    And they add a Lotto Menu item
    Then CA-AH can log out of backoffice
    When user go to lotto club homepage
    And they follow the Lotto link for "Ireland" club
    And they can add "losing" Lotto to cart "without autoRenewal" for "prod" club
    Then cart has their "Lotto" purchase
    And user attempts purchase with "4111111111111111" and gets "Your card was declined. Your request was in live mode, but used a known test card." for "lotto"

