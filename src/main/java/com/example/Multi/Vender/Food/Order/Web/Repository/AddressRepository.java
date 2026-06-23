package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);
}