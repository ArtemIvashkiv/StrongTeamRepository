package api.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class NewMockedTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startMockServer() {
        wireMockServer = new WireMockServer(8000); // локальный порт
        wireMockServer.start();

        configureFor("localhost", 8000);

        stubFor(get(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                    {
                      "data": {
                        "id": 1,
                        "email": "george.bluth@reqres.in",
                        "first_name": "George",
                        "last_name": "Bluth"
                      }
                    }
                """)));
        stubFor(post(urlEqualTo("/api/new-user"))
                .withRequestBody(containing("""
                        {
                        "id": 2,
                        "email": "george.bluth@reqres.in",
                        "first_name": "George",
                        "last_name": "Bluth"
                      }
                      """))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(201)
                .withBody("""
                    {
                      "data": {
                        "status": "created",
                        "id": 2,
                        "email": "george.bluth@reqres.in",
                        "first_name": "George",
                        "last_name": "Bluth"
                      }
                    }
                """)));

        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                    {
                      "data": {
                      "1": {
                        "id": 1,
                        "email": "george.bluth@reqres.in",
                        "first_name": "George",
                        "last_name": "Bluth"
                      },
                      "2": {
                      "id": 2,
                        "email": "george.bluth@reqres.in",
                        "first_name": "George",
                        "last_name": "Bluth"
                      }
                    }
                """)));
    }

    @AfterAll
    static void stopMockServer() {
        wireMockServer.stop();
    }

    @Test
    void postMockedUser() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
                .log().all()
                .body("""
                        {
                        "id": 2,
                        "email": "george.bluth@reqres.in",
                        "first_name": "George",
                        "last_name": "Bluth"
                      }
                      """)
                .when()
                .post("/api/new-user")
                .then()
                .log().body()
                .statusCode(201)
                .body("data.status", equalTo("created"))
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("george.bluth@reqres.in"))
                .body("data.first_name", equalTo("George"))
                .body("data.last_name", equalTo("Bluth"));
    }

    @Test
    void getMockedUsers() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
                .log().all()
                .when()
                .get("/api/users")
                .then()
                .log().body()
                .statusCode(200);
    }
}
