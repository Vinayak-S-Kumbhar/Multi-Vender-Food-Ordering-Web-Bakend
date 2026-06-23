package com.example.Multi.Vender.Food.Order.Web.config.mapper;

import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {
    FoodItem toEntity(FoodItemDto foodItemDto);

    FoodItemDto toDto(FoodItem foodItem);
}
