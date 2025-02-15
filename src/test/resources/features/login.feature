Feature: Login Functionality

  Background:
    Given I open the login page
  @smoke
  Scenario Outline: Login
    When I enter "<username>" and "<password>"
    Then I should see the "<outcome>"

    Examples:
      | username              | password     | outcome          |
      | lumaQA@mailinator.com | Test@12345   | Home Page        |
      | lumaQA@mailinator.com | Test@123456  | Customer Login   |

    @regression
  Scenario Outline: Login
    When I enter "<username>" and "<password>"
    Then I should get the list of men menus
    Examples:
      | username              | password   | outcome         |
      | lumaQA@mailinator.com | Test@12345 | Home Page |

