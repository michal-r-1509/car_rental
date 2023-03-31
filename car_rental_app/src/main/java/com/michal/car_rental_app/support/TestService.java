package com.michal.car_rental_app.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    public TestEntity saveTestEntity(TestEntity testEntity) {
        TestEntity entity = testRepository.save(testEntity);
        System.out.println("SERVICE: " + entity);
        return entity;
    }
}
