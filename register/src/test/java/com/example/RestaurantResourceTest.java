package com.example;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
class RestaurantResourceTest {

    @Test
    public void testSearchRestaurants() {
        given()
             .when().get("/restaurants")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
}