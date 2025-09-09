package api.base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification request;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";

        request = RestAssured
                .given()
                .log().all()  // лог запроса
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json");
    }
}

