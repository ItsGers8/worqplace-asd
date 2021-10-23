package com.quintor.worqplace.reservation.domain;

import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class ReservationTest {
    @Test
    @DisplayName("timeslot changes when updateTimeslot method is called")
    void timeslotChanges() throws InvalidTimeslotException {
        LocalDate date = LocalDate.of(2021, 10, 19);
        LocalTime from = LocalTime.of(13, 00);
        LocalTime to = LocalTime.of(14, 00);
        LocalDate newDate = LocalDate.of(2021, 10, 20);

        Timeslot timeslot = new Timeslot(date, from, to);
        Reservation reservation1 = new Reservation(timeslot);
        Reservation reservation = new Reservation(timeslot);
        reservation.updateTimeslot(newDate, from, to);

        assertNotEquals(reservation, reservation1);
    }

    @Test
    @DisplayName("date changes when inserting a new date")
    void dateChanges() throws InvalidTimeslotException {
        LocalDate date = LocalDate.of(2021, 10, 19);
        LocalTime from = LocalTime.of(13, 00);
        LocalTime to = LocalTime.of(14, 00);
        LocalDate newDate = LocalDate.of(2021, 10, 20);

        Timeslot timeslot = new Timeslot(date, from, to);
        Reservation reservation = new Reservation(timeslot);

        assertNotEquals(newDate, reservation.getTimeslot().getDate());

        reservation.updateTimeslot(newDate, from, to);

        assertEquals(newDate, reservation.getTimeslot().getDate());
    }

    @Test
    @DisplayName("time changes when inserting a new time")
    void timeChanges() throws InvalidTimeslotException {
        LocalDate date = LocalDate.of(2021, 10, 19);
        LocalTime from = LocalTime.of(13, 00);
        LocalTime to = LocalTime.of(14, 00);
        LocalTime newFrom = LocalTime.of(16, 00, 00);
        LocalTime newTo = LocalTime.of(17, 00, 00);

        Timeslot timeslot = new Timeslot(date, from, to);
        Reservation reservation = new Reservation(timeslot);

        assertNotEquals(newFrom, reservation.getTimeslot().getFromTime());
        assertNotEquals(newTo, reservation.getTimeslot().getToTime());

        reservation.updateTimeslot(date, newFrom, newTo);

        assertEquals(newFrom, reservation.getTimeslot().getFromTime());
        assertEquals(newTo, reservation.getTimeslot().getToTime());

    }

}
