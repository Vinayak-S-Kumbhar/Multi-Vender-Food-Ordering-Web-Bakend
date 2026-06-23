package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class VendorDto {

    private Long venderId;
    private String ownerName;
    private String restorentName;

    private String mobileNumber;
    private String email;
    private RestorentType businessType;

    private int totalRatings;
    private double averageRating;
    private AddressResponseDto address;
}