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

class WorkplaceTest {
    private Workplace workplace;
    private Timeslot timeslot;

    @BeforeEach
    void populate() throws InvalidTimeslotException {
        this.workplace = new Workplace("First", "Workplace in room",
                "Lovely place located on A1C",
                "Simple black chair",
                DeskType.HYBRID);
        this.timeslot = new Timeslot(LocalDate.of(2021, 10, 22),
                LocalTime.of(10, 0), LocalTime.of(15, 0));
    }

    @Test
    @DisplayName("reserve should link reservation and room")
    void reserve() throws TimeslotOverlapException {
        Reservation reservation = this.workplace.reserve(this.timeslot, false);
        assertEquals(this.workplace, reservation.getReservable());
        assertTrue(this.workplace.getReservations().contains(reservation));
    }
}