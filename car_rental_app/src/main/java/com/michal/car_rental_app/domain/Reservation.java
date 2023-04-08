package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{
    private LocalDate startDay;
    private LocalDate endDay;
    private BigDecimal totalCost;
    private Long clientId;
    private Long lenderId;
    private Long carId;
}
