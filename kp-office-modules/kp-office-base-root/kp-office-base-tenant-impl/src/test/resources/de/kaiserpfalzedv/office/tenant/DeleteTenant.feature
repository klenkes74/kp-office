Feature: Delete Tenant
  Deleting tenants from the system

  Scenario: Delete an existing tenant
    Given the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test tenant 1' is existing
    When deleting tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
    Then there should be no tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' in the system


  Scenario: Delete a non-existing tenant
    Given the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' does not exist in the system.
    When deleting tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
    Then there should be no tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' in the system
