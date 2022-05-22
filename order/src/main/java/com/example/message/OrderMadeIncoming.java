package com.example.message;

import com.example.DTO.DishOrderedDTO;
import com.example.DTO.OrderMadeDTO;
import com.example.elasticsearch.ESService;
import com.example.entity.Dish;
import com.example.entity.Order;
import com.example.entity.Restaurant;
import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;

@ApplicationScoped
public class OrderMadeIncoming {

    @Inject
    ESService esService;

    @Incoming("orders")
    public void redOrders(OrderMadeDTO dto) {
        Order order = new Order();
        order.client = dto.client;
        order.dishes = new ArrayList<>();
        dto.dishes.forEach(dish -> order.dishes.add(from((dish))));
        Restaurant restaurant = new Restaurant();
        restaurant.name = dto.restaurant.name;
        order.restaurant = restaurant;
        String json = JsonbBuilder.create().toJson(dto);
        esService.index("orders", json);
        order.persist();
    }

    private Dish from(DishOrderedDTO dishOrdered) {
        Dish dish = new Dish();
        dish.description = dishOrdered.description;
        dish.name = dishOrdered.name;
        dish.price = new Decimal128(dishOrdered.price);
        return dish;
    }
}
