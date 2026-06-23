package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RatingsDto {
    private long ratingId;
    private String name;
    private double ratings;
    private String review;
    private LocalDateTime createdAt;
}
