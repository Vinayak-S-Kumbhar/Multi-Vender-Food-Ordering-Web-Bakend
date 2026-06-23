package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.CartDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.CartItemDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.CartItemResponceDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UpdateCartDto;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    String addToCart(CartDto cartDto);

    List<CartItemResponceDto> getCartItemsByUserId(long userId);

    String deleteCartItem(long foodId, long userId);

    int getUserCartCount(long userId);

    String deleteCartItemsByRestorent(long restorentId);

    String updateCartItem(UpdateCartDto updateCartDto);
}
