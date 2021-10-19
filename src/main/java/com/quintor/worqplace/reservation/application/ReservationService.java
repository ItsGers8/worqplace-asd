package com.quintor.worqplace.reservation.application;

import com.quintor.worqplace.reservation.application.DTO.ReservationDTO;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationDTO updateReservationTimeslot(Long reservationId, LocalDate newDate, LocalTime newFrom, LocalTime newTo){
        Reservation reservation = this.reservationRepository.getById(reservationId);
        reservation.updateTimeslot(newDate, newFrom, newTo);
        this.reservationRepository.save(reservation);
        return new ReservationDTO(reservation);
    }
}
