package com.michal.car_rental_app.user.service;

import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.user.dto.UserUpdateRequest;
import com.michal.car_rental_app.user.dto.UserRequest;

import java.util.List;

public interface UserService {
    void saveUser(UserRequest user);
    void updateUser(UserUpdateRequest user);
    User getUser(Long id);
    List<User> getUsers();
    void deleteUser(Long id);
    void testConnection();
}
