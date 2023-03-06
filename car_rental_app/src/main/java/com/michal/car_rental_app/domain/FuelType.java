package com.michal.car_rental_app.domain;

import com.fasterxml.jackson.annotation.JsonValue;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FuelType {
    DIESEL("diesel"),
    GASOLINE("gasoline"),
    LPG("lpg"),
    CNG("cng"),
    HYBRID("hybrid"),
    ELECTRIC("electric");

    private final String fuelType;

    FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @JsonValue
    public String getFuelType() {
        return fuelType;
    }
}