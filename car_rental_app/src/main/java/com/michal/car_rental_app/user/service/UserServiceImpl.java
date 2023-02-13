package com.michal.car_rental_app.user.service;

import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.user.dto.UserUpdateRequest;
import com.michal.car_rental_app.user.dto.UserRequest;
import com.michal.car_rental_app.user.repository.ClientDetailRepository;
import com.michal.car_rental_app.user.repository.LenderDetailRepository;
import com.michal.car_rental_app.user.repository.UserRepository;
import com.michal.car_rental_app.user.tools.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ClientDetailRepository clientDetailRepository;
    private final LenderDetailRepository lenderDetailRepository;
    private final UserMapper userMapper;
    private final CurrentUser currentUser;

    @Override
    public void saveUser(UserRequest request) {
        User user = userMapper.userMapper(request);
        userRepository.save(user);
        log.info("user with id " + user.getId() + " created");
    }

    @Override
    public void updateUser(UserUpdateRequest data) {
//        TODO not working because of currentUser
        User user = userRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new ElementNotFoundException("user with email " + data.getEmail() + " not found"));
        UserDetails userDetails = userMapper.userDataMapper(data, user.getRole());

        if (user.getRole().equals(RoleType.CLIENT)) {
            user.setClientDetails((ClientDetails) userDetails);
            clientDetailRepository.save((ClientDetails) userDetails);
        } else if (user.getRole().equals(RoleType.LENDER)) {
            user.setLenderDetails((LenderDetails) userDetails);
            lenderDetailRepository.save((LenderDetails) userDetails);
        }

        userRepository.save(user);
        System.out.println("currentUser check: " + currentUser.getEmail() + " === " + currentUser.getId());
        log.info("user with email " + data.getEmail() + " is updated");
    }

    @Override
    public void testConnection() {
        System.out.println("user service test bean scope: " + currentUser.getEmail() + " === " + currentUser.getId());
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
