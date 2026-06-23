package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.Cart;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserAndFoodItem(User user , FoodItem foodItem);

    boolean deleteAllByUser(User user);

    Long countByUser(User user);

    List<Cart> findAllByRestorentId(long restorentId);

}