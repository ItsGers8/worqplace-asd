package com.quintor.worqplace.reservation.application;

import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    @Test
    @DisplayName("changes a timeslot")
    void changeTimeslot(){
        LocalDate date = LocalDate.of(2021, 10, 19);
        LocalTime from = LocalTime.of(13, 00);
        LocalTime to = LocalTime.of(14, 00);
        LocalDate newDate = LocalDate.of(2021, 10, 20);

        Timeslot timeslot = new Timeslot(date, from, to);
        Reservation reservation = new Reservation(timeslot);
        reservation.updateTimeslot(newDate, from, to);
        Reservation expectedOutcome = reservation;

        ReservationRepository reservationRepository = mock(ReservationRepository.class);

        ReservationService service = new ReservationService(reservationRepository);
        when(reservationRepository.findByReservationId(any())).thenReturn(Optional.of(reservation));
        Reservation result = service.updateReservationTimeslot(reservation.getId(), newDate, from, to);

        assertEquals(expectedOutcome, result);
    }

}
