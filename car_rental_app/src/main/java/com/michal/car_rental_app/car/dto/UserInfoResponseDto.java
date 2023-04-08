package com.michal.car_rental_app.car.dto;

import lombok.*;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;
}
