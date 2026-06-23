package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RestorentDto {

    private long id;

    private long userId;

    private String restorentName;

    private RestorentType businessType;

    private String productCategerys;

    private int yearOfEstablish;

    private String ownerName;

    private double mobileNumber;

    private double alternativeMobNumber;

    private String email;

    private double averageRating;

    private int totalRatings;

    private LocalDateTime createdAt;

    private RestorentStatus status;

}
