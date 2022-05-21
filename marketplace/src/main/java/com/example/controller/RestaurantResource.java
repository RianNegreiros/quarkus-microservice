package com.example.controller;


import com.example.DTO.DishDTO;
import com.example.DTO.RestaurantDTO;
import com.example.entity.Dish;
import com.example.entity.Restaurant;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    @Inject
    PgPool pgPool;

    @GET
    @Path("{idRestaurant}/dishes")
    public Multi<DishDTO> searchDishes(@PathParam("idRestaurant") Long idRestaurant) {
        return Dish.findAll(pgPool, idRestaurant);
    }
}
