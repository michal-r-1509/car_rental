package com.michal.car_rental_app.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GearboxType {
    MANUAL("manual"),
    AUTO("auto");

    private final String gearboxType;

    GearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    @JsonValue
    public String getGearboxType() {
        return gearboxType;
    }
}
