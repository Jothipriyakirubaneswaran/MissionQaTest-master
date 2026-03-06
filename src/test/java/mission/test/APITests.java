package mission.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.*;

import mission.utils.ExtentReportManager;

import java.util.*;

import static io.restassured.RestAssured.*;

public class APITests {

    /*
     * This method runs once before all tests.
     * It sets the Base URI and request configuration.
     */

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://reqres.in";

        RestAssured.requestSpecification =
                new RequestSpecBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("x-api-key", "reqres_ebc851dd85f949078aff49135b8de49b")
                        .setContentType(ContentType.JSON)
                        .build();

        // Initialize report
        ExtentReportManager.getReportInstance();
    }

    /*
     * Flush report after all tests complete
     */
    @AfterClass
    public void tearDown() {

        ExtentReportManager.flushReport();
    }

    /*
     * Utility method to validate response status code
     */

    private Response assertStatus(Response r, int expectedStatus) {

        String body = r.getBody().asString();

        ExtentReportManager.logStep("Response Body: " + body);

        Assert.assertEquals(
                r.getStatusCode(),
                expectedStatus,
                "Unexpected status code. Response body:\n" + body
        );

        return r;
    }

    // --------------------------------
    // List Users Pagination Test
    // --------------------------------

    @Test
    public void shouldListUsers_totalEqualsNumberOfIds() {

        ExtentReportManager.createTest("Verify Users Pagination");

        ExtentReportManager.logStep("Sending GET request to /api/users?page=1");

        Response first = given()
                .get("/api/users?page=1");

        assertStatus(first, 200);

        int totalPages = first.jsonPath().getInt("total_pages");
        int total = first.jsonPath().getInt("total");

        ExtentReportManager.logStep("Total Pages: " + totalPages);
        ExtentReportManager.logStep("Total Users: " + total);

        List<Integer> ids = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {

            ExtentReportManager.logStep("Fetching page: " + page);

            Response r = given()
                    .get("/api/users?page=" + page);

            assertStatus(r, 200);

            List<Integer> pageIds = r.jsonPath().getList("data.id");

            if (pageIds != null) {
                ids.addAll(pageIds);
            }
        }

        Assert.assertEquals(ids.size(), total);

        ExtentReportManager.pass("User pagination validation successful");
    }

    // --------------------------------
    // Single User Test
    // --------------------------------

    @Test
    public void shouldReturnSingleUserData() {

        ExtentReportManager.createTest("Verify Single User API");

        ExtentReportManager.logStep("Sending request to /api/users/3");

        Response r = given()
                .get("/api/users/3");

        assertStatus(r, 200);

        String firstName = r.jsonPath().getString("data.first_name");
        String email = r.jsonPath().getString("data.email");

        ExtentReportManager.logStep("Validating user details");

        Assert.assertEquals(firstName, "Emma");
        Assert.assertEquals(email, "emma.wong@reqres.in");

        ExtentReportManager.pass("User details validated successfully");
    }

    // --------------------------------
    // 404 Test
    // --------------------------------

    @Test
    public void shouldReturn404ForMissingUser() {

        ExtentReportManager.createTest("Verify Missing User API");

        ExtentReportManager.logStep("Sending request for non-existing user");

        Response r = given()
                .get("/api/users/55");

        assertStatus(r, 404);

        ExtentReportManager.pass("404 validation successful");
    }

    // --------------------------------
    // DataProvider
    // --------------------------------

    @DataProvider(name = "createUserData")
    public Object[][] createUserData() {

        return new Object[][]{
                {"Peter", "Manager"},
                {"Liza", "Sales"}
        };
    }

    // --------------------------------
    // Create User Test
    // --------------------------------

    @Test(dataProvider = "createUserData")
    public void shouldCreateUser_andReturnExpectedFields(String name, String job) {

        ExtentReportManager.createTest("Create User API - " + name);

        String payload = String.format(
                "{\"name\": \"%s\", \"job\": \"%s\"}",
                name,
                job
        );

        ExtentReportManager.logStep("Sending POST request to create user");

        Response r = given()
                .body(payload)
                .post("/api/users");

        int status = r.getStatusCode();

        Assert.assertTrue(status == 201 || status == 200);

        String respName = r.jsonPath().getString("name");
        String respJob = r.jsonPath().getString("job");

        Assert.assertEquals(respName, name);
        Assert.assertEquals(respJob, job);

        ExtentReportManager.pass("User created successfully");
    }

    // --------------------------------
    // Login Success Test
    // --------------------------------

    @Test
    public void loginSuccessful_shouldReturn200() {

        ExtentReportManager.createTest("Login Success API");

        String payload =
                "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        ExtentReportManager.logStep("Sending login request");

        Response r = given()
                .body(payload)
                .post("/api/login");

        assertStatus(r, 200);

        String token = r.jsonPath().getString("token");

        Assert.assertNotNull(token);

        ExtentReportManager.pass("Login successful and token received");
    }

    // --------------------------------
    // Login Negative Test
    // --------------------------------

    @Test
    public void loginUnsuccessful_missingPassword_shouldReturn400() {

        ExtentReportManager.createTest("Login Negative Test");

        String payload =
                "{\"email\": \"eve.holt@reqres.in\"}";

        Response r = given()
                .body(payload)
                .post("/api/login");

        assertStatus(r, 400);

        String error = r.jsonPath().getString("error");

        Assert.assertTrue(error.contains("Missing password"));

        ExtentReportManager.pass("Proper error message returned");
    }

    // --------------------------------
    // Delayed Response Test
    // --------------------------------

    @Test
    public void delayedResponse_everyUserHasUniqueId() {

        ExtentReportManager.createTest("Delayed Response API");

        Response first = given()
                .get("/api/users?delay=3&page=1");

        assertStatus(first, 200);

        int totalPages = first.jsonPath().getInt("total_pages");

        List<Integer> ids = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {

            Response r = given()
                    .get("/api/users?delay=3&page=" + page);

            assertStatus(r, 200);

            List<Integer> pageIds = r.jsonPath().getList("data.id");

            if (pageIds != null) {
                ids.addAll(pageIds);
            }
        }

        Set<Integer> uniqueIds = new HashSet<>(ids);

        Assert.assertEquals(uniqueIds.size(), ids.size());

        ExtentReportManager.pass("All user IDs are unique");
    }
}