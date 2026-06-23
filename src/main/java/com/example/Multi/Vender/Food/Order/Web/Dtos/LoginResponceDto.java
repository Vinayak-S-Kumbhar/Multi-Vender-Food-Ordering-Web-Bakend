package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponceDto {
    private long userId;
    private String accessToken;
    private UserRole userRole;


}
