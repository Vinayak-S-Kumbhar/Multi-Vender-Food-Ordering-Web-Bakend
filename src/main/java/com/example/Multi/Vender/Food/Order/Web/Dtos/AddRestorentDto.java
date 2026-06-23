package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRestorentDto {


    private long userId;

    private String restorentName;

    private RestorentType businessType;

    private String productCategerys;

    private int yearOfEstablish;

    private String ownerName;

    private String mobileNumber;

    private String alternativeMobNumber;

    private String email;

    private String fssaiNumber;

    private LocalDate fssaiExpiry;

    private String accountNumber;

    private String ifscCode;

    private String accountType;
}
