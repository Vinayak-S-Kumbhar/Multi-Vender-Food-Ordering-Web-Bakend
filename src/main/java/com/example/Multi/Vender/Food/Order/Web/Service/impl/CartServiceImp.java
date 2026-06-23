package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.CartDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.CartItemDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.CartItemResponceDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UpdateCartDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.Cart;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.Repository.CartRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.FoodItemRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.RestorentRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.CartService;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;
    private final FoodItemRepository foodItemRepository;
    private final RestorentRepository restorentRepository;
    private final FoodItemMapper foodItemMapper;

    @Override
    public String addToCart(CartDto cartDto) {
        User user = usersRepository.findById(cartDto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found with this user id : " + cartDto.getUserId()));

        if(user.getUserRole() == UserRole.BLOCKED){
            throw new RuntimeException("User is Blocked!");
        }
        FoodItem foodItem = foodItemRepository.findById(cartDto.getFoodId()).orElseThrow(() ->
                new RuntimeException("Food Item not found with this Food id : " + cartDto.getFoodId()));
        Restorent restorent = restorentRepository.findById(cartDto.getRestorentId()).orElseThrow(() ->
                new RuntimeException("Restorent Not Found With this Id : " +cartDto.getRestorentId()));
        if(!foodItem.isAvailable()){
            throw new RuntimeException("Food is not Available Now! try after Sometimes");
        }

        if(restorent.getStatus() != RestorentStatus.APPROVED){
            throw new RuntimeException("The Restorenis Temperary " + restorent.getStatus() + "! Please try some other restorent");
        }

        Cart cartItem = cartRepository.findByUserAndFoodItem(user,foodItem);
        if(cartItem != null) {
            cartItem.setQuantity(cartDto.getQuantity() + cartItem.getQuantity());
            Cart savedCart = cartRepository.save(cartItem);
            return "Added to Cart";
        }
        Cart cart = Cart.builder()
                .user(user)
                .foodItem(foodItem)
                .restorentId(cartDto.getRestorentId())
                .quantity(cartDto.getQuantity())
                .createdAt(LocalDateTime.now()).build();
        Cart SavedCart = cartRepository.save(cart);

        return "Added To Cart";
    }


    @Override
    public List<CartItemResponceDto> getCartItemsByUserId(long userId) {

        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("user not found with this id : " + userId));

        // latest added cart first
        List<Cart> cartList = user.getCart().stream()
                .sorted((c1, c2) ->
                        c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .toList();

        // preserve latest restorent order
        Map<Restorent, List<Cart>> groupedCart =
                cartList.stream()
                        .collect(Collectors.groupingBy(
                                cart -> cart.getFoodItem().getRestorent(),
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

        List<CartItemResponceDto> cartItemResponceDto =
                groupedCart.entrySet().stream().map(entry -> {

                    Restorent restorent = entry.getKey();

                    CartItemResponceDto cartItemResDto =
                            CartItemResponceDto.builder()
                                    .restorentId(restorent.getId())
                                    .restorentName(restorent.getRestorentName())
                                    .ownerName(restorent.getOwnerName())
                                    .mobileNumber(restorent.getMobileNumber())
                                    .email(restorent.getEmail())
                                    .build();

                    List<CartItemDto> cartItemDto =
                            entry.getValue().stream()
                                    .map(cart -> new CartItemDto(
                                            cart.getFoodItem().getId(),
                                            cart.getFoodItem().getFoodname(),
                                            cart.getFoodItem().getDescription(),
                                            cart.getFoodItem().getPrice(),
                                            cart.getQuantity()
                                    ))
                                    .toList();

                    cartItemResDto.setFoodItemList(cartItemDto);

                    return cartItemResDto;

                }).toList();

        return cartItemResponceDto;
    }

    public String deleteCartItem(long foodId, long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this id : " + userId));

        List<Cart> cartList = user.getCart();
        Cart cart = cartList.stream().filter(c -> c.getFoodItem().getId() == foodId).findFirst().orElseThrow(() -> new RuntimeException("Cart item not found"));

        user.getCart().remove(cart);
        usersRepository.save(user);
        return "Cart Item Removed";
    }


    @Override
    public int getUserCartCount(long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this User Id : " +userId));

        return user.getCart().size();
    }

    @Override
    public String deleteCartItemsByRestorent(long restorentId) {
        Restorent restorent = restorentRepository.findById(restorentId).orElseThrow(() ->
                new RuntimeException("Restorent not found By this Id : " + restorentId));

        List<Cart> cartList = cartRepository.findAllByRestorentId(restorentId);

        cartRepository.deleteAll(cartList);

        return "Cart Items Removed SuccessFully";
    }

    @Override
    public String updateCartItem(UpdateCartDto updateCartDto) {
        User user = usersRepository.findById(updateCartDto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found with this Id : " + updateCartDto.getUserId()));
        FoodItem foodItem = foodItemRepository.findById(updateCartDto.getFoodId()).orElseThrow(() ->
                new RuntimeException("Food not Found with this Id : " + updateCartDto.getFoodId()));

        List<Cart> cartList = user.getCart();


        Cart cart = cartList.stream().filter(cart1 -> cart1.getFoodItem().getId() == updateCartDto.getFoodId()).findFirst().orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.setQuantity(updateCartDto.getQuantity());

        cartRepository.save(cart);
        return "Cart Food Quantity updated SuccessFully";
    }


}
