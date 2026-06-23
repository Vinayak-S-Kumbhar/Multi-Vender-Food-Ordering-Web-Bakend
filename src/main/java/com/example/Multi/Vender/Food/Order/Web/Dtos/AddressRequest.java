package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddressRequest {

    private Long userId;

    private String addressType;
    private String fullName;
    private String phone;

    private String addressLine1;
    private String addressLine2;
    private String landmark;

    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;

    private Boolean isDefault;
}