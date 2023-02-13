package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientDetails")
public class ClientDetails extends BaseEntity implements UserDetails {
    private String name;
    private String surname;
    private String phoneNumber;

    @OneToOne(mappedBy = "clientDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(referencedColumnName = "client_details_id")
    private User user;
}
