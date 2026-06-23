package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddFoodRetingDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.AddRetorentRatingDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.RatingsResponceDto;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public interface RatingsService {
    String addFoodReting(AddFoodRetingDto addFoodRetingDto);

    RatingsResponceDto getFoodRatings(long foodId,int page,int size);

    String addRestorentReting(AddRetorentRatingDto addRetorentRatingDto);

    RatingsResponceDto getRetorentRatings(long restorentId,int page,int size);
}
