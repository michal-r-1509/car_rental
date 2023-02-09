package com.michal.car_rental_app.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.car_rental_app.domain.RoleType;
import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.user.dto.UserRequest;
import com.michal.car_rental_app.user.repository.UserRepository;
import com.michal.car_rental_app.user.tools.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void saveUser(UserRequest request) {
        User user = userMapper.userMapper(request);
        userRepository.save(user);
        log.info("user with id " + user.getId() + " created");
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
