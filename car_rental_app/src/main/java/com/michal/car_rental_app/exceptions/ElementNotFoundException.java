package com.michal.car_rental_app.exceptions;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(final String message) {
        super(message);
    }
}
