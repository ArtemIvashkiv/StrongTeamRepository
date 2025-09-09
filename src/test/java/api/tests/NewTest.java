package api.tests;

import api.base.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NewTest extends BaseTest {
    @Test
    void giveProToUser(){
        request
                .basePath("/api/clerk/link-pro")
                .body("""
                        {
                        "email": "eve.holt@reqres.in"
                        }
                        """)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    void autoGiveProToUser(){
        request
                .basePath("/api/clerk/auto-link-pro")
                .body("""
                        {
                        "email": "eve.holt@reqres.in"
                        }
                        """)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    void removeProToUser(){
        request
                .basePath("/api/clerk/unlink-pro")
                .body("""
                        {
                        "email": "eve.holt@reqres.in"
                        }
                        """)
                .when()
                .post()
                .then()
                .statusCode(201);
    }


    @Test
    void getCustomEndpoints(){
        request
                .basePath("/api/custom-endpoints")
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    void getSingleCustomEndpoint(){
        request
                .basePath("/api/custom-endpoints/2")
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    void registerUser() {
        String body = """
        {
            "email": "eve.holt@reqres.in",
            "password": "cityslicka"
        }
    """;

        request
                .basePath("/api/register")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200);
    }
}
