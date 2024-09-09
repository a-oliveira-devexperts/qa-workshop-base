# Playwright Workshop

## Overview
This project is a Java-based application that uses Playwright for browser automation and testing. The tests are written using JUnit 5 and are configured to run with Gradle.

## Prerequisites
- Java 17
- Gradle 8.5 or higher

## Running Tests

To run the tests, use the following Gradle command:
```sh
./gradlew :playwright-workshop:test
```

## Running Tests Separately
To run tests separately in a Gradle project, you can use the --tests option followed by the fully qualified name of the test class or method you want to run. Here are some examples:  
Running a Single Test Class
To run a single test class, use the following command:
```sh
./gradlew :playwright-workshop:test --tests org.example.ExampleOneTest
```

## Running a Single Test Method
To run a specific test method within a test class, use the following command:
```sh
./gradlew :playwright-workshop:test --tests org.example.ExampleOneTest.testLogin
```

## Running Multiple Test Classes
To run multiple test classes, use the following command:
```sh
./gradlew :playwright-workshop:test --tests "org.example.ExampleOneTest,org.example.ExampleTwoTest"
```

## Running Multiple Test Methods
To run multiple test methods within a test class, use the following command:
```sh
./gradlew :playwright-workshop:test --tests "org.example.ExampleOneTest.testLogin,org.example.ExampleOneTest.testLogout"
```
