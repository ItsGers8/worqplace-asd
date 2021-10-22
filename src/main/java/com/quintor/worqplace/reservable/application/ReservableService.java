package com.quintor.worqplace.reservable.application;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservable.domain.Reservable;
import com.quintor.worqplace.reservable.domain.ReservableRepository;
import com.quintor.worqplace.reservation.application.DTO.ReservationDTO;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservable.domain.Reservable;
import com.quintor.worqplace.reservable.domain.ReservableRepository;
import com.quintor.worqplace.reservable.domain.Room;
import com.quintor.worqplace.reservation.application.ReservationService;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.AllArgsConstructor;
import net.bytebuddy.dynamic.loading.ClassInjector;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReservableService {
    private final ReservableRepository reservableRepository;
    private final ReservationRepository reservationRepository;

    public ReservationDTO reserve(Long id, Timeslot timeslot, boolean recurring) throws TimeslotOverlapException {
        Reservable reservable = this.reservableRepository.getById(id);
        for (int i = 0; i < reservable.getReservations().size(); i++) {
            Reservation currentReservation = reservable.getReservations().get(i);
            Timeslot existing = currentReservation.getTimeslot();
            if (existing.getDate() != timeslot.getDate() && !currentReservation.isRecurring()) {
                continue;
            }
            if (timeslot.getFromTime().isAfter(existing.getToTime())
                    && timeslot.getToTime().isBefore(existing.getFromTime())) {
                continue;
            }
            throw new TimeslotOverlapException("There is already a timeslot");
        }
        Reservation reservation = new Reservation(reservable, timeslot, recurring);

        reservable.addReservation(reservation);

        this.reservationRepository.save(reservation);
        this.reservableRepository.save(reservable);

        return new ReservationDTO(reservation);
    }

    public Reservable getReservableById(Long id) {
        return this.reservableRepository.getById(id);
    }

    public void save(Reservable reservable) {
        this.reservableRepository.save(reservable);
    }

    public void remove(Reservable reservable) {
        this.reservableRepository.delete(reservable);
    }
    private final ReservableRepository repository;
    private final ReservationService reservationService;

    public List<Room> getAvailableRoomsAtTimeslot(Timeslot timeslot){
        List<Room> unavailableRooms = getReservedRoomsAtTimeslot(timeslot);
        List<Room> allRooms = getAllRooms();
        allRooms.removeAll(unavailableRooms);
        return allRooms;
    }

    public List<Room> getAllRooms(){
        List<Reservable> reservables = repository.findAll().stream()
                .filter(reservable -> reservable instanceof Room)
                .collect(Collectors.toList());
        List<Room> allRooms = new ArrayList<>();
        for(Reservable reservable : reservables){
            allRooms.add((Room)reservable);
        }
        return allRooms;
    }

    public List<Room> getReservedRoomsAtTimeslot(Timeslot timeslot){
        List<Reservation> reservations = reservationService.getReservationsAtTimeslot(timeslot);
        List<Room> rooms = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getReservable() instanceof Room) {
                rooms.add((Room) reservation.getReservable());
            }
        }
        return rooms;
    }
}
