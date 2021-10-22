package com.quintor.worqplace.reservation.application;

import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.Timeslot;
import com.quintor.worqplace.reservation.application.DTO.ReservationDTO;
import com.quintor.worqplace.reservation.domain.Reservation;
import com.quintor.worqplace.reservation.domain.ReservationRepository;
import com.quintor.worqplace.reservation.domain.exceptions.InvalidTimeslotException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationDTO updateReservationTimeslot(Long reservationId, LocalDate newDate, LocalTime newFrom, LocalTime newTo) throws InvalidTimeslotException {
        Reservation reservation = this.reservationRepository.getById(reservationId);
        reservation.updateTimeslot(newDate, newFrom, newTo);
        this.reservationRepository.save(reservation);
        return new ReservationDTO(reservation);
    }

    public List<Reservation> getReservationsAtTimeslot(Timeslot timeslot) {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getTimeslot().equals(timeslot))
                .collect(Collectors.toList());
    }

}
