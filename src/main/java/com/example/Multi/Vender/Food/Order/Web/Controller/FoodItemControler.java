package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemResDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Repository.FoodItemRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.FoodItemService;
import com.example.Multi.Vender.Food.Order.Web.config.FoodCategery;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodItemControler {

    private final FoodItemService foodItemService;
    private final FoodItemRepository foodItemRepository;

    @PostMapping(value = "/FoodItem/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addFoodItem( @RequestParam("foodname") String foodname,
                                               @RequestParam("description") String description,
                                               @RequestParam("foodCategery") FoodCategery foodCategery,
                                               @RequestParam("foodType") FoodType foodType,
                                               @RequestParam("price") double price,
                                               @RequestParam("restorentId") long restorentId
            ,@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodItemService.addFoodItem(foodname,description,foodCategery,foodType,price,restorentId,file));
    }


    @GetMapping("/public/FoodItem/list")
    public ResponseEntity<List<FoodItemResDto>> getAllFoodItems(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(foodItemService.getAllFoodItmes(page,size));
    }

    @GetMapping("/public/FoodItem/list/categery/{categery}")
    public ResponseEntity<List<FoodItemDto>> getAllFoodByCategery(@PathVariable FoodCategery categery,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(foodItemService.getAllFoodByCategery(categery,page, size));
    }

    @GetMapping("/FoodItem/list/Hotel/{hotelId}")
    public ResponseEntity<List<FoodItemDto>> getAllFoodByHotelId(@PathVariable long hotelId){
        return ResponseEntity.ok(foodItemService.getAllFoodByHotelId(hotelId));
    }
    @GetMapping("/public/FoodItem/{FoodId}")
    public ResponseEntity<FoodItemDto> getFoodItemById(@PathVariable long FoodId){
        return ResponseEntity.ok(foodItemService.getFoodItemById(FoodId));
    }

    @DeleteMapping("/FoodItem/{id}")
    public ResponseEntity<String> deleteFoodItem(@PathVariable long id){
        return ResponseEntity.ok(foodItemService.deleteFoodItem(id));
    }

    @PatchMapping("/FoodItem/isAvailable/{foodId}")
    public ResponseEntity<Boolean> updateIsAvailable(@PathVariable long foodId){
        return ResponseEntity.ok(foodItemService.updateIsAvailable(foodId));
    }

    @GetMapping("/Owner/foodItem/list")
    public ResponseEntity<List<FoodItemResDto>> getAllFoodItmesForOwner(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(foodItemService.getAllFoodItmesForOwner(page,size));
    }

}
