package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}