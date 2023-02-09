package com.michal.car_rental_app.domain;

public enum RoleType {
    ADMIN("admin"),
    LENDER ("lender"),
    CLIENT("client"),
    VISITOR("visitor");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
