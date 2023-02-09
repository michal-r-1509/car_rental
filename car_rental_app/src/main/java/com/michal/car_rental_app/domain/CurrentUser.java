package com.michal.car_rental_app.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@SessionScope
@Component
public class CurrentUser {
    private Long id;
    private String email;
}
