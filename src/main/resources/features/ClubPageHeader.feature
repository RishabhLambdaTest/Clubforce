@Win10ChromeLatest
@excludeFromProd @excludeFromSandbox

Feature: Club Page Header

  Scenario: Check menu items are working
    Given SuperUser is logged into SU
    And they search "Automation Club QA" club
    And "Automation Club QA" club is displayed
    And Lotto is "enabled" for club
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Navigation" page
    And ClubAdmin adds menu items for club
    And user goes to URL "https://automationclubqa.test.clubforce.io"
    Then menu items are displayed as expected on club website