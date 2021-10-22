package com.quintor.worqplace.reservable.application;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservable.domain.*;
import com.quintor.worqplace.reservation.application.DTO.ReservationDTO;
import com.quintor.worqplace.reservation.application.ReservationService;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservableServiceTest {
    static Long id;
    static ReservableService reservableService;
    static Workplace workplace;
    static Timeslot timeslot;

    @BeforeAll
    static void setUp() throws InvalidTimeslotException {
        id = 1L;
        workplace = new Workplace(id,
                "first", "Solo workplace",
                "Lovely place located on A1C",
                "first",
                DeskType.HYBRID);
        timeslot = new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(10, 0), LocalTime.of(15, 0));
        Reservation reservation = new Reservation(2L, true, timeslot, workplace);
        workplace.addReservation(reservation);

        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        ReservableRepository reservableRepository = mock(ReservableRepository.class);
        ReservationService reservationService = mock(ReservationService.class);

        when(reservableRepository.getById(anyLong())).thenReturn(workplace);
        when(reservationRepository.save(any())).thenReturn(reservation);
        when(reservableRepository.save(any())).thenReturn(workplace);

        reservableService = new ReservableService(reservableRepository, reservationRepository, reservationService);
    }

    @Test
    @DisplayName("when the timeslots overlap a reservation should not be able to be made")
    void cannotReserveWhenTimeslotOverlaps() {
        assertThrows(TimeslotOverlapException.class, () ->
                reservableService.reserve(id, new Timeslot(LocalDate.now(), LocalTime.of(12, 0),
                        LocalTime.of(16, 0)), false));
    }

    @Test
    @DisplayName("when the timeslots are before or after each other a reservation should be made")
    void canReserveWhenTimeslotIsBeforeOrAfter() {
        assertDoesNotThrow(() -> reservableService.reserve(id,
                new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(16, 0), LocalTime.of(17, 0)),
                false));
        assertDoesNotThrow(() -> reservableService.reserve(id,
                new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(8, 0), LocalTime.of(9, 0)),
                false));
    }

    @Test
    @DisplayName("getting a reservable by id should return that reservable")
    void getReservableShouldReturnReservable() {
        assertEquals(id, reservableService.getReservableById(id).getId());
    }

    @Test
    @DisplayName("Recurring reservations on different days should not give an error")
    void recurringReservationDifferentDay() {
        assertDoesNotThrow(() -> reservableService.reserve(1L,
                new Timeslot(LocalDate.of(2021, 10, 23), LocalTime.of(10, 0),
                        LocalTime.of(15, 0)), false));
    }

    @Test
    @DisplayName("Recurring reservations on the same day should give an error")
    void recurringReservationSameDay() {
        assertThrows(TimeslotOverlapException.class, () -> reservableService.reserve(1L,
                new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(10, 0),
                        LocalTime.of(15, 0)), false));
    }
}