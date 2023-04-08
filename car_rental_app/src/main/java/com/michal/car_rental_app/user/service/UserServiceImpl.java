package com.michal.car_rental_app.user.service;

import com.michal.car_rental_app.car.repository.CarRepository;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.exceptions.IncorrectPasswordException;
import com.michal.car_rental_app.exceptions.UserExistException;
import com.michal.car_rental_app.reservation.repository.ReservationRepository;
import com.michal.car_rental_app.user.dto.UserDeleteRequestDto;
import com.michal.car_rental_app.user.dto.UserDto;
import com.michal.car_rental_app.user.dto.UserRegistrationRequestDto;
import com.michal.car_rental_app.user.repository.UserDetailsRepository;
import com.michal.car_rental_app.user.repository.UserRepository;
import com.michal.car_rental_app.user.tools.MD5Encoder;
import com.michal.car_rental_app.user.tools.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MD5Encoder md5Encoder;
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;
    private final CurrentUser currentUser;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void createUser(UserRegistrationRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserExistException("user with email " + request.getEmail() + " already exists");
        }
        User user = userMapper.userMapper(request);
        userRepository.save(user);
        log.info("user with id " + user.getId() + " created");
    }

    @Override
    public void updateUser(UserDto data) {
        User user = userRepository.findByIdAndActive(currentUser.getId(), true)
                .orElseThrow(getUserNotFound());

        UserDetails userDetails = userDetailsRepository
                .findUserDetailsByUserId(currentUser.getId()).orElse(new UserDetails());

        userMapper.userDataMapper(data, user.getRole(), userDetails);
        userDetailsRepository.save(userDetails);
        user.setUserDetails(userDetails);

        userRepository.save(user);
        System.out.println("currentUser check: " + currentUser.getEmail() + " === " + currentUser.getId());
        System.out.println(userDetails.getName());
        log.info("user with email " + user.getEmail() + " updated");
    }

    @Override
    public UserDto getUser() {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(getUserNotFound());
        if (user.getUserDetails() == null) {
            return new UserDto();
        }
        return userMapper.userResponseMapper(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UserDeleteRequestDto data) {
        User user = userRepository.findByIdAndActive(currentUser.getId(), true)
                .orElseThrow(getUserNotFound());
        if (user.getPassword().equals(md5Encoder.getMD5Hash(data.getPassword())) && data.isConfirm()) {
            user.setActive(false);
            userRepository.save(user);
            List<Car> cars = carRepository.findCarsByUserId(currentUser.getId());
            cars.forEach(car -> {
                        car.setAvailable(false);
                        carRepository.save(car);
                        reservationRepository.deleteByLenderId(currentUser.getId());
                    });
            return;
        }
        throw new IncorrectPasswordException("password incorrect");
    }

    private static Supplier<ElementNotFoundException> getUserNotFound() {
        return () -> new ElementNotFoundException("user not found");
    }

    @Override
    public void updateInitUser(UserDto data) {
        User user = userRepository.findById(data.getId()).orElseThrow(getUserNotFound());

        UserDetails userDetails = new UserDetails();

        userMapper.userDataMapper(data, user.getRole(), userDetails);
        userDetailsRepository.save(userDetails);
        user.setUserDetails(userDetails);

        userRepository.save(user);
        System.out.println(userDetails.getName());
        log.info("user with email " + user.getEmail() + " updated");
    }

    @Override
    public void createDummyUser(UserRegistrationRequestDto registrationDto, UserDto detailsDto) {
        User user = userMapper.userMapper(registrationDto);
        UserDetails userDetails = userMapper.userDataMapper(detailsDto, user.getRole());
        user.setUserDetails(userDetails);
        userRepository.save(user);
        userDetailsRepository.save(userDetails);
        log.info("dummy user with id " + user.getId() + " created");
    }
}
