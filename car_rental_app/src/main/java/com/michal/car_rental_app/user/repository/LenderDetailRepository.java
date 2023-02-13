package com.michal.car_rental_app.user.repository;

import com.michal.car_rental_app.domain.LenderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LenderDetailRepository extends JpaRepository<LenderDetails, Long> {
}
