package com.michal.car_rental_app.car.tools;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.domain.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    public Car mapCarToDatabase(CarRequestDto carRequestDto){
        Car car = Car.builder()
                .regNo(carRequestDto.getRegNo())
                .brand(carRequestDto.getBrand())
                .model(carRequestDto.getModel())
                .available(carRequestDto.isAvailable())
                .carDetails(carRequestDto.getCarDetails())
                .cost(carRequestDto.getCost())
                .build();
        return car;
    }
}
