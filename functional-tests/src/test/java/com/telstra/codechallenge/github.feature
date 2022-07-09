Feature: The user can fetch hottest repositories in github created in last 7 days

  Scenario: Get a the hottest github repo with more stars those were created in last 7 days
    * def query = {repos_limit:1}
    Given url microserviceUrl
    And path '/hottest-repos'
    And params query
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    # see https://github.com/intuit/karate#schema-validation
    And match response ==
"""
    {
      "items":

      [
        {"html_url" : '#string',
      "name" : '#string',
      "description" : '#string',
      "language" : '#string',
      "watchers_count" : '#number'}
      ]

    }
    """

  Scenario: Get the two top starred github repositories those were created in last 7 days
    * def query = {repos_limit:2}
    Given url microserviceUrl
    And path '/hottest-repos'
    And params query
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    # see https://github.com/intuit/karate#schema-validation
    And match response ==
"""
    {
      "items":

      [
        {"html_url" : '#string',
      "name" : '#string',
      "description" : '#string',
      "language" : '#string',
      "watchers_count" : '#number'},
      {"html_url" : '#string',
      "name" : '#string',
      "description" : '#string',
      "language" : '#string',
      "watchers_count" : '#number'}
      ]

    }
    """

  Scenario: Try to a fetch 1001 github repo with more stars those were created in last 7 days and get 400 error code

    * def query = {repos_limit:1001}
    Given url microserviceUrl
    And path '/hottest-repos'
    And params query
    When method GET
    Then status 400


  Scenario: Try to a fetch 0  github repo with more stars those were created in last 7 days and get 400 error code

    * def query = {repos_limit:0}
    Given url microserviceUrl
    And path '/hottest-repos'
    And params query
    When method GET
    Then status 400


  Scenario: Try to a fetch github repo with more stars with an invalid request param and get 400 error code

    * def query = {repos_limit:test}
    Given url microserviceUrl
    And path '/hottest-repos'
    And params query
    When method GET
    Then status 400

  Scenario: Try to a fetch github repo with more stars with an invalid URL param and get 404 error code

    Given url microserviceUrl
    And path '/hottest-repos/2'
    When method GET
    Then status 404