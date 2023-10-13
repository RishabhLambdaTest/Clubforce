@LottoRandomnessTest

Feature: LottoRandomness

  Scenario: Lotto randomness test
#    Given user goes to URL "https://donnamoregolfclub.test.clubforce.io/products/donnamore-daily-lotto"
    Given user goes to URL "https://genirelandmandl862552.sandbox.clubforce.io/products/lotto-oct-18-ipx"
    Then lotto selection is random when selected 1000 times
