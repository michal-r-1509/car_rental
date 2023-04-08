package com.michal.car_rental_app.car.tools;

import com.michal.car_rental_app.car.dto.UserInfoResponseDto;
import com.michal.car_rental_app.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {
    public UserInfoResponseDto mapUserToResponse(User user) {
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .name(user.getUserDetails().getName())
                .address(user.getUserDetails().getAddress())
                .phoneNumber(user.getUserDetails().getPhoneNumber())
                .description(user.getUserDetails().getDescription())
                .build();
    }
}
