package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemResDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.config.FoodCategery;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodType;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FoodItemService {

    String addFoodItem(String foodname, String description, FoodCategery foodCategory, FoodType foodType, double price, long restaurantId, MultipartFile file) throws Exception;

    List<FoodItemResDto> getAllFoodItmes(int page, int size);

    String deleteFoodItem(long id);

    List<FoodItemDto> getAllFoodByHotelId(long id);

    List<FoodItemDto> getAllFoodByCategery(FoodCategery categery,int page, int size);

    FoodItemDto getFoodItemById(long foodId);

    boolean updateIsAvailable(long foodId);

    List<FoodItemResDto> getAllFoodItmesForOwner(int page, int size);

}
