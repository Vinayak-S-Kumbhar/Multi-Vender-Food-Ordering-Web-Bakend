package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsResDto;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ProblemDetail;

import java.util.List;

public interface ContactUsService {
    String addContact(ContactUsDto contactUsDto);

    List<ContactUsResDto> getAllContacts();
}
