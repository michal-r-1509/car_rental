package com.michal.car_rental_app.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteRequestDto {
    private boolean confirm;
    private String password;
}
