Feature: User Registration on Basketball England
  Scenario: Register user
    Given User navigates to the registration page with "chrome"
    And Entered date of brith "21/05/2006"
    And Entered first name "Semon"
    And Entered last name "Andy"
    And Entered Email Adress "mstira9@live.com"
    And Entered confirmed Email Adress "mstira9@live.com"
    And Entered password "ms5919"
    And Entered Retyped password "ms5919"
    And Checked box for marketing communications
    And Checked box for Terms and Conditions "true"
    And Checked the box for age over 18
    And Checked box for Code of Ethics
    When  Press for confirm and join
    Then the application is successful

    Scenario: Last name missing
      Given User navigates to the registration page with "chrome"
      And Entered date of brith "21/05/2006"
      And Entered first name "Emil"
      And Entered last name ""
      And Entered Email Adress "mstira@live.com"
      And Entered confirmed Email Adress "mstira@live.com"
      And Entered password "hello123"
      And Entered Retyped password "hello123"
      And Checked box for marketing communications
      And Checked box for Terms and Conditions "true"
      And Checked the box for age over 18
      And Checked box for Code of Ethics
      When  Press for confirm and join
      Then the application not successful

  Scenario Outline: Register user with different input variations
    Given User navigates to the registration page with "chrome"
    And Entered date of brith "21/05/2006"
    And Entered first name "<first_name>"
    And Entered last name "<last_name>"
    And Entered Email Adress "<email>"
    And Entered confirmed Email Adress "<confirm_email>"
    And Entered password "<password>"
    And Entered Retyped password "<retype_password>"
    And Checked box for marketing communications
    And Checked box for Terms and Conditions "<accept_terms>"
    And Checked the box for age over 18
    And Checked box for Code of Ethics
    When Press for confirm and join
    Then the application result should be "<expected_result>"

    Examples:
      | first_name | last_name | email            | confirm_email    | password | retype_password | accept_terms | expected_result    |
      | Semon      |           | mstira5@live.com | mstira5@live.com | ms5919   | ms5919          | true         | missing_last_name  |
      | Semon      | Andy      | mstira5@live.com | mstira5@live.com | ms5919   | ms9999          | true         | password_mismatch  |
      | Semon      | Andy      | mstira5@live.com | mstira5@live.com | ms5919   | ms5919          | false        | terms_not_accepted |
