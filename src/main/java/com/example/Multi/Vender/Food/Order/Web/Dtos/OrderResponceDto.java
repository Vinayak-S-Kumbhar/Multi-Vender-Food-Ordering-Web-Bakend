package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import com.example.Multi.Vender.Food.Order.Web.config.PaymentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponceDto {
    private long orderId;
    private AddressResponseDto addressResponse;
    private double totalAmount;
    private double savings;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private String note;
    private LocalDateTime orderTime;
    private RestorentStatus restorentStatus;
    private List<OrderItemsDto> itemList;
}
