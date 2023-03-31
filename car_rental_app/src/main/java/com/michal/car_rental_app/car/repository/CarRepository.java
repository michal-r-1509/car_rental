package com.michal.car_rental_app.car.repository;

import com.michal.car_rental_app.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByUserId(Long userId);
    List<Car> findCarsByAvailableIsTrue();
}
