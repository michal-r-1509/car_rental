package com.michal.car_rental_app.support;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "api/test")
public class TestController {

    private final TestService testService;

    @PostMapping("/post")
    ResponseEntity<TestEntity> testPost(@RequestBody TestEntity testEntity){
        System.out.println("CONTROLLER: " + testEntity);
        TestEntity response = testService.saveTestEntity(testEntity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
