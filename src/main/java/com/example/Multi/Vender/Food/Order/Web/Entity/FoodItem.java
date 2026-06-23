package com.example.Multi.Vender.Food.Order.Web.Entity;

import com.example.Multi.Vender.Food.Order.Web.config.FoodCategery;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String foodname;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_categery")
    private FoodCategery foodCategery;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type",nullable = false)
    private FoodType foodType;

    @Column(nullable = false)
    private double price;

    private double averageRating =0.0;

    private int totalRatings = 0;

    private boolean isAvailable;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restorent restorent;

    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cart> cart;

    @OneToMany(mappedBy = "foodItem" , cascade = CascadeType.ALL)
    private List<Ratings> retings = new ArrayList<>();

}
