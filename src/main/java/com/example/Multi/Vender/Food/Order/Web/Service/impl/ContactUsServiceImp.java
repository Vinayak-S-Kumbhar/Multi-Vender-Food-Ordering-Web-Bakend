package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsResDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.ContactUs;
import com.example.Multi.Vender.Food.Order.Web.Repository.ContactUsRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.ContactUsService;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.ContactUsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactUsServiceImp implements ContactUsService {

    private final ContactUsRepository contactUsRepository;
    private final ContactUsMapper contactUsMapper;

    @Override
    public String addContact(ContactUsDto contactUsDto) {
        ContactUs contactUs = ContactUs.builder()
                .reason(contactUsDto.getReason())
                .name(contactUsDto.getName())
                .email(contactUsDto.getEmail())
                .message(contactUsDto.getMessage())
                .createdAt(LocalDateTime.now()).build();

        contactUsRepository.save(contactUs);
        return "Message send successfully! Thank you for contacting us! We'll get back to you soon";
    }

    @Override
    public List<ContactUsResDto> getAllContacts() {
        List<ContactUs> contactUs = contactUsRepository.findAll();
        return contactUs.stream().map(contactUsMapper::toDto).toList();
    }
}
