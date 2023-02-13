package com.michal.car_rental_app.user.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.user.dto.UserRequest;
import com.michal.car_rental_app.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserMapper {
    private final ObjectMapper objectMapper;
    private final MD5Encoder md5Encoder;

    public User userMapper(UserRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .password(md5Encoder.getMD5Hash(request.getPassword()))
                .role(objectMapper.convertValue(request.getRole().toUpperCase(), RoleType.class))
                .createDate(LocalDateTime.now())
                .build();
        return user;
    }


    public UserDetails userDataMapper(UserUpdateRequest data, RoleType role) {
        if (role.equals(RoleType.CLIENT)){
            return ClientDetails.builder()
                    .name(data.getName())
                    .surname(data.getSurname())
                    .phoneNumber(data.getPhoneNumber())
                    .build();
        } else if (role.equals(RoleType.LENDER)) {
            return LenderDetails.builder()
                    .name(data.getName())
                    .address(data.getAddress())
                    .phoneNumber(data.getPhoneNumber())
                    .taxPayerIdentNo(data.getTaxPayerIdentNo())
                    .description(data.getDescription())
                    .build();
        }else {
            throw new IllegalArgumentException();
        }
    }
}
