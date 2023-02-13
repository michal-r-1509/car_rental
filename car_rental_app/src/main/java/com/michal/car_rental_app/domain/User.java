package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private LocalDateTime createDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_details_id", referencedColumnName = "id")
    private ClientDetails clientDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lender_details_id", referencedColumnName = "id")
    private LenderDetails lenderDetails;
}
