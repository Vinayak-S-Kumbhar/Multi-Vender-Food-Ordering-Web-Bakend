package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Entity.Order;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private double deliveryFee;
    private double totalAmount;
    private double savings;
    private String note;
    private long userId;
    private long restorentId;

}
