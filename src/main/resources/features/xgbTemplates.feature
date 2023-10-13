@XGB

Feature: Templates

  Scenario: Template presence
    Given XGBAdmin is logged into XGB
    When Admin go to XGB "Templates" page
    Then Admin can see "Player" template

#  Scenario: Template flow - unclear what questions should be included
#    Given XGBAdmin is logged into XGB
#    And Admin go to XGB "Templates" page
#    When Admin clicks "Player" template
#    Then they can step through activation flow

#  Scenario: Template optional questions search - unclear what questions should be included
#    Given XGBAdmin is logged into XGB
#    And Admin go to XGB "Templates" page
#    When Admin clicks "Player" template
#    Then they can search specific questions
