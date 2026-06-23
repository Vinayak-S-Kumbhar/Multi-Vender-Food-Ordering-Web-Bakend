package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContactUsDto {
    private String reason;

    private String name;

    private String email;

    private String message;

}
