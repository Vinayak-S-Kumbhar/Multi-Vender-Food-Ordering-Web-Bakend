package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private long foodId;
    private String foodName;
    private String description;
    private double price;
    private int quantity;

}
