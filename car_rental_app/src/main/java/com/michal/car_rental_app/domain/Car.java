package com.michal.car_rental_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{
    private String regNo;
    private String brand;
    private String model;
    private boolean available;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "carDetailsId", referencedColumnName = "id")
    private CarDetails carDetails;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "costsId", referencedColumnName = "id")
    private Cost cost;
}
