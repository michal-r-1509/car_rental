package com.michal.car_rental_app.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails extends BaseEntity {
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String taxPayerIdentNo;
    private String description;

    @OneToOne(mappedBy = "userDetails")
    @JoinColumn(name = "user_id")
    private User user;
}
