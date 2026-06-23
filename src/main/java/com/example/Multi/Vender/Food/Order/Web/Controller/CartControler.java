package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.CartDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.CartItemResponceDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UpdateCartDto;
import com.example.Multi.Vender.Food.Order.Web.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartControler {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDto cartDto){
        return ResponseEntity.ok(cartService.addToCart(cartDto));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<CartItemResponceDto>> getCartItemsByUserId(@PathVariable long userId){
        return ResponseEntity.ok(cartService.getCartItemsByUserId(userId));
    }

    @DeleteMapping("/{foodId}/{userId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable long foodId, @PathVariable long userId){
        return ResponseEntity.ok(cartService.deleteCartItem(foodId,userId));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> getUserCartCount(@PathVariable long userId){
        return ResponseEntity.ok(cartService.getUserCartCount(userId));
    }

    @DeleteMapping("/restaurant/{restorentId}")
    public ResponseEntity<String> deleteCartItemsByRestorent(@PathVariable long restorentId){
        return ResponseEntity.ok(cartService.deleteCartItemsByRestorent(restorentId));
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody UpdateCartDto updateCartDto){
        return ResponseEntity.ok(cartService.updateCartItem(updateCartDto));
    }

}
