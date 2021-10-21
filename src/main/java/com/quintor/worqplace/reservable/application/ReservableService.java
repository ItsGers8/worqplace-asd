package com.quintor.worqplace.reservable.application;

import com.quintor.worqplace.reservable.application.exceptions.TimeslotOverlapException;
import com.quintor.worqplace.reservable.domain.Reservable;
import com.quintor.worqplace.reservable.domain.ReservableRepository;
import com.quintor.worqplace.reservation.application.DTO.ReservationDTO;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
