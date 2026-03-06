package mission.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

public class APITests {

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://reqres.in";

        RestAssured.requestSpecification =
                new RequestSpecBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("x-api-key", "reqres_ebc851dd85f949078aff49135b8de49b")
                        .setContentType(ContentType.JSON)
                        .build();
    }

    private Response assertStatus(Response r, int expectedStatus) {
        String body = r.getBody().asString();

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

        Response first = given()
                .log().all()
                .get("/api/users?page=1");

        assertStatus(first, 200);

        int totalPages = first.jsonPath().getInt("total_pages");
        int total = first.jsonPath().getInt("total");

        List<Integer> ids = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {

            Response r = given()
                    .get("/api/users?page=" + page);

            assertStatus(r, 200);

            List<Integer> pageIds = r.jsonPath().getList("data.id");

            if (pageIds != null) {
                ids.addAll(pageIds);
            }
        }

        Assert.assertEquals(
                ids.size(),
                total,
                "Collected ids should match total users count"
        );
    }

    // --------------------------------
    // Single User Test
    // --------------------------------

    @Test
    public void shouldReturnSingleUserData() {

        Response r = given()
                .get("/api/users/3");

        assertStatus(r, 200);

        String firstName = r.jsonPath().getString("data.first_name");
        String email = r.jsonPath().getString("data.email");

        Assert.assertEquals(firstName, "Emma");
        Assert.assertEquals(email, "emma.wong@reqres.in");
    }

    // --------------------------------
    // 404 Test
    // --------------------------------

    @Test
    public void shouldReturn404ForMissingUser() {

        Response r = given()
                .get("/api/users/55");

        assertStatus(r, 404);
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

        String payload = String.format(
                "{\"name\": \"%s\", \"job\": \"%s\"}",
                name,
                job
        );

        Response r = given()
                .body(payload)
                .post("/api/users");

        int status = r.getStatusCode();

        Assert.assertTrue(
                status == 201 || status == 200,
                "Unexpected status code: " + status
        );

        String respName = r.jsonPath().getString("name");
        String respJob = r.jsonPath().getString("job");

        String id = r.jsonPath().getString("id");
        String createdAt = r.jsonPath().getString("createdAt");

        Assert.assertEquals(respName, name);
        Assert.assertEquals(respJob, job);

        Assert.assertNotNull(id);
        Assert.assertNotNull(createdAt);
    }

    // --------------------------------
    // Login Success
    // --------------------------------

    @Test
    public void loginSuccessful_shouldReturn200() {

        String payload =
                "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        Response r = given()
                .body(payload)
                .post("/api/login");

        assertStatus(r, 200);

        String token = r.jsonPath().getString("token");

        Assert.assertNotNull(token);
    }

    // --------------------------------
    // Login Negative Test
    // --------------------------------

    @Test
    public void loginUnsuccessful_missingPassword_shouldReturn400() {

        String payload =
                "{\"email\": \"eve.holt@reqres.in\"}";

        Response r = given()
                .body(payload)
                .post("/api/login");

        assertStatus(r, 400);

        String error = r.jsonPath().getString("error");

        Assert.assertTrue(
                error.contains("Missing password"),
                "Expected error message not found"
        );
    }

    // --------------------------------
    // Delayed Response Test
    // --------------------------------

    @Test
    public void delayedResponse_everyUserHasUniqueId() {

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

        Assert.assertEquals(
                uniqueIds.size(),
                ids.size(),
                "User IDs should be unique across pages"
        );
    }
}