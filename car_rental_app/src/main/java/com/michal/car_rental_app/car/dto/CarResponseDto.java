package com.michal.car_rental_app.car.dto;

import com.michal.car_rental_app.domain.CarDetails;
import com.michal.car_rental_app.domain.Cost;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private boolean available;
    private CarDetails carDetails;
    private Cost cost;
}
