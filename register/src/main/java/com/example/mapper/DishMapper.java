package com.example.mapper;

import com.example.dto.DishDTO;
import com.example.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    Dish toDish(DishDTO dto);
}
