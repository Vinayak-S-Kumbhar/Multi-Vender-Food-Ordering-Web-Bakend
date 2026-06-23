package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdressDto {

    private double longitude;
    private double latitude;
    private long userId;

    private LocalDateTime createdAt;
}
