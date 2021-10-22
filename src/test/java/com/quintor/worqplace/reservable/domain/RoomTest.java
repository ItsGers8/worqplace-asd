package com.quintor.worqplace.reservable.domain;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomTest {
    private Room room;
    private Timeslot timeslot;

    @BeforeEach
    void populate() throws InvalidTimeslotException {
        this.room = new Room("First", "Big room",
                "This room is on the first floor, next to A1C", true, false);
        this.timeslot = new Timeslot(LocalDate.of(2021, 10, 22),
                LocalTime.of(10, 0), LocalTime.of(15, 0));
    }

    @Test
    @DisplayName("addWorkplace should add the workplace to the list")
    void addWorkplace() {
        int oldSize = this.room.getWorkplaces().size();
        this.room.addWorkplace(new Workplace());
        assertEquals(oldSize + 1, this.room.getWorkplaces().size());
    }

    @Test
    @DisplayName("reserve should link reservation and room")
    void reserve() throws TimeslotOverlapException {
        Reservation reservation = this.room.reserve(timeslot, false);
        assertEquals(this.room, reservation.getReservable());
        assertTrue(this.room.getReservations().contains(reservation));
    }
}