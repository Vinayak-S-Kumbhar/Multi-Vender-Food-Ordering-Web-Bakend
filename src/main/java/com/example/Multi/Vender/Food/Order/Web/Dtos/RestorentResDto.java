package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestorentResDto {
    private long id;
    private long userId;
    private String restorentName;
    private RestorentStatus status;
    private LocalDateTime createdAt;
    private double averageRating;
    private int totalRatings;
    private AddressResponseDto address;
}
