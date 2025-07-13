Feature: SIM Card Activation Feature

  Scenario: Successful SIM card activation
    Given the SIM card ICCID is "1255789453849037777" and customer email is "success@test.com"
    When I submit the activation request
    Then the SIM card should be activated with ID 1

  Scenario: Failed SIM card activation
    Given the SIM card ICCID is "8944500102198304826" and customer email is "fail@test.com"
    When I submit the activation request
    Then the SIM card should not be activated with ID 2
