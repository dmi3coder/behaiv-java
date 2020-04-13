Feature: Batching for data #38
  currently captured data will load all at once, we need to split saved data into batches

  Scenario: Large amount of data should enable batching mode
    Given amount of data stored just below large threshold
    When app starts capturing data
    Then kernel should switch to batching mode

  Scenario: App shouldn't store data in-memory in batching mode
    Given large amount of data saved
    When app starts and tries to restore data
    Then kernel should only keep model in-memory

  Scenario: During training data should be fed in batches if possible
    Given large amount of data saved
    Given kernel that supports partialFit
    When app starts capturing data
    Then kernel should use batches while training