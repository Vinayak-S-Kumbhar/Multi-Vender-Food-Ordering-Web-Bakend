package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.ContactUsResDto;
import com.example.Multi.Vender.Food.Order.Web.Service.ContactUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactUsControler {

    private final ContactUsService contactUsService;

    @PostMapping("/public/Contact")
    public ResponseEntity<String> addContact(@RequestBody ContactUsDto contactUsDto){
        return ResponseEntity.ok(contactUsService.addContact(contactUsDto));
    }

    @GetMapping("/Contact")
    public ResponseEntity<List<ContactUsResDto>> getAllContacts(){
        return ResponseEntity.ok(contactUsService.getAllContacts());
    }

}
