package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.repository.CarDetailsRepository;
import com.michal.car_rental_app.car.repository.CarRepository;
import com.michal.car_rental_app.car.repository.CostsRepository;
import com.michal.car_rental_app.car.tools.CarMapper;
import com.michal.car_rental_app.domain.Car;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveCar(CarRequestDto carRequestDto) {
        Car car = carMapper.mapCarToDatabase(carRequestDto, true);
        carRepository.save(car);
        carDetailsRepository.save(car.getCarDetails());
        costsRepository.save(car.getCost());
        log.info("car created");
    }

    @Override
    public void updateCar(CarRequestDto carRequestDto) {
    }

    @Override
    public List<CarResponseDto> getCurrentUserCars() {
        return List.of();
    }

    @Override
    public Car getCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("car with id: " + id + " not found"));
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
