package com.example.controllers;

import dto.DishDTO;
import entities.Dish;
import entities.Restaurant;
import mapper.DishMapper;
import mapper.RestaurantMapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DishResource {

    @Inject
    DishMapper dishMapper;

    @GET
    @Path("/{idRestaurant}/dishes")
    public List<Dish> search(@PathParam("idRestaurant") Long idRestaurant) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant do not exist");
        }
        return Dish.list("restaurant", restaurantOp.get());
    }

    @POST
    @Path("/{idRestaurant}/dish")
    @Transactional
    public Response add(@PathParam("idRestaurant") Long idRestaurant, DishDTO dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant do not exist");
        }
        Dish dish = dishMapper.toDish(dto);
        dish.restaurant = restaurantOp.get();
        dish.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{idRestaurant}/dish/{id}")
    @Transactional
    public void update(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id, DishDTO dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant do not exist");
        }

        Optional<Dish> dishOp = Dish.findByIdOptional(id);
        if(dishOp.isEmpty()) {
            throw new NotFoundException("Dish do not exist");
        }
        Dish dish = dishOp.get();
        dish.name = dto.name;
        dish.persist();
    }

    @DELETE
    @Path("/{idRestaurant}/dish/{id}")
    @Transactional
    public void delete(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant do not exist");
        }

        Optional<Dish> dishOp = Dish.findByIdOptional(id);
        dishOp.ifPresentOrElse(Dish::delete, () -> {
            throw new NotFoundException();
        });
    }
}
