package com.example.DTO;

import java.math.BigDecimal;

public class DishOrderedDTO {

    public String name;

    public String description;

    public BigDecimal price;

    public DishOrderedDTO() {
        super();
    }

    public DishOrderedDTO(String name, String description, BigDecimal price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
