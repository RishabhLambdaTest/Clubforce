@Win10ChromeLatest
@excludeFromSandbox @excludeFromProd
#  TODO remove exclude from Prod after release
#@MacSafari https://clubforce.atlassian.net/browse/QTA-167
# noinspection SpellCheckingInspection

Feature: SignUp

  @pixel7viewport @iPhone14viewport
  Scenario: Sign up from club homepage
    Given user go to club homepage
    Then they can sign up to become a member
    And they can see their myaccount page
    And they can verify their mail address

  Scenario: AH resends verification email in MyAccount
    Given user go to club homepage
    Then they can sign up to become a member
    And they can see their myaccount page
    And they resend verification email
    And they can verify their mail address

  @excludeFromProd #code cannot run on prod becuse the mailtrap locators don't work on testmail app
  Scenario: Existing CA can sign up a new CA from backoffice
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Users" page
    And CA add "brand new" additional "Club admin" account
    And signs out of their Account
    Then they can get "new CA" mail and do process to log in
    When ClubAdmin go to Backoffice "Users" page
    And ClubAdmin see today as last login for themselves
    And signs out of their Account
    And CA-AH can log into myaccount

  @excludeFromProd #code cannot run on prod ebcuse the mailtrap locators don't work on testmail app
  Scenario: Existing CA can sign up a new GM from backoffice
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Users" page
    And CA add "brand new" additional "Group manager" account
    And signs out of their Account
    Then they can get "new GM" mail and do process to log in

  @excludeFromProd #code cannot run on prod ebcuse the mailtrap locators don't work on testmail app
  Scenario: Existing CA can sign up a new GM and CA from backoffice
    Given "ClubAdmin" is logged into Backoffice
    When ClubAdmin go to Backoffice "Users" page
    And CA add "brand new" additional "GM and CA" account
    And signs out of their Account
    Then they can get "GM and CA" mail and do process to log in

  @excludeFromProd #code cannot run on prod because the mailtrap locators don't work on testmail app
  Scenario: Existing CA can add AH as CA from backoffice
    Given user go to club homepage
    And they can sign up to become a member
    And "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Users" page
    When CA add "already AH" additional "Club admin" account
    And signs out of their Account
    Then they can get "AH to CA" mail and do process to log in
    And ClubAdmin go to Backoffice "Users" page
    And ClubAdmin see today as last login for themselves

  @excludeFromProd #code cannot run on prod because the mailtrap locators don't work on testmail app
  Scenario: Existing CA can add AH as a GM from backoffice
    Given user go to club homepage
    And they can sign up to become a member
    And "ClubAdmin" is logged into Backoffice
    And ClubAdmin go to Backoffice "Users" page
    When CA add "already AH" additional "Group manager" account
    And signs out of their Account
    Then they can get "AH to GM" mail and do process to log in
