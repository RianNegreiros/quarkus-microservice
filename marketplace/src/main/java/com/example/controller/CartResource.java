package com.example.controller;

import com.example.DTO.DishDTO;
import com.example.DTO.DishOrderedDTO;
import com.example.DTO.OrderMadeDTO;
import com.example.DTO.RestaurantDTO;
import com.example.entity.CartDish;
import com.example.entity.Dish;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private static final String CLIENT = "a";

    @Inject
    PgPool pgPool;

    @Inject
    @Channel("orders")
    Emitter<OrderMadeDTO> Orderemitter;

    @GET
    public Uni<List<CartDish>> searchCarts() {
        return CartDish.findCart(pgPool, CLIENT);
    }

    @POST
    @Path("/dish/{idDish}")
    public Uni<Long> addDish(@PathParam("idDish") Long idDish) {
        CartDish pc = new CartDish();
        pc.client = CLIENT;
        pc.dish = idDish;
        return CartDish.save(pgPool, CLIENT, idDish);

    }

    @POST
    @Path("/finish-order")
    public Uni<Boolean> finish() {
        OrderMadeDTO order = new OrderMadeDTO();
        String client = CLIENT;
        order.client = client;
        List<CartDish> cartDish = CartDish.findCart(pgPool, client).await().indefinitely();
        List<DishOrderedDTO> dishes = cartDish.stream().map(pc -> from(pc)).collect(Collectors.toList());
        order.dishes = dishes;

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.name = "restaurant name";
        order.restaurant = restaurant;
        Orderemitter.send(order);
        return CartDish.delete(pgPool, client);
    }

    private DishOrderedDTO from(CartDish cartDish) {
        DishDTO dto = Dish.findById(pgPool, cartDish.dish).await().indefinitely();
        return new DishOrderedDTO(dto.name, dto.description, dto.price);
    }
}
