package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import com.example.Multi.Vender.Food.Order.Web.config.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserOrdersResDto {

    private long orderId;
    private long restorentId;
    private String restorentName;
    private AddressResponseDto orderAddress;
    private List<FoodItemDto> foodList;
    private LocalDateTime dateTime;
    private double totalAmount;
    private OrderStatus orderStatus;
    private double deliveryFee;
    private String note;
    private PaymentStatus paymentStatus;
}
