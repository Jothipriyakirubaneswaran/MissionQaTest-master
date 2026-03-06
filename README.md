QA Automation Assignment – Project Overview
This automation project is designed to validate API endpoints and UI user flows using an automated testing framework. The framework is Maven-based and implemented in Java, leveraging Selenium WebDriver, RestAssured, TestNG, and Extent Reports.
________________________________________
1. Project Scope
The framework includes two main modules:
1.	API Tests – Tests various endpoints provided by ReqRes.
2.	UI Tests – Automates user flows on SauceDemo.
The goal is to create a reusable, maintainable, and scalable automation framework.
________________________________________
2. Technologies & Tools
•	Programming Language: Java
•	Web Automation: Selenium WebDriver
•	API Testing: RestAssured
•	Test Framework: TestNG
•	Reporting: Extent Reports
•	Design Pattern: Page Object Model (POM)
•	Build Tool: Maven
Additional dependencies in pom.xml include:
•	Selenium WebDriver
•	TestNG
•	RestAssured
•	Extent Reports
________________________________________
3. Project Structure
3.1 Core Framework – src/main/java/mission
mission.base
•	BaseTest.java – Handles browser initialization and teardown. Opens and closes the browser before and after test execution.
mission.pages – Page Object Model (POM) classes representing UI pages.
•	LoginPage.java – Manages login functionality.
•	ProductsPage.java – Handles product listings, adding products to the cart, and cart navigation.
•	CartPage.java – Manages cart operations, verifies quantities, and removes items.
•	CheckoutPage.java – Handles checkout flows, user details entry, and order total validation.
mission.utils – Reusable utility classes.
•	ConfigReader.java – Reads test configuration values from config.properties.
•	ExtentReportManager.java – Configures and manages Extent Reports.
•	TestListener.java – TestNG listener for logging steps, capturing screenshots on failure, and updating reports.
________________________________________
3.2 Test Implementation – src/test/java/mission/test
UI Tests (UITest.java)
Automates the end-to-end checkout flow in SauceDemo:
Test Steps:
1.	Launch the browser.
2.	Login using valid credentials.
3.	Add multiple products to the cart:
o	Sauce Labs Backpack
o	Sauce Labs Fleece Jacket
o	Sauce Labs Bolt T-Shirt
o	Sauce Labs Onesie
4.	Verify cart count.
5.	Navigate to the cart page.
6.	Validate item quantities.
7.	Remove one item.
8.	Proceed to checkout.
9.	Enter checkout details.
10.	Validate tax calculation.
API Tests (APITests.java)
Validates multiple endpoints from ReqRes:
Test Scenarios:
•	List users with pagination.
•	Validate total user count.
•	Get single user details.
•	Validate 404 response for missing users.
•	Create users using DataProvider.
•	Validate successful login.
•	Validate login failure.
•	Validate delayed API responses.
________________________________________
4. Configuration
All test data and configuration values are stored in:
src/test/resources/config.properties
Example:
username=standard_user
password=secret_sauce
browser=chrome
This allows easy modification of test inputs without changing the code.
________________________________________
5. Reporting
The framework uses Extent Reports for detailed reporting:
•	Displays test case status, execution steps, pass/fail results, error messages.
•	Captures screenshots on test failure.
•	Reports are saved in /reports with timestamped filenames to prevent overwriting.
Example:
reports/TestExecutionReport_20260306_184530.html
________________________________________
6. Execution Instructions
Prerequisites
•	Java SDK 8 or higher
•	Maven
•	Chrome Browser
•	Internet access
Running Tests
From Command Line:
•	Run all tests:
mvn clean test
•	Compile tests only:
mvn test-compile
From IDE (Eclipse / IntelliJ):
1.	Open the project.
2.	Locate testng.xml.
3.	Run the TestNG file.
Test Results
Results are generated in:
•	test-output folder
•	/reports folder
________________________________________
7. Troubleshooting
Issue: Cannot find class in classpath: mission.test.APITests
Fixes:
•	Build the project first:
mvn test-compile
•	Or clean and test:
mvn clean test
•	Ensure the test classes exist in: target/test-classes
•	From IDE, include the module’s test output directory in the classpath.
API Authentication Issues:
•	If API requests return 401 Unauthorized, verify the header includes the API key:
x-api-key: <your_api_key>

