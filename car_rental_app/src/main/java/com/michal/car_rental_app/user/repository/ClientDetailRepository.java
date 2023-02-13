package com.michal.car_rental_app.user.repository;

import com.michal.car_rental_app.domain.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailRepository extends JpaRepository<ClientDetails, Long> {
}
