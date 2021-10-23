package com.quintor.worqplace.reservation.application.exceptions;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(Long id) {
        super("Could not find id of reservation " + id);
    }

}
