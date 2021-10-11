package com.quintor.worqplace.application.exception;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException(Long id) {
        super("Reservation with id " + id + "was not found.");
    }
}
