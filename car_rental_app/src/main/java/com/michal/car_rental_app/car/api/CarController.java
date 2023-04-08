package com.michal.car_rental_app.car.api;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.dto.UserInfoResponseDto;
import com.michal.car_rental_app.car.service.CarService;
import com.michal.car_rental_app.domain.Car;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(@Qualifier("carServiceOnRuntime") CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/new")
    ResponseEntity<CarResponseDto> saveCar(@RequestBody CarRequestDto requestDto){
        CarResponseDto carResponseDto = carService.saveCar(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(carResponseDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<CarResponseDto> updateCar(@RequestBody CarRequestDto requestDto){
        CarResponseDto carResponseDto = carService.updateCar(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(carResponseDto);
    }

    @GetMapping("/{id}")
    ResponseEntity<CarResponseDto> getCar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCar(id));
    }

    @GetMapping
    ResponseEntity<List<CarResponseDto>> getAllAvailableCars(){
        List<CarResponseDto> cars = carService.getAllAvailableCars();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @GetMapping("/currentUser")
    ResponseEntity<List<CarResponseDto>> getCurrentUserCars(){
        List<CarResponseDto> cars = carService.getCurrentUserCars();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @GetMapping("/user/{carId}")
    ResponseEntity<UserInfoResponseDto> getUserInfo(@PathVariable Long carId){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getUserInfo(carId));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
