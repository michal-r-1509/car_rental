package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_details")
public class CarDetails extends BaseEntity {
    private int seats;
    @Enumerated(EnumType.STRING)
    private GearboxType gearboxType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

}
