package com.michal.car_rental_app.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@NoArgsConstructor
@SessionScope
@Component
public class CurrentUser {
    //TODO not working because of frontend session issue
    private Long id;
    private String email;
}
