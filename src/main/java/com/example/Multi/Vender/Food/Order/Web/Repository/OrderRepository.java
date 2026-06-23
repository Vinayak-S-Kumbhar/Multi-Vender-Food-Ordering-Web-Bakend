package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
    SELECT DISTINCT o.*
    FROM orders o
    JOIN order_items oi
        ON o.id = oi.order_id
    JOIN food_item fi
        ON oi.food_item_id = fi.id
    JOIN restorent r
        ON fi.restorent_id = r.id
    WHERE r.id = :restorentId
    ORDER BY o.date_time DESC
    """, nativeQuery = true)
    List<Order> findByRestorentIdOrderByDateTimeDesc(Long restorentId);

    Page<Order> findAll(Pageable pageable);

    List<Order> findByUserIdOrderByDateTimeDesc(Long userId);
}