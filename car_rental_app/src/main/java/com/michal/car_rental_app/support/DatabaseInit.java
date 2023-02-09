package com.michal.car_rental_app.support;

import com.michal.car_rental_app.user.dto.UserRequest;
import com.michal.car_rental_app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInit {

    private final UserService userService;

    @EventListener
    public void onStartup(ApplicationReadyEvent event){
        UserRequest userAdmin = UserRequest.builder()
                .email("admin")
                .password("admin")
                .role("admin")
                .build();
        UserRequest userClient = UserRequest.builder()
                .email("nowy")
                .password("nowy")
                .role("client")
                .build();
        UserRequest userLender = UserRequest.builder()
                .email("lender")
                .password("lender")
                .role("lender")
                .build();
        userService.saveUser(userAdmin);
        userService.saveUser(userClient);
        userService.saveUser(userLender);

        log.info("INITIALIZED DATABASE WITH DUMMY ENTITIES");
    }
}
