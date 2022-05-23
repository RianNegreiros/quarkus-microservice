package com.example.mapper;

import com.example.dto.RestaurantDTO;
import com.example.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant toRestaurant(RestaurantDTO dto);
}
