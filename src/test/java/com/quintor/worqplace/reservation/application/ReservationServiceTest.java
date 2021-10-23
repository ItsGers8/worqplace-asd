package com.quintor.worqplace.reservation.application;

import com.quintor.worqplace.reservable.application.ReservableService;
import com.quintor.worqplace.reservable.domain.*;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReservationServiceTest {
    Timeslot timeslot;
    ReservationService reservationService;
    @BeforeEach
    void populate() throws InvalidTimeslotException {

        Room room1 = new Room("First", "Big boy room", "A room for hefty lads", true, true);
        Room room2 = new Room("First", "Small boy room", "A room for miniscule men", true, true);

        this.timeslot = new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(10, 0), LocalTime.of(15, 0));
        Reservation reservation1 = new Reservation(room1, this.timeslot, false);
        Reservation reservation2 = new Reservation(room2, this.timeslot, false);

        ReservationRepository reservationRepository = mock(ReservationRepository.class);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);

        when(reservationRepository.findAll()).thenReturn(reservations);
        this.reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void getReservationsAtTimeslot() {
        this.reservationService.getReservationsAtTimeslot(this.timeslot)
                .forEach(reservation -> assertEquals(this.timeslot, reservation.getTimeslot()));
    }

}