package com.example.Multi.Vender.Food.Order.Web.config.mapper;

import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsResDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.ContactUs;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactUsMapper {

    ContactUs toEntity(ContactUsResDto contactUsResDto);

    ContactUsResDto toDto(ContactUs contactUs);
}
