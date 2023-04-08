package com.michal.car_rental_app.reservation.repository;

import com.michal.car_rental_app.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCarId(Long carId);
    List<Reservation> findByClientId(Long carId);
    List<Reservation> findByLenderId(Long carId);
    void deleteByLenderId(Long lenderId);
}
