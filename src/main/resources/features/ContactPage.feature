@Win10ChromeLatest @WinFirefoxLatest
#  @MacSafari delete all cookies is not working. Need to investigate why.
@ProdRegressionTest @excludeFromSandbox

Feature: ContactPage

  Scenario: ContactPage message send and receipt
    Given "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Contact Messages" page
    And all contact messages in admin are read
    When user go to club contact page
    And they can send a contact message to the club
    And "ClubAdmin" is logged into Backoffice
    Then "1 New Message" is displayed on Dashboard contact message tile
    And ClubAdmin go to Backoffice "Contact Messages" page
    And ClubAdmin see contact message
    And ClubAdmin go to Backoffice "Dashboard" page
    And "No New Messages" is displayed on Dashboard contact message tile
