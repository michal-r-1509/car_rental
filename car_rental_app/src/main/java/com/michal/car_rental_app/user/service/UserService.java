package com.michal.car_rental_app.user.service;

import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.user.dto.*;

import java.util.List;

public interface UserService {
    void createUser(UserRegistrationRequestDto user);
    void updateUser(UserDto user);
    UserDto getUser();
    List<User> getUsers();
    void deleteUser(UserDeleteRequestDto user);
}
