package com.michal.car_rental_app.support;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.michal.car_rental_app.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "for_test")
public class TestEntity extends BaseEntity {
    private int testNumber;
    private String testString;
    @Enumerated(EnumType.STRING)
    private TestEnum testEnum;

    @Override
    public String toString() {
        return  "testNumber = " + testNumber +
                ", testString = '" + testString + '\'' +
                ", testEnum = " + testEnum;
    }
}
