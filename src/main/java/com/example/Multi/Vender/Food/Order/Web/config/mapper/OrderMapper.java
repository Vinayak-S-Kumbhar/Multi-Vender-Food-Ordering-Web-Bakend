package com.example.Multi.Vender.Food.Order.Web.config.mapper;

import com.example.Multi.Vender.Food.Order.Web.Dtos.OrderDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);

    OrderDto toDto(Order order);
}
