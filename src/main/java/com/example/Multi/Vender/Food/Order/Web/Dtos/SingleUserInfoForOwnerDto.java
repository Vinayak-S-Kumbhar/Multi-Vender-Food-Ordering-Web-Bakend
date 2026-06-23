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
public class SingleUserInfoForOwnerDto {
    private UsersInfoForOwnerDto userInfo;
    private List<RestorentResDto> restorentInfo;

}
