package com.michal.car_rental_app.support;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.service.CarService;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.user.dto.UserRegistrationRequestDto;
import com.michal.car_rental_app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class DatabaseInit {

    private final UserService userService;
    private final CarService carService;

    @EventListener
    public void onStartup(ApplicationReadyEvent event){
        UserRegistrationRequestDto userAdmin = UserRegistrationRequestDto.builder()
                .email("admin")
                .password("admin")
                .role("admin")
                .build();
        UserRegistrationRequestDto userClient = UserRegistrationRequestDto.builder()
                .email("c")
                .password("c")
                .role("client")
                .build();
        UserRegistrationRequestDto userLender = UserRegistrationRequestDto.builder()
                .email("l")
                .password("l")
                .role("lender")
                .build();
        userService.createUser(userAdmin);
        userService.createUser(userClient);
        userService.createUser(userLender);

        CarRequestDto carRequestDto_1 =  CarRequestDto.builder()
                .regNo("SC 29541")
                .brand("opel")
                .model("corsa")
                .available(true)
                .carDetails(new CarDetails(5, 250, GearboxType.MANUAL, FuelType.DIESEL))
                .cost(new Cost(BigDecimal.valueOf(120.0), BigDecimal.valueOf(590.0), BigDecimal.valueOf(2100.0)))
                .build();

        carService.saveCar(carRequestDto_1);

        log.info("INITIALIZED DATABASE WITH DUMMY ENTITIES");
    }
}
