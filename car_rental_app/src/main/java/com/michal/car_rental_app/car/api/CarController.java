package com.michal.car_rental_app.car.api;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.service.CarService;
import com.michal.car_rental_app.domain.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cars")
public class CarController {
    private final CarService carService;

    @PostMapping("/new")
    ResponseEntity<Void> saveCar(@RequestBody CarRequestDto requestDto){
        carService.saveCar(requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    ResponseEntity<List<Car>> getAllCars(){
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }
}
