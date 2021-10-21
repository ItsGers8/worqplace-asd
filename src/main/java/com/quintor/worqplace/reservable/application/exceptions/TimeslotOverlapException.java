package com.quintor.worqplace.reservable.application.exceptions;

public class TimeslotOverlapException extends Exception {
    public TimeslotOverlapException(String message) {
        super(message);
    }
}
