package com.michal.car_rental_app.reservation.tools;

import com.michal.car_rental_app.car.tools.CarMapper;
import com.michal.car_rental_app.car.tools.UserInfoMapper;
import com.michal.car_rental_app.domain.Car;
import com.michal.car_rental_app.domain.Reservation;
import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.reservation.dto.ReservationRequestDto;
import com.michal.car_rental_app.reservation.dto.ReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    private final CarMapper carMapper;
    private final UserInfoMapper userInfoMapper;

    public Reservation mapReservationToDatabase(ReservationRequestDto requestDto, Long clientId, Long lenderId,
                                                Long carId, BigDecimal totalCost) {
        return Reservation.builder()
                .startDay(requestDto.getStartDay())
                .endDay(requestDto.getEndDay())
                .totalCost(totalCost)
                .clientId(clientId)
                .lenderId(lenderId)
                .carId(carId)
                .build();
    }

    public ReservationResponseDto mapReservationToResponse(Reservation reservation, User user, Car car) {
        String surname = user.getUserDetails().getSurname();
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .startDay(reservation.getStartDay())
                .endDay(reservation.getEndDay())
                .daysAmount((int)ChronoUnit.DAYS.between(reservation.getStartDay(), reservation.getEndDay()))
                .totalCost(reservation.getTotalCost())
                .brand(car.getBrand())
                .model(car.getModel())
                .name(user.getUserDetails().getName() + " " + surname != null ? surname : "")
                .address(user.getUserDetails().getAddress())
                .phoneNumber(user.getUserDetails().getPhoneNumber())
                .build();
    }
}
