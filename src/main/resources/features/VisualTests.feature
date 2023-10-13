@VisualTests @Win10ChromeLatest @excludeFromSandbox @excludeFromProd
Feature: Compare recent screenshots to stored screenshots

  Scenario: Visually inspect homepage test
    Given user goes to URL "https://irishvisuals.test.clubforce.io"
    Then "IVDesktopHomepage" is visually as expected

  Scenario: Visually inspect homepage prod
    Given user goes to URL "https://irishvisuals.clubforce.com"
    Then "IVDesktopHomepageProd" is visually as expected

  Scenario: Visually inspect contact page
    Given user goes to URL "https://irishvisuals.test.clubforce.io/contact"
    Then "IVDesktopContactPage" is visually as expected

  Scenario: Visually inspect news page
    When user goes to URL "https://irishvisuals.test.clubforce.io/news/news"
    Then "IVDesktopNewsPage" is visually as expected

  Scenario: Visually inspect about us page
    When user goes to URL "https://irishvisuals.test.clubforce.io/pages/about-us"
    Then "IVDesktopAboutUsPage" is visually as expected

  Scenario: Visually inspect error page
    When user goes to URL "https://irishvisuals.test.clubforce.io/error"
    Then "IVDesktopErrorPage" is visually as expected

  Scenario: Visually inspect backoffice login page
    Given user is on the backoffice login page
    Then "IVDesktopBackofficeLoginPage" is visually as expected

  Scenario: Visually inspect backoffice Dashboard page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    Then "IVDesktopBackofficeDashboardPage" is visually as expected

  Scenario: Visually inspect backoffice About Us page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website About Us" page
    Then "IVDesktopBackofficeAboutUsPage" is visually as expected

  Scenario: Visually inspect backoffice Website Articles page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website Articles" page
    Then "IVDesktopBackofficeWebsiteContentArticlesPage" is visually as expected

  Scenario: Visually inspect backoffice Website Sections and Pages page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website Sections and Pages" page
    Then "IVDesktopBackofficeWebsiteContentPagesPage" is visually as expected

  Scenario: Visually inspect backoffice Website Banner page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website Banner" page
    Then "IVDesktopBackofficeWebsiteSettingsBannerPage" is visually as expected

  Scenario: Visually inspect backoffice Website Navigation page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website Navigation" page
    Then "IVDesktopBackofficeWebsiteSettingsMenuPage" is visually as expected

  Scenario: Visually inspect backoffice Website Settings Theme page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website Theme" page
    Then "IVDesktopBackofficeWebsiteSettingsThemePage" is visually as expected

  Scenario: Visually inspect backoffice Website Sponsor page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Website Sponsor" page
    Then "IVDesktopBackofficeWebsiteSettingsSponsorPage" is visually as expected

  Scenario: Visually inspect backoffice Contact Messages page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Contact Messages" page
    Then "IVDesktopBackofficeContactMessagesPage" is visually as expected

  Scenario: Visually inspect backoffice Account page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Account" page
    Then "IVDesktopBackofficeAccountPage" is visually as expected

  Scenario: Visually inspect backoffice Users page
    Given user is on the backoffice login page
    And admin enter "Irish visuals club" login details
    And ClubAdmin go to Backoffice "Users" page
    Then "IVDesktopBackofficeUsersPage" is visually as expected
