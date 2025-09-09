package api.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MockedTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startMockServer() {
        wireMockServer = new WireMockServer(8089); // локальный порт
        wireMockServer.start();

        configureFor("localhost", 8089);

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
    }

    @AfterAll
    static void stopMockServer() {
        wireMockServer.stop();
    }

    @Test
    void getMockedUser() {
        RestAssured.baseURI = "http://localhost:8089";

        given()
            .log().all()
        .when()
             .get("/api/users/1")
        .then()
            .log().body()
            .statusCode(200)
            .body("data.id", equalTo(1))
            .body("data.email", equalTo("george.bluth@reqres.in"))
            .body("data.first_name", equalTo("George"))
            .body("data.last_name", equalTo("Bluth"));
    }
}
