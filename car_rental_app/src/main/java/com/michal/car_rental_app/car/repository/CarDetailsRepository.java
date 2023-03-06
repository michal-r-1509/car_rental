package com.michal.car_rental_app.car.repository;

import com.michal.car_rental_app.domain.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
}
