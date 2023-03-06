package com.michal.car_rental_app.car.dto;

import com.michal.car_rental_app.domain.Cost;
import com.michal.car_rental_app.domain.CarDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDto {
    private String regNo;
    private String brand;
    private String model;
    private boolean available;
    private CarDetails carDetails;
    private Cost cost;
}
