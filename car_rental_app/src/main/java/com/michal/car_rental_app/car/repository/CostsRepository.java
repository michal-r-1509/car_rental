package com.michal.car_rental_app.car.repository;

import com.michal.car_rental_app.domain.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostsRepository extends JpaRepository<Cost, Long> {
}
