package com.example.message;

import com.example.entity.Restaurant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class RegisteredRestaurant {

  @Inject
  PgPool pgPool;

  @Incoming("restaurants")
  public void receiveRestaurant(String json) {
    Jsonb create = JsonBuilder.create();
    Restaurant restaurant = create.fromJson(json, Restaurant.class);

    restaurant.persist(pgPool);
  }
  
}
