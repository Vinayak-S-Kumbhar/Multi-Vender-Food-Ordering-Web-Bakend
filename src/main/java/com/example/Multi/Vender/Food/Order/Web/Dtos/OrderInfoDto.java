package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto {
    private long orderId;
    private String VenderName;
    private int totalItems;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderTime;
}
