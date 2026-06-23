package com.example.Multi.Vender.Food.Order.Web.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ContactUsResDto {

    private long id;

    private String reason;

    private String name;

    private String email;

    private String message;

    private LocalDateTime createdAt;

}
