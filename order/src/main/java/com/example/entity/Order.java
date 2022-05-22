package com.example.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.List;

@MongoEntity(collection = "requests", database = "request")
public class Order extends PanacheMongoEntity {

    public String client;

    public List<Dish> dishes;

    public Restaurant restaurant;

    public String deliveryMan;

    public Localization localizationDeliveryMan;
}
