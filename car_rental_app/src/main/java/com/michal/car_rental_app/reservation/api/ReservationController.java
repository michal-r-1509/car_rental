package com.michal.car_rental_app.reservation.api;

import com.michal.car_rental_app.reservation.dto.ReservationRequestDto;
import com.michal.car_rental_app.reservation.dto.ReservationResponseDto;
import com.michal.car_rental_app.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/new")
    ResponseEntity<Void> saveReservation(@RequestBody ReservationRequestDto requestDto){
        reservationService.saveReservation(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    ResponseEntity<List<ReservationResponseDto>> getReservations(){
        List<ReservationResponseDto> list = reservationService.getReservations();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
