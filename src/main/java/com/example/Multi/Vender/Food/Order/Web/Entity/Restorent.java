package com.example.Multi.Vender.Food.Order.Web.Entity;

import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Restorent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String restorentName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RestorentType businessType;

    @Column(nullable = false)
    private String productCategerys;

    @Column(nullable = false)
    private int yearOfEstablish;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String mobileNumber;

    private String alternativeMobNumber;

    @Email
    @Column(nullable = false)
    private String email;

    @ManyToOne
    private Address address;

    @Column(unique = true,nullable = false)
    private String fssaiNumber;

    private LocalDate fssaiExpiry;

    private String accountNumber;

    private String ifscCode;

    private String accountType;

    private LocalDateTime createdAt;

    private double averageRating = 0.0;

    private int totalRatings = 0;

    @Enumerated(EnumType.STRING)
    private RestorentStatus status;

    @OneToMany(mappedBy = "restorent" ,cascade = CascadeType.ALL)
    private List<FoodItem> foodItemList = new ArrayList<>();

    @OneToMany(mappedBy = "restorent")
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "restorent" , cascade = CascadeType.ALL)
    private List<Ratings> retings = new ArrayList<>();


}
