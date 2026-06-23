package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequestDto {

    private String username;
    private String password;

}
