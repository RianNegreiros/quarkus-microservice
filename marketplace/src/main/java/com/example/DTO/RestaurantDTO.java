package com.example.DTO;

import com.example.entity.Localization;
import io.vertx.mutiny.sqlclient.Row;

public class RestaurantDTO {

    public Long id;

    public String name;

    public Localization localization;

    public static RestaurantDTO from(Row row) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.name = row.getString("name");
        dto.id = row.getLong("id");
        return dto;
    }
}
