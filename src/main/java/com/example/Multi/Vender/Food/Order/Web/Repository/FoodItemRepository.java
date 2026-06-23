package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.config.FoodCategery;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    boolean existsByFoodnameAndRestorentId(String foodname, long restorentId);

    List<FoodItem> findByRestorentId(long restorentId);

    Page<FoodItem> findByFoodCategeryAndIsAvailableTrue(FoodCategery categery,Pageable pageable);

    Page<FoodItem> findByIsAvailableTrueAndRestorent_Status(
            RestorentStatus status,
            Pageable pageable
    );

}