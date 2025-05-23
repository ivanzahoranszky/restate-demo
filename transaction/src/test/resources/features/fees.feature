Feature: Fee calculation

  Scenario: When the RateCalculator is called with a FeeRequest it returns the correct rate for the transaction
    Given the RateCalculator is initialized
    When the RateCalculator is called with the following requests:
      | transaction_id | amount | asset    | expected_rate |
      | ID1            | 100    | FIAT     | 1             |
      | ID2            | 100    | CRYPTO   | 2             |
    Then it returns the correct rates for all requests