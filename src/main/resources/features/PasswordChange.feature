@Win10ChromeLatest @WinEdgeLatest @excludeFromSandbox
Feature: PasswordChange

  Scenario: Password change for ClubMember Login
  Given ClubMember is logged into MyAccount
  When user updates password
  Then user can login into myAccount with new password
  And change password back to original
  Then ClubMember is logged into MyAccount

