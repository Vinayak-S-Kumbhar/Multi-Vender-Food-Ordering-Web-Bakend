package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Entity.Ratings;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    Optional<Ratings> findByUserAndFoodItem(User user, FoodItem foodItem);

    Optional<Ratings> findByUserAndRestorent(User user, Restorent restorent);

}
