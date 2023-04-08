package com.michal.car_rental_app.reservation.service;

import com.michal.car_rental_app.car.repository.CarRepository;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.exceptions.ActionNotAllowedException;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.reservation.dto.ReservationRequestDto;
import com.michal.car_rental_app.reservation.dto.ReservationResponseDto;
import com.michal.car_rental_app.reservation.repository.ReservationRepository;
import com.michal.car_rental_app.reservation.tools.ReservationMapper;
import com.michal.car_rental_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CurrentUser currentUser;
    private final ReservationMapper reservationMapper;

    public void saveReservation(ReservationRequestDto requestDto) {
        if (!requestCorrectnessCheck(requestDto)) {
            throw new IllegalArgumentException("reservation data are incorrect");
        }

        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));
        Car car = carRepository.findById(requestDto.getCarId()).orElseThrow(
                () -> new ElementNotFoundException("car with id: " + requestDto.getCarId() + " not found"));

        if (!car.isAvailable()){
            throw new ActionNotAllowedException("cannot lend inactive car");
        }

        BigDecimal totalCost = totalCost(requestDto, car.getCost());
        reservationRepository.save(reservationMapper.mapReservationToDatabase(requestDto, user.getId(),
                car.getUser().getId(), car.getId(), totalCost));
    }

    public List<ReservationResponseDto> getReservations() {
        userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));

        List<ReservationResponseDto> responseList = new ArrayList<>();

        if (currentUser.getRole().equals(RoleType.CLIENT)){
            List<Reservation> reservations = reservationRepository.findByClientId(currentUser.getId());
            responseList = reservations.stream()
                    .map(reservation -> {
                        User lender = userRepository.findById(reservation.getLenderId()).orElse(null);
                        Car car = carRepository.findById(reservation.getCarId()).orElse(null);
                        return reservationMapper.mapReservationToResponse(reservation, lender, car);
                    })
                    .collect(Collectors.toList());
        }
        if (currentUser.getRole().equals(RoleType.LENDER)){
            List<Reservation> reservations = reservationRepository.findByLenderId(currentUser.getId());
            responseList = reservations.stream()
                    .map(reservation -> {
                        User client = userRepository.findById(reservation.getClientId()).orElse(null);
                        Car car = carRepository.findById(reservation.getCarId()).orElse(null);
                        return reservationMapper.mapReservationToResponse(reservation, client, car);
                    })
                    .collect(Collectors.toList());
        }
        return responseList;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private boolean requestCorrectnessCheck(ReservationRequestDto requestDto) {
        List<Reservation> list = reservationRepository.findByCarId(requestDto.getCarId());
        if(list.isEmpty()){
            return true;
        }else {
            for (Reservation r : list) {
                if (requestDto.getStartDay().isEqual(r.getStartDay()) ||
                        requestDto.getEndDay().isEqual(r.getEndDay()) ||
                        requestDto.getStartDay().isEqual(r.getEndDay()) ||
                        requestDto.getEndDay().isEqual(r.getStartDay())){
                    System.out.println("days are equals: " + r.getId());
                    return false;
                }
                if (requestDto.getStartDay().isAfter(r.getStartDay()) &&
                        requestDto.getStartDay().isBefore(r.getEndDay())){
                    System.out.println("start day between start/end: " + r.getId());
                    return false;
                }
                if ((requestDto.getEndDay().isAfter(r.getStartDay()) &&
                        requestDto.getEndDay().isBefore(r.getEndDay()))) {
                    System.out.println("end day between start/end " + r.getId());
                    return false;
                }
                if (requestDto.getStartDay().isBefore(r.getStartDay()) &&
                        requestDto.getEndDay().isAfter(r.getEndDay())) {
                    System.out.println("reservation surrounds other reservation: "  + r.getId());
                    return false;
                }
            }
        }
        System.out.println("returned true");
        return true;
    }

    private BigDecimal totalCost(ReservationRequestDto requestDto, Cost cost) {
        long daysBetween = ChronoUnit.DAYS.between(requestDto.getStartDay(), requestDto.getEndDay());
        return (BigDecimal.valueOf(daysBetween + 1L).multiply(cost.getPerDay())).add(cost.getInsurance());
    }
}
