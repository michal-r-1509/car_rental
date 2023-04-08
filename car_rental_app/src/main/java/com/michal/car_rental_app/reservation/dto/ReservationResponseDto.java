package com.michal.car_rental_app.reservation.dto;

import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.dto.UserInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private LocalDate startDay;
    private LocalDate endDay;
    private BigDecimal totalCost;
    private int daysAmount;
    private String brand;
    private String model;
    private String name;
    private String address;
    private String phoneNumber;
}
