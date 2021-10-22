package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ReservableTest {
    private Reservable reservable;
    private Reservation reservation;

    @BeforeEach
    void populate() throws InvalidTimeslotException {
        this.reservable = new Room("First", "Big room",
                "This room is on the first floor on A1C", true, false);
        Timeslot timeslot = new Timeslot(LocalDate.of(2021, 10, 22),
                LocalTime.of(12, 0), LocalTime.of(15, 0));
        Timeslot recurringTimeslot = new Timeslot(LocalDate.of(2021, 10, 22),
                LocalTime.of(8, 0), LocalTime.of(11, 0));
        this.reservation = new Reservation(reservable, timeslot, false);
        Reservation recurring = new Reservation(reservable, recurringTimeslot, true);
        this.reservable.addReservation(recurring);
    }

    @Test
    @DisplayName("addReservation should add the reservation to the list")
    void addReservation() {
        int oldSize = this.reservable.getReservations().size();
        this.reservable.addReservation(reservation);
        assertEquals(oldSize + 1, this.reservable.getReservations().size());
    }

    @Test
    @DisplayName("isNotAvailableDuringTimeslot should be false when recurring timeslot is on another day")
    void isNotAvailableDuringTimeslot() throws InvalidTimeslotException {
        Timeslot timeslot = new Timeslot(LocalDate.of(2021, 10, 23),
                LocalTime.of(10, 0), LocalTime.of(15, 0));
        assertFalse(this.reservable.isNotAvailableDuringTimeslot(timeslot));
    }
}