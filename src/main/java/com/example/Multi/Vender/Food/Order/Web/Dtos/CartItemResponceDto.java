package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CartItemResponceDto {
    private long restorentId;
    private String restorentName;
    private String ownerName;
    private String mobileNumber;
    private String email;
    private List<CartItemDto> foodItemList;
}
