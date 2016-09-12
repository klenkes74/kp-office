Feature: Update Tenant
  The data of a tenant needs to be changeable.

  Scenario: Update an existing tenant
    Given: the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with name 'Test tenant 1' is existing.
    When updating tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with the name 'Test tenant A'
    Then tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' should have the name 'Test tenant A'


  Scenario: Updating a non-existing tenant
    Given: the tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' does not exist on the sytem
    When updating tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0' with the name 'Test tenant A'
    Then a failure should be generated pointing to tenant 'c72178d0-d8f6-42a8-9991-5d1bb744efa0'
