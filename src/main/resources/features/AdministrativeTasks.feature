@AdministrativeTasks
    # noinspection SpellCheckingInspection
Feature: Administrative Tasks

#  Scenario Outline: Create a new membership automated user for Test, Sandbox and Prod.
#    Given user goes to URL <url>
#    When they can sign up to become a member using "mem0005@clubforce.com"
#    Examples:
#      |                       url                         |
#      | "https://pennybridgerugby.test.clubforce.io"      |
#      | "https://pennybridgerugby.sandbox.clubforce.io"   |
#      | "https://pennycricket.clubforce.com"              |

  @ProdRegressionTest
  Scenario Outline: Check Apple store page
    When user goes to URL <url>
    Then they can find Apple text string <textString>
    Examples:
      | url                                                                  | textString      |
      | "http://apps.apple.com/ie/app/mcf/id1270809419"                      | "Version 6.0.8" |
      | "http://apps.apple.com/ie/app/clubforce-connect/id1637436091"        | "Version 3.0.0" |

  @ProdRegressionTest
  Scenario Outline: Check Google store page
    When user goes to URL <url>
    Then they can find Google text string <textString>
    Examples:
      | url                                                                  | textString  |
      | "http://play.google.com/store/apps/details?id=com.girt.ebadism.mcf"  | "4.0.9"     |
      | "http://play.google.com/store/apps/details?id=com.clubforce.comms"   | "3.0.0"     |

  Scenario Outline: Check redirects for old domains
    When user goes to URL <url>
    Then they are redirected to <landingURL>
    Examples:
      | url                         | landingURL  |
#      | "https://locallotto.ie"     | "play.clubforce.com" |  https://clubforce.atlassian.net/browse/TST-7057
      | "http://www.helpourclub.ie" | "member.clubforce.com" |

  Scenario Outline: Validate club appears in google search
    Given I google <googleSearch>
    Then <searchResult> is found on google
    Examples:
      | googleSearch | searchResult |
      | "\"Caherconlish A.F.C\" \"Established 1972\" \"clubforce\"" | "caherconlishafc.clubforce.com" |
      | "\"Curracloe United AFC\" \"clubforce\""                    | "curracloeunitedafc.clubforce.com" |
      | "\"Kilcullen Tennis Club\" \"clubforce\""                   | "kilcullentennis.clubforce.com" |
      | "\"Limerick Hockey club\" \"clubforce\""                    | "limerickhockeyclub.clubforce.com" |
      | "\"Athlone A.C. Juvenile Athletics Club\" \"clubforce\"" | "athloneac.clubforce.com" |
      | "\"Milltown-Castlemaine GAA\" \"clubforce\"" | "milltown-castlemaine.clubforce.com" |
      | "\"Stabannon Parnells GFC\" \"clubforce\""                  | "stabannonparnells.clubforce.com" |
      | "\"Swanlinbar St. Mary's\" \"clubforce\""                   | "swanlinbarstmarys.clubforce.com" |
      | "\"The Downs GAA Club\" \"clubforce\""                      | "thedownsgaa.clubforce.com" |

#  Scenario: Check prod clubPages
#    Given basic club page content is displayed as expected
