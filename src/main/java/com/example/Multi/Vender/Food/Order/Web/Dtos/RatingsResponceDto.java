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
public class RatingsResponceDto {
    private double averageRatings;
    private int totalRatings;
    private List<RatingsDto> ratingsList;
    private int page;
    private int totalPages;
    private boolean last;

    private long oneStar;
    private long twoStar;
    private long threeStar;
    private long fourStar;
    private long fiveStar;
}
