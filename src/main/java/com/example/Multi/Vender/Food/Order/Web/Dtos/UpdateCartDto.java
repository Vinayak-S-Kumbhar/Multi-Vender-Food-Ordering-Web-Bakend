package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartDto {
    private long foodId;
    private long userId;
    private int quantity;
}
