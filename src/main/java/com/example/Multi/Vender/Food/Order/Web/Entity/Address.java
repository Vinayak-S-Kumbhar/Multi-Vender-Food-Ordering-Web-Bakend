package com.example.Multi.Vender.Food.Order.Web.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String addressType;
    private String fullName;
    private String phone;

    private String addressLine1;
    private String addressLine2;
    private String landmark;

    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;

    private Boolean isDefault;
}