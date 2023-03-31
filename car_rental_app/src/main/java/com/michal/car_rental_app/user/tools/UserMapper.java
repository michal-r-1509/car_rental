package com.michal.car_rental_app.user.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.car_rental_app.domain.RoleType;
import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.domain.UserDetails;
import com.michal.car_rental_app.user.dto.UserDto;
import com.michal.car_rental_app.user.dto.UserRegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserMapper {
    private final ObjectMapper objectMapper;
    private final MD5Encoder md5Encoder;

    public User userMapper(UserRegistrationRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .password(md5Encoder.getMD5Hash(request.getPassword()))
                .active(true)
//                .role(objectMapper.convertValue(request.getRole().toUpperCase(), RoleType.class))
                .role(request.getRole())
                .createDate(LocalDateTime.now())
                .build();
    }

    public UserDetails userDataMapper(UserDto data, RoleType role) {
        if (role.equals(RoleType.CLIENT)) {
            return UserDetails.builder()
                    .name(data.getName())
                    .surname(data.getSurname())
                    .address(data.getAddress())
                    .phoneNumber(data.getPhoneNumber())
                    .build();
        } else if (role.equals(RoleType.LENDER)) {
            return UserDetails.builder()
                    .name(data.getName())
                    .address(data.getAddress())
                    .phoneNumber(data.getPhoneNumber())
                    .taxPayerIdentNo(data.getTaxPayerIdentNo())
                    .description(data.getDescription())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public UserDto userResponseMapper(User user) {
        if (user.getRole().equals(RoleType.CLIENT)) {
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .role(RoleType.CLIENT.toString())
                    .name(user.getUserDetails().getName())
                    .surname(user.getUserDetails().getSurname())
                    .address((user.getUserDetails().getAddress()))
                    .phoneNumber(user.getUserDetails().getPhoneNumber())
                    .taxPayerIdentNo("")
                    .description("")
                    .build();
        } else if (user.getRole().equals(RoleType.LENDER)) {
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .role(RoleType.LENDER.toString())
                    .name(user.getUserDetails().getName())
                    .surname("")
                    .address(user.getUserDetails().getAddress())
                    .phoneNumber(user.getUserDetails().getPhoneNumber())
                    .taxPayerIdentNo(user.getUserDetails().getTaxPayerIdentNo())
                    .description(user.getUserDetails().getDescription())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void userDataMapper(UserDto data, RoleType role, UserDetails userDetails) {
        if (role.equals(RoleType.CLIENT)) {
            userDetails.setName(data.getName());
            userDetails.setSurname(data.getSurname());
            userDetails.setAddress(data.getAddress());
            userDetails.setPhoneNumber(data.getPhoneNumber());
        } else if (role.equals(RoleType.LENDER)) {
            userDetails.setName(data.getName());
            userDetails.setAddress(data.getAddress());
            userDetails.setPhoneNumber(data.getPhoneNumber());
            userDetails.setTaxPayerIdentNo(data.getTaxPayerIdentNo());
            userDetails.setDescription(data.getDescription());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
