package api.tests;

import api.base.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {

    @Test
    void createUser1() {
        String body = """
                    {
                      "name": "morpheus",
                      "job": "leader"
                    }
                """;

        given()
                .baseUri("https://reqres.in")
                .basePath("/api/users")
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue());
    }

    @Test
    void createUser2() {
        String body = """
                    {
                      "name": "morpheus",
                      "job": "leader"
                    }
                """;

        request
                .basePath("/api/users")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue());
    }

    @Test
    void getUsersList() {
        request
                .basePath("/api/users")
                .queryParam("page", 1)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("data", not(empty()))
                .body("data[0].email", endsWith("@reqres.in"));
    }

    @Test
    void getSingleUser() {
        request
                .basePath("/api/users/2")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", containsString("@reqres.in"));
    }

    @Test
    void getNonExistentUser() {
        request
                .basePath("/api/users/23")
                .when()
                .get()
                .then()
                .statusCode(404);
    }

    @Test
    void registerUserMissingPassword() {
        String body = """
        {
            "email": "eve.holt@reqres.in"
        }
    """;

        request
                .basePath("/api/register")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    void loginSuccessfully() {
        String body = """
        {
            "email": "eve.holt@reqres.in",
            "password": "cityslicka"
        }
    """;

        request
                .basePath("/api/login")
                .queryParam("page", 1)
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }




}