package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.repository.*;
import com.michal.car_rental_app.car.tools.CarMapper;
import com.michal.car_rental_app.domain.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final CarDetailsRepository carDetailsRepository;
    private final CostsRepository costsRepository;
    private final CarMapper carMapper;

    public void saveCar(CarRequestDto carRequestDto){
        Car car = carMapper.mapCarToDatabase(carRequestDto);
        carRepository.save(car);
        carDetailsRepository.save(car.getCarDetails());
        costsRepository.save(car.getCost());
    }

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();

        return cars;
    }
}
