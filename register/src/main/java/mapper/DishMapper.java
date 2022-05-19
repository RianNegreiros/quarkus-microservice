package mapper;

import dto.DishDTO;
import entities.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    Dish toDish(DishDTO dto);
}
