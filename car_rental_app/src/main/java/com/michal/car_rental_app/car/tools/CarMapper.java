package com.michal.car_rental_app.car.tools;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.domain.Car;
import com.michal.car_rental_app.domain.User;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapCarToDatabase(CarRequestDto carRequestDto, User user, boolean isUserInfoComplete) {
        return Car.builder()
                .brand(carRequestDto.getBrand())
                .model(carRequestDto.getModel())
                .available(carRequestDto.isAvailable() && isUserInfoComplete)
                .carDetails(carRequestDto.getCarDetails())
                .cost(carRequestDto.getCost())
                .user(user)
                .build();
    }

    public Car mapCarToDatabase(CarRequestDto carRequestDto, boolean isUserInfoComplete) {
        return Car.builder()
                .brand(carRequestDto.getBrand())
                .model(carRequestDto.getModel())
                .available(carRequestDto.isAvailable() && isUserInfoComplete)
                .carDetails(carRequestDto.getCarDetails())
                .cost(carRequestDto.getCost())
                .build();
    }

    public CarResponseDto mapCarToResponse(Car car) {
        return CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .available(car.isAvailable())
                .carDetails(car.getCarDetails())
                .cost(car.getCost())
                .build();
    }

    public Car testMapCarToDatabase(CarRequestDto carRequestDto, User user, boolean isUserInfoComplete) {
        Car car = Car.builder()
                .brand(carRequestDto.getBrand())
                .model(carRequestDto.getModel())
                .available(carRequestDto.isAvailable() && isUserInfoComplete)
                .carDetails(carRequestDto.getCarDetails())
                .cost(carRequestDto.getCost())
                .build();
        if (user != null){
            car.setUser(user);
        }
        return car;
    }
}
