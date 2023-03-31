package com.michal.car_rental_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "costs")
public class Cost extends BaseEntity{
    private BigDecimal perDay;
    private BigDecimal insurance;
}
