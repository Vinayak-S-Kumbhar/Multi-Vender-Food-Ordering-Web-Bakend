package com.example.Multi.Vender.Food.Order.Web.Dtos;

import com.example.Multi.Vender.Food.Order.Web.config.FoodCategery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FoodItemDto {

    private long id;

    private String imageUrl;

    private String foodname;

    private String description;

    private FoodCategery foodCategery;

    private double price;

    private int totalRatings;

    private double averageRating;

    private boolean isAvailable;

    private long restorentId;

}
