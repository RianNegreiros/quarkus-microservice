package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class DishResourceTest {

    @Test
    public void testGetDishes() {
        given()
                .when().get("/dishes")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
