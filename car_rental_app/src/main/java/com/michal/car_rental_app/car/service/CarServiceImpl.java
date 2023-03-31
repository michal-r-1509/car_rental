package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.repository.CarDetailsRepository;
import com.michal.car_rental_app.car.repository.CarRepository;
import com.michal.car_rental_app.car.repository.CostsRepository;
import com.michal.car_rental_app.car.tools.CarMapper;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.exceptions.ActionNotAllowedException;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("carServiceOnRuntime")
@Transactional
public class CarServiceImpl implements CarService{
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarDetailsRepository carDetailsRepository;
    private final CostsRepository costsRepository;
    private final CarMapper carMapper;
    private final CurrentUser currentUser;

    @Override
    public void saveCar(CarRequestDto carRequestDto) {
        lenderPermissionCheck(currentUser.getRole());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));

        Car car = carMapper.mapCarToDatabase(carRequestDto, user, userInfoCompletenessCheck(user));
        carRepository.save(car);
        carDetailsRepository.save(car.getCarDetails());
        costsRepository.save(car.getCost());

        user.getCars().add(car);
        userRepository.save(user);
        log.info("car created");
    }

    @Override
    public void updateCar(CarRequestDto carRequestDto) {
        lenderPermissionCheck(currentUser.getRole());
        Car toUpdate = carRepository.findById(carRequestDto.getId()).orElseThrow(
                () -> new ElementNotFoundException("car with id: " + carRequestDto.getId() + " not found"));
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));

        toUpdate.setCarDetails(carRequestDto.getCarDetails());
        toUpdate.setBrand(carRequestDto.getBrand());
        toUpdate.setModel(carRequestDto.getModel());
        toUpdate.setAvailable(carRequestDto.isAvailable() && userInfoCompletenessCheck(user));
        toUpdate.setCost(carRequestDto.getCost());

        carRepository.save(toUpdate);
        carDetailsRepository.save(toUpdate.getCarDetails());
        costsRepository.save(toUpdate.getCost());
        log.info("car updated");
    }

    @Override
    public List<CarResponseDto> getCurrentUserCars() {
        lenderPermissionCheck(currentUser.getRole());
        List<Car> cars = carRepository.findCarsByUserId(currentUser.getId());
        return cars.stream().map(carMapper::mapCarToResponse).collect(Collectors.toList());
    }

    @Override
    public Car getCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("car with id: " + id + " not found"));
    }

    @Override
    public List<CarResponseDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(carMapper::mapCarToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getAllAvailableCars() {
        List<Car> cars = carRepository.findCarsByAvailableIsTrue();
        return cars.stream().map(carMapper::mapCarToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        lenderPermissionCheck(currentUser.getRole());
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            log.info("car deleted");
        } else {
            throw new ElementNotFoundException("car with id: " + id + " not found");
        }
    }

    private void lenderPermissionCheck(RoleType role) {
        if (!role.equals(RoleType.LENDER) && !role.equals(RoleType.ADMIN)){
            throw new ActionNotAllowedException("action is not allowed at current account type");
        }
    }

    boolean userInfoCompletenessCheck(User user) {
        UserDetails details = user.getUserDetails();
        if (details == null) {
            return false;
        } else if (details.getAddress().isEmpty() || details.getPhoneNumber().isEmpty() ||
                details.getTaxPayerIdentNo().isEmpty()) {
            return false;
        }else {
            return true;
        }
    }

}
