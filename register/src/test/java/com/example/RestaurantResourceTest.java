package com.example;

import entities.Restaurant;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
@Transactional
class RestaurantResourceTest {

    @Test
    public void testSearchRestaurants() {
        final Restaurant restaurant1 = new Restaurant();
        restaurant1.name = "any-name1";
        restaurant1.owner = "any-owner1";
        restaurant1.persist();
        final Restaurant restaurant2 = new Restaurant();
        restaurant2.name = "any-name2";
        restaurant2.owner = "any-owner2";
        restaurant2.persist();

        given().contentType(ContentType.JSON)
             .when().get("/restaurants")
                .then()
                .statusCode(200);
    }
}