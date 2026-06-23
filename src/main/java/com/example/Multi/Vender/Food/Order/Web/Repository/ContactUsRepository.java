package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
}
