Feature: Retrieve Tenant(s)
  A single tenant or a list of all tenants needs to be retrieved from the system.

  Scenario: Listing all tenants
    Given a tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test tenant 1' is existing
    And the tenant 'f2781086-ffb9-4b2b-ae7f-c634b587e131' with name 'Test tenant 2' is existing
    And the tenant 'eac4a467-1d5c-437f-ab89-955f375aec6e' with name 'Test tenant 3' is existing
    When retrieving the set of tenants
    Then '3' tenants should be returned
    And tenant 'eac4a467-1d5c-437f-ab89-955f375aec6e' should be in the result set
    And tenant 'f2781086-ffb9-4b2b-ae7f-c634b587e131' should be in the result set
    And tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' should be in the result set


  Scenario: Retrieve an existing tenant
    Given the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test tenant 1' is existing
    When retrieving tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
    Then tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' should have the name 'Test tenant 1'


  Scenario: Retrieve a non-existing tenant
    Given the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' does not exist in the system
    When retrieving tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
    Then a failure should be generated pointing to tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
