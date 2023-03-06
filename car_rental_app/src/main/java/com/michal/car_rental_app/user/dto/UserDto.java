package com.michal.car_rental_app.user.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id = 0L;
    private String email = "";
    private String role = "";
    private String name = "";
    private String surname = "";
    private String address = "";
    private String phoneNumber = "";
    private String taxPayerIdentNo = "";
    private String description = "";

}
