package com.michal.car_rental_app.user.dto;

import com.michal.car_rental_app.domain.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegistrationRequestDto {
    @NotNull
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
