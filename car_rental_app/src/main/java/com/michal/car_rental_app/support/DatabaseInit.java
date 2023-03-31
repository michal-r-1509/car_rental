package com.michal.car_rental_app.support;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.service.CarService;
import com.michal.car_rental_app.car.service.CarServiceImpl;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.user.dto.UserDto;
import com.michal.car_rental_app.user.dto.UserRegistrationRequestDto;
import com.michal.car_rental_app.user.service.UserService;
import com.michal.car_rental_app.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@Transactional
public class DatabaseInit {

    private final UserService userService;
    private final CarService carService;

    public DatabaseInit(UserService userService,
                        @Qualifier("carServiceDatabaseInit") CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    @EventListener
    public void onStartup(ApplicationReadyEvent event){
        UserRegistrationRequestDto userAdmin = UserRegistrationRequestDto.builder()
                .email("admin")
                .password("admin")
                .role(RoleType.ADMIN)
                .build();
        UserRegistrationRequestDto userClient = UserRegistrationRequestDto.builder()
                .email("c")
                .password("c")
                .role(RoleType.CLIENT)
                .build();
        UserRegistrationRequestDto userLender = UserRegistrationRequestDto.builder()
                .email("l")
                .password("l")
                .role(RoleType.LENDER)
                .build();

        UserDto lenderDetailsDto = UserDto.builder()
                .address("Krakow")
                .name("Majkel")
                .surname("Scott")
                .phoneNumber("509641555")
                .taxPayerIdentNo("5621457895")
                .description("przykladowe")
                .build();

        UserDto clientDetailsDto = UserDto.builder()
                .address("Krakow")
                .name("Jim")
                .surname("Halpert")
                .phoneNumber("654341555")
                .taxPayerIdentNo("")
                .description("")
                .build();

        userService.createUser(userAdmin);
        userService.createDummyUser(userClient, clientDetailsDto);
        userService.createDummyUser(userLender, lenderDetailsDto);

        CarRequestDto carRequestDto_1 =  CarRequestDto.builder()
                .brand("opel")
                .model("corsa")
                .available(true)
                .carDetails(new CarDetails(5, GearboxType.MANUAL, FuelType.DIESEL))
                .cost(new Cost(BigDecimal.valueOf(120.0), BigDecimal.valueOf(2100.0)))
                .build();
        CarRequestDto carRequestDto_2 =  CarRequestDto.builder()
                .brand("ford")
                .model("focus")
                .available(true)
                .carDetails(new CarDetails(5, GearboxType.MANUAL, FuelType.DIESEL))
                .cost(new Cost(BigDecimal.valueOf(220.0), BigDecimal.valueOf(2200.0)))
                .build();

        carService.saveCar(carRequestDto_1);
        carService.saveCar(carRequestDto_2);

        log.info("INITIALIZED DATABASE WITH DUMMY ENTITIES");
    }
}
