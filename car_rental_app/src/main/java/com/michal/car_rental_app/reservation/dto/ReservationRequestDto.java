package com.michal.car_rental_app.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {
    private LocalDate startDay;
    private LocalDate endDay;
    private Long carId;
}
