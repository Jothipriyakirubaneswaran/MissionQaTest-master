# QA Automation Assignment

We have added two tests: the first task is API-Test.feature and the second is UI-Test.feature.
- For the API-Test.feature, please visit https://reqres.in/. This should contain all the requirements.
- UI-Test.feature please visit https://www.saucedemo.com/


Please DO use Page objects, make sure the code is reusable and feel free to improve the current code.

**Note: We have intentionally added some bugs for you to debug.** 

Please contact the Mission Team if you have any questions.


Good luck!

## Test Automation Framework

- This is a Maven based framework
- `pom.xml` should have everything you need to create and run the tests. Please add further dependencies if you require it.

The following folder `src/test/java/AutomationTest/mission` contains the following class:

- `Hook` - this is the before and after. This launches and kills the browser.
- `RunnerTest` - contains the CucumberOptions which runs the BDD's

The following folder `src/main/java/AutomationTest/mission` contains the following class:

- `BrowserSetup` - This contains the setup of a given browser based on what is set to Browser property within `TestData.properties` 

 
## Steps to execute this project

- Pre-requisites
    - JAVA SDK 1.8 or higher
    - Maven CLI (on PATH)
    
- Steps
    - Clone the project to local
    - Open a command line or any IDE that supports Java & Maven
    - Make sure to build the project so test classes are compiled into `target/test-classes` before running TestNG directly:

        - From command line run:

            mvn clean test

        - Or run only compilation (if you want to compile without running tests):

            mvn test-compile

    - If you prefer to run TestNG via IDE, open the `testng.xml` at the project root (we included one that references `mission.test.APITests`) and run it from the IDE **after building the project**.

    - On Windows you can use the helper script `run-tests.bat` which will attempt to run `mvn test` (it checks that `mvn` is on PATH and prints instructions if not).

    - Results will be captured in the `test-output` folder.

Troubleshooting: "Cannot find class in classpath: mission.test.APITests"

- This error means TestNG could not find the compiled test class. Common fixes:
  - Build the project first (see `mvn test-compile` or `mvn test`). TestNG needs the compiled classes in `target/test-classes`.
  - Ensure your IDE's Run Configuration includes the module's test output directory on the classpath.
  - If running TestNG from command line with `testng.xml`, run `mvn test` or `mvn -Dtest=mission.test.APITests test` so surefire compiles and executes tests with dependencies on the classpath.
  - If you still see 403 or non-JSON responses, verify network access and that `https://reqres.in/api` is reachable from your environment.

If you'd like, I can also add a small `mvnw` wrapper to the repo so you can run `mvnw test` without installing Maven globally. Let me know if you want that and I will add it.