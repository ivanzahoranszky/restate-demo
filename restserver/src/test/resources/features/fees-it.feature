Feature: Test REST endpoint

  Scenario: When the REST endpoint is called with the following requests the correct responses are returned
    Given the system is up and running
    When the system is called with the following requests:
      | transaction_id | amount | asset_type | asset | expected_response                                      |
      | ID1            | 100    | FIAT       | USD   | {"transactionId":"ID1","fee":"0.1500","rate":"0.0015"} |
      | ID2            | 100    | CRYPTO     | ETH   | {"transactionId":"ID2","fee":"10.0","rate":"0.1"}      |
    Then it returns the correct responses