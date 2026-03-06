# QA Automation Assignment

This project contains two automated test modules:
API Tests
UI Tests
# For the API tests, please visit:
https://reqres.in/
# For the UI tests, please visit:
https://www.saucedemo.com/
The framework validates both API endpoints and UI user flows using an automated testing approach.

# The framework is implemented using:

Java
Selenium WebDriver
RestAssured
TestNG
Extent Reports
Page Object Model
The code is designed to be reusable, maintainable, and scalable.
Test Automation Framework
This is a Maven-based Automation Framework.
The pom.xml file contains all dependencies required to build and execute the tests.

# Additional dependencies added include:
Selenium WebDriver
TestNG
RestAssured
Extent Reports
Project Structure

# The following folder contains the core automation framework classes

src/main/java/mission
mission.base

# BaseTest
Handles WebDriver initialization and teardown
Opens and closes the browser before and after test execution

# mission.pages
This package contains Page Object Model classes.
Each class represents a page of the UI application.
LoginPage
Handles login functionality
ProductsPage
Handles product listing page
Adding products to cart
Cart navigation
CartPage
Handles cart operations
Verify quantities
Remove items
CheckoutPage
Handles checkout flow
Entering user details
Validating order totals
mission.utils
This package contains reusable utility classes.
ConfigReader
Reads values from config.properties
ExtentReportManager
Creates and configures Extent Reports
TestListener
# TestNG listener used for:
Logging test steps
Capturing screenshots on failure
Updating Extent Reports
Test Classes

# The following folder contains the test implementation classes

src/test/java/mission/test
UITest
Automates the end-to-end checkout flow in SauceDemo.

# Steps covered:
Launch browser
Login with valid credentials
Add multiple products to cart
Verify cart count
Navigate to cart page
Validate item quantities
Remove one item
Proceed to checkout
Enter checkout details
Validate tax calculation

Products added to cart:
Sauce Labs Backpack
Sauce Labs Fleece Jacket
Sauce Labs Bolt T-Shirt
Sauce Labs Onesie

APITests
Automates multiple ReqRes API endpoints.
Test scenarios include:
List users with pagination
Validate total users count
Get single user details
Validate 404 response for missing user
Create user using DataProvider
Validate successful login
Validate login failure
Validate delayed API responses
Configuration

# Test data and configuration values are stored in:
src/test/resources/config.properties
Example:
username=standard_user
password=secret_sauce
browser=chrome
This allows easy modification of test inputs without changing the code.

# Reporting
The framework uses Extent Reports for detailed test reporting.
Reports include:
Test case status
Execution steps
Pass/Fail results
Error messages
Screenshots on failure

# Reports are generated in:
/reports
Each execution generates a timestamped report so previous results are not overwritten.

# Example:
reports/TestExecutionReport_20260306_184530.html
Steps to Execute This Project
Pre-requisites

# Install the following tools:
Java SDK 8 or higher
Maven
Chrome Browser
Internet access
Running the Tests
Run All Tests

# From command line:

mvn clean test
Compile Tests Only
mvn test-compile
Run Tests from IDE

# Open the project in Eclipse or IntelliJ
Locate testng.xml

# Run the TestNG file

# Test Results
Execution results will be generated in:
test-output and /reports
# Troubleshooting
Error:
Cannot find class in classpath: mission.test.APITests
Possible fixes:
Build the project before executing tests
mvn test-compile
or
mvn clean test
Ensure the test classes exist in:
target/test-classes

# If running from IDE, ensure the module's test output directory is included in the classpath
API Authentication Issues
# If API requests return 401 Unauthorized, verify that the request header includes the required API key.
Example header used in this framework:
x-api-key: <your_api_key>
