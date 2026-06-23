package com.example.Multi.Vender.Food.Order.Web.config.mapper;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddRestorentDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.RestorentDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.RestorentWithFoodListDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestorentMapper {

    Restorent toEntity(RestorentDto dto);

    RestorentDto toDto(Restorent entity);

    Restorent toEntity(AddRestorentDto dto);

    AddRestorentDto toAddRestorentDto(Restorent entity);


}