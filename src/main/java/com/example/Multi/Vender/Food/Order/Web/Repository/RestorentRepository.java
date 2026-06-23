package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.Address;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestorentRepository extends JpaRepository<Restorent, Long> {

    boolean existsByEmail(String email);

    boolean existsByFssaiNumber(String fssaiNumber);

    List<Restorent> findByUserId(long userId);

    List<Restorent> findByStatus(RestorentStatus status);

    List<Restorent> findByUserIdOrderByCreatedAtDesc(long userId);

}