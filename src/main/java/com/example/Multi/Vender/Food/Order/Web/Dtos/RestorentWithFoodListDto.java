package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestorentWithFoodListDto {
    private long id;
    private String restorentName;
    private RestorentType businessType;
    private String productCategerys;
    private int yearOfEstablish;
    private String ownerName;
    private String mobileNumber;
    private String alternativeMobNumber;
    private String email;
    private double averageRating;
    private int totalRatings;
    private int totalOrders;
    private RestorentStatus status;
    private LocalDateTime createdAt;
    private AddressResponseDto address;
    private List<FoodItemDto> foodItemList;
}
