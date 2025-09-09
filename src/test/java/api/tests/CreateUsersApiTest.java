package api.tests;

import api.base.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateUsersApiTest extends BaseTest {

    @Test
    @BeforeEach
    void createUserForTest() {
        String body = """
        {
            "name": "TestUser",
            "job": "qa"
        }
    """;

        request
                .basePath("/api/users")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }
}
