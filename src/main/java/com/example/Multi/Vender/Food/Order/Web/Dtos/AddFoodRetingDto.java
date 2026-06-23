package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFoodRetingDto {

    private int rating;

    private String review;

    private long userId;

    private long foodId;
}
