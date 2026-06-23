package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodItemResDto {
    private FoodItemDto foodItem;

    private String venderName;
}
