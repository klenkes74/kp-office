Feature: A correctly working paging request.

  Scenario: Creating a valid new paging request.
    Given a start row of '5'
    And a page size of '5'
    When creating the paging request
    Then there is a paging request with a start row of '5' and a page size of '5'

  Scenario: Creating an invalid new paging request with a not-matching start row.
    Given a start row of '7'
    And a page size of '10'
    When creating the paging request
    Then The 'IllegalArgumentException' should contain the message 'bla'
