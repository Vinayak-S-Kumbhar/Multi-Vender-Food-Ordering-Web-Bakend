package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.Service.RestorentService;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/Restorent")
public class RestorentControler {

    private final RestorentService restorentService;

    @PostMapping("/Restorent/add")
    public ResponseEntity<String> addRestorent(@RequestBody AddRestorentDto restorentDto){
        return ResponseEntity.ok(restorentService.addRestorent(restorentDto));
    }

    @GetMapping("/Restorent/user/{userId}")
    public ResponseEntity<List<RestorentInfoDto>> getUserRestorents(@PathVariable long userId){
        return ResponseEntity.ok(restorentService.getUserRestorents(userId));
    }

    @GetMapping("/public/Restorent/Food/{FoodId}")
    public ResponseEntity<RestorentWithFoodListDto> getRestorentAndFoodListFromFoodId(@PathVariable long FoodId) {
        return ResponseEntity.ok(restorentService.getRestorentAndFoodListFromFoodId(FoodId));
    }

    @GetMapping("/public/Restorent/all")
    public ResponseEntity<List<RestorentDto>> getAllRestorents(){
        return ResponseEntity.ok(restorentService.getAllRestorents());
    }

    @GetMapping("/public/Restorent/{restorentId}")
    public ResponseEntity<RestorentWithFoodListDto> getRestorentAndFoodListFromRestorentId(@PathVariable long restorentId) {
        return ResponseEntity.ok(restorentService.getRestorentAndFoodListFromRestorentId(restorentId));
    }

    @PatchMapping("/Restorent/status/{restorentId}/status")
    public ResponseEntity<String> updateRestorentStatus(@PathVariable long restorentId,@RequestParam RestorentStatus status){
        return ResponseEntity.ok((restorentService.updateRestorentStatus(restorentId,status)));
    }

    @GetMapping("/Restorent/owner/all")
    public ResponseEntity<List<RestorentDto>> getAllRestorentsForOwner(){
        return ResponseEntity.ok(restorentService.getAllRestorentsForOwner());
    }

    @GetMapping("/Restorent/{userId}")
    public ResponseEntity<List<RequestedRestorentDto>> getUserAllRestorents(@PathVariable long userId){
        return ResponseEntity.ok(restorentService.getUserAllRestorents(userId));
    }

}
