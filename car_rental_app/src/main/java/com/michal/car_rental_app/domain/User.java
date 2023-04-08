package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String email;
    private String password;
    private boolean active = true;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private LocalDateTime createDate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private UserDetails userDetails;

    /*@OneToMany(orphanRemoval = true)
    private List<Car> cars;*/
}
