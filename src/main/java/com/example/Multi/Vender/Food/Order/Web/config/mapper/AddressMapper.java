package com.example.Multi.Vender.Food.Order.Web.config.mapper;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressResponseDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressResponseDto addressResponseDto);

    AddressResponseDto toDto(Address address);
}
