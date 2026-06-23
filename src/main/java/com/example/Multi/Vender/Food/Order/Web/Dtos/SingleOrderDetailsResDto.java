package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleOrderDetailsResDto {

    private OrderResponceDto orderResponce;
    private UserInfoDto customerInfo;
    private VendorDto vendor;
}
