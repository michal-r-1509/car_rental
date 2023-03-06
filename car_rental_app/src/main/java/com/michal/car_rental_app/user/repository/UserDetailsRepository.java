package com.michal.car_rental_app.user.repository;

import com.michal.car_rental_app.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> getUserDetailsByUserId(Long userId);
    Optional<UserDetails> findUserDetailsByUserId(Long userId);
}
