package com.example;

import com.example.DTO.OrderMadeDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<OrderMadeDTO> {

    public OrderDeserializer() {
        super(OrderMadeDTO.class);
    }
}
