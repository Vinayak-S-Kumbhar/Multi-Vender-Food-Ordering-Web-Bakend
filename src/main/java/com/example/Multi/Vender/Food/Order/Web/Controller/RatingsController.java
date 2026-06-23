package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddFoodRetingDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.AddRetorentRatingDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.RatingsResponceDto;
import com.example.Multi.Vender.Food.Order.Web.Service.RatingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/Rating")
@RequiredArgsConstructor
public class RatingsController {
    private final RatingsService retingsService;

    @PostMapping("/Rating/Food/add")
    public ResponseEntity<String> addFoodReting(@RequestBody AddFoodRetingDto addFoodRetingDto){
        return ResponseEntity.ok(retingsService.addFoodReting(addFoodRetingDto));
    }

    @PostMapping("/Rating/Restorent/add")
    public ResponseEntity<String> addRestorentReting(@RequestBody AddRetorentRatingDto addRetorentRatingDto){
        return ResponseEntity.ok(retingsService.addRestorentReting(addRetorentRatingDto));
    }

    @GetMapping("/public/Rating/food/{foodId}")
    public ResponseEntity<RatingsResponceDto> getFoodRatings(@PathVariable long foodId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(retingsService.getFoodRatings(foodId,page,size));
    }
    @GetMapping("/public/Rating/restorent/{restorentId}")
    public ResponseEntity<RatingsResponceDto> getRetorentRatings(@PathVariable long restorentId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(retingsService.getRetorentRatings(restorentId, page, size));
    }
}
