package com.michal.car_rental_app.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String taxPayerIdentNo;
    private String description;
}
