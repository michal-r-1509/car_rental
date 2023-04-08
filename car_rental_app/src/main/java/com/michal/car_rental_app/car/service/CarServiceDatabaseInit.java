package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.dto.UserInfoResponseDto;
import com.michal.car_rental_app.car.repository.CarDetailsRepository;
import com.michal.car_rental_app.car.repository.CarRepository;
import com.michal.car_rental_app.car.repository.CostsRepository;
import com.michal.car_rental_app.car.tools.CarMapper;
import com.michal.car_rental_app.domain.Car;
import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("carServiceDatabaseInit")
@Transactional
public class CarServiceDatabaseInit implements CarService{
    private final CarRepository carRepository;
    private final CarDetailsRepository carDetailsRepository;
    private final CostsRepository costsRepository;
    private final CarMapper carMapper;

    @Override
    public CarResponseDto saveCar(CarRequestDto carRequestDto) {
        Car car = carMapper.mapCarToDatabase(carRequestDto, true);
        carRepository.save(car);
        carDetailsRepository.save(car.getCarDetails());
        costsRepository.save(car.getCost());
        log.info("car created");
        return null;
    }

    @Override
    public CarResponseDto updateCar(CarRequestDto carRequestDto) {
        return null;
    }

    @Override
    public List<CarResponseDto> getCurrentUserCars() {
        return List.of();
    }

    @Override
    public UserInfoResponseDto getUserInfo(Long id) {
        return null;
    }

    @Override
    public CarResponseDto getCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("car with id: " + id + " not found"));
        return carMapper.mapCarToResponse(car);
    }

    @Override
    public List<CarResponseDto> getAllCars() {
        return List.of();
    }

    @Override
    public List<CarResponseDto> getAllAvailableCars() {
        return List.of();
    }

    @Override
    public void deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            log.info("car deleted");
        } else {
            throw new ElementNotFoundException("car with id: " + id + " not found");
        }
    }
}
