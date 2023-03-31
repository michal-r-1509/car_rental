package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.domain.Car;

import java.util.List;

public interface CarService {
    void saveCar(CarRequestDto carRequestDto);
    void updateCar(CarRequestDto carRequestDto);
    Car getCar(Long id);
    List<CarResponseDto> getAllCars();
    List<CarResponseDto> getAllAvailableCars();
    void deleteCar(Long id);
    List<CarResponseDto> getCurrentUserCars();
}
