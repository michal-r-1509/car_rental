package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.dto.UserInfoResponseDto;

import java.util.List;

public interface CarService {
    CarResponseDto saveCar(CarRequestDto carRequestDto);
    CarResponseDto updateCar(CarRequestDto carRequestDto);
    CarResponseDto getCar(Long id);
    List<CarResponseDto> getAllCars();
    List<CarResponseDto> getAllAvailableCars();
    void deleteCar(Long id);
    List<CarResponseDto> getCurrentUserCars();
    UserInfoResponseDto getUserInfo(Long id);
}
