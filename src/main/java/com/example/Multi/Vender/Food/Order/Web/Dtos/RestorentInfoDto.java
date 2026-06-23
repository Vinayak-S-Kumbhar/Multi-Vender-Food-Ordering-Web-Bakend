package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestorentInfoDto {

    private long id;
    private long userId;
    private String restorentName;
    private RestorentStatus status;
    private AddressResponseDto address;

}
