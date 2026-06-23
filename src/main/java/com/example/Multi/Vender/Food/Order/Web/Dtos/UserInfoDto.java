package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDto {

    private long id;
    private String name;
    private String username;
    private UserRole userRole;
    private int cardCount;
}
