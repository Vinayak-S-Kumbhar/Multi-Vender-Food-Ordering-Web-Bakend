package com.example.Multi.Vender.Food.Order.Web.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int ratings;

    private String review;

    @ManyToOne
    private FoodItem foodItem;

    @ManyToOne
    private Restorent restorent;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;
}
