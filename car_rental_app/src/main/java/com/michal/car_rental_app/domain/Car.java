package com.michal.car_rental_app.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    private String brand;
    private String model;
    private boolean available;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "carDetailsId", referencedColumnName = "id")
    private CarDetails carDetails;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "costsId", referencedColumnName = "id")
    private Cost cost;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
