package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UsersInfoForOwnerDto {

    private long userId;

    private String customerName;

    private String email;

    private String city;

    private LocalDateTime registerdAt;

    private int totalOrders;

    private double totalSpend;

    private UserRole userRole;

    private List<AddressResponseDto> address;
}
