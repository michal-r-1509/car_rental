package com.michal.car_rental_app.user.api;

import com.michal.car_rental_app.user.dto.*;
import com.michal.car_rental_app.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/new")
    ResponseEntity<Void> createUser(@RequestBody UserRegistrationRequestDto user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/current")
    ResponseEntity<Void> updateUser(@RequestBody UserDto userData){
        userService.updateUser(userData);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/current")
    ResponseEntity<UserDto> getUser(){
        UserDto user = userService.getUser();
        System.out.println(user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/current")
    ResponseEntity<Void> deactivateUser(@RequestBody UserDeleteRequestDto request){
        userService.deleteUser(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
