package com.example;

import entities.Restaurant;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
@Transactional
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