@Win11ChromeLatest
@excludeFromProd @excludeFromSandbox
# noinspection SpellCheckingInspection

Feature: SectionsAndPages

#  No pages or sections are created for latest generated membership clubs
  Scenario: View and check sections and pages default empty state
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Membership" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then The default empty state is displayed for sections
    And The default empty state is displayed for pages

  Scenario: Create a section
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then Club admin creates a section

  Scenario: Create a page
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then Club admin creates a new page

  Scenario: Add pages to a section
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin adds a page to a section

  Scenario: Delete a section
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then Club admin deletes a section

  Scenario: Edit a section
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then Club admin edits a section

  Scenario: Remove a page from a section
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then Club admin removes pages from a section

  Scenario: Edit section title for section that is already displayed on website
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin adds a page to a section
    And ClubAdmin go to Backoffice "Website Navigation" page
    And Club admin add a section to website using website navigation
    Then the section and its pages are "displayed" on the website
    And Club admin goes back to backoffice
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin "edits" the section that is displayed on the website
    And the sections "menu item title" should be updated on "website navigation page"
    And the sections "menu item title" should be updated on "website"

  Scenario: Removes section that is already displayed on website
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin adds a page to a section
    And ClubAdmin go to Backoffice "Website Navigation" page
    And Club admin add a section to website using website navigation
    Then the section and its pages are "displayed" on the website
    And Club admin goes back to backoffice
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin "removes" the section that is displayed on the website
    And ClubAdmin go to Backoffice "Website Navigation" page
    Then the section and its pages are not displayed on the website navigation page
    Then the section and its pages are "not displayed" on the website

  Scenario: Add another page for section that is already displayed on website
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin adds a page to a section
    And ClubAdmin go to Backoffice "Website Navigation" page
    And Club admin add a section to website using website navigation
    Then the section and its pages are "displayed" on the website
    And Club admin goes back to backoffice
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin adds another page to the section that is displayed on the website
    Then the sections "add page to list dropdown" should be updated on "website navigation page"
    Then the sections "add page to list dropdown" should be updated on "website"

  Scenario: Removes page for section that is already displayed on website
    Given SuperUser is logged into SU
    And SuperUser selects the latest complete "Lotto" account
    And SuperUser logs in as ClubAdmin
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin adds 3 pages to a section
    And ClubAdmin go to Backoffice "Website Navigation" page
    And Club admin add a section to website using website navigation
    Then the section and the 3 pages are displayed on the website navigation page
    Then the section and the 3 pages are displayed on the website
    And Club admin goes back to backoffice
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    And Club admin removes a page from a section that is displayed on the website
    Then the sections "remove page from list dropdown" should be updated on "website navigation page"
    Then the sections "remove page from list dropdown" should be updated on "website"




