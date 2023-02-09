package com.michal.car_rental_app.user.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.car_rental_app.domain.RoleType;
import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.user.dto.UserRequest;
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
}
