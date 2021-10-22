package com.quintor.worqplace.reservable.application;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservable.domain.DeskType;
import com.quintor.worqplace.reservable.domain.ReservableInformation;
import com.quintor.worqplace.reservable.domain.ReservableRepository;
import com.quintor.worqplace.reservable.domain.Workplace;
import com.quintor.worqplace.reservation.application.ReservationService;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class ReservableServiceTest {
    private ReservableService reservableService;
    private Timeslot timeslot;

    @BeforeEach
    void populate() throws InvalidTimeslotException {
        Long id = 1L;
        Workplace workplace = new Workplace(id, "First",
                new ReservableInformation("Small worplace", "Solo workplace"),
                new ArrayList<>(), null,
                "Lovely place located on A1C",
                DeskType.HYBRID, null);
        this.timeslot = new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(10, 0), LocalTime.of(15, 0));

        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        ReservableRepository reservableRepository = mock(ReservableRepository.class);
        ReservationService reservationService = mock(ReservationService.class);

        when(reservableRepository.getById(1L)).thenReturn(workplace);
        when(reservationRepository.save(any())).then(returnsFirstArg());
        when(reservableRepository.save(any())).thenReturn(workplace);

        this.reservableService = new ReservableService(reservableRepository, reservationRepository, reservationService);
    }

    @Test
    @DisplayName("reserve should return a reservation object with the same timeslot")
    void reserve() throws TimeslotOverlapException {
        assertEquals(this.timeslot, this.reservableService.reserve(1L, this.timeslot, false).getTimeslot());
    }

    @Test
    @DisplayName("getReservableById should return corresponding reservable")
    void getReservableById() {
        assertEquals(1L, this.reservableService.getReservableById(1L).getId());
    }
}