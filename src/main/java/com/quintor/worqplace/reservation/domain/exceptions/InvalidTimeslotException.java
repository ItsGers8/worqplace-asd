package com.quintor.worqplace.reservation.domain.exceptions;

public class InvalidTimeslotException extends Exception {
    public InvalidTimeslotException(String message) {
        super(message);
    }
}
