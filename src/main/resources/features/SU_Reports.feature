@Win11ChromeLatest
# noinspection SpellCheckingInspection

Feature: SU reports

  Scenario: Basic Report CSV can be downloaded in the SU
    Given SuperUser is logged into SU
    Then the SU "Basic Report" CSV file can be downloaded

  Scenario: Financial Report CSV can be downloaded in the SU
    Given SuperUser is logged into SU
    Then the SU "Financial Report" CSV file can be downloaded


