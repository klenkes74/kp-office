Feature: Create Tenant
  A tenant must be added to the system.

  Scenario: Add a non-existing tenant to the system
    Given the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' does not exist in the system
    When a tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test Tenant 1' should be created
    Then a tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' should exist
    And the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' should have the name 'Test Tenant 1'


  Scenario: Add an existing tenant to the system
    Given the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test tenant 1' is existing
    When a tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test Tenant 1' should be created
    Then a failure should be generated pointing to tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
