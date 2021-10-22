package com.quintor.worqplace.reservation.application;

import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationId;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation updateReservationTimeslot(ReservationId reservationId, LocalDate newDate, LocalTime newFrom, LocalTime newTo){
        Reservation reservation = this.reservationRepository.findByReservationId(reservationId).orElseThrow();
        if (reservation != null) {
            reservation.updateTimeslot(newDate, newFrom, newTo);
            this.reservationRepository.save(reservation);
            return reservation;
        }
        return null;
    }
}
