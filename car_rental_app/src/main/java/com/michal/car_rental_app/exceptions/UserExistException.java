package com.michal.car_rental_app.exceptions;

public class UserExistException extends RuntimeException{

    public UserExistException(String message) {
        super(message);
    }
}
