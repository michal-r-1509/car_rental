package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_details")
public class CarDetails extends BaseEntity {
    private int seats;
    private int trunkCap;
    @Enumerated(EnumType.STRING)
    private GearboxType gearboxType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

}