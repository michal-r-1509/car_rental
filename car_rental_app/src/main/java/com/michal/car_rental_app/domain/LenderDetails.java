package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lenderDetails")
public class LenderDetails extends BaseEntity implements UserDetails{
    private String name;
    private String address;
    private String phoneNumber;
    private String taxPayerIdentNo;
    private String description;

    @OneToOne(mappedBy = "lenderDetails")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
