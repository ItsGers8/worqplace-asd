package com.quintor.worqplace.reservable.application;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservable.domain.*;
import com.quintor.worqplace.reservation.application.ReservationService;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class ReservableServiceTest {
    private ReservableService reservableService;
    private Timeslot timeslot;
    private ReservationRepository reservationRepo;

    @BeforeEach
    void populate() throws InvalidTimeslotException {
        Long id = 1L;
        Workplace workplace = new Workplace(id, "First",
                new ReservableInformation("Small worplace", "Solo workplace"),
                new ArrayList<>(), null,
                "Lovely place located on A1C",
                DeskType.HYBRID, null);

        Room room1 = new Room("First", "Big boy room", "A room for hefty lads", true, true);
        Room room2 = new Room("First", "Small boy room", "A room for miniscule men", true, true);

        this.timeslot = new Timeslot(LocalDate.of(2021, 10, 22), LocalTime.of(10, 0), LocalTime.of(15, 0));
        Reservation reservation1 = new Reservation(room1, this.timeslot, false);
        Reservation reservation2 = new Reservation(room2, this.timeslot, false);

        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        ReservableRepository reservableRepository = mock(ReservableRepository.class);
        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);

        when(reservableRepository.getById(1L)).thenReturn(workplace);
        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationService.getReservationsAtTimeslot(this.timeslot)).thenReturn(reservations);
        when(reservationRepository.save(any())).then(returnsFirstArg());
        when(reservableRepository.save(any())).thenReturn(workplace);

        this.reservationRepo = reservationRepository;
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

    @Test
    @DisplayName("getReservedRoomsAtTimeslot should return all reservation objects with the same timeslot")
    void getReservedRoomsAtTimeslot(){
        List<Room> reservedRooms = this.reservableService.getReservedRoomsAtTimeslot(this.timeslot);
        assertEquals(2, reservedRooms.size());
        List<Reservation> reservationsAtTimeSlot = this.reservationRepo.findAll()
                .stream()
                .filter(reservation -> reservation.getTimeslot().equals(this.timeslot) && reservation.getReservable() instanceof Room)
                .collect(Collectors.toList());
        for (Reservation reservation : reservationsAtTimeSlot) {
            assertTrue(reservedRooms.contains(
                    (Room) reservation.getReservable()
            ));
        }
    }
}