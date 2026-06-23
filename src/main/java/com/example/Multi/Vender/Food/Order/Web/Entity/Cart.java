package com.example.Multi.Vender.Food.Order.Web.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private FoodItem foodItem;

    private long restorentId;

    @ManyToOne
    private User user;

    private int quantity;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
